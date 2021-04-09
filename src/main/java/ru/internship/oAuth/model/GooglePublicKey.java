package ru.internship.oAuth.model;


public class GooglePublicKey {

    String kid;
    String n;

    public GooglePublicKey(String kid, String n) {
        this.kid = kid;
        this.n = n;
    }

    public String getKid() {
        return kid;
    }

    public void setKid(String kid) {
        this.kid = kid;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }
}
