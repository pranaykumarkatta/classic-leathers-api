package com.classicLeathers.classicLeathersTool.retail.model;

import java.util.Date;

public class TimeSheet implements Comparable {
    private String rowNumber;
    private String date;
    private String employeeName;
    private String present;
    private String inTime;
    private String OutTime;
    private String entryType;
    private String updated_time;

    public String getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(String rowNumber) {
        this.rowNumber = rowNumber;
    }

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

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOutTime() {
        return OutTime;
    }

    public void setOutTime(String outTime) {
        OutTime = outTime;
    }

    public String getEntryType() {
        return entryType;
    }

    public void setEntryType(String entryType) {
        this.entryType = entryType;
    }

    public String getUpdated_time() {
        return updated_time;
    }

    public void setUpdated_time(String updated_time) {
        this.updated_time = updated_time;
    }

    @Override
    public int compareTo(Object o) {
        Date thisDate = new Date(this.getDate());
        Date oDate = new Date(((TimeSheet) o).getDate());
        if (thisDate.after(oDate) || ((thisDate.equals(oDate)) && Integer.parseInt(this.getRowNumber()) > Integer.parseInt(((TimeSheet) o).getRowNumber()))) {
            return 1;
        }
        if (thisDate.before(oDate) || ((thisDate.equals(oDate)) && Integer.parseInt(this.getRowNumber()) < Integer.parseInt(((TimeSheet) o).getRowNumber()))) {
            return -1;
        } else {
            return 0;
        }
    }
}
