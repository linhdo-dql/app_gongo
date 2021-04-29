package com.example.app_go_go.Objects;

public class Friends {
    public String acc_friends, name_friends, fl_friends, acc_name;

    public Friends() {
    }

    public Friends(String acc_friends, String name_friends, String fl_friends, String acc_name) {
        this.acc_friends = acc_friends;
        this.name_friends = name_friends;
        this.fl_friends = fl_friends;
        this.acc_name = acc_name;
    }

    public String getAcc_friends() {
        return acc_friends;
    }

    public void setAcc_friends(String acc_friends) {
        this.acc_friends = acc_friends;
    }

    public String getName_friends() {
        return name_friends;
    }

    public void setName_friends(String name_friends) {
        this.name_friends = name_friends;
    }

    public String getFl_friends() {
        return fl_friends;
    }

    public void setFl_friends(String fl_friends) {
        this.fl_friends = fl_friends;
    }

    public String getAcc_name() {
        return acc_name;
    }

    public void setAcc_name(String acc_name) {
        this.acc_name = acc_name;
    }
}
