/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import com.mileset.neeleshengineers.modal.Customer;
import com.mileset.neeleshengineers.modal.Po;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Rahul
 */
public class Utility {

    public static ArrayList<String> cname, cId, gstin;
    public static HashMap<String, String> CIDCNAMEMAP = new HashMap();
    public static HashMap<String, String> gstInMap = new HashMap();
    public static HashMap<String, String> gstInCustIdMap = new HashMap();
    public static HashMap<String, String> pocMap = new HashMap();

    public static boolean isNetIsAvailable() {
        try {
            final URL url = new URL("https://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            return false;
        }
    }

    public static void setToTable(ArrayList<Customer> data1, JTable jTable1) {
        //      data = firebaseMethods.getAllCustomers();
        cname = new ArrayList();
        cId = new ArrayList();
        gstin = new ArrayList();
        System.out.println("setToTable called " + data1);

        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1);

        for (int i = 0; i < data1.size(); i++) {
            System.out.println("row:" + data1.get(i));
            //  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            System.out.println(data1.get(i).getId());
            row.add(data1.get(i).getId());
            row.add(data1.get(i).getName());
            row.add(data1.get(i).getGstin());
            row.add(data1.get(i).getAddress());
            row.add(data1.get(i).getContact());
            row.add(data1.get(i).getEmail());
            row.add(data1.get(i).getVendor_code());
            row.add(data1.get(i).getPanNo());
            row.add(data1.get(i).getCgst());
            row.add(data1.get(i).getSgst());
            row.add(data1.get(i).getIgst());
            cname.add(data1.get(i).getName());
            cId.add(data1.get(i).getId());
            gstin.add(data1.get(i).getGstin());
            CIDCNAMEMAP.put(data1.get(i).getId(), data1.get(i).getName());
            gstInCustIdMap.put(data1.get(i).getId(), data1.get(i).getGstin());
            pocMap.put(data1.get(i).getName().replace("\n", " "), data1.get(i).getAddress());
            gstInMap.put(data1.get(i).getName().replace("\n", " "), data1.get(i).getGstin());
            //model.addRow(row);
            //dm.addRow(row);
            dm.addRow(row);
        }
        System.out.println("setting data to utilvars " + data1);
        UtilVars.setCustList(data1);
    }

    public static String getPlaceOfSupply(String cname) {
        return pocMap.get(cname);
    }

    public static String getGstIn(String cname) {
        System.out.println("*************");
        System.out.println("my input is " + cname);
        System.out.println("i am sending back" + gstInMap.get(cname));
        System.out.println("*************");
        return gstInMap.get(cname);

    }

    public static void setToPoWarningsTable(ArrayList<Po> data1, JTable jTable1) {
        //      data = firebaseMethods.getAllCustomers();
        System.out.println("setToPoWarningsTable called");
        DefaultTableModel dm = (DefaultTableModel) jTable1.getModel();
        while (dm.getRowCount() > 0) {
            dm.removeRow(0);
        }

        System.out.println("row:" + data1);

        for (int i = 0; i < data1.size(); i++) {
            System.out.println("row:" + data1.get(i));
            //  DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            Vector row = new Vector();
            row.add(data1.get(i).getDelivery_date());
            row.add(data1.get(i).getPoid());
            row.add(data1.get(i).getCustName());
            //model.addRow(row);
            //dm.addRow(row);
            dm.addRow(row);
        }
    }

    public static String convertDateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            return sdf.parse(date);
        } catch (ParseException ex) {
            System.out.println("Error in converting date from string to date " + ex.getMessage());
            return null;
        }
    }
}
