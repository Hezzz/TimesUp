package com.example.timesup_final_project;

public class UserClass{

    private String username, password,
        firstname, lastname, email, contactNo;

    public UserClass(String username, String password,
                     String firstname, String lastname,
                     String email, String contactNo){
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.contactNo = contactNo;
    }
    public String getUsername(){ return this.username; }
    public String getPassword(){ return this.password; }
    public String getFirstname(){ return this.firstname; }
    public String getLastname(){ return this.lastname; }
    public String getEmail(){ return this.email; }
    public String getContactNo(){ return this.contactNo; }
}
