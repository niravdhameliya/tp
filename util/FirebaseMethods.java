/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import com.mileset.neeleshengineers.View.Quatation.ShowAllQuatations;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference.CompletionListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mileset.neeleshengineers.View.Invoice.NewInvoice1;
import com.mileset.neeleshengineers.View.Invoice.ShowAllInvoicesByPoid1;
import com.mileset.neeleshengineers.View.PurchaseOrder.PurchaseOrder;
import com.mileset.neeleshengineers.View.Quatation.Quatation;
import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrder;
import com.mileset.neeleshengineers.View.PurchaseOrder.ShowAllPurchaseOrderVars;
import com.mileset.neeleshengineers.View.SupplyOrder.AddNewSupplyOrder;
import com.mileset.neeleshengineers.View.SupplyOrder.ShowAllSupplyOrder;
import com.mileset.neeleshengineers.View.laborinvoice.ShowAllLaborInvoicesByPoid1;
import com.mileset.neeleshengineers.View.newlaborinvoice.NewLabourInvoice;
import com.mileset.neeleshengineers.View.pricebid.ShowAllPricelBid;
import com.mileset.neeleshengineers.View.technicalbid.ShowAllTechnicalBid;
import com.mileset.neeleshengineers.controller.servicePO.getLabourInvoiceFromCustId;
import com.mileset.neeleshengineers.controller.servicePO.labourCustIDCallBack;
import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Invoice.InvoiceMasterData;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import com.mileset.neeleshengineers.modal.pricebid.PriceBidItems;
import com.mileset.neeleshengineers.modal.quatation.QuatationData;
import com.mileset.neeleshengineers.modal.quatation.QuatationItems;
import com.mileset.neeleshengineers.modal.supplyorder.SupplyOrder;
import com.mileset.neeleshengineers.modal.supplyorder.SupplyOrderItems;
import com.mileset.neeleshengineers.modal.technicalbid.TechnicalBid;
import com.mileset.neeleshengineers.modal.technicalbid.TechnicalBidItems;
import com.mileset.neeleshengineers.report.dao.B2BModel;
import com.mileset.neeleshengineers.report.dao.POReportNew;
import com.mileset.neeleshengineers.report.writer.ReportGenerator;
import java.awt.Container;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Rahul
 */
public class FirebaseMethods {

