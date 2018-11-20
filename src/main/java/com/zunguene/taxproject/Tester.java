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
        int age = 2;
        String salaryType = "a";
        double earning = 360000;

        // double rebate = calculateRebateTax(age);
        double rebate = TaxCalculator2017.calculateRebateTax(age);
        //  double totalEarning = calculateTotalEarnings(salaryType, earning);
        double totalEarning = TaxCalculator2017.calculateTotalEarnings(salaryType, earning);
        double totalTax = TaxCalculator2017.taxPayable(rebate, earning);
        System.out.println(totalTax);
    }
}
