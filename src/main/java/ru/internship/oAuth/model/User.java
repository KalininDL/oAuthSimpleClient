package ru.internship.oAuth.model;

import javax.persistence.*;

@Entity
@Table(name = "OAUTH_USERS")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "google_id")
    private String google_id;

    @Column(name = "email")
    private String email;

    @Column(name = "refresh_token")
    private String refresh_token;

    public User(){
    }


    public User(String name, String email, String refresh_token) {
        this.name = name;
        this.email = email;
        this.refresh_token = refresh_token;
    }

    public User(String google_id, String name, String email, String refresh_token) {
        this.google_id = google_id;
        this.name = name;
        this.email = email;
        this.refresh_token = refresh_token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    @Override
    public String toString(){
        return "User: id=" + id + " name=" + name + " email=" + email + "refresh_token=" + refresh_token;
    }
}
