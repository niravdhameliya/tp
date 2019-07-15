/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.controller.servicePO;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class getInvoicesListFromPOId {

    private String poid;
    private ArrayList<Invoice_details> invoices;

    public getInvoicesListFromPOId(String poid) {
        this.poid = poid;
        // this.invoices = new ArrayList<>();
    }

    public void fetch(final allInvoiceCallBack challanCallback) {
        invoices = new ArrayList<>();
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("invoice").orderByChild("pono").equalTo(poid);

        // query.equalTo("pono", poid).addValueEventListener(new ValueEventListener() {
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    System.out.println("exist");
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                         System.out.println("invoice" + singleSnapshot);
                        Invoice_details c = singleSnapshot.getValue(Invoice_details.class);
                        invoices.add(c);
                    }
                    challanCallback.onCallback1(invoices);

                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError arg0) {

            }
        });
    }

}
