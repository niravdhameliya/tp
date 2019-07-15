/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.Invoice;

import java.util.Objects;

/**
 *
 * @author rahul
 */
public class Items {

    private String item_name;
    private int quantity;
    private double unit_rate;
    private double amount;
    private String hsn;
    private String itemCode;
    private String drawingNumber;
    private String work_order;
    private String unit;
    private String unitCode;

    public Items() {

    }

    public Items(String item_name, int quantity, double unit_rate, double amount, String hsn, String itemCode, String drawingNumber, String work_order, String unit, String unitCode) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.unit_rate = unit_rate;
        this.amount = amount;
        this.hsn = hsn;
        this.itemCode = itemCode;
        this.work_order = work_order;
        this.drawingNumber = drawingNumber;
        this.unit = unit;
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getWork_order() {
        return work_order;
    }

    public void setWork_order(String work_order) {
        this.work_order = work_order;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_rate() {
        return unit_rate;
    }

    public void setUnit_rate(double unit_rate) {
        this.unit_rate = unit_rate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getHsn() {
        return hsn;
    }

    public void setHsn(String hsn) {
        this.hsn = hsn;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "Items{" + "item_name=" + item_name + ", quantity=" + quantity + ", unit_rate=" + unit_rate + ", amount=" + amount + ", hsn=" + hsn + ", itemCode=" + itemCode + ", drawingNumber=" + drawingNumber + ", unit=" + unit + '}';
    }

    @Override
    public int hashCode() {
        return this.work_order.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Items other = (Items) obj;
        if (!Objects.equals(this.work_order, other.work_order)) {
            return false;
        }
        return true;
    }

}
