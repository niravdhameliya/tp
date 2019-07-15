/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.pricebid;

/**
 *
 * @author rahul
 */
public class PriceBidItems {

    private String nomenclature;
    private String quantity;
    private String freight;
    private String basic_rate;
    private String cgst;
    private String sgst;
    private String igst;
    private String total;

    public PriceBidItems() {
    }

    public PriceBidItems(String nomenclature, String quantity, String freight, String basic_rate, String cgst, String sgst, String igst, String total) {
        this.nomenclature = nomenclature;
        this.quantity = quantity;
        this.freight = freight;
        this.basic_rate = basic_rate;
        this.cgst = cgst;
        this.sgst = sgst;
        this.igst = igst;
        this.total = total;
    }

    public String getNomenclature() {
        return nomenclature;
    }

    public void setNomenclature(String nomenclature) {
        this.nomenclature = nomenclature;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }

    public String getBasic_rate() {
        return basic_rate;
    }

    public void setBasic_rate(String basic_rate) {
        this.basic_rate = basic_rate;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PriceBidItems{" + "nomenclature=" + nomenclature + ", quantity=" + quantity + ", freight=" + freight + ", basic_rate=" + basic_rate + ", cgst=" + cgst + ", sgst=" + sgst + ", igst=" + igst + ", total=" + total + '}';
    }
}
