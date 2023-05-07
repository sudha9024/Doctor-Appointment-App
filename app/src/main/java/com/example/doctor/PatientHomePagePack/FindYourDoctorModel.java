package com.example.doctor.PatientHomePagePack;

public class FindYourDoctorModel {
    private String doctorId;
  private String doctorName;
  private String doctorPhone;
  private String doctorImage;

    public FindYourDoctorModel(String doctorId, String doctorName, String doctorPhone, String doctorImage) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.doctorPhone = doctorPhone;
        this.doctorImage = doctorImage;
    }

    public String getDoctorName() {
        return doctorName;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
