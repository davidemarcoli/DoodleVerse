package com.dala.views.home;

import com.dala.views.MainLayout;
import com.dala.views.cityoverview.CityOverviewView;
import com.dala.views.play.PlayView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;

@PageTitle("Home")
@Route(value = "", layout = MainLayout.class)
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSpacing(false);

        Image img = new Image("images/empty-plant.png", "placeholder plant");
        img.setWidth("200px");
        add(img);

        add(new H2("DoodleVerse"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button primaryButton = new Button("Play");
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        primaryButton.addClickListener(e -> {
            UI.getCurrent().navigate(PlayView.class);
        });

        Button secondaryButton = new Button("City Overview");
        secondaryButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_LARGE);
        secondaryButton.addClickListener(e -> {
            UI.getCurrent().navigate(CityOverviewView.class);
        });

        horizontalLayout.add(primaryButton);
        horizontalLayout.add(secondaryButton);

        add(horizontalLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
