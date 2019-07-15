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
import com.mileset.neeleshengineers.modal.Invoice.Invoice_details;
import com.mileset.neeleshengineers.modal.Invoice.Items;
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

public class TaxInvoice {

    ArrayList<Items> itemList;
    static String companyName = "NEELESH ENGINEERS\n";
    static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "Works: Sector No. 7,Plot. No. 86, PCNTDA, Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);

    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static final Font font7 = new Font(Font.FontFamily.TIMES_ROMAN, 8,
            Font.NORMAL);

    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private String billNo;

    private String FILE = "D:\\NeeleeshEngineers\\taxinvoice\\";
    Invoice_details invoice_details;
    private String website = "www.neeleshengineers.com";

    public TaxInvoice(Invoice_details invoice_details) {
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
            System.out.println("done@@@@@@@@@@@@@@@@@@");
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
        //finalBlock.getDefaultCell().setBorder(0);
        finalBlock.addCell(image);
        finalBlock.addCell(textt);

        //    finalBlock.addCell(detail);
        Paragraph quot = new Paragraph();
        quot.setAlignment(Element.ALIGN_CENTER);
        quot.setFont(font1);
        quot.add("Tax Invoice \n\n");

        Paragraph copies = new Paragraph();
        copies.setAlignment(Element.ALIGN_RIGHT);
        copies.setFont(font7);
        copies.add("Party/ Akw./Sales/Book");
        document.add(copies);
        document.add(quot);

        document.add(finalBlock);
        // document.newPage();
    }
    /*  private void addTitlePage(Document document)
     throws DocumentException {

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
     tagLine.add("Die & Mould Design, CNC Machine, Export Components, Import Substitutes, All Types Of Gauges\n");
     textt.add(tagLine);

        
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

     iInfo.add("Email:" + mailid + "                                                            Website: " + website);
     textt.add(iInfo);

     Paragraph tInfo = new Paragraph();
     tInfo.setAlignment(Element.ALIGN_CENTER);
     tInfo.setFont(font3);
     tInfo.add("\nGSTIN No.:27ABJPP1674P1ZW                                                                PAN No.: ABJPP1674P");
     textt.add(tInfo);

     PdfPTable finalBlock = new PdfPTable(1);
     finalBlock.setWidthPercentage((float) 100.0);
     // finalBlock.setWidths(new int[]{70});
     finalBlock.addCell(textt);
     //    finalBlock.addCell(detail);

     Paragraph quot = new Paragraph();
     quot.setAlignment(Element.ALIGN_CENTER);
     quot.setFont(font4);
     quot.add("Tax Invoice \n\n");
     document.add(quot);

     document.add(finalBlock);
     // document.newPage();
     }
     */

