package com.flipkart.retail.analytics.persistence.utility;

public class YearDateHelper {


    public static String getYearFromYearMonth(String yearMonth){
        return yearMonth.substring(0,3);
    }
}
