package com.mackvel.soma.Model;

public class StudentResults {

    public String student_name;
    public String marks;

    public StudentResults(String student_name, String marks) {
        this.student_name = student_name;
        this.marks = marks;
    }

    public String getStudent_name() {
        return student_name;
    }

    public String getMarks() {
        return marks;
    }
}
