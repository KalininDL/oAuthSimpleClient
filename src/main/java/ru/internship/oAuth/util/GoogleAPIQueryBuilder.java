package ru.internship.oAuth.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@PropertySource(ignoreResourceNotFound=true,value="classpath:secrets.properties")
public class GoogleAPIQueryBuilder {

    private Map<String, String> tokenQueryParams;

    @Value("${googleApi.secret}")
    private String SECRET;

    @Value("${googleApi.client_id}")
    private String CLIENT_ID;

    @Value("${googleApi.access_type}")
    private String ACCESS_TYPE;

    @Value("${googleApi.scope}")
    private String SCOPE;

    @Value("${googleApi.redirect_uri}")
    private String REDIRECT_URI;

    @Value("${googleApi.state}")
    private String STATE;

    @Value(("${googleApi.token_redirect_uri}"))
    private String TOKEN_REDIRECT_URI;

    @Value("${googleApi.response_type}")
    private String RESPONSE_TYPE;

    @Value("${googleApi.include_granted_scopes}")
    private String INCLUDE_GRANTED_SCOPES;

    @PostConstruct
    public void initMap() {
        tokenQueryParams = new LinkedHashMap<>(){{
            put("code", "");
            put("client_id", CLIENT_ID);
            put("client_secret", SECRET);
            put("redirect_uri", REDIRECT_URI);
            put("grant_type", "authorization_code");
        }};
    }


    public Map<String, String> buildClientJWTQuery(String code){
        tokenQueryParams.replace("code", code);
        return tokenQueryParams;
    }

    public String buildClientInfoQuery(String ACCESS_TOKEN){
        return "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" +
                ACCESS_TOKEN;
    }

    public String buildClientIdRedirectQuery(){
        return "redirect:https://accounts.google.com/o/oauth2/v2/auth?" +
                "scope=" + SCOPE + "&" +
                "access_type=" + ACCESS_TYPE + "&" +
                "include_granted_scopes=" + INCLUDE_GRANTED_SCOPES + "&" +
                "response_type=" + RESPONSE_TYPE + "&" +
                "state=" + STATE + "&" +
                "redirect_uri=" + REDIRECT_URI + "&" +
                "client_id=" + CLIENT_ID;
    }

    public String buildGooglePublicKeysQuery(){
        return "https://www.googleapis.com/oauth2/v3/certs";
    }

    public String buildVerifyGoogleAccessKey(String accessToken){
        return "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + accessToken;
    }

    public String buildRefreshGoogleAccessKey(String accessToken){
        return "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + accessToken;
    }


}
