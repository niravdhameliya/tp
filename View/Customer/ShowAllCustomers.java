/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Customer;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mileset.neeleshengineers.View.PurchaseOrder.PurchaseOrder;
import com.mileset.neeleshengineers.View.SupplyOrder.AddNewSupplyOrder;
import com.mileset.neeleshengineers.View.pricebid.AddNewPriceBid;
import com.mileset.neeleshengineers.View.servicePO.AddNewPurchaseOrder;
import com.mileset.neeleshengineers.View.servicePO.ViewAllServicePO;
import com.mileset.neeleshengineers.View.technicalbid.AddNewTechnicalBid;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.pricebid.PriceBidItems;
import com.mileset.neeleshengineers.report.dao.InvoiceRangeSelector;
import com.mileset.neeleshengineers.util.AdminPassword;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import com.mileset.neeleshengineers.util.UtilVars;
import com.mileset.neeleshengineers.util.Utility;
import java.awt.Font;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author rahul
 */
public class ShowAllCustomers extends javax.swing.JInternalFrame {

    /**
     * Creates new form ShowAllCustomers
     */
    private TableRowSorter<TableModel> rowSorter;
    private FirebaseMethods firebaseMethods;
    private ArrayList<Customer> data;

    public ShowAllCustomers() {
        super("All Customers Records");
        initComponents();

        btn_getallpurchaseorders.setVisible(false);
        btn_printpurchaseorder.setVisible(false);
        btn_showallquatations.setVisible(false);
        btn_addNewPriceBid.setVisible(false);
        btn_addNewTechnicalBid.setVisible(false);
        btnAddNewSupplyOrder.setVisible(false);
        btn_addNewLaborInvoice.setVisible(false);
        btn_getAllPriceBid.setVisible(false);
        btn_getAllTechnicalBids.setVisible(false);
        jButton1.setVisible(false);
        firebaseMethods = new FirebaseMethods();
        jTextField1.setText("");
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(10).setPreferredWidth(50);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        jTable1.setRowSelectionAllowed(true);
        jTable1.setFont(new Font("Serif", Font.BOLD, 22));
        tbl_powarning.setFont(new Font("Serif", Font.BOLD, 22));
        jTable1.setDefaultEditor(Object.class, null);

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(rowSorter);
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = jTextField1.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = jTextField1.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        });

