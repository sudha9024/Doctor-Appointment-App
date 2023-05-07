package com.example.doctor.DoctorHomePagePack;

public class DoctorSettingsModel {
    private int icon;
    private String heading;

    public DoctorSettingsModel(int icon, String heading) {
        this.icon = icon;
        this.heading = heading;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }
}
