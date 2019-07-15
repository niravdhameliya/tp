package com.mileset.neeleshengineers.View.servicePO;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.util.CurrentDate;
import com.mileset.neeleshengineers.util.UtilVars;
import com.mileset.neeleshengineers.util.checkDateFormat;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahul
 */
public class AddNewPurchaseOrder extends javax.swing.JDialog {

    /**
     * Creates new form PurchaseOrder
     */
    private String cust_id;
    private DefaultTableModel model;
    ArrayList<PoItems> poItemsList;

    public AddNewPurchaseOrder(String cust_id) {
        super();
        initComponents();
        this.setTitle("Add New Service Order - " + UtilVars.getCustomer().getName());
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        this.cust_id = cust_id;
        poItemsList = new ArrayList<>();
        txt_billdate.setText(CurrentDate.getDate());
        String cgst = UtilVars.getCustomer().getCgst();
        String sgst = UtilVars.getCustomer().getSgst();
        String igst = UtilVars.getCustomer().getIgst();
        if (cgst.equals("0")) {
            txt_cgst.setText("0");
            txt_cgst.setEnabled(false);
        } else {
            txt_cgst.setText(cgst);
            txt_cgst.setEnabled(false);
        }
        if (sgst.equals("0")) {
            txt_sgst.setText("0");
            txt_sgst.setEnabled(false);

        } else {
            txt_sgst.setText(sgst);
            txt_sgst.setEnabled(false);
        }
        if (igst.equals("0")) {
            txt_igst.setText("0");
            txt_igst.setEnabled(false);
        } else {
            txt_igst.setText(igst);
            txt_igst.setEnabled(false);
        }
        //setItemVisibility(false);
        //btn_add.setEnabled(false);
        addListnerToJTable();
        this.setVisible(true);
    }

    private void setSelectedPoItemsObject() {
        try {
            int row = jTable1.getSelectedRow();
            txt_itemcode.setText(jTable1.getValueAt(row, 5) + "");
            txt_drawingnumber.setText(jTable1.getValueAt(row, 6) + "");
            txt_itemname.setText(jTable1.getValueAt(row, 0) + "");
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
                    // setSelectedPoItemsObject();
                    //btn_add.setEnabled(true);
                    //btn_add.setText("Click here to Update Item");
                    //updateFlat=true;

                    String item_name = jTable1.getValueAt(row, 0).toString();
                    String[] quantString = jTable1.getValueAt(row, 1).toString().split(" ");
                    String unit_rate = jTable1.getValueAt(row, 2).toString();
                    String amount = jTable1.getValueAt(row, 3).toString();
                    String item_code = jTable1.getValueAt(row, 4).toString();
                    String drawing_number = jTable1.getValueAt(row, 5).toString();
                    String work_order = jTable1.getValueAt(row, 6).toString();
                    String unitCode = jTable1.getValueAt(row, 7).toString();
                    txt_itemname.setText(item_name);
                    txt_quantity.setText(quantString[0]);
                    txt_unitrate.setText(unit_rate);
                    txt_amount.setText(amount);
                    txt_itemcode.setText(item_code);
                    txt_workorder.setText(work_order);
                    txt_drawingnumber.setText(drawing_number);
                    txt_itemname.requestFocus();
                    combo_unit.setSelectedItem(quantString[1]);

                    deletSelectedRow();
                }
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

        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_quantity = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_unitrate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txt_amount = new javax.swing.JTextField();
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
        jLabel19 = new javax.swing.JLabel();
        txt_sac = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Service Purchase Order");
        setModal(true);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Item Name");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Quantity");

