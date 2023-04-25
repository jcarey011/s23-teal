package com.example.drivesafe;

public class HazardReport {
    private String driverId;
    private String hazardType;
    private String photoFilename;
    private String date;
    private String time;
    private String location;

    public HazardReport(String driverId, String hazardType, String photoFilename, String date, String time, String location) {
        this.driverId = driverId;
        this.hazardType = hazardType;
        this.photoFilename = photoFilename;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    @Override
    public String toString() {
        return "HazardReport{" +
                "driverId='" + driverId + '\'' +
                ", hazardType='" + hazardType + '\'' +
                ", photoFilename='" + photoFilename + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}