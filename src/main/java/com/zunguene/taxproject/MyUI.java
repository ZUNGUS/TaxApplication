package com.zunguene.taxproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.beans.property.ObjectProperty;
import javafx.scene.control.RadioButton;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 *
 * author lucky zunguene
 */
@Theme("mytheme")
public class MyUI extends UI
{

    RadioButtonGroup<String> grpTaxYear;
    RadioButtonGroup<String> grpTaxType;
    RadioButtonGroup<String> grpAgeGroup;
    RadioButtonGroup<String> grpMedicalAid;
    Label lblHeading;
    VerticalLayout display;
    HorizontalLayout groupLayout, mainLayyout, pnlLayout;

    TextField txtAmount, txtMembers;
    TextArea txtResults;
    Button btnCalculate;

    static int depandents;

    DecimalFormat f = new DecimalFormat("##.00");

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {
        buildDisply();
        medicalAidListner();
        calculateListener();

        setContent(mainLayyout);

        //  VaadinService.reinitializeSession(request);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet
    {

    }

    public void buildDisply()
    {

        //LAYOUT COMPONETS
        display = new VerticalLayout();
        groupLayout = new HorizontalLayout();
        pnlLayout = new HorizontalLayout();
        mainLayyout = new HorizontalLayout();

        //RADIO  BUTTONS COMPONENTS
        grpTaxYear = new RadioButtonGroup("Select tax year");
        grpTaxType = new RadioButtonGroup("Select Tax type");
        grpAgeGroup = new RadioButtonGroup("Select age group");
        grpMedicalAid = new RadioButtonGroup("Medical Aid Details");

        ///adding radiobuttons items
        grpTaxYear.setItems("2017", "2018");
        grpTaxType.setItems("Monthly", "Annually");
        grpAgeGroup.setItems("Below 65", "Between 65 and 75", "Above 75");
        grpMedicalAid.setItems("Main Member", "Main + dependents");

        //PANEL COMPONETS
        Panel pnlAgeGroup = new Panel("Select age group");
        pnlAgeGroup.setSizeUndefined();
        Panel pnlTaxYear = new Panel("Select tax year");
        pnlTaxYear.setSizeUndefined();
        Panel pnlTaxType = new Panel("Select tax Type");
        pnlTaxType.setSizeUndefined();
        Panel pnlMedicalAid = new Panel("Medical Aid detials");
        pnlMedicalAid.setSizeUndefined();
        Panel pnlMain = new Panel("TAX DETAILS");
        pnlMain.setSizeUndefined();

        ////ADDING RADIO BUTTONS TO PANELS
        pnlAgeGroup.setContent(grpAgeGroup);
        pnlTaxYear.setContent(grpTaxYear);
        pnlTaxType.setContent(grpTaxType);
        pnlMedicalAid.setContent(grpMedicalAid);

        ////ADDING THE PANEL TO A LAYOUT
        pnlLayout.addComponent(pnlTaxYear);
        pnlLayout.addComponent(pnlTaxType);
        pnlLayout.addComponent(pnlAgeGroup);
        pnlLayout.addComponent(pnlMedicalAid);

////ADDING PNL LAYOUT TO MAIN PANEL
        pnlMain.setContent(pnlLayout);

        //TEXTFIED COMPONETS
        lblHeading = new Label("Tax Calculator");
        txtAmount = new TextField("Taxable Earnings");
        txtMembers = new TextField("Number of depandents");
        txtMembers.setValue(0 + "");
        txtMembers.setEnabled(false);
        btnCalculate = new Button("Calculate");
        txtResults = new TextArea("RESULTS");
        txtResults.setSizeFull();

        display.addComponent(lblHeading);
        display.addComponent(txtAmount);
        display.addComponent(pnlMain);
        display.addComponent(txtMembers);
        display.addComponent(btnCalculate);

        display.setComponentAlignment(lblHeading, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(txtAmount, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(txtMembers, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(btnCalculate, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(pnlMain, Alignment.MIDDLE_CENTER);

        mainLayyout.addComponent(display);
        mainLayyout.addComponent(txtResults);

    }

    public void calculateListener()
    {

        btnCalculate.addClickListener(e ->
        {
//getting the members value
            depandents = Integer.parseInt(txtMembers.getValue());
            //enter earning amount 
            double earning = Double.parseDouble(txtAmount.getValue());
            //get tax type annual or monthly
            String taxType = TaxCalculator2017.getTaxType(grpTaxType.getSelectedItem().get());
            ///earning converted to annual
            double totalEarnings = TaxCalculator2017.calculateTotalEarnings(taxType, earning);
            //get tax year
            String taxYear = grpTaxYear.getSelectedItem().get();
            if (taxYear.equalsIgnoreCase("2017"))
            {

                //medical aid price for 2017
                double medicalPrice = TaxCalculator2017.medicalAid2017(grpMedicalAid.getSelectedItem().get(), depandents);
                System.out.println(medicalPrice);

                //get age group
                int age = TaxCalculator2017.getAgeGroup(grpAgeGroup.getSelectedItem().get());

                //calculate rebate 
                double rebate = TaxCalculator2017.calculateRebateTax(age);

                //calculate payable tax monthly
                double totalTax = TaxCalculator2017.taxPayable(rebate, totalEarnings);
                // txtMembers.setValue("" + totalTax);

                double monthlyPaye, annualPaye, taxCradit, payAfterTax, netCashAfterPayee;

                taxCradit = totalTax;

                if (grpTaxType.getSelectedItem().get().equalsIgnoreCase("Monthly"))
                {
                    monthlyPaye = earning;
                    annualPaye = earning * 12;
                    payAfterTax = earning - taxCradit;
                    netCashAfterPayee = monthlyPaye - medicalPrice - totalTax;

                } else
                {
                    monthlyPaye = earning / 12;
                    annualPaye = earning;
                    payAfterTax = earning - taxCradit;

                    netCashAfterPayee = annualPaye - medicalPrice - (totalTax * 12);
                }

                String monthlyp, annullyP, taxCdit, payeDue, netcash;
                monthlyp = "Monthly PAYE:" + monthlyPaye;
                annullyP = "Annually PAYE:" + annualPaye;
                taxCdit = "Tax Credit:" + taxCradit;
                payeDue = "PAYE DUE AT:" + payAfterTax;
                netcash = "Net Cash Pay:" + netCashAfterPayee;

                txtResults.setValue(monthlyp + "\n" + annullyP + "\n" + taxCdit + "\n" + payeDue + "\n" + f.format(netcash));

            } else if (taxYear.equalsIgnoreCase("2018"))
            {

                //medical aid price for 2018
                double medicalPrice = TaxCalculator2018.medicalAid2018(grpMedicalAid.getSelectedItem().get(), depandents);
                System.out.println(medicalPrice + "2018");
                //get age group
                int age = TaxCalculator2018.getAgeGroup(grpAgeGroup.getSelectedItem().get());

                //calculate rebate 
                double rebate = TaxCalculator2018.calculateRebateTax(age);

                //calculate payable tax monthly
                double totalTax = TaxCalculator2018.taxPayable(rebate, totalEarnings);

                double monthlyPaye, annualPaye, taxCradit, payAfterTax, netCashAfterPayee;
                taxCradit = totalTax;
                if (grpTaxType.getSelectedItem().get().equalsIgnoreCase("Monthly"))
                {
                    monthlyPaye = earning;
                    annualPaye = earning * 12;
                    payAfterTax = earning - taxCradit;
                    netCashAfterPayee = payAfterTax - medicalPrice;

                } else
                {
                    monthlyPaye = earning / 12;
                    annualPaye = earning;
                    payAfterTax = earning - taxCradit;
                    netCashAfterPayee = payAfterTax - medicalPrice;
                }

                String monthlyp, annullyP, taxCdit, payeDue, netcash;
                monthlyp = "Monthly PAYE:" + monthlyPaye;
                annullyP = "Annually PAYE:" + annualPaye;
                taxCdit = "Tax Credit:" + taxCradit;
                payeDue = "PAYE DUE:" + payAfterTax;
                netcash = "Net Cash Pay" + netCashAfterPayee;

                txtResults.setValue(monthlyp + "\n" + annullyP + "\n" + taxCdit + "\n" + payeDue + "\n" + netcash);

            }
        });

    }

    public void medicalAidListner()
    {
        grpMedicalAid.addValueChangeListener(e ->
        {
            if (grpMedicalAid.getSelectedItem().get().equalsIgnoreCase("Main + dependents"))
            {
                txtMembers.setEnabled(true);
                txtMembers.setValue(0 + "");
            } else if (grpMedicalAid.getSelectedItem().get().equalsIgnoreCase("Main Member"))
            {
                txtMembers.setEnabled(false);
                txtMembers.setValue(0 + "");
            }

        });

    }
}
