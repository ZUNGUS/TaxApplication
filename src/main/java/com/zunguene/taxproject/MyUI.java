package com.zunguene.taxproject;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.util.ArrayList;
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
    HorizontalLayout groupLayout;

    TextField txtAmount, txtMembers;
    Button btnCalculate;

    @Override
    protected void init(VaadinRequest vaadinRequest)
    {

        /////Initialise layout
        display = new VerticalLayout();
        // display.setSizeFull();
        groupLayout = new HorizontalLayout();
       

        //initialise  screen components
        grpTaxYear = new RadioButtonGroup("Select tax year");
        grpTaxType = new RadioButtonGroup("Select Tax type");
        grpAgeGroup = new RadioButtonGroup("Select age group");
        grpMedicalAid = new RadioButtonGroup<>("Medical Aid Details");

        grpTaxYear.setItems("2017", "2018");
        grpTaxType.setItems("Monthly", "Annually");
        grpAgeGroup.setItems("Below 65", "Between 65 and 75", "Above 75");
        grpMedicalAid.setItems("Main Member", "Main + dependents");

        lblHeading = new Label("Tax Calculator");

        txtAmount = new TextField("Taxable Earnings");
        txtMembers = new TextField("Number of depandents");

        btnCalculate = new Button("Results");
          
      
        
        display.addComponent(lblHeading);
        display.addComponent(txtAmount);
        groupLayout.addComponent(grpTaxYear);
        groupLayout.addComponent(grpTaxType);
        groupLayout.addComponent(grpAgeGroup);
        display.addComponent(groupLayout);
        display.addComponent(grpMedicalAid);
        display.addComponent(txtMembers);
        display.addComponent(btnCalculate);
      
        
        
        display.setComponentAlignment(lblHeading, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(txtAmount, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(txtMembers, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(btnCalculate, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(groupLayout, Alignment.MIDDLE_CENTER);
        display.setComponentAlignment(grpMedicalAid, Alignment.MIDDLE_CENTER);

        setContent(display);

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet
    {

    }

}
