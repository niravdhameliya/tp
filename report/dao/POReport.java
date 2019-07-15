package com.mileset.neeleshengineers.report.dao;

import com.mileset.neeleshengineers.controller.servicePO.allInvoiceCallBack;
import com.mileset.neeleshengineers.controller.servicePO.getInvoicesListFromPOId;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.Po;
import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.util.UtilVars;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POReport {

    Po currentPo;
    String filePath;
    HashMap<String, Integer> invoiceItemsQuantityCount, poItemsQuantityCount, remainingItemsQuantityCount;
    boolean flag = false;

    public POReport(Po po, String filePath) {
        this.currentPo = po;
        this.filePath = filePath;
        poItemsQuantityCount = new HashMap();
        invoiceItemsQuantityCount = new HashMap();
        remainingItemsQuantityCount = new HashMap();

    }

    public void generate() {
        ArrayList<PoItems> poItems = currentPo.getItems();
        for (int g = 0; g < poItems.size(); g++) {
            PoItems poItem = poItems.get(g);
            poItemsQuantityCount.put(poItem.getItem_name(), Integer.parseInt(poItem.getQuanatity().trim().split(" ")[0]));
            invoiceItemsQuantityCount.put(poItem.getItem_name(), 0);
            remainingItemsQuantityCount.put(poItem.getItem_name(), 0);

        }

        Workbook writingBook = new XSSFWorkbook();
        Sheet writingSheet = writingBook.createSheet("PO Report");
        writingSheet.setColumnWidth(0, 7000);
        writingSheet.setColumnWidth(1, 7000);
        writingSheet.setColumnWidth(2, 7000);
        writingSheet.setColumnWidth(3, 7000);
        writingSheet.setColumnWidth(4, 7000);
        writingSheet.setColumnWidth(5, 7000);
        writingSheet.setColumnWidth(6, 7000);
        Row r0 = writingSheet.createRow(0);
        Row r1 = writingSheet.createRow(2);
        r0.createCell(1).setCellValue("" + UtilVars.getCustomer().getName());
        r1.createCell(1).setCellValue("PO Number: " + currentPo.getPoid());
        r1.createCell(3).setCellValue("PO Date: " + currentPo.getBill_date());
        r1.createCell(6).setCellValue("Delivery  Date: " + currentPo.getDelivery_date());

        writingSheet = showAllItems(writingSheet);

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(filePath + ".xlsx");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(POReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writingBook.write(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(POReport.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(POReport.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Closing the workbook
            writingBook.close();
        } catch (IOException ex) {
            Logger.getLogger(POReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Sheet showAllItems(final Sheet writingSheet) {
        flag = true;
        final ArrayList<PoItems> items = currentPo.getItems();
//        Row r4 = writingSheet.createRow(4);
//        r4.createCell(0).setCellValue("Item Name");
//        r4.createCell(1).setCellValue("Total");
//        r4.createCell(2).setCellValue("Processed");
//        r4.createCell(3).setCellValue("Remaining");

        getInvoicesListFromPOId u = new getInvoicesListFromPOId(currentPo.getPoid());
        u.fetch(new allInvoiceCallBack() {
            @Override
            public void onCallback1(ArrayList<Invoice_details> value) {
                for (int i = 0; i < value.size(); i++) {
                    System.out.println("" + value.get(i).getInvoice_id());
                    Invoice_details invoice = value.get(i);
                    ArrayList<Items> itemList = invoice.getItemList();
                    for (int j = 0; j < itemList.size(); j++) {
                        Items item = itemList.get(j);
                        int invoiceTotalQuantity = invoiceItemsQuantityCount.get(item.getItem_name());
                        System.out.println("1: " + invoiceTotalQuantity);
                        int itemQuantity = item.getQuantity();
                        invoiceTotalQuantity = invoiceTotalQuantity + itemQuantity;
                        System.out.println("2: " + invoiceTotalQuantity);
                        invoiceItemsQuantityCount.replace(item.getItem_name(), invoiceTotalQuantity);
                    }

                }

//                for (int i = 0; i < items.size(); i++) {
//                    Row r = writingSheet.createRow(i + 5);
//                    r.createCell(0).setCellValue(items.get(i).getItem_name());
//                    r.createCell(1).setCellValue(items.get(i).getQuanatity());
//                    r.createCell(2).setCellValue(invoiceItemsQuantityCount.get(items.get(i).getItem_name()));
//  
//                }

            }

            @Override
            public void recordNotPresent() {

            }

        });

        //JOptionPane.showMessageDialog(null, "");
        return writingSheet;
    }

    private Sheet writeItemsCount(Sheet writingSheet, ArrayList<PoItems> items) {
        for (int i = 0; i < items.size(); i++) {
            Row r = writingSheet.createRow(i + 5);
            r.createCell(0).setCellValue(items.get(i).getItem_name());
            r.createCell(1).setCellValue(items.get(i).getQuanatity());
            r.createCell(2).setCellValue(invoiceItemsQuantityCount.get(items.get(i).getItem_name()));
            //r.createCell(2).setCellValue(b2blist.get(i).getDate());
            //r.createCell(3).setCellValue(b2blist.get(i).getInvoiceValue());
        }
        return writingSheet;
    }
}
