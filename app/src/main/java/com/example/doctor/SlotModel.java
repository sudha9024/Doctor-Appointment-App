package com.example.doctor;

public class SlotModel {
    private int numberOfPatients;
    private String time;
    public SlotModel(){

    }
    public SlotModel(int numberOfPatients, String time) {
        this.numberOfPatients = numberOfPatients;
        this.time = time;
    }

    public int getNumberOfPatients() {
        return numberOfPatients;
    }

    public void setNumberOfPatients(int numberOfPatients) {
        this.numberOfPatients = numberOfPatients;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
