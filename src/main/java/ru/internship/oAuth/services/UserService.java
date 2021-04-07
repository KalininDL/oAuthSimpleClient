package ru.internship.oAuth.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.internship.oAuth.model.GoogleAccessObject;
import ru.internship.oAuth.model.GoogleUserObject;
import ru.internship.oAuth.model.User;
import ru.internship.oAuth.repository.UserRepository;

import java.util.List;

@Service
@Slf4j
public class UserService {


    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addNewUser(User user){
        User authedUser = userRepository.save(user);
        System.out.println(authedUser.toString());
        return authedUser;
    }

    public void addNewGoogleUser(GoogleAccessObject googleAccessObject, GoogleUserObject googleUserObject){
        User user = new User(
                googleUserObject.getId(),
                googleUserObject.getName(),
                googleUserObject.getEmail(),
                googleAccessObject.getRefresh_token()
        );
        userRepository.save(user);
    }

    public List<User> getAllUsers(){
        List<User> allUsers = userRepository.findAll();
        allUsers.forEach(user ->
            System.out.println(user.toString()));
        return allUsers;
    }

    public User findByID(Long id){
        User user = userRepository.findById(id).orElse(null);
        System.out.println(user);
        return user;
    }

    public Boolean isNewGoogleUser(String google_id){
        return findByGoogleID(google_id) == null;
    }

    public User findByGoogleID(String google_id){
        User user = userRepository.findByGoogleID(google_id);
        return user;
    }


}
