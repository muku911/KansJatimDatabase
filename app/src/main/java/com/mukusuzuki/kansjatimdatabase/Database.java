package com.mukusuzuki.kansjatimdatabase;

/**
 * Created by Muku Suzuki on 7/31/2017.
 */

public class Database {

    private String uid, name, nickname, birthday, phone, email, school, address, motto, university,
                    parrentname, parrentphone, parrentaddress, lineinfo, instagtaminfo, user;

    public Database() {
    }

    public Database(String uid, String name, String nickname, String birthday, String phone, String email, String school, String address, String motto, String university, String parrentname, String parrentphone, String parrentaddress, String lineinfo, String instagtaminfo, String user) {
        this.uid = uid;
        this.name = name;
        this.nickname = nickname;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.school = school;
        this.address = address;
        this.motto = motto;
        this.university = university;
        this.parrentname = parrentname;
        this.parrentphone = parrentphone;
        this.parrentaddress = parrentaddress;
        this.lineinfo = lineinfo;
        this.instagtaminfo = instagtaminfo;
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getParrentname() {
        return parrentname;
    }

    public void setParrentname(String parrentname) {
        this.parrentname = parrentname;
    }

    public String getParrentphone() {
        return parrentphone;
    }

    public void setParrentphone(String parrentphone) {
        this.parrentphone = parrentphone;
    }

    public String getParrentaddress() {
        return parrentaddress;
    }

    public void setParrentaddress(String parrentaddress) {
        this.parrentaddress = parrentaddress;
    }

    public String getLineinfo() {
        return lineinfo;
    }

    public void setLineinfo(String lineinfo) {
        this.lineinfo = lineinfo;
    }

    public String getInstagtaminfo() {
        return instagtaminfo;
    }

    public void setInstagtaminfo(String instagtaminfo) {
        this.instagtaminfo = instagtaminfo;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
