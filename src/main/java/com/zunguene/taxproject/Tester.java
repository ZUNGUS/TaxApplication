/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zunguene.taxproject;

/**
 *
 * @author Luckyz
 */
public class Tester
{

    public static void main(String[] args)
    {
     
        String salaryType = "a";
        double earning = 360000;
        

        double rebate = TaxCalculator2017.calculateRebateTax(2);
        double totalEarning = TaxCalculator2017.calculateTotalEarnings(salaryType, earning);
        double totalTax = TaxCalculator2017.taxPayable(rebate, earning);
        System.out.println(totalTax +"rebate  secondary");
        
        double rebate1 = TaxCalculator2017.calculateRebateTax(3);
        double totalEarning1 = TaxCalculator2017.calculateTotalEarnings(salaryType, earning);
        double totalTax1 = TaxCalculator2017.taxPayable(rebate, earning);
        System.out.println(totalTax1 +"rebate  tertiary");
    }
}
