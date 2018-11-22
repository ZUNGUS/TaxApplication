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
public class TaxCalculator2017
{
    
    final static double TAX_THRESHOLD_AGE_BELOW_65 = 75000;
    final static double TAX_THRESHOLD_AGE_65_TO_75 = 116150;
    final static double TAX_THRESHOLD_AGE__65 = 129850;
    
    final static int AGE_GROUP_ONE_BELOW_65 = 1;
    final static int AGE_GROUP_TWO_65_AND_75 = 2;
    final static int AGE_GROUP_THREE_ABOVE_75 = 3;
    
    final static double TAX_RREBATE_PRIMARY = 13500;
    final static double TAX_RREBATE_SECONDARY_AGE_65_74 = 7407;
    final static double TAX_RREBATE_TERTIARY_AGE_ABOVE_75 = 2466;

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
        
        if (salaryType.equalsIgnoreCase("m"))
        
        {
            totalEarning = earnings * 12;
        } else if (salaryType.equalsIgnoreCase("a"))
        {
            
            totalEarning = earnings;
        }
        
        return totalEarning;
    }

    //calcuating total of tax amount payable     
    public static double taxPayable(double rebate, double totalEarning)
    {
        
        double taxable = 0.0;
        
        if (totalEarning <= 188000)
        {
            taxable = (totalEarning * (0.18) - rebate) / 12;
            
        } else if (totalEarning >= 188001 & totalEarning <= 293600)
        {
            taxable = (((totalEarning - 33840) * 0.26) + 33840 - rebate) / 12;
            
        } else if (totalEarning >= 293601 & totalEarning <= 406400)
        
        {
            taxable = (((totalEarning - 293600) * 0.31) + 61296 - rebate) / 12;
            
        } else if (totalEarning >= 406401 & totalEarning <= 550100)
        
        {
            taxable = (((totalEarning - 406400) * 0.36) + 96264 - rebate) / 12;
            
        } else if (totalEarning >= 550101 & totalEarning <= 701300)
        
        {
            
            taxable = (((totalEarning - 550100) * 0.39) + 147996 - rebate) / 12;
        } else if (totalEarning >= 701301)
        
        {
            
            taxable = (((totalEarning - 701300) * 0.41) + 147996 - rebate) / 12;
        }
        
        DecimalFormat f = new DecimalFormat("##.00");
        
        return Double.parseDouble(f.format(taxable));
        
    }

    ///get age group and return a number corresponding to the age group constant
    public static int getAgeGroup(String ageGroup)
    {
        
        int age = 0;
        
        if (ageGroup.equalsIgnoreCase("Below 65"))
        {
            age = 1;
        } else if (ageGroup.equalsIgnoreCase("Between 65 and 75"))
        
        {
            age = 2;
        } else if (ageGroup.equalsIgnoreCase("Above 75"))
        
        {
            age = 3;
        }
        return age;
    }

    //get tax type and return based on the constant
    public static String getTaxType(String taxType)
    {
        String typeT = null;
        
        if (taxType.equalsIgnoreCase("Monthly"))
        {
            typeT = "M";
        } else if (taxType.equalsIgnoreCase("Annually"))
        {
            typeT = "A";
        }
        return typeT;
    }
    
    public static double medicalAid2017(String checkMedicalAid, int numberOfMembers)
    {
        double amount = 0;
        double extraPersonPrice;
        
        if (checkMedicalAid.equalsIgnoreCase("main"))
        
        {
            amount = 286;
        } else if (checkMedicalAid.equalsIgnoreCase("Main + dependents"))
        
        {
            amount=286+(192*numberOfMembers);
        }
        
        return amount;
    }
    
}
