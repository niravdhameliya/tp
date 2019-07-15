/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal;

/**
 *
 * @author rahul
 */
public class Customer {

    private String id;
    private String name;
    private String contact;
    private String gstin;
    private String address;
    private String email;
    private String vendor_code;
    private String panNo;
    private String cgst, sgst, igst;

    public Customer() {

    }

    public Customer(String id, String name, String contact, String gstin, String address, String email, String vendor_code, String panNo, String cgst, String sgst, String igst) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.gstin = gstin;
        this.address = address;
        this.email = email;
        this.vendor_code = vendor_code;
        this.panNo = panNo;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVendor_code() {
        return vendor_code;
    }

    public void setVendor_code(String vendor_code) {
        this.vendor_code = vendor_code;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    @Override
    public String toString() {
        return "Customer{" + "id=" + id + ", name=" + name + ", contact=" + contact + ", gstin=" + gstin + ", address=" + address + ", email=" + email + ", vendor_code=" + vendor_code + ", panNo=" + panNo + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + '}';
    }

}
