package ru.internship.oAuth.util;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.internship.oAuth.model.GoogleAccessObject;
import ru.internship.oAuth.model.GooglePublicKey;
import ru.internship.oAuth.model.GoogleUserObject;
import ru.internship.oAuth.model.JWTDecodedPayload;

import java.io.IOException;

@Component
public class JSONToObjectMapper {

    Gson gson;

    @Autowired
    public JSONToObjectMapper(Gson gson) {
        this.gson = gson;
    }


    public GoogleAccessObject jsonToGoogleAccessObject(String json) throws IOException {
        GoogleAccessObject googleAccessObject = gson.fromJson(json, GoogleAccessObject.class);
        return googleAccessObject;
    }

    public GoogleUserObject jsonToGoogleUser(String json) throws IOException {
        GoogleUserObject googleUserObject = gson.fromJson(json, GoogleUserObject.class);
        return googleUserObject;
    }

    public GooglePublicKey[] jsonToGooglePublicKeys(String json) throws IOException {
        GooglePublicKey[] keys = gson.fromJson(json, GooglePublicKey[].class);
        return keys;
    }

    public JWTDecodedPayload jsonToJWTDecodedPayload(String json) throws IOException {
        JWTDecodedPayload jwtDecodedPayload = gson.fromJson(json, JWTDecodedPayload.class);
        return jwtDecodedPayload;
    }


}
