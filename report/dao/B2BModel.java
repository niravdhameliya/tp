/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.report.dao;

/**
 *
 * @author rahul
 */
public class B2BModel {

    private String gstInNo;
    private String invoiceId;
    private String date;
    private double invoiceValue;
    private String placeOfSupply;
    private int reverseCharge;
    private String invoiceType;
    private String eCommGstin;
    private int rate;
    private int taxableValue;
    private int cessAmount;
    private String hsn;

    public B2BModel() {
    }

    public B2BModel(String gstInNo, String invoiceId, String date, double invoiceValue, String placeOfSupply, int reverseCharge, String invoiceType, String eCommGstin, int rate, int taxableValue, int cessAmount, String hsn) {
        this.gstInNo = gstInNo;
        this.invoiceId = invoiceId;
        this.date = date;
        this.invoiceValue = invoiceValue;
        this.placeOfSupply = placeOfSupply;
        this.reverseCharge = reverseCharge;
        this.invoiceType = invoiceType;
        this.eCommGstin = eCommGstin;
        this.rate = rate;
        this.taxableValue = taxableValue;
        this.cessAmount = cessAmount;
        this.hsn = hsn;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getGstInNo() {
        return gstInNo;
    }

    public void setGstInNo(String gstInNo) {
        this.gstInNo = gstInNo;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getInvoiceValue() {
        return invoiceValue;
    }

    public void setInvoiceValue(double invoiceValue) {
        this.invoiceValue = invoiceValue;
    }

    public String getPlaceOfSupply() {
        return placeOfSupply;
    }

    public void setPlaceOfSupply(String placeOfSupply) {
        this.placeOfSupply = placeOfSupply;
    }

    public int getReverseCharge() {
        return reverseCharge;
    }

    public void setReverseCharge(int reverseCharge) {
        this.reverseCharge = reverseCharge;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String geteCommGstin() {
        return eCommGstin;
    }

    public void seteCommGstin(String eCommGstin) {
        this.eCommGstin = eCommGstin;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getTaxableValue() {
        return taxableValue;
    }

    public void setTaxableValue(int taxableValue) {
        this.taxableValue = taxableValue;
    }

    public int getCessAmount() {
        return cessAmount;
    }

    public void setCessAmount(int cessAmount) {
        this.cessAmount = cessAmount;
    }

    @Override
    public String toString() {
        return "B2BModel{" + "gstInNo=" + gstInNo + ", invoiceId=" + invoiceId + ", date=" + date + ", invoiceValue=" + invoiceValue + ", placeOfSupply=" + placeOfSupply + ", reverseCharge=" + reverseCharge + ", invoiceType=" + invoiceType + ", eCommGstin=" + eCommGstin + ", rate=" + rate + ", taxableValue=" + taxableValue + ", cessAmount=" + cessAmount + ", hsn=" + hsn + '}';
    }

}
