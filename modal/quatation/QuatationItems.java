/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.quatation;

/**
 *
 * @author Rahul
 */
public class QuatationItems {

    //  private String quatatiionId;
    private String itemname;
    private String quantity;
    private String unitrate;

    public QuatationItems() {
    }

    public QuatationItems(String itemname, String quantity, String unitrate) {
        this.itemname = itemname;
        this.quantity = quantity;
        this.unitrate = unitrate;
    }

    /*  public String getQuatatiionId() {
     return quatatiionId;
     }

     public void setQuatatiionId(String quatatiionId) {
     this.quatatiionId = quatatiionId;
     }*/
    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitrate() {
        return unitrate;
    }

    public void setUnitrate(String unitrate) {
        this.unitrate = unitrate;
    }

    @Override
    public String toString() {
        //return "QuatationItems{" + "quatatiionId=" + quatatiionId + ", itemname=" + itemname + ", quantity=" + quantity + ", unitrate=" + unitrate + '}';
        return "QuatationItems{" + ", itemname=" + itemname + ", quantity=" + quantity + ", unitrate=" + unitrate + '}';
    }

}