    //static ArrayList<Customer> customers = new ArrayList<>();
    public FirebaseMethods() {
        System.out.println("counstructer");
        FileInputStream serviceAccount = null;
        try {

//            serviceAccount = new FileInputStream("D://serviceAccountKey.json");
            serviceAccount = new FileInputStream("I://neeleshengineers2.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    //.setDatabaseUrl("https://neeleshengineers-12da7.firebaseio.com/")
                    .setDatabaseUrl("https://neeleshengineers-d1bd5.firebaseio.com/")
                    .build();

            FirebaseApp df = FirebaseApp.initializeApp(options);

            System.out.println("tryng to get name" + df.getName());

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (IllegalStateException ex) {
            return;
        }

    }

    public static void getAllInvoicesForReport(final JDialog jframe, final Date from, final Date to, final boolean isAllCust, final String custName, ArrayList<B2BModel> b2blist1) throws Exception {
        System.out.println("gstin of selected:" + custName);

        final ArrayList<B2BModel> b2blist = b2blist1;
        if (Utility.isNetIsAvailable()) {
            try {
                System.out.println("tax invoice");
                FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = defaultDatabase.getReference("data");
                Query query = mRef.child("invoice");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        System.out.println("full data snapshot " + ds);
                        if (ds.exists()) {
                            System.out.println("in if");
                            ArrayList<Invoice_details> invoice_details = new ArrayList<>();
                            for (DataSnapshot singleSnapshot : ds.getChildren()) {
                                System.out.println("for loop");
                                Invoice_details invoice = singleSnapshot.getValue(Invoice_details.class);

                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                                Date innerDate = null;
                                try {
                                    innerDate = sdf.parse(invoice.getDate());
                                    System.out.println("inner date");
                                } catch (ParseException ex) {
                                    Logger.getLogger(FirebaseMethods.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                if (isAllCust) {
                                    System.out.println("all cust");
                                    if (innerDate.equals(to) || innerDate.equals(from)) {
                                        invoice_details.add(invoice);
                                    } else if ((innerDate.before(to) && innerDate.after(from))) {
                                        invoice_details.add(invoice);
                                    }
                                } else {
                                    System.out.println("not all cust: " + invoice.getGstin());
                                    //if (invoice.getCname().replace("\n", " ").equals(custName)) {
                                    boolean flag = invoice.getGstin().trim().toLowerCase().equals(custName.trim().toLowerCase());
                                    System.out.println("index found: " + flag);
                                    if (flag) {
                                        if (innerDate.equals(to) || innerDate.equals(from)) {
                                            invoice_details.add(invoice);
                                        } else if ((innerDate.before(to) && innerDate.after(from))) {
                                            invoice_details.add(invoice);
                                        }
                                    }
                                }
                            }
                            //System.out.println("invoice for report" + invoice_details);
                            //ArrayList<B2BModel> b2blist = new ArrayList();
                            System.out.println("creating arraylist of b2bmodel");
                            for (int i = 0; i < invoice_details.size(); i++) {
                                double cgst = 0, sgst = 0, igst = 0;
                                cgst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getCgst();
                                sgst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getSgst();
                                igst = ((invoice_details.get(i).getBasicTotal()) / 100) * invoice_details.get(i).getIgst();
                                B2BModel b1 = new B2BModel();
                                System.out.println("Object created");
                                System.out.println("setting 1");
                                System.out.println("dates: " + invoice_details.get(i).getDate());
                                System.out.println("basic: " + invoice_details.get(i).getBasicTotal());
                                b1.setDate(invoice_details.get(i).getDate());
                                //  System.out.println("setting 2" + invoice_details.get(i).getCname());
                                try {
                                    b1.setGstInNo(Utility.getGstIn(invoice_details.get(i).getCname().replace("\n", " ")));
                                } catch (Exception ex) {
                                    ex.printStackTrace();

                                }

                                System.out.println("setting 3");
                                b1.setInvoiceId(invoice_details.get(i).getInvoice_id());
                                System.out.println("setting 4");
                                b1.setInvoiceValue(invoice_details.get(i).getBasicTotal());
                                System.out.println("setting 5");
                                System.out.println("cname: " + invoice_details.get(i).getCname());
                                b1.setPlaceOfSupply(Utility.getPlaceOfSupply(invoice_details.get(i).getCname().replace("\n", " ")));
                                System.out.println("setting 6");
                                b1.setInvoiceType("Regular");
                                System.out.println("setting 7");
                                b1.setTaxableValue((int) (cgst + sgst + igst + invoice_details.get(i).getBasicTotal() + invoice_details.get(i).getOthercharges()));
                                System.out.println("setting 8");
                                b1.setHsn(invoice_details.get(i).getItemList().get(0).getHsn());
                                b2blist.add(b1);

                                System.out.println(b1);
                            }
                            System.out.println("print b2b list " + b2blist);
                            ReportGenerator.generateB2B(b2blist, jframe);
//                        getAllLabourInvoices(jframe, from, to, isAllCust, cID, b2blist);
                        } else {
                            System.out.println("no tax invoice");
//                            JOptionPane.showMessageDialog(jframe, "No Invoice Generated for this Purchase Order");
//                            ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
//                            sap.setVisible(true);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError de) {
                        System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("in finally");
                //    ReportGenerator.generateB2B(b2blist, jframe);
            }
        } else {
            System.out.println("No Internet Connection");
        }
    }

    public static void updateInvoice(final Invoice_details invoicedetails, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("invoice");
        //mRef.child(String.valueOf(new Random().nextInt()));
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        //    int year = now.get(Calendar.YEAR);
        String year = FiscalDate.getInitialYear(now);
        invoicedetails.setYear(String.valueOf(year));
        if (Utility.isNetIsAvailable()) {
            mRef.child(invoicedetails.getFid()).setValue(invoicedetails, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invoice Updated Successfully.");
                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void updateLaborInvoice(final LaborInvoiceDetails invoicedetails, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("LaborInvoice");
        //mRef.child(String.valueOf(new Random().nextInt()));
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        invoicedetails.setYear(String.valueOf(year));
        if (Utility.isNetIsAvailable()) {
            mRef.child(invoicedetails.getFid()).setValue(invoicedetails, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invoice Updated Successfully.");

                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void deleteTb(final String tbId, final JDialog myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("MyTechBids");
        mRef.child(tbId).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                //JOptionPane.showMessageDialog(myFrame,"Record deleted successfully");
                System.out.println("tbId deleted " + tbId);
            }
        });
    }

    public static void deleteInvoice(final Invoice_details invoice, final JDialog myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("invoice");
        mRef.child(invoice.getFid()).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                //JOptionPane.showMessageDialog(myFrame,"Record deleted successfully");
                System.out.println("invoice deleted " + invoice.getFid());
            }
        });
    }

    public static void deletePb(final String pbId, final JDialog myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("MyPriceBids");
        mRef.child(pbId).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                //JOptionPane.showMessageDialog(myFrame,"Record deleted successfully");
                System.out.println("pbId deleted " + pbId);
            }
        });
    }

    public static void deleteQuatation(final String fId, final JDialog myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("quatation");
        mRef.child(fId).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                //JOptionPane.showMessageDialog(myFrame,"Record deleted successfully");
                System.out.println("po deleted " + fId);
            }
        });
    }

    public static void deletePo(final String poId, final JDialog myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("purchase_order");
        mRef.child(poId).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                //JOptionPane.showMessageDialog(myFrame,"Record deleted successfully");
                System.out.println("po deleted " + poId);
            }
        });
    }

    public static void deleteCustomer(final String custId, final JInternalFrame myFrame) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("customers");
        mRef.child(custId).setValue(null, new CompletionListener() {
            @Override
            public void onComplete(DatabaseError de, DatabaseReference dr) {
                JOptionPane.showMessageDialog(myFrame, "Record deleted successfully");
            }
        });
    }

    public static void getAllSupplyOrderByCustomer(final Customer c, final JInternalFrame jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("SupplyOrderDb");
        Query query = mRef.orderByChild("cname").equalTo(c.getName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {
                    ArrayList<SupplyOrder> solist = new ArrayList<>();
                    System.out.println("Full Snapshot of so " + ds);
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        //SupplyOrder so = new SupplyOrder();
                        SupplyOrder so = (SupplyOrder) singleSnapshot.getValue(SupplyOrder.class);
                        System.out.println("single: " + singleSnapshot);
                        /* so.setCgst((String) singleSnapshot.child("cgst").getValue());
                         so.setCname((String) singleSnapshot.child("cname").getValue());
                         so.setDelivery((String) singleSnapshot.child("delivery").getValue());
                         so.setDiscount((String) singleSnapshot.child("discount").getValue());
                         so.setDispatch((String) singleSnapshot.child("dispatch").getValue());
                         so.setEnclosures((String) singleSnapshot.child("enclosures").getValue());
                         so.setGrandTotal((String) singleSnapshot.child("grandTotal").getValue());
                         so.setIgst((String) singleSnapshot.child("igst").getValue());
                         so.setLessDiscount((String) singleSnapshot.child("lessDiscount").getValue());
                         so.setNote((String) singleSnapshot.child("note").getValue());
                         so.setOtherCharges((String) singleSnapshot.child("otherCharges").getValue());
                         so.setPerCgst((String) singleSnapshot.child("perCgst").getValue());
                         so.setPerIgst((String) singleSnapshot.child("perIgst").getValue());
                         so.setPerSgst((String) singleSnapshot.child("perSgst").getValue());
                         so.setRefDate((String) singleSnapshot.child("refDate").getValue());
                         so.setSODate((String) singleSnapshot.child("sodate").getValue());
                         so.setSONo((String) singleSnapshot.child("sono").getValue());
                         so.setSgst((String) singleSnapshot.child("sgst").getValue());
                         so.setSubTotal((String) singleSnapshot.child("subTotal").getValue());
                         so.setSupplierContact((String) singleSnapshot.child("supplierContact").getValue());
                         so.setSupplierGSTIN((String) singleSnapshot.child("supplierGSTIN").getValue());
                         so.setSupplierNameAddress((String) singleSnapshot.child("supplierNameAddress").getValue());
                         so.setSupplierPAN((String) singleSnapshot.child("supplierPAN").getValue());
                         so.setSupplyOrderItemsList((ArrayList<SupplyOrderItems>) singleSnapshot.child("supplyOrderItemsList").getValue());
                         so.setYour_Ref((String) singleSnapshot.child("your_Ref").getValue());
                         so.setRefDate((String) singleSnapshot.child("refDate").getValue());
                         so.setFid((String) singleSnapshot.child("fid").getValue());*/
                        solist.add(so);
                        System.out.println("Single Snapshot of so " + so);
                    }
                    if (solist.size() > 0) {
                        ShowAllSupplyOrder saso = new ShowAllSupplyOrder(solist, c);
                        saso.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Supply Order Generated for this Customer");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError de) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    public static void addNewLaborInvoice(final LaborInvoiceDetails invoicedetails, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("LaborInvoice");
        //mRef.child(String.valueOf(new Random().nextInt()));
        final String fid = mRef.push().getKey();
        invoicedetails.setFid(fid);
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        String financialYear = FiscalDate.getFinancialYear(now);
        invoicedetails.setYear(financialYear);
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(invoicedetails, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invoice Saved Successfully.");
                        //jframe.dispose();
                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void generateLaborInvoiceId(final JInternalFrame jframe, final boolean isNewLaborInvoice) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            final String financialYear = FiscalDate.getFinancialYear(Calendar.getInstance());
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("LaborInvoice").orderByChild("year").equalTo(financialYear);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    ArrayList<LaborInvoiceDetails> invoices = new ArrayList<>();
                    System.out.println("full data snapshot " + ds);
                    int id = 1;

                    for (DataSnapshot ds1 : ds.getChildren()) {
                        LaborInvoiceDetails lbi = ds1.getValue(LaborInvoiceDetails.class);
                        invoices.add(lbi);
                        id++;
                    }
                    final int finalId = id;
                    Thread worker = new Thread() {
                        public void run() {
                            try {
                                final String generatedId = "LC/" + financialYear + "/" + finalId;
                                System.out.println("Generated LaborInvoiceid is" + generatedId);

                                if (isNewLaborInvoice) {
                                    com.mileset.neeleshengineers.View.newlaborinvoice.NewLabourInvoice lbi = new com.mileset.neeleshengineers.View.newlaborinvoice.NewLabourInvoice(generatedId);
                                    lbi.setVisible(true);
                                } else {
                                    com.mileset.neeleshengineers.View.laborinvoice.NewLaborInvoice lbi = new com.mileset.neeleshengineers.View.laborinvoice.NewLaborInvoice(generatedId);
                                    lbi.setVisible(true);
                                }
                            } catch (Exception ex) {
                                System.out.println("Exception occured " + ex);
                            }
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    //resultLabel.setText("Finished");

                                }
                            });
                        }
                    };
                    worker.start();

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void generateLaborInvoiceId(final JDialog jframe, final boolean isNewLaborInvoice) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            final String financialYear = FiscalDate.getFinancialYear(Calendar.getInstance());
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("LaborInvoice").orderByChild("year").equalTo(financialYear);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    ArrayList<LaborInvoiceDetails> invoices = new ArrayList<>();
                    System.out.println("full data snapshot " + ds);
                    int id = 1;

                    for (DataSnapshot ds1 : ds.getChildren()) {
                        LaborInvoiceDetails lbi = ds1.getValue(LaborInvoiceDetails.class);
                        invoices.add(lbi);
                        id++;
                    }
                    String generatedId = "LC/" + financialYear + "/" + id;
                    System.out.println("Generated LaborInvoiceid is" + generatedId);
                    if (isNewLaborInvoice) {
                        com.mileset.neeleshengineers.View.newlaborinvoice.NewLabourInvoice lbi = new NewLabourInvoice(generatedId);
                        lbi.setVisible(true);
                    } else {
                        com.mileset.neeleshengineers.View.laborinvoice.NewLaborInvoice lbi = new com.mileset.neeleshengineers.View.laborinvoice.NewLaborInvoice(generatedId);
                        lbi.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void getPurchaseOrderByCustomer(final JInternalFrame frame, final Customer customer) {
        if (Utility.isNetIsAvailable()) {
            final ArrayList<Po> polist = new ArrayList();
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("purchase_order").orderByChild("cust_id").equalTo(customer.getId());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {

                            Po po = singleSnapshot.getValue(Po.class);
                            System.out.println("po is here " + po);
                            polist.add(po);
                        }
                        // JOptionPane.showMessageDialog(frame,"All Purchase ordrer got successfully");
                        ShowAllPurchaseOrderVars.setCustomer(customer);
                        ShowAllPurchaseOrderVars.setPolist(polist);
                        ShowAllPurchaseOrder spo = new ShowAllPurchaseOrder(polist, customer);
                        spo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No Purchase Order Generated for this Customer");
                    }

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {

            JOptionPane.showMessageDialog(frame, "No Internet Connection");
        }
    }

    public static void getQuatationByCustomer(final JInternalFrame frame, String id) {
        final ArrayList<QuatationData> customers = new ArrayList<>();
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("quatation").orderByChild("custId").equalTo(id);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            System.out.println("single snapshot is here " + ds1);
                            //Customer c =ds1.getValue(Customer.class);
/*                            String billDate = (String) ds1.child("billDate").getValue();
                             String custId = (String) ds1.child("custId").getValue();
                             String fid = (String) ds1.child("fid").getValue();
                             String quatationId = (String) ds1.child("quatationId").getValue();
                             String taxes = (String) ds1.child("taxes").getValue();
                             String delivery = (String) ds1.child("delivery").getValue();
                             String payment = (String) ds1.child("payment").getValue();
                             String validity = (String) ds1.child("validity").getValue();
                             String mode_of_dispatch = (String) ds1.child("mode_of_dispatch").getValue();
                             String remark = (String) ds1.child("remark").getValue();
                             String quatation_type = (String) ds1.child("quatation_type").getValue();
                             ArrayList<QuatationItems> quatationItemses = (ArrayList<QuatationItems>) ds1.child("quatationItemses").getValue();
                             QuatationData quatationData = new QuatationData();
                             quatationData.setTaxes(taxes);
                             quatationData.setDelivery(delivery);
                             quatationData.setPayment(payment);
                             quatationData.setQuatation_type(quatation_type);
                             quatationData.setValidity(validity);
                             quatationData.setMode_of_dispatch(mode_of_dispatch);
                             quatationData.setRemark(remark);
                             quatationData.setBillDate(billDate);
                             quatationData.setCustId(custId);
                             quatationData.setFid(fid);
                             quatationData.setQuatationId(quatationId);
                             quatationData.setQuatationItemses(quatationItemses);
                             System.out.println(quatationData);*/
                            QuatationData quatationData = (QuatationData) ds1.getValue(QuatationData.class);
                            customers.add(quatationData);
                        }
                        System.out.println("no of records " + customers.size());
                        // showMessage(sb.toString(), null);
                        System.out.println("customers are here" + customers);
                        ShowAllQuatations show = new ShowAllQuatations(customers);
                        show.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "No Quatations generated for this customer");
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void addNewPriceBid1(PriceBid pb, final JDialog jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("MyPriceBids");
            String fid = mRef.push().getKey();
            pb.setFid(fid);
            System.out.println("Iterating before adding");
            for (PriceBidItems item : pb.getPriceBidItems()) {
                System.out.println(item);
                System.out.println(item.getBasic_rate());
            }
            mRef.child(fid).setValue(pb, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "PriceBid Saved Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No Internet Connection");
        }
    }

    public static void addNewTechnicalBid1(TechnicalBid tb, final JDialog jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("MyTechBids");
            String fid = mRef.push().getKey();
            tb.setFid(fid);
            System.out.println("Iterating before adding");
            for (TechnicalBidItems item : tb.getTechnicalBidItems()) {
                System.out.println(item);
                System.out.println(item.getRemarks());
            }
            mRef.child(fid).setValue(tb, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Technical Saved Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No Internet Connection");
        }
    }

    public static void getAllPriceBid1(final JInternalFrame jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            System.out.println("Searching price bid for customers " + UtilVars.getCustomer().getName());
            Query query = mRef.child("MyPriceBids").orderByChild("custName").equalTo(UtilVars.getCustomer().getName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    ArrayList<PriceBid> priceBidList = new ArrayList();
                    System.out.println("full data snapshot is here " + ds);
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        PriceBid mpb = singleSnapshot.getValue(PriceBid.class);
                        priceBidList.add(mpb);
                    }
                    if (priceBidList.size() > 0) {
                        ShowAllPricelBid showAllPriceBid = new ShowAllPricelBid(priceBidList);
                        showAllPriceBid.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(jframe, "No PriceBids Found for this Customer");
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {

                }
            });
        } else {
            JOptionPane.showMessageDialog(jframe, "No Internet Connection");
        }
    }

    public static void getAllTechnicalBid1(final JInternalFrame jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("MyTechBids").orderByChild("custName").equalTo(UtilVars.getCustomer().getName());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    ArrayList<TechnicalBid> techBids = new ArrayList<TechnicalBid>();
                    System.out.println("full data snapshot is here " + ds);
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        TechnicalBid tb = singleSnapshot.getValue(TechnicalBid.class);
                        techBids.add(tb);
                    }
                    if (techBids.size() > 0) {
                        ShowAllTechnicalBid satb = new ShowAllTechnicalBid(techBids);
                        satb.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Technical Bid found for this Customer");
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {

                }
            });
        } else {
            JOptionPane.showMessageDialog(jframe, "No Internet Connection");
        }
    }

    public static void showAllQuatations() {
        final ArrayList<QuatationData> customers = new ArrayList<>();
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("quatation");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        System.out.println("single snapshot is here " + ds1);
                        //Customer c =ds1.getValue(Customer.class);
                        String billDate = (String) ds1.child("billDate").getValue();
                        String custId = (String) ds1.child("custId").getValue();
                        String fid = (String) ds1.child("fid").getValue();
                        String quatationId = (String) ds1.child("quatationId").getValue();
                        String quatationType = (String) ds1.child("quatation_type").getValue();
                        ArrayList<QuatationItems> quatationItemses = (ArrayList<QuatationItems>) ds1.child("quatationItemses").getValue();
                        QuatationData quatationData = new QuatationData();
                        quatationData.setBillDate(billDate);
                        quatationData.setCustId(custId);
                        quatationData.setFid(fid);
                        quatationData.setQuatation_type(quatationType);
                        quatationData.setQuatationId(quatationId);
                        quatationData.setQuatationItemses(quatationItemses);
                        System.out.println(quatationData);
                        customers.add(quatationData);
                    }
                    System.out.println("no of records " + customers.size());
                    // showMessage(sb.toString(), null);
                    System.out.println("customers are here" + customers);
                    ShowAllQuatations show = new ShowAllQuatations(customers);
                    show.setVisible(true);
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void getInvoiceByPo1(Po po, final JDialog jframe, final Customer customer) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("invoice").orderByChild("pono").equalTo(po.getPoid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        ArrayList<Invoice_details> invoice_details = new ArrayList<>();

                        for (DataSnapshot singleSnapshot : ds.getChildren()) {
                            Invoice_details invoice = singleSnapshot.getValue(Invoice_details.class
                            );
                            invoice_details.add(invoice);
                            System.out.println("invoice " + invoice);
                        }
                        ShowAllInvoicesByPoid1 show = new ShowAllInvoicesByPoid1(invoice_details, customer);
                        show.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Invoice Generated for this Purchase Order");
                        ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                        sap.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }
    }

    public static void getLaborInvoiceByPo(Po po, final JDialog jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("LaborInvoice").orderByChild("pono").equalTo(po.getPoid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        ArrayList<LaborInvoiceDetails> invoice_details = new ArrayList<>();
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {
                            LaborInvoiceDetails invoice = singleSnapshot.getValue(LaborInvoiceDetails.class);
                            invoice_details.add(invoice);
                            System.out.println("Laborinvoice " + invoice);
                        }
                        ShowAllLaborInvoicesByPoid1 show = new ShowAllLaborInvoicesByPoid1(invoice_details);
                        show.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Invoice Generated for this Purchase Order");
                        ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                        sap.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }
    }

    public static void getInvoiceByPo(Po po, final JDialog jframe, final Customer customer) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("invoice").orderByChild("pono").equalTo(po.getPoid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        ArrayList<Invoice_details> invoice_details = new ArrayList<>();
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {
                            Invoice_details invoice = singleSnapshot.getValue(Invoice_details.class);
                            invoice_details.add(invoice);
                            System.out.println("invoice " + invoice);
                        }
                        ShowAllInvoicesByPoid1 show = new ShowAllInvoicesByPoid1(invoice_details, customer);
                        show.setVisible(true);

                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Invoice Generated for this Purchase Order");
                        ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                        sap.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }
    }

    public static void getInvoiceByPoToReport(final Po po, final JDialog jframe, final Customer customer, final String file_path) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("invoice").orderByChild("pono").equalTo(po.getPoid());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    if (ds.exists()) {
                        ArrayList<Invoice_details> invoice_details = new ArrayList<>();
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {
                            Invoice_details invoice = singleSnapshot.getValue(Invoice_details.class);
                            invoice_details.add(invoice);
                            System.out.println("invoice " + invoice);
                        }
                        //                 ShowAllInvoicesByPoid1 show = new ShowAllInvoicesByPoid1(invoice_details, customer);
                        //               show.setVisible(true);

                        try {
                            Thread.sleep(5000);

                            POReportNew poi = new POReportNew(jframe, po, invoice_details, file_path);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }

                    } else {
                        JOptionPane.showMessageDialog(jframe, "No Invoice Generated for this Purchase Order");
                        ShowAllPurchaseOrder sap = new ShowAllPurchaseOrder(ShowAllPurchaseOrderVars.getPolist(), ShowAllPurchaseOrderVars.getCustomer());
                        sap.setVisible(true);
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }
    }

    public static void updateTechnicalBid(TechnicalBid technicalBid, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("MyTechBids");
        //mRef.child(String.valueOf(new Random().nextInt()));
        if (Utility.isNetIsAvailable()) {
            mRef.child(technicalBid.getFid()).setValue(technicalBid, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Technical Bid Updated Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void addNewSupplyOrderToDb(SupplyOrder so, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("SupplyOrderDb");
        //mRef.child(String.valueOf(new Random().nextInt()));
        so.setCname(UtilVars.getCustomer().getName());
        final String fid = mRef.push().getKey();
        so.setFid(fid);
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(so, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
//                        JOptionPane.showMessageDialog(jframe, "Supply Order Saved Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void updatePriceBid(PriceBid pricebid, final JDialog jframe) {

        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("MyPriceBids");
        pricebid.setFid(pricebid.getFid());
        if (Utility.isNetIsAvailable()) {
            mRef.child(pricebid.getFid()).setValue(pricebid, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "PriceBid Updated Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void addNewPriceBid(PriceBid pricebid, final JDialog jframe) {

        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("priceBids");
        //mRef.child(String.valueOf(new Random().nextInt()));
        final String fid = mRef.push().getKey();
        pricebid.setFid(fid);
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(pricebid, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "PriceBid Saved Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void addNewQuatation(QuatationData quatationData, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("quatation");
        //mRef.child(String.valueOf(new Random().nextInt()));
        final String fid = mRef.push().getKey();
        final String todayDate = Utility.convertDateToString(new Date());
        quatationData.setFid(fid);
        quatationData.setQuatationIdDate(todayDate);
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int year = now.get(Calendar.YEAR);
        quatationData.setYear(String.valueOf(year));
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(quatationData, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Quatation Saved Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void updateQuatation(QuatationData quatationData, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("quatation");
        //mRef.child(String.valueOf(new Random().nextInt()));
        final String fid = quatationData.getFid();
        //final String todayDate = Utility.convertDateToString(new Date());

        //quatationData.setQuatationIdDate(todayDate);
        // Calendar now = Calendar.getInstance();   // Gets the current date and time
        // int year = now.get(Calendar.YEAR);
        //quatationData.setYear(String.valueOf(year));
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(quatationData, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Quatation updated Successfully.");
                        jframe.dispose();
                    }
                }
            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void generateQuatationId(JInternalFrame jframe, final String custId, int year) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            final String todayDate = Utility.convertDateToString(new Date());

            Query query = mRef.child("quatation").orderByChild("quatationIdDate").equalTo(String.valueOf(todayDate));
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    int id = 1;

                    for (DataSnapshot ds1 : ds.getChildren()) {
                        QuatationData qtn = ds1.getValue(QuatationData.class);
                        id++;
                    }
                    String[] dateParams = todayDate.split("/");
                    //String generateId = "NE/QTN/" + dateParams[0] + dateParams[1] + "-" + id + "/" + dateParams[2].substring(2, 4);
                    String generateId = "NE/QTN/" + dateParams[0] + dateParams[1] + "-" + id + "/" + FiscalDate.getInitialYear(Calendar.getInstance());
                    System.out.println("generated id " + generateId);
                    //Calendar now = Calendar.getInstance();   // Gets the current date and time
                    //int year = now.get(Calendar.YEAR);
                    //int month = now.get(Calendar.MONTH) + 1;
                    //int day = now.get(Calendar.DAY_OF_MONTH);
                    //String generatedId = "NE/QTN/" + day + "" + month + "-" + id + "/" + String.valueOf(year).substring(2, 4);
                    //System.out.println("Generated id is" + generatedId);
                    Quatation quatation = new Quatation(generateId, custId);
                    quatation.setVisible(true);
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void getInvoiceById(String invoicdId, final JDialog jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("invoice").orderByChild("invoice_id").equalTo(invoicdId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);

                    if (ds.exists()) {
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {

                            Po po = singleSnapshot.getValue(Po.class);
                            System.out.println("po is here " + po);
                            JOptionPane.showMessageDialog(jframe, "Purchase ordrer found.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invalid Purchase order id");
                    }

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
        }
    }

//    private static void addInvoiceId(final String id,final JDialog jframe){
//        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference mRef = defaultDatabase.getReference("data").child("invoice_id");
//        //mRef.child(String.valueOf(new Random().nextInt()));
//        final String fid = mRef.push().getKey(); 
//        String date = Utility.convertDateToString(new Date()).replace("/", "");
//        if (Utility.isNetIsAvailable()) {
//            mRef.child(date).setValue(id, new CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError de, DatabaseReference dr) {
//                    if (de != null) {
//                          JOptionPane.showMessageDialog(jframe,"Error in saving record ");
//                    } else {
//                          JOptionPane.showMessageDialog(jframe,"Record saved");
//                    }
//                }
//
//            });
//
//        } else {
//                JOptionPane.showMessageDialog(jframe,"No internet connection");
//        }
//    }
    public static void addNewInvoice(final Invoice_details invoicedetails, final JDialog jframe) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data").child("invoice");
        //mRef.child(String.valueOf(new Random().nextInt()));
        final String fid = mRef.push().getKey();
        invoicedetails.setFid(fid);
        Calendar now = Calendar.getInstance();   // Gets the current date and time
//        int year = now.get(Calendar.YEAR);
        String year = FiscalDate.getInitialYear(now);
        invoicedetails.setYear(String.valueOf(year));
        if (Utility.isNetIsAvailable()) {
            mRef.child(fid).setValue(invoicedetails, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record ");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invoice Saved Successfully.");

                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet connection");
        }
    }

    public static void addNewCustomer(final JDialog jframe, final Customer c) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        String fid = mRef.push().getKey();
        c.setId(fid);
        if (Utility.isNetIsAvailable()) {
            mRef.child("customers").child(fid).setValue(c, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving Customer" + de.getMessage());
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Customer saved");
                        jframe.dispose();
                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "Check your internet connection");
        }

    }

    public static void generateInvoiceId(final String cust_id, final String custname, final Po po, final JDialog jframe) {
        if (Utility.isNetIsAvailable()) {
            try {

                FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
                DatabaseReference mRef = defaultDatabase.getReference("data");
                Calendar now = Calendar.getInstance();   // Gets the current date and time
                //        final int year = now.get(Calendar.YEAR);
                final String year = FiscalDate.getInitialYear(now);
                Query query = mRef.child("invoice").orderByChild("year").equalTo(String.valueOf(year));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        ArrayList<Invoice_details> invoices = new ArrayList<>();
                        System.out.println("full data snapshot " + ds);
                        int id = 1;
                        for (DataSnapshot ds1 : ds.getChildren()) {
                            Invoice_details invoice_details = ds1.getValue(Invoice_details.class);
                            invoices.add(invoice_details);
                            id++;
                        }
                        String generatedId = "NE/00" + id + "/" + year;
                        System.out.println("Generated id is" + generatedId);

                        NewInvoice1 newInvoice1 = new NewInvoice1(generatedId, po, invoices);
                        newInvoice1.setVisible(true);

//                    NewInvoiceUtilVars.setGeneratedId(generatedId);
//                    NewInvoiceUtilVars.setPo(po);
//                    NewInvoiceUtilVars.setInvoices(invoices);
                    }

                    @Override
                    public void onCancelled(DatabaseError de) {
                        System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                    }
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void showCustomerIdList() {
        final ArrayList<String> customerIds = new ArrayList<>();
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("customers");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        System.out.println("single snapshot is here " + ds1);
                        //Customer c =ds1.getValue(Customer.class);
                        String id = (String) ds1.child("id").getValue();
                        customerIds.add(id);
                    }
                    System.out.println("no of records " + customerIds.size());
                    // showMessage(sb.toString(), null);
                    Object[] options = new Object[customerIds.size()];
                    for (int i = 0; i < customerIds.size(); i++) {
                        options[i] = customerIds.get(i);
                    }
                    JComboBox optionList = new JComboBox(options);
                    //JOptionPane.showMessageDialog(null, optionList, "Select Customer Id", JOptionPane.QUESTION_MESSAGE);
                    int n = JOptionPane.showOptionDialog(null,
                            "Choose a mode to play ",
                            "New Game",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null, //do not use a custom Icon
                            options, //the titles of buttons
                            options[0]); //default button title        
                    System.out.println("selected id " + n);
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }

    }

    public static void getAllPurchaseOrders(final JInternalFrame frame, final Customer customer) {
        if (Utility.isNetIsAvailable()) {
            final ArrayList<Po> polist = new ArrayList();
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("purchase_order");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);

                    if (ds.exists()) {
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {

                            Po po = singleSnapshot.getValue(Po.class);
                            System.out.println("po is here " + po);
                            polist.add(po);
                        }
                        //JOptionPane.showMessageDialog(frame, "All Purchase ordrer got successfully");
                        ShowAllPurchaseOrder spo = new ShowAllPurchaseOrder(polist, customer);
                        spo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid Purchase order id");
                    }

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {

            JOptionPane.showMessageDialog(frame, "No Internet Connection");
        }
    }

    /**
     * This method will return Single Purchase Order
     *
     * @param poid this is purchase order id
     * @return Purcahse Order Object
     */
    public static void getPurchaseOrder(String poid, final JInternalFrame jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("purchase_order").orderByChild("poid").equalTo(poid);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);

                    if (ds.exists()) {
                        for (DataSnapshot singleSnapshot : ds.getChildren()) {

                            Po po = singleSnapshot.getValue(Po.class
                            );
                            System.out.println("po is here " + po);
                            JOptionPane.showMessageDialog(jframe, "Purchase ordrer found.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Invalid Purchase order id");
                    }

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void updatePurchaseOrder(final JDialog jframe, Po po) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        if (Utility.isNetIsAvailable()) {
            mRef.child("purchase_order").child(po.getFid()).setValue(po, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record " + de.getMessage());
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Purchase Order Updated Successfully ");
                        jframe.dispose();
                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet Connection");
        }
    }

    /**
     * This method is used to add the Purchaase Order Object(i.e
     * com.mileset.neeleshengineersmodal.Po.java) to firebase database. Above
     * object is added at node of data/purchase_order Parameters will be JFrame
     * :- Required to show actual result status i.e. data saved or not to user
     * via JOptionPane.ShowMesage Po :- object to be saved
     */
    public static void addPurchaseOrderToDb(final JDialog jframe, Po po) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        String fid = mRef.push().getKey();
        po.setFid(fid);
        if (Utility.isNetIsAvailable()) {
            mRef.child("purchase_order").child(fid).setValue(po, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Error in saving record " + de.getMessage());
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Purchase Order Saved Successfully ");
                        jframe.dispose();
                    }
                }

            });

        } else {
            JOptionPane.showMessageDialog(jframe, "No internet Connection");
        }
    }

    public static void getAllCustmers1(final JTable jTable1) {
        System.out.println("getAllCustmers1 called");
        if (Utility.isNetIsAvailable()) {
            System.out.println("getAllCustmers1 inside netIsAvailable");
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("customers");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        System.out.println(ds1);
                    }

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static ArrayList<Customer> getAllCustomers(final JTable jTable1) {
        System.out.println("GetAllCustomers called here");
        final ArrayList<Customer> customers = new ArrayList<>();
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data").child("customers");
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("full data snapshot " + ds);
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        System.out.println("single snapshot is here " + ds1);
                        //Customer c =ds1.getValue(Customer.class);
                        String address = (String) ds1.child("address").getValue();
                        String contact = (String) ds1.child("contact").getValue();
                        String email = (String) ds1.child("email").getValue();
                        String gstin = (String) ds1.child("gstin").getValue();
                        String id = (String) ds1.child("id").getValue();
                        String name = (String) ds1.child("name").getValue();
                        String panNo = (String) ds1.child("panNo").getValue();
                        String vendor_code = (String) ds1.child("vendor_code").getValue();
                        String cgst = (String) ds1.child("cgst").getValue();
                        String sgst = (String) ds1.child("sgst").getValue();
                        String igst = (String) ds1.child("igst").getValue();
                        Customer c = new Customer();
                        c.setAddress(address);
                        c.setContact(contact);
                        c.setEmail(email);
                        c.setGstin(gstin);
                        c.setId(id);
                        c.setName(name);
                        c.setPanNo(panNo);
                        c.setVendor_code(vendor_code);
                        System.out.println("cgst " + cgst);
                        System.out.println("sgst " + sgst);
                        System.out.println("igst" + igst);
                        c.setCgst(cgst);
                        c.setSgst(sgst);
                        c.setIgst(igst);
                        //System.out.println(c);
                        customers.add(c);
                    }
                    System.out.println("no of records " + customers.size());
                    // showMessage(sb.toString(), null);
                    System.out.println("customers are here" + customers);

                    Utility.setToTable(customers, jTable1);

                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }

        return customers;

    }

    public static void updateCustomer(final JTable jtable, final JDialog jframe, String fid, String name, String gstin, String address, String contact, String email, String vendor_code, String panNo, String cgst, String sgst, String igst) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Customer c = new Customer(fid, name, contact, gstin, address, email, vendor_code, panNo, cgst, sgst, igst);
        if (Utility.isNetIsAvailable()) {
            mRef.child("customers").child(fid).setValue(c, new CompletionListener() {
                @Override
                public void onComplete(DatabaseError de, DatabaseReference dr) {
                    if (de != null) {
                        JOptionPane.showMessageDialog(jframe, "Fail to update record...try again");
                    } else {
                        JOptionPane.showMessageDialog(jframe, "Customer data Updated");
                        jframe.dispose();
                        getAllCustomers(jtable);
                    }

                }

            });

        } else {
            //showMessage("No Intenet Connection", null);
        }
    }

    public static Customer getSingleCustomer(String customer_id) {
        System.out.println("getSingleCustomer called");

        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("customers").orderByKey().equalTo(customer_id);

            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    System.out.println("inside ondatachange" + ds);
                    for (DataSnapshot ds1 : ds.getChildren()) {
                        if (ds1.exists()) {
                            System.out.println("Record found");
                            Customer c = ds1.getValue(Customer.class);
//                            String address = (String) ds1.child("address").getValue();
//                            String contact = (String) ds1.child("contact").getValue();
//                            String email = (String) ds1.child("email").getValue();
//                            String gstin = (String) ds1.child("gstin").getValue();
//                            String id = (String) ds1.child("id").getValue();
//                            String name = (String) ds1.child("name").getValue();
//                            String vendor_code = (String) ds1.child("vendor_code").getValue();
//                            
//                            Customer c = new Customer(id, name, gstin, address, contact, email, vendor_code);
                            System.out.println(c);
                        } else {
                            System.out.println("No Record found");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            //showMessage("No Internet Connection", null);
        }
        return UtilVars.getCustomer();
    }

    public static void generateSoId(final JInternalFrame jframe) {
        if (Utility.isNetIsAvailable()) {
            FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
            DatabaseReference mRef = defaultDatabase.getReference("data");
            Query query = mRef.child("SupplyOrderDb");
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    ArrayList<SupplyOrder> sos = new ArrayList<>();
                    System.out.println("full data snapshot " + ds);
                    int id = 1;

                    for (DataSnapshot ds1 : ds.getChildren()) {
                        id++;
                    }
                    System.out.println("Generated id for so is" + id);

                    AddNewSupplyOrder anso = new AddNewSupplyOrder(id + "");
                    anso.setVisible(true);
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
                }
            });

        } else {
            System.out.println("No Internet Connection");
            //showMessage("No Internet Connection", null);
        }
    }

    public static void getWarningPo(final JTable jtable) {
        FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance();
        DatabaseReference mRef = defaultDatabase.getReference("data");
        Query query = mRef.child("purchase_order");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                System.out.println("full data snapshot " + ds);
                ArrayList<Po> warningPos = new ArrayList();
                if (ds.exists()) {
                    for (DataSnapshot singleSnapshot : ds.getChildren()) {
                        Po po = singleSnapshot.getValue(Po.class);
                        System.out.println("po is here " + po);
                        Date poDate = Utility.convertStringToDate(po.getDelivery_date());
                        System.out.println("Po date is here " + poDate);
                        Calendar calAfterSevenDays = Calendar.getInstance();
                        calAfterSevenDays.add(Calendar.DAY_OF_MONTH, 7);
                        Date d1 = calAfterSevenDays.getTime();
                        System.out.println("dateAfterSevenDays After Conerting " + d1);
                        System.out.println("Before test" + poDate.before(d1));
                        System.out.println("After Test" + poDate.after(new Date()));
                        System.out.println("new date " + new Date() + "po date " + poDate + "ater 7 days " + d1);
                        if (poDate.before(d1) && poDate.after(new Date())) {
                            System.out.println("This is warning po " + po);
                            warningPos.add(po);
                        }
                        Utility.setToPoWarningsTable(warningPos, jtable);
                        System.out.println("list of warning pos " + warningPos);
                    }
                    //JOptionPane.showMessageDialog(frame, "All Purchase ordrer got successfully");

                } else {
                    //JOptionPane.showMessageDialog(frame, "Invalid Purchase order id");
                }

            }

            @Override
            public void onCancelled(DatabaseError de) {
                System.out.println("FirebaseMethods : getAllCustomers " + de.getMessage());
            }
        });

    }
}
