package com.mukusuzuki.kansjatimdatabase;

/**
 * Created by Muku Suzuki on 7/31/2017.
 */

    public class User {

    private String user, uid, nickname, name, password, phone, university;

    public User() {
    }

    public User(String user, String uid, String nickname, String name, String password, String phone, String university) {
        this.user = user;
        this.uid = uid;
        this.nickname = nickname;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.university = university;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
