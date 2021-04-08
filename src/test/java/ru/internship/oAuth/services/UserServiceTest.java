package ru.internship.oAuth.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.internship.oAuth.model.User;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    @BeforeAll
    public void addUsers(){
        userService.addNewUser(new User("Ivan", "ivan@email.com", "dffd"));
        userService.addNewUser(new User("Petr", "petr@email.com", "dffd"));
        userService.addNewUser(new User("Sergey", "sergey@email.com", "dffd"));
        User user = new User(
                "118444746558514495912",
                "Ivan",
                null,
                "12424903493208314"
        );
        userService.addNewUser(user);
    }

    @Autowired
    UserService userService;

    @Test
    public void getAllUser(){
        assertNotNull(userService.getAllUsers());
    }


    @Test
    public void getUserById(){
        assertNotNull(userService.findByID(1L));
    }

    @Test
    public void getUserByGoogleId(){
        assertEquals(userService.findByGoogleID("118444746558514495912").getRefresh_token(), "12424903493208314");
    }


}