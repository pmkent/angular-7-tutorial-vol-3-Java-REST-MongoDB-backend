package com.pmk.app.model;

import java.util.Date;

public class User extends DAOBean {
    private int userId;
    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String token;
    private String showpassword;
    private Date dateOfBirth;
    private String gender;

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public String getShowpassword() {
        return showpassword;
    }
    public void setShowpassword(String showpassword) {
        this.showpassword = showpassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString() {
        return "User [\nid="+getId()+
                ", userId="+userId+
                ", username="+username+
                ", firstName="+firstName+
                ", middleName="+middleName+
                ", lastName="+lastName+
                ", password="+password+
                ", token="+token+
                ", showpassword="+showpassword+
                ", dateOfBirth="+dateOfBirth+
                ", gender="+gender+
                "]\n";
    }
}
