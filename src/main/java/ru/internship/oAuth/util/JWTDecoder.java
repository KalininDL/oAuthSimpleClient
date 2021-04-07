package ru.internship.oAuth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.internship.oAuth.model.JWTDecodedPayload;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class JWTDecoder {

    JSONToObjectMapper jsonToObjectMapper;

    @Autowired
    public JWTDecoder(JSONToObjectMapper jsonToObjectMapper) {
        this.jsonToObjectMapper = jsonToObjectMapper;
    }

    public JWTDecodedPayload decode(String token) throws IOException {
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String jsonPayload = new String(decoder.decode(chunks[1]));
        return jsonToObjectMapper.jsonToJWTDecodedPayload(jsonPayload);
    }
}
