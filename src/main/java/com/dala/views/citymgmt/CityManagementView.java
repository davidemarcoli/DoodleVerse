package com.dala.views.citymgmt;

import com.dala.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Manage City")
@Route(value = "citymgmt", layout = MainLayout.class)
@RouteAlias(value = "citymgmt", layout = MainLayout.class)
@AnonymousAllowed
public class CityManagementView extends VerticalLayout {

    public CityManagementView() {
        setSpacing(false);

        add(new H1("Manage City"));

        HorizontalLayout verticalLayout = new HorizontalLayout();

        Button manageCompaniesBtn = new Button("Manage Companies");
        manageCompaniesBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        /*manageCompaniesBtn.addClickListener(e -> {
            UI.getCurrent().navigate(PlayView.class);
        });*/

        Button manageCityStatsBtn = new Button("View Stats");
        manageCityStatsBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        Button buildHouseBtn = new Button("Build House");
        buildHouseBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        verticalLayout.add(manageCompaniesBtn);
        verticalLayout.add(manageCityStatsBtn);
        verticalLayout.add(buildHouseBtn);

        add(verticalLayout);

        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
