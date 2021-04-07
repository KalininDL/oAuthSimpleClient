package ru.internship.oAuth.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GoogleAPIQueryBuilderTest {

    @Autowired
    private GoogleAPIQueryBuilder googleAPIQueryBuilder;

    @Test
    public void buildClientIdRedirectQuery() {
        Assert.assertEquals(googleAPIQueryBuilder.buildClientIdRedirectQuery(), "redirect:https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.profile&access_type=offline&include_granted_scopes=true&response_type=code&state=state_parameter_passthrough_value&redirect_uri=http://localhost:8080/auth/redirect&client_id=222993294296-u43uufmrrasasjm7c24r34huc60dr1f2.apps.googleusercontent.com");
    }
}