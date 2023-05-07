package com.example.doctor;

import android.net.Uri;

public class Users {
    private String name, email, pass, repass, dob, gender, phone, role,specialisation, experience, buildingNo, street, city, country, pinCode, bloodType, weight, fees, timeFrom, timeTo;
    private String ImageUrl;
    private String userId;
    public Users(){

    }

    public Users(String name, String email, String pass, String repass, String dob, String gender, String phone,
                 String role,
                 String specialisation, String experience, String buildingNo, String street, String city,
                 String country, String pinCode, String bloodType, String weight, String fees, String timeFrom,
                 String timeTo, String ImageUrl, String userId) {
        this.name = name;
        this.email = email;
        this.pass = pass;
        this.repass = repass;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
        this.specialisation = specialisation;
        this.experience = experience;
        this.buildingNo = buildingNo;
        this.street = street;
        this.city = city;
        this.country = country;
        this.pinCode = pinCode;
        this.bloodType = bloodType;
        this.weight = weight;
        this.fees = fees;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.ImageUrl = ImageUrl;

        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.ImageUrl = imageUrl;
    }


}