        txt_quantity.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_quantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_quantityKeyReleased(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Unit Rate");

        txt_unitrate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Amount");

        txt_amount.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_amount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_amountFocusGained(evt);
            }
        });

        txt_itemname.setColumns(20);
        txt_itemname.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_itemname.setRows(5);
        jScrollPane2.setViewportView(txt_itemname);

        txt_itemcode.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_itemcode.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_itemcodeKeyReleased(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Item Code");

        txt_drawingnumber.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        txt_drawingnumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_drawingnumberKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("Drawing number");

        combo_unit.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        combo_unit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nos", "Kg", "Length", "SET" }));

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Work Order");

        txt_workorder.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 5, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(combo_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(6, 6, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_unitrate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_itemcode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_drawingnumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_amount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_unitrate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txt_amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(combo_unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txt_itemcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel11)
                                        .addComponent(txt_drawingnumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_workorder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15))))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Customer PO Id");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("PO Date");

        txt_billdate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("For Eg: 25/3/1992");

        txt_poid.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        btn_save.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
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
                "Item Name", "Quantity", "Unit Rate", "Total", "Item Code", "Drawing number", "Work Order", "Unit"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        btn_add.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("CGST");

        txt_cgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("SGST");

        txt_sgst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("IGST");

        txt_igst.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("Delivery Date");

        txt_deliverydate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("PSD");

        txtPSD.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_save))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(6, 6, 6)))
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_deliverydate, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(txtPSD)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(62, 62, 62)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(txt_cgst, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_poid, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19)
                        .addGap(6, 6, 6)
                        .addComponent(txt_sac, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_billdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(txt_poid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(txt_sac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_save))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(btn_add)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txt_cgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_sgst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_igst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_deliverydate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtPSD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
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

    private void addRow() {
        String POId = txt_poid.getText().trim();
        String item_name = txt_itemname.getText().trim();
        String quantity = txt_quantity.getText().trim();
        String unit_rate = txt_unitrate.getText().trim();
        String amount = txt_amount.getText().trim();
        String item_code = txt_itemcode.getText().trim();
        String drawing_number = txt_drawingnumber.getText().trim();
        String work_order = txt_workorder.getText().trim();
        String unitCode = combo_unit.getSelectedItem().toString();

        if (POId.length() == 0) {
            JOptionPane.showMessageDialog(this, "Enter PO Id");
            return;
        }
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
        row.add(quantity + " " + combo_unit.getSelectedItem());
        row.add(unit_rate);
        row.add(amount);
        row.add(item_code);
        row.add(drawing_number);
        row.add(work_order);
        row.add(unitCode);
        model.addRow(row);

        PoItems poitems = new PoItems(POId, item_name, quantity, unit_rate, amount, item_code, drawing_number, work_order, unitCode);
        poItemsList.add(poitems);
        clear1();
    }

    private void clear1() {
        txt_quantity.setText("");
        txt_amount.setText("");
        txt_unitrate.setText("");
        txt_itemcode.setText("");
        txt_itemname.setText("");
        txt_drawingnumber.setText("");
        txt_workorder.setText("");
        combo_unit.setSelectedIndex(0);
    }

    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        addRow();
        //setItemVisibility(false);
        //btn_add.setEnabled(false);
        // txt_itemname.setText("");
    }//GEN-LAST:event_btn_addActionPerformed

    private void deletSelectedRow() {
        int rowToDel = jTable1.getSelectedRow();
        if (rowToDel > -1) {

            DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
            model1.removeRow(rowToDel);

            poItemsList.remove(rowToDel);

        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        deletSelectedRow();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        String POId = txt_poid.getText().trim();
        String PODate = txt_billdate.getText().trim();
        String sac = txt_sac.getText().trim();
        String psd = txtPSD.getText().trim();
        String delDate = txt_deliverydate.getText().trim();
        String cgst = txt_cgst.getText().trim();
        String sgst = txt_sgst.getText().trim();
        String igst = txt_igst.getText().trim();

        if (delDate.length() < 2) {
            JOptionPane.showMessageDialog(this, "Delivery Date Needed");
            return;
        }
        if (PODate.length() < 2) {
            JOptionPane.showMessageDialog(this, "PO Date Needed");
            return;
        }
        if (!checkDateFormat.isValidFormat(PODate)) {
            JOptionPane.showMessageDialog(this, "PO Date should be in format of 22/01/2019");
            return;
        }
        if (!checkDateFormat.isValidFormat(delDate)) {
            JOptionPane.showMessageDialog(this, "Delivery Date should be in format of 22/01/2019");
            return;
        }

        ServicePO servicePO = new ServicePO(POId, PODate, cust_id, poItemsList, cgst, sgst, igst, delDate, psd, sac);
        saveServicePO(servicePO);
    }//GEN-LAST:event_btn_saveActionPerformed
    private void saveServicePO(ServicePO servicePO) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");

        ref.child("servicePO").child(servicePO.getCust_id()).child(servicePO.getPoid()).setValue(servicePO, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                    JOptionPane.showMessageDialog(null, "Service PO Data Saved");
                    closeWindow();
                }
            }
        });

    }

    public void closeWindow() {
        this.dispose();
    }

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
            java.util.logging.Logger.getLogger(AddNewPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddNewPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddNewPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddNewPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AddNewPurchaseOrder dialog = new AddNewPurchaseOrder("-");
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
    private javax.swing.JTextField txt_igst;
    private javax.swing.JTextField txt_itemcode;
    private javax.swing.JTextArea txt_itemname;
    private javax.swing.JTextField txt_poid;
    private javax.swing.JTextField txt_quantity;
    private javax.swing.JTextField txt_sac;
    private javax.swing.JTextField txt_sgst;
    private javax.swing.JTextField txt_unitrate;
    private javax.swing.JTextField txt_workorder;
    // End of variables declaration//GEN-END:variables

    private void clear() {
        txt_amount.setText("");
        txt_sac.setText("");
        txt_quantity.setText("");
        txt_unitrate.setText("");
        txt_workorder.setText("");
    }
}
