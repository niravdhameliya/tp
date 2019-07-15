/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.Report;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.util.NumberToWord;
import com.mileset.neeleshengineers.util.UtilVars;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class ConstructorBill {

    Invoice_details invoice_details;
    static String companyName = "NEELESH ENGINEERS\n";
    //static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "                      Sector No. 7,Plot. No. 86, PCNTDA, Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 7,
            Font.NORMAL);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 20,
            Font.BOLD);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.BOLD);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private String billNo;

    private String FILE = "D:\\NeeleeshEngineers\\contractorbill\\";

    private String website = "www.neeleshengineers.com";

    public ConstructorBill(Invoice_details invoice_details) {
        this.invoice_details = invoice_details;

        billNo = invoice_details.getInvoice_id().replaceAll("/", "_");
        //billNo = str;
        billNo = billNo.replaceAll("\\\\", "_");

        //this.amountWords = words;
        File dir = new File(FILE);
        if (dir.exists()) {

        } else {
            dir.mkdir();
        }
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

            addItemsData(document);
            addFinalBlock(document);
            document.newPage();
            addNewInstructions(document);
            document.close();
            System.out.println("done@@@@@@@@@@@@@@@@@@");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Constructor Bill");
        document.addSubject("Constructor Bill");
        document.addKeywords("Java, PDF, Constructor Bill, Neelesh Engineers, Swapnil Gandhi");
        document.addAuthor("Swapnil Gandhi");
        document.addCreator("Software By Swapnil Gandhi");
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);

        Paragraph compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font3);
        compName.add("Head Of Accounts\n");
        textt.add(compName);

        Paragraph tagLine = new Paragraph();
        tagLine.setAlignment(Element.ALIGN_CENTER);
        tagLine.setFont(font3);

        System.out.println("Contract Agreement NO.  " + invoice_details.getPONo());
        System.out.println("Dated:" + UtilVars.getPo().getBill_date());
        tagLine.add("Contract Agreement NO.  " + invoice_details.getPONo() + "                                              Dated:" + UtilVars.getPo().getBill_date() + "\n"); //add here Purchase order no
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
        address2.add("For the period from:                                                       To: \n");
        textt.add(address2);

        Paragraph ph = new Paragraph();
        ph.setAlignment(Element.ALIGN_CENTER);
        ph.setFont(font3);
        ph.add("Name & Address of the contractor: NEELESH ENGINEERS \n" + add2);
        textt.add(ph);

        /* Paragraph tInfo = new Paragraph();
         tInfo.setAlignment(Element.ALIGN_CENTER);
         tInfo.setFont(font3);
         tInfo.add("GSTIN No.:27ABJPP1674P1ZW                                                                PAN No.: ABJPP1674P");
         textt.add(tInfo);
         */
        PdfPTable finalBlock = new PdfPTable(1);
        finalBlock.setWidthPercentage((float) 100.0);
        // finalBlock.setWidths(new int[]{70});
        finalBlock.addCell(textt);
        //    finalBlock.addCell(detail);

        Paragraph quot = new Paragraph();
        quot.setAlignment(Element.ALIGN_CENTER);
        quot.setFont(font6);
        quot.add("CONTRACTOR'S BILL \n\n");
        document.add(quot);

        document.add(finalBlock);
    }

    public static void main(String[] args) {
//        new ConstructorBill("qwe");
    }

    private void addItemsData(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("Supporting Voucher No & date"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Name of unit"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Description of articles supplied or service rendered"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Rate (per) Rs"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Total Cost Rs"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Remarks"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{15f, 10f, 40f, 13f, 10f, 10f, 15f};
        table.setWidths(columnWidths);
        /*      table.addCell(setItemsFont("" + invoice_details.getInvoice_id()));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));  */
        ArrayList<Items> itemList = invoice_details.getItemList();

        for (int i = 0; i < itemList.size(); i++) {
            Items temp = itemList.get(i);
            int n = i + 1;
            //    table.addCell(setItemsFont("" + n));
            if (i == 0) {
                table.addCell(setItemsFont("Invoice No: " + invoice_details.getInvoice_id() + "\nDt:- " + invoice_details.getDate()));
            } else {
                table.addCell(setItemsFont(" "));
            }
            table.addCell(setItemsFont(" "));
            String items_name = temp.getItem_name();
            String itemCode = temp.getItemCode();

            String drawingNo = temp.getDrawingNumber();
            String quant = temp.getQuantity() + " " + temp.getUnitCode();
            String unit_rate = temp.getUnit_rate() + " /-";
            String total_amount = temp.getAmount() + " /-";
            String work_no = temp.getWork_order();
            //table.addCell(setItemsFont(itemCode));
            table.addCell(setItemsFont(n + ") " + itemCode + "  " + items_name + "   " + drawingNo));
            table.addCell(setItemsFont("    " + quant));
            table.addCell(setItemsFont(unit_rate));
            table.addCell(setItemsFont(total_amount));
            table.addCell(setItemsFont(" "));
        }

        for (int j = 4; j > itemList.size(); j--) {
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));
            table.addCell(setItemsFont("\n\n"));

        }

        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont("Total Freight"));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" " + invoice_details.getOthercharges()));
        table.addCell(setItemsFont(" "));

        /*  table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont("Basic Total"));
         table.addCell(setItemsFont(" "));
         double bt = invoice_details.getBasicTotal() + invoice_details.getOthercharges();
         table.addCell(setItemsFont(" " + bt));
         table.addCell(setItemsFont(" "));
         */
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont("CGST + SGST +IGST "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" " + calcTaxableAmount()));
        table.addCell(setItemsFont(" "));

        /*  table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont("SGST(%)"));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" " + invoice_details.getSgst()));
         table.addCell(setItemsFont(" "));

         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont("IGST(%)"));
         table.addCell(setItemsFont(" "));
         table.addCell(setItemsFont(" " + invoice_details.getIgst()));
         table.addCell(setItemsFont(" "));
         */
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont("Grand Total"));
        table.addCell(setItemsFont(" "));
        table.addCell(setItemsFont(" " + invoice_details.getGrandTotal()));
        table.addCell(setItemsFont(" "));
        document.add(table);

    }

    public double calcTaxableAmount() {
        double bt = invoice_details.getBasicTotal() + invoice_details.getOthercharges();
        double cgst = (invoice_details.getCgst() * bt) / 100;
        double sgst = (invoice_details.getSgst() * bt) / 100;
        double igst = (invoice_details.getIgst() * bt) / 100;

        double tax = cgst + sgst + igst;
        return tax;
    }

    private Paragraph setItemsFont(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font1);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        return p;
    }

    private Paragraph setParaHeader(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font4);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        return p;
    }

    private void addFinalBlock(Document document) throws DocumentException {
        PdfPTable finalBlock = new PdfPTable(3);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{50, 25, 25});

        PdfPTable leftBlock = new PdfPTable(1);
        leftBlock.getDefaultCell().setBorder(0);
        leftBlock.setWidthPercentage((float) 100.0);
        leftBlock.addCell(addParagraph("Certified that :- \n"));
        leftBlock.addCell(addParagraphHeader("           SALE TAX CERTIFICATE \n"));
        leftBlock.addCell(addParagraph("Certified that the goods on which sales tax has been charged have not been exempted under the centrl Sales/states/Union Territery Tax Act or Rules made there under and the amount charged on acccount of sales Tax on these goods is not more than that what is payble under the provision of the relevant Act on the rules made there under: \n"));
        leftBlock.addCell(addParagraph("Certified further that we of our Branch or Agent NEELESH ENGINEERS  (Address) " + add2.trim() + " are registered as dealers in the state of Maharashtra uder Central GST Registration No. 27ABJPP1674P1ZW for the purpose of Sale Tax \n\n\nSeal of the firm\n"));
        leftBlock.addCell(addParagraph("Dated: " + invoice_details.getDate() + "                         Signature of the contractor"));//add here date
        leftBlock.addCell(addParagraphHeader("        CERTIFICATE FROM CONTRACTOR\n"));
        leftBlock.addCell(addParagraph("I have personally examined & verified & do hereby certify that the goods in respect of which payment is being claimed have been dispatched by me/us under Receipt No " + invoice_details.getInvoice_id() + "    Dated " + invoice_details.getDate() + "   duly drawn in favour of the consignee which is genuine & mentioned in the bill & that hold my self personally responsible for the correctness of this statement.\n"));
        leftBlock.addCell(addParagraph("I further here certify that the above mentioned Consignment No/Receipt No " + invoice_details.getInvoice_id() + "    Dated " + invoice_details.getDate() + "   has been forwarded to the consignee mentioned in the contract under registered post acknowledgement due on .......... vide our letter no............dated.....\n\n\n\n\n\n"));
        leftBlock.addCell(addParagraph("Date: " + invoice_details.getDate() + "   (Signature & Seal of the contractor)"));

        PdfPTable middleBlock = new PdfPTable(1);
        middleBlock.getDefaultCell().setBorder(0);
        middleBlock.setWidthPercentage((float) 100.0);
        middleBlock.addCell(addParagraphHeader("       Certify\n"));
        middleBlock.addCell(addParagraph("(a) that the stores have been duly delivered & inspected & found comfortable patterns & specification & fit for Govt. service & taken on charge vide\n\n\n............\n"));
        middleBlock.addCell(addParagraph("(b) that the rates passed in this billed\n\n"));
        middleBlock.addCell(addParagraph("(1) agree with those passed i the ...... &  that they have been compared & agree with original documents recorded in this office.\n"));
        middleBlock.addCell(addParagraph("(2) are reasonable: \n\n"));
        middleBlock.addCell(addParagraph("(c) certified correct according to transport indent register item No............. & I.A.F.Z-2150 attached.\n\n"));
        middleBlock.addCell(addParagraph("(d) that no Govt. transport was available.\n\n\n"));
        middleBlock.addCell(addParagraph("Stattion                        Rank\n\n"));
        middleBlock.addCell(addParagraph("       For General Manager\n\n"));
        middleBlock.addCell(addParagraph("Date.................Designation\n"));
        middleBlock.addCell(addParagraph("Those certificate are not required in respect of A.S.C suppliers."));

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.getDefaultCell().setBorder(0);
        rightBlock.setWidthPercentage((float) 100.0);
        rightBlock.addCell(addParagraph("Received the amount\n"));
        Double d = new Double(invoice_details.getGrandTotal());
        rightBlock.addCell(addParagraph("of Rupees(in words) " + NumberToWord.NumberToWord(d.intValue() + "") + " Only" + "\n\n"));
        rightBlock.addCell(addParagraph("Payment to be made.\n\n"));
        rightBlock.addCell(addParagraph("to my Bankers account\n"));
        rightBlock.addCell(addParagraphHeader("The CANARA BANK\n Pimpri Branch, Pune-411018\nCC A/C No-0418261010227\nIFSC Code-CNRB0000418\n\n\n\n"));
        rightBlock.addCell(addParagraph("\n    Rs1 Stamp\n\n\nSignature of Contractor\n\n\n................\n\n\nStation Pune\n\n\nSignature of contractor\n\n\n Date:  " + invoice_details.getDate()));//date
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(middleBlock);
        finalBlock.addCell(rightBlock);

        document.add(finalBlock);

    }

    public PdfPCell addParagraph(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font3);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        PdfPCell cell16 = new PdfPCell(p);
        cell16.setBorder(Rectangle.NO_BORDER);
        return cell16;
    }

    public PdfPCell addParagraphHeader(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font4);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        PdfPCell cell16 = new PdfPCell(p);
        cell16.setBorder(Rectangle.NO_BORDER);
        return cell16;
    }

    private void addNewInstructions(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);

        Paragraph compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font5);
        compName.add("Contractor's Bill\n\n");
        textt.add(compName);

        compName = new Paragraph();
        compName.setAlignment(Element.ALIGN_CENTER);
        compName.setFont(font2);
        compName.add("INSTRUCTION\n\n");
        textt.add(compName);

        document.add(textt);

        PdfPTable block = new PdfPTable(1);
        block.getDefaultCell().setBorder(0);
        block.setWidthPercentage((float) 100.0);
        block.addCell(addParagraph("1. Each bill must refer to only one order contract or station as the case may be.\n"));
        block.addCell(addParagraph("2. Bill should be prepared in ink & the original copy receipted & stampted where the amount exceeds Rs20/- & should be supported by the original copy(ies) of inspection Notes, Supply order IAFS-1530 Transport Indent & From.\n"));
        block.addCell(addParagraph("3. Separate bill should be prepared for each month supply as also for supplies made under contract & out of contract & for other depots in the case of M.S. Depots.\n"));
        block.addCell(addParagraph("4. Bills for supplies under A.S.C. contracts sholud be submitted direct to the C.D.A. In the letter cases extra copies of bill should be prepared & submitted along with the original where required.\n"));
        block.addCell(addParagraph("5. The instructions contained in the pamphlet 'Instructions for the guidance of A.S.C. Contractors in the preparation of their bills for supply-1935' should in general be followed in the preparartion of bill.\n\n"));

        document.add(block);
        LineSeparator ls = new LineSeparator();
        document.add(new Chunk(ls));

        Paragraph p1 = new Paragraph("For use in the Defense Accounts Department\n\n");
        p1.setAlignment(Element.ALIGN_CENTER);
        p1.setFont(font2);

        document.add(p1);

        Paragraph p2 = new Paragraph("1. Register of payment to local purchase Contractors, etc ...........................................................\nDist ................................................................. Page No ................................................................");
        p2.setAlignment(Element.ALIGN_LEFT);
        p2.setFont(font3);
        document.add(p2);

        Paragraph p3 = new Paragraph("2. Bill Register No .............................................................................................................................");
        p3.setAlignment(Element.ALIGN_LEFT);
        p3.setFont(font3);
        document.add(p3);

        Paragraph p4 = new Paragraph("3. Number of Enclosure ....................................................................................................................");
        p4.setAlignment(Element.ALIGN_LEFT);
        p4.setFont(font3);
        document.add(p4);

        Paragraph p5 = new Paragraph("4. Rate & distance verified by ...........................................................................................................");
        p5.setAlignment(Element.ALIGN_LEFT);
        p5.setFont(font3);
        document.add(p5);

        Paragraph p6 = new Paragraph("5. Retrenchment outstanding demand ..............................................................................................\nPassed for (Rs................. p................) Rupees for .........................................................................\nPayment as under - Voucher no ................................................................................................\n\n\n");
        p6.setAlignment(Element.ALIGN_LEFT);
        p6.setFont(font3);
        document.add(p6);

        PdfPTable table = new PdfPTable(7);

        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("A.G.S code No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Treasury"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Name of payee"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Amount Cheque"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Date of Cheque"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Intial of supdt D.sec"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Intial of C.D sec"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{10f, 10f, 40f, 10f, 10f, 10f, 15f};
        table.setWidths(columnWidths);
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));
        table.addCell(setItemsFont("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"));

        document.add(table);

        Paragraph p7 = new Paragraph("\nAuditor                                                 Superitendent                                           A.O");
        p7.setAlignment(Element.ALIGN_LEFT);
        p7.setFont(font3);
        document.add(p7);

        Paragraph p8 = new Paragraph("\nDated                                                         Dated                                               A.G.D.A.");
        p8.setAlignment(Element.ALIGN_LEFT);
        p8.setFont(font3);
        document.add(p8);

        Paragraph p9 = new Paragraph("\n                                                                                                                            D.C.C.A.");
        p9.setAlignment(Element.ALIGN_LEFT);
        p9.setFont(font3);
        document.add(p9);

        Paragraph p10 = new Paragraph("\n                                                                                                                              Dated");
        p10.setAlignment(Element.ALIGN_LEFT);
        p10.setFont(font3);
        document.add(p10);
    }
}
