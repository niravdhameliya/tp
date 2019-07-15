/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal;

import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class ServicePO {

    private String poid;
    private String bill_date;
    private String cust_id;
    private ArrayList<PoItems> items;
    private String cgst, sgst, igst, delivery_date, psd, custName;
    private String sac;

    public ServicePO() {

    }

    public ServicePO(String poid, String bill_date, String cust_id, ArrayList<PoItems> items, String cgst, String sgst, String igst, String delivery_date, String psd, String sac) {
        this.poid = poid;
        this.sac = sac;
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
        return this.custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPoid() {
        return this.poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getBill_date() {
        return this.bill_date;
    }

    public void setBill_date(String bill_date) {
        this.bill_date = bill_date;
    }

    public String getCust_id() {
        return this.cust_id;
    }

    public void setCust_id(String cust_id) {
        this.cust_id = cust_id;
    }

    public ArrayList<PoItems> getItems() {
        return this.items;
    }

    public void setItems(ArrayList<PoItems> items) {
        this.items = items;
    }

    public String getCgst() {
        return this.cgst;
    }

    public void setCgst(String cgst) {
        this.cgst = cgst;
    }

    public String getSgst() {
        return this.sgst;
    }

    public void setSgst(String sgst) {
        this.sgst = sgst;
    }

    public String getIgst() {
        return this.igst;
    }

    public void setIgst(String igst) {
        this.igst = igst;
    }

    public String getDelivery_date() {
        return this.delivery_date;
    }

    public void setDelivery_date(String delivery_date) {
        this.delivery_date = delivery_date;
    }

    public String getPsd() {
        return this.psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    @Override
    public String toString() {
        return "Po{" + "poid=" + poid + ", bill_date=" + bill_date + ", cust_id=" + cust_id + ", items=" + items + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", delivery_date=" + delivery_date + ", psd=" + psd + '}';
    }

    public String getSac() {
        return this.sac;
    }

    public void setSac(String sac) {
        this.sac = sac;
    }

}
