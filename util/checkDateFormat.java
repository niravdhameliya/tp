/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author Amin
 */
public class checkDateFormat {

    public static boolean isValidFormat(String value) {
        Date date = null;
        String format = "dd/MM/yyyy";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);

            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            
            ex.printStackTrace();
            return false;
        }
        return date != null;
    }
}
