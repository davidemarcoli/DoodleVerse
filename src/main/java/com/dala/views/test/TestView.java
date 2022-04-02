package com.dala.views.test;

import com.dala.views.MainLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@PageTitle("Test")
@Route(value = "test", layout = MainLayout.class)
@RouteAlias(value = "test", layout = MainLayout.class)
@AnonymousAllowed
public class TestView extends VerticalLayout {

    public TestView() {
        add(new H1("Test"));
    }
}
