package com.dala.views.play;

import com.dala.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Play Menu")
@Route(value = "play", layout = MainLayout.class)
@RouteAlias(value = "play", layout = MainLayout.class)
@AnonymousAllowed
public class PlayView extends VerticalLayout {

    public PlayView() {
        setSpacing(false);

        add(new H1("Play"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();

        Button primaryButton = new Button("Manage citizens");
        primaryButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        /*primaryButton.addClickListener(e -> {
            UI.getCurrent().navigate(PlayView.class);
        });*/

        Button secondaryButton = new Button("Manage city");
        secondaryButton.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_LARGE);
        horizontalLayout.add(primaryButton);
        horizontalLayout.add(secondaryButton);

        add(horizontalLayout);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
