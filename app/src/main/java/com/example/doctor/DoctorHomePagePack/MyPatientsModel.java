package com.example.doctor.DoctorHomePagePack;

public class MyPatientsModel {
  private String patientName;
  private String patientPhone;
  private String patientImage;

    public MyPatientsModel(String patientName, String patientPhone, String patientImage) {
        this.patientName = patientName;
        this.patientPhone = patientPhone;
        this.patientImage = patientImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }
}
