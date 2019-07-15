package com.mileset.neeleshengineers.View.Report;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mileset.neeleshengineers.View.Quatation.Quatation;
import com.mileset.neeleshengineers.modal.quatation.QuatationData;
import com.mileset.neeleshengineers.modal.quatation.QuatationItems;
import com.mileset.neeleshengineers.util.NumberToWord;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QuotationPDF {

    static String companyName = "NEELESH ENGINEERS\n";
    static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "Works: Sector No. 7,Plot. No. 86, PCNTDA, Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103,        Fax- 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 26,
            Font.BOLDITALIC);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 13,
            Font.BOLD);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private String billNo;
    private String amountWords;
    private String FILE = "D:\\NeeleeshEngineers\\quotation\\";
    String quantityType = " Nos.";
    private String website = "www.neeleshengineers.com";
    QuatationData quot;
    ArrayList<QuatationItems> quatationItems;

    public QuotationPDF(QuatationData quot) {
        this.quot = quot;
        quatationItems = quot.getQuatationItemses();
        System.out.println("debug 3" + quatationItems.get(0).getItemname());
        billNo = quot.getQuatationId().replaceAll("/", "_");
        billNo = billNo.replaceAll("\\\\", "_");
        File dir = new File(FILE);
        if (dir.exists()) {

        } else {
            dir.mkdir();
        }
        //this.amountWords = words;
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
            addItem(document);
            addTip(document);
            addNotes(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTitlePage(Document document)
            throws DocumentException {

        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);

        Paragraph compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font5);
        compName.add(companyName);
        textt.add(compName);

        /*   Paragraph tagLine = new Paragraph();
         tagLine.setAlignment(Element.ALIGN_CENTER);
         tagLine.setFont(font2);
         tagLine.add("Die & Mould Design, CNC Machine, Export Components, Import Substitutes, All Types Of Gauges");
         textt.add(tagLine);
         */
        /*  Paragraph address1 = new Paragraph();
         address1.setAlignment(Element.ALIGN_CENTER);
         address1.setFont(font3);
         //address1.add(add1+ "        Contact: " + phone);
         address1.add(add1);
         textt.add(address1);*/
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

        Paragraph email = new Paragraph();
        email.setAlignment(Element.ALIGN_CENTER);
        email.setFont(font3);
        email.add("Email:" + mailid + "\nWebsite: " + website);
        textt.add(email);

        Paragraph detail = new Paragraph();
        detail.setAlignment(Element.ALIGN_CENTER);
        detail.setFont(font3);
        detail.add("Manufacturers & Suppliers of \nAll type of Gauges, Import Substitute Items, Jig Bushes, CNC Machining components, Special tooling items, Jigs & Fixtures, SPM Designs & Manufacturing, Wind Mill Parts.\nAn ISO 9001-2015 Certified Company");

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{60, 40});
        finalBlock.addCell(textt);
        finalBlock.addCell(detail);

        Paragraph quot = new Paragraph();
        quot.setAlignment(Element.ALIGN_CENTER);
        quot.setFont(font6);
        quot.add("Quotation \n\n");
        document.add(quot);

        document.add(finalBlock);

        // document.newPage();
    }

    private void addClientInfo(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);
        textt.setFont(font4);
        textt.add("Kind Attn:- " + quot.getKind_attn() + "\n\nTo,\n" + quot.getTo() + "\n");

        PdfPCell cell1 = new PdfPCell(textt);
        cell1.setBorder(Rectangle.NO_BORDER);

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.setWidthPercentage((float) 100.0);
        leftBlock.addCell(cell1);

        PdfPTable rightBlock = new PdfPTable(2);
        rightBlock.setWidthPercentage((float) 100.0);
        rightBlock.setWidths(new int[]{50, 50});

        Paragraph p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Your Enq. No: " + quot.getEnquiry_number());//add here enq no
        PdfPCell cell11 = new PdfPCell(p11);
        //cell11.setBorder(Rectangle.BOTTOM);
        cell11.setBorder(Rectangle.BOX);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Enq. Date: " + quot.getEnquiry_date());//add here enq date
        PdfPCell cell12 = new PdfPCell(p11);
        //cell12.setBorder(Rectangle.BOTTOM);
        cell12.setBorder(Rectangle.BOX);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Our Qtn. No: " + quot.getQuatationId());//add here qtn no
        PdfPCell cell13 = new PdfPCell(p11);
        //cell13.setBorder(Rectangle.NO_BORDER);
        cell13.setBorder(Rectangle.RIGHT);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Qtn. Date: " + quot.getBillDate());//add here Qtn no
        PdfPCell cell14 = new PdfPCell(p11);
        //cell14.setBorder(Rectangle.NO_BORDER);
        cell14.setBorder(Rectangle.RIGHT);

        rightBlock.addCell(cell11);
        rightBlock.addCell(cell12);
        rightBlock.addCell(cell13);
        rightBlock.addCell(cell14);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{40, 60});
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    private void addItem(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(new Phrase("Sr. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Particulars\n\n" + quot.getQuatation_type()));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Basic Rate Each(Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(" Min Order Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        /*        c1 = new PdfPCell(new Phrase("" + quot.getQuatation_type()));
         c1.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(c1);
         */
        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase(" "));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        float[] columnWidths = new float[]{8f, 55f, 40f, 12f};
        table.setWidths(columnWidths);
        System.out.println("debug 1" + quatationItems);
        for (int y = 0; y < quatationItems.size(); y++) {
            QuatationItems qItems = quatationItems.get(y);
            int q = y + 1;
            PdfPCell cell = new PdfPCell(new Phrase(q + ""));     // Creating a cell 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("" + qItems.getItemname()));     // Creating a cell 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Rs." + qItems.getUnitrate() + "/-" + "\n" + NumberToWord.NumberToWord(qItems.getUnitrate()) + " only"));     // Creating a cell 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("" + qItems.getQuantity()));     // Creating a cell 
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            String space = "";
        }
        PdfPCell cell = new PdfPCell((new Phrase("")));     // Creating a cell 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell((new Phrase("")));     // Creating a cell 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell((new Phrase("")));     // Creating a cell 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        cell = new PdfPCell((new Phrase("")));     // Creating a cell 
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        String space = "";

        for (int p = 0; p < 24 - (4 * quatationItems.size()); p++) {
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

    public void addTip(Document document) throws DocumentException {
        Paragraph tip = new Paragraph();
        tip.setAlignment(Element.ALIGN_LEFT);
        tip.setFont(font3);
        tip.add("We hope you will find above quotation in order & favour us with your valued purchases order & oblige. \nAssuring you our best & prompt services at all the times.\n\n");
        document.add(tip);
    }

    public void addNotes(Document document) throws DocumentException {
        Paragraph p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("\n");
        PdfPCell cell01 = new PdfPCell(p1);
        cell01.setBorder(Rectangle.BOTTOM);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("\n");
        PdfPCell cell02 = new PdfPCell(p1);
        cell02.setBorder(Rectangle.BOTTOM);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("1. Taxes");
        PdfPCell cell11 = new PdfPCell(p1);
        cell11.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getTaxes());
        PdfPCell cell12 = new PdfPCell(p1);
        cell12.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("2. Delivery");
        PdfPCell cell21 = new PdfPCell(p1);
        cell21.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getDelivery());
        PdfPCell cell22 = new PdfPCell(p1);
        cell22.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("3. Payment");
        PdfPCell cell31 = new PdfPCell(p1);
        cell31.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getPayment());
        PdfPCell cell32 = new PdfPCell(p1);
        cell32.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("4. Validity");
        PdfPCell cell41 = new PdfPCell(p1);
        cell41.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getValidity());
        PdfPCell cell42 = new PdfPCell(p1);
        cell42.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("5. Mode Of   Dispatch");
        PdfPCell cell51 = new PdfPCell(p1);
        cell51.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getMode_of_dispatch());
        PdfPCell cell52 = new PdfPCell(p1);
        cell52.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add("6. Remark");
        PdfPCell cell61 = new PdfPCell(p1);
        cell61.setBorder(Rectangle.RECTANGLE);

        p1 = new Paragraph();
        p1.setAlignment(Element.ALIGN_LEFT);
        p1.add(quot.getRemark());
        PdfPCell cell62 = new PdfPCell(p1);
        cell62.setBorder(Rectangle.RECTANGLE);

        PdfPTable leftBlock = new PdfPTable(2);
        leftBlock.setWidthPercentage((float) 100.0);
        leftBlock.setWidths(new int[]{30, 70});
        leftBlock.addCell(cell01);
        leftBlock.addCell(cell02);
        leftBlock.addCell(cell11);
        leftBlock.addCell(cell12);
        leftBlock.addCell(cell21);
        leftBlock.addCell(cell22);
        leftBlock.addCell(cell31);
        leftBlock.addCell(cell32);
        leftBlock.addCell(cell41);
        leftBlock.addCell(cell42);
        leftBlock.addCell(cell51);
        leftBlock.addCell(cell52);
        leftBlock.addCell(cell61);
        leftBlock.addCell(cell62);

        Paragraph sign = new Paragraph();
        sign.setAlignment(Element.ALIGN_LEFT);
        sign.setFont(font4);
        sign.add("For Neelesh Engineers\n\n\n\n\n\n\n\n\nAuthorised Signature");
        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.setWidthPercentage((float) 100.0);
        rightBlock.addCell(sign);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{70, 30});
        finalBlock.getDefaultCell().setBorderWidth(0f);
        finalBlock.addCell(new Paragraph("\n"));
        finalBlock.addCell(new Paragraph(""));
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    private static void addMetaData(Document document) {
        document.addTitle("Quotation from Neelesh Engineers");
        document.addSubject("Quotation from Neelesh Engineers");
        document.addKeywords("Java, PDF, Quotation, Neelesh Engineers, Swapnil Gandhi");
        document.addAuthor("Swapnil Gandhi");
        document.addCreator("Software By Swapnil Gandhi");
    }
}
