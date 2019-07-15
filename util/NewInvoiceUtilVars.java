/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Po;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class NewInvoiceUtilVars {

    private static Po po;
    private static ArrayList<Invoice_details> invoices;
    private static String generatedId;

    public static Po getPo() {
        return po;
    }

    public static void setPo(Po po) {
        NewInvoiceUtilVars.po = po;
    }

    public static ArrayList<Invoice_details> getInvoices() {
        return invoices;
    }

    public static void setInvoices(ArrayList<Invoice_details> invoices) {
        NewInvoiceUtilVars.invoices = invoices;
    }

    public static String getGeneratedId() {
        return generatedId;
    }

    public static void setGeneratedId(String generatedId) {
        NewInvoiceUtilVars.generatedId = generatedId;
    }
}
