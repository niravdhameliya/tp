/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.PurchaseOrder;

import com.mileset.neeleshengineers.View.Invoice.NewInvoice1;
import com.mileset.neeleshengineers.View.PurchaseOrder.PurchaseOrder;
import com.mileset.neeleshengineers.View.ShowListOfPoItems;
import com.mileset.neeleshengineers.View.newlaborinvoice.NewLabourInvoice;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.report.dao.POReport;
import com.mileset.neeleshengineers.util.AdminPassword;
import com.mileset.neeleshengineers.util.FirebaseMethods;
import com.mileset.neeleshengineers.util.NewInvoiceUtilVars;
import com.mileset.neeleshengineers.util.ProgressDialogFrame;
import com.mileset.neeleshengineers.util.UtilVars;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import sun.net.PortConfig;

/**
 *
 * @author Rahul
 */
public class ShowAllPurchaseOrder extends javax.swing.JDialog {

    /**
     * Creates new form ShowAllPurchaseOrder
     */
    private TableRowSorter<TableModel> rowSorter;
    private ArrayList<Po> polist;
    private Customer customer;
    private String custname;

    public ShowAllPurchaseOrder(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTable1.setFont(new Font("Serif", Font.BOLD, 20));
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);

        btnGetAllLaborInvoie.setVisible(false);
        // btn_generateLaborInvoice.setVisible(false);
        btn_generateinvoice.setVisible(false);
        btn_showinvoice.setVisible(false);
        btn_updatepo.setVisible(false);
        btn_updatepo1.setVisible(false);
        btn_generateLaborInvoice.setVisible(false);
    }

    public ShowAllPurchaseOrder(final ArrayList<Po> purchase_order_list, Customer customer) {
        initComponents();
        jTable1.setFont(new Font("Serif", Font.BOLD, 20));
        this.customer = customer;
        this.polist = purchase_order_list;
        String mCustName = UtilVars.getCustomer().getName();
        jLabel1.setText(mCustName);
        this.custname = mCustName;
        //jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
        jTable1.setRowSelectionAllowed(true);

        jTable1.setDefaultEditor(Object.class, null);

        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rowSorter = new TableRowSorter<>(jTable1.getModel());
        jTable1.setRowSorter(rowSorter);
        setToTable(purchase_order_list, jTable1);
//        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
//        public void valueChanged(ListSelectionEvent event) {
//           ArrayList<PoItems> poitems = getSelectedPoObject(purchase_order_list);
//           ShowListOfPoItems showListOfPoItems =new ShowListOfPoItems(poitems);
//           showListOfPoItems.setVisible(true);
//        }
//    });
        jTable1.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    ArrayList<PoItems> poitems = getSelectedPoItemsObject(purchase_order_list);
                    ShowListOfPoItems showListOfPoItems = new ShowListOfPoItems(poitems);
                    showListOfPoItems.setVisible(true);
                }
            }
        });
        btnGetAllLaborInvoie.setVisible(false);
        //   btn_generateLaborInvoice.setVisible(false);
        btn_generateinvoice.setVisible(false);
        btn_showinvoice.setVisible(false);
        btn_updatepo.setVisible(false);
        btn_updatepo1.setVisible(false);
        btn_generateLaborInvoice.setVisible(false);
    }

    private ArrayList<PoItems> getSelectedPoItemsObject(ArrayList<Po> polist) {
        ArrayList<PoItems> poItems = null;
        String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        for (Po po : polist) {
            if (po.getPoid().equalsIgnoreCase(id)) {
                poItems = po.getItems();
                break;
            }
        }
        return poItems;
    }

    private void setToTable(ArrayList<Po> data1, JTable jTable1) {
        System.out.println("setToTable called");
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1.size());
        for (int i = 0; i < data1.size(); i++) {
            //  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(data1.get(i).getPoid());
            row.add(data1.get(i).getCust_id());
            row.add(data1.get(i).getBill_date());
            row.add(data1.get(i).getItems().size());
            //model.addRow(row);
            //dm.addRow(row);
            dm.addRow(row);
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
        btn_updatepo = new javax.swing.JButton();
        btn_generateinvoice = new javax.swing.JButton();
        btn_showinvoice = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btn_generateLaborInvoice = new javax.swing.JButton();
        btnGetAllLaborInvoie = new javax.swing.JButton();
        btn_updatepo1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        updatePO_menuItem = new javax.swing.JMenuItem();
        delPo_menuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        createInvoice_menuItem = new javax.swing.JMenuItem();
        showInvoice_menuItem = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Purchase Order");
        setModal(true);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Po Id", "Customer Id", "Date", "Item Counts"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btn_updatepo.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_updatepo.setText("Update Po Order");
        btn_updatepo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatepoActionPerformed(evt);
            }
        });

        btn_generateinvoice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_generateinvoice.setText("Generate Invoice");
        btn_generateinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateinvoiceActionPerformed(evt);
            }
        });

        btn_showinvoice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_showinvoice.setText("Show Invoice");
        btn_showinvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_showinvoiceActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel1.setText("Customer Name: ");

        btn_generateLaborInvoice.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_generateLaborInvoice.setText("Generate Labor Invoice");
        btn_generateLaborInvoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_generateLaborInvoiceActionPerformed(evt);
            }
        });

        btnGetAllLaborInvoie.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btnGetAllLaborInvoie.setText("Show All Labor Invoice");
        btnGetAllLaborInvoie.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGetAllLaborInvoieActionPerformed(evt);
            }
        });

        btn_updatepo1.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        btn_updatepo1.setText("Delet PO");
        btn_updatepo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatepo1ActionPerformed(evt);
            }
        });

        jMenu1.setText("Purchase Order");
        jMenu1.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        updatePO_menuItem.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        updatePO_menuItem.setText("Update PO");
        updatePO_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePO_menuItemActionPerformed(evt);
            }
        });
        jMenu1.add(updatePO_menuItem);

        delPo_menuItem.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        delPo_menuItem.setText("Delete PO");
        delPo_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delPo_menuItemActionPerformed(evt);
            }
        });
        jMenu1.add(delPo_menuItem);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Invoice");
        jMenu2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N

        createInvoice_menuItem.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        createInvoice_menuItem.setText("Create Invoice");
        createInvoice_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createInvoice_menuItemActionPerformed(evt);
            }
        });
        jMenu2.add(createInvoice_menuItem);

        showInvoice_menuItem.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        showInvoice_menuItem.setText("Show Invoice");
        showInvoice_menuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showInvoice_menuItemActionPerformed(evt);
            }
        });
        jMenu2.add(showInvoice_menuItem);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("PO report");
        jMenu3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        jCheckBoxMenuItem1.setFont(new java.awt.Font("Times New Roman", 0, 14)); // NOI18N
        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("Generate");
        jCheckBoxMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jCheckBoxMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_updatepo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_generateinvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_showinvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_generateLaborInvoice)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGetAllLaborInvoie))
                    .addComponent(btn_updatepo1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 792, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updatepo)
                    .addComponent(btn_generateinvoice)
                    .addComponent(btn_showinvoice)
                    .addComponent(btn_generateLaborInvoice)
                    .addComponent(btnGetAllLaborInvoie))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_updatepo1)
                .addGap(21, 21, 21))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_updatepoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatepoActionPerformed
        // TODO add your handling code here:
        String poid = getSelectedId();
        try {
            if (!poid.equals("")) {
//        System.out.println(getSelectedId()+" id is here");
                Po po = getSelectedPoObject(polist);
                PurchaseOrderVars.setPo(po);
                ShowAllPurchaseOrderVars.setPolist(polist);
                ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
                this.dispose();
                PurchaseOrder purchaseOrder = new PurchaseOrder(po);
                purchaseOrder.setVisible(true);
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Please select id.");
            ex.printStackTrace();
        }

    }//GEN-LAST:event_btn_updatepoActionPerformed

    private void btn_generateinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generateinvoiceActionPerformed
        // TODO add your handling code here:
        final String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            final Po po = getSelectedPoObject(polist);
            NewInvoiceUtilVars.setPo(po);
            System.out.println("Setting purchase order " + po);
            this.dispose();
            FirebaseMethods.generateInvoiceId(id, custname, po, this);

        }
    }//GEN-LAST:event_btn_generateinvoiceActionPerformed

    private void btn_showinvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_showinvoiceActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            Po po = getSelectedPoObject(polist);
            UtilVars.setPo(po);
            ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
            ShowAllPurchaseOrderVars.setPolist(polist);
            this.dispose();
            FirebaseMethods.getInvoiceByPo(po, this, customer);
        }
    }//GEN-LAST:event_btn_showinvoiceActionPerformed

    private void btn_generateLaborInvoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_generateLaborInvoiceActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            // UtilVars.setPo(getSelectedPoObject(polist));

            Po po = getSelectedPoObject(polist);
            UtilVars.setPo(po);
            ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
            ShowAllPurchaseOrderVars.setPolist(polist);
            FirebaseMethods.generateLaborInvoiceId(this, true);
            /*  NewLabourInvoice dialog = new NewLabourInvoice();
             dialog.addWindowListener(new java.awt.event.WindowAdapter() {
             @Override
             public void windowClosing(java.awt.event.WindowEvent e) {
             System.exit(0);
             }
             });
             dialog.setVisible(true);*/
        }
    }//GEN-LAST:event_btn_generateLaborInvoiceActionPerformed

    private void btnGetAllLaborInvoieActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGetAllLaborInvoieActionPerformed
        // TODO add your handling code here:
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            Po po = getSelectedPoObject(polist);
            ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
            ShowAllPurchaseOrderVars.setPolist(polist);
            this.dispose();
            FirebaseMethods.getLaborInvoiceByPo(po, this);
        }
    }//GEN-LAST:event_btnGetAllLaborInvoieActionPerformed

    private void btn_updatepo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatepo1ActionPerformed
        // TODO add your handling code here:
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            Po po = getSelectedPoObject(polist);
            final String poId = po.getFid();
            final JDialog jd = this;
            final ProgressDialogFrame pd = new ProgressDialogFrame("Deleting...");
            pd.setVisible(true);
            Thread worker = new Thread() {
                public void run() {
                    try {
                        FirebaseMethods.deletePo(poId, jd);

                    } catch (Exception ex) {
                        System.out.println("Exception occured " + ex);
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            //resultLabel.setText("Finished");
                            pd.setVisible(false);
                            pd.dispose();
                            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                            model.removeRow(jTable1.getSelectedRow());
                            JOptionPane.showMessageDialog(jd, "Purchase Order Deleted");
                        }
                    });
                }
            };
            worker.start();

        }
    }//GEN-LAST:event_btn_updatepo1ActionPerformed

    private void updatePO_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePO_menuItemActionPerformed
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            if (AdminPassword.getStr().equals(password)) {
                String poid = getSelectedId();
                try {
                    if (!poid.equals("")) {
//        System.out.println(getSelectedId()+" id is here");
                        Po po = getSelectedPoObject(polist);
                        PurchaseOrderVars.setPo(po);
                        ShowAllPurchaseOrderVars.setPolist(polist);
                        ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
                        this.dispose();
                        PurchaseOrder purchaseOrder = new PurchaseOrder(po);
                        purchaseOrder.setVisible(true);
                    }
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(this, "Please select id.");
                    ex.printStackTrace();
                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_updatePO_menuItemActionPerformed

    private void delPo_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delPo_menuItemActionPerformed
        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null, pf, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (okCxl == JOptionPane.OK_OPTION) {
            String password = new String(pf.getPassword());
            if (AdminPassword.getStr().equals(password)) {

                String id = getSelectedId();
                if (id.equals("")) {
                    JOptionPane.showMessageDialog(this, "Please Select A Customer");
                } else {
                    Po po = getSelectedPoObject(polist);
                    final String poId = po.getFid();
                    final JDialog jd = this;
                    final ProgressDialogFrame pd = new ProgressDialogFrame("Deleting...");
                    pd.setVisible(true);
                    Thread worker = new Thread() {
                        public void run() {
                            try {
                                FirebaseMethods.deletePo(poId, jd);

                            } catch (Exception ex) {
                                System.out.println("Exception occured " + ex);
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    //resultLabel.setText("Finished");
                                    pd.setVisible(false);
                                    pd.dispose();
                                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                                    model.removeRow(jTable1.getSelectedRow());
                                    JOptionPane.showMessageDialog(jd, "Purchase Order Deleted");
                                }
                            });
                        }
                    };
                    worker.start();

                }

            } else {
                JOptionPane.showMessageDialog(this, "Invalid Password", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_delPo_menuItemActionPerformed

    private void showInvoice_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showInvoice_menuItemActionPerformed
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {
            Po po = getSelectedPoObject(polist);
            UtilVars.setPo(po);
            ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
            ShowAllPurchaseOrderVars.setPolist(polist);
            this.dispose();
            FirebaseMethods.getInvoiceByPo(po, this, customer);
        }
    }//GEN-LAST:event_showInvoice_menuItemActionPerformed

    private void createInvoice_menuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createInvoice_menuItemActionPerformed
        final String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            final Po po = getSelectedPoObject(polist);
            NewInvoiceUtilVars.setPo(po);
            System.out.println("Setting purchase order " + po);
            this.dispose();
            FirebaseMethods.generateInvoiceId(id, custname, po, this);

        }
    }//GEN-LAST:event_createInvoice_menuItemActionPerformed

    private void jCheckBoxMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxMenuItem1ActionPerformed
