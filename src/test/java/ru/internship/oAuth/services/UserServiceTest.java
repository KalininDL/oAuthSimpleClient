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
                "Марина Королева",
                null,
                "1//0c4EgXNXV3pMBCgYIARAAGAwSNwF-L9Irr0a_4Ylxglje3beKdG1W9V_ZUzDiakLzIHwj8l6GOkzDBjATDw6JlROJsJkUrkB0wL4"
        );
        userService.addNewUser(user);
    }

    @Autowired
    UserService userService;

    @Test
    public void getAllUser(){
        userService.getAllUsers();
    }


    @Test
    public void getUserById(){
        userService.findByID(1L);
    }

    @Test
    public void getUserByGoogleId(){
        userService.findByGoogleID("118444746558514495912");
        userService.getAllUsers();
    }


}