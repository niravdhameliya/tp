/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.supplyorder;

/**
 *
 * @author rahul
 */
public class SupplyOrderItems {

    private String workOrderNo;
    private String particulars;
    private String quantity;
    private String rate;
    private String amount;

    public SupplyOrderItems(String workOrderNo, String particulars, String quantity, String rate, String amount) {
        this.workOrderNo = workOrderNo;
        this.particulars = particulars;
        this.quantity = quantity;
        this.rate = rate;
        this.amount = amount;
    }

    public SupplyOrderItems() {
    }

    public String getWorkOrderNo() {
        return workOrderNo;
    }

    public void setWorkOrderNo(String workOrderNo) {
        this.workOrderNo = workOrderNo;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "SupplyOrderItems{" + "workOrderNo=" + workOrderNo + ", particulars=" + particulars + ", quantity=" + quantity + ", rate=" + rate + ", amount=" + amount + '}';
    }

}
