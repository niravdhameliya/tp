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
import com.mileset.neeleshengineers.modal.Challan;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class getChallanFromChallanId {

    String poid;
    Challan value;
    String custid;
    String challanId;

    public getChallanFromChallanId() {
    }

    public getChallanFromChallanId(String poid, String custid, String challanId) {
        this.poid = poid;
        this.custid = custid;
        this.challanId = challanId;
        System.out.println("poid: " + poid);
        System.out.println("custid: " + custid);
        System.out.println("challanId: " + challanId);
    }

    public void fetch(final alChallanCallBack challanCallback) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("SPOChallan/" + custid + "/" + poid + "/" + challanId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    System.out.println("exist");
                    //    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                    // System.out.println("" + singleSnapshot);
                    value = ds.getValue(Challan.class);
                    System.out.println("value2: " + value.getChallanNo());
                    //  }
                    challanCallback.onCallback1(value);

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
