/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Invoice;

//import View.Report.GeneratePDF;
//import View.Report.TestPDF;
//import controller.Database.Fetch.getInvoiceDataFromInvoiceId;
import com.mileset.neeleshengineers.View.Report.ConstructorBill;
import com.mileset.neeleshengineers.View.Report.TaxInvoice;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.util.NumberToWord;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
//import model.Customer.Invoice;

//import modal.Invoice.Invoice_details;
import modal.Invoice.Path;
//import modal.operations.NumberToWordsConverter;

/**
 *
 * @author rahul
 */
public class InvoiceDetailsFromInvoiceId extends javax.swing.JDialog {

    /**
     * Creates new form InvoiceDetailsFromInvoiceId
     */
    String cust_name;
    int invoice_id;
    //getInvoiceDataFromInvoiceId invDataList;
    ArrayList<Invoice_details> invoiceDetailsList;
    String[] str;
    ArrayList<String[]> strList;
    String cust_gstin;
    String cust_address;
    String vendorCode, vehicleNo, dte2, PONo;
    Invoice_details invoice_details;

//    public InvoiceDetailsFromInvoiceId(java.awt.Frame parent, boolean modal, String cust_name, int invoice_id, String cust_gstin, String cust_address) {
//        super(parent, modal);
//        initComponents();
//        this.invoice_id = invoice_id;
//        this.cust_name = cust_name;
//        this.cust_gstin = cust_gstin;
//        this.cust_address = cust_address;
//        strList = new ArrayList<>();
//        jLabel2.setText(cust_name);
//        jLabel1.setText("Invoice Id: 000" + invoice_id);
//
//        jTextField1.setText("Nos");
//
//        //invDataList = new getInvoiceDataFromInvoiceId(invoice_id);
//        //invDataList.fetch();
//        //invoiceDetailsList = invDataList.getInvoiceDetailsList();
//
//        for (int i = 0; i < invoiceDetailsList.size(); i++) {
//            jLabel3.setText("Date: " + invoiceDetailsList.get(i).getDate());
//            jLabel6.setText("Basic Total: " + invoiceDetailsList.get(i).getBasicTotal());
//            jLabel7.setText("Other Charges: " + invoiceDetailsList.get(i).getOthercharges());
//            jLabel8.setText("CGST: " + invoiceDetailsList.get(i).getCgst());
//            jLabel9.setText("SGST: " + invoiceDetailsList.get(i).getSgst());
//            jLabel10.setText("IGST: " + invoiceDetailsList.get(i).getIgst());
//
//            vehicleNo = invoiceDetailsList.get(i).getVehicleNo();
//            vendorCode = invoiceDetailsList.get(i).getVendorCode();
//            dte2 = invoiceDetailsList.get(i).getDte2();
//            PONo = invoiceDetailsList.get(i).getPONo();
//
//            jLabel4.setText("Grand Total: " + invoiceDetailsList.get(i).getGrandTotal());
//            //String words = NumberToWordsConverter.convert((int) invoiceDetailsList.get(i).getGrandTotal());
//            //jLabel5.setText("In Words: " + words + " Only");
//
//            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
//
//            Vector row = new Vector();
//            
//            
//            row.add(invoiceDetailsList.get(i).getItemList());
//            row.add(invoiceDetailsList.get(i).getItemList().getHsn());
//            row.add(invoiceDetailsList.get(i).getQuantity());
//            row.add(invoiceDetailsList.get(i).getUnit_rate());
//            row.add(invoiceDetailsList.get(i).getAmount());
//            str = new String[5];
//            str[0] = invoiceDetailsList.get(i).getItem_name();
//            str[1] = invoiceDetailsList.get(i).getHsn();
//            str[2] = "" + invoiceDetailsList.get(i).getQuantity();
//            str[3] = "" + invoiceDetailsList.get(i).getUnit_rate();
//            str[4] = "" + invoiceDetailsList.get(i).getAmount();
//
//            strList.add(str);
//
//            model.addRow(row);
//
//        }
//
//    }
    public InvoiceDetailsFromInvoiceId(Invoice_details invoice_details, Customer customer) {
        initComponents();
        this.invoice_details = invoice_details;
        jTable1.setFont(new Font("Serif", Font.PLAIN, 24));
        jTable1.setRowSelectionAllowed(true);

        System.out.println("Here is selected customer " + customer);
        System.out.println(invoice_details.getBasicTotal());
        lbl_basictotal.setText(lbl_basictotal.getText() + " " + invoice_details.getBasicTotal());
        lbl_CustName.setText(lbl_CustName.getText() + " " + invoice_details.getCname());
        lbl_PONo.setText(lbl_PONo.getText() + " " + invoice_details.getPONo());
        lbl_cgst.setText(lbl_cgst.getText() + " " + invoice_details.getCgst() + " %");
        lbl_sgst.setText(lbl_sgst.getText() + " " + invoice_details.getSgst() + " %");
        lbl_igst.setText(lbl_igst.getText() + " " + invoice_details.getIgst() + " %");
        lbl_invoiceid.setText(lbl_invoiceid.getText() + " " + invoice_details.getInvoice_id());
        lbl_date.setText(lbl_date.getText() + " " + invoice_details.getDate());
        lbl_othercharges.setText(lbl_othercharges.getText() + " " + invoice_details.getOthercharges());
        lbl_totalamount.setText(lbl_totalamount.getText() + " " + invoice_details.getGrandTotal());
        Double d = new Double(invoice_details.getGrandTotal());

        jLabel5.setText(jLabel5.getText() + " " + NumberToWord.NumberToWord(d.intValue() + ""));
        ArrayList<Items> items = invoice_details.getItemList();
        for (Items i : items) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(i.getItem_name());
            row.add(i.getHsn());
            row.add(i.getQuantity());
            row.add(i.getUnit_rate());
            row.add(i.getAmount());
            model.addRow(row);
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
        lbl_invoiceid = new javax.swing.JLabel();
        lbl_CustName = new javax.swing.JLabel();
        lbl_date = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lbl_basictotal = new javax.swing.JLabel();
        lbl_othercharges = new javax.swing.JLabel();
        lbl_cgst = new javax.swing.JLabel();
        lbl_sgst = new javax.swing.JLabel();
        lbl_igst = new javax.swing.JLabel();
        lbl_totalamount = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        lbl_PONo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Show Invoice");
        setModal(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ItemName", "HSN", "Quantity", "Unit Price", "Amount"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        lbl_invoiceid.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_invoiceid.setText("Invoice Id: ");

        lbl_CustName.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_CustName.setText("Customer Name");

        lbl_date.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_date.setText("Date: ");

        lbl_basictotal.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_basictotal.setText("Basic Total");

        lbl_othercharges.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_othercharges.setText("Other Charges");

        lbl_cgst.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_cgst.setText("CGST");

        lbl_sgst.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_sgst.setText("SGST");

        lbl_igst.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_igst.setText("IGST");

        lbl_totalamount.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_totalamount.setText("Total Amount");

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jLabel5.setText("In Words:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_othercharges, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_cgst, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_igst, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                            .addComponent(lbl_basictotal, javax.swing.GroupLayout.PREFERRED_SIZE, 458, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_totalamount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 214, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_basictotal, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_sgst)
                    .addComponent(lbl_othercharges)
                    .addComponent(lbl_cgst)
                    .addComponent(lbl_igst))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_totalamount, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        jButton1.setText("Print");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lbl_PONo.setFont(new java.awt.Font("Times New Roman", 1, 30)); // NOI18N
        lbl_PONo.setText("Purchase Order No.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbl_invoiceid, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lbl_CustName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_PONo, javax.swing.GroupLayout.PREFERRED_SIZE, 635, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_invoiceid, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_date, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(lbl_CustName, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbl_PONo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        TaxInvoice tax = new TaxInvoice(invoice_details);
        ConstructorBill constBill = new ConstructorBill(invoice_details);
    }//GEN-LAST:event_jButton1ActionPerformed

    public String getValue(String sss) {
        int index = sss.indexOf(":");
        String v = sss.substring(index + 1);
        return v.trim();
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
            java.util.logging.Logger.getLogger(InvoiceDetailsFromInvoiceId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoiceDetailsFromInvoiceId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoiceDetailsFromInvoiceId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoiceDetailsFromInvoiceId.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*InvoiceDetailsFromInvoiceId dialog = new InvoiceDetailsFromInvoiceId(new javax.swing.JFrame(), true);
                 dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                 @Override
                 public void windowClosing(java.awt.event.WindowEvent e) {
                 System.exit(0);
                 }
                 });
                 dialog.setVisible(true);*/
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_CustName;
    private javax.swing.JLabel lbl_PONo;
    private javax.swing.JLabel lbl_basictotal;
    private javax.swing.JLabel lbl_cgst;
    private javax.swing.JLabel lbl_date;
    private javax.swing.JLabel lbl_igst;
    private javax.swing.JLabel lbl_invoiceid;
    private javax.swing.JLabel lbl_othercharges;
    private javax.swing.JLabel lbl_sgst;
    private javax.swing.JLabel lbl_totalamount;
    // End of variables declaration//GEN-END:variables
}
