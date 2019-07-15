/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mileset.neeleshengineers.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Rahul
 */
public class FiscalDate {

    private static final int FIRST_FISCAL_MONTH = Calendar.APRIL;

    private Calendar calendarDate;

    public FiscalDate() {
    }

    public FiscalDate(Calendar calendarDate) {
        this.calendarDate = calendarDate;
    }

    public FiscalDate(Date date) {
        this.calendarDate = Calendar.getInstance();
        this.calendarDate.setTime(date);
    }

    public int getFiscalMonth() {
        int month = calendarDate.get(Calendar.MONTH);
        int result = ((month - FIRST_FISCAL_MONTH - 1) % 12) + 1;
        if (result < 0) {
            result += 12;
        }
        return result;
    }

    public int getFiscalYear() {
        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        return (month >= FIRST_FISCAL_MONTH) ? year : year - 1;
    }

    public int getCalendarMonth() {
        return calendarDate.get(Calendar.MONTH);
    }

    public int getCalendarYear() {
        return calendarDate.get(Calendar.YEAR);
    }

    private static Calendar setDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar;
    }

    public static String getFinancialYear(Calendar calendar) {
        FiscalDate fiscalDate = new FiscalDate(calendar);
//        System.out.println("month: " + fiscalDate.getFiscalMonth());
        int year = fiscalDate.getFiscalYear();
        String financialYear = year + "-" + (year + 1);
        return financialYear;
    }

    public static String getInitialYear(Calendar calendar) {
        FiscalDate fiscalDate = new FiscalDate(calendar);
        String year = fiscalDate.getFiscalYear() + "";
        System.out.println("" + year.substring(2));
        return year.substring(2);
    }

    public static void main(String[] args) {
        String financialYear = FiscalDate.getFinancialYear(Calendar.getInstance());
        System.out.println("" + financialYear);
        FiscalDate.getInitialYear(Calendar.getInstance());

    }

}
