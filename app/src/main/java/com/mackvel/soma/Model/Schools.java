package com.mackvel.soma.Model;

public class Schools {

    private String schoolname;
    private String schoolcode;

    public Schools() {
    }

    public Schools(String schoolname, String schoolcode) {
        this.schoolname = schoolname;
        this.schoolcode = schoolcode;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getSchoolcode() {
        return schoolcode;
    }
}

