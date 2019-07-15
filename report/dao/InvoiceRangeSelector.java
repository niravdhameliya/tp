package com.mileset.neeleshengineers.report.dao;

import com.mileset.neeleshengineers.controller.servicePO.getLabourInvoiceFromCustId;
import com.mileset.neeleshengineers.controller.servicePO.labourCustIDCallBack;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.util.CurrentDate;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.Utility;
import com.mileset.neeleshengineers.util.checkDateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rahul
 */
public class InvoiceRangeSelector extends javax.swing.JDialog {

    ArrayList<String> cId;
    ArrayList<String> gstinList;
    public static HashMap<String, String> gstInCustIdMap;

    /**
     * Creates new form InvoiceRangeSelector
     */
    public InvoiceRangeSelector(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public InvoiceRangeSelector() {
        initComponents();
        cName.removeAllItems();
        txt_fromDate.setText(CurrentDate.getDate());
        txt_toDate.setText(CurrentDate.getDate());
        gstInCustIdMap = Utility.gstInCustIdMap;
        ArrayList<String> cname = Utility.cname;
        gstinList = Utility.gstin;
        cId = Utility.cId;
        for (String name : cname) {
            cName.addItem(name.replace("\n", " "));
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
        txt_fromDate = new javax.swing.JTextField();
        txt_toDate = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cName = new javax.swing.JComboBox<String>();
        jCheckBox1 = new javax.swing.JCheckBox();
        btn_generateReport = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Generate Report");
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel1.setText("From Date(dd/MM/yyyy)");

        txt_fromDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        txt_fromDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_fromDateActionPerformed(evt);
            }
        });

        txt_toDate.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jLabel2.setText("To Date(dd/MM/yyyy)");

        cName.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        cName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jCheckBox1.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        jCheckBox1.setText("All Customers");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        btn_generateReport.setFont(new java.awt.Font("Times New Roman", 0, 18)); // NOI18N
        btn_generateReport.setText("Generate Report");
        btn_generateReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateReportActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(cName, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
                                .addComponent(txt_toDate, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(216, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(226, 226, 226))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btn_generateReport)
                        .addGap(208, 208, 208))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_fromDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_toDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_generateReport)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_fromDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_fromDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_fromDateActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void btn_generateReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generateReportActionPerformed
        // TODO add your handling code here:
        String d1 = txt_fromDate.getText();
        String d2 = txt_toDate.getText();
        if (checkDateFormat.isValidFormat(d1)) {
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Start Date Format. Eg: 05/03/2019");
            return;
        }
        if (checkDateFormat.isValidFormat(d2)) {
        } else {
            JOptionPane.showMessageDialog(this, "Invalid Invalid Date Format. Eg: 05/03/2019");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fromDate = null;
        Date toDate = null;
        try {
            fromDate = sdf.parse(d1);
            toDate = sdf.parse(d2);
        } catch (ParseException ex) {
            System.out.println("Prasing Exception");
        }
        if (fromDate != null && toDate != null) {
            getAllLabourInvoice(fromDate, toDate);
            //  JOptionPane.showMessageDialog(this, "Report Generated");
        }
    }//GEN-LAST:event_btn_generateReportActionPerformed

    public void getAllLabourInvoice(final Date from, final Date to) {
        final ArrayList<String> custId = new ArrayList();
        final ArrayList<LaborInvoiceDetails> invoice_details = new ArrayList<>();
        if (jCheckBox1.isSelected()) {
//            custId = Utility.cId;
            for (int h = 0; h < Utility.cId.size(); h++) {
                custId.add(Utility.cId.get(h));
            }
        } else {
            custId.add(cId.get(cName.getSelectedIndex()));
        }
        for (int y = 0; y < custId.size(); y++) {
            final String cid = custId.get(y);
            getLabourInvoiceFromCustId getLabpurInvoice = new getLabourInvoiceFromCustId(cid);
            getLabpurInvoice.fetch(new labourCustIDCallBack() {
                @Override
                public void callBack(final ArrayList<LaborInvoiceDetails> value) {
                    for (LaborInvoiceDetails lid : value) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date innerDate = null;
                        try {
                            innerDate = sdf.parse(lid.getDate());
                        } catch (ParseException ex) {
                            Logger.getLogger(FirebaseMethods.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if (innerDate.equals(to) || innerDate.equals(from)) {
                            System.out.println("lc found");
                            lid.setGSTIN(Utility.gstInCustIdMap.get(cid));
                            invoice_details.add(lid);
                        } else if ((innerDate.before(to) && innerDate.after(from))) {
                            System.out.println("lc found");
                            lid.setGSTIN(Utility.gstInCustIdMap.get(cid));
                            invoice_details.add(lid);
                        }
                    }
                }

                @Override
                public void recordNotPresent() {

                }
            });
        }

        try {
            Thread.sleep(10000);
            System.out.println("lc.size: " + invoice_details.size());
            ArrayList<B2BModel> b2blist = new ArrayList();
            for (int i = 0; i < invoice_details.size(); i++) {
                double cgst = 0, sgst = 0, igst = 0;
                cgst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getCgst();
                sgst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getSgst();
                igst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getIgst();
                B2BModel b1 = new B2BModel();
                System.out.println("Object created");
                System.out.println("lc setting 1 " + invoice_details.get(i).getInvoice_id());
                b1.setDate(invoice_details.get(i).getDate());
                System.out.println("lc setting 2" + invoice_details.get(i).getCname());
                //b1.setGstInNo(Utility.getGstIn(invoice_details.get(i).getCname().replace("\n", " ")));
                b1.setGstInNo(invoice_details.get(i).getGSTIN());
                System.out.println("lc setting 3");
                b1.setInvoiceId(invoice_details.get(i).getInvoice_id());
                System.out.println("lc setting 4");
                b1.setInvoiceValue(invoice_details.get(i).getBasicTotal());
                System.out.println("lc setting 5");
                //b1.setPlaceOfSupply(Utility.getPlaceOfSupply(invoice_details.get(i).getCname().replace("\n", " ")));
                System.out.println("lc setting 6");
                b1.setInvoiceType("Labour Invoice");
                System.out.println("lc setting 7");
                b1.setTaxableValue((int) (cgst + sgst + igst + invoice_details.get(i).getBasicTotal() + invoice_details.get(i).getOthercharges()));
                System.out.println("setting 8");
                b1.setHsn(invoice_details.get(i).getSac());
                b2blist.add(b1);
                System.out.println(b1);
            }

            FirebaseMethods.getAllInvoicesForReport(this, from, to, jCheckBox1.isSelected(), Utility.gstInCustIdMap.get(cId.get(cName.getSelectedIndex())), b2blist);
            //FirebaseMethods.getAllInvoicesForReport(this, from, to, jCheckBox1.isSelected(), cName.getSelectedItem().toString(), b2blist);
        } catch (InterruptedException ex) {
            Logger.getLogger(InvoiceRangeSelector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InvoiceRangeSelector.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            java.util.logging.Logger.getLogger(InvoiceRangeSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InvoiceRangeSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InvoiceRangeSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InvoiceRangeSelector.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InvoiceRangeSelector dialog = new InvoiceRangeSelector(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_generateReport;
    private javax.swing.JComboBox<String> cName;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField txt_fromDate;
    private javax.swing.JTextField txt_toDate;
    // End of variables declaration//GEN-END:variables
}