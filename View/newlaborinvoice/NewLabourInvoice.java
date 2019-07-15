/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.newlaborinvoice;

import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrder;
import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrderVars;
import com.mileset.neeleshengineers.View.Report.LabourInvoice;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceItems;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.NumberToWord;
import com.mileset.neeleshengineers.util.UtilVars;
import com.mileset.neeleshengineers.util.Utility;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rahul
 */
public class NewLabourInvoice extends javax.swing.JDialog {

    /**
     * Creates new form NewLabourInvoice
     */
    double basic_total;

    int count;
    String dte;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<Items> itemList;
    String cust_id;
    String invoice_id;

    private HashMap<String, Integer> maxItemsMap = new HashMap<>();
    private HashMap<String, Integer> currentsItemsMap = new HashMap<>();
    private String custname;
    private Po currentPo;
    private ArrayList<Invoice_details> invoices = new ArrayList<>();
    private boolean updateFlag = false;
    private Invoice_details invoiceToChange;

    private boolean isUpdate = false;
    private LaborInvoiceDetails updatingLaborInvoice;

    public NewLabourInvoice(LaborInvoiceDetails lbd) {
        initComponents();
        this.setTitle("Update Labour Invoice");
        updatingLaborInvoice = lbd;
        System.out.println("i am from updating customer" + lbd);
        btn_save.setText("Update");
        isUpdate = true;
        this.setModal(true);
//        this.setAlwaysOnTop(true);
        basic_total = 0;
        ArrayList<LaborInvoiceItems> lbrItems = lbd.getItemList();
        setToTable(lbrItems);
        lbl_invoiceid.setText(lbd.getInvoice_id());
        txt_billdate.setText(lbd.getDate());
        txtChallanDate.setText(lbd.getChallanDate());
        txtChallanNo.setText(lbd.getChallanNo());
        txt_othercharges.setText(lbd.getOthercharges() + "");
        txt_customerContact.setText(lbd.getCname() + "\n" + UtilVars.getCustomer().getAddress());
        lbl_invoiceid.setText(lbd.getInvoice_id());
        txt_quantity.setText(lbd.getItemList().get(0).getQuantity() + "");
        txt_unitrate.setText(lbd.getItemList().get(0).getUnit_rate() + "");
        txt_itemname.setText(lbd.getItemList().get(0).getItem_name() + "");
        txt_itemCode.setText(lbd.getItemList().get(0).getItemCode() + "");
        txt_drawingNumber.setText(lbd.getItemList().get(0).getDrawingNumber() + "");
        txt_amount.setText(lbd.getItemList().get(0).getAmount() + "");
        //txt_hsn.setText(lbd.getItemList().get(0).getHSN() + "");
        lbl_total.setText(lbd.getBasicTotal() + "");
        txt_Frght.setText(lbd.getOthercharges() + "");
        txt_cgst.setText(lbd.getCgst() + "");
        txt_sgst.setText(lbd.getSgst() + "");
        txt_igst.setText(lbd.getIgst() + "");
        lbl_grandtotal.setText(lbd.getGrandTotal() + "");
        txt_vendorcode.setText(lbd.getVendorCode());
        txt_pono.setText(lbd.getPONo() + "");
        txt_date.setText(lbd.getDte2());
        Po po1 = UtilVars.getPo();
        txt_vehicleno.setText(lbd.getVehicleNo());
        txt_workorder.removeAllItems();
        //lbl_inwords.setText(NumberToWord.NumberToWord(lbd.getGrandTotal()+""));
        for (PoItems items : po1.getItems()) {
            txt_workorder.addItem(items.getWork_order().toString());

        }
        currentPo = UtilVars.getPo();
        txt_workorder.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int index = txt_workorder.getSelectedIndex();
                String[] arr1 = currentPo.getItems().get(index).getQuanatity().split(" ");
                String quant = arr1[0];
                txt_quantity.setText(quant);
                //txt_Frght.setText(currentPo.getItems().get(index).get);
                txt_unitrate.setText(currentPo.getItems().get(index).getUnitrate());
                txt_amount.setText(currentPo.getItems().get(index).getAmount());
                txt_hsn.setText(currentPo.getItems().get(index).getHsn());
                txt_itemname.setText(currentPo.getItems().get(index).getItem_name());
                txt_itemCode.setText(currentPo.getItems().get(index).getItem_code());
                txt_drawingNumber.setText(currentPo.getItems().get(index).getDrawing_number());
                lbl_unit.setText(currentPo.getItems().get(index).getUnitCode());
            }
        });
    }

    private void setToTable(ArrayList<LaborInvoiceItems> items) {
        //      data = firebaseMethods.getAllCustomers();
        System.out.println("setToTable called");
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        for (LaborInvoiceItems i : items) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(i.getItem_name());
            //row.add(i.getHSN());
            row.add(i.getQuantity());
            row.add(i.getUnit_rate());
            basic_total = basic_total + i.getAmount();
            row.add(i.getAmount());
            row.add(i.getItemCode());
            row.add(i.getDrawingNumber());
            row.add(i.getUnitCode());
            model.addRow(row);
        }
    }

    public NewLabourInvoice(String generatedId) {
        // super(parent, modal);
        initComponents();
        this.setTitle(UtilVars.getCustomer().getName() + ", " + UtilVars.getCustomer().getAddress());
        lbl_invoiceid.setText(generatedId);
        txt_othercharges.setText("0");
        txt_customerContact.setText(UtilVars.getCustomer().getName() + ",\n" + UtilVars.getCustomer().getAddress());
        Po po1 = UtilVars.getPo();
        currentPo = po1;
        txt_vendorcode.setText(UtilVars.getCustomer().getVendor_code());
        txt_pono.setText(currentPo.getPoid());
        txt_date.setText(currentPo.getBill_date());

        txt_workorder.removeAllItems();
        String[] arr1 = currentPo.getItems().get(0).getQuanatity().split(" ");
        String quant = arr1[0];
        txt_quantity.setText(quant);
        txt_unitrate.setText(currentPo.getItems().get(0).getUnitrate());
        txt_amount.setText(currentPo.getItems().get(0).getAmount());
        txt_hsn.setText(currentPo.getItems().get(0).getHsn());
        txt_itemname.setText(currentPo.getItems().get(0).getItem_name());
        txt_itemCode.setText(currentPo.getItems().get(0).getItem_code());
        txt_drawingNumber.setText(currentPo.getItems().get(0).getDrawing_number());
        lbl_unit.setText(arr1[1]);

        txt_cgst.setText(currentPo.getCgst());
        txt_sgst.setText(currentPo.getSgst());
        txt_igst.setText(currentPo.getIgst());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        txt_billdate.setText("" + dtf.format(now));
        lbl_deliverydate.setText("Delivery Date :" + po1.getDelivery_date());

        for (PoItems items : po1.getItems()) {
            txt_workorder.addItem(items.getWork_order().toString());

        }
        txt_workorder.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int index = txt_workorder.getSelectedIndex();
                String[] arr1 = currentPo.getItems().get(index).getQuanatity().split(" ");
                String quant = arr1[0];
                txt_quantity.setText(quant);
                //txt_Frght.setText(currentPo.getItems().get(index).get);
                txt_unitrate.setText(currentPo.getItems().get(index).getUnitrate());
                txt_amount.setText(currentPo.getItems().get(index).getAmount());
                txt_hsn.setText(currentPo.getItems().get(index).getHsn());
                txt_itemname.setText(currentPo.getItems().get(index).getItem_name());
                txt_itemCode.setText(currentPo.getItems().get(index).getItem_code());
                txt_drawingNumber.setText(currentPo.getItems().get(index).getDrawing_number());
                lbl_unit.setText(arr1[1]);
            }
        });

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_unitrate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_hsn = new javax.swing.JTextField();
        txt_workorder = new javax.swing.JComboBox<>();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel1.setText("Work Order No");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel2.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
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

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setText("Unit Rate");

        txt_unitrate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
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

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setText("Amount");

        txt_amount.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_amount.setText("jTextField4");
        txt_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_amountFocusGained(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel19.setText("HSN");

        txt_hsn.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_hsn.setText("jTextField10");

        txt_workorder.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_workorder.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txt_itemCode.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_itemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemCodeKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel9.setText("Item Code");

        txt_drawingNumber.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_drawingNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_drawingNumberKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel11.setText("Drawing Number");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel16.setText("Name And Address of Supplier");

        txt_customerContact.setColumns(20);
        txt_customerContact.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_customerContact.setRows(5);
        jScrollPane2.setViewportView(txt_customerContact);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel18.setText("Item Name");

        txt_itemname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_itemname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemnameKeyReleased(evt);
            }
        });

        lbl_unit.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_unit.setText("Unit");

        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelet.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnDelet.setText("Delete");
        btnDelet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_itemname)
                                .addComponent(txt_workorder, 0, 105, Short.MAX_VALUE))
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_itemCode)
                            .addComponent(txt_quantity)
                            .addComponent(txt_hsn, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbl_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_drawingNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnAdd)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnDelet)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_Frght)))
                                .addGap(10, 10, 10))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(lbl_unit))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_drawingNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel11))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(txt_itemCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_hsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnAdd)
                                    .addComponent(btnDelet)
                                    .addComponent(txt_Frght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(txt_itemname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(16, 16, 16))))
        );

        txtChallanNo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        lbl_invoiceid.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_invoiceid.setText("jLabel11");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel10.setText("Invoice Id");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel12.setText("Bill Date");

        txt_billdate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_billdate.setText("jTextField9");

        txtChallanDate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel13.setText("Challan Date");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel14.setText("For Eg: 25/3/1992");

        lbl_deliverydate.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_deliverydate.setText("Delivery Date");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel15.setText("Challan No.");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "hsn", "Quantity", "Unit Rate", "Total", "Item Code", "Drawing Number", "Unit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel5.setText("CGST");

        txt_sgst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
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

        txt_cgst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
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

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel6.setText("SGST");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel7.setText("IGST");

        txt_igst.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
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

        txt_othercharges.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_othercharges.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_otherchargesKeyReleased(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel17.setText("Freight Charges");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel8.setText("Total");

        lbl_total.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_total.setText("basic total");
        lbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_totalMouseReleased(evt);
            }
        });

        lbl_inwords.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_inwords.setText("jLabel18");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel20.setText("In Words");

        lbl_grandtotal.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lbl_grandtotal.setText("jLabel16");
        lbl_grandtotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_grandtotalMouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel21.setText("Grand Total");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel22.setText("Vendor Code");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel23.setText("P.O. No.");

        jLabel24.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel24.setText("P.O Date");

        jLabel25.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel25.setText("Vehicle No");

        txt_vendorcode.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_vendorcode.setText("jTextField11");

        txt_pono.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_pono.setText("jTextField12");

        txt_date.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_date.setText("jTextField13");

        txt_vehicleno.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel17))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_othercharges, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(119, 119, 119)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel24))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_pono, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                            .addComponent(txt_date)
                                            .addComponent(txt_vehicleno)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel22)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_vendorcode, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(20, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btn_save, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_inwords, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbl_total))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_othercharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txt_vendorcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txt_pono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(txt_vehicleno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_save)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_inwords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtChallanNo, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_invoiceid, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(lbl_deliverydate, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(265, 265, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 52, Short.MAX_VALUE))))
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
                                .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_invoiceid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChallanNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    private void calculateResult() {
        try {
            txt_amount.setText((Integer.parseInt(txt_quantity.getText().toString()) * Integer.parseInt(txt_unitrate.getText().toString())) + "");
        } catch (Exception ex) {
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
    private void txt_unitrateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_unitrateKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            calculateResult();
        } else {
            String str = txt_unitrate.getText();
            String result = str.substring(0, str.length() - 1);
            txt_unitrate.setText(result);
        }
    }//GEN-LAST:event_txt_unitrateKeyReleased

    private void txt_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_amountFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_amountFocusGained

    private void txt_itemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemCodeKeyReleased

    private void txt_drawingNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drawingNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_drawingNumberKeyReleased

    private void txt_itemnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemnameKeyReleased

    private void txt_sgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_sgstFocusLost

    }//GEN-LAST:event_txt_sgstFocusLost

    private void txt_sgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sgstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_sgstKeyReleased

    private void txt_cgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cgstFocusLost

    }//GEN-LAST:event_txt_cgstFocusLost

    private void txt_cgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cgstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstKeyReleased

    private void txt_igstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_igstFocusLost

    }//GEN-LAST:event_txt_igstFocusLost

    private void txt_igstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_igstKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_igstKeyReleased

    private void txt_otherchargesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherchargesKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_otherchargesKeyReleased

    private void lbl_totalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_totalMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_totalMouseReleased

    private void lbl_grandtotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_grandtotalMouseClicked

    }//GEN-LAST:event_lbl_grandtotalMouseClicked
    private void setGrandTotal() {
        lbl_total.setText("" + basic_total);
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
            finalvalue = amount + cgstval + sgstval + igstval;

        } catch (Exception ex) {
            System.out.println("exception occured within setGrandTotal()" + ex.getMessage());
            ex.printStackTrace();
        }
        if (finalvalue != 0) {
            Double dVal = (Double) finalvalue;
            int intVal = dVal.intValue();
            String words = NumberToWord.NumberToWord(intVal + "");
            lbl_grandtotal.setText(intVal + "");
            lbl_inwords.setText(words);
        } else {
            lbl_grandtotal.setText("0");
        }
    }
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        String work_order = txt_workorder.getSelectedItem().toString();
        String item_name = txt_itemname.getText().trim();
        String quantity = txt_quantity.getText().trim();
        String unit_rate = txt_unitrate.getText().trim();
        String amount = txt_amount.getText().trim();
        String hsn = txt_hsn.getText();
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
        if (hsn.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter HSN");
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
        System.out.println("data got successfuly");

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Vector row = new Vector();
        //    row.add(work_order);
        row.add(item_name);
        row.add(hsn);
        row.add(quantity);
        row.add(unit_rate);
        row.add(amount);
        row.add(itemCode);
        row.add(drawingNumber);

        row.add(unitCode);
        model.addRow(row);

        basic_total = basic_total + Double.parseDouble(amount);

        setGrandTotal();
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletActionPerformed
        // TODO add your handling code here:
        int rowToDel = jTable1.getSelectedRow();
        System.out.println("selected row is" + rowToDel);
        if (rowToDel > -1) {

            basic_total = basic_total - Double.parseDouble("" + jTable1.getValueAt(rowToDel, 4));
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(rowToDel);

            setGrandTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }//GEN-LAST:event_btnDeletActionPerformed
    private ArrayList<LaborInvoiceItems> getJTableData() {
        System.out.println("getJTableData Called");
        ArrayList<LaborInvoiceItems> items = new ArrayList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (model != null) {
            for (int count = 0; count < model.getRowCount(); count++) {
                //numdata.add();
                String item_name = model.getValueAt(count, 0).toString();
                String hsn = model.getValueAt(count, 1).toString();
                int quantity = Integer.valueOf(model.getValueAt(count, 2).toString());
                Double unit_rate = Double.valueOf(model.getValueAt(count, 3).toString());
                Double total = Double.valueOf(model.getValueAt(count, 4).toString());
                String itemCode = model.getValueAt(count, 5).toString();
                String drawingNumber = model.getValueAt(count, 6).toString();
                LaborInvoiceItems item = new LaborInvoiceItems();
                String unitCode = model.getValueAt(count, 7).toString();
                item.setItemCode(itemCode);
                item.setDrawingNumber(drawingNumber);
                item.setAmount(total);
                //item.setHSN(hsn);
                item.setItem_name(item_name);
                item.setQuantity(quantity);
                item.setUnit_rate(unit_rate);
                item.setUnitCode(unitCode);
                items.add(item);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please add items first");
        }
        System.out.println(items);
        return items;
    }
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String invoice_id = lbl_invoiceid.getText();
        String dte = txt_billdate.getText();
        String challanNo = txtChallanNo.getText();
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
        ArrayList<LaborInvoiceItems> invoiceItems = getJTableData();

        LaborInvoiceDetails invoice_details = new LaborInvoiceDetails();
        invoice_details.setBasicTotal(basic);
        invoice_details.setCgst(cgst);
        invoice_details.setDate(dte);
        invoice_details.setDte2(dte2);
        invoice_details.setGrandTotal(grand);
        invoice_details.setIgst(igst);
        invoice_details.setInvoice_id(invoice_id);
        invoice_details.setItemList(invoiceItems);
        invoice_details.setOthercharges(other);
        invoice_details.setPONo(pono);
        invoice_details.setSgst(sgst);
        invoice_details.setVehicleNo(vehicleNo);
        invoice_details.setVendorCode(vendorCode);
        invoice_details.setChallanNo(challanNo);
        invoice_details.setChallanDate(txtChallanDate.getText());
        invoice_details.setCname(UtilVars.getCustomer().getName() + "\n" + UtilVars.getCustomer().getAddress());
        String dDate = UtilVars.getPo().getDelivery_date();
        String bDate = txt_billdate.getText().toString();
        Date dd = Utility.convertStringToDate(dDate);
        Date bd = Utility.convertStringToDate(bDate);
        if (bd.after(dd)) {
            JOptionPane.showMessageDialog(this, "Bill date should be less than Delivery Date");
            return;
        }
        if (isUpdate) {
            invoice_details.setFid(updatingLaborInvoice.getFid());
            LabourInvoice labour = new LabourInvoice(invoice_details);
            FirebaseMethods.updateLaborInvoice(invoice_details, this);
            this.dispose();
        } else {
            LabourInvoice labour = new LabourInvoice(invoice_details);
            this.dispose();
            FirebaseMethods.addNewLaborInvoice(invoice_details, this);
        }

    }//GEN-LAST:event_btn_saveActionPerformed

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
            java.util.logging.Logger.getLogger(NewLabourInvoice.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewLabourInvoice.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewLabourInvoice.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewLabourInvoice.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                /*NewLabourInvoice dialog = new NewLabourInvoice(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel lbl_deliverydate;
    private javax.swing.JLabel lbl_grandtotal;
    private javax.swing.JLabel lbl_invoiceid;
    private javax.swing.JLabel lbl_inwords;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_unit;
    private javax.swing.JTextField txtChallanDate;
    private javax.swing.JTextField txtChallanNo;
    private javax.swing.JTextField txt_Frght;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_billdate;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextArea txt_customerContact;
    private javax.swing.JTextField txt_date;
    private javax.swing.JTextField txt_drawingNumber;
    private javax.swing.JTextField txt_hsn;
    private javax.swing.JTextField txt_igst;
    private javax.swing.JTextField txt_itemCode;
    private javax.swing.JTextField txt_itemname;
    private javax.swing.JTextField txt_othercharges;
    private javax.swing.JTextField txt_pono;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_unitrate;
    private javax.swing.JTextField txt_vehicleno;
    private javax.swing.JTextField txt_vendorcode;
    private javax.swing.JComboBox<String> txt_workorder;
    // End of variables declaration//GEN-END:variables
}
