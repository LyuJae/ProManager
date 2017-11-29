package com.example.ryu.promanager;

import android.widget.Button;

import java.util.Date;

/**
 * Created by YM on 2017-11-07.
 */
//
public class teammate_item {
    private int profile_image;
    private String usr_name;
    private String phone;
    private String email;


    public teammate_item(int profile_image, String usr_name, String phone, String email) {
        this.profile_image = profile_image;
        this.phone = phone;
        this.email = email;
        this.usr_name = usr_name;
    }

    public int getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(int profile_image) {
        this.profile_image = profile_image;
    }

    public String getName() {
        return usr_name;
    }

    public void setName(String usr_name) {
        this.usr_name = usr_name;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }
}

