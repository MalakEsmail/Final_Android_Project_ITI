package com.example.mytrips;

public class UpcomingData {
 private String date,time,tripName,state,startPoint,endPoint;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public UpcomingData(String date, String time, String tripName, String state, String startPoint, String endPoint) {
        this.date = date;
        this.time = time;
        this.tripName = tripName;
        this.state = state;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }
}
