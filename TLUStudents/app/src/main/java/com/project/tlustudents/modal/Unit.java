package com.project.tlustudents.modal;

public class Unit {
    private String uid;
    private String name;
    private String address;
    private String logoURL;
    private String phone;
    private String email;
//    private String fax;
    private String type; // Loại đơn vị: "Khoa", "Phòng", "Trung tâm"...

    public Unit(String uid, String name, String address, String logoURL, String phone, String email, String type) {
        this.uid = uid;
        this.name = name;
        this.address = address;
        this.logoURL = logoURL;
        this.phone = phone;
        this.email = email;
        this.type = type;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
