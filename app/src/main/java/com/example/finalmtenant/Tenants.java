package com.example.finalmtenant;

public class Tenants {

    String name;
    String username;
    String email;
    String pNo;
    String apartmentNo;
    String rent;
    String password;

    public Tenants() {
    }

    public Tenants( String name, String username, String email, String pNo, String apartmentNo, String rent, String password) {

        this.name = name;
        this.username = username;
        this.email = email;
        this.pNo = pNo;
        this.apartmentNo = apartmentNo;
        this.rent = rent;
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpNo() {
        return pNo;
    }

    public void setpNo(String pNo) {
        this.pNo = pNo;
    }

    public String getApartmentNo() {
        return apartmentNo;
    }

    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