//        Po po = getSelectedPoObject(polist);
//        NewInvoiceUtilVars.setPo(po);
//
//        final JFileChooser fc = new JFileChooser();
//        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx", "excel");
//        fc.addChoosableFileFilter(filter);
//        fc.setFileFilter(filter);
//        int returnVal = fc.showSaveDialog(this);
//        File f = fc.getSelectedFile();

        //POReport report = new POReport(po, f.getAbsolutePath());
        //report.generate();
        String id = getSelectedId();
        if (id.equals("")) {
            JOptionPane.showMessageDialog(this, "Please Select A Customer");
        } else {

            JFileChooser chooser = new JFileChooser();
            // chooser.setCurrentDirectory(new File("/home/me/Documents"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                final String file_path = chooser.getSelectedFile().getAbsolutePath();
                Po po = getSelectedPoObject(polist);
                UtilVars.setPo(po);
                ShowAllPurchaseOrderVars.setCustomer(UtilVars.getCustomer());
                ShowAllPurchaseOrderVars.setPolist(polist);
                this.dispose();
                FirebaseMethods.getInvoiceByPoToReport(po, this, customer, file_path);
            }

        }
    }//GEN-LAST:event_jCheckBoxMenuItem1ActionPerformed

    private Po getSelectedPoObject(ArrayList<Po> polist) {
        Po po = null;
        String id = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        for (Po po1 : polist) {
            if (po1.getPoid().equalsIgnoreCase(id)) {
                po = po1;
                break;
            }
        }
        return po;
    }

    public String getSelectedId() {
        String val = "";
        try {
            int row = jTable1.getSelectedRow();

            val = jTable1.getValueAt(row, 0) + "";

        } catch (IndexOutOfBoundsException ex) {
            //ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Please select id to update");
        }
        return val;
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
            java.util.logging.Logger.getLogger(ShowAllPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowAllPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowAllPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowAllPurchaseOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ShowAllPurchaseOrder dialog = new ShowAllPurchaseOrder(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnGetAllLaborInvoie;
    private javax.swing.JButton btn_generateLaborInvoice;
    private javax.swing.JButton btn_generateinvoice;
    private javax.swing.JButton btn_showinvoice;
    private javax.swing.JButton btn_updatepo;
    private javax.swing.JButton btn_updatepo1;
    private javax.swing.JMenuItem createInvoice_menuItem;
    private javax.swing.JMenuItem delPo_menuItem;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JMenuItem showInvoice_menuItem;
    private javax.swing.JMenuItem updatePO_menuItem;
    // End of variables declaration//GEN-END:variables
}
