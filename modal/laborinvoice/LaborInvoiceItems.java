/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.laborinvoice;

import com.mileset.neeleshengineers.modal.Invoice.*;
import java.util.Objects;

/**
 *
 * @author rahul
 */
public class LaborInvoiceItems {

    private String item_name;
    private int quantity;
    private double unit_rate;
    private double amount;
    private String itemCode, drawingNumber, unitCode;

    public LaborInvoiceItems() {
    }

    public LaborInvoiceItems(String item_name, int quantity, double unit_rate, double amount, String itemCode, String drawingNumber, String unitCode) {
        this.item_name = item_name;
        this.quantity = quantity;
        this.unit_rate = unit_rate;
        this.amount = amount;
        this.itemCode = itemCode;
        this.drawingNumber = drawingNumber;
        this.unitCode = unitCode;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    @Override
    public int hashCode() {
        return this.item_name.hashCode();
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
        final LaborInvoiceItems other = (LaborInvoiceItems) obj;
        if (!Objects.equals(this.item_name, other.item_name)) {
            return false;
        }
        return true;
    }

    public String getDrawingNumber() {
        return drawingNumber;
    }

    public void setDrawingNumber(String drawingNumber) {
        this.drawingNumber = drawingNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

}
