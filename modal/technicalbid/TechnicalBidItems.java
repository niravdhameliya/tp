/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.technicalbid;

/**
 *
 * @author rahul
 */
public class TechnicalBidItems {

    private String nomenclature;
    private String quantity;
    private String remarks;

    public TechnicalBidItems() {
    }

    public TechnicalBidItems(String nomenclature, String quantity, String remarks) {
        this.nomenclature = nomenclature;
        this.quantity = quantity;
        this.remarks = remarks;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "TechnicalBidItems{" + "nomenclature=" + nomenclature + ", quantity=" + quantity + ", remarks=" + remarks + '}';
    }

}
