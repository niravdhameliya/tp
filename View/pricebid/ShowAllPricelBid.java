/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.pricebid;

import com.mileset.neeleshengineers.View.technicalbid.AddNewTechnicalBid;
import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import com.mileset.neeleshengineers.modal.pricebid.PriceBidItems;
import com.mileset.neeleshengineers.modal.quatation.QuatationData;
import com.mileset.neeleshengineers.modal.technicalbid.TechnicalBid;
import com.mileset.neeleshengineers.util.AdminPassword;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahul
 */
public class ShowAllPricelBid extends javax.swing.JDialog {

    /**
     * Creates new form ShowAllPriceBid
     */
    public ShowAllPricelBid(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));

    }

    private ArrayList<PriceBid> data;

    public ShowAllPricelBid(ArrayList<PriceBid> data) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));

        this.data = data;
        //System.out.println("data got " + data);
        loadDataToTable(data);
    }

    private void loadDataToTable(ArrayList<PriceBid> data1) {
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1.size());
        for (int i = 0; i < data1.size(); i++) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(data1.get(i).getQtnNo());
            row.add(data1.get(i).getDate());
            row.add(data1.get(i).getClientName());
            row.add(data1.get(i).getPriceBidItems().size());
            model.addRow(row);

        }
    }

    private PriceBid getSelectedPbObject(ArrayList<PriceBid> polist) {
        PriceBid pb = null;
        String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        for (PriceBid q1 : data) {
            if (q1.getQtnNo().equalsIgnoreCase(id)) {
                pb = q1;
                break;
            }
        }
        return pb;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_updatePriceBid = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quatations Id", "Bill Date", "Customer Name", "No Of Items"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btn_updatePriceBid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_updatePriceBid.setText("Update Price Bid");
        btn_updatePriceBid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePriceBidActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Delete Price Bid");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_updatePriceBid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(474, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(250, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updatePriceBid)
                    .addComponent(jButton1))
                .addGap(19, 19, 19))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(58, 58, 58)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(58, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_updatePriceBidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePriceBidActionPerformed
        // TODO add your handling code here:
        getSelectedPriceBid();
    }//GEN-LAST:event_btn_updatePriceBidActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:

        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            if (AdminPassword.getStr().equals(password)) {
                final String id = getSelectedId();
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(this, "Please Select A Quatation");
                } else {
                    PriceBid qdta = getSelectedPbObject(data);
                    final JDialog jd = this;
                    final String qfid = qdta.getFid();
                    final ProgressDialogFrame pd = new ProgressDialogFrame("Deleting...");
                    pd.setVisible(true);
                    Thread worker = new Thread() {
                        public void run() {
                            try {
                                FirebaseMethods.deletePb(qfid, jd);

                            } catch (Exception ex) {
                                System.out.println("Exception occured " + ex);
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    //resultLabel.setText("Finished");
                                    pd.setVisible(false);
                                    pd.dispose();
                                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                                    model.removeRow(jTable1.getSelectedRow());
                                    JOptionPane.showMessageDialog(jd, "PriceBid Deleted");
                                }
                            });
                        }
                    };
                    worker.start();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void getSelectedPriceBid() {
        PriceBid foundPriceBid = null;
        String val = getSelectedId();
        if (val.equals("")) {
            JOptionPane.showMessageDialog(this, "Please select Bid to update");
        } else {
            System.out.println("ShowAllPriceBid :- selected quatation id is " + val);
            System.out.println("ShowAllPriceBid :- Searching for Price Bid from quatation id " + val);
            for (PriceBid priceBid : data) {
                System.out.println("ShowAllPriceBid :- Current PriceBid is " + priceBid);
                if (priceBid.getQtnNo().equals(val)) {
                    foundPriceBid = priceBid;
                    foundPriceBid.setPriceBidItems(priceBid.getPriceBidItems());
                    System.out.println("ShowAllPriceBid :- PriceBid found for Quatation Id ");
                    break;
                }

            }
            UtilVars.setPriceBid(foundPriceBid);
            ArrayList<PriceBidItems> myitem = foundPriceBid.getPriceBidItems();
            for (PriceBidItems item : myitem) {
                System.out.println("****************************");
                System.out.println(item.getBasic_rate());
                System.out.println(item.getCgst());
                System.out.println(item.getFreight());
                System.out.println(item.getIgst());
                System.out.println(item.getNomenclature());
                System.out.println(item.getQuantity());
                System.out.println(item.getTotal());
                System.out.println("****************************");
            }
            ShowAllPriceBidVars.setData(data);
            this.dispose();
            AddNewPriceBid addNewPriceBid = new AddNewPriceBid(foundPriceBid.getPriceBidItems(), true);
            addNewPriceBid.setVisible(true);
        }
    }

    public String getSelectedId() {
        String val = "";
        try {
            int row = jTable1.getSelectedRow();
            val = jTable1.getValueAt(row, 0) + "";

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return val;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ShowAllPricelBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowAllPricelBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowAllPricelBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowAllPricelBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowAllPricelBid dialog = new ShowAllPricelBid(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_updatePriceBid;
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
