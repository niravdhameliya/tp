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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.mileset.neeleshengineers.View.Report.TaxInvoice.companyName;
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceDetails;
import com.mileset.neeleshengineers.modal.laborinvoice.LaborInvoiceItems;
import com.mileset.neeleshengineers.util.NumberToWord;
import com.mileset.neeleshengineers.util.UtilVars;
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
public class LabourInvoice {

    ArrayList<LaborInvoiceItems> itemList;
    static String companyName = "NEELESH ENGINEERS\n";
    //static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "Works: Sector No. 7,Plot. No. 86, PCNTDA , Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 26,
            Font.BOLDITALIC);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private String billNo;

    private String FILE = "D:\\NeeleeshEngineers\\labourinvoice\\";
    LaborInvoiceDetails invoice_details;
    private String website = "www.neeleshengineers.com";

    public LabourInvoice(LaborInvoiceDetails invoice_details) {
        this.invoice_details = invoice_details;
        billNo = invoice_details.getInvoice_id().replaceAll("/", "_");
        billNo = billNo.replaceAll("\\\\", "_");
        itemList = invoice_details.getItemList();
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
            addClientInfo(document);
            addItemsData(document);
            // addTip(document);
            addFinalBlock(document);
            document.close();
            //      System.out.println("done@@@@@@@@@@@@@@@@@@");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Tax Invoice");
        document.addSubject("Tax Invoice");
        document.addKeywords("Java, PDF,Tax Invoice, Neelesh Engineers, Swapnil Gandhi");
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
        tagLine.setFont(font6);
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

        iInfo.add("Email:" + mailid + "                                     Website: " + website);
        textt.add(iInfo);

        Paragraph tInfo = new Paragraph();
        tInfo.setAlignment(Element.ALIGN_CENTER);
        tInfo.setFont(font3);
        tInfo.add("\nGSTIN No.:27ABJPP1674P1ZW                                                                PAN No.: ABJPP1674P");
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
        quot.add("Labour Invoice \n\n");
        document.add(quot);

        document.add(finalBlock);
        // document.newPage();
    }

    private void addClientInfo(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);
        textt.setFont(font4);
