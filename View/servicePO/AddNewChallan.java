package com.mileset.neeleshengineers.View.servicePO;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mileset.neeleshengineers.controller.servicePO.alChallanCallBack;
import com.mileset.neeleshengineers.controller.servicePO.getCHallanFromPoId;
import com.mileset.neeleshengineers.modal.Challan;
import com.mileset.neeleshengineers.modal.ChallanItems;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.util.CurrentDate;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rahul
 */
public class AddNewChallan extends javax.swing.JDialog {

    /**
     * Creates new form AddNewChallan
     */
    ServicePO po;
    ArrayList<ChallanItems> challanItemsList;
    private ArrayList<PoItems> itemList;
    private ArrayList<Challan> challanList;
    HashMap<String, Integer> itemQuantHashMap, origItemQuantHashMap;

    public AddNewChallan(ServicePO po) {
        this.po = po;
        initComponents();
        jTable1.setFont(new Font("Serif", Font.PLAIN, 20));
        itemQuantHashMap = new HashMap<>();
        origItemQuantHashMap = new HashMap<>();
        populateItemsFromServicePO();
        challanItemsList = new ArrayList<>();
        txtChallanDate.setText(CurrentDate.getDate());
        jLabel4.setText("Purchase Number: " + po.getPoid());
        getAllChallanItems();
        this.setVisible(true);
    }

    public void populateItemsFromServicePO() {

        itemList = po.getItems();

        for (int i = 0; i < itemList.size(); i++) {
            jComboBox1.addItem(itemList.get(i).getItem_name());
            itemQuantHashMap.put(itemList.get(i).getItem_name(), 0);
            origItemQuantHashMap.put(itemList.get(i).getItem_name(), Integer.parseInt(itemList.get(i).getQuanatity()));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtChallanNumber = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtItemQuantity = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtChallanDate = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Challan");
        setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        setModal(true);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel1.setText("Challan Number");

        txtChallanNumber.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("Quantity");

        txtItemQuantity.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Date");

        txtChallanDate.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel4.setText("Purchase Number");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Quantity"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("Item Description");

        jButton2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jButton4.setText("Delete");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jComboBox1.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtChallanNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4))
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtChallanNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtChallanDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtItemQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2)
                            .addComponent(jButton4))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String challanNo = txtChallanNumber.getText().trim();
        String date = txtChallanDate.getText().trim();
        if (challanItemsList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Add Items in the list");
            return;
        }
        if (ifEmpty(challanNo)) {
            JOptionPane.showMessageDialog(this, "Challan Number must not be empty");
            return;
        }
        if (ifEmpty(date)) {
            JOptionPane.showMessageDialog(this, "Challan Date must not be empty");
            return;
        }
        Challan c = new Challan(po.getPoid(), challanNo, date, challanItemsList);
        saveChallan(c, challanNo);
    }//GEN-LAST:event_jButton1ActionPerformed
    public void saveChallan(Challan c, String challan_No) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("data");
//        String id = ref.push().getKey();
        ref.child("SPOChallan").child(po.getCust_id()).child(po.getPoid()).child(challan_No).setValue(c, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    System.out.println("Data could not be saved " + databaseError.getMessage());
                } else {

                    JOptionPane.showMessageDialog(null, "Challan Data Saved");
                    closeWindow();
                }
            }
        });

    }

    public void getAllChallanItems() {
        getCHallanFromPoId challan = new getCHallanFromPoId(po.getPoid(), UtilVars.getCustomer().getId());
        challan.fetch(new alChallanCallBack() {

            @Override
            public void onCallback(ArrayList<Challan> value) {
                challanList = value;
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                for (int i = 0; i < value.size(); i++) {
                    Challan st = value.get(i);
                    ArrayList<ChallanItems> challanItemsList1 = st.getChallanItemsList();
                    for (ChallanItems c : challanItemsList1) {
                        String currName = c.getItemName();
                        int currQuant = c.getQuantity();
                        int originalQuant = itemQuantHashMap.get(currName);
                        int newQuantity = currQuant + originalQuant;

                        itemQuantHashMap.replace(currName, newQuantity);
                    }
                }
            }

            @Override
            public void recordNotPresent() {

            }

            @Override
            public void onCallback1(Challan value) {

            }
        });
    }

    private void clear() {
        //txtItemName.setText("");
        txtItemQuantity.setText("");
    }
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String itemName = jComboBox1.getSelectedItem().toString().trim();
        int challanQuant = itemQuantHashMap.get(itemName);
        int origQuant = origItemQuantHashMap.get(itemName);
        System.out.println("challanQuant: " + challanQuant);
        int quantity = Integer.parseInt(txtItemQuantity.getText().trim());
        if (quantity < 1) {
            JOptionPane.showMessageDialog(this, "Quantity must be greater than 0");
            return;
        }
        if (ifEmpty(itemName)) {
            JOptionPane.showMessageDialog(this, "Item Name must not be empty");
            return;
        }
        int remaining = origQuant - challanQuant;
        if (remaining < quantity) {

            JOptionPane.showMessageDialog(this, "Item canot be added.");
            if (remaining > 0) {
                JOptionPane.showMessageDialog(this, "You can add only " + remaining);
                txtItemQuantity.setText("" + remaining);
            }
            return;
        }

        ChallanItems CI = new ChallanItems(itemName, quantity);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        Vector row = new Vector();
        row.add(itemName);
        row.add(quantity);
        model.addRow(row);

        challanItemsList.add(CI);

        clear();
    }//GEN-LAST:event_jButton2ActionPerformed
    public void closeWindow() {
        this.dispose();
    }
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int rowToDel = jTable1.getSelectedRow();
        if (rowToDel > -1) {
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            challanItemsList.remove(rowToDel);
            model.removeRow(rowToDel);
        } else {
            JOptionPane.showMessageDialog(this, "Select Row");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        int index = jComboBox1.getSelectedIndex();
        txtItemQuantity.setText(itemList.get(index).getQuanatity());
    }//GEN-LAST:event_jComboBox1ItemStateChanged
    public boolean ifEmpty(String str) {
        boolean flag = false;
        if (str.trim().length() == 0) {
            flag = true;
        }
        return flag;

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtChallanDate;
    private javax.swing.JTextField txtChallanNumber;
    private javax.swing.JTextField txtItemQuantity;
    // End of variables declaration//GEN-END:variables
}
