package com.example.mytrips;

public class HistoryData {
    private String tripNameHistory,stateHistory,dateHistory,timeHistory,startPointHistory,endPointHistory;
    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getTripNameHistory() {
        return tripNameHistory;
    }

    public void setTripNameHistory(String tripNameHistory) {
        this.tripNameHistory = tripNameHistory;
    }

    public String getStateHistory() {
        return stateHistory;
    }

    public void setStateHistory(String stateHistory) {
        this.stateHistory = stateHistory;
    }

    public String getDateHistory() {
        return dateHistory;
    }

    public void setDateHistory(String dateHistory) {
        this.dateHistory = dateHistory;
    }

    public String getTimeHistory() {
        return timeHistory;
    }

    public void setTimeHistory(String timeHistory) {
        this.timeHistory = timeHistory;
    }

    public String getStartPointHistory() {
        return startPointHistory;
    }

    public void setStartPointHistory(String startPointHistory) {
        this.startPointHistory = startPointHistory;
    }

    public String getEndPointHistory() {
        return endPointHistory;
    }

    public void setEndPointHistory(String endPointHistory) {
        this.endPointHistory = endPointHistory;
    }

    public HistoryData(String tripNameHistory, String stateHistory, String dateHistory, String timeHistory, String startPointHistory, String endPointHistory) {
        this.tripNameHistory = tripNameHistory;
        this.stateHistory = stateHistory;
        this.dateHistory = dateHistory;
        this.timeHistory = timeHistory;
        this.startPointHistory = startPointHistory;
        this.endPointHistory = endPointHistory;
        this.expanded=false;
    }
}
