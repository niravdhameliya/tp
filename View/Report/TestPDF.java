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
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import static com.mileset.neeleshengineers.View.Report.TaxInvoice.companyName;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

/**
 *
 * @author rahul
 */
public class TestPDF {

    static String companyName = "NEELESH ENGINEERS\n";
    static String add1 = "Office: 4, Nature View, Co-OP. Society, Senapati bapat Road, Pune-16";
    static String add2 = "Works: Sector No. 7,Plot. No. 86, PCNDTA , Bhosari, Pune - 411026\n";
    static String mailid = "neeleshengineers@gmail.com";
    static String phone = "020-27126103, 27126104\n";
    //private final String FILE = "I:\\1.pdf";
    private static final Font font1 = new Font(Font.FontFamily.TIMES_ROMAN, 26,
            Font.BOLDITALIC);
    private static final Font font5 = new Font(Font.FontFamily.TIMES_ROMAN, 22,
            Font.BOLDITALIC);
    private static final Font font2 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL);
    private static final Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.NORMAL);
    private static final Font font6 = new Font(Font.FontFamily.TIMES_ROMAN, 10,
            Font.BOLD);
    private static final Font font4 = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private String billNo = "1";
    private String website = "www.neeleshengineers.com";
    private String FILE = "D:\\taxinvoice\\";

    public TestPDF() {
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
        quot.add("Tax Invoice \n\n");
        document.add(quot);

        document.add(finalBlock);
        // document.newPage();
    }

    public static void main(String[] args) {
        new TestPDF();
    }
}
