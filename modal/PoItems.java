/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal;

/**
 *
 * @author Rahul
 */
public class PoItems {

    private String poid;
    private String item_name;
    private String quanatity;
    private String unitrate;
    private String amount;
    private String hsn;
    private String work_order;
    String item_code, drawing_number;
    String unitCode;

    public PoItems() {
    }

    public PoItems(String poid, String item_name, String quanatity, String unitrate, String amount, String hsn, String item_code, String drawing_number, String work_order, String unitCode) {
        this.poid = poid;
        this.item_name = item_name;
        this.quanatity = quanatity;
        this.unitrate = unitrate;
        this.amount = amount;
        this.hsn = hsn;
        this.item_code = item_code;
        this.drawing_number = drawing_number;
        this.work_order = work_order;
        this.unitCode = unitCode;
    }

    public PoItems(String poid, String item_name, String quanatity, String unitrate, String amount, String item_code, String drawing_number, String work_order, String unitCode) {
        this.poid = poid;
        this.item_name = item_name;
        this.quanatity = quanatity;
        this.unitrate = unitrate;
        this.amount = amount;
        this.item_code = item_code;
        this.drawing_number = drawing_number;
        this.work_order = work_order;
        this.unitCode = unitCode;
    }

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getQuanatity() {
        return quanatity;
    }

    public void setQuanatity(String quanatity) {
        this.quanatity = quanatity;
    }

    public String getUnitrate() {
        return unitrate;
    }

    public void setUnitrate(String unitrate) {
        this.unitrate = unitrate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getDrawing_number() {
        return drawing_number;
    }

    public void setDrawing_number(String drawing_number) {
        this.drawing_number = drawing_number;
    }

    @Override
    public String toString() {
        return "PoItems{" + "poid=" + poid + ", item_name=" + item_name + ", quanatity=" + quanatity + ", unitrate=" + unitrate + ", amount=" + amount + ", hsn=" + hsn + ", item_code=" + item_code + ", drawing_number=" + drawing_number + ", work_order=" + work_order + '}';
    }

}
