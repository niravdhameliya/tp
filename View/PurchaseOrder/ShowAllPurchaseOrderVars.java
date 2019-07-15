/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.PurchaseOrder;

import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Po;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class ShowAllPurchaseOrderVars {

    public static ArrayList<Po> polist;
    public static Customer customer;

    public static ArrayList<Po> getPolist() {
        return polist;
    }

    public static void setPolist(ArrayList<Po> polist) {
        ShowAllPurchaseOrderVars.polist = polist;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        ShowAllPurchaseOrderVars.customer = customer;
    }

}
