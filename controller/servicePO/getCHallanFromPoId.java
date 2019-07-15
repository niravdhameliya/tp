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
import com.mileset.neeleshengineers.util.FirebaseMethods;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class getCHallanFromPoId {

    String poid;
    ArrayList<Challan> value;
    String custid;

    public getCHallanFromPoId(String poid, String custid) {
        this.poid = poid;
        this.custid = custid;
    }

    public void fetch(final alChallanCallBack challanCallback) {
        value = new ArrayList<>();
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("SPOChallan/" + custid + "/" + poid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    System.out.println("exist");
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        // System.out.println("" + singleSnapshot);
                        Challan c = singleSnapshot.getValue(Challan.class);
                        value.add(c);
                    }
                    challanCallback.onCallback(value);

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
