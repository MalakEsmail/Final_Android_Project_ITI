package com.example.mytrips;

public class HistoryData {
    private String name,startPoint,endPoint,time,date,tripType,repetition,status,uId,tripId,dateAndTimeInMillis;
    private boolean expanded;

    public HistoryData() {
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public HistoryData(String name, String startPoint, String endPoint, String time, String date, String tripType, String repetition, String status, String uId,
                       String tripId, String dateAndTimeInMillis) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
        this.date = date;
        this.tripType = tripType;
        this.repetition = repetition;
        this.status = status;
        this.uId = uId;
        this.tripId = tripId;
        this.dateAndTimeInMillis = dateAndTimeInMillis;
        this.expanded = false;
    }

    public HistoryData(String name, String startPoint, String endPoint, String time, String date, String status) {
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.time = time;
        this.date = date;
        this.status = status;
        this.expanded = false;

    }
    //    public HistoryData(String tripNameHistory, String stateHistory, String dateHistory, String timeHistory, String startPointHistory, String endPointHistory) {
//        this.tripNameHistory = tripNameHistory;
//        this.stateHistory = stateHistory;
//        this.dateHistory = dateHistory;
//        this.timeHistory = timeHistory;
//        this.startPointHistory = startPointHistory;
//        this.endPointHistory = endPointHistory;
//        this.expanded=false;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getDateAndTimeInMillis() {
        return dateAndTimeInMillis;
    }

    public void setDateAndTimeInMillis(String dateAndTimeInMillis) {
        this.dateAndTimeInMillis = dateAndTimeInMillis;
    }

}
