/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import com.mileset.neeleshengineers.modal.technicalbid.TechnicalBid;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class UtilVars {

    private static Customer cust;
    private static TechnicalBid technicalBid;
    private static PriceBid priceBid;
    private static Po po;
    private static Invoice_details requiredInvoiceDetails;
    private static ArrayList<Customer> custList = new ArrayList<Customer>();

    public static ArrayList<Customer> getCustList() {
        return custList;
    }

    public static void setCustList(ArrayList<Customer> custList) {
        UtilVars.custList = custList;
    }

    public static Invoice_details getRequiredInvoiceDetails() {
        return requiredInvoiceDetails;
    }

    public static void setRequiredInvoiceDetails(Invoice_details requiredInvoiceDetails) {
        UtilVars.requiredInvoiceDetails = requiredInvoiceDetails;
    }

    public static Customer getCustomer() {
        return cust;
    }

    public static void setCustomer(Customer customer) {
        cust = customer;
    }

    public static TechnicalBid getTechnicalBid() {
        return technicalBid;
    }

    public static void setTechnicalBid(TechnicalBid technicalBid) {
        UtilVars.technicalBid = technicalBid;
    }

    public static PriceBid getPriceBid() {
        return priceBid;
    }

    public static void setPriceBid(PriceBid priceBid) {
        UtilVars.priceBid = priceBid;
    }

    public static Po getPo() {
        return po;
    }

    public static void setPo(Po po) {
        UtilVars.po = po;
    }

}
