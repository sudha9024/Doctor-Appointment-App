package com.example.doctor.DoctorHomePagePack;

public class AppointmentScheduleModel {
  private String patientName;
  private String patientDate;
  private String patientTime,patientPhone;
  private String patientImage;

    public AppointmentScheduleModel(String patientName, String patientDate,String patientTime,String patientPhone, String patientImage) {
        this.patientName = patientName;
        this.patientDate = patientDate;
        this.patientTime=patientTime;
        this.patientPhone=patientPhone;
        this.patientImage = patientImage;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientDate() {
        return patientDate;
    }

    public void setPatientDate(String patientDate) {
        this.patientDate = patientDate;
    }

    public String getPatientTime() {
        return patientTime;
    }

    public void setPatientTime(String patientTime) {
        this.patientTime = patientTime;
    }

    public String getPatientImage() {
        return patientImage;
    }

    public void setPatientImage(String patientImage) {
        this.patientImage = patientImage;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }
}
