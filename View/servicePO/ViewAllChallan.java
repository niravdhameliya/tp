/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.servicePO;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mileset.neeleshengineers.controller.servicePO.alChallanCallBack;
import com.mileset.neeleshengineers.controller.servicePO.getCHallanFromPoId;
import com.mileset.neeleshengineers.modal.Challan;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.util.UtilVars;
import java.util.ArrayList;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rahul
 */
public class ViewAllChallan extends javax.swing.JDialog {

    /**
     * Creates new form ViewAllChallan
     */
    String poid, custId;
    int option;
    ServicePO po;
    ArrayList<Challan> challanList;

    public ViewAllChallan(ServicePO po, int id) {
        this.po = po;
        this.poid = po.getPoid();
        this.option = id;
        this.custId = UtilVars.getCustomer().getId();

        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        jButton1.setVisible(false);
        jButton2.setVisible(false);
        jButton3.setVisible(false);
        jLabel1.setText(UtilVars.getCustomer().getName() + "------" + poid);
        getCHallanFromPoId challan = new getCHallanFromPoId(poid, custId);
        challan.fetch(new alChallanCallBack() {
            @Override
            public void onCallback(ArrayList<Challan> value) {
                challanList = value;
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i = 0; i < value.size(); i++) {
                    Challan st = value.get(i);
                    model.addRow(new Object[]{st.getChallanNo(), st.getChallanItemsList().size(), st.getDate()});
                }
            }

            @Override
            public void recordNotPresent() {

            }

            @Override
            public void onCallback1(Challan value) {

            }
        });
        options();
        this.setVisible(true);
    }

    public void options() {
        switch (option) {
            case 0:
                this.setTitle("View All Challan");
                break;
            case 1:
                this.setTitle("Select Challan To Edit");
                jButton1.setVisible(true);
                break;
            case 2:
                this.setTitle("Select Challan To Delete");
                jButton2.setVisible(true);
                break;
            case 3:
                this.setTitle("Select Challan For Labour Invoice");
                jButton3.setVisible(true);
                break;

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View All Challan");
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("jLabel1");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Challan Number", "Challan Total Items", "Challan Delivery Date"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Edit Challan");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Delete Challan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Create Labour Invoice");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2)
                                .addGap(32, 32, 32)
                                .addComponent(jButton3)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select Challan to delete");
            return;
        }
        String challanId = jTable1.getValueAt(rowIndex, 0) + "";
        deleteRow(challanId);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select Challan to delete");
            return;
        }
        EditChallan edit = new EditChallan(po, challanList.get(rowIndex));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select Challan");
            return;
        }

//AddNewLabourInvoice newLabuorInvoice = new AddNewLabourInvoice(challanList.get(rowIndex), po);
        NewLabourInvoice newLabuorInvoice = new NewLabourInvoice(challanList.get(rowIndex), po);

    }//GEN-LAST:event_jButton3ActionPerformed
    public void deleteRow(String challanId) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("SPOChallan").child(custId).child(poid);
        mRef.child(challanId).setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                JOptionPane.showMessageDialog(null, "Challan deleted successfully");
                close();
                //System.out.println("id deleted " + id);
            }
        });
    }

    public void close() {
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
