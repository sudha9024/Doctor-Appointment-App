package com.example.doctor.DoctorHomePagePack;

public class MyFeedbackModel {

    private String PatientName;
    private  String FeedText;
    private Double rate;
    public MyFeedbackModel(String PatientName,String FeedText,Double rate){
        this.PatientName=PatientName;
        this.FeedText=FeedText;
        this.rate=rate;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getFeedText() {
        return FeedText;
    }

    public void setFeedText(String feedText) {
        FeedText = feedText;
    }
}
