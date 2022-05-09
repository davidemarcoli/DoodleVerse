package com.dala.views.companymgmt;

import com.dala.data.company.Company;
import com.dala.data.company.CompanyDataProvider;
import com.dala.data.company.CompanyRepository;
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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.Arrays;
import java.util.List;

@PageTitle("Manage Companies")
@Route(value = "companymgmt", layout = MainLayout.class)
@PermitAll
@Log4j2
public class CompanyManagementView extends VerticalLayout {
    private final CompanyRepository companyRepository;
    private final CompanyDataProvider dataProvider;
    private final String COMPANY_NAME = "companyName";
    private final String EDIT_COLUMN = "vaadin-crud-edit-column";
    Crud<Company> crud;

    @Autowired
    public CompanyManagementView(CompanyDataProvider companyDataProvider, CompanyRepository companyRepository) {
        this.dataProvider = companyDataProvider;
        this.companyRepository = companyRepository;

        setupPage();
    }

    private CrudEditor<Company> createEditor() {
        TextField companyName = new TextField("Company");
        FormLayout form = new FormLayout(companyName);
        form.setMaxWidth("480px");
        form.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("30em", 2)
        );

        Binder<Company> binder = new Binder<>(Company.class);
        binder.forField(companyName).asRequired().bind(Company::getCompanyName, Company::setCompanyName);

        return new BinderCrudEditor<>(binder, form);
    }

    public void setupPage() {
        crud = new Crud<>(Company.class, createEditor());
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
        Grid<Company> grid = crud.getGrid();

        // Only show these columns (all columns shown by default):
        List<String> visibleColumns = Arrays.asList(
                COMPANY_NAME,
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
                grid.getColumnByKey(COMPANY_NAME),
                grid.getColumnByKey(EDIT_COLUMN)
        );
    }

    private void setupToolbar() {
        Html total = new Html("<span>Total: <b>" + companyRepository.count() + "</b> companies</span>");

        Button button = new Button("New company", VaadinIcon.PLUS.create());
        button.addClickListener(event -> {
            Company newCompany = new Company();
            crud.edit(newCompany, Crud.EditMode.NEW_ITEM);
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
