/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.servicePO;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mileset.neeleshengineers.controller.servicePO.getLabourInvoiceFromCustId;
import com.mileset.neeleshengineers.controller.servicePO.getServicePOFromCustID;
import com.mileset.neeleshengineers.controller.servicePO.labourCustIDCallBack;
import com.mileset.neeleshengineers.controller.servicePO.servicePOCallBack;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.report.dao.LabourReportNew;
import com.mileset.neeleshengineers.report.dao.LabourReportWithChallan;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rahul
 */
public class ViewAllServicePO extends javax.swing.JDialog {

    String custId;
    ArrayList<ServicePO> servicePOValue;

    public ViewAllServicePO(String custId) {
        super();
        this.custId = custId;
        initComponents();
        jMenuItem5.setVisible(false);
        this.custId = custId;
        this.jLabel1.setText("" + UtilVars.getCustomer().getName());

        getServicePOFromCustID getSPO = new getServicePOFromCustID(custId);
        getSPO.fetch(new servicePOCallBack() {

            @Override
            public void onCallback(ArrayList<ServicePO> value) {
                servicePOValue = value;
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i = 0; i < value.size(); i++) {
                    ServicePO st = value.get(i);
                    System.out.println("swap" + st.getItems().size());
                    model.addRow(new Object[]{st.getPoid(), st.getItems().size(), st.getDelivery_date()});
                }
            }

            @Override
            public void recordNotPresent() {
            }
        });
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        jMenuItem10.setText("jMenuItem10");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("View All Service PO");
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("jLabel1");

        jTable1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PO Id", "PO Total Items", "PO Delivery Date"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jMenu1.setText("Service PO");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem1.setText("Edit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem2.setText("Delete");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Challan");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem3.setText("Add New Challan");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem4.setText("View Challan");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem5.setText("Edit Challan");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuItem6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem6.setText("Remove Challan");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Labour Invoice");
        jMenu3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jMenuItem7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem7.setText("Add New Labour Invoice");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem8.setText("Edit Labour Invoice");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);

        jMenuItem9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem9.setText("Remove Labour Invoice");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem9);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Service PO Report");
        jMenu4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jCheckBoxMenuItem2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jCheckBoxMenuItem2.setText("Invoice Report");
        jCheckBoxMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jCheckBoxMenuItem2);

        jMenuItem11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jMenuItem11.setText("With Challan Report");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Selec PO to delete");
            return;
        }
        String poId = jTable1.getValueAt(rowIndex, 0) + "";
        deleteServicePO(poId);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }

        EditServicePO edit = new EditServicePO(custId, temp);
        this.setVisible(false);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed

        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }

        AddNewChallan newChallan = new AddNewChallan(temp);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }
        System.out.println("poid: " + temp.getPoid());
        System.out.println("poid: " + temp.getPoid());
        ViewAllChallan viewCh = new ViewAllChallan(temp, 0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }

        ViewAllChallan viewCh = new ViewAllChallan(temp, 2);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }

        ViewAllChallan viewCh = new ViewAllChallan(temp, 1);

    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        ServicePO temp = getSelectedPO();
        if (temp == null) {
            JOptionPane.showMessageDialog(this, "Select PO");
            return;
        }

        ViewAllChallan viewCh = new ViewAllChallan(temp, 3);
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        ServicePO temp = getSelectedPO();
        ViewAllLabourList viewAll = new ViewAllLabourList(custId, temp);

    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        ServicePO temp = getSelectedPO();
        ViewAllLabourList viewAll = new ViewAllLabourList(custId, temp);

    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jCheckBoxMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem2ActionPerformed

        final ServicePO temp = getSelectedPO();

        getLabourInvoiceFromCustId glifc = new getLabourInvoiceFromCustId(custId);

        JFileChooser chooser = new JFileChooser();
        // chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            final String file_path = chooser.getSelectedFile().getAbsolutePath();
            glifc.fetch(new labourCustIDCallBack() {
                @Override
                public void callBack(ArrayList<LaborInvoiceDetails> value) {
                    LabourReportNew l = new LabourReportNew(temp, value, file_path);
                }

                @Override
                public void recordNotPresent() {

                }
            });

        } else {

        }


    }//GEN-LAST:event_jCheckBoxMenuItem2ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        final ServicePO temp = getSelectedPO();
        getLabourInvoiceFromCustId glifc = new getLabourInvoiceFromCustId(custId);

        JFileChooser chooser = new JFileChooser();
        // chooser.setCurrentDirectory(new File("/home/me/Documents"));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            final String file_path = chooser.getSelectedFile().getAbsolutePath();

            glifc.fetch(new labourCustIDCallBack() {
                @Override
                public void callBack(ArrayList<LaborInvoiceDetails> value) {
                    LabourReportWithChallan lbr = new LabourReportWithChallan(temp, value, custId, file_path);
                }

                @Override
                public void recordNotPresent() {

                }
            });

        } else {
//no file selected
        }


    }//GEN-LAST:event_jMenuItem11ActionPerformed
    public ServicePO getSelectedPO() {
        int rowIndex = jTable1.getSelectedRow();
        if (rowIndex == -1) {
            JOptionPane.showMessageDialog(this, "Select PO");
            // return;
        }
        String poId = jTable1.getValueAt(rowIndex, 0) + "";
        ServicePO temp = null;
        for (int i = 0; i < servicePOValue.size(); i++) {
            ServicePO ser = servicePOValue.get(i);
            if (ser.getPoid().equals(poId)) {
                temp = ser;
                break;
            }
        }
        return temp;
    }

    public void deleteServicePO(String id) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("servicePO").child(custId);
        mRef.child(id).setValue(null, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                JOptionPane.showMessageDialog(null, "Service PO deleted successfully");
                close();
                //System.out.println("id deleted " + id);
            }
        });
    }

    public void close() {
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
