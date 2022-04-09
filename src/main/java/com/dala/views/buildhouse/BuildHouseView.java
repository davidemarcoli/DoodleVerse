package com.dala.views.buildhouse;

import com.dala.data.building.ceiling.Ceiling;
import com.dala.data.building.ceiling.CeilingRepository;
import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.building.size.Size;
import com.dala.data.building.size.SizeRepository;
import com.dala.data.building.wall.Wall;
import com.dala.data.building.wall.WallRepository;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Arrays;

@PageTitle("Build House")
@Route(value = "build", layout = MainLayout.class)
@PermitAll
@Log4j2
public class BuildHouseView extends VerticalLayout {

    private final HouseRepository houseRepository;
    private final CeilingRepository ceilingRepository;
    private final SizeRepository sizeRepository;
    private final WallRepository wallRepository;

    Select<String> sizeSelect = new Select<>();
    Select<String> wallSelect = new Select<>();
    Select<String> ceilingSelect = new Select<>();

    Text selectedStuff = new Text("");
    Text price = new Text("0$");


    public void setupPage() {

        Button createButton = new Button();

        HorizontalLayout createButtonLayout = new HorizontalLayout();
        createButton.setIcon(new Icon(VaadinIcon.HAMMER));
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        createButtonLayout.setAlignItems(Alignment.END);
//        createButtonLayout.setVerticalComponentAlignment(Alignment.END);

        createButtonLayout.add(createButton);
        add(createButtonLayout);

        add(selectedStuff);
//        add(price);

        HorizontalLayout dropdownLayout = new HorizontalLayout();

        sizeSelect.setLabel("Size");
        Size[] sizes = sizeRepository.findAll().toArray(new Size[0]);
        ArrayList<String> sizeNames = new ArrayList<>();
        Arrays.stream(sizes).forEach(size -> sizeNames.add(size.getType()));
        sizeSelect.setItems(sizeNames);
        sizeSelect.setValue(sizeNames.get(0));
        dropdownLayout.add(sizeSelect);

        wallSelect.setLabel("Wall");
        Wall[] walls = wallRepository.findAll().toArray(new Wall[0]);
        ArrayList<String> wallNames = new ArrayList<>();
        Arrays.stream(walls).forEach(wall -> wallNames.add(wall.getType()));
        wallSelect.setItems(wallNames);
        wallSelect.setValue(wallNames.get(0));
        dropdownLayout.add(wallSelect);

        ceilingSelect.setLabel("Ceiling");
        Ceiling[] ceilings = ceilingRepository.findAll().toArray(new Ceiling[0]);
        ArrayList<String> ceilingNames = new ArrayList<>();
        Arrays.stream(ceilings).forEach(ceiling -> ceilingNames.add(ceiling.getType()));
        ceilingSelect.setItems(ceilingNames);
        ceilingSelect.setValue(ceilingNames.get(0));
        dropdownLayout.add(ceilingSelect);

        add(dropdownLayout);

        createButton.addClickListener(buttonClickEvent -> {
            saveHouse();
        });

        ceilingSelect.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            setInfoText();
        });

        sizeSelect.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            setInfoText();
        });

        wallSelect.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            setInfoText();
        });

        setInfoText();

        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    private void saveHouse() {

        House house = new House();
        house.setCeiling(ceilingRepository.getCeilingByType(ceilingSelect.getValue()).orElse(null));
        house.setSize(sizeRepository.getSizeByType(sizeSelect.getValue()).orElse(null));
        house.setWall(wallRepository.getWallByType(wallSelect.getValue()).orElse(null));
        house = houseRepository.save(house);

        if (houseRepository.findById(house.getId()).isPresent()) {
            Notification notification = Notification.show("House Built!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
        } else {
            Notification notification = Notification.show("Unknown Error! Please try again later.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
        }
    }

    private void setInfoText() {
        selectedStuff.setText("Ceiling: " + ceilingSelect.getValue() + "   " +
                "Size: " + sizeSelect.getValue() + "   " +
                "Wall: " + wallSelect.getValue());
    }

    @Autowired
    public BuildHouseView(HouseRepository houseRepository, CeilingRepository ceilingRepository, SizeRepository sizeRepository, WallRepository wallRepository) {
        this.houseRepository = houseRepository;
        this.ceilingRepository = ceilingRepository;
        this.sizeRepository = sizeRepository;
        this.wallRepository = wallRepository;

        setupPage();
    }
}
