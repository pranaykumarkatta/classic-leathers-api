package com.classicLeathers.classicLeathersTool.model;

import java.util.Date;

public class TimeSheetDto implements Comparable{
    private String date;
    private String employeeName;
    private String present;
    private String totalWorkingHours;
    private String rowNumber;

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
