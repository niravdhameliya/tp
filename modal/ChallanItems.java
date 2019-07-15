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
public class ChallanItems {

    private String itemName;
    private int quantity;

    public ChallanItems() {
    }

    public ChallanItems(String itemName, int quantity) {
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
