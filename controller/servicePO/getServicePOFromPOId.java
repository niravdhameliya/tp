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

/**
 *
 * @author rahul
 */
public class getServicePOFromPOId {

    String custID;
    String poid;
    ServicePO sPO;

    public getServicePOFromPOId(String custID, String poid) {
        this.custID = custID;
        this.poid = poid;
        sPO = null;
    }

    public ServicePO getPO() {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("servicePO/" + custID + "/" + poid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                if (ds.exists()) {
                    sPO = ds.getValue(ServicePO.class);
                    System.out.println("nn: " + sPO.getCustName());
                }
            }

            @Override
            public void onCancelled(DatabaseError arg0) {

            }
        });
        return sPO;
    }

    public static void main(String[] args) {
        getServicePOFromPOId aaa = new getServicePOFromPOId("-LKp4tFxfUHuS2NAJk3O", "ddsf2");
        ServicePO po = aaa.getPO();
        System.out.println("ss: " + po.getItems().size());
    }
}
