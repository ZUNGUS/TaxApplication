/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zunguene.taxproject;

import java.text.DecimalFormat;

/**
 *
 * @author Luckyz
 */
public class TaxCalculator2018
{

    final static double TAX_THRESHOLD_AGE_BELOW_65 = 75750;
    final static double TAX_THRESHOLD_AGE_65_TO_75 = 117300;
    final static double TAX_THRESHOLD_AGE__65 = 131150;

    final static int AGE_GROUP_ONE_BELOW_65 = 1;
    final static int AGE_GROUP_TWO_65_AND_75 = 2;
    final static int AGE_GROUP_THREE_ABOVE_75 = 3;

    final static double TAX_RREBATE_PRIMARY = 13635;
    final static double TAX_RREBATE_SECONDARY_AGE_65_74 = 7479;
    final static double TAX_RREBATE_TERTIARY_AGE_ABOVE_75 = 2493;

    final static String MONTHLY = "M";
    final static String ANNUAL = "A";

    //checking thresholds
//    public static String checkThreshold(int age, double earning)
//    {
//
//        if (earning <= 75750)
//
//        {
//        }
//
//    }

    ///return tax rebate based on the age group
    public static double calculateRebateTax(int age)
    {
        double taxRebate = 0;

        switch (age)
        {
            case 1:
                taxRebate = TAX_RREBATE_PRIMARY;
                break;
            case 2:
                taxRebate = TAX_RREBATE_SECONDARY_AGE_65_74 + TAX_RREBATE_PRIMARY;
                break;
            case 3:
                taxRebate = TAX_RREBATE_TERTIARY_AGE_ABOVE_75 + TAX_RREBATE_PRIMARY;
                break;
            default:
                break;
        }
        return taxRebate;
    }

    ///return total earning based on salryType
    public static double calculateTotalEarnings(String salaryType, double earnings)
    {
        double totalEarning = 0;

        if (salaryType.equalsIgnoreCase(MONTHLY))

        {
            totalEarning = earnings * 12;
        } else if (salaryType.equalsIgnoreCase(ANNUAL))
        {

            totalEarning = earnings;
        }

        return totalEarning;
    }

    //calcuating total of tax amount payable     
    public static double taxPayable(double rebate, double totalEarning)
    {

        double taxable = 0.0;

        if (totalEarning <= 189880)
        {
            taxable = (totalEarning * (0.18) - rebate) / 12;

        } else if (totalEarning >= 189881 & totalEarning <= 296540)
        {
            taxable = (((totalEarning - 189880) * 0.26) + 34178 - rebate) / 12;

        } else if (totalEarning >= 296541 & totalEarning <= 410460)

        {
            taxable = (((totalEarning - 296540) * 0.31) + 61910 - rebate) / 12;

        } else if (totalEarning >= 410461 & totalEarning <= 555600)

        {
            taxable = (((totalEarning - 410460) * 0.36) + 97225 - rebate) / 12;

        } else if (totalEarning >= 555601 & totalEarning <= 708310)

        {

            taxable = (((totalEarning - 555600) * 0.39) + 149475 - rebate) / 12;
        } else if (totalEarning >= 708311 & totalEarning <=1500000)

        {

            taxable = (((totalEarning - 708310) * 0.41) + 209032 - rebate) / 12;
        }
        else 
           if (totalEarning >= 1500001 )
           {
               taxable = (((totalEarning - 1500000) * 0.45) + 209032 - rebate) / 12;
           } 

        DecimalFormat f = new DecimalFormat("##.00");

        return Double.parseDouble(f.format(taxable));

    }
}
