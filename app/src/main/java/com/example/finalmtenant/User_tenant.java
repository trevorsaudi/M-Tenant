package com.example.finalmtenant;

public class User_tenant {

    private String email;
    private String password;
    private String fullName;
    private String phoneNo;
    private String as;
    private String username;

    public User_tenant() {
    }

    public User_tenant(String email, String password){
        this.email = email;
        this.password = password;
    }

    public User_tenant(String email, String password, String fullName, String phoneNo, String username) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNo = phoneNo;
        this.username = username;
//        this.as =as;
    }

    public String getAs() {
        return as;
    }

    public void setAs(String as) {
        this.as = as;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getphoneNo() {
        return phoneNo;
    }

    public void setphoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
