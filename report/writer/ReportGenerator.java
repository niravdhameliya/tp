/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.report.writer;

import com.mileset.neeleshengineers.report.dao.B2BModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReportGenerator {

    public static void generateB2B(ArrayList<B2BModel> b2blist, JDialog jframe) {
        JOptionPane.showMessageDialog(jframe, "File generation is in progress");
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel files", "xls", "xlsx", "excel");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        int returnVal = fc.showSaveDialog(jframe);
        File f = fc.getSelectedFile();

        Workbook writingBook = new XSSFWorkbook();
        CellStyle style1 = writingBook.createCellStyle();
        Font font1 = writingBook.createFont();
        font1.setFontHeightInPoints((short) 13);
        font1.setFontName("Arial");
        //font.setColor(IndexedColors.WHITE.getIndex());
        font1.setBold(true);
        font1.setItalic(false);
        style1.setFont(font1);

        Sheet writingSheet = writingBook.createSheet("b2b");
        writingSheet.setColumnWidth(0, 7000);
        writingSheet.setColumnWidth(1, 7000);
        writingSheet.setColumnWidth(2, 7000);
        writingSheet.setColumnWidth(3, 7000);
        writingSheet.setColumnWidth(4, 0);
        writingSheet.setColumnWidth(5, 7000);
        writingSheet.setColumnWidth(6, 7000);
        Row r0 = writingSheet.createRow(0);
        Row r1 = writingSheet.createRow(1);
        r1.createCell(3).setCellValue("Total Invoice Value");
        r1.createCell(6).setCellValue("Total Taxable Value");
        r1.getCell(3).setCellStyle(style1);
        r1.getCell(6).setCellStyle(style1);
        Row r3 = writingSheet.createRow(3);
        r3.createCell(0).setCellValue("GSTIN/UIN of Recipient");
        r3.createCell(1).setCellValue("Invoice Number");
        r3.createCell(2).setCellValue("Invoice date");
        r3.createCell(3).setCellValue("Invoice Value");
        //r3.createCell(4).setCellValue("Place Of Supply");
        r3.createCell(4).setCellValue(" ");
        r3.createCell(5).setCellValue("Invoice Type");
        r3.createCell(6).setCellValue("Taxable Value");
        r3.createCell(7).setCellValue("HSN");

        r3.getCell(0).setCellStyle(style1);
        r3.getCell(1).setCellStyle(style1);
        r3.getCell(2).setCellStyle(style1);
        r3.getCell(3).setCellStyle(style1);
        r3.getCell(4).setCellStyle(style1);
        r3.getCell(5).setCellStyle(style1);
        r3.getCell(6).setCellStyle(style1);
        r3.getCell(7).setCellStyle(style1);

        double totAmt = 0, totTaxAmt = 0;
        for (int i = 0; i < b2blist.size(); i++) {
            System.out.println("writing at " + (i + 4) + " row");
            Row r = writingSheet.createRow(i + 4);
            r.createCell(0).setCellValue(b2blist.get(i).getGstInNo());
            r.createCell(1).setCellValue(b2blist.get(i).getInvoiceId());
            r.createCell(2).setCellValue(b2blist.get(i).getDate());
            r.createCell(3).setCellValue(b2blist.get(i).getInvoiceValue());
            //r.createCell(4).setCellValue(b2blist.get(i).getPlaceOfSupply());
            r.createCell(4).setCellValue(" ");
            r.createCell(5).setCellValue(b2blist.get(i).getInvoiceType());
            r.createCell(6).setCellValue(b2blist.get(i).getTaxableValue());
            r.createCell(7).setCellValue(b2blist.get(i).getHsn());
            totAmt = totAmt + b2blist.get(i).getInvoiceValue();
            totTaxAmt = totTaxAmt + b2blist.get(i).getTaxableValue();
        }
        Row r2 = writingSheet.createRow(2);
        r2.createCell(3).setCellValue(totAmt);
        r2.createCell(6).setCellValue(totTaxAmt);
        JOptionPane.showMessageDialog(null, "Report Generated");
        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(f.getAbsolutePath() + ".xlsx");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            writingBook.write(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Closing the workbook
            writingBook.close();
        } catch (IOException ex) {
            Logger.getLogger(ReportGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
