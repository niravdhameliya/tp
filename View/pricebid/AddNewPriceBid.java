/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.pricebid;

import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import com.mileset.neeleshengineers.modal.pricebid.PriceBidItems;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rahul
 */
public class AddNewPriceBid extends javax.swing.JDialog {

    private String QtnNo;
    private String clientName;
    private String subject;
    private String tenderEnqNo;
    private String tenderEnqDate;
    private String nomenclature;
    private String quantity;
    private String freight;
    private String basic_rate;
    private String cgst;
    private String sgst;
    private String igst;
    private String paymentTerms;
    private String govtTaxes;
    private String delPeriod;
    private String delTerms;
    private String PFCharges;
    private String transitInsu;
    private String emd;
    private String valOffer;
    private String installComm;
    private String inspection;
    private String PSD;
    private String FIM;
    private String note;
    private String total;
    private boolean updateFlag;
    private DefaultTableModel model;
    private ArrayList<PriceBidItems> priceBidItemsList;

    /**
     * Creates new form AddNewPriceBid
     */
    public AddNewPriceBid(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        addListnerToJTable();
        priceBidItemsList = new ArrayList<>();
    }

    public AddNewPriceBid(ArrayList<PriceBidItems> myItems, boolean updateFlag) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        addListnerToJTable();
        this.updateFlag = updateFlag;
        if (updateFlag) {
            PriceBid priceBid = UtilVars.getPriceBid();
            txtDate.setText(priceBid.getDate());
            txtDelPeriod.setText(priceBid.getDelPeriod());
            txtDelTerms.setText(priceBid.getDelTerms());
            txtEarnestMoney.setText(priceBid.getEmd());
            txtEnqNo.setText(priceBid.getTenderEnqNo());
            txtFIM.setText(priceBid.getFim());
            txtGovtTax.setText(priceBid.getGovtTaxes());
            txtInspection.setText(priceBid.getInspection());
            txtInstallComission.setText(priceBid.getInstallComm());
            txtNote.setText(priceBid.getNote());
            txtPFCharges.setText(priceBid.getPFCharges());
            txtPSD.setText(priceBid.getPsd());
            txtPartyName.setText(priceBid.getClientName());
            txtPayment.setText(priceBid.getPaymentTerms());
            txtQtnNo.setText(priceBid.getQtnNo());
            txtSubject.setText(priceBid.getSubject());
            txtTenderDate.setText(priceBid.getTenderEnqDate());
            txtTransitInsurance.setText(priceBid.getTransitInsu());
            txtValidityOffer.setText(priceBid.getValOffer());
            ArrayList<PriceBidItems> priceBidItems = priceBid.getPriceBidItems();
            priceBidItemsList = priceBidItems;
            btn_save.setText("update");
            loadDataToTable(myItems);
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {

                    ShowAllPricelBid sap = new ShowAllPricelBid(ShowAllPriceBidVars.getData());
                    sap.setVisible(true);

                }

            });
        } else {
            priceBidItemsList = new ArrayList<>();
            Customer c = UtilVars.getCustomer();
            txtPartyName.setText(c.getName() + ",\n" + c.getAddress());
        }

    }

    private void addListnerToJTable() {
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    setSelectedInvoiceItemsObject();
                    //btn_add.setEnabled(true);
                    //btn_add.setText("Click here to Update Item");
                    //updateFlat=true;
                    deletSelectedRow();

                }
            }

        });
    }

    private void setSelectedInvoiceItemsObject() {
        try {
            int row = jTable1.getSelectedRow();
            txtNomenclature.setText(jTable1.getValueAt(row, 0) + "");
            String[] quantString = jTable1.getValueAt(row, 1).toString().split(" ");
            txtQuantity.setText(quantString[0]);
            txtBasic.setText(jTable1.getValueAt(row, 2) + "");
            txtFreight.setText(jTable1.getValueAt(row, 3) + "");
            txtcgst.setText(jTable1.getValueAt(row, 4) + "");
            txtSgst.setText(jTable1.getValueAt(row, 5) + "");
            txtIgst.setText(jTable1.getValueAt(row, 6) + "");
            txtTotal.setText(jTable1.getValueAt(row, 7) + "");

            priceBidItemsList.remove(row);
        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please select id to update");
        }

    }

    private void deletSelectedRow() {
        int rowSelected = jTable1.getSelectedRow();
        if (rowSelected > -1) {

            DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
            model1.removeRow(rowSelected);
            priceBidItemsList.remove(rowSelected);

        } else {
            JOptionPane.showMessageDialog(this, "Select a record to delete");
        }
    }

    private void loadDataToTable(ArrayList<PriceBidItems> data1) {
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1.size());
        for (int i = 0; i < data1.size(); i++) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(data1.get(i).getNomenclature());
            row.add(data1.get(i).getQuantity());
            row.add(data1.get(i).getBasic_rate());
            row.add(data1.get(i).getFreight());
            row.add(data1.get(i).getCgst());
            row.add(data1.get(i).getSgst());
            row.add(data1.get(i).getIgst());
            row.add(data1.get(i).getTotal());
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSubject = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        txtEnqNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTenderDate = new javax.swing.JTextField();
        txtQtnNo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPartyName = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNomenclature = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        txtQuantity = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtBasic = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtFreight = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtcgst = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSgst = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtIgst = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        comboOptions = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtPayment = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        txtGovtTax = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtDelPeriod = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDelTerms = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtPFCharges = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTransitInsurance = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtEarnestMoney = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtValidityOffer = new javax.swing.JTextField();
        txtInstallComission = new javax.swing.JTextField();
        txtInspection = new javax.swing.JTextField();
        txtPSD = new javax.swing.JTextField();
        txtFIM = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        btn_save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Price Bid");
        setModal(true);

        txtSubject.setColumns(20);
        txtSubject.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtSubject.setRows(5);
        jScrollPane2.setViewportView(txtSubject);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Tender Enquiry No.");

        txtEnqNo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Tender Date");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("QTN. No");

        txtTenderDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtQtnNo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("To");

        txtPartyName.setColumns(20);
        txtPartyName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPartyName.setRows(5);
        jScrollPane1.setViewportView(txtPartyName);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Subject");

        txtDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel27.setText("Date");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtQtnNo)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtEnqNo, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTenderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel27)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtQtnNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel27)
                            .addComponent(txtDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtEnqNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(txtTenderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Nomenclature");

        txtNomenclature.setColumns(20);
        txtNomenclature.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNomenclature.setRows(5);
        jScrollPane3.setViewportView(txtNomenclature);

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Quantity");

        txtQuantity.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtQuantity.setText("0");
        txtQuantity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtQuantityActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Basic Rate");

        txtBasic.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtBasic.setText("0");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Freight");

        txtFreight.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtFreight.setText("0");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("CGST(%)");

        txtcgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtcgst.setText("9");

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("SGST(%)");

        txtSgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtSgst.setText("9");
        txtSgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSgstActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("IGST(%)");

        txtIgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtIgst.setText("0");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("Grand total");

        txtTotal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtTotal.setText("0");
        txtTotal.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTotalFocusGained(evt);
            }
        });
        txtTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomenclature", "Quoted Qty.", "Basic Rate", "Freight Charges", "CGST", "SGST", "IGST", "Total Amount"
            }
        ));
        jScrollPane4.setViewportView(jTable1);

        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Delete");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        comboOptions.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        comboOptions.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nos", "Kg", "Length", "SET", "REGRET" }));
        comboOptions.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboOptionsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtFreight, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtcgst, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4)
                                        .addComponent(comboOptions, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtBasic, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(8, 8, 8))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtIgst, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel13)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAdd)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtSgst, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jButton2))))
                    .addComponent(jScrollPane4))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txtBasic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboOptions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtcgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(txtSgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtFreight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAdd)
                            .addComponent(jButton2)
                            .addComponent(jLabel12)
                            .addComponent(txtIgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("Payment Terms");

        txtPayment.setColumns(20);
        txtPayment.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtPayment.setRows(5);
        jScrollPane5.setViewportView(txtPayment);

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Govt. Taxes");

        txtGovtTax.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setText("Delivery Period");

        txtDelPeriod.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setText("Delivery Terms");

        txtDelTerms.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setText("P & F Charges");

        txtPFCharges.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText("Transit Insurance");

        txtTransitInsurance.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setText("Earnest Money Deposit");

        txtEarnestMoney.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Validity of the Offer");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setText("Installing & Commissioning");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setText("Inspection");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setText("PSD");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setText("Free Issue Material");

        txtValidityOffer.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtInstallComission.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtInspection.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtPSD.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txtFIM.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel26.setText("Note");

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNote.setRows(5);
        jScrollPane6.setViewportView(txtNote);

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25)
                            .addComponent(jLabel26)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPFCharges)
                            .addComponent(txtTransitInsurance)
                            .addComponent(txtEarnestMoney)
                            .addComponent(txtValidityOffer)
                            .addComponent(txtInstallComission)
                            .addComponent(txtInspection)
                            .addComponent(txtPSD)
                            .addComponent(txtFIM)
                            .addComponent(jScrollPane6))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel16)
                            .addComponent(jLabel17))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(106, 106, 106)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDelPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtDelTerms, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtGovtTax, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtGovtTax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(txtDelPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtDelTerms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtPFCharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTransitInsurance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtEarnestMoney, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(txtValidityOffer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel22)
                    .addComponent(txtInstallComission, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(txtInspection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(txtPSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtFIM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        nomenclature = txtNomenclature.getText();
        quantity = txtQuantity.getText();
        freight = txtFreight.getText();
        basic_rate = txtBasic.getText();
        cgst = txtcgst.getText();
        sgst = txtSgst.getText();
        igst = txtIgst.getText();
        total = txtTotal.getText();
        model = (DefaultTableModel) jTable1.getModel();
        Vector row = new Vector();
        row.add(nomenclature);
        row.add(quantity + " " + comboOptions.getSelectedItem());
        row.add(basic_rate);
        row.add(freight);
        row.add(cgst);
        row.add(sgst);
        row.add(igst);
        row.add(total);
        model.addRow(row);
        PriceBidItems pbi = new PriceBidItems(nomenclature, quantity + " " + comboOptions.getSelectedItem(), freight, basic_rate, cgst, sgst, igst, total);
        priceBidItemsList.add(pbi);
    }//GEN-LAST:event_btnAddActionPerformed

    private void comboOptionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboOptionsActionPerformed
        // TODO add your handling code here:
        int quanityOption = comboOptions.getSelectedIndex();

        if (quanityOption == 4) {
            basic_rate = "-";
            quantity = "";
            freight = "-";
            cgst = "-";
            sgst = "-";
            igst = "-";
            total = "-";
            paymentTerms = "NA";
            govtTaxes = "NA";
            delPeriod = "NA";
            delTerms = "NA";
            PFCharges = "NA";
            transitInsu = "NA";
            emd = "NA";
            valOffer = "NA";
            installComm = "NA";
            inspection = "NA";
            PSD = "NA";
            FIM = "NA";
            note = "NA";
            txtQuantity.setText(quantity);
            txtFreight.setText(freight);
            txtBasic.setText(basic_rate);
            txtcgst.setText(cgst);
            txtSgst.setText(sgst);
            txtIgst.setText(igst);
            txtTotal.setText(total);
            txtPayment.setText(paymentTerms);
            txtGovtTax.setText(govtTaxes);
            txtDelPeriod.setText(delPeriod);
            txtDelTerms.setText(delTerms);
            txtPFCharges.setText(PFCharges);
            txtTransitInsurance.setText(transitInsu);
            txtEarnestMoney.setText(emd);
            txtValidityOffer.setText(valOffer);
            txtInstallComission.setText(installComm);
            txtInspection.setText(inspection);
            txtPSD.setText(PSD);
            txtFIM.setText(FIM);
            txtNote.setText(note);

        } else {
            basic_rate = "0";
            freight = "0";
            cgst = "0";
            sgst = "0";
            igst = "0";
            total = "0";
            txtFreight.setText(freight);
            txtBasic.setText(basic_rate);
            txtcgst.setText(cgst);
            txtSgst.setText(sgst);
            txtIgst.setText(igst);
            txtTotal.setText(total);
        }
    }//GEN-LAST:event_comboOptionsActionPerformed

    private void txtTotalFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalFocusGained
        double c = Double.parseDouble(txtcgst.getText());
        double s = Double.parseDouble(txtSgst.getText());
        double i = Double.parseDouble(txtIgst.getText());
        double bsc = Double.parseDouble(txtBasic.getText());
        int quant = Integer.parseInt(txtQuantity.getText());
        double amount = bsc * quant;
        double freight1 = Double.parseDouble(txtFreight.getText());
        amount = amount + (freight1 * quant);
        double cgstval = (amount / 100) * c;
        double sgstval = (amount / 100) * s;
        double igstval = (amount / 100) * i;
        double finalvalue = amount + cgstval + sgstval + igstval;

        BigInteger biBasic = new BigInteger(txtBasic.getText());
        BigInteger biQuant = new BigInteger(txtQuantity.getText());
        BigInteger biFreight = new BigInteger(txtFreight.getText());
        BigInteger biAmount = biBasic.multiply(biQuant);
        biAmount = biAmount.add(biFreight.multiply(biQuant));
        BigInteger hund = new BigInteger("100");
        BigInteger temp = biAmount.divide(hund);

        //BigInteger biCgst = new BigInteger(cgstval + "");
        BigInteger biCgst = temp.multiply(new BigInteger(txtcgst.getText()));
        BigInteger biSgst = temp.multiply(new BigInteger(txtSgst.getText()));
        BigInteger biIgst = temp.multiply(new BigInteger(txtIgst.getText()));
//        BigInteger biSgst = new BigInteger(sgstval + "");
        //      BigInteger biIgst = new BigInteger("" + igstval);
        //BigInteger biTotal = new BigInteger("" + amount);
        //BigInteger biFinal = new BigInteger("" + finalvalue);
        BigInteger biFinal = biAmount.add(biCgst).add(biSgst).add(biIgst);
        txtTotal.setText(biFinal + "");
    }//GEN-LAST:event_txtTotalFocusGained

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        QtnNo = txtQtnNo.getText();
        clientName = txtPartyName.getText();
        subject = txtSubject.getText();
        tenderEnqNo = txtEnqNo.getText();
        tenderEnqDate = txtTenderDate.getText();
        paymentTerms = txtPayment.getText();
        govtTaxes = txtGovtTax.getText();
        delPeriod = txtDelPeriod.getText();
        delTerms = txtDelTerms.getText();
        PFCharges = txtPFCharges.getText();
        transitInsu = txtTransitInsurance.getText();
        emd = txtEarnestMoney.getText();
        valOffer = txtValidityOffer.getText();
        installComm = txtInstallComission.getText();
        inspection = txtInspection.getText();
        PSD = txtPSD.getText();
        FIM = txtFIM.getText();
        note = txtNote.getText();
        String date = txtDate.getText().toString();
        if (updateFlag) {
            PriceBid pricebid = new PriceBid(QtnNo, date, clientName, subject, tenderEnqNo, tenderEnqDate, paymentTerms, govtTaxes, delPeriod, delTerms, PFCharges, transitInsu, emd, valOffer, installComm, inspection, PSD, FIM, note, priceBidItemsList);
            pricebid.setFid(UtilVars.getPriceBid().getFid());
            pricebid.setCustName(UtilVars.getCustomer().getName());
            FirebaseMethods.updatePriceBid(pricebid, this);
            new com.mileset.neeleshengineers.View.Report.PriceBidPDF(pricebid);
        } else {
            ArrayList<PriceBidItems> mypriceBidItems = getJTableData();
            PriceBid pricebid = new PriceBid(QtnNo, date, clientName, subject, tenderEnqNo, tenderEnqDate, paymentTerms, govtTaxes, delPeriod, delTerms, PFCharges, transitInsu, emd, valOffer, installComm, inspection, PSD, FIM, note, mypriceBidItems);
            pricebid.setCustName(UtilVars.getCustomer().getName());
            for (PriceBidItems pbi : pricebid.getPriceBidItems()) {
                System.out.println("This is validaing before insertion " + pbi);
            }
            FirebaseMethods.addNewPriceBid1(pricebid, this);
            new com.mileset.neeleshengineers.View.Report.PriceBidPDF(pricebid);
        }

    }//GEN-LAST:event_btn_saveActionPerformed
    private ArrayList<PriceBidItems> getJTableData() {
        ArrayList<PriceBidItems> bidItems = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (model != null) {
            for (int count = 0; count < model.getRowCount(); count++) {
                //numdata.add();
                String nomenclature = model.getValueAt(count, 0).toString();
                String quatedQty = model.getValueAt(count, 1).toString();
                String basic_rate = model.getValueAt(count, 2).toString();
                String fright_charges = model.getValueAt(count, 3).toString();
                String cgst = model.getValueAt(count, 4).toString();
                String sgst = model.getValueAt(count, 5).toString();
                String igst = model.getValueAt(count, 6).toString();
                String total_amount = model.getValueAt(count, 7).toString();
                PriceBidItems priceBidItem = new PriceBidItems();
                priceBidItem.setBasic_rate(basic_rate);
                priceBidItem.setCgst(cgst);
                priceBidItem.setFreight(fright_charges);
                priceBidItem.setIgst(igst);
                priceBidItem.setNomenclature(nomenclature);
                priceBidItem.setQuantity(quatedQty);
                priceBidItem.setSgst(sgst);
                priceBidItem.setTotal(total_amount);
                bidItems.add(priceBidItem);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please add items first");
        }
        System.out.println("After reading finished " + bidItems);
        return bidItems;
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int rowSelected = jTable1.getSelectedRow();
        if (rowSelected > -1) {
            DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
            priceBidItemsList.remove(rowSelected);
            model1.removeRow(rowSelected);
        } else {
            JOptionPane.showMessageDialog(this, "Select a record to delete");
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtSgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSgstActionPerformed

    private void txtQuantityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtQuantityActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtQuantityActionPerformed

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalActionPerformed

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
            java.util.logging.Logger.getLogger(AddNewPriceBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewPriceBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewPriceBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewPriceBid.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddNewPriceBid dialog = new AddNewPriceBid(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox comboOptions;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtBasic;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtDelPeriod;
    private javax.swing.JTextField txtDelTerms;
    private javax.swing.JTextField txtEarnestMoney;
    private javax.swing.JTextField txtEnqNo;
    private javax.swing.JTextField txtFIM;
    private javax.swing.JTextField txtFreight;
    private javax.swing.JTextField txtGovtTax;
    private javax.swing.JTextField txtIgst;
    private javax.swing.JTextField txtInspection;
    private javax.swing.JTextField txtInstallComission;
    private javax.swing.JTextArea txtNomenclature;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtPFCharges;
    private javax.swing.JTextField txtPSD;
    private javax.swing.JTextArea txtPartyName;
    private javax.swing.JTextArea txtPayment;
    private javax.swing.JTextField txtQtnNo;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSgst;
    private javax.swing.JTextArea txtSubject;
    private javax.swing.JTextField txtTenderDate;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTransitInsurance;
    private javax.swing.JTextField txtValidityOffer;
    private javax.swing.JTextField txtcgst;
    // End of variables declaration//GEN-END:variables
}
