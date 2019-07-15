package com.mileset.neeleshengineers.report.dao;

import com.mileset.neeleshengineers.modal.PoItems;
import com.mileset.neeleshengineers.modal.ServicePO;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceItems;
import com.mileset.neeleshengineers.util.UtilVars;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LabourReportNew {

    ArrayList<LaborInvoiceDetails> invoice_details;
    ServicePO currentPo;
    LaborInvoiceDetails lid;
    HashMap<String, Integer> invoiceItemsQuantityCount, poItemsQuantityCount, remainingItemsQuantityCount;
    final int item_line_space = 5, line_gap = 2;
    String file_path;

    public LabourReportNew(ServicePO poTemp, ArrayList<LaborInvoiceDetails> labourInvoiceList, String file_path) {
        this.currentPo = poTemp;
        this.file_path = file_path;
        //filePath = "D:\\a";
        this.invoice_details = labourInvoiceList;
        invoiceItemsQuantityCount = new HashMap();
        poItemsQuantityCount = new HashMap();
        remainingItemsQuantityCount = new HashMap();
        try {
            generate();
            JOptionPane.showMessageDialog(null, "Report Generated");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void generate() throws Exception {
        ArrayList<PoItems> poItems = currentPo.getItems();

        for (int g = 0; g < poItems.size(); g++) {
            PoItems poItem = poItems.get(g);
            System.out.println("item: " + poItem.getItem_name());
            poItemsQuantityCount.put(poItem.getItem_name(), Integer.parseInt(poItem.getQuanatity().trim().split(" ")[0]));
            invoiceItemsQuantityCount.put(poItem.getItem_name(), 0);
            remainingItemsQuantityCount.put(poItem.getItem_name(), 0);

        }

        Workbook writingBook = new XSSFWorkbook();
        CellStyle style = writingBook.createCellStyle();
        Font font = writingBook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Arial");
        //font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        font.setItalic(false);
        style.setFont(font);

        CellStyle style1 = writingBook.createCellStyle();
        Font font1 = writingBook.createFont();
        font1.setFontHeightInPoints((short) 13);
        font1.setFontName("Arial");
        //font.setColor(IndexedColors.WHITE.getIndex());
        font1.setBold(true);
        font1.setItalic(false);
        style1.setFont(font1);

        Sheet writingSheet = writingBook.createSheet("Service PO Report");
        writingSheet.setColumnWidth(0, 7000);
        writingSheet.setColumnWidth(1, 7000);
        writingSheet.setColumnWidth(2, 7000);
        writingSheet.setColumnWidth(3, 7000);
        writingSheet.setColumnWidth(4, 7000);
        writingSheet.setColumnWidth(5, 7000);
        writingSheet.setColumnWidth(6, 7000);
        Row r0 = writingSheet.createRow(0);
        Row r1 = writingSheet.createRow(3);

        Row r2 = writingSheet.createRow(2);
        r2.createCell(1).setCellValue("Service PO Information");
        r0.createCell(1).setCellValue("" + UtilVars.getCustomer().getName());
        r1.createCell(1).setCellValue("PO Number: " + currentPo.getPoid());
        r1.createCell(3).setCellValue("PO Date: " + currentPo.getBill_date());
        r1.createCell(5).setCellValue("Delivery  Date: " + currentPo.getDelivery_date());
        r0.getCell(1).setCellStyle(style);
        r1.getCell(1).setCellStyle(style);
        r1.getCell(3).setCellStyle(style);
        r1.getCell(5).setCellStyle(style);
        r2.getCell(1).setCellStyle(style1);

        Row r4 = writingSheet.createRow(5);
        r4.createCell(0).setCellValue("Item Name");
        r4.createCell(1).setCellValue("Total");
        r4.createCell(2).setCellValue("Processed");
        r4.createCell(3).setCellValue("Remaining");
        r4.getCell(0).setCellStyle(style);
        r4.getCell(1).setCellStyle(style);
        r4.getCell(2).setCellStyle(style);
        r4.getCell(3).setCellStyle(style);

        final ArrayList<PoItems> items = currentPo.getItems();
        int line_count = 6;
        for (int i = 0; i < invoice_details.size(); i++) {
            System.out.println("" + invoice_details.get(i).getInvoice_id());
            LaborInvoiceDetails invoice = invoice_details.get(i);
            ArrayList<LaborInvoiceItems> itemList = invoice.getItemList();
            for (int j = 0; j < itemList.size(); j++) {
                LaborInvoiceItems item = itemList.get(j);
                System.out.println("---" + item.getItem_name());
                int invoiceTotalQuantity = invoiceItemsQuantityCount.get(item.getItem_name());
                System.out.println("1: " + invoiceTotalQuantity);
                int itemQuantity = item.getQuantity();
                invoiceTotalQuantity = invoiceTotalQuantity + itemQuantity;
                System.out.println("2: " + invoiceTotalQuantity);
                invoiceItemsQuantityCount.replace(item.getItem_name(), invoiceTotalQuantity);
            }
        }

        for (int i = 0; i < items.size(); i++) {
            //Row r = writingSheet.createRow(i + item_line_space);
            Row r = writingSheet.createRow(line_count);
            r.createCell(0).setCellValue(items.get(i).getItem_name());
            r.createCell(1).setCellValue(items.get(i).getQuanatity());
            r.createCell(2).setCellValue(invoiceItemsQuantityCount.get(items.get(i).getItem_name()));
            int remaining = poItemsQuantityCount.get(items.get(i).getItem_name()) - invoiceItemsQuantityCount.get(items.get(i).getItem_name());
            r.createCell(3).setCellValue(remaining);
            System.out.println("" + items.get(i).getQuanatity());
            line_count++;

        }

        //int line_count = item_line_space + items.size() + line_gap;
        line_count++;
        line_count++;
        Row r21 = writingSheet.createRow(line_count);
        r21.createCell(1).setCellValue("Invoice Information");
        r21.getCell(1).setCellStyle(style1);
        line_count++;
        for (int i = 0; i < invoice_details.size(); i++) {
            LaborInvoiceDetails invoice = invoice_details.get(i);
            System.out.println("invoice id: " + invoice.getInvoice_id());
            //line_count = line_count + i;
            Row r = writingSheet.createRow(line_count);
            r.createCell(0).setCellValue("Invoice Id: " + invoice.getInvoice_id());
            r.createCell(2).setCellValue("Challan No: " + invoice.getChallanNo());
            r.getCell(0).setCellStyle(style);
            r.getCell(2).setCellStyle(style);
            line_count++;
            ArrayList<LaborInvoiceItems> itemList = invoice.getItemList();
            for (int j = 0; j < itemList.size(); j++) {
                LaborInvoiceItems item = itemList.get(j);
                //line_count = line_count + i + j;
                Row r5 = writingSheet.createRow(line_count);
                r5.createCell(0).setCellValue(item.getItem_name());
                r5.createCell(1).setCellValue(item.getQuantity() + " " + item.getUnitCode());
                line_count++;
                System.out.println("lines count" + line_count);

            }
            line_count++;
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(file_path + ".xlsx");
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
}
