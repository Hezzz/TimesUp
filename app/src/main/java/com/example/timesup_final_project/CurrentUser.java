package com.example.timesup_final_project;

public class CurrentUser {

    private static String userName, firstName, lastName, eMail, contact_no;


    public static void setUser(String firstname, String lastname,
                               String email, String contactNo){
        firstName = firstname;
        lastName = lastname;
        eMail = email;
        contact_no = contactNo;
    }
    public static void setUserName(String userName) { CurrentUser.userName = userName; }
    public static String getUserName() { return userName; }
    public static String getFirstname(){ return firstName; }
    public static String getLastname(){ return lastName; }
    public static String getEmail(){ return eMail; }
    public static String getContactNo(){ return contact_no; }
}
