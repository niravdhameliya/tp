/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.laborinvoice;

import com.mileset.neeleshengineers.modal.Invoice.*;
import com.mileset.neeleshengineers.util.UtilVars;
import java.util.ArrayList;

public class LaborInvoiceDetails {

    private String sac, gstin;
    private String invoice_id, fid, cname;
    private String date, year;
    private double Othercharges, cgst, sgst, igst, basicTotal, grandTotal, amount, roundOFF;
    private String vehicleNo, PONo, vendorCode, dte2;
    private ArrayList<LaborInvoiceItems> itemList;
    private String challanNo, challanDate;
    private String financialYear;

    public LaborInvoiceDetails() {
    }

    public LaborInvoiceDetails(String invoice_id, String cname, String date, String year, double Othercharges, double amount, double roundOFF, double cgst, double sgst, double igst, double basicTotal, double grandTotal, String vehicleNo, String PONo, String vendorCode, String dte2, ArrayList<LaborInvoiceItems> itemList, String challanNo, String challanDate, String sac) {
        this.sac = sac;
        this.invoice_id = invoice_id;
        this.cname = cname;
        this.date = date;
        this.year = year;
        this.Othercharges = Othercharges;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.amount = amount;
        this.roundOFF = roundOFF;
        this.basicTotal = basicTotal;
        this.grandTotal = grandTotal;
        this.vehicleNo = vehicleNo;
        this.PONo = PONo;
        this.vendorCode = vendorCode;
        this.dte2 = dte2;
        this.itemList = itemList;
        this.challanNo = challanNo;
        this.challanDate = challanDate;
    }

    public String getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(String invoice_id) {
        this.invoice_id = invoice_id;
    }

    public String getFid() {
        return fid;
    }

    public void setGSTIN(String gstin) {
        this.gstin = gstin;
    }

    public String getGSTIN() {
        return gstin;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
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

    public ArrayList<LaborInvoiceItems> getItemList() {
        return itemList;
    }

    public void setItemList(ArrayList<LaborInvoiceItems> itemList) {
        this.itemList = itemList;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getChallanDate() {
        return challanDate;
    }

    public void setChallanDate(String challanDate) {
        this.challanDate = challanDate;
    }

    public String getFinancialYear() {
        return financialYear;
    }

    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    @Override
    public String toString() {
        return "LaborInvoiceDetails{" + "invoice_id=" + invoice_id + ", fid=" + fid + ", cname=" + cname + ", date=" + date + ", year=" + year + ", Othercharges=" + Othercharges + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", basicTotal=" + basicTotal + ", grandTotal=" + grandTotal + ", vehicleNo=" + vehicleNo + ", PONo=" + PONo + ", vendorCode=" + vendorCode + ", dte2=" + dte2 + ", itemList=" + itemList + ", challanNo=" + challanNo + ", challanDate=" + challanDate + '}';
    }

    public String getSac() {
        return sac;
    }

    public void setSac(String sac) {
        this.sac = sac;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRoundOFF() {
        return roundOFF;
    }

    public void setRoundOFF(double roundOFF) {
        this.roundOFF = roundOFF;
    }

}
