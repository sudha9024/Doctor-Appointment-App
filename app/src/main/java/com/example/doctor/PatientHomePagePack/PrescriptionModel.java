package com.example.doctor.PatientHomePagePack;

public class PrescriptionModel  {
    private String DoctorName,Date;
    private String imageUrl;

//    public int getImage() {
//        return image;
//    }
//
//    public void setImage(int image) {
//        this.image = image;
//    }
//
//    private int image;

    public PrescriptionModel() {

    }
//    public PrescriptionModel(int image,String DoctorName, String Date) {
//        this.DoctorName = DoctorName;
//        this.Date = Date;
//        this.image=image;
//    }
    public PrescriptionModel(String imageUrl,String DoctorName, String Date) {
        this.DoctorName = DoctorName;
        this.Date = Date;
        this.imageUrl=imageUrl;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String DoctorName) {
        this.DoctorName = DoctorName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
}
}