    private void addClientInfo(Document document) throws DocumentException {
        Paragraph textt = new Paragraph();
        textt.setAlignment(Element.ALIGN_CENTER);
        textt.setFont(font4);
        textt.add("M/S,\n" + invoice_details.getCustomerContact());

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
        rightBlock.setWidths(new int[]{65, 35});

        Paragraph p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Invoice No.: " + invoice_details.getInvoice_id()); // add here invoice no
        PdfPCell cell11 = new PdfPCell(p11);
        cell11.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Dt.: " + invoice_details.getDate());//add here invoice date
        PdfPCell cell12 = new PdfPCell(p11);
        cell12.setBorder(Rectangle.RECTANGLE);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Order No:" + invoice_details.getPONo()); // add here Order number
        PdfPCell cell13 = new PdfPCell(p11);
        cell13.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add(" ");//leave it blank
        PdfPCell cell14 = new PdfPCell(p11);
        cell14.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Order Date: " + invoice_details.getDte2());//add here Order Date
        PdfPCell cell15 = new PdfPCell(p11);
        cell15.setBorder(Rectangle.BOTTOM);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Vendor Code: " + invoice_details.getVendorCode());//add here Vendor Code
        PdfPCell cell16 = new PdfPCell(p11);
        cell16.setBorder(Rectangle.NO_BORDER);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add("Vehicle No:");//add here vehicle no
        PdfPCell cell17 = new PdfPCell(p11);
        cell17.setBorder(Rectangle.NO_BORDER);

        p11 = new Paragraph();
        p11.setAlignment(Element.ALIGN_LEFT);
        p11.add(" "); //leave it blank
        PdfPCell cell18 = new PdfPCell(p11);
        cell18.setBorder(Rectangle.NO_BORDER);

        rightBlock.addCell(cell11);
        rightBlock.addCell(cell12);
        rightBlock.addCell(cell13);
        rightBlock.addCell(cell14);
        rightBlock.addCell(cell15);
        rightBlock.addCell(cell14);
        rightBlock.addCell(cell16);

        // rightBlock.addCell(cell17);
        rightBlock.addCell(cell18);

        PdfPTable finalBlock = new PdfPTable(2);
        finalBlock.setWidthPercentage((float) 100.0);
        finalBlock.setWidths(new int[]{45, 55});
        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);
    }

    public double calGst(double gstPer, double amount) {
        double gst = (gstPer * amount) / 100;

        return gst;
    }

    private void addItemsData(Document document) throws DocumentException {
        String uniqueHSN = "";

        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage((float) 100.0);
        PdfPCell c1 = new PdfPCell(setParaHeader("Sr. No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        /*c1 = new PdfPCell(setParaHeader("Item Code"));
         c1.setHorizontalAlignment(Element.ALIGN_CENTER);
         table.addCell(c1);*/
        c1 = new PdfPCell(setParaHeader("Item Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Drawing No."));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Quantity"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        c1 = new PdfPCell(setParaHeader("Unit Rate (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(setParaHeader("Amount (Rs.)"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        float[] columnWidths = new float[]{5f, 43f, 15f, 12f, 15f, 15f};
        table.setWidths(columnWidths);
        table.addCell(setItemsFont(""));
        //  table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        table.addCell(setItemsFont(""));
        for (int i = 0; i < itemList.size(); i++) {
            Items temp = itemList.get(i);
            int n = i + 1;
            table.addCell(setItemsFont("" + n));
            String items_name = temp.getItem_name();
            String itemCode = temp.getItemCode();
            String hsn = temp.getHsn();
            String drawingNo = temp.getDrawingNumber();
            String quant = temp.getQuantity() + "";
            String unit_rate = temp.getUnit_rate() + "";
            String total_amount = temp.getAmount() + "";
            String work_no = temp.getWork_order();
            //table.addCell(setItemsFont(itemCode));
            table.addCell(setItemsFont("Item Code:- " + itemCode + "\n" + items_name + "   " + work_no));
            table.addCell(setItemsFont(drawingNo));
            table.addCell(setItemsFont("     " + quant + " " + temp.getUnitCode()));
            table.addCell(setItemsFont("      " + unit_rate));
            table.addCell(setItemsFont("      " + total_amount));
        }
        /* PdfPCell cell1 = new PdfPCell(new Phrase(" "));
         cell1.setBorder(Rectangle.NO_BORDER);

         for (int k = 0; k < 15 - itemList.size(); k++) {
         table.addCell(cell1);
         table.addCell(cell1);
         table.addCell(cell1);
         table.addCell(cell1);
         table.addCell(cell1);
         table.addCell(cell1);

         }*/

        uniqueHSN = "\n\nHSN:" + itemList.get(0).getHsn();

        String space1 = uniqueHSN + "\n\nNote: " + invoice_details.getNote();
        String space = "\n\n";
        //String space = "";

        for (int p = 0; p < 16 - (6 * itemList.size()); p++) {
            space = space + "\n";
            space1 = space1 + "\n";

        }

        table.addCell(setItemsFont(space));
        table.addCell(setItemsFont(space1));
        table.addCell(setItemsFont(space));
        table.addCell(setItemsFont(space));
        table.addCell(setItemsFont(space));
        table.addCell(setItemsFont(space));

        document.add(table);
    }

    private Paragraph setParaHeader(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font4);
        p.setAlignment(Element.ALIGN_CENTER);
        p.add(str);
        return p;
    }

    private Paragraph setParaHeader2(String str) {
        Paragraph p = new Paragraph();
        p.setFont(font2);
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
        block3.addCell(addTerms("We hereby certify that my/our registration certificate under the GST ACT 2017 is in force on the date of which the sale of goods specified in this Tax invoice is made by me/us and the transaction of sale covered for this tax invoice has been effected by me/us and it shall be accounted in the turnover of sales while filing of return and the due tax if any payable on the sale has been paid or shall be paid.\n\nAll disputes subject to Pune Jurisdiction"));

        Double d = new Double(invoice_details.getGrandTotal());
        leftBlock.addCell(addAmountInwords("Amount (In Words):Rupees " + NumberToWord.NumberToWord(d.intValue() + "") + " Only"));//add amount in words
        leftBlock.addCell(block3);
        //leftBlock.addCell(block3);

        PdfPTable rightBlock = new PdfPTable(1);
        rightBlock.getDefaultCell().setBorder(0);
        rightBlock.setWidthPercentage((float) 100.0);
        PdfPTable block2 = new PdfPTable(2);
        block2.setWidthPercentage((float) 100.0);
        block2.setWidths(new int[]{40, 60});
        block2.addCell(setParaHeader("Basic Total"));
        block2.addCell(setParaHeader2("              Rs. " + invoice_details.getBasicTotal() + ""));//add basic total
        block2.addCell(setParaHeader("F. Charges"));
        block2.addCell(setParaHeader2("              Rs. " + invoice_details.getOthercharges() + ""));//add freight Charges
        block2.addCell(setParaHeader("Taxable Amount"));
        double taxAmnt = invoice_details.getBasicTotal() + invoice_details.getOthercharges();
        block2.addCell(setParaHeader2("              Rs. " + taxAmnt + ""));//add taxable amount

        block2.addCell(setParaHeader("SGST " + invoice_details.getSgst() + " %"));
        block2.addCell(setParaHeader2("              Rs. " + calGst(invoice_details.getSgst(), taxAmnt)));//add sgst
        block2.addCell(setParaHeader("CGSI " + invoice_details.getCgst() + " %"));
        block2.addCell(setParaHeader2("              Rs. " + calGst(invoice_details.getCgst(), taxAmnt)));//add cgst
        block2.addCell(setParaHeader("IGST " + invoice_details.getIgst() + " %"));
        block2.addCell(setParaHeader2("              Rs. " + calGst(invoice_details.getIgst(), taxAmnt)));//add Igst

        block2.addCell(setParaHeader("Round OFF"));
        block2.addCell(setParaHeader2("              Rs. " + invoice_details.getRoundOFF()));//add round off
        //      block2.addCell(setParaHeader("Total"));
        //    block2.addCell(setParaHeader(""+invoice_details.getGrandTotal()));//add total
        block2.addCell(setParaHeader("Grand Total"));
        block2.addCell(setParaHeader2("              Rs. " + invoice_details.getGrandTotal()));//add grand Total
        rightBlock.addCell(block2);
        rightBlock.addCell(setParaHeader("\nFor NEELESH ENGINEERS\n\n\n\n"));
        rightBlock.addCell("Authorised Signatory");

        finalBlock.addCell(leftBlock);
        finalBlock.addCell(rightBlock);
        document.add(finalBlock);

    }

    public static void main(String[] args) {
        try {
            //   new TaxInvoice("1");
            Desktop.getDesktop().open(new File("D:\\1.pdf"));
        } catch (IOException ex) {
            Logger.getLogger(TaxInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
