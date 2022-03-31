package com.dala.views.person;

import com.dala.data.DataManager;
import com.dala.data.person.Person;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import javax.annotation.security.PermitAll;

@PageTitle("Person List")
@Route(value = "person-list", layout = MainLayout.class)
@PermitAll
public class PersonListView extends VerticalLayout {

    public PersonListView() {

        setSizeFull();

        Grid<Person> grid = new Grid<>();
        grid.setItems(DataManager.getInstance().persons);
        grid.addColumn(Person::getFirstName).setHeader("First Name");
        grid.addColumn(Person::getLastName).setHeader("Last Name");
//        grid.addColumn(Person::getMoney).setHeader("Money");
        grid.addColumn(LitRenderer.<Person>of("${item.money} $")
                .withProperty("money", Person::getMoney)).setHeader("Money");

        add(grid);

    }
}
