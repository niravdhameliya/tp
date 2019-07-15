/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.Invoice;

/**
 *
 * @author rahul
 */
public class InvoiceMasterData {

    private int invoice_id;
    private int cust_id;
    private String dte;
    private double other;
    private double cgst;
    private double sgst;
    private double igst;
    private double grand;
    private double basic;

    public InvoiceMasterData() {

    }

    public InvoiceMasterData(int invoice_id, int cust_id, String dte, double other, double cgst, double sgst, double igst, double grand, double basic) {
        this.invoice_id = invoice_id;
        this.cust_id = cust_id;
        this.dte = dte;
        this.other = other;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.grand = grand;
        this.basic = basic;
    }

    public int getInvoice_id() {
        return invoice_id;
    }

    public void setInvoice_id(int invoice_id) {
        this.invoice_id = invoice_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id = cust_id;
    }

    public String getDte() {
        return dte;
    }

    public void setDte(String dte) {
        this.dte = dte;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
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

    public double getGrand() {
        return grand;
    }

    public void setGrand(double grand) {
        this.grand = grand;
    }

    public double getBasic() {
        return basic;
    }

    public void setBasic(double basic) {
        this.basic = basic;
    }

}
