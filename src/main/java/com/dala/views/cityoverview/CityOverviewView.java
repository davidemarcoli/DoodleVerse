package com.dala.views.cityoverview;

import com.dala.data.building.Building;
import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.company.Company;
import com.dala.data.company.CompanyRepository;
import com.dala.utils.HouseImageUtils;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@PageTitle("City Overview")
@Route(value = "overview", layout = MainLayout.class)
@PermitAll
public class CityOverviewView extends VerticalLayout {

    @Serial
    private static final long serialVersionUID = 2847631440914813073L;
    private final HouseRepository houseRepository;
    private final CompanyRepository companyRepository;
    HouseImageUtils houseImageUtils = HouseImageUtils.getInstance();

    @Autowired
    public CityOverviewView(HouseRepository houseRepository, CompanyRepository companyRepository) {
        this.houseRepository = houseRepository;
        this.companyRepository = companyRepository;

        setupPage();
    }

    /**
     * Append the houses to the page in random order
     */
    public void setupPage() {
        List<House> houses = houseRepository.findAll();
        List<Company> companies = companyRepository.findAll();

        List<Building> buildings = new ArrayList<>(Stream.concat(houses.stream(), companies.stream()).toList());
        Collections.shuffle(buildings);

        for (Building building : buildings) {
            if (building instanceof House house) {
                appendHouseImage(house);
            } else if (building instanceof Company company) {
                appendCompanyImage(company);
            }
        }

        setSizeFull();
    }

    /**
     * Append the house to the page
     * @param house the house to append
     */
    public void appendHouseImage(House house) {
        Image image = new Image();
        String source = houseImageUtils.generateImageByHouse(house);
        StreamResource resource = new StreamResource(Arrays.stream(source.split("/")).reduce((first, second) -> second).orElse("image.jpg"), () -> {
            try {
                return new FileInputStream(source);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        });
        image.setSrc(resource);
        image.setWidth(house.getSize().getWidth() / 2f, Unit.PIXELS);
        image.setHeight(500, Unit.PIXELS);
        add(image);
    }

    /**
     * Append the company to the page
     * @param company the company to append
     */
    public void appendCompanyImage(Company company) {
        Image companyImage = new Image();
        companyImage.setSrc("images/office.jpg");
        companyImage.setHeight(400, Unit.PIXELS);

        add(companyImage);
        add(company.getCompanyName());
    }
}
