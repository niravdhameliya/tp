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
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class getLabourInvoiceFromCustId {

    String cust_id;
    ArrayList<LaborInvoiceDetails> lInvoiceList;
    String challanId, poID;

    public getLabourInvoiceFromCustId(String cust_id) {
        this.cust_id = cust_id;

    }

    public getLabourInvoiceFromCustId(String cust_id, String challanId, String poID) {
        this.cust_id = cust_id;
        this.poID = poID;
        this.challanId = challanId;

    }

    public void fetch(final labourCustIDCallBack lCustCallBack) {
        lInvoiceList = new ArrayList<>();
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("SPOLabourInvoice/" + cust_id);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    System.out.println("exist");
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        // System.out.println("" + singleSnapshot);
                        LaborInvoiceDetails c = singleSnapshot.getValue(LaborInvoiceDetails.class);
                        //     System.out.println("labour: " + c.getInvoice_id());
                        lInvoiceList.add(c);
                    }
                    lCustCallBack.callBack(lInvoiceList);

                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError arg0) {

            }
        });

    }

    public void fetchWithChallan(final labourCustIDCallBack lCustCallBack) {
        lInvoiceList = new ArrayList<>();
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("SPOLabourInvoice/" + cust_id);
        query.orderByChild("challanNo").equalTo(challanId).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    System.out.println("exist");
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        // System.out.println("" + singleSnapshot);
                        LaborInvoiceDetails c = singleSnapshot.getValue(LaborInvoiceDetails.class);
                        System.out.println("labour: " + c.getInvoice_id());

                        if (c.getPONo().equals(poID)) {
                            lInvoiceList.add(c);
                        }
                    }
                    lCustCallBack.callBack(lInvoiceList);

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
