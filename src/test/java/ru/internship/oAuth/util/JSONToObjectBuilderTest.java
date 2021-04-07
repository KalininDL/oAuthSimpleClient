package ru.internship.oAuth.util;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.internship.oAuth.model.GoogleAccessObject;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JSONToObjectBuilderTest {

    @Autowired
    JSONToObjectMapper jsonToObjectBuilder;

    String json = "  {\n" +
            "  \"access_token\": \"ya29.a0AfH6SMDqhGubsMJmnXhidE2qFCEl64-TapYtcnD2Pob8GiqRmfLDFPZtt8MUX3lxgAcnctp4CKsuk1pjWJSjS-yqbK1tGK8OEF4BBVZkliZ7slfs_dZFs0EP3xifjVOm_XazNex2Xt2JdJOuCrw6IomTWHSf\",\n" +
            "  \"expires_in\": 3599,\n" +
            "  \"refresh_token\": \"1//0c4EgXNXV3pMBCgYIARAAGAwSNwF-L9Irr0a_4Ylxglje3beKdG1W9V_ZUzDiakLzIHwj8l6GOkzDBjATDw6JlROJsJkUrkB0wL4\",\n" +
            "  \"scope\": \"https://www.googleapis.com/auth/userinfo.profile\",\n" +
            "  \"token_type\": \"Bearer\",\n" +
            "  \"id_token\": \"eyJhbGciOiJSUzI1NiIsImtpZCI6ImUxYWMzOWI2Y2NlZGEzM2NjOGNhNDNlOWNiYzE0ZjY2ZmFiODVhNGMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiMjIyOTkzMjk0Mjk2LXU0M3V1Zm1ycmFzYXNqbTdjMjRyMzRodWM2MGRyMWYyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiMjIyOTkzMjk0Mjk2LXU0M3V1Zm1ycmFzYXNqbTdjMjRyMzRodWM2MGRyMWYyLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTE4NDQ0NzQ2NTU4NTE0NDk1OTEyIiwiYXRfaGFzaCI6IjhDQXRSaEUzdldObEE3UW9xQWJkRmciLCJpYXQiOjE2MTc2MzAxMzMsImV4cCI6MTYxNzYzMzczM30.bGCorlIXfZnPv2S6QhUYXJnJYFIaHZC4mQ6uYqUHC1WKHRlvMw4LLA2cLiX1li6vb6Fgs3TMHkRnVv5KgUtq82u4F20m9pYB8MzvezaF7OceTywmTe0AnzzCs0z-RmGjBAFquhtOXmbj6vNbP63EYiwsvaGDQwzGlOdv8BCBU2xQx7fjnaH8Jfup09dA1Sx3QmSdzHMZPf38qKJTA2jdD1qcfLAnhsMINL1hW_k7QGyMjViPZgaPM82e0fJAZu9nSwEg51e4WDYnPF0Wcz9INgTALUbs1_hsJU9NaKVYTTMumx9SQhpwzchgCufdDOCg5iicEfMP6gGHzG0d9MFN3w\"\n" +
            "}";

    @Test
    public void JSONToGoogleAccessObjectMapper() throws IOException {
        GoogleAccessObject gobj = jsonToObjectBuilder.jsonToGoogleAccessObject(json);
        System.out.println(gobj);
    }
}