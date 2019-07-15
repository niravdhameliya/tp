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
import com.mileset.neeleshengineers.modal.ServicePO;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class getServicePOFromCustID {

    String custID;
    ArrayList<ServicePO> servicePOList;

    public getServicePOFromCustID(String custID) {
        this.custID = custID;
    }

    public void fetch(final servicePOCallBack shiftTimeCallback) {
        servicePOList = new ArrayList<>();
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("servicePO/" + custID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

//                System.out.println("full data snapshot " + ds);
                if (ds.exists()) {
                    System.out.println("exist");
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        // System.out.println("" + singleSnapshot);
                        ServicePO st = singleSnapshot.getValue(ServicePO.class);
                        servicePOList.add(st);

                    }
                    shiftTimeCallback.onCallback(servicePOList);

                } else {
                    System.out.println("error");
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
            }
        });
    }
}
