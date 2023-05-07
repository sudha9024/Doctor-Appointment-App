package com.example.doctor.PatientHomePagePack;

public class FeedbackModel {
    private String P_name;
    private Integer ratings;
    private String text;
    public FeedbackModel() {

    }
    public FeedbackModel(String P_name,Integer ratings,String text){
        this.P_name=P_name;
        this.ratings=ratings;
        this.text=text;


    }



    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public Integer getRatings(int i) {
        return ratings;
    }

    public void setRatings(Integer ratings) {
        this.ratings = ratings;
    }

    public String getText(String hi_doctor) {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
