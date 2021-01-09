package com.mackvel.soma.Model;

public class Teachers {

    private String id;
    private String schoolcode;
    private String schoolname;
    private String username;

    public Teachers() {
    }

    public Teachers(String id, String schoolcode, String schoolname, String username) {
        this.id = id;
        this.schoolcode = schoolcode;
        this.schoolname = schoolname;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getSchoolcode() {
        return schoolcode;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getUsername() {
        return username;
    }
}
