package com.classicLeathers.classicLeathersTool.production.model;

public class JobCardProgressDto {


    private String jobCard;
    private String Date;
    private String trackingSku;
    private String count;

    public String getJobCard() {
        return jobCard;
    }

    public void setJobCard(String jobCard) {
        this.jobCard = jobCard;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTrackingSku() {
        return trackingSku;
    }

    public void setTrackingSku(String trackingSku) {
        this.trackingSku = trackingSku;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
