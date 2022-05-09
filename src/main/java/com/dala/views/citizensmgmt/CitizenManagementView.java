package com.dala.views.citizensmgmt;

import com.dala.data.person.Person;
import com.dala.data.person.PersonDataProvider;
import com.dala.data.person.PersonRepository;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.crud.BinderCrudEditor;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.crud.CrudEditor;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


@PageTitle("Manage Citizens")
@Route(value = "citizensmgmt", layout = MainLayout.class)
@PermitAll
@Log4j2
public class CitizenManagementView extends VerticalLayout {

    private final PersonRepository personRepository;
    private final PersonDataProvider dataProvider;
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String MONEY = "money";
    private final String EDIT_COLUMN = "vaadin-crud-edit-column";
    Crud<Person> crud;
    Random rand = new Random();

    @Autowired
    public CitizenManagementView(PersonDataProvider personDataProvider, PersonRepository personRepository) {
        this.dataProvider = personDataProvider;
        this.personRepository = personRepository;

        setupPage();
    }

    private CrudEditor<Person> createEditor() {
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        NumberField money = new NumberField("Money");
        money.setReadOnly(true);
        Double randomMoneyAmount = rand.nextDouble(100000 + 1 - 10000) + 10000;
        money.setValue(randomMoneyAmount);

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
        binder.forField(money).asRequired().bind(Person::getMoney, Person::setMoney).setReadOnly(true);

        return new BinderCrudEditor<>(binder, form);
    }

    public void setupPage() {
        crud = new Crud<>(Person.class, createEditor());
        crud.setDataProvider(dataProvider);

        setupGrid();
        setupToolbar();
        setupDataProvider();

        crud.setSizeFull();
        add(crud);
        setSizeFull();
    }

    private void setupDataProvider() {
        crud.addSaveListener(personSaveEvent -> {
            log.info("Save");
            dataProvider.persist(personSaveEvent.getItem());
            setupToolbar();
        });

        crud.addDeleteListener(personDeleteEvent -> {
            log.info("Delete");
            dataProvider.delete(personDeleteEvent.getItem());
            setupToolbar();
        });
    }

    private void setupGrid() {
        Grid<Person> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(
                FIRST_NAME,
                LAST_NAME,
                MONEY,
                EDIT_COLUMN
        );
        grid.getColumns().forEach(column -> {
            String key = column.getKey();
            if (!visibleColumns.contains(key)) {
                grid.removeColumn(column);
            }
        });

        // Reorder the columns (alphabetical by default)
        grid.setColumnOrder(
                grid.getColumnByKey(FIRST_NAME),
                grid.getColumnByKey(LAST_NAME),
                grid.getColumnByKey(MONEY),
                grid.getColumnByKey(EDIT_COLUMN)
        );
    }

    private void setupToolbar() {
        Html total = new Html("<span>Total: <b>" + personRepository.count() + "</b> persons</span>");

        Button button = new Button("New person", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            Person newPerson = new Person();
            crud.edit(newPerson, Crud.EditMode.NEW_ITEM);
        });
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        HorizontalLayout toolbar = new HorizontalLayout(total, button);
        toolbar.setAlignItems(FlexComponent.Alignment.CENTER);
        toolbar.setFlexGrow(1, toolbar);
        toolbar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        toolbar.setSpacing(false);

        crud.setToolbar(toolbar);
    }
}
