/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.PurchaseOrder;

import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Component;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author Rahul
 */
public class PurchaseOrder extends javax.swing.JDialog {

    /**
     * Creates new form PurchaseOrder
     */
    private String cust_id;
    private DefaultTableModel model;
    private Po poToUpdate;
    private boolean updateFlat = false;

    public PurchaseOrder(String cust_id) {
        super();
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        this.cust_id = cust_id;
        clear();
        addListnerToJTable();
        String cgst = UtilVars.getCustomer().getCgst();
        String sgst = UtilVars.getCustomer().getSgst();
        String igst = UtilVars.getCustomer().getIgst();
        if (cgst.equals("0")) {
            txt_cgst.setText("0");
            txt_cgst.setEnabled(false);
        } else {
            txt_cgst.setText(cgst);
        }
        if (sgst.equals("0")) {
            txt_sgst.setText("0");
            txt_sgst.setEnabled(false);
        } else {
            txt_sgst.setText(sgst);
        }
        if (igst.equals("0")) {
            txt_igst.setText("0");
            txt_igst.setEnabled(false);
        } else {
            txt_igst.setText(igst);
        }
        //setItemVisibility(false);
        //btn_add.setEnabled(false);
    }

    public PurchaseOrder(Po po) {
        super();
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        poToUpdate = po;
        clear();
        txt_poid.setEnabled(false);
        btn_save.setText("Update Po Order");
        txt_billdate.setText(po.getBill_date());
        txt_poid.setText(po.getPoid());
        if (po.getCgst().equals("0")) {
            txt_cgst.setText("0");
            txt_cgst.setEnabled(false);
        } else {
            txt_cgst.setText(po.getCgst());
        }
        if (po.getSgst().equals("0")) {
            txt_sgst.setText("0");
            txt_sgst.setEnabled(false);
        } else {
            txt_sgst.setText(po.getCgst());
        }
        if (po.getIgst().equals("0")) {
            txt_igst.setText("0");
            txt_igst.setEnabled(false);
        } else {
            txt_igst.setText(po.getCgst());
        }
        txt_deliverydate.setText(po.getDelivery_date());
        txtPSD.setText(po.getPsd());
        fillTableWithPoItems(po.getItems());
        addListnerToJTable();
        //setItemVisibility(false);
        //btn_add.setEnabled(false);
        jTable1.setDefaultEditor(Object.class, null);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {

                ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                sap.setVisible(true);

            }

        });

    }

    private void setSelectedPoItemsObject() {
        try {
            int row = jTable1.getSelectedRow();
            txt_itemcode.setText(jTable1.getValueAt(row, 5) + "");
            txt_drawingnumber.setText(jTable1.getValueAt(row, 6) + "");
            txt_itemname.setText(jTable1.getValueAt(row, 0) + "");
            txt_hsn.setText(jTable1.getValueAt(row, 1) + "");
            String[] quantString = jTable1.getValueAt(row, 2).toString().split(" ");
            txt_quantity.setText(quantString[0]);
            txt_unitrate.setText(jTable1.getValueAt(row, 3) + "");
            txt_amount.setText(jTable1.getValueAt(row, 4) + "");
            txt_workorder.setText(jTable1.getValueAt(row, 7) + "");
            txt_itemname.requestFocus();
            combo_unit.setSelectedItem(quantString[1]);

        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please select id to update");
        }
        //setItemVisibility(true);
    }

    private void addListnerToJTable() {
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    setSelectedPoItemsObject();
                    //btn_add.setEnabled(true);
                    //btn_add.setText("Click here to Update Item");
                    //updateFlat=true;
                    deletSelectedRow();
                }
            }
        });
    }

    private void fillTableWithPoItems(ArrayList<PoItems> items) {
        model = (DefaultTableModel) jTable1.getModel();
        for (PoItems poitem : items) {
            Vector row = new Vector();
            row.add(poitem.getItem_name());
            row.add(poitem.getHsn());
            row.add(poitem.getQuanatity());
            row.add(poitem.getUnitrate());
            row.add(poitem.getAmount());
            row.add(poitem.getItem_code());
            row.add(poitem.getDrawing_number());
            row.add(poitem.getWork_order());
            row.add(poitem.getUnitCode());
            model.addRow(row);
            System.out.println("filled row " + poitem);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_unitrate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txt_hsn = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_itemname = new javax.swing.JTextArea();
        txt_itemcode = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_drawingnumber = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        combo_unit = new javax.swing.JComboBox();
        jLabel15 = new javax.swing.JLabel();
        txt_workorder = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txt_billdate = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txt_poid = new javax.swing.JTextField();
        btn_save = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        btn_add = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_cgst = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_sgst = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_igst = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_deliverydate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtPSD = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Purchase Order");
        setModal(true);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel5.setText("Item Name");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel6.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_quantityKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel7.setText("Unit Rate");

        txt_unitrate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel8.setText("Amount");

        txt_amount.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_amountFocusGained(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel19.setText("HSN");

        txt_hsn.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        txt_itemname.setColumns(20);
        txt_itemname.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        txt_itemname.setRows(5);
        jScrollPane2.setViewportView(txt_itemname);

        txt_itemcode.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_itemcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemcodeKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel9.setText("Item Code");

        txt_drawingnumber.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        txt_drawingnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_drawingnumberKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel11.setText("Drawing number");

        combo_unit.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        combo_unit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nos", "Kg", "Length", "SET" }));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel15.setText("Work Order");

        txt_workorder.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(combo_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txt_hsn, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_drawingnumber, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txt_itemcode, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                .addComponent(txt_unitrate)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_itemcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(txt_hsn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel19)))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel7)
                                .addComponent(combo_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_drawingnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11)
                        .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8))
                .addContainerGap())
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel10.setText("Customer PO Id");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel12.setText("PO Date");

        txt_billdate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel13.setText("For Eg: 25/3/1992");

        txt_poid.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "hsn", "Quantity", "Unit Rate", "Total", "Item Code", "Drawing number", "Work Order", "Unit"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_add.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel1.setText("CGST");

        txt_cgst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel2.setText("SGST");

        txt_sgst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel3.setText("IGST");

        txt_igst.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel4.setText("Delivery Date");

        txt_deliverydate.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N
        jLabel14.setText("PSD");

        txtPSD.setFont(new java.awt.Font("Times New Roman", 1, 20)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_poid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_save)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 753, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel14)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtPSD, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txt_deliverydate)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3)
                                    .addComponent(btn_add, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13)
                        .addComponent(jLabel12)
                        .addComponent(txt_poid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_sgst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_deliverydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_save)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(txtPSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_quantityKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_quantityKeyReleased
        System.out.println("" + evt.getKeyCode());
        if (isValidNumber(evt.getKeyCode())) {

        } else {
            txt_quantity.setText("");
        }
    }//GEN-LAST:event_txt_quantityKeyReleased

    private void txt_amountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_amountFocusGained
        // TODO add your handing code here:

        double unit_price = Double.parseDouble("" + txt_unitrate.getText());
        double quan = Double.parseDouble("" + txt_quantity.getText());

        double price = unit_price * quan;

        txt_amount.setText(price + "");
    }//GEN-LAST:event_txt_amountFocusGained
    private ArrayList<PoItems> getJTableData() {
        System.out.println("getJTableData Called");
        String poid = txt_poid.getText().toString();
        ArrayList<PoItems> items = new ArrayList();
        if (model != null) {
            for (int count = 0; count < model.getRowCount(); count++) {
                //numdata.add();
                String item_name = model.getValueAt(count, 0).toString();
                String hsn = model.getValueAt(count, 1).toString();
                String quantity = model.getValueAt(count, 2).toString();
                String unit_rate = model.getValueAt(count, 3).toString();
                String total = model.getValueAt(count, 4).toString();
                String item_code = model.getValueAt(count, 5).toString();
                String drawing_number = model.getValueAt(count, 6).toString();
                String work_order = model.getValueAt(count, 7).toString();
                String unitCode = model.getValueAt(count, 8).toString();
                PoItems poItem = new PoItems(poid, item_name, quantity, unit_rate, total, hsn, item_code, drawing_number, work_order, unitCode);
                items.add(poItem);

            }
        } else {
            JOptionPane.showMessageDialog(this, "Please add items first");
        }
        System.out.println(items);
        return items;
    }

    private void addRow() {
        String item_name = txt_itemname.getText().trim();
        String quantity = txt_quantity.getText().trim();
        String unit_rate = txt_unitrate.getText().trim();
        String amount = txt_amount.getText().trim();
        String hsn = txt_hsn.getText();
        String item_code = txt_itemcode.getText().trim();
        String drawing_number = txt_drawingnumber.getText().trim();
        String work_order = txt_workorder.getText().trim();
        String unitCode = combo_unit.getSelectedItem().toString();

        if (item_name.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter item Name");
            return;
        }
        if (quantity.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter quantity");
            return;
        }
        if (unit_rate.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Unit Rate");
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
        if (item_code.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Item Code");
            return;
        }
        if (drawing_number.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Drawing Number");
            return;
        }
        if (work_order.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter Work Order Number");
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

        model = (DefaultTableModel) jTable1.getModel();

        Vector row = new Vector();

        row.add(item_name);
        row.add(hsn);
        row.add(quantity + " " + combo_unit.getSelectedItem());
        row.add(unit_rate);
        row.add(amount);
        row.add(item_code);
        row.add(drawing_number);
        row.add(work_order);
        row.add(unitCode);
        model.addRow(row);
        txt_quantity.setText("");
        txt_amount.setText("");
        txt_hsn.setText("");
        txt_unitrate.setText("");
        txt_itemcode.setText("");
        txt_itemname.setText("");
        txt_drawingnumber.setText("");
        txt_workorder.setText("");
        combo_unit.setSelectedIndex(0);
    }
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        if (updateFlat) {
            deletSelectedRow();
            addRow();
            //btn_add.setText("Add");
        } else {
            addRow();
        }
        //setItemVisibility(false);
        //btn_add.setEnabled(false);
        // txt_itemname.setText("");
    }//GEN-LAST:event_btn_addActionPerformed

    private void deletSelectedRow() {
        int rowToDel = jTable1.getSelectedRow();
        if (rowToDel > -1) {
            double val = Double.parseDouble(jTable1.getModel().getValueAt(rowToDel, 4).toString());
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.removeRow(rowToDel);

        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        deletSelectedRow();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        ArrayList<PoItems> poitems = getJTableData();
        String poid = txt_poid.getText();
        String date = txt_billdate.getText();
        String igst = txt_igst.getText();
        String cgst = txt_cgst.getText();
        String sgst = txt_sgst.getText();
        String psd = txtPSD.getText();
        String deliveryDate = txt_deliverydate.getText();
        if (btn_save.getText().equalsIgnoreCase("save")) {
            final Po po = new Po(poid, date, cust_id, poitems, cgst, sgst, igst, deliveryDate, psd);
            po.setCustName(UtilVars.getCustomer().getName());
            final ProgressDialogFrame pd = new ProgressDialogFrame("Saving...");
            final JDialog jif = this;
            pd.setVisible(true);
            Thread worker = new Thread() {
                public void run() {
                    try {
                        FirebaseMethods.addPurchaseOrderToDb(jif, po);

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

        } else {
            poToUpdate.setItems(poitems);
            poToUpdate.setBill_date(date);
            poToUpdate.setCustName(UtilVars.getCustomer().getName());
            poToUpdate.setCgst(cgst);
            poToUpdate.setCust_id(UtilVars.getCustomer().getId());
            poToUpdate.setDelivery_date(deliveryDate);
            poToUpdate.setIgst(igst);
            poToUpdate.setPoid(poid);
            poToUpdate.setSgst(sgst);
            FirebaseMethods.updatePurchaseOrder(this, poToUpdate);
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void txt_itemcodeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_itemcodeKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_itemcodeKeyReleased

    private void txt_drawingnumberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_drawingnumberKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_drawingnumberKeyReleased

//    private void setItemVisibility(boolean status){
//        txt_amount.setEditable(status);
//        txt_hsn.setEditable(status);
//        txt_itemname.setEditable(status);
//        txt_quantity.setEditable(status);
//        txt_unitrate.setEditable(status);
//    }
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
            java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PurchaseOrder dialog = new PurchaseOrder("-");
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

    public boolean isValidNumber(int key) {
        boolean flag = false;
        if ((key > 47 && key < 58) || (key > 95 && key < 106)) {
            flag = true;
        } else if (key == 8 || key == 46) {
            flag = true;
        }
        return flag;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_save;
    private javax.swing.JComboBox combo_unit;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtPSD;
    private javax.swing.JTextField txt_amount;
    private javax.swing.JTextField txt_billdate;
    private javax.swing.JTextField txt_cgst;
    private javax.swing.JTextField txt_deliverydate;
    private javax.swing.JTextField txt_drawingnumber;
    private javax.swing.JTextField txt_hsn;
    private javax.swing.JTextField txt_igst;
    private javax.swing.JTextField txt_itemcode;
    private javax.swing.JTextArea txt_itemname;
    private javax.swing.JTextField txt_poid;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_unitrate;
    private javax.swing.JTextField txt_workorder;
    // End of variables declaration//GEN-END:variables

    private void clear() {
        txt_amount.setText("");
        txt_hsn.setText("");
        txt_quantity.setText("");
        txt_unitrate.setText("");
        txt_workorder.setText("");
    }
}
