package com.example.doctor;

public class AppointmentListModel {
    private String patientId;
    private String doctorId;
    private String slotTimeSelected;

    public String getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(String dateSelected) {
        this.dateSelected = dateSelected;
    }

    private String dateSelected;

    public AppointmentListModel(){}

    public AppointmentListModel(String patientId, String doctorId, String slotTimeSelected, String dateSelected) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.slotTimeSelected = slotTimeSelected;
        this.dateSelected = dateSelected;

    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSlotTimeSelected() {
        return slotTimeSelected;
    }

    public void setSlotTimeSelected(String slotTimeSelected) {
        this.slotTimeSelected = slotTimeSelected;
    }


}
