package com.mukusuzuki.kansjatimdatabase;

/**
 * Created by Muku Suzuki on 8/3/2017.
 */

public class Search {
    private String uname, phone, university;

    public Search() {
    }

    public Search(String uname, String phone, String university) {
        this.uname = uname;
        this.phone = phone;
        this.university = university;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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
