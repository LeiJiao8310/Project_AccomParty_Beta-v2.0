package com.example.project_accomparty;

public class Users {
    public String username,email,phoneNum,cityName,status,uid;

    public Users(){

    }

    public Users(String email, String status){
        this.username = "username";
        this.email = email;
        this.phoneNum = "911";
        this.cityName = "city";
        this.status = status;
    }

   public Users (String username, String email, String phoneNum, String cityName, String uid){
        this.username = username;
        this.email = email;
        this.phoneNum=phoneNum;
        this.cityName=cityName;
        this.status = "Online";
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phoneNum;
    }

    public String getCity() {
        return cityName;
    }

    public String getStatus() {
        return status;
    }

    public String getUid(){return uid;}

    public void setStatus() {
        if (status.equals("Online"))
            status = "Offline";
        else
            status = "Online";
    }
}
