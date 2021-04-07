package ru.internship.oAuth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.oAuth.exceptions.JWTSecurityException;
import ru.internship.oAuth.model.GoogleAccessObject;
import ru.internship.oAuth.model.GoogleUserObject;
import ru.internship.oAuth.model.JWTDecodedPayload;
import ru.internship.oAuth.model.User;
import ru.internship.oAuth.util.GoogleAPIQueryBuilder;
import ru.internship.oAuth.util.JWTDecoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Service
public class RegistrationService {

    private final GoogleAPIQueryBuilder googleAPIQueryBuilder;

    private final GoogleApiService googleApiService;

    private final SecurityService securityService;

    private final UserService userService;

    @Autowired
    public RegistrationService(GoogleAPIQueryBuilder googleAPIQueryBuilder,
                          GoogleApiService googleApiService, UserService userService,
                          SecurityService securityService) {
        this.googleAPIQueryBuilder = googleAPIQueryBuilder;
        this.googleApiService = googleApiService;
        this.securityService = securityService;
        this.userService = userService;
    }

    public boolean GoogleSignIn(String code, HttpServletResponse response) throws IOException, JWTSecurityException, Exception {
        Map<String, String> body = googleAPIQueryBuilder.buildClientJWTQuery(code);
            GoogleAccessObject googleAccessObject = googleApiService.getGoogleAccessObject(body);

            if (!securityService.verifyIdToken(googleAccessObject.getId_token()))
                throw new JWTSecurityException("Token seems to be fake!");

            GoogleUserObject googleUserObject = googleApiService.getGoogleUserInfo(
                    googleAPIQueryBuilder.buildClientInfoQuery(googleAccessObject.getAccess_token()));

            if (userService.isNewGoogleUser(googleUserObject.getId())) {
                userService.addNewGoogleUser(googleAccessObject, googleUserObject);
            }

            securityService.saveTokenInHTTPOnlyCookies(
                    googleAccessObject.getAccess_token(),
                    googleAccessObject.getExpires_in(),
                    response
            );
            return true;
    }
}
