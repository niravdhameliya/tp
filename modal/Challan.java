/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal;

import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class Challan {

    private String poid;
    private String challanNo;
    private String date;
    private ArrayList<ChallanItems> challanItemsList;

    public Challan() {
    }

    public Challan(String poid, String challanNo, String date, ArrayList<ChallanItems> challanItemsList) {
        this.poid = poid;
        this.challanNo = challanNo;
        this.date = date;
        this.challanItemsList = challanItemsList;
    }

    public String getPoid() {
        return poid;
    }

    public void setPoid(String poid) {
        this.poid = poid;
    }

    public String getChallanNo() {
        return challanNo;
    }

    public void setChallanNo(String challanNo) {
        this.challanNo = challanNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<ChallanItems> getChallanItemsList() {
        return challanItemsList;
    }

    public void setChallanItemsList(ArrayList<ChallanItems> challanItemsList) {
        this.challanItemsList = challanItemsList;
    }

}
