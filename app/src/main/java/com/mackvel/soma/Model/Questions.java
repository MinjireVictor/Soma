package com.mackvel.soma.Model;

public class Questions {
    private String correctans;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String image_URL;
    private String question;

    public Questions() {
    }

    public Questions(String correctans, String optionA, String optionB, String optionC, String optionD, String image_URL, String question) {
        this.correctans = correctans;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.image_URL = image_URL;
        this.question = question;
    }

    public String getImage_URL() {
        return image_URL;
    }
}
