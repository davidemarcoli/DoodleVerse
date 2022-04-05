package com.dala.views.citizensmgmt;

import com.dala.data.person.Person;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import javax.annotation.security.PermitAll;

@PageTitle("Manage Citizens")
@Route(value = "citizensmgmt", layout = MainLayout.class)
@PermitAll
public class CitizenManagementView extends VerticalLayout {
    private CrudEditor<Person> createEditor() {
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        NumberField money = new NumberField("Money");
        money.setEnabled(false);

        //profession.setItems(professions);

        FormLayout form = new FormLayout(firstName, lastName, money);
        form.setColspan(money, 2);
        form.setMaxWidth("480px");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2)
        );

        Binder<Person> binder = new Binder<>(Person.class);
        binder.forField(firstName).asRequired().bind(Person::getFirstName, Person::setFirstName);
        binder.forField(lastName).asRequired().bind(Person::getLastName, Person::setLastName);
        binder.forField(money).asRequired().bind(Person::getMoney, Person::setMoney);

        return new BinderCrudEditor<>(binder, form);
    }

    public CitizenManagementView() {
        add(new Crud<>(Person.class, createEditor()));
        setSizeFull();
    }
}
