/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.Invoice;

import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class Invoice_details {

    private String gstin;
    private String customerContact, note;
    private String invoice_id, fid, cname;
    private String date, year;
    private double Othercharges, cgst, sgst, igst, basicTotal, grandTotal, roundOFF;
    private String PONo, vendorCode, dte2;
    private ArrayList<Items> itemList;

    public Invoice_details() {
    }

    public Invoice_details(String invoice_id, String date, double Othercharges, double cgst, double sgst, double igst, double basicTotal, double grandTotal, String vehicleNo, String PONo, String vendorCode, String dte2, ArrayList<Items> itemList, String cname, String year, String note, double roundOFF, String gstin) {
        this.note = note;
        this.roundOFF = roundOFF;
        this.gstin = gstin;
        this.invoice_id = invoice_id;
        this.date = date;
        this.Othercharges = Othercharges;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.basicTotal = basicTotal;
        this.grandTotal = grandTotal;
        this.PONo = PONo;
        this.vendorCode = vendorCode;
        this.dte2 = dte2;
        this.itemList = itemList;
        this.cname = cname;
        this.year = year;
    }

    public void setRoundOFF(double roundOFF) {
        this.roundOFF = roundOFF;
    }

    public double getRoundOFF() {
        return roundOFF;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getOthercharges() {
        return Othercharges;
    }

    public void setOthercharges(double Othercharges) {
        this.Othercharges = Othercharges;
    }

    public double getCgst() {
        return cgst;
    }

    public void setCgst(double cgst) {
        this.cgst = cgst;
    }

    public double getSgst() {
        return sgst;
    }

    public void setSgst(double sgst) {
        this.sgst = sgst;
    }

    public double getIgst() {
        return igst;
    }

    public void setIgst(double igst) {
        this.igst = igst;
    }

    public double getBasicTotal() {
        return basicTotal;
    }

    public void setBasicTotal(double basicTotal) {
        this.basicTotal = basicTotal;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getPONo() {
        return PONo;
    }

    public void setPONo(String PONo) {
        this.PONo = PONo;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getDte2() {
        return dte2;
    }

    public void setDte2(String dte2) {
        this.dte2 = dte2;
    }

    public ArrayList<Items> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<Items> itemList) {
        this.itemList = itemList;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    @Override
    public String toString() {
        return "Invoice_details{" + "customerContact=" + customerContact + ", invoice_id=" + invoice_id + ", fid=" + fid + ", cname=" + cname + ", date=" + date + ", year=" + year + ", Othercharges=" + Othercharges + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", basicTotal=" + basicTotal + ", grandTotal=" + grandTotal + ", PONo=" + PONo + ", vendorCode=" + vendorCode + ", dte2=" + dte2 + ", itemList=" + itemList + '}';
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

}
