/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.supplyorder;

import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class SupplyOrder {

    private String refDate, cname;
    private String supplierNameAddress, supplierContact, supplierGSTIN, supplierPAN;
    private String SONo, SODate, Your_Ref;
    private ArrayList<SupplyOrderItems> supplyOrderItemsList;
    private String lessDiscount, otherCharges, subTotal, cgst, sgst, igst, grandTotal;
    private String note, payment, dispatch, discount, delivery, enclosures;
    private String perCgst, perSgst, perIgst;
    private String fid;

    public SupplyOrder() {
    }

    public SupplyOrder(String refDate, String supplierNameAddress, String supplierContact, String supplierGSTIN, String supplierPAN, String SONo, String SODate, String Your_Ref, ArrayList<SupplyOrderItems> supplyOrderItemsList, String lessDiscount, String otherCharges, String subTotal, String cgst, String sgst, String igst, String grandTotal, String note, String payment, String dispatch, String discount, String delivery, String enclosures, String perCgst, String perSgst, String perIgst) {
        this.refDate = refDate;
        this.supplierNameAddress = supplierNameAddress;
        this.supplierContact = supplierContact;
        this.supplierGSTIN = supplierGSTIN;
        this.supplierPAN = supplierPAN;
        this.SONo = SONo;
        this.SODate = SODate;
        this.Your_Ref = Your_Ref;
        this.supplyOrderItemsList = supplyOrderItemsList;
        this.lessDiscount = lessDiscount;
        this.otherCharges = otherCharges;
        this.subTotal = subTotal;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.grandTotal = grandTotal;
        this.note = note;
        this.payment = payment;
        this.dispatch = dispatch;
        this.discount = discount;
        this.delivery = delivery;
        this.enclosures = enclosures;
        this.perCgst = perCgst;
        this.perSgst = perSgst;
        this.perIgst = perIgst;
    }

    public String getRefDate() {
        return refDate;
    }

    public void setRefDate(String refDate) {
        this.refDate = refDate;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSupplierNameAddress() {
        return supplierNameAddress;
    }

    public void setSupplierNameAddress(String supplierNameAddress) {
        this.supplierNameAddress = supplierNameAddress;
    }

    public String getSupplierContact() {
        return supplierContact;
    }

    public void setSupplierContact(String supplierContact) {
        this.supplierContact = supplierContact;
    }

    public String getSupplierGSTIN() {
        return supplierGSTIN;
    }

    public void setSupplierGSTIN(String supplierGSTIN) {
        this.supplierGSTIN = supplierGSTIN;
    }

    public String getSupplierPAN() {
        return supplierPAN;
    }

    public void setSupplierPAN(String supplierPAN) {
        this.supplierPAN = supplierPAN;
    }

    public String getSONo() {
        return SONo;
    }

    public void setSONo(String SONo) {
        this.SONo = SONo;
    }

    public String getSODate() {
        return SODate;
    }

    public void setSODate(String SODate) {
        this.SODate = SODate;
    }

    public String getYour_Ref() {
        return Your_Ref;
    }

    public void setYour_Ref(String Your_Ref) {
        this.Your_Ref = Your_Ref;
    }

    public ArrayList<SupplyOrderItems> getSupplyOrderItemsList() {
        return supplyOrderItemsList;
    }

    public void setSupplyOrderItemsList(ArrayList<SupplyOrderItems> supplyOrderItemsList) {
        this.supplyOrderItemsList = supplyOrderItemsList;
    }

    public String getLessDiscount() {
        return lessDiscount;
    }

    public void setLessDiscount(String lessDiscount) {
        this.lessDiscount = lessDiscount;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getCgst() {
        return cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getIgst() {
        return igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getDispatch() {
        return dispatch;
    }

    public void setDispatch(String dispatch) {
        this.dispatch = dispatch;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getEnclosures() {
        return enclosures;
    }

    public void setEnclosures(String enclosures) {
        this.enclosures = enclosures;
    }

    public String getPerCgst() {
        return perCgst;
    }

    public void setPerCgst(String perCgst) {
        this.perCgst = perCgst;
    }

    public String getPerSgst() {
        return perSgst;
    }

    public void setPerSgst(String perSgst) {
        this.perSgst = perSgst;
    }

    public String getPerIgst() {
        return perIgst;
    }

    public void setPerIgst(String perIgst) {
        this.perIgst = perIgst;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Override
    public String toString() {
        return "SupplyOrder{" + "refDate=" + refDate + ", cname=" + cname + ", supplierNameAddress=" + supplierNameAddress + ", supplierContact=" + supplierContact + ", supplierGSTIN=" + supplierGSTIN + ", supplierPAN=" + supplierPAN + ", SONo=" + SONo + ", SODate=" + SODate + ", Your_Ref=" + Your_Ref + ", supplyOrderItemsList=" + supplyOrderItemsList + ", lessDiscount=" + lessDiscount + ", otherCharges=" + otherCharges + ", subTotal=" + subTotal + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", grandTotal=" + grandTotal + ", note=" + note + ", payment=" + payment + ", dispatch=" + dispatch + ", discount=" + discount + ", delivery=" + delivery + ", enclosures=" + enclosures + ", perCgst=" + perCgst + ", perSgst=" + perSgst + ", perIgst=" + perIgst + ", fid=" + fid + '}';
    }

}
