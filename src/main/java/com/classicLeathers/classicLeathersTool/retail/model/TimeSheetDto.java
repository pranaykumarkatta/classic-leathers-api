package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Date;
import java.util.List;

public class TimeSheetDto implements Comparable{
    private String date;
    private String employeeName;
    private String present;
    private String totalWorkingHours;
    private String rowNumber;
    private String isTodayEntry;
    private String latestInTime;
    private String latestOutTime;
    private List<String> eventlogList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }

    public String getTotalWorkingHours() {
        return totalWorkingHours;
    }

    public void setTotalWorkingHours(String totalWorkingHours) {
        this.totalWorkingHours = totalWorkingHours;
    }

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

    public String getIsTodayEntry() {
        return isTodayEntry;
    }

    public void setIsTodayEntry(String isTodayEntry) {
        this.isTodayEntry = isTodayEntry;
    }

    public String getLatestInTime() {
        return latestInTime;
    }

    public void setLatestInTime(String latestInTime) {
        this.latestInTime = latestInTime;
    }

    public String getLatestOutTime() {
        return latestOutTime;
    }

    public void setLatestOutTime(String latestOutTime) {
        this.latestOutTime = latestOutTime;
    }

    public List<String> getEventlogList() {
        return eventlogList;
    }

    public void setEventlogList(List<String> eventlogList) {
        this.eventlogList = eventlogList;
    }

    @Override
    public int compareTo(Object o) {
        Date thisDate = new Date(this.getDate());
        Date oDate = new Date(((TimeSheetDto) o).getDate());
        if (thisDate.after(oDate) || ((thisDate.equals(oDate)) && Integer.parseInt(this.getRowNumber()) > Integer.parseInt(((TimeSheetDto) o).getRowNumber()))) {
            return 1;
        }
        if (thisDate.before(oDate) || ((thisDate.equals(oDate)) && Integer.parseInt(this.getRowNumber()) < Integer.parseInt(((TimeSheetDto) o).getRowNumber()))) {
            return -1;
        } else {
            return 0;
        }
    }
}
