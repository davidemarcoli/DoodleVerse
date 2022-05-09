package com.dala.views.buildhouse;

import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseBuilder;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.building.size.Size;
import com.dala.data.building.size.SizeRepository;
import com.dala.data.building.wall.Wall;
import com.dala.data.building.wall.WallRepository;
import com.dala.utils.HouseImageUtils;
import com.dala.utils.MathUtils;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

@PageTitle("Build House")
@Route(value = "build", layout = MainLayout.class)
@PermitAll
@Log4j2
public class BuildHouseView extends VerticalLayout implements Serializable {
    @Serial
    private static final long serialVersionUID = 9142204424500605320L;
    private final HouseRepository houseRepository;
    private final SizeRepository sizeRepository;
    private final WallRepository wallRepository;
    Random random = new Random();
    HouseImageUtils houseImageUtils = HouseImageUtils.getInstance();

    Select<String> sizeSelect = new Select<>();
    Select<String> wallSelect = new Select<>();

    TextField colorField = new TextField();
    Image houseImage = new Image();

    House currentHouse = null;

    @Autowired
    public BuildHouseView(HouseRepository houseRepository, SizeRepository sizeRepository, WallRepository wallRepository) {
        this.houseRepository = houseRepository;
        this.sizeRepository = sizeRepository;
        this.wallRepository = wallRepository;

        setupPage();
    }

    public void setupPage() {

        Button createButton = new Button();

        HorizontalLayout createButtonLayout = new HorizontalLayout();
        createButton.setIcon(new Icon(VaadinIcon.HAMMER));
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);
        createButtonLayout.setAlignItems(Alignment.END);

        createButtonLayout.add(createButton);
        add(createButtonLayout);

        houseImage.setAlt("The Selected House!");

        add(houseImage);

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


        colorField.setLabel("Ceiling Color");
        colorField.setRequired(true);
        String colorHelperText = "Example: blue<br>or 0000ff or 00f<br>or 0, 0, 255";
        Div colorHelperDiv = new Div();
        colorHelperDiv.getElement().setProperty("innerHTML", colorHelperText);
        colorHelperDiv.getStyle().set("text-align", "left");
        colorField.setHelperComponent(colorHelperDiv);
        dropdownLayout.add(colorField);

        add(dropdownLayout);


        createButton.addClickListener(buttonClickEvent -> {
            saveHouse();
        });

        colorField.addValueChangeListener(event -> {
            updateInfos();
            setImageSource();
        });

        sizeSelect.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            updateInfos();
            setImageSource();
        });

        wallSelect.addValueChangeListener(selectStringComponentValueChangeEvent -> {
            updateInfos();
            setImageSource();
        });

        updateInfos();
        setImageSource();


        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(FlexComponent.Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

    @SneakyThrows
    private void saveHouse() {

        Thread thread = startLoadingBar();
        thread.start();
        thread.join();

        currentHouse = houseRepository.save(currentHouse);

        if (houseRepository.findById(currentHouse.getId()).isPresent()) {
            Notification notification = Notification.show("House Built!");
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
        } else {
            Notification notification = Notification.show("Unknown Error! Please try again later.");
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            notification.setPosition(Notification.Position.BOTTOM_CENTER);
        }
    }

    private void setImageSource() {
        String source = houseImageUtils.generateImageByHouse(currentHouse);
        StreamResource resource = new StreamResource(Arrays.stream(source.split("/")).reduce((first, second) -> second).orElse("image.jpg"), () -> {
            try {
                return new FileInputStream(source);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });

        int width = currentHouse.getSize().getWidth();
        houseImage.setWidth(width / 2f, Unit.PIXELS);
        houseImage.setHeight(500, Unit.PIXELS);
        houseImage.setSrc(resource);
    }

    private void updateInfos() {
        Color inputColor = houseImageUtils.getColor(colorField.getValue());

        if (inputColor == null) {
            inputColor = Color.black;
            colorField.setInvalid(true);
        } else {
            colorField.setInvalid(false);
        }

        currentHouse = new HouseBuilder()
                .ceilingColor(Integer.toHexString(inputColor.getRGB()).substring(2))
                .size(sizeRepository.getSizeByType(sizeSelect.getValue()).orElse(null))
                .wall(wallRepository.getWallByType(wallSelect.getValue()).orElse(null))
                .build();
    }

    @SneakyThrows
    private Thread startLoadingBar() {
        return new Thread(() -> {
            ProgressBar loadingBar = new ProgressBar();
            loadingBar.setMin(0);
            loadingBar.setValue(0);
            loadingBar.setMax(MathUtils.getInstance().randomMinMax(2000, 5000));

            while (loadingBar.getMax() > loadingBar.getValue()) {
                int nextValue = random.nextInt((int) (loadingBar.getMax() / 5));

                try {
                    Thread.sleep(nextValue);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                loadingBar.setValue(Math.min(loadingBar.getValue() + nextValue, loadingBar.getMax()));
            }
        });
    }
}
