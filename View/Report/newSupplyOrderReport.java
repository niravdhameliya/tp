/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Report;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mileset.neeleshengineers.modal.supplyorder.SupplyOrder;
import com.mileset.neeleshengineers.modal.supplyorder.SupplyOrderItems;
import com.mileset.neeleshengineers.util.FiscalDate;
import com.mileset.neeleshengineers.util.NumberToWord;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import modal.operations.NumberToWordsConverter;

/**
 *
 * @author rahul
 */
public class newSupplyOrderReport {

    private ArrayList<SupplyOrderItems> supplyOrderItemsList;
    static String companyName = "NEELESH ENGINEERS\n";
    static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "\nOffice & Works: Sector No. 7,Plot. No. 86, PCNTDA , Bhosari, Pune - 411026";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 26,
            Font.BOLDITALIC);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 15,
            Font.BOLD);
    private String billNo;

    private String amountnumber;
    private String amountWords;
    private String FILE = "D:\\NeeleeshEngineers\\supplyorder\\";
    private String website = "www.neeleshengineers.com";
    SupplyOrder supplyOrder;

    //public newSupplyOrderReport(SupplyOrder supplyOrder) {
    public newSupplyOrderReport(SupplyOrder supplyOrder) {
        this.supplyOrder = supplyOrder;
        billNo = this.supplyOrder.getSONo().replaceAll("/", "_");
        billNo = billNo.replaceAll("\\\\", "_");
        //billNo = b;
        //this.amountWords = words;
        File dir = new File(FILE);
        if (dir.exists()) {

        } else {
            dir.mkdir();
        }
        FILE = FILE + billNo + "-" + FiscalDate.getFinancialYear(Calendar.getInstance()) + ".pdf";
        try {
            //supplyOrderItemsList = new ArrayList<>();
            supplyOrderItemsList = this.supplyOrder.getSupplyOrderItemsList();
            Document document = new Document();
            //    PdfWriter.getInstance(document, new FileOutputStream(FILE));
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE));
            //  GeneratePDF.BlackBorder event = new GeneratePDF.BlackBorder();
            //writer.setPageEvent(event);
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addClientInfo(document);
            addItemsData(document);
            addTip(document);
            addFinalBlock(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Supply Order");
        document.addSubject("Supply Order");
        document.addKeywords("Java, PDF, Supply Order, Neelesh Engineers, Swapnil Gandhi");
        document.addAuthor("Swapnil Gandhi");
        document.addCreator("Software By Swapnil Gandhi");
    }

    private void addTitlePage(Document document)
            throws DocumentException, BadElementException, IOException {

        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);

        Paragraph compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font5);
        compName.add("                 " + companyName);
        textt.add(compName);

        Paragraph tagLine = new Paragraph();
        tagLine.setAlignment(Element.ALIGN_CENTER);
        tagLine.setFont(font2);
        tagLine.add("Die & Mould Design, CNC Machine, Export Components, Import Substitutes, All Types Of Gauges");
        textt.add(tagLine);

        /*Paragraph address1 = new Paragraph();
         address1.setAlignment(Element.ALIGN_CENTER);
         address1.setFont(font3);
         //address1.add(add1+ "        Contact: " + phone);
         address1.add(add1);
         textt.add(address1);
         */
        Paragraph address2 = new Paragraph();
        address2.setAlignment(Element.ALIGN_CENTER);
        address2.setFont(font3);
        address2.add(add2);
        textt.add(address2);

        Paragraph ph = new Paragraph();
        ph.setAlignment(Element.ALIGN_CENTER);
        ph.setFont(font3);
        ph.add("\nContact: " + phone);
        textt.add(ph);

        Paragraph iInfo = new Paragraph();
        iInfo.setAlignment(Element.ALIGN_CENTER);
        iInfo.setFont(font3);

        iInfo.add("\nPAN No.:ABJPP1674P                                                     Email:" + mailid);
        textt.add(iInfo);

        Paragraph tInfo = new Paragraph();
        tInfo.setAlignment(Element.ALIGN_CENTER);
        tInfo.setFont(font3);
        tInfo.add("\nGSTIN No.:27ABJPP1674P1ZW                                     Website: " + website);
        textt.add(tInfo);

        PdfPTable finalBlock = new PdfPTable(1);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{70});

        String imFile = "/resources/logo.png";

        // Creating an Image object        
        //    Image image = Image.getInstance(getClass().getClassLoader().getResource(imFile));
        //  System.out.println("poiuy"+image.getCompressionLevel());
        // finalBlock.addCell(image);
        finalBlock.addCell(textt);

        //    finalBlock.addCell(detail);
        Paragraph quot = new Paragraph();
        quot.setAlignment(Element.ALIGN_CENTER);
        quot.setFont(font6);
        quot.add("Supply Order \n\n");
        document.add(quot);
        document.add(finalBlock);
        // document.newPage();
    }

    private void addClientInfo(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);
        textt.setFont(font2);
        textt.add("Supplier,\n" + supplyOrder.getSupplierNameAddress() + "\n");

        Paragraph textt1 = new Paragraph();
        textt1.setAlignment(Element.ALIGN_CENTER);
        textt1.setFont(font3);
        textt1.add("Contact No." + supplyOrder.getSupplierContact() + "\nGSTIN No." + supplyOrder.getSupplierGSTIN() + "\nPAN No." + supplyOrder.getSupplierPAN());

        PdfPCell cell1 = new PdfPCell(textt);
        cell1.setBorder(Rectangle.NO_BORDER);

        PdfPCell cell2 = new PdfPCell(textt1);
        cell2.setBorder(Rectangle.NO_BORDER);

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.setWidthPercentage((float) 100.0);
        leftBlock.addCell(cell1);
        leftBlock.addCell(cell2);

        PdfPTable rightBlock = new PdfPTable(2);
        rightBlock.setWidthPercentage((float) 100.0);
        rightBlock.setWidths(new int[]{50, 50});
        int year = Calendar.getInstance().get(Calendar.YEAR);

        Paragraph p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("S.O. No.: " + supplyOrder.getSONo() + "/" + year);//add here supply no
        PdfPCell cell11 = new PdfPCell(p11);
        cell11.setBorder(Rectangle.BOX);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("S.O. Date: " + supplyOrder.getSODate());//add here SO Date
        PdfPCell cell12 = new PdfPCell(p11);
        cell12.setBorder(Rectangle.BOX);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Your Ref: " + supplyOrder.getYour_Ref());//add here Refererance
        PdfPCell cell13 = new PdfPCell(p11);
        cell13.setBorder(Rectangle.BOX);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Ref. Date: " + supplyOrder.getRefDate());//add here Referance Date
        PdfPCell cell14 = new PdfPCell(p11);
        cell14.setBorder(Rectangle.BOX);

        /*p11 = new Paragraph();
         p11.setAlignment(Element.ALIGN_LEFT);
         p11.add("Your Ref:");
         PdfPCell cell15 = new PdfPCell(p11);
         cell15.setBorder(Rectangle.NO_BORDER);

         p11 = new Paragraph();
         p11.setAlignment(Element.ALIGN_LEFT);
         p11.add(supplyOrder.getYour_Ref());//add here Refererance
         PdfPCell cell16 = new PdfPCell(p11);
         cell16.setBorder(Rectangle.NO_BORDER);
         */
        rightBlock.addCell(cell11);
        rightBlock.addCell(cell12);
        rightBlock.addCell(cell13);
        rightBlock.addCell(cell14);
        //   rightBlock.addCell(cell15);
        //    rightBlock.addCell(cell16);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{40, 60});
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    private void addItemsData(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("Sr. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("W.O No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Particulars"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Rate (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Amount in Rs."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{5f, 8f, 55f, 10f, 11f, 10f};
        table.setWidths(columnWidths);
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        for (int i = 0; i < supplyOrderItemsList.size(); i++) {
            SupplyOrderItems temp = supplyOrderItemsList.get(i);
            int n = i + 1;
            table.addCell(setItemsFont("" + n));
            String WONo = temp.getWorkOrderNo();
            String particulars = temp.getParticulars();
            String quant = temp.getQuantity();
            String basic = setDoubleZeroToText(temp.getRate());
            String total_amount = setDoubleZeroToText(temp.getAmount());
            table.addCell(setItemsFont(WONo));
            table.addCell(setItemsFont(particulars));
            table.addCell(setItemsFont(quant));
            table.addCell(setItemsFont(basic));
            table.addCell(setItemsFont(total_amount));
        }
        String space = "";

        for (int p = 0; p < 24 - (6 * supplyOrderItemsList.size()); p++) {
            space = space + "\n";
        }

        table.addCell(space);
        table.addCell(space);
        table.addCell(space);
        table.addCell(space);
        table.addCell(space);
        table.addCell(space);
        document.add(table);

    }

    private Paragraph setParaHeader(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font4);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        return p;
    }

    private Paragraph setItemsFont(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font3);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        return p;
    }

    public void addTip(Document document) throws DocumentException {
        Paragraph tip = new Paragraph();
        tip.setAlignment(Element.ALIGN_LEFT);
        tip.setFont(font4);
        tip.add("Note: " + supplyOrder.getNote() + "\n");  //add note 
        PdfPCell cell16 = new PdfPCell(tip);
        cell16.setBorder(Rectangle.BOX);
        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.setWidthPercentage((float) 100.0);
        //document.add(tip);
        leftBlock.addCell(cell16);
        document.add(leftBlock);
    }

    public PdfPCell addTerms(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font3);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        PdfPCell cell16 = new PdfPCell(p);
        cell16.setBorder(Rectangle.NO_BORDER);
        return cell16;
    }

    public PdfPCell addAmountInwords(String str) {
        Paragraph p = new Paragraph();
        //p.setFont(font3);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        PdfPCell cell16 = new PdfPCell(p);
        cell16.setBorder(Rectangle.BOTTOM);
        return cell16;
    }

    private void addFinalBlock(Document document) throws DocumentException {
        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{60, 40});

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.getDefaultCell().setBorder(0);
        leftBlock.setWidthPercentage((float) 100.0);

        PdfPTable block1 = new PdfPTable(2);
        block1.setWidthPercentage((float) 100.0);
        block1.setWidths(new int[]{30, 70});
        block1.addCell(setParaHeader("1) Payment"));
        block1.addCell(setParaHeader(supplyOrder.getPayment()));//add payment
        block1.addCell(setParaHeader("2) Dispatch"));
        block1.addCell(setParaHeader(supplyOrder.getDispatch()));//add dispatch
        block1.addCell(setParaHeader("3) Discount(%)"));
        block1.addCell(setParaHeader(supplyOrder.getDiscount())); //add discount percent
        block1.addCell(setParaHeader("4) Delivery"));
        block1.addCell(setParaHeader(supplyOrder.getDelivery()));//add Delivery
        block1.addCell(setParaHeader("5) Enclosures"));
        block1.addCell(setParaHeader(supplyOrder.getEnclosures()));//add enclosures
        PdfPTable block3 = new PdfPTable(1);
        block3.setWidthPercentage((float) 100.0);
        block3.addCell(addTerms("A) Inform Discrepancy/ Non acceptance immediately."));
        block3.addCell(addTerms("B) Inform if schedule can't be meet."));
        block3.addCell(addTerms("C) Ask doubts."));
        block3.addCell(addTerms("D) For deliveries more than one month inform status fortnightly"));

        leftBlock.addCell(block1);
        String s = NumberToWord.NumberToWord(supplyOrder.getGrandTotal());
        leftBlock.addCell(addAmountInwords("Amount (In Words):Rupees " + s + " only"));
        leftBlock.addCell(block3);

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.getDefaultCell().setBorder(0);
        rightBlock.setWidthPercentage((float) 100.0);
        PdfPTable block2 = new PdfPTable(2);
        block2.setWidthPercentage((float) 100.0);
        block2.setWidths(new int[]{45, 55});
        block2.addCell(setParaHeader("Less Discount"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getLessDiscount())));//add less discount
        block2.addCell(setParaHeader("Other Charges"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getOtherCharges())));//add Other Charges
        block2.addCell(setParaHeader("Sub Total"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getSubTotal()))); //add sub total
        block2.addCell(setParaHeader("SGST " + supplyOrder.getPerSgst() + "%"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getSgst())));//add sgst
        block2.addCell(setParaHeader("CGST " + supplyOrder.getPerCgst() + "%"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getCgst())));//add cgst
        block2.addCell(setParaHeader("IGST " + supplyOrder.getPerIgst() + "%"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getIgst())));//add Igst
        // block2.addCell(setParaHeader("Total"));
        //block2.addCell(setParaHeader("Rs. "+supplyOrder.getTotal()));//add total
        block2.addCell(setParaHeader("Grand Total"));
        block2.addCell(setParaHeader(" Rs. " + setDoubleZeroToText(supplyOrder.getGrandTotal())));//add grand Total
        rightBlock.addCell(block2);
        rightBlock.addCell(setParaHeader("For NEELESH ENGINEERS\n\n\n\n\n"));
        rightBlock.addCell("Authorised Signatory");

        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);

    }

    public String setDoubleZeroToText(String str) {
        try {
            double val = Double.parseDouble(str);
            return "" + val;
        } catch (NumberFormatException ex) {
            return str;
        }

    }

    public static void main(String[] args) {

        try {
            // new newSupplyOrderReport("1");
            Desktop.getDesktop().open(new File("I:\\1.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(newSupplyOrderReport1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
