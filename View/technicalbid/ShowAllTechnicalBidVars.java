/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.technicalbid;

import com.mileset.neeleshengineers.modal.technicalbid.TechnicalBid;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class ShowAllTechnicalBidVars {

    private static ArrayList<TechnicalBid> data;

    public static ArrayList<TechnicalBid> getData() {
        return data;
    }

    public static void setData(ArrayList<TechnicalBid> data) {
        ShowAllTechnicalBidVars.data = data;
    }

}
