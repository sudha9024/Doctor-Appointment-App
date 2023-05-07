
package com.example.doctor.PatientHomePagePack;

public class MyAppointmentModel {
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    private String doctorId;
    private String doctorName;
//    private String appointmentDate;
//
//    public String getAppointmentDate() {
//        return appointmentDate;
//    }
//
//    public void setAppointmentDate(String appointmentDate) {
//        this.appointmentDate = appointmentDate;
//    }

    private String appointmentTime;
    private String DoctorImage;



    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }



    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorImage() {
        return DoctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        DoctorImage = doctorImage;
    }



    public MyAppointmentModel(String  docId,String DoctorName,String appointmentTime, String DoctorImage) {
        this.doctorId=docId;
        this.doctorName = DoctorName;
      //  this.appointmentDate=appointmentDate;
        this.appointmentTime=appointmentTime;
        this.DoctorImage = DoctorImage;
    }


}
