/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Invoice;

import com.mileset.neeleshengineers.View.Customer.ShowAllCustomers;
import com.mileset.neeleshengineers.View.Report.ConstructorBill;
import com.mileset.neeleshengineers.View.Report.TaxInvoice;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.util.AdminPassword;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahul
 */
public class ShowAllInvoicesByPoid1 extends javax.swing.JDialog {

    /**
     * Creates new form ShowAllInvoicesByPoid1
     */
    private Customer customer;
    private ArrayList<Invoice_details> invoices;

    public ShowAllInvoicesByPoid1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
    }

    public ShowAllInvoicesByPoid1(final ArrayList<Invoice_details> invoices, final Customer customer) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 23));
        this.invoices = invoices;
        ShowwAllInvoicesByPoid1Vars.setInvoices(invoices);
        ShowwAllInvoicesByPoid1Vars.setCustomer(customer);
        loadInvoiceToJTable(invoices);
        jLabel2.setText(customer.getName());
        jLabel1.setText(jLabel1.getText() + " " + invoices.get(0).getPONo());
        this.setTitle(jLabel1.getText());
        jTable1.setCellSelectionEnabled(false);
        jTable1.setRowSelectionAllowed(true);
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    Invoice_details invoice_details = getSelectedInvoice(invoices);
                    System.out.println(invoice_details);
                    InvoiceDetailsFromInvoiceId detailsFromInvoiceId = new InvoiceDetailsFromInvoiceId(invoice_details, customer);
                    detailsFromInvoiceId.setVisible(true);
                }
            }
        });
    }

    private Invoice_details getSelectedInvoice(ArrayList<Invoice_details> invoices) {
        Invoice_details invoice_details = new Invoice_details();
        String id = null;
        try {
            id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please Select Invoice");
        }
        for (Invoice_details invoice_detail : invoices) {
            if (invoice_detail.getInvoice_id().equalsIgnoreCase(id)) {
                invoice_details = invoice_detail;
                break;
            }
        }
        return invoice_details;
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnGenerateLbrInvoice = new javax.swing.JButton();
        btn_deleteInvoice = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice id", "Po No", "Date", "Items", "basic total", "Grand total"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Purchase Order No:");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Customer Name:");

        btnGenerateLbrInvoice.setText("Generate labor invoice");
        btnGenerateLbrInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateLbrInvoiceActionPerformed(evt);
            }
        });

        btn_deleteInvoice.setText("Delete Invoice");
        btn_deleteInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteInvoiceActionPerformed(evt);
            }
        });

        jButton1.setText("Edit Invoice");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("PDF");
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
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnGenerateLbrInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_deleteInvoice)
                        .addGap(32, 32, 32)
                        .addComponent(jButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jButton2))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 537, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGenerateLbrInvoice)
                    .addComponent(btn_deleteInvoice)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGenerateLbrInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateLbrInvoiceActionPerformed
        // TODO add your handling code here:
        UtilVars.setRequiredInvoiceDetails(getSelectedInvoice(invoices));
        this.dispose();
        FirebaseMethods.generateLaborInvoiceId(this, false);
    }//GEN-LAST:event_btnGenerateLbrInvoiceActionPerformed

    private void btn_deleteInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteInvoiceActionPerformed
        // TODO add your handling code here:
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            if (AdminPassword.getStr().equals(password)) {
                final Invoice_details invoice = getSelectedInvoice(invoices);
                final ProgressDialogFrame pd = new ProgressDialogFrame("Deleting...");
                pd.setVisible(true);
                final JDialog myjd = this;
                Thread worker = new Thread() {
                    public void run() {
                        try {

                            FirebaseMethods.deleteInvoice(invoice, myjd);

                        } catch (Exception ex) {
                            System.out.println("Exception occured " + ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                                model.removeRow(jTable1.getSelectedRow());
                                pd.setVisible(false);
                                JOptionPane.showMessageDialog(myjd, "Invoice Deleted...");
                                pd.dispose();
                            }
                        });
                    }
                };
                worker.start();

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btn_deleteInvoiceActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Invoice_details invoice = getSelectedInvoice(invoices);
        NewInvoice1 updateInvoice = new NewInvoice1(invoice, invoices);
        updateInvoice.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        Invoice_details invoice1 = getSelectedInvoice(invoices);
        TaxInvoice invoice = new TaxInvoice(invoice1);
        ConstructorBill bill = new ConstructorBill(invoice1);
        JOptionPane.showMessageDialog(this, "Pdf Generated");

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
            java.util.logging.Logger.getLogger(ShowAllInvoicesByPoid1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowAllInvoicesByPoid1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowAllInvoicesByPoid1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowAllInvoicesByPoid1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowAllInvoicesByPoid1 dialog = new ShowAllInvoicesByPoid1(new javax.swing.JFrame(), true);
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

    private void loadInvoiceToJTable(ArrayList<Invoice_details> data1) {
        System.out.println("setToTable called");
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1.size());
        for (int i = 0; i < data1.size(); i++) {
            //  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(data1.get(i).getInvoice_id());
            row.add(data1.get(i).getPONo());
            row.add(data1.get(i).getDate());
            row.add(data1.get(i).getItemList().size());
            row.add(data1.get(i).getBasicTotal());
            row.add(data1.get(i).getGrandTotal());

            //model.addRow(row);
            //dm.addRow(row);
            dm.addRow(row);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerateLbrInvoice;
    private javax.swing.JButton btn_deleteInvoice;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