//        textt.add("M/S,\n" + UtilVars.getCustomer().getName() + "\n" + UtilVars.getCustomer().getAddress());
        textt.add("M/S,\n" + invoice_details.getCname());

        Paragraph textt1 = new Paragraph();
        textt1.setAlignment(Element.ALIGN_CENTER);
        textt1.setFont(font3);
        textt1.add("\n\nContact No." + UtilVars.getCustomer().getContact() + "\nGSTIN No." + UtilVars.getCustomer().getGstin() + "\nPAN No. " + UtilVars.getCustomer().getPanNo());

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

        Paragraph p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Bill No.: " + invoice_details.getInvoice_id()); // add here invoice no
        PdfPCell cell11 = new PdfPCell(p11);
        cell11.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Bill Date.: " + invoice_details.getDate());//add here invoice date
        PdfPCell cell12 = new PdfPCell(p11);
        cell12.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Order No." + invoice_details.getPONo()); // add here Order number
        PdfPCell cell13 = new PdfPCell(p11);
        cell13.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Order Date:" + invoice_details.getDte2());//add here Order Date
        PdfPCell cell14 = new PdfPCell(p11);
        cell14.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Your Challan No:" + invoice_details.getChallanNo());//add here challan No
        PdfPCell cell15 = new PdfPCell(p11);
        cell15.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Challan Date: " + invoice_details.getChallanDate());//add here Vendor Code
        PdfPCell cell16 = new PdfPCell(p11);
        cell16.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);

        PdfPCell cell17 = new PdfPCell(p11);
        cell17.setBorder(Rectangle.BOTTOM);
        p11.add("Vendor Code: " + invoice_details.getVendorCode());//add here Vendor Code
        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Vehicle No:" + invoice_details.getVehicleNo());//add here vehicle no
        PdfPCell cell18 = new PdfPCell(p11);
        cell18.setBorder(Rectangle.BOTTOM);

        rightBlock.addCell(cell11);
        rightBlock.addCell(cell12);
        rightBlock.addCell(cell13);
        rightBlock.addCell(cell14);
        rightBlock.addCell(cell15);
        rightBlock.addCell(cell16);
        rightBlock.addCell(cell17);
        rightBlock.addCell(cell18);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{40, 60});
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    public double calGst(double gstPer, double amount) {
        double gst = (gstPer * amount) / 100;

        return gst;
    }

    private void addItemsData(Document document) throws DocumentException {
        PdfPTable table = new PdfPTable(5);

        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("Sr. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Item Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Dispatched Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Unit Rate (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Amount (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{5f, 55f, 15f, 15f, 15f};
        table.setWidths(columnWidths);
        //table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        for (int i = 0; i < itemList.size(); i++) {
            LaborInvoiceItems temp = itemList.get(i);
            int n = i + 1;
            table.addCell(setItemsFont("" + n));
            String items_name = temp.getItem_name();
            //String hsn = temp.getHSN();
            String item_code = temp.getItemCode();

            String quant = temp.getQuantity() + "";
            String unit_rate = temp.getUnit_rate() + "";
            String total_amount = temp.getAmount() + "";
            table.addCell(setItemsFont("Item Code:" + item_code + "\n" + items_name + " Hsn:"));
            table.addCell(setItemsFont(quant));
            table.addCell(setItemsFont(unit_rate));
            table.addCell(setItemsFont(total_amount));
        }
        PdfPCell cell1 = new PdfPCell(new Phrase(" "));
        cell1.setBorder(Rectangle.BOX);

        for (int k = 0; k < 15 - itemList.size(); k++) {
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);
            table.addCell(cell1);

        }
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
        tip.setFont(font3);
        tip.add("Note: \n\n");  //add note 
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

        PdfPTable block1 = new PdfPTable(1);
        block1.getDefaultCell().setBorder(0);
        block1.setWidthPercentage((float) 100.0);

        block1.addCell(setParaHeader("GSTIN No: 27ABJPP1674P1ZW \n\n"));
        block1.addCell(setParaHeader(" "));

        block1.addCell(setParaHeader("PAN No: ABJPP1674P\n\n"));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));
        block1.addCell(setParaHeader(" "));

        PdfPTable block3 = new PdfPTable(1);
        block3.setWidthPercentage((float) 100.0);
        block3.addCell(addTerms("We hereby certy that our registration certiicate under the GST ACT 2017 is in on the date of which the supply of goods specified in this Tax invoice is made by me/us and the transaction of sale convered by this tax invoice has been affected by me/us and it shall be accounted in the turnover of sales while filing return and the due tax if any payable on the supplies has been paid or shall be paid.\n\nAll disputes subject to Pune Jurisdiction"));

        Double d = new Double(invoice_details.getGrandTotal());
        leftBlock.addCell(addAmountInwords("Amount (In Words): " + NumberToWord.NumberToWord(d.intValue() + "") + " Only"));//add amount in words
        //leftBlock.addCell(block1);
        leftBlock.addCell(block3);

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.getDefaultCell().setBorder(0);
        rightBlock.setWidthPercentage((float) 100.0);
        PdfPTable block2 = new PdfPTable(2);
        block2.setWidthPercentage((float) 100.0);
        block2.setWidths(new int[]{40, 60});
        block2.addCell(setParaHeader("Basic Total"));
        block2.addCell(setParaHeader(invoice_details.getBasicTotal() + ""));//add basic total
        block2.addCell(setParaHeader("F. Charges"));
        block2.addCell(setParaHeader(invoice_details.getOthercharges() + ""));//add freight Charges
        block2.addCell(setParaHeader("Taxable Amount"));
        double taxAmnt = invoice_details.getBasicTotal() + invoice_details.getOthercharges();
        block2.addCell(setParaHeader(taxAmnt + ""));//add taxable amount
        block2.addCell(setParaHeader("SGST " + invoice_details.getSgst() + " %"));
        block2.addCell(setParaHeader("" + calGst(invoice_details.getSgst(), taxAmnt)));//add sgst
        block2.addCell(setParaHeader("CGSI " + invoice_details.getCgst() + " %"));
        block2.addCell(setParaHeader("" + calGst(invoice_details.getCgst(), taxAmnt)));//add cgst
        block2.addCell(setParaHeader("IGST " + invoice_details.getIgst() + " %"));
        block2.addCell(setParaHeader("" + calGst(invoice_details.getIgst(), taxAmnt)));//add Igst
        //      block2.addCell(setParaHeader("Total"));
        //    block2.addCell(setParaHeader(""+invoice_details.getGrandTotal()));//add total
        block2.addCell(setParaHeader("Grand Total"));
        block2.addCell(setParaHeader("" + invoice_details.getGrandTotal()));//add grand Total
        rightBlock.addCell(block2);
        rightBlock.addCell(setParaHeader("\n\n\n\nFor NEELESH ENGINEERS\n\n\n\n\n"));
        rightBlock.addCell("Authorised Signatory");

        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);

    }

    public static void main(String[] args) {
        try {
            //   new LabourInvoice("1");
            Desktop.getDesktop().open(new File("D:\\1.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(LabourInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
