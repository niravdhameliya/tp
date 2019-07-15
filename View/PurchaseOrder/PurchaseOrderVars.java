/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.PurchaseOrder;

import com.mileset.neeleshengineers.modal.Po;

/**
 *
 * @author Rahul
 */
public class PurchaseOrderVars {

    private static Po po;

    public static Po getPo() {
        return po;
    }

    public static void setPo(Po po) {
        PurchaseOrderVars.po = po;
    }

}
