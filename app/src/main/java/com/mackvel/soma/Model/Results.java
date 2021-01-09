package com.mackvel.soma.Model;

public class Results {

    private String maths;
    private String english;
    private String kiswahili;
    private String science;
    private String socialstudies;
    private String cre;

    public Results() {
    }

    public Results(String maths, String english, String kiswahili, String science, String socialstudies, String cre) {
        this.maths = maths;
        this.english = english;
        this.kiswahili = kiswahili;
        this.science = science;
        this.socialstudies = socialstudies;
        this.cre = cre;
    }

    public String getMaths() {
        return maths;
    }

    public String getEnglish() {
        return english;
    }

    public String getKiswahili() {
        return kiswahili;
    }

    public String getScience() {
        return science;
    }

    public String getSocialstudies() {
        return socialstudies;
    }

    public String getCre() {
        return cre;
    }
}
