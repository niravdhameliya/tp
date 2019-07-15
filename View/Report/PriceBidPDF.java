/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Report;

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
import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import com.mileset.neeleshengineers.modal.pricebid.PriceBidItems;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rahul
 */
public class PriceBidPDF {

    String refData = "Reference is made to the above mentioned Tender Enquiry, the price details in respect of the Tender Enquiry are mentioned below:";
    static String companyName = "NEELESH ENGINEERS\n";
    //static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "Work & Office: Sector No. 7,Plot. No. 86, PCNTDA, Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 26,
            Font.BOLDITALIC);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private static final Font font7 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private String billNo;

    private String FILE = "D:\\NeeleeshEngineers\\pricebid\\";
    String quantityType = " Nos.";
    private String website = "www.neeleshengineers.com";
    ArrayList<PriceBidItems> priceBidItemsList;
    PriceBid priceBid;

    public PriceBidPDF(PriceBid pb) {
        this.priceBid = pb;
        this.billNo = this.priceBid.getTenderEnqNo().replaceAll("/", "_");
        this.billNo = billNo.replaceAll("\\\\", "_");
        File dir = new File(FILE);
        if (dir.exists()) {

        } else {
            dir.mkdir();
        }
        priceBidItemsList = priceBid.getPriceBidItems();

        FILE = FILE + billNo + ".pdf";
        try {
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
            addEmptySpace(document, 3);
            addTermsAndConditions(document);
            addSignature(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Price Bid By Neelesh Engineers");
        document.addSubject("Price Bid By Neelesh Engineers");
        document.addKeywords("Java, PDF,Price bid, Neelesh Engineers, Swapnil Gandhi");
        document.addAuthor("Swapnil Gandhi");
        document.addCreator("Software By Swapnil Gandhi");
    }

    private void addTitlePage(Document document)
            throws DocumentException, IOException, URISyntaxException {

        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);

        Paragraph compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font5);
        compName.add("                 " + companyName);
        textt.add(compName);

        Paragraph tagLine = new Paragraph();
        tagLine.setAlignment(Element.ALIGN_CENTER);
        tagLine.setFont(font7);
        tagLine.add("Die & Mould Design, CNC Machine, Export Components, Import Substitutes, All Types Of Gauges\n");
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
        ph.add("Contact: " + phone);
        textt.add(ph);

        Paragraph iInfo = new Paragraph();
        iInfo.setAlignment(Element.ALIGN_CENTER);
        iInfo.setFont(font3);

        iInfo.add("GSTIN No.:27ABJPP1674P1ZW                                     Website: " + website);
        textt.add(iInfo);

        Paragraph tInfo = new Paragraph();
        tInfo.setAlignment(Element.ALIGN_CENTER);
        tInfo.setFont(font3);
        tInfo.add("\nPAN No.:ABJPP1674P                                                     Email:" + mailid);
        textt.add(tInfo);

        Image image = Image.getInstance("D:\\asset\\logo.png");
        image.setAlignment(Image.ALIGN_RIGHT);
        image.setAbsolutePosition(0f, 0f);
        //image.scalePercent(, 20);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{15, 75});
        finalBlock.addCell(image);
        finalBlock.addCell(textt);
        //    finalBlock.addCell(detail);

        Paragraph quot = new Paragraph();
        quot.setAlignment(Element.ALIGN_CENTER);
        quot.setFont(font4);
        quot.add("Price Bid \n\n");
        document.add(quot);

        document.add(finalBlock);
        // document.newPage();
    }

    private void addClientInfo(Document document) throws DocumentException {

        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);
        textt.setFont(font2);
        textt.add("Qtn.No:-" + priceBid.getQtnNo() + "                                                                            Date:" + priceBid.getDate() + "\n\n\nTo,\n" + priceBid.getClientName());

        PdfPCell cell1 = new PdfPCell(textt);
        cell1.setBorder(Rectangle.NO_BORDER);

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.setWidthPercentage((float) 100.0);
        leftBlock.addCell(cell1);

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.setWidthPercentage((float) 100.0);

        Paragraph p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.setFont(font2);
        p11.add("Subject: " + priceBid.getSubject());//add here subject
        PdfPCell cell11 = new PdfPCell(p11);
        cell11.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.setFont(font2);
        p11.add("Reference: Tender Enquiry No: " + priceBid.getTenderEnqNo() + "           Date: " + priceBid.getTenderEnqDate() + "\n" + refData);//add here enq no & date
        PdfPCell cell12 = new PdfPCell(p11);
        cell12.setBorder(Rectangle.NO_BORDER);

        rightBlock.addCell(cell11);
        rightBlock.addCell(cell12);

        PdfPTable finalBlock = new PdfPTable(1);
        finalBlock.setWidthPercentage((float) 100.0);

        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    private void addItemsData(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("Sr. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Nomenclature"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Quoted Qty."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Basic Rate per unit in Rs."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Freight Charges per unit in Rs."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        // table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("CGST %"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("SGST %"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("IGST %"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Total Amount in Rs."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{5f, 52f, 10f, 15f, 8f, 8f, 8f, 15f};
        table.setWidths(columnWidths);

        for (int i = 0; i < priceBidItemsList.size(); i++) {
            PriceBidItems temp = priceBidItemsList.get(i);
            int n = i + 1;
            table.addCell(setItemsFont("" + n));
            String nomenclature = temp.getNomenclature();
            String quant = temp.getQuantity();
            String basic = setDoubleZeroToText(temp.getBasic_rate());
            String cgst = setDoubleZeroToText(temp.getCgst());
            String sgst = setDoubleZeroToText(temp.getSgst());
            String igst = setDoubleZeroToText(temp.getIgst());
            String freight = setDoubleZeroToText(temp.getFreight());
            String total_amount = setDoubleZeroToText(temp.getTotal());

            table.addCell(setItemsFont(nomenclature));
            table.addCell(setItemsFont(quant));
            table.addCell(setItemsFont(basic));
            //    table.addCell(setItemsFont(freight));
            table.addCell(setItemsFont(cgst));
            table.addCell(setItemsFont(sgst));
            table.addCell(setItemsFont(igst));
            table.addCell(setItemsFont(total_amount));

        }
        document.add(table);

    }

    private void addEmptySpace(Document document, int lines) throws DocumentException {
        for (int i = 0; i < lines; i++) {
            Paragraph p = new Paragraph();
            p.add("\n");
            document.add(p);
        }

    }

    private void addTermsAndConditions(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_LEFT);
        textt.setFont(font6);
        textt.add("Terms & Conditions\n");

        PdfPCell cell11 = new PdfPCell(Para4TC(" "));
        cell11.setBorder(Rectangle.BOTTOM);
        PdfPCell cell12 = new PdfPCell(Para4TC("1. Payment Terms"));
        cell12.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell13 = new PdfPCell(Para4TC("2. Govt. Taxes"));
        cell13.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell14 = new PdfPCell(Para4TC("3. Delivery Period"));
        cell14.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell15 = new PdfPCell(Para4TC("4. Delivery Terms"));
        cell15.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell16 = new PdfPCell(Para4TC("5. P & F Charges"));
        cell16.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell17 = new PdfPCell(Para4TC("6. Transit Insurance"));
        cell17.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell18 = new PdfPCell(Para4TC("7. Earnest Money Deposit"));
        cell18.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell19 = new PdfPCell(Para4TC("8. Validity Of the Offer"));
        cell19.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell110 = new PdfPCell(Para4TC("9. Installation & Commissioning"));
        cell110.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell111 = new PdfPCell(Para4TC("10. Inspection"));
        cell111.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell112 = new PdfPCell(Para4TC("11. PSD"));
        cell112.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell113 = new PdfPCell(Para4TC("12. Free Issue Material"));
        cell113.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell114 = new PdfPCell(Para4TC("13. NOTE:"));
        cell114.setBorder(Rectangle.RECTANGLE);

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.setWidthPercentage((float) 100.0);
        //leftBlock.addCell(cell11);
        leftBlock.addCell(cell12);
        leftBlock.addCell(cell13);
        leftBlock.addCell(cell14);
        leftBlock.addCell(cell15);
        leftBlock.addCell(cell16);
        leftBlock.addCell(cell17);
        leftBlock.addCell(cell18);
        leftBlock.addCell(cell19);
        leftBlock.addCell(cell110);
        leftBlock.addCell(cell111);
        leftBlock.addCell(cell112);
        leftBlock.addCell(cell113);
        leftBlock.addCell(cell114);

        PdfPCell cell21 = new PdfPCell(Para4TC(" "));
        cell21.setBorder(Rectangle.BOTTOM);
        PdfPCell cell22 = new PdfPCell(Para4TC("" + priceBid.getPaymentTerms()));
        cell22.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell23 = new PdfPCell(Para4TC("" + priceBid.getGovtTaxes()));
        cell23.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell24 = new PdfPCell(Para4TC("" + priceBid.getDelPeriod()));
        cell24.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell25 = new PdfPCell(Para4TC("" + priceBid.getDelTerms()));
        cell25.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell26 = new PdfPCell(Para4TC("" + priceBid.getPFCharges()));
        cell26.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell27 = new PdfPCell(Para4TC("" + priceBid.getTransitInsu()));
        cell27.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell28 = new PdfPCell(Para4TC("" + priceBid.getEmd()));
        cell28.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell29 = new PdfPCell(Para4TC("" + priceBid.getValOffer()));
        cell29.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell210 = new PdfPCell(Para4TC("" + priceBid.getInstallComm()));
        cell210.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell211 = new PdfPCell(Para4TC("" + priceBid.getInspection()));
        cell211.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell212 = new PdfPCell(Para4TC("" + priceBid.getPsd()));
        cell212.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell213 = new PdfPCell(Para4TC("" + priceBid.getFim()));
        cell213.setBorder(Rectangle.RECTANGLE);
        PdfPCell cell214 = new PdfPCell(Para4TC("" + priceBid.getNote()));
        cell214.setBorder(Rectangle.RECTANGLE);

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.setWidthPercentage((float) 100.0);
        //  rightBlock.addCell(cell21);
        rightBlock.addCell(cell22);
        rightBlock.addCell(cell23);
        rightBlock.addCell(cell24);
        rightBlock.addCell(cell25);
        rightBlock.addCell(cell26);
        rightBlock.addCell(cell27);
        rightBlock.addCell(cell28);
        rightBlock.addCell(cell29);
        rightBlock.addCell(cell210);
        rightBlock.addCell(cell211);
        rightBlock.addCell(cell212);
        rightBlock.addCell(cell213);
        rightBlock.addCell(cell214);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{30, 70});

        //finalBlock.addCell(leftBlock);
        //finalBlock.addCell(rightBlock);
        finalBlock.addCell(cell11);
        finalBlock.addCell(cell21);
        finalBlock.addCell(cell12);
        finalBlock.addCell(cell22);
        finalBlock.addCell(cell13);
        finalBlock.addCell(cell23);
        finalBlock.addCell(cell14);
        finalBlock.addCell(cell24);
        finalBlock.addCell(cell15);
        finalBlock.addCell(cell25);
        finalBlock.addCell(cell16);
        finalBlock.addCell(cell26);
        finalBlock.addCell(cell17);
        finalBlock.addCell(cell27);
        finalBlock.addCell(cell18);
        finalBlock.addCell(cell28);
        finalBlock.addCell(cell19);
        finalBlock.addCell(cell29);
        finalBlock.addCell(cell110);
        finalBlock.addCell(cell210);
        finalBlock.addCell(cell111);
        finalBlock.addCell(cell211);
        finalBlock.addCell(cell112);
        finalBlock.addCell(cell212);
        finalBlock.addCell(cell113);
        finalBlock.addCell(cell213);
        finalBlock.addCell(cell114);
        finalBlock.addCell(cell214);

        document.add(textt);
        document.add(finalBlock);
    }

    private Paragraph Para4TC(String p) {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_LEFT);
        textt.setFont(font3);
        textt.add(p);
        System.out.println("p:" + p.length());
        return textt;
    }

    private void addSignature(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_LEFT);
        textt.setFont(font6);
        textt.add("\n\nFor Neelesh Engineers\n\n\n");
        document.add(textt);

        textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_LEFT);
        textt.setFont(font3);
        textt.add("\nAuthorised Signature");
        document.add(textt);

    }

    private Paragraph setParaHeader(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font3);
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
            // new PriceBidPDF("90");
            Desktop.getDesktop().open(new File("I:\\90.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(PriceBidPDF.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
