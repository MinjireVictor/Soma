package com.mackvel.soma.Model;

public class Students {
    private String grade;
    private String id;
    private String schoolname;
    private String username;

    public Students() {
    }

    public Students(String grade, String id, String schoolname, String username) {
        this.grade = grade;
        this.id = id;
        this.schoolname = schoolname;
        this.username = username;
    }

    public String getGrade() {
        return grade;
    }

    public String getId() {
        return id;
    }

    public String getSchoolname() {
        return schoolname;
    }

    public String getUsername() {
        return username;
    }
}