//        this.setVisible(true);
//        boolean val = Utility.isNetIsAvailable();
//        if (val) {
//            setToTable();
//        } else {
//            JOptionPane.showMessageDialog(this, "Internet connection Needed.");
//            this.dispose();
//
//        }
        final ProgressDialogFrame pd = new ProgressDialogFrame("Loading...");
        pd.setVisible(true);
        Thread worker = new Thread() {
            public void run() {
                try {
                    data = FirebaseMethods.getAllCustomers(jTable1);

                    FirebaseMethods.getWarningPo(tbl_powarning);

                } catch (Exception ex) {
                    System.out.println("Exception occured " + ex);
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            //resultLabel.setText("Finished");
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ShowAllCustomers.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        pd.setVisible(false);
                        pd.dispose();
                        System.out.println("This is all customer data data " + data);
                    }
                });
            }
        };
        worker.start();
        addChildEventListner();
        addChildEventListnerForPoWarning();
    }

    public String getSelectedCustName() {
        String val = "";
        try {
            int row = jTable1.getSelectedRow();

            val = jTable1.getValueAt(row, 1) + "";

        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return val;
    }

    public String getSelectedId() {
        String val = "";
        try {
            int row = jTable1.getSelectedRow();

            val = jTable1.getValueAt(row, 0) + "";

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please select Customer first");
            ex.printStackTrace();

        }
        return val;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btn_printpurchaseorder = new javax.swing.JButton();
        btn_getallpurchaseorders = new javax.swing.JButton();
        btn_showallquatations = new javax.swing.JButton();
        btn_addNewPriceBid = new javax.swing.JButton();
        btn_addNewTechnicalBid = new javax.swing.JButton();
        btn_getAllPriceBid = new javax.swing.JButton();
        btn_getAllTechnicalBids = new javax.swing.JButton();
        btnAddNewSupplyOrder = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_powarning = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btn_addNewLaborInvoice = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu8 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jTextField1.setText("jTextField1");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel1.setText("Search");

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Name", "GSTIN", "Address", "Contact", "Email", "Vendor Code", "Pan Number", "CGST", "SGST", "IGST"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btn_printpurchaseorder.setText("Get Single Purchase Order");
        btn_printpurchaseorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_printpurchaseorderActionPerformed(evt);
            }
        });

        btn_getallpurchaseorders.setText("Get All Purchase Orders");
        btn_getallpurchaseorders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getallpurchaseordersActionPerformed(evt);
            }
        });

        btn_showallquatations.setText("Show All Quatations");
        btn_showallquatations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showallquatationsActionPerformed(evt);
            }
        });

        btn_addNewPriceBid.setText("Add New Price Bid");
        btn_addNewPriceBid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addNewPriceBidActionPerformed(evt);
            }
        });

        btn_addNewTechnicalBid.setText("Add New Technical Bid");
        btn_addNewTechnicalBid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addNewTechnicalBidActionPerformed(evt);
            }
        });

        btn_getAllPriceBid.setText("Show All Price Bids");
        btn_getAllPriceBid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getAllPriceBidActionPerformed(evt);
            }
        });

        btn_getAllTechnicalBids.setText("Show All Technical Bids");
        btn_getAllTechnicalBids.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_getAllTechnicalBidsActionPerformed(evt);
            }
        });

        btnAddNewSupplyOrder.setText("Add New Supply Order");
        btnAddNewSupplyOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewSupplyOrderActionPerformed(evt);
            }
        });

        jButton1.setText("Show All Supply Order");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tbl_powarning.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Po Number", "Customer Name"
            }
        ));
        jScrollPane2.setViewportView(tbl_powarning);

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setText("Purchase Order To Deliver");

        btn_addNewLaborInvoice.setText("Add New Labor Invoice");
        btn_addNewLaborInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addNewLaborInvoiceActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jButton2.setText("Show Report");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jMenu1.setText("Customers");
        jMenu1.setFocusable(false);
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem1.setText("Add New");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem2.setText("Edit Customer");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem13.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem13.setText("Delete Customer");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem13);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Manufacturing PO");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jMenu2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu2ActionPerformed(evt);
            }
        });

        jMenuItem3.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem3.setText("Add New PO");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem5.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem5.setText("PO & Invoice Operations");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu8.setText("Service PO");
        jMenu8.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem15.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem15.setText("Add New Service PO");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem15);

        jMenuItem16.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem16.setText("View All Service PO");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu8.add(jMenuItem16);

        jMenuBar1.add(jMenu8);

        jMenu3.setText("Quotations");
        jMenu3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem4.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem4.setText("Add New Quotation");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuItem6.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem6.setText("Get Quotations");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Supply Order");
        jMenu4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem7.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem7.setText("Add New Supply Order");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem7);

        jMenuItem8.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem8.setText("Show Supply Order");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem8);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Technical Bid");
        jMenu5.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem9.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem9.setText("Add New Technical Bid");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem9);

        jMenuItem10.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem10.setText("Show Technical Bid");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem10);

        jMenuBar1.add(jMenu5);

        jMenu6.setText("Price Bid");
        jMenu6.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        jMenuItem11.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem11.setText("Add New Price Bid");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem11);

        jMenuItem12.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jMenuItem12.setText("Show Price Bid");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuBar1.add(jMenu6);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(28, 28, 28)
                        .addComponent(btn_printpurchaseorder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_getallpurchaseorders, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_showallquatations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNewSupplyOrder)
                        .addGap(57, 57, 57)
                        .addComponent(btn_addNewPriceBid)
                        .addGap(18, 18, 18)
                        .addComponent(btn_addNewTechnicalBid))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 695, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(131, 131, 131)
                                .addComponent(btn_getAllPriceBid)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_getAllTechnicalBids))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addGap(72, 72, 72)
                                .addComponent(btn_addNewLaborInvoice))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1162, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(276, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_printpurchaseorder)
                        .addComponent(btn_getallpurchaseorders)
                        .addComponent(btn_showallquatations)
                        .addComponent(btnAddNewSupplyOrder)
                        .addComponent(jButton1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_addNewPriceBid)
                        .addComponent(btn_addNewTechnicalBid)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_getAllPriceBid)
                            .addComponent(btn_getAllTechnicalBids))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btn_addNewLaborInvoice)
                                .addGap(60, 60, 60))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2)
                                .addGap(71, 71, 71))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        // TODO add your handling code here:
        //setToTable();

    }//GEN-LAST:event_formComponentShown
    private Customer getSelectedCustomer() {
        String id = getSelectedId();
        System.out.println("Searching for id " + id);
        ArrayList<Customer> al = data;
        System.out.println("i am from getSelectedCustomer " + al);
        Customer foundCustomer = null;
        for (Customer c : al) {
            if (c.getId().equalsIgnoreCase(id)) {
                foundCustomer = c;
                break;
            }
        }
        return foundCustomer;
    }

    private Customer getCustomerFromRow() {
        Customer val = new Customer();
        try {
            int row = jTable1.getSelectedRow();
            val.setId(jTable1.getValueAt(row, 0) + "");
            val.setName(jTable1.getValueAt(row, 1) + "");
            val.setGstin(jTable1.getValueAt(row, 2) + "");
            val.setAddress(jTable1.getValueAt(row, 3) + "");
            val.setContact(jTable1.getValueAt(row, 4) + "");
            val.setEmail(jTable1.getValueAt(row, 5) + "");
            val.setVendor_code(jTable1.getValueAt(row, 6) + "");
            val.setPanNo(jTable1.getValueAt(row, 7) + "");
            val.setCgst(jTable1.getValueAt(row, 8) + "");
            val.setSgst(jTable1.getValueAt(row, 9) + "");
            val.setIgst(jTable1.getValueAt(row, 10) + "");
            System.out.println("selected customer is " + val);
            UtilVars.setCustomer(val);
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(this, "Please select customer.");
        } catch (Exception ex) {
            ex.printStackTrace();
            //JOptionPane.showMessageDialog(this, "Please select customer.");
        }
        return val;
    }

    private void btn_printpurchaseorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_printpurchaseorderActionPerformed
        // TODO add your handling code here:
        FirebaseMethods.getPurchaseOrder("1", this);
    }//GEN-LAST:event_btn_printpurchaseorderActionPerformed

    private void btn_getallpurchaseordersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getallpurchaseordersActionPerformed
        // TODO add your handling code here:
        FirebaseMethods.getAllPurchaseOrders(this, getCustomerFromRow());
    }//GEN-LAST:event_btn_getallpurchaseordersActionPerformed

    private void btn_showallquatationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showallquatationsActionPerformed
        // TODO add your handling code here:
        FirebaseMethods.showAllQuatations();
    }//GEN-LAST:event_btn_showallquatationsActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Customer myCust = getSelectedCustomer();
        //String cust_id = "-LD0SrsFA0JLzehpkQ4V";

        if (!myCust.getId().trim().equals("")) {
            ShowCustomerInfo dialog = new ShowCustomerInfo(new javax.swing.JFrame(), true, myCust, jTable1);
            dialog.setVisible(true);
//            FirebaseMethods.getSingleCustomer(myCust);
        } else {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        }

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        AddCustomer dialog = new AddCustomer();
        dialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            PurchaseOrder po = new PurchaseOrder(id);
            po.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            Calendar now = Calendar.getInstance();   // Gets the current date and time
            int year = now.get(Calendar.YEAR);
            System.out.println("This is year " + year);
            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.generateQuatationId(this, getSelectedId(), year);
        }

    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            final ProgressDialogFrame pd = new ProgressDialogFrame("Loading...");
            UtilVars.setCustomer(getCustomerFromRow());
            final JInternalFrame jif = this;
            pd.setVisible(true);
            Thread worker = new Thread() {
                public void run() {
                    try {
                        FirebaseMethods.getPurchaseOrderByCustomer(jif, getCustomerFromRow());

                    } catch (Exception ex) {
                        System.out.println("Exception occured " + ex);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //resultLabel.setText("Finished");
                            pd.setVisible(false);
                            pd.dispose();
                        }
                    });
                }
            };
            worker.start();

            //FirebaseMethods.getPurchaseOrderByCustomer(this, getCustomerFromRow());
        }
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            FirebaseMethods.getQuatationByCustomer(this, id);
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenu2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu2ActionPerformed

    private void btn_addNewPriceBidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addNewPriceBidActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            AddNewPriceBid addNewPriceBid = new AddNewPriceBid(new ArrayList<PriceBidItems>(), false);
            addNewPriceBid.setVisible(true);
        }
    }//GEN-LAST:event_btn_addNewPriceBidActionPerformed

    private void btn_addNewTechnicalBidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addNewTechnicalBidActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            AddNewTechnicalBid addNewTechnicalBid = new AddNewTechnicalBid(false);
            addNewTechnicalBid.setVisible(true);
        }
    }//GEN-LAST:event_btn_addNewTechnicalBidActionPerformed

    private void btn_getAllPriceBidActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getAllPriceBidActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllPriceBid1(this);
        }
    }//GEN-LAST:event_btn_getAllPriceBidActionPerformed

    private void btn_getAllTechnicalBidsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_getAllTechnicalBidsActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllTechnicalBid1(this);
        }
    }//GEN-LAST:event_btn_getAllTechnicalBidsActionPerformed

    private void btnAddNewSupplyOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewSupplyOrderActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.generateSoId(this);
        }
    }//GEN-LAST:event_btnAddNewSupplyOrderActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllSupplyOrderByCustomer(getCustomerFromRow(), this);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.generateSoId(this);
        }
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllSupplyOrderByCustomer(getCustomerFromRow(), this);
        }
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            AddNewPriceBid addNewPriceBid = new AddNewPriceBid(new ArrayList<PriceBidItems>(), false);
            addNewPriceBid.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllPriceBid1(this);
        }
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            AddNewTechnicalBid addNewTechnicalBid = new AddNewTechnicalBid(false);
            addNewTechnicalBid.setVisible(true);
        }
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            FirebaseMethods.getAllTechnicalBid1(this);
        }
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        // TODO add your handling code here:

        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            if (AdminPassword.getStr().equals(password)) {
                FirebaseMethods.deleteCustomer(getSelectedId(), this);
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.removeRow(jTable1.getSelectedRow());
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void btn_addNewLaborInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addNewLaborInvoiceActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_btn_addNewLaborInvoiceActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            AddNewPurchaseOrder po = new AddNewPurchaseOrder(id);

        }

    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            UtilVars.setCustomer(getCustomerFromRow());
            ViewAllServicePO view = new ViewAllServicePO(id);
        }
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        InvoiceRangeSelector irs = new InvoiceRangeSelector();
        irs.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNewSupplyOrder;
    private javax.swing.JButton btn_addNewLaborInvoice;
    private javax.swing.JButton btn_addNewPriceBid;
    private javax.swing.JButton btn_addNewTechnicalBid;
    private javax.swing.JButton btn_getAllPriceBid;
    private javax.swing.JButton btn_getAllTechnicalBids;
    private javax.swing.JButton btn_getallpurchaseorders;
    private javax.swing.JButton btn_printpurchaseorder;
    private javax.swing.JButton btn_showallquatations;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tbl_powarning;
    // End of variables declaration//GEN-END:variables

    private void addChildEventListner() {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        mRef.child("customers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
//                String address = (String) ds.child("address").getValue();
//                String contact = (String) ds.child("contact").getValue();
//                String email = (String) ds.child("email").getValue();
//                String gstin = (String) ds.child("gstin").getValue();
//                String id = (String) ds.child("id").getValue();
//                String name = (String) ds.child("name").getValue();
//                String panNo = (String) ds.child("panNo").getValue();
//                String vendor_code = (String) ds.child("vendor_code").getValue();
//                Vector row = new Vector();
//                DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
//                row.add(id);
//                row.add(name);
//                row.add(gstin);
//                row.add(address);
//                row.add(contact);
//                row.add(email);
//                row.add(vendor_code);
//                row.add(panNo);
//                dm.addRow(row);
                DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                while (dm.getRowCount() > 0) {
                    dm.removeRow(0);
                }
                final ProgressDialogFrame pd = new ProgressDialogFrame("Loading...");
                //pd.setVisible(true);
                Thread worker = new Thread() {
                    public void run() {
                        try {
                            data = FirebaseMethods.getAllCustomers(jTable1);

                            FirebaseMethods.getWarningPo(tbl_powarning);

                        } catch (Exception ex) {
                            System.out.println("Exception occured " + ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    //resultLabel.setText("Finished");
                                    Thread.sleep(2000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ShowAllCustomers.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                pd.setVisible(false);
                                pd.dispose();
                                System.out.println("This is all customer data data " + data);
                            }
                        });
                    }
                };
                worker.start();

            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                while (dm.getRowCount() > 0) {
                    dm.removeRow(0);
                }
                final ProgressDialogFrame pd = new ProgressDialogFrame("Loading...");
                pd.setVisible(true);
                Thread worker = new Thread() {
                    public void run() {
                        try {
                            data = FirebaseMethods.getAllCustomers(jTable1);

                            FirebaseMethods.getWarningPo(tbl_powarning);

                        } catch (Exception ex) {
                            System.out.println("Exception occured " + ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    //resultLabel.setText("Finished");
                                    Thread.sleep(2000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ShowAllCustomers.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                pd.setVisible(false);
                                pd.dispose();
                                System.out.println("This is all customer data data " + data);
                            }
                        });
                    }
                };
                worker.start();
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
                while (dm.getRowCount() > 0) {
                    dm.removeRow(0);
                }
                final ProgressDialogFrame pd = new ProgressDialogFrame("Loading...");
                pd.setVisible(true);
                Thread worker = new Thread() {
                    public void run() {
                        try {
                            data = FirebaseMethods.getAllCustomers(jTable1);

                            FirebaseMethods.getWarningPo(tbl_powarning);

                        } catch (Exception ex) {
                            System.out.println("Exception occured " + ex);
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                try {
                                    //resultLabel.setText("Finished");
                                    Thread.sleep(2000);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(ShowAllCustomers.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                pd.setVisible(false);
                                pd.dispose();
                                System.out.println("This is all customer data data " + data);
                            }
                        });
                    }
                };
                worker.start();
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    private void addChildEventListnerForPoWarning() {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        mRef.child("purchase_order").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                FirebaseMethods.getWarningPo(tbl_powarning);
            }
            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                FirebaseMethods.getWarningPo(tbl_powarning);
            }
            @Override
            public void onChildRemoved(DataSnapshot ds) {
                FirebaseMethods.getWarningPo(tbl_powarning);
            }
            @Override
            public void onChildMoved(DataSnapshot ds, String string) {
            }
            @Override
            public void onCancelled(DatabaseError de) {
            }
        });
    }
}