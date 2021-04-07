package ru.internship.oAuth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.internship.oAuth.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT u FROM User u WHERE u.google_id = :google_id")
    User findByGoogleID(@Param("google_id") String google_id);

    Optional<User> findById(Long id);
}
