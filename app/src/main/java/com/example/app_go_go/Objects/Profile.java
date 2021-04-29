package com.example.app_go_go.Objects;

public class Profile {
    public String name_user,user_avatar_image,user_cover_image,user_job,user_school,user_address,user_country,user_birth,user_relation,accName;

    public Profile() {
    }

    public Profile(String name_user, String user_avatar_image, String user_cover_image, String user_job, String user_school, String user_address, String user_country, String user_birth, String user_relation, String accName) {
        this.name_user = name_user;
        this.user_avatar_image = user_avatar_image;
        this.user_cover_image = user_cover_image;
        this.user_job = user_job;
        this.user_school = user_school;
        this.user_address = user_address;
        this.user_country = user_country;
        this.user_birth = user_birth;
        this.user_relation = user_relation;
        this.accName = accName;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name_user='" + name_user + '\'' +
                ", user_avatar_image='" + user_avatar_image + '\'' +
                ", user_cover_image='" + user_cover_image + '\'' +
                ", user_job='" + user_job + '\'' +
                ", user_school='" + user_school + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_country='" + user_country + '\'' +
                ", user_birth='" + user_birth + '\'' +
                ", user_relation='" + user_relation + '\'' +
                ", accName='" + accName + '\'' +
                '}';
    }

    public String getName_user() {
        return name_user;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public String getUser_avatar_image() {
        return user_avatar_image;
    }

    public void setUser_avatar_image(String user_avatar_image) {
        this.user_avatar_image = user_avatar_image;
    }

    public String getUser_cover_image() {
        return user_cover_image;
    }

    public void setUser_cover_image(String user_cover_image) {
        this.user_cover_image = user_cover_image;
    }

    public String getUser_job() {
        return user_job;
    }

    public void setUser_job(String user_job) {
        this.user_job = user_job;
    }

    public String getUser_school() {
        return user_school;
    }

    public void setUser_school(String user_school) {
        this.user_school = user_school;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_country() {
        return user_country;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public String getUser_relation() {
        return user_relation;
    }

    public void setUser_relation(String user_relation) {
        this.user_relation = user_relation;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }
}
