/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *   https://stackoverflow.com/questions/16098046/how-do-i-print-a-double-value-without-scientific-notation-using-java
 */
package com.mileset.neeleshengineers.View.servicePO;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mileset.neeleshengineers.View.Report.LabourInvoicePDF;
import com.mileset.neeleshengineers.controller.servicePO.getLabourInvoiceFromCustId;
import com.mileset.neeleshengineers.controller.servicePO.labourCustIDCallBack;
import com.mileset.neeleshengineers.modal.Challan;
import com.mileset.neeleshengineers.modal.ChallanItems;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceItems;
import com.mileset.neeleshengineers.modal.laborinvoice.LabourIdObj;
import com.mileset.neeleshengineers.util.CurrentDate;
import com.mileset.neeleshengineers.util.FiscalDate;
import com.mileset.neeleshengineers.util.NumberToWord;
import com.mileset.neeleshengineers.util.UtilVars;
import com.mileset.neeleshengineers.util.Utility;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NewLabourInvoice extends javax.swing.JDialog {

    Customer cust;
    LabourIdObj lIdObj;
    Challan challan;
    ServicePO po;
    double basic_total = 0;
    int count;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    //ArrayList<Items> itemList;
    int labourId;
    ArrayList<PoItems> poItemsList, tempPoItemsList;
    ArrayList<ChallanItems> challanItemsList;
    ArrayList<LaborInvoiceItems> LabourItemList;
    LaborInvoiceDetails lid;
    HashMap<String, Integer> itemQuantHashMap, origItemQuantHashMap;

    public NewLabourInvoice(Challan challan, ServicePO po) {
        // super(parent, modal);
        this.challan = challan;
        this.po = po;
        challanItemsList = this.challan.getChallanItemsList();
        initComponents();
        this.cust = UtilVars.getCustomer();
        txt_vendorcode.setText(cust.getVendor_code());
        txt_customerContact.setText(cust.getName() + "\n" + cust.getAddress());
        //txt_workorder.removeAllItems();
        LabourItemList = new ArrayList<>();
        txt_billdate.setText(CurrentDate.getDate());
        txt_othercharges.setText("0");
        txt_cgst.setText(UtilVars.getCustomer().getCgst());
        txt_sgst.setText(UtilVars.getCustomer().getSgst());
        txt_igst.setText(UtilVars.getCustomer().getIgst());
        lbl_deliverydate.setText(this.po.getDelivery_date());
        txtChallanNo.setText(this.challan.getChallanNo());
        txtChallanDate.setText(this.challan.getDate());
        txt_sac.setText(this.po.getSac());
        txt_Frght.setVisible(false);
        txt_pono.setText(this.po.getPoid());
        txt_date.setText(this.po.getBill_date());
        basic_total = 0;
        tempPoItemsList = this.po.getItems();
        poItemsList = new ArrayList<>();

        getLabourInvoiceId();
        itemQuantHashMap = new HashMap<>();
        origItemQuantHashMap = new HashMap<>();
        for (int v = 0; v < challanItemsList.size(); v++) {
            ChallanItems cItems = challanItemsList.get(v);
            origItemQuantHashMap.put(cItems.getItemName(), cItems.getQuantity());
            for (int f = 0; f < tempPoItemsList.size(); f++) {
                PoItems pItems = tempPoItemsList.get(f);
                if (cItems.getItemName().equals(pItems.getItem_name())) {
                    pItems.setQuanatity("" + cItems.getQuantity());
                    poItemsList.add(pItems);
                    itemQuantHashMap.put(pItems.getItem_name(), 0);
                    break;
                }
            }
        }

        for (int y = 0; y < poItemsList.size(); y++) {
            PoItems item = poItemsList.get(y);
            System.out.println("" + item.getItem_name());
            txt_workorder.addItem(y + "  " + item.getWork_order());
        }

        getLabourInvoiceFromCustId getLInvoice = new getLabourInvoiceFromCustId(cust.getId(), this.challan.getChallanNo(), this.po.getPoid());
        getLInvoice.fetchWithChallan(new labourCustIDCallBack() {

            @Override
            public void callBack(ArrayList<LaborInvoiceDetails> value) {
                for (LaborInvoiceDetails invoice : value) {
                    ArrayList<LaborInvoiceItems> lItems = invoice.getItemList();
                    for (LaborInvoiceItems item : lItems) {
                        String currName = item.getItem_name();
                        int currQuant = item.getQuantity();
                        int originalQuant = itemQuantHashMap.get(currName);
                        int newQuantity = currQuant + originalQuant;
                        itemQuantHashMap.replace(currName, newQuantity);
                    }
                }
            }

            @Override
            public void recordNotPresent() {

            }
        });
          txt_workorder.setSize(150, 25);
        this.setVisible(true);
    }

    public void getLabourInvoiceId() {
        final String financialYear = FiscalDate.getFinancialYear(Calendar.getInstance());
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");

        Query query = mRef.child("labourId").child("" + financialYear).orderByKey().limitToLast(1);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {

                        String Key = childSnapshot.getKey();
                        int k = Integer.parseInt(Key);
                        k = k + 1;
                        labourId = k;
                        lIdObj = new LabourIdObj(k + "");
                        lbl_invoiceid.setText("LC/" + financialYear + "/" + k);
                    }
                } else {
                    System.out.println("poiuyt=0");
                    lbl_invoiceid.setText("0");
                    lIdObj = new LabourIdObj("1");
                    lbl_invoiceid.setText("LC/" + financialYear + "/1");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException(); // don't swallow errors
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_unitrate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        txt_workorder = new javax.swing.JComboBox<String>();
        txt_itemCode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_drawingNumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_customerContact = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        txt_itemname = new javax.swing.JTextField();
        lbl_unit = new javax.swing.JLabel();
        btnAdd = new javax.swing.JButton();
        btnDelet = new javax.swing.JButton();
        txt_Frght = new javax.swing.JTextField();
        txtChallanNo = new javax.swing.JTextField();
        lbl_invoiceid = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_billdate = new javax.swing.JTextField();
        txtChallanDate = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lbl_deliverydate = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txt_sgst = new javax.swing.JTextField();
        txt_cgst = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_igst = new javax.swing.JTextField();
        txt_othercharges = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        lbl_inwords = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lbl_grandtotal = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txt_vendorcode = new javax.swing.JTextField();
        txt_pono = new javax.swing.JTextField();
        txt_date = new javax.swing.JTextField();
        txt_vehicleno = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        jLabel26 = new javax.swing.JLabel();
        txtRoundOff = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        lblBasicTotal = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txt_sac = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Labour Invoice");
        setModal(true);

        jPanel1.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Work Order No");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_quantity.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_quantityFocusLost(evt);
            }
        });
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_quantityKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Unit Rate");

        txt_unitrate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_unitrate.setText("jTextField3");
        txt_unitrate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_unitrateFocusLost(evt);
            }
        });
        txt_unitrate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_unitrateKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Amount");

        txt_amount.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_amount.setText("jTextField4");
        txt_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_amountFocusGained(evt);
            }
        });

        txt_workorder.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_workorder.setMaximumSize(new java.awt.Dimension(50, 30));
        txt_workorder.setMinimumSize(new java.awt.Dimension(50, 30));
        txt_workorder.setPreferredSize(new java.awt.Dimension(50, 30));
        txt_workorder.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                txt_workorderItemStateChanged(evt);
            }
        });

        txt_itemCode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_itemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemCodeKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Item Code");

        txt_drawingNumber.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_drawingNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_drawingNumberKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Drawing Number");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setText("Name And Address of Supplier");

        txt_customerContact.setColumns(20);
        txt_customerContact.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_customerContact.setRows(5);
        jScrollPane2.setViewportView(txt_customerContact);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setText("Item Name");

        txt_itemname.setEditable(false);
        txt_itemname.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_itemname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemnameKeyReleased(evt);
            }
        });

        lbl_unit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_unit.setText("Unit");

        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelet.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btnDelet.setText("Delete");
        btnDelet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletActionPerformed(evt);
            }
        });

        txt_Frght.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txt_itemname, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_itemCode, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_drawingNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnAdd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnDelet)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_Frght))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(lbl_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_unit)
                    .addComponent(jLabel3)
                    .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(txt_itemname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_itemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txt_drawingNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd)
                        .addComponent(btnDelet)
                        .addComponent(txt_Frght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12))
        );

        txtChallanNo.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        lbl_invoiceid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_invoiceid.setText("jLabel11");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Invoice Id");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Bill Date");

        txt_billdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_billdate.setText("jTextField9");

        txtChallanDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("Challan Date");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("For Eg: 25/3/1992");

        lbl_deliverydate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_deliverydate.setText("Delivery Date");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Your Challan No.");

        jTable1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Quantity", "Unit Rate", "Total", "Item Code", "Drawing Number", "Unit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("CGST (%)");

        txt_sgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_sgst.setText("jTextField6");
        txt_sgst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_sgstFocusLost(evt);
            }
        });
        txt_sgst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_sgstKeyReleased(evt);
            }
        });

        txt_cgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_cgst.setText("jTextField5");
        txt_cgst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_cgstFocusLost(evt);
            }
        });
        txt_cgst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cgstKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("SGST (%)");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("IGST (%)");

        txt_igst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_igst.setText("jTextField7");
        txt_igst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_igstFocusLost(evt);
            }
        });
        txt_igst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_igstKeyReleased(evt);
            }
        });

        txt_othercharges.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_othercharges.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_otherchargesKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setText("Freight Charges");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Total");

        lbl_total.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_total.setText("basic total");
        lbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_totalMouseReleased(evt);
            }
        });

        lbl_inwords.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_inwords.setText("jLabel18");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setText("In Words");

        lbl_grandtotal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lbl_grandtotal.setText("jLabel16");
        lbl_grandtotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_grandtotalMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Grand Total");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setText("Vendor Code");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel23.setText("P.O. No.");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel24.setText("P.O Date");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel25.setText("Vehicle No");

        txt_vendorcode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        txt_pono.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_pono.setText("jTextField12");

        txt_date.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_date.setText("jTextField13");

        txt_vehicleno.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel26.setText("Round Off");

        txtRoundOff.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtRoundOff.setText("0");
        txtRoundOff.setEnabled(false);

        jLabel27.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel27.setText("Basic Total");

        lblBasicTotal.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        lblBasicTotal.setText("jLabel28");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btn_save)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(lblBasicTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_cgst, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lbl_total, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(txt_othercharges, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_sgst, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_igst, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel22)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(txt_vendorcode))
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addComponent(txtRoundOff, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel25)
                                                        .addComponent(jLabel24)
                                                        .addComponent(jLabel23))
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txt_vehicleno, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txt_pono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(0, 76, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_inwords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lbl_total))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_othercharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jLabel22)
                    .addComponent(txt_vendorcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_pono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_date, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(txt_vehicleno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblBasicTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtRoundOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_inwords))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_save)
                .addContainerGap())
        );

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText("SAC Code");

        txt_sac.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChallanNo)
                            .addComponent(lbl_invoiceid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_deliverydate, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_sac, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(146, 146, 146))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(lbl_deliverydate)
                                .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_invoiceid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtChallanNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel19)
                                .addComponent(txt_sac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void calculateResult() {
        try {
            //    txt_amount.setText((Integer.parseInt(txt_quantity.getText().toString()) * Integer.parseInt(txt_unitrate.getText().toString())) + "");
            Double qua = Double.parseDouble(txt_quantity.getText());
            Double unit_rate = Double.parseDouble(txt_unitrate.getText());
            double amnt = qua * unit_rate;
            txt_amount.setText(String.format("%.2f", amnt) + "");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("exception occured during result calculation " + ex.getMessage());
        }
    }

    public boolean isValidNumber(int key) {
        boolean flag = false;
        if ((key > 47 && key < 58) || (key > 95 && key < 106)) {
            flag = true;
        } else if (key == 8 || key == 46) {
            flag = true;
        }
        return flag;
    }
    private void txt_sgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_sgstFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstFocusLost

    private void txt_sgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sgstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstKeyReleased

    private void txt_cgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cgstFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstFocusLost

    private void txt_cgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cgstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstKeyReleased

    private void txt_igstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_igstFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_igstFocusLost

    private void txt_igstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_igstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_igstKeyReleased

    private void txt_otherchargesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherchargesKeyReleased
        setGrandTotal();
    }//GEN-LAST:event_txt_otherchargesKeyReleased
    private void setGrandTotal() {
        lbl_total.setText(String.format("%.2f", basic_total));
        double finalvalue = 0;
        try {
            double cgst = Double.parseDouble(txt_cgst.getText().toString());
            double sgst = Double.parseDouble(txt_sgst.getText().toString());
            double othercharges = Double.parseDouble(txt_othercharges.getText().toString());
            double igst = Double.parseDouble(txt_igst.getText().toString());
            double amount = Double.parseDouble(lbl_total.getText().toString());

            double cgstval = ((amount + othercharges) / 100) * cgst;
            double sgstval = ((amount + othercharges) / 100) * sgst;
            double igstval = ((amount + othercharges) / 100) * igst;
            finalvalue = amount + cgstval + sgstval + igstval + othercharges;

        } catch (Exception ex) {
            System.out.println("exception occured within setGrandTotal()" + ex.getMessage());
            ex.printStackTrace();
        }

        Double dVal = (Double) finalvalue;

        long longVal = Math.round(dVal);
        double roundOff = Double.parseDouble(longVal + "") - dVal;

        DecimalFormat df = new DecimalFormat("0.00");
        String formate = df.format(roundOff);
        try {
            roundOff = (Double) df.parse(formate);
            formate = df.format(dVal);
            dVal = (Double) df.parse(formate);
        } catch (ParseException ex) {
            roundOff = 0;
            Logger.getLogger(EditLabourInvoice.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            //int intVal = dVal.intValue();
            String words = NumberToWord.NumberToWord(longVal + "");
            //lbl_grandtotal.setText(intVal + "");
            lblBasicTotal.setText(String.format("%.2f", dVal) + "");
            txtRoundOff.setText(roundOff + "");
            lbl_grandtotal.setText(longVal + "");
            lbl_inwords.setText(words);
        }
    }
    private void lbl_totalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_totalMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_totalMouseReleased

    private void lbl_grandtotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_grandtotalMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_grandtotalMouseClicked

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String invoice_id = lbl_invoiceid.getText();
        String dte = txt_billdate.getText();
        String challanNo = txtChallanNo.getText();
        String sac = txt_sac.getText();
        double other = Double.parseDouble(txt_othercharges.getText());
        double cgst = Double.parseDouble(txt_cgst.getText());
        double sgst = Double.parseDouble(txt_sgst.getText());
        double igst = Double.parseDouble(txt_igst.getText());
        double grand = Double.parseDouble(lbl_grandtotal.getText());;
        double basic = Double.valueOf(lbl_total.getText().toString());
        String vendorCode = txt_vendorcode.getText().trim();
        String pono = txt_pono.getText().trim();
        String dte2 = txt_date.getText().trim();
        String vehicleNo = txt_vehicleno.getText().trim();
        double taxAmount = Double.valueOf(lblBasicTotal.getText().trim());
        double roundOFF = Double.valueOf(txtRoundOff.getText().trim());

        LaborInvoiceDetails invoice_details = new LaborInvoiceDetails();
        invoice_details.setBasicTotal(basic);
        invoice_details.setCgst(cgst);
        invoice_details.setSac(sac);
        invoice_details.setDate(dte);
        invoice_details.setDte2(dte2);
        invoice_details.setAmount(taxAmount);
        invoice_details.setRoundOFF(roundOFF);
        invoice_details.setGrandTotal(grand);
        invoice_details.setIgst(igst);
        invoice_details.setInvoice_id(invoice_id);
        invoice_details.setItemList(LabourItemList);
        invoice_details.setGSTIN(UtilVars.getCustomer().getGstin());
        invoice_details.setOthercharges(other);
        invoice_details.setPONo(pono);
        invoice_details.setSgst(sgst);
        invoice_details.setVehicleNo(vehicleNo);
        invoice_details.setVendorCode(vendorCode);
        invoice_details.setChallanNo(challanNo);
        invoice_details.setChallanDate(txtChallanDate.getText());
        invoice_details.setCname(txt_customerContact.getText().trim());
        String dDate = po.getDelivery_date();
        String bDate = txt_billdate.getText().toString();
        Date dd = Utility.convertStringToDate(dDate);
        Date bd = Utility.convertStringToDate(bDate);
        if (bd.after(dd)) {
            JOptionPane.showMessageDialog(this, "Bill date should be less than Delivery Date");
            return;
        }
        try {
            saveLabourId();
            Thread.sleep(3000);
            LabourInvoicePDF pdf = new LabourInvoicePDF(invoice_details);
            saveLabourInvoice(invoice_details);
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btnDeletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletActionPerformed
        int rowToDel = jTable1.getSelectedRow();
        System.out.println("selected row is" + rowToDel);
        if (rowToDel > -1) {
            LabourItemList.remove(rowToDel);
            basic_total = basic_total - Double.parseDouble("" + jTable1.getValueAt(rowToDel, 3));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(rowToDel);

            setGrandTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }//GEN-LAST:event_btnDeletActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        String work_order = txt_workorder.getSelectedItem().toString();
        String item_name = txt_itemname.getText().trim();
        String quantity = txt_quantity.getText().trim();
        String unit_rate = txt_unitrate.getText().trim();
        String amount = txt_amount.getText().trim();
        String sac = txt_sac.getText();
        String itemCode = txt_itemCode.getText();
        String drawingNumber = txt_drawingNumber.getText();
        String unitCode = lbl_unit.getText();

        if (quantity.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter quantity");
            return;
        }
        if (unit_rate.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Unit Rate");
            return;
        }
        if (itemCode.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Item Code");
            return;
        }
        if (item_name.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Item Name");
            return;
        }
        if (drawingNumber.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Drawing Number");
            return;
        }
        if (amount.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Amount");
            return;
        }
        if (sac.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter SAC Code");
            return;
        }
        int quant = 0;
        try {
            quant = Integer.parseInt(quantity);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid quanitity");
            return;
        }
        double unit_rate1;

        try {
            unit_rate1 = Double.parseDouble(unit_rate);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Enter valid price");
            return;
        }
        //        itemList.add(item);

        int LInvoiceQuant = itemQuantHashMap.get(item_name);
        int origQuant = origItemQuantHashMap.get(item_name);

        int remaining = origQuant - LInvoiceQuant;
        System.out.println("orig: " + origQuant);
        System.out.println("LInvoiceQ: " + LInvoiceQuant);
        if (remaining < quant) {
            JOptionPane.showMessageDialog(this, "Item canot be added.");
            if (remaining > 0) {
                JOptionPane.showMessageDialog(this, "You can add only " + remaining);
                txt_quantity.setText("" + remaining);
                double u = Double.parseDouble(txt_unitrate.getText().trim());
                double tot = u * remaining;
                txt_amount.setText(String.format("%.2f", tot));

            }
            return;
        }

        System.out.println("data got successfuly");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Vector row = new Vector();
        //row.add(work_order);
        row.add(item_name);

        row.add(quantity);
        row.add(unit_rate);
        row.add(String.format("%.2f", Double.parseDouble(amount)));
        row.add(itemCode);
        row.add(drawingNumber);

        row.add(unitCode);
        model.addRow(row);
        LaborInvoiceItems ll = new LaborInvoiceItems(item_name, quant, unit_rate1, Double.parseDouble(amount), itemCode, drawingNumber, unitCode);
        LabourItemList.add(ll);
        basic_total = basic_total + (Double.parseDouble(amount));
        setGrandTotal();
    }//GEN-LAST:event_btnAddActionPerformed

    private void txt_itemnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemnameKeyReleased

    private void txt_drawingNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drawingNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_drawingNumberKeyReleased

    private void txt_itemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemCodeKeyReleased

    private void txt_workorderItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_txt_workorderItemStateChanged
        System.out.println("index: " + txt_workorder.getSelectedIndex());
        PoItems items = poItemsList.get(txt_workorder.getSelectedIndex());
        txt_quantity.setText(items.getQuanatity());
        lbl_unit.setText(items.getUnitCode());
        txt_itemname.setText(items.getItem_name());
        txt_itemCode.setText(items.getItem_code());
        txt_drawingNumber.setText(items.getDrawing_number());
        txt_unitrate.setText(items.getUnitrate());
        calculateResult();
    }//GEN-LAST:event_txt_workorderItemStateChanged

    private void txt_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_amountFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_amountFocusGained

    private void txt_unitrateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_unitrateKeyReleased
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            calculateResult();
        } else {
            String str = txt_unitrate.getText();
            String result = str.substring(0, str.length() - 1);
            txt_unitrate.setText(result);
        }
    }//GEN-LAST:event_txt_unitrateKeyReleased

    private void txt_unitrateFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_unitrateFocusLost
        try {
            Double qua = Double.parseDouble(txt_quantity.getText());
            Double unit_rate = Double.parseDouble(txt_unitrate.getText());
            Double amnt = qua * unit_rate;
            txt_amount.setText(amnt + "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_txt_unitrateFocusLost

    private void txt_quantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_quantityKeyReleased
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            calculateResult();
        } else {
            String str = txt_quantity.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_quantity.setText(result);
        }
    }//GEN-LAST:event_txt_quantityKeyReleased

    private void txt_quantityFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_quantityFocusLost
        try {
            Double qua = Double.parseDouble(txt_quantity.getText());
            Double unit_rate = Double.parseDouble(txt_unitrate.getText());
            Double amnt = qua * unit_rate;
            txt_amount.setText(amnt + "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_txt_quantityFocusLost
    public void saveLabourId() {
        lIdObj = new LabourIdObj(labourId + "");
        final String financialYear = FiscalDate.getFinancialYear(Calendar.getInstance());
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");
//        String id = ref.push().getKey();
        ref.child("labourId").child(financialYear).child(labourId + "").setValue(lIdObj, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {

                    JOptionPane.showMessageDialog(null, "Labour Data Saved");
                    closeWindow();
                }
            }
        });

    }

    public void saveLabourInvoice(LaborInvoiceDetails lid) {
        String custId = cust.getId();
        String poId = po.getPoid();
        String challanId = challan.getChallanNo();
        //String lbId = lbl_invoiceid.getText().replaceAll("/", "-");
        String lbId = lid.getInvoice_id().replaceAll("/", "-");

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");
//        String id = ref.push().getKey();
        ref.child("SPOLabourInvoice").child(custId).child(lbId).setValue(lid, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {

                    JOptionPane.showMessageDialog(null, "Labour Data Saved");
                    closeWindow();
                }
            }
        });

    }

    public void closeWindow() {
        this.dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelet;
    private javax.swing.JButton btn_save;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblBasicTotal;
    private javax.swing.JLabel lbl_deliverydate;
    private javax.swing.JLabel lbl_grandtotal;
    private javax.swing.JLabel lbl_invoiceid;
    private javax.swing.JLabel lbl_inwords;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_unit;
    private javax.swing.JTextField txtChallanDate;
    private javax.swing.JTextField txtChallanNo;
    private javax.swing.JTextField txtRoundOff;
    private javax.swing.JTextField txt_Frght;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_billdate;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextArea txt_customerContact;
    private javax.swing.JTextField txt_date;
    private javax.swing.JTextField txt_drawingNumber;
    private javax.swing.JTextField txt_igst;
    private javax.swing.JTextField txt_itemCode;
    private javax.swing.JTextField txt_itemname;
    private javax.swing.JTextField txt_othercharges;
    private javax.swing.JTextField txt_pono;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_sac;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_unitrate;
    private javax.swing.JTextField txt_vehicleno;
    private javax.swing.JTextField txt_vendorcode;
    private javax.swing.JComboBox<String> txt_workorder;
    // End of variables declaration//GEN-END:variables
}
