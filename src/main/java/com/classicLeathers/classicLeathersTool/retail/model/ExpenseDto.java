package com.classicLeathers.classicLeathersTool.retail.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class ExpenseDto implements Comparable{
    private String date;
    private String updatedBy;
    private String expenseType;
    private String amount;
    private String paidTo;
    private String mop;
    private String paymentReference;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public void setPaidTo(String paidTo) {
        this.paidTo = paidTo;
    }

    public String getMop() {
        return mop;
    }

    public void setMop(String mop) {
        this.mop = mop;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    @Override
    public int compareTo(@NotNull Object o) {
        Date thisDate = new Date(this.getDate());
        Date oDate = new Date(((ExpenseDto) o).getDate());
        if (thisDate.after(oDate) || ((thisDate.equals(oDate)))) {
            return 1;
        }
        if (thisDate.before(oDate) || ((thisDate.equals(oDate)))) {
            return -1;
        } else {
            return 0;
        }
    }
}