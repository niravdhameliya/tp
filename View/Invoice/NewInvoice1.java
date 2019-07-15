/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Invoice;

//import controller.Database.Fetch.getInvoiceCount;
//import controller.Database.INsert.SaveInvoice;
//import controller.Database.INsert.SaveInvoiceItems;
//import controller.Database.INsert.SaveInvoiceMaster;
import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrder;
import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrderVars;
import com.mileset.neeleshengineers.View.Report.ConstructorBill;
import com.mileset.neeleshengineers.View.Report.TaxInvoice;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.NewInvoiceUtilVars;
import com.mileset.neeleshengineers.util.NumberToWord;
import com.mileset.neeleshengineers.util.UtilVars;
import com.mileset.neeleshengineers.util.Utility;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
//import modal.operations.NumberToWordsConverter;

public class NewInvoice1 extends javax.swing.JDialog {

    /**
     * Creates new form NewInvoice1
     */
    double basic_total;
    //getInvoiceCount invoiceCount;
    int count;
    String dte;
    private static final DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    ArrayList<Items> itemList;
    String cust_id;
    String invoice_id;
    private ArrayList<Items> publicInvoiceItems = new ArrayList<>();
    private HashMap<String, Integer> maxItemsMap = new HashMap<>();
    private HashMap<String, Integer> currentsItemsMap = new HashMap<>();
    private String custname;
    private Po currentPo;

    public NewInvoice1() {
    }

    private ArrayList<Invoice_details> invoices = new ArrayList<>();

