package ru.internship.oAuth.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.internship.oAuth.model.GoogleAccessObject;
import ru.internship.oAuth.model.GooglePublicKey;
import ru.internship.oAuth.model.GoogleUserObject;
import ru.internship.oAuth.model.JWTDecodedPayload;
import ru.internship.oAuth.util.JSONToObjectMapper;
import ru.internship.oAuth.util.HTTPRequestProvider;


import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.net.http.HttpResponse;

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
        if (response.statusCode() == 400) throw new Exception("Error!");

        try {
            GoogleAccessObject googleAccessObject = jsonParser.jsonToGoogleAccessObject(response.body());
            return googleAccessObject;
        }
        catch (JsonProcessingException e){
            throw new Exception("Error!");
        }
    }

    public GoogleUserObject getGoogleUserInfo(String uri) throws IOException, Exception {
        HttpResponse<String> response = requestProvider.sendGetRequest(uri);
        if (response.statusCode() == 400) throw new Exception("Google api returned error");
            return jsonParser.jsonToGoogleUser(response.body());
    }

    public JWTDecodedPayload verifyGoogleAccessToken(String token) {
        String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token="+token;
        System.out.println("In user info");
        try {
            HttpResponse<String> response = requestProvider.sendGetRequest(uri);
            if (response.statusCode() == 200) {
                JWTDecodedPayload payload = jsonParser.jsonToJWTDecodedPayload(response.body());
                return payload;
            }
            else return null;
        }
        catch (IOException e)
        {
            return null;
        }
    }

    public Boolean verifyIdToken(String token){
        String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token="+token;
        System.out.println(uri);
        HttpResponse<String> response = requestProvider.sendGetRequest(uri);
        return response.statusCode() == 200;
    }

    public List<GooglePublicKey> getGooglePublicKeys(String uri) throws Exception {
        System.out.println("In user info");
        List<GooglePublicKey> keys = new ArrayList<>();
        HttpResponse<String> response = requestProvider.sendGetRequest(uri);
        if (response.statusCode() == 400) throw new Exception("Error!");
        try {
            System.out.println("not 400");
            GooglePublicKey[] keysArray = jsonParser.jsonToGooglePublicKeys(response.body());
            if(keysArray.length != 0){
                Collections.addAll(keys, keysArray);
            }
            System.out.println(keys.get(0).getKid());
            return keys;
        }
        catch (JsonProcessingException e){
            throw new Exception("Error!");
        }
    }









}
