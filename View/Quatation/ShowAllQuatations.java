/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Quatation;

import com.mileset.neeleshengineers.View.Report.QuotationPDF;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.quatation.QuatationData;
import com.mileset.neeleshengineers.modal.quatation.QuatationItems;
import com.mileset.neeleshengineers.util.AdminPassword;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahul
 */
public class ShowAllQuatations extends javax.swing.JDialog {

    /**
     * Creates new form ShowAllQuatations
     */
    private ArrayList<QuatationData> quatationsList;

    public ShowAllQuatations(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTable1.setFont(new Font("Serif", Font.BOLD, 20));

        jTable1.getColumnModel().getColumn(3).setMaxWidth(0);

    }

    public ShowAllQuatations(ArrayList<QuatationData> quatations) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.BOLD, 20));
        this.quatationsList = quatations;
        loadDataToTable(quatations);

        jTable1.getColumnModel().getColumn(3).setMaxWidth(0);
    }

    private void loadDataToTable(ArrayList<QuatationData> data1) {
        try {
            DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
            while (dm.getRowCount() > 0) {
                dm.removeRow(0);
            }

            System.out.println("row:" + data1.size());
            for (int i = 0; i < data1.size(); i++) {
                //  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                Vector row = new Vector();
                row.add(data1.get(i).getQuatationId());
                row.add(data1.get(i).getBillDate());
                row.add(data1.get(i).getQuatationItemses().size());
                ArrayList<QuatationItems> quatationItemses12 = data1.get(i).getQuatationItemses();
                for (int r = 0; r < quatationItemses12.size(); r++) {
                    System.out.println("asd: " + quatationItemses12.get(r).getItemname());
                }

                row.add(data1.get(i).getCustId());
                dm.addRow(row);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnGeneratePDF = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Show All Quotations");
        setModal(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quatations Id", "Qtn. Date", "No Of Items", "Customer Id"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnGeneratePDF.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        btnGeneratePDF.setText("Generate Pdf");
        btnGeneratePDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGeneratePDFActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jButton1.setText("Delete Quotation");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jButton2.setText("Edit Quotation");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(btnGeneratePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnGeneratePDF, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGeneratePDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGeneratePDFActionPerformed
        QuatationData qdta = getSelectedQuatationObject(quatationsList);
        System.out.println("debug 2" + qdta.getBillDate());
        QuotationPDF quot = new QuotationPDF(qdta);
        JOptionPane.showMessageDialog(this, "Quotation PDF Generated.");
    }//GEN-LAST:event_btnGeneratePDFActionPerformed
    public String getSelectedId() {
        String val = "";
        try {
            int row = jTable1.getSelectedRow();

            val = jTable1.getValueAt(row, 0) + "";

        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            //JOptionPane.showMessageDialog(this,"Please select id to update");
        }
        return val;
    }

    private QuatationData getSelectedQuatationObject(ArrayList<QuatationData> polist) {
        QuatationData quatation = null;
        String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        for (QuatationData q1 : quatationsList) {
            if (q1.getQuatationId().equalsIgnoreCase(id)) {
                quatation = q1;
                break;
            }
        }
        return quatation;
    }
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
                    QuatationData qdta = getSelectedQuatationObject(quatationsList);
                    final JDialog jd = this;
                    final String qfid = qdta.getFid();
                    final ProgressDialogFrame pd = new ProgressDialogFrame("Deleting...");
                    pd.setVisible(true);
                    Thread worker = new Thread() {
                        public void run() {
                            try {
                                FirebaseMethods.deleteQuatation(qfid, jd);

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
                                    JOptionPane.showMessageDialog(jd, "Quatation Deleted");
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        QuatationData qdta = getSelectedQuatationObject(quatationsList);
        EditQuotation edit = new EditQuotation(qdta);
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(ShowAllQuatations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowAllQuatations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowAllQuatations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowAllQuatations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowAllQuatations dialog = new ShowAllQuatations(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGeneratePDF;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
