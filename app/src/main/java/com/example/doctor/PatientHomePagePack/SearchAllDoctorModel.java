package com.example.doctor.PatientHomePagePack;

public class SearchAllDoctorModel {

    private String doctorName;
    private String doctorPhone;
    private String doctorImage;
    private String doctorId;

    public SearchAllDoctorModel(String doctorName, String doctorPhone, String doctorImage,String doctorId) {
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.doctorImage = doctorImage;
        this.doctorId=doctorId;
    }
    public String getDoctorName() {
        return doctorName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }
}


