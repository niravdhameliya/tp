/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Invoice;

import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class ShowwAllInvoicesByPoid1Vars {

    private static ArrayList<Invoice_details> invoices;
    private static Customer customer;

    public static ArrayList<Invoice_details> getInvoices() {
        return invoices;
    }

    public static void setInvoices(ArrayList<Invoice_details> invoices) {
        ShowwAllInvoicesByPoid1Vars.invoices = invoices;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        ShowwAllInvoicesByPoid1Vars.customer = customer;
    }
}
