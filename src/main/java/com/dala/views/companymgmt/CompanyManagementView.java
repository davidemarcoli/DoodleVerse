package com.dala.views.companymgmt;

import com.dala.data.company.Company;
import com.dala.data.person.Person;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;

@PageTitle("Manage Companies")
@Route(value = "companymgmt", layout = MainLayout.class)
@PermitAll
@Log4j2
public class CompanyManagementView extends VerticalLayout {
    Crud<Company> crud;
    private final String FIRST_NAME = "companyName";
    private final String EDIT_COLUMN = "vaadin-crud-edit-column";

    private CrudEditor<Company> createEditor() {
        TextField companyName = new TextField("First name");
        FormLayout form = new FormLayout(companyName);
        form.setMaxWidth("480px");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2)
        );

        Binder<Person> binder = new Binder<>(Person.class);
        binder.forField(companyName).asRequired().bind(Company::getCompanyName, Company::setCompanyName);

        return new BinderCrudEditor<>(binder, form);
    }

    @Autowired
    public CompanyManagementView() {
        setupPage();
    }

    public void setupPage() {
        crud = new Crud<>(Company.class, createEditor());

    }

}
