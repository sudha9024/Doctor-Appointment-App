package com.example.doctor;

public class AverageRating {
    private String docId;
    private Double avgRating;
    public AverageRating(){}


    public AverageRating(String docId, Double avgRating) {
        this.docId = docId;
        this.avgRating = avgRating;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }
}
