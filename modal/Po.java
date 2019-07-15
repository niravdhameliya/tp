/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal;

import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class Po {

    private String poid;
    private String bill_date;
    private String cust_id;
    private ArrayList<PoItems> items;
    private String fid, cgst, sgst, igst, delivery_date, psd, custName;

    public Po() {

    }

    public Po(String poid, String bill_date, String cust_id, ArrayList<PoItems> items, String cgst, String sgst, String igst, String delivery_date, String psd) {
        this.poid = poid;
        this.bill_date = bill_date;
        this.cust_id = cust_id;
        this.items = items;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.delivery_date = delivery_date;
        this.psd = psd;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getBill_date() {
        return bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getCust_id() {
        return cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public ArrayList<PoItems> getItems() {
        return items;
    }

    public void setItems(ArrayList<PoItems> items) {
        this.items = items;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
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

    public String getDelivery_date() {
        return delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    @Override
    public String toString() {
        return "Po{" + "poid=" + poid + ", bill_date=" + bill_date + ", cust_id=" + cust_id + ", items=" + items + ", fid=" + fid + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", delivery_date=" + delivery_date + ", psd=" + psd + '}';
    }

}
