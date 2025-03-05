package com.project.tlustudents;

public class Student {
    private  String sid;
    private String fullname;
    private int avatar;

    public Student(String sid, String fullname, int avatar) {
        this.sid = sid;
        this.fullname = fullname;
        this.avatar = avatar;
    }
    public Student(String sid) {
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
