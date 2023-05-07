package com.example.doctor.PatientHomePagePack;

import java.time.LocalDate;

public class SlotModelDate {
    private LocalDate slotDate;
    private String doctorId;

    public SlotModelDate(LocalDate slotDate, String doctorId) {
        this.slotDate = slotDate;

        this.doctorId = doctorId;
    }

    public LocalDate getSlotDate() {
        return slotDate;
    }

    public void setSlotDate(LocalDate slotDate) {
        this.slotDate = slotDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }
}
