package com.example.test;

public class resturant {
    private String resturant_name;
    private String resturant_tag;

    public resturant() {
    }

    public resturant(String resturant_name, String resturant_tag) {
        this.resturant_name = resturant_name;
        this.resturant_tag = resturant_tag;
    }

    public String getResturant_name() {
        return resturant_name;
    }

    public void setResturant_name(String resturant_name) {
        this.resturant_name = resturant_name;
    }

    public String getResturant_tag() {
        return resturant_tag;
    }

    public void setResturant_tag(String resturant_tag) {
        this.resturant_tag = resturant_tag;
    }
}
