/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.quatation;

import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class QuatationData {

    private String kind_attn;
    private String quatation_type;
    private String to;
    private String quatationId;
    private String billDate;
    private String enquiry_number;
    private String enquiry_date;
    private ArrayList<QuatationItems> quatationItemses;
    private String fid;
    private String custId;
    private String year;
    private String taxes;
    private String delivery;
    private String payment;
    private String validity;
    private String mode_of_dispatch;
    private String remark;
    private String quatationIdDate;

    public QuatationData() {
    }

    public QuatationData(String to, String quatationId, String billDate, String enquiry_number, String enquiry_date, ArrayList<QuatationItems> quatationItemses, String fid, String custId, String year, String taxes, String delivery, String payment, String validity, String mode_of_dispatch, String remark, String quatation_type, String kind_attn) {
        this.kind_attn = kind_attn;
        this.quatation_type = quatation_type;
        this.to = to;
        this.quatationId = quatationId;
        this.billDate = billDate;
        this.enquiry_number = enquiry_number;
        this.enquiry_date = enquiry_date;
        this.quatationItemses = quatationItemses;
        this.fid = fid;
        this.custId = custId;
        this.year = year;
        this.taxes = taxes;
        this.delivery = delivery;
        this.payment = payment;
        this.validity = validity;
        this.mode_of_dispatch = mode_of_dispatch;
        this.remark = remark;
    }

    public String getQuatation_type() {
        return quatation_type;
    }

    public void setQuatation_type(String quatation_type) {
        this.quatation_type = quatation_type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getQuatationId() {
        return quatationId;
    }

    public void setQuatationId(String quatationId) {
        this.quatationId = quatationId;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getEnquiry_number() {
        return enquiry_number;
    }

    public void setEnquiry_number(String enquiry_number) {
        this.enquiry_number = enquiry_number;
    }

    public String getEnquiry_date() {
        return enquiry_date;
    }

    public void setEnquiry_date(String enquiry_date) {
        this.enquiry_date = enquiry_date;
    }

    public ArrayList<QuatationItems> getQuatationItemses() {
        return quatationItemses;
    }

    public void setQuatationItemses(ArrayList<QuatationItems> quatationItemses) {
        this.quatationItemses = quatationItemses;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getMode_of_dispatch() {
        return mode_of_dispatch;
    }

    public void setMode_of_dispatch(String mode_of_dispatch) {
        this.mode_of_dispatch = mode_of_dispatch;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getQuatationIdDate() {
        return quatationIdDate;
    }

    public void setQuatationIdDate(String quatationIdDate) {
        this.quatationIdDate = quatationIdDate;
    }

    public String getKind_attn() {
        return kind_attn;
    }

    public void setKind_attn(String kind_attn) {
        this.kind_attn = kind_attn;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

}
