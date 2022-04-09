package com.dala.views.citymgmt;

import com.dala.views.MainLayout;
import com.dala.views.buildhouse.BuildHouseView;
import com.dala.views.statistics.StatisticsView;
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

import javax.annotation.security.PermitAll;

@PageTitle("Manage City")
@Route(value = "citymgmt", layout = MainLayout.class)
@PermitAll
public class CityManagementView extends VerticalLayout {

    public CityManagementView() {
        setSpacing(false);

        add(new H1("Manage City"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button manageCompaniesBtn = new Button("Manage Companies");
        manageCompaniesBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        /*manageCompaniesBtn.addClickListener(e -> {
            UI.getCurrent().navigate(PlayView.class);
        });*/

        Button manageCityStatsBtn = new Button("View Stats");
        manageCityStatsBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        manageCityStatsBtn.addClickListener(e -> {
            UI.getCurrent().navigate(StatisticsView.class);
        });

        Button buildHouseBtn = new Button("Build House");
        buildHouseBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        buildHouseBtn.addClickListener(e -> {
            UI.getCurrent().navigate(BuildHouseView.class);
        });

        horizontalLayout.add(manageCompaniesBtn);
        horizontalLayout.add(manageCityStatsBtn);
        horizontalLayout.add(buildHouseBtn);

        add(horizontalLayout);

        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }
}