    public NewInvoice1(String invoice_id, Po po, ArrayList<Invoice_details> invoices) {
        try {
            initComponents();
            jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
            this.jTable1.setDefaultEditor(Object.class, null);
            this.invoices = invoices;
            txt_customerContact.setText(UtilVars.getCustomer().getName() + ",\n" + UtilVars.getCustomer().getAddress());
            currentPo = po;
            System.out.println("Current po from NewInvoice1 " + po);
            System.out.println("All invoices are here " + invoices.size());
            this.cust_id = po.getCust_id();
            txt_vendorcode.setText(UtilVars.getCustomer().getVendor_code());
            txt_cgst.setText(po.getCgst());
            txt_sgst.setText(po.getSgst());
            txt_igst.setText(po.getIgst());
            this.invoice_id = invoice_id;
            this.custname = UtilVars.getCustomer().getName();
            lbl_deliverydate.setText("Delivery Date :" + po.getDelivery_date());
            clear();
            lbl_invoiceid.setText(invoice_id);
            basic_total = 0;
            Date date = new Date();
            System.out.println(sdf.format(date));
            dte = sdf.format(date);
            txt_billdate.setText(dte);
            txt_date.setText("");
            itemList = new ArrayList<>();
            txt_workorder.removeAllItems();
            for (PoItems items : po.getItems()) {
                txt_workorder.addItem(items.getWork_order().toString());
            }
            txt_itemname.setText(currentPo.getItems().get(0).getItem_name());
            String[] arr = currentPo.getItems().get(0).getQuanatity().split(" ");
            txt_quantity.setText(arr[0]);
            lbl_unit.setText(arr[1]);
            txt_unitrate.setText(currentPo.getItems().get(0).getUnitrate());
            txt_amount.setText(currentPo.getItems().get(0).getAmount());
            txt_hsn.setText(currentPo.getItems().get(0).getHsn());
            txt_itemCode.setText(currentPo.getItems().get(0).getItem_code());
            txt_drawingNumber.setText(currentPo.getItems().get(0).getDrawing_number());
            txt_workorder.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    int index = txt_workorder.getSelectedIndex();
                    String[] arr1 = currentPo.getItems().get(index).getQuanatity().split(" ");
                    String quant = arr1[0];
                    txt_quantity.setText(quant);
                    txt_unitrate.setText(currentPo.getItems().get(index).getUnitrate());
                    txt_amount.setText(currentPo.getItems().get(index).getAmount());
                    txt_hsn.setText(currentPo.getItems().get(index).getHsn());
                    txt_itemname.setText(currentPo.getItems().get(index).getItem_name());
                    txt_itemCode.setText(currentPo.getItems().get(index).getItem_code());
                    txt_drawingNumber.setText(currentPo.getItems().get(index).getDrawing_number());
                    lbl_unit.setText(arr1[1]);
                }
            });
            txt_pono.setText(po.getPoid());
            txt_pono.setEnabled(false);
            txt_date.setText(po.getBill_date());
            txt_date.setEnabled(false);
            this.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {

                    ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                    sap.setVisible(true);

                }

            });
            jTable1.setDefaultEditor(Object.class, null);
            addListnerToJTable();
            fillMaxItemsMap();
            fillCurrentItemsMap();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private boolean updateFlag = false;
    private Invoice_details invoiceToChange;

    public NewInvoice1(Invoice_details invoice, ArrayList<Invoice_details> invoices) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        this.jTable1.setDefaultEditor(Object.class, null);
        updateFlag = true;
        invoiceToChange = invoice;
        System.out.println("This is invoice to change " + invoiceToChange);
        lbl_invoiceid.setText(invoiceToChange.getInvoice_id());
        txt_billdate.setText(invoiceToChange.getDate());
        txt_customerContact.setText(invoiceToChange.getCustomerContact());
        lbl_total.setText(invoiceToChange.getBasicTotal() + "");
        txt_othercharges.setText(invoiceToChange.getOthercharges() + "");
        txt_cgst.setText(invoiceToChange.getCgst() + "");
        txt_sgst.setText(invoiceToChange.getSgst() + "");
        txt_igst.setText(invoiceToChange.getIgst() + "");
        lbl_grandtotal.setText(invoiceToChange.getGrandTotal() + "");
        txt_vendorcode.setText(invoiceToChange.getVendorCode());
        txtNote.setText(invoiceToChange.getNote());
        txt_pono.setText(invoiceToChange.getPONo());
        txt_pono.setEditable(false);
        txt_date.setText(invoiceToChange.getDte2());
        setToTable(invoiceToChange.getItemList());
        txt_workorder.removeAllItems();
        final Po currentPo = UtilVars.getPo();
        for (PoItems items : currentPo.getItems()) {
            txt_workorder.addItem(items.getWork_order().toString());
        }
        txt_workorder.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int index = txt_workorder.getSelectedIndex();
                String[] arr1 = currentPo.getItems().get(index).getQuanatity().split(" ");
                String quant = arr1[0];
                txt_quantity.setText(quant);
                txt_unitrate.setText(currentPo.getItems().get(index).getUnitrate());
                txt_amount.setText(currentPo.getItems().get(index).getAmount());
                txt_hsn.setText(currentPo.getItems().get(index).getHsn());
                txt_itemname.setText(currentPo.getItems().get(index).getItem_name());
                txt_itemCode.setText(currentPo.getItems().get(index).getItem_code());
                txt_drawingNumber.setText(currentPo.getItems().get(index).getDrawing_number());
                lbl_unit.setText(arr1[1]);
            }
        });
        txt_itemname.setText(currentPo.getItems().get(0).getItem_name());
        String[] arr = currentPo.getItems().get(0).getQuanatity().split(" ");
        txt_quantity.setText(arr[0]);
        lbl_unit.setText(arr[1]);
        txt_unitrate.setText(currentPo.getItems().get(0).getUnitrate());
        txt_amount.setText(currentPo.getItems().get(0).getAmount());
        txt_hsn.setText(currentPo.getItems().get(0).getHsn());
        txt_itemCode.setText(currentPo.getItems().get(0).getItem_code());
        txt_drawingNumber.setText(currentPo.getItems().get(0).getDrawing_number());
        this.currentPo = currentPo;
        this.invoices = invoices;
        fillMaxItemsMap();
        fillCurrentItemsMap();
        btn_save.setText("Update");

    }

    private void setToTable(ArrayList<Items> invoiceItems) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        for (Items item : invoiceItems) {
            Vector row = new Vector();
            row.add(item.getWork_order());
            row.add(item.getItem_name());
            row.add(item.getHsn());
            row.add(item.getQuantity());
            row.add(item.getUnit_rate());
            row.add(item.getAmount());
            row.add(item.getItemCode());
            row.add(item.getDrawingNumber());
            row.add(item.getUnitCode());
            basic_total = basic_total + item.getAmount();
            publicInvoiceItems.add(item);
            model.addRow(row);
            setGrandTotal();
        }

    }

    private void setSelectedInvoiceItemsObject() {
        try {
            int row = jTable1.getSelectedRow();
            txt_itemCode.setText(jTable1.getValueAt(row, 6) + "");
            txt_drawingNumber.setText(jTable1.getValueAt(row, 7) + "");
            txt_workorder.setSelectedItem(jTable1.getValueAt(row, 0) + "");
            txt_hsn.setText(jTable1.getValueAt(row, 2) + "");
            String[] quantString = jTable1.getValueAt(row, 3).toString().split(" ");
            txt_quantity.setText(quantString[0]);
            lbl_unit.setText(quantString[1]);
            txt_unitrate.setText(jTable1.getValueAt(row, 4) + "");
            txt_amount.setText(jTable1.getValueAt(row, 5) + "");
            txt_workorder.requestFocus();
            Items item1 = new Items();
            item1.setItem_name(jTable1.getValueAt(row, 1) + "");
            if (publicInvoiceItems.contains(item1)) {
                publicInvoiceItems.remove(item1);
                System.out.println("item removed rom itemList " + item1);
            }
        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please select id to update");
        }
        //setItemVisibility(true);
    }

    private void deletSelectedRow() {
        int rowToDel = jTable1.getSelectedRow();
        if (rowToDel > -1) {
            double val = Double.parseDouble(jTable1.getModel().getValueAt(rowToDel, 4).toString());
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(rowToDel);
            basic_total = basic_total - val;
            lbl_total.setText(basic_total + "");
            setGrandTotal();
        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
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

    private void fillMaxItemsMap() {
        ArrayList<PoItems> poitems = currentPo.getItems();
        System.out.println("printing from poitems" + poitems);
        for (PoItems item : poitems) {
            System.out.println("printing po item fro fillMaxItemsMap " + item);
            String quantityString = item.getQuanatity().split(" ")[0];
            int quant = Integer.parseInt(quantityString);
            System.out.println("work order " + item.getWork_order() + " quantity" + quant);
            maxItemsMap.put(item.getWork_order(), quant);
            currentsItemsMap.put(item.getWork_order(), 0);

        }
        System.out.println("This is maxItemMap " + maxItemsMap);
    }

    private void fillCurrentItemsMap() {
        for (Invoice_details invoice : invoices) {
            System.out.println("invoice found");
            if (invoice.getPONo().equals(currentPo.getPoid())) {
                for (Items invoiceItems : invoice.getItemList()) {
                    if (currentsItemsMap.containsKey(invoiceItems.getWork_order())) {
                        currentsItemsMap.put(invoiceItems.getWork_order(),
                                currentsItemsMap.get((invoiceItems.getWork_order())) + invoiceItems.getQuantity());
                    } else {
                        currentsItemsMap.put(invoiceItems.getWork_order(), invoiceItems.getQuantity());
                    }
                }
            }
        }
        System.out.println("Current hash map is " + currentsItemsMap);
    }

    public void clear() {
        //txt_itemname.setText("");
        txt_quantity.setText("");
        txt_unitrate.setText("");
        txt_amount.setText("");
//        txt_cgst.setText("9");
//        txt_sgst.setText("9");
//        txt_igst.setText("0");
        txt_othercharges.setText("0");
        lbl_unit.setText("");

//        txt_vendorcode.setText("");
        txt_pono.setText("");
        //txt_vehicleno.setText("");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        txt_billdate.setText("" + dtf.format(now));

        lbl_invoiceid.setText("");
        lbl_grandtotal.setText("Click Here");
        lbl_inwords.setText("");
        lbl_total.setText("");

        txt_hsn.setText("");
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
        btnAdd = new javax.swing.JButton();
        btnDelet = new javax.swing.JButton();
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
        jLabel14 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        lbl_inwords = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lbl_grandtotal = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txt_vendorcode = new javax.swing.JTextField();
        txt_pono = new javax.swing.JTextField();
        txt_date = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtNote = new javax.swing.JTextArea();
        txtRoundOff = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lbl_invoiceid = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_billdate = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        lbl_deliverydate = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Invoice");
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setText("Work Order No");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_quantityKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setText("Unit Rate");

        txt_unitrate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_unitrate.setText("jTextField3");
        txt_unitrate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_unitrateKeyReleased(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel4.setText("Amount");

        txt_amount.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_amount.setText("jTextField4");
        txt_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_amountFocusGained(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel19.setText("HSN");

        txt_hsn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_hsn.setText("jTextField10");

        btnAdd.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnDelet.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btnDelet.setText("Delete");
        btnDelet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletActionPerformed(evt);
            }
        });

        txt_workorder.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_workorder.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        txt_workorder.setMaximumSize(new java.awt.Dimension(200, 30));
        txt_workorder.setMinimumSize(new java.awt.Dimension(200, 30));
        txt_workorder.setPreferredSize(new java.awt.Dimension(200, 30));
        txt_workorder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_workorderActionPerformed(evt);
            }
        });

        txt_itemCode.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_itemCode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemCodeKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel9.setText("Item Code");

        txt_drawingNumber.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_drawingNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_drawingNumberKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setText("Drawing Number");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel16.setText("Name And Address of Supplier");

        txt_customerContact.setColumns(20);
        txt_customerContact.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_customerContact.setRows(5);
        jScrollPane2.setViewportView(txt_customerContact);

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel18.setText("Item Name");

        txt_itemname.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_itemname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemnameKeyReleased(evt);
            }
        });

        lbl_unit.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_unit.setText("Unit");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_itemname)
                                .addComponent(txt_workorder, 0, 105, Short.MAX_VALUE))
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_itemCode)
                            .addComponent(txt_quantity)
                            .addComponent(txt_hsn, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelet)
                                .addGap(8, 8, 8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_drawingNumber))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lbl_unit, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 22, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnDelet)
                                    .addComponent(btnAdd))))
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Work Order Number", "Item Name", "hsn", "Quantity", "Unit Rate", "Total", "Item Code", "Drawing Number", "Unit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setText("CGST(%)");

        txt_sgst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
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

        txt_cgst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_cgst.setText("jTextField5");
        txt_cgst.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_cgstFocusLost(evt);
            }
        });
        txt_cgst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cgstActionPerformed(evt);
            }
        });
        txt_cgst.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cgstKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel6.setText("SGST(%)");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setText("IGST(%)");

        txt_igst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
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

        txt_othercharges.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_othercharges.setText("jTextField8");
        txt_othercharges.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_otherchargesKeyReleased(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel14.setText("Other Charges");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel8.setText("Total");

        lbl_total.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_total.setText("basic total");
        lbl_total.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lbl_totalMouseReleased(evt);
            }
        });

        lbl_inwords.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_inwords.setText("jLabel18");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel17.setText("In Words");

        lbl_grandtotal.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_grandtotal.setText("jLabel16");
        lbl_grandtotal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_grandtotalMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel15.setText("Grand Total");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel20.setText("Vendor Code");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel21.setText("P.O. No.");

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel22.setText("Date");

        txt_vendorcode.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_vendorcode.setText("jTextField11");

        txt_pono.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_pono.setText("jTextField12");

        txt_date.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_date.setText("jTextField13");

        jLabel23.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel23.setText("Note");

        txtNote.setColumns(20);
        txtNote.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtNote.setRows(5);
        jScrollPane3.setViewportView(txtNote);

        txtRoundOff.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txtRoundOff.setText("0");
        txtRoundOff.setEnabled(false);

        jLabel26.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel26.setText("Round Off");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(47, 47, 47))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel6)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel14)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(7, 7, 7)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtRoundOff, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_total, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txt_sgst, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                                .addComponent(txt_cgst, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                .addComponent(txt_othercharges, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                            .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(20, 20, 20)
                                .addComponent(txt_vendorcode))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel23)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21)
                                            .addComponent(jLabel22))
                                        .addGap(48, 48, 48)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txt_pono)
                                            .addComponent(txt_date)))))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_inwords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(lbl_total))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_othercharges, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRoundOff, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_grandtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txt_vendorcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txt_pono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_date, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel22))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_inwords, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel10.setText("Invoice Id");

        lbl_invoiceid.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_invoiceid.setText("jLabel11");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel12.setText("Bill Date");

        txt_billdate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_billdate.setText("jTextField9");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel13.setText("For Eg: 25/3/1992");

        lbl_deliverydate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        lbl_deliverydate.setText("Delivery Date");

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 588, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_save)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_invoiceid, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(lbl_deliverydate, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbl_invoiceid, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13)
                        .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbl_deliverydate))
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_save)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
    private void txt_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_amountFocusGained
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_amountFocusGained

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        String work_order = txt_workorder.getSelectedItem().toString();
        String item_name = txt_itemname.getText().trim();
        String quantity = txt_quantity.getText().trim();
        String unit_rate = txt_unitrate.getText().trim();
        String amount = txt_amount.getText().trim();
        String hsn = txt_hsn.getText();
        String itemCode = txt_itemCode.getText();
        String drawingNumber = txt_drawingNumber.getText();
        String unitCode1 = lbl_unit.getText().toString();
        System.out.println("selected unit code is " + unitCode1);

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
        Items item = new Items(item_name, quant, unit_rate1, Double.parseDouble(amount), hsn, itemCode, drawingNumber, work_order, lbl_unit.getText(), unitCode1);
        if (!publicInvoiceItems.contains(item)) {
            int currentItems = 0;
            System.out.println("Printing current itemsMaps form add button " + currentsItemsMap);
            try {
                currentItems = currentsItemsMap.get(item.getWork_order()) + quant;
            } catch (NullPointerException ex) {
                return;
            }
            int maxitems = maxItemsMap.get(item.getWork_order());
            if (maxitems < currentItems) {
                JOptionPane.showMessageDialog(this, "You can only generate invoice for " + maxItemsMap.get(item.getWork_order()) + " items. You already generated for " + currentsItemsMap.get(item.getWork_order()));
                return;
            } else {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                Vector row = new Vector();
                row.add(work_order);
                row.add(item_name);
                row.add(hsn);
                row.add(quantity);
                row.add(unit_rate);
                row.add(amount);
                row.add(itemCode);
                row.add(drawingNumber);
                row.add(unitCode1);
                model.addRow(row);
                basic_total = basic_total + Double.parseDouble(amount);
                lbl_total.setText(basic_total + "");
                setGrandTotal();
                txt_hsn.setText("");
                txt_quantity.setText("");
                txt_unitrate.setText("");
                txt_amount.setText("");
                txt_itemCode.setText("");
                txt_drawingNumber.setText("");
                txt_workorder.setSelectedIndex(0);
                txt_itemname.setText(currentPo.getItems().get(0).getItem_name());
                String[] arr = currentPo.getItems().get(0).getQuanatity().split(" ");
                txt_quantity.setText(arr[0]);
                lbl_unit.setText(arr[1]);
                txt_unitrate.setText(currentPo.getItems().get(0).getUnitrate());
                txt_amount.setText(currentPo.getItems().get(0).getAmount());
                txt_hsn.setText(currentPo.getItems().get(0).getHsn());
                txt_itemCode.setText(currentPo.getItems().get(0).getItem_code());
                txt_drawingNumber.setText(currentPo.getItems().get(0).getDrawing_number());
                //txt_itemname.setText("");
                //txt_itemname.requestFocus();
                publicInvoiceItems.add(item);
                System.out.println("Max HashMap " + maxItemsMap);
                System.out.println("Current HashMap " + currentsItemsMap);
                if (currentsItemsMap.containsKey(work_order)) {
                    currentsItemsMap.put(work_order, currentsItemsMap.get(work_order) + quant);
                } else {
                    currentsItemsMap.put(work_order, quant);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Item already added");
        }

    }//GEN-LAST:event_btnAddActionPerformed

    private void setGrandTotal() {
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
            finalvalue = amount + othercharges + cgstval + sgstval + igstval;

        } catch (Exception ex) {
            System.out.println("exception occured within setGrandTotal()" + ex.getMessage());
            ex.printStackTrace();
        }

        Double dVal = (Double) finalvalue;

        long longVal = Math.round(dVal);
        System.out.println("longVal: " + longVal);
        double roundOff = Double.parseDouble(longVal + "") - dVal;
        System.out.println("roundOff: " + roundOff);
        DecimalFormat df = new DecimalFormat("0.00");
        String formate = df.format(roundOff);
        try {
//            roundOff = (Double) df.parse(formate);
            System.out.println("poiu: " + formate);
            System.out.println("poiu: " + df.parse(formate));

            Object o = (Double) df.parse(formate);
            roundOff = (double) o;

            formate = df.format(dVal);
            dVal = (Double) df.parse(formate);
        } catch (Exception ex) {
            roundOff = 0;
            Logger.getLogger(NewInvoice1.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (finalvalue != 0) {
                //  Double dVal = (Double) finalvalue;
                //int intVal = dVal.intValue();
                String words = NumberToWord.NumberToWord(longVal + "");
                lbl_grandtotal.setText(longVal + "");
                txtRoundOff.setText(roundOff + "");
                lbl_inwords.setText(words);
            } else {
                lbl_grandtotal.setText("0");
            }
        }
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private void btnDeletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletActionPerformed
        // TODO add your handling code here:
        int rowToDel = jTable1.getSelectedRow();
        String work_order = null;
        int quantity = 0;
        try {
            work_order = jTable1.getValueAt(rowToDel, 0).toString();
            quantity = Integer.parseInt(jTable1.getValueAt(rowToDel, 3).toString());
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Please select item to delete");
        }
        Items item1 = new Items();

        item1.setWork_order(work_order);
        if (rowToDel > -1) {
            double val = Double.parseDouble(jTable1.getModel().getValueAt(rowToDel, 5).toString());
            basic_total = basic_total - val;
            lbl_total.setText(basic_total + "");
            setGrandTotal();
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(rowToDel);
            if (publicInvoiceItems.contains(item1)) {
                publicInvoiceItems.remove(item1);
                System.out.println("item removed rom itemList " + rowToDel);
            }
            currentsItemsMap.put(work_order, currentsItemsMap.get(work_order) - quantity);
        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }//GEN-LAST:event_btnDeletActionPerformed

    private void txt_sgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_sgstKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            setGrandTotal();
        } else {
            String str = txt_sgst.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_sgst.setText(result);
        }
    }//GEN-LAST:event_txt_sgstKeyReleased

    private void txt_cgstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cgstKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            setGrandTotal();
        } else {
            String str = txt_cgst.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_cgst.setText(result);
        }
    }//GEN-LAST:event_txt_cgstKeyReleased

    private void txt_igstKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_igstKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            setGrandTotal();
        } else {
            String str = txt_igst.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_igst.setText(result);
        }
    }//GEN-LAST:event_txt_igstKeyReleased

    private void txt_otherchargesKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_otherchargesKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            setGrandTotal();
        } else {
            String str = txt_othercharges.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_othercharges.setText(result);
        }
    }//GEN-LAST:event_txt_otherchargesKeyReleased

    private void lbl_totalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_totalMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_totalMouseReleased

    private double getPercentAMount(double percent, double base) {
        double result = 0;
        result = (percent * base) / 100;
        return result;

    }

    private void lbl_grandtotalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_grandtotalMouseClicked
        double other = Double.parseDouble(txt_othercharges.getText());
        double cgst = getPercentAMount(Double.parseDouble(txt_cgst.getText()), basic_total);
        double sgst = getPercentAMount(Double.parseDouble(txt_sgst.getText()), basic_total);
        double igst = getPercentAMount(Double.parseDouble(txt_igst.getText()), basic_total);
        System.out.println("sgst: " + sgst);
        System.out.println("cgst: " + cgst);
        System.out.println("igst: " + igst);
        //double grand_total = basic_total + other + cgst + sgst + igst;
        //int roundOff = (int) Math.round(grand_total);
        //jLabel16.setText("" + roundOff);

        //String words = NumberToWordsConverter.convert(roundOff);
        //jLabel18.setText(words);
    }//GEN-LAST:event_lbl_grandtotalMouseClicked

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String invoice_id = this.invoice_id;
        String dte = txt_billdate.getText();
        String note = txtNote.getText().trim();
        double other = Double.parseDouble(txt_othercharges.getText());
        double roundOff = Double.parseDouble(txtRoundOff.getText());
        double cgst = Double.parseDouble(txt_cgst.getText());
        double sgst = Double.parseDouble(txt_sgst.getText());
        double igst = Double.parseDouble(txt_igst.getText());
        double grand = Double.parseDouble(lbl_grandtotal.getText());;
        double basic = Double.valueOf(lbl_total.getText().toString());
        String vendorCode = txt_vendorcode.getText().trim();
        String pono = txt_pono.getText().trim();
        String dte2 = txt_date.getText().trim();
        //String vehicleNo = txt_vehicleno.getText().trim();
        ArrayList<Items> invoiceItems = getJTableData();
        Invoice_details invoice_details = new Invoice_details();
        invoice_details.setBasicTotal(basic_total);
        invoice_details.setNote(note);
        invoice_details.setCgst(cgst);
        invoice_details.setDate(dte);
        invoice_details.setDte2(dte2);
        invoice_details.setRoundOFF(roundOff);
        invoice_details.setGrandTotal(grand);
        invoice_details.setIgst(igst);
        invoice_details.setInvoice_id(invoice_id);
        invoice_details.setItemList(invoiceItems);
        invoice_details.setOthercharges(other);
        invoice_details.setPONo(pono);
        invoice_details.setSgst(sgst);
        //invoice_details.setVehicleNo(vehicleNo);
        invoice_details.setVendorCode(vendorCode);
        invoice_details.setCname(custname);
        invoice_details.setGstin(UtilVars.getCustomer().getGstin());
        invoice_details.setCustomerContact(txt_customerContact.getText());
        String dDate = currentPo.getDelivery_date();
        String bDate = txt_billdate.getText().toString();
        Date dd = Utility.convertStringToDate(dDate);
        Date bd = Utility.convertStringToDate(bDate);
        if (bd.after(dd)) {
            int reply = JOptionPane.showConfirmDialog(null, "Bill Date is After Delivery Date. Do you want to countinue?", "Mileset Software Solution Pvt Ltd", JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
//                this.dispose();
//                System.out.println("before saving invoice " + invoice_details);
//                FirebaseMethods.addNewInvoice(invoice_details, this);
//                TaxInvoice invoice = new TaxInvoice(invoice_details);
//                ConstructorBill constBill = new ConstructorBill(invoice_details);
                updateOrSave(invoice_details);
            } else {
                return;
                //JOptionPane.showMessageDialog(null, "GOODBYE");
                //System.exit(0);
            }
        } else {

            updateOrSave(invoice_details);
        }
    }//GEN-LAST:event_btn_saveActionPerformed
    public void updateOrSave(Invoice_details invoice_details) {
        if (btn_save.getText().equalsIgnoreCase("update")) {
//                JOptionPane.showMessageDialog(this, "update");
            this.dispose();
            invoice_details.setFid(invoiceToChange.getFid());
            invoice_details.setInvoice_id(invoiceToChange.getInvoice_id());
            System.out.println("Before updating invoice " + invoice_details);
            FirebaseMethods.updateInvoice(invoice_details, this);
            TaxInvoice invoice = new TaxInvoice(invoice_details);
            ConstructorBill constBill = new ConstructorBill(invoice_details);
        } else {
            //JOptionPane.showMessageDialog(this, "save");
            this.dispose();
            System.out.println("Before saving invoice " + invoice_details);
            FirebaseMethods.addNewInvoice(invoice_details, this);
            TaxInvoice invoice = new TaxInvoice(invoice_details);
            ConstructorBill constBill = new ConstructorBill(invoice_details);
        }
    }

    private ArrayList<Items> getJTableData() {
        System.out.println("getJTableData Called");
        ArrayList<Items> items = new ArrayList();
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (model != null) {
            for (int count = 0; count < model.getRowCount(); count++) {
                //numdata.add();
                String item_name = model.getValueAt(count, 1).toString();
                String hsn = model.getValueAt(count, 2).toString();
                int quantity = Integer.valueOf(model.getValueAt(count, 3).toString());
                Double unit_rate = Double.valueOf(model.getValueAt(count, 4).toString());
                Double total = Double.valueOf(model.getValueAt(count, 5).toString());
                String drawingNumber = model.getValueAt(count, 7).toString();
                String itemCode = model.getValueAt(count, 6).toString();
                String work_order = model.getValueAt(count, 0).toString();
                String unitCode = model.getValueAt(count, 8).toString();
                Items item = new Items();
                item.setAmount(total);
                item.setHsn(hsn);
                item.setItemCode(itemCode);
                item.setItem_name(item_name);
                item.setQuantity(quantity);
                item.setDrawingNumber(drawingNumber);
                item.setUnit_rate(unit_rate);
                item.setWork_order(work_order);
                item.setUnitCode(unitCode);
                items.add(item);
                System.out.println("item added " + item);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please add items first");
        }
        System.out.println(items);
        return items;
    }
    private void txt_unitrateKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_unitrateKeyReleased
        // TODO add your handling code here:
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {
            calculateResult();
        } else {
            String str = txt_unitrate.getText().toString();
            String result = str.substring(0, str.length() - 1);
            txt_unitrate.setText(result);
        }
    }//GEN-LAST:event_txt_unitrateKeyReleased

    private void txt_cgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cgstFocusLost
        /*double cgst = getPercentAMount(Double.parseDouble(txt_cgst.getText()), basic_total);
         lblCgst.setText(cgst + "");*/
    }//GEN-LAST:event_txt_cgstFocusLost

    private void txt_sgstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_sgstFocusLost
        /*double sgst = getPercentAMount(Double.parseDouble(txt_sgst.getText()), basic_total);
         lblSgst.setText(sgst + "");*/
    }//GEN-LAST:event_txt_sgstFocusLost

    private void txt_igstFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_igstFocusLost
        /*double igst = getPercentAMount(Double.parseDouble(txt_igst.getText()), basic_total);
         lblIgst.setText(igst + "");*/
    }//GEN-LAST:event_txt_igstFocusLost

    private void txt_itemCodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemCodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemCodeKeyReleased

    private void txt_drawingNumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drawingNumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_drawingNumberKeyReleased

    private void txt_cgstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cgstActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cgstActionPerformed

    private void txt_itemnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemnameKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemnameKeyReleased

    private void txt_workorderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_workorderActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_workorderActionPerformed

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
            java.util.logging.Logger.getLogger(NewInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewInvoice1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewInvoice1 dialog = new NewInvoice1();
                dialog.setVisible(true);
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
    private javax.swing.JLabel jLabel26;
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
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_deliverydate;
    private javax.swing.JLabel lbl_grandtotal;
    private javax.swing.JLabel lbl_invoiceid;
    private javax.swing.JLabel lbl_inwords;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JLabel lbl_unit;
    private javax.swing.JTextArea txtNote;
    private javax.swing.JTextField txtRoundOff;
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
    private javax.swing.JTextField txt_vendorcode;
    private javax.swing.JComboBox<String> txt_workorder;
    // End of variables declaration//GEN-END:variables
}