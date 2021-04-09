package ru.internship.oAuth.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.oAuth.model.GoogleAccessObject;
import ru.internship.oAuth.model.GoogleUserObject;
import ru.internship.oAuth.model.JWTDecodedPayload;
import ru.internship.oAuth.util.HTTPRequestProvider;
import ru.internship.oAuth.util.JSONToObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@Service
public class GoogleApiService {

    HTTPRequestProvider requestProvider;
    JSONToObjectMapper jsonParser;

    @Autowired
    public GoogleApiService(HTTPRequestProvider postRequestProvider, JSONToObjectMapper jsonParser) {
        this.requestProvider = postRequestProvider;
        this.jsonParser = jsonParser;
    }

    public GoogleAccessObject getGoogleAccessObject(Map<String, String> body) throws Exception {
        String uri = "https://accounts.google.com/o/oauth2/token";
        HttpResponse<String> response = requestProvider.sendPostRequest(uri, body);
        if (response.statusCode() == 400) throw new Exception("Google returned 400");

        try {
            return jsonParser.jsonToGoogleAccessObject(response.body());
        } catch (JsonProcessingException e) {
            throw new Exception("JSON Parsing error!");
        }
    }

    public GoogleUserObject getGoogleUserInfo(String uri) throws Exception {
        HttpResponse<String> response = requestProvider.sendGetRequest(uri);
        if (response.statusCode() == 400) throw new Exception("Google api returned error");
        return jsonParser.jsonToGoogleUser(response.body());
    }

    public JWTDecodedPayload verifyGoogleAccessToken(String token) {
        String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
        try {
            HttpResponse<String> response = requestProvider.sendGetRequest(uri);
            if (response.statusCode() == 200) {
                return jsonParser.jsonToJWTDecodedPayload(response.body());
            } else return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Boolean verifyIdToken(String token) {
        String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + token;
        HttpResponse<String> response = requestProvider.sendGetRequest(uri);
        return response.statusCode() == 200;
    }

}
