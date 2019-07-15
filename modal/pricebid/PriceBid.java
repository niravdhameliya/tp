/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.modal.pricebid;

import java.util.ArrayList;

/**
 *
 * @author rahul
 */
public class PriceBid {

    private String qtnNo;
    private String date;
    private String clientName;
    private String subject;
    private String tenderEnqNo;
    private String tenderEnqDate;
    private String paymentTerms;
    private String govtTaxes;
    private String delPeriod;
    private String delTerms;
    private String PFCharges;
    private String transitInsu;
    private String emd;
    private String valOffer;
    private String installComm;
    private String inspection;
    private String psd;
    private String fim;
    private String note;
    private String fid;
    private String custName;
    private ArrayList<PriceBidItems> priceBidItems;

    public PriceBid() {
    }

    public PriceBid(String qtnNo, String date, String clientName, String subject, String tenderEnqNo, String tenderEnqDate, String paymentTerms, String govtTaxes, String delPeriod, String delTerms, String PFCharges, String transitInsu, String emd, String valOffer, String installComm, String inspection, String psd, String fim, String note, ArrayList<PriceBidItems> priceBidItems) {
        this.qtnNo = qtnNo;
        this.date = date;
        this.clientName = clientName;
        this.subject = subject;
        this.tenderEnqNo = tenderEnqNo;
        this.tenderEnqDate = tenderEnqDate;
        this.paymentTerms = paymentTerms;
        this.govtTaxes = govtTaxes;
        this.delPeriod = delPeriod;
        this.delTerms = delTerms;
        this.PFCharges = PFCharges;
        this.transitInsu = transitInsu;
        this.emd = emd;
        this.valOffer = valOffer;
        this.installComm = installComm;
        this.inspection = inspection;
        this.psd = psd;
        this.fim = fim;
        this.note = note;
        this.priceBidItems = priceBidItems;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getQtnNo() {
        return qtnNo;
    }

    public void setQtnNo(String qtnNo) {
        this.qtnNo = qtnNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTenderEnqNo() {
        return tenderEnqNo;
    }

    public void setTenderEnqNo(String tenderEnqNo) {
        this.tenderEnqNo = tenderEnqNo;
    }

    public String getTenderEnqDate() {
        return tenderEnqDate;
    }

    public void setTenderEnqDate(String tenderEnqDate) {
        this.tenderEnqDate = tenderEnqDate;
    }

    public String getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(String paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public String getGovtTaxes() {
        return govtTaxes;
    }

    public void setGovtTaxes(String govtTaxes) {
        this.govtTaxes = govtTaxes;
    }

    public String getDelPeriod() {
        return delPeriod;
    }

    public void setDelPeriod(String delPeriod) {
        this.delPeriod = delPeriod;
    }

    public String getDelTerms() {
        return delTerms;
    }

    public void setDelTerms(String delTerms) {
        this.delTerms = delTerms;
    }

    public String getPFCharges() {
        return PFCharges;
    }

    public void setPFCharges(String PFCharges) {
        this.PFCharges = PFCharges;
    }

    public String getTransitInsu() {
        return transitInsu;
    }

    public void setTransitInsu(String transitInsu) {
        this.transitInsu = transitInsu;
    }

    public String getEmd() {
        return emd;
    }

    public void setEmd(String emd) {
        this.emd = emd;
    }

    public String getValOffer() {
        return valOffer;
    }

    public void setValOffer(String valOffer) {
        this.valOffer = valOffer;
    }

    public String getInstallComm() {
        return installComm;
    }

    public void setInstallComm(String installComm) {
        this.installComm = installComm;
    }

    public String getInspection() {
        return inspection;
    }

    public void setInspection(String inspection) {
        this.inspection = inspection;
    }

    public String getPsd() {
        return psd;
    }

    public void setPsd(String psd) {
        this.psd = psd;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public ArrayList<PriceBidItems> getPriceBidItems() {
        return priceBidItems;
    }

    public void setPriceBidItems(ArrayList<PriceBidItems> priceBidItems) {
        this.priceBidItems = priceBidItems;
    }

    @Override
    public String toString() {
        return "PriceBid{" + "qtnNo=" + qtnNo + ", date=" + date + ", clientName=" + clientName + ", subject=" + subject + ", tenderEnqNo=" + tenderEnqNo + ", tenderEnqDate=" + tenderEnqDate + ", paymentTerms=" + paymentTerms + ", govtTaxes=" + govtTaxes + ", delPeriod=" + delPeriod + ", delTerms=" + delTerms + ", PFCharges=" + PFCharges + ", transitInsu=" + transitInsu + ", emd=" + emd + ", valOffer=" + valOffer + ", installComm=" + installComm + ", inspection=" + inspection + ", psd=" + psd + ", fim=" + fim + ", note=" + note + ", fid=" + fid + ", priceBidItems=" + priceBidItems + '}';
    }
}
