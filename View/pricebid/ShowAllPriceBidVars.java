/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.View.pricebid;

import com.mileset.neeleshengineers.modal.pricebid.PriceBid;
import java.util.ArrayList;

/**
 *
 * @author Rahul
 */
public class ShowAllPriceBidVars {

    private static ArrayList<PriceBid> data;

    public static ArrayList<PriceBid> getData() {
        return data;
    }

    public static void setData(ArrayList<PriceBid> data) {
        ShowAllPriceBidVars.data = data;
    }
}
