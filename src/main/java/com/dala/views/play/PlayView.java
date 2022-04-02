package com.dala.views.play;

import com.dala.views.MainLayout;
import com.vaadin.flow.component.html.H1;
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
        add(new H1("Play"));
    }
}
