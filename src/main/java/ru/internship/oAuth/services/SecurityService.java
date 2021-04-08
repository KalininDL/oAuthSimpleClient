package ru.internship.oAuth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.oAuth.exceptions.JWTSecurityException;
import ru.internship.oAuth.model.JWTDecodedPayload;
import ru.internship.oAuth.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class SecurityService {

    GoogleApiService googleApiService;
    UserService userService;

    @Autowired
    public SecurityService(GoogleApiService googleApiService, UserService userService) {
        this.googleApiService = googleApiService;
        this.userService = userService;
    }


    public void saveTokenInHTTPOnlyCookies(String token, int maxAge, HttpServletResponse response) {
        Cookie jwtTokenCookie = new Cookie("user-id", token);
        jwtTokenCookie.setMaxAge(maxAge);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");
        response.addCookie(jwtTokenCookie);
    }

    public void DeleteCoockies(HttpServletResponse response) {
        Cookie emptyCookie = new Cookie("user-id", null);
        emptyCookie.setMaxAge(0);
        emptyCookie.setHttpOnly(true);
        emptyCookie.setPath("/");
        response.addCookie(emptyCookie);
    }

    public Boolean isSignedIn(HttpServletRequest request) {
        String jwt = readServletCookie(request, "user-id");
        return jwt != null;
    }

    public User getUserAuthentication(HttpServletRequest request) throws JWTSecurityException {
        JWTDecodedPayload payload = verifyAccessToken(getToken(request));
        User user = userService.findByGoogleID(payload.getSub());
        if (user != null)
            return user;
        else throw new JWTSecurityException("User has valid access token but was not previously registered!");
    }

    public Boolean verifyIdToken(String token) {
        return googleApiService.verifyIdToken(token);
    }

    public JWTDecodedPayload verifyAccessToken(String token) {
        return googleApiService.verifyGoogleAccessToken(token);
    }

    public String getToken(HttpServletRequest request) {
        return readServletCookie(request, "user-id");
    }

    private String readServletCookie(HttpServletRequest request, String name) {
        try {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))
                    .map(Cookie::getValue)
                    .findAny().orElse(null);
        } catch (NullPointerException e) {
            return null;
        }
    }

}
