package com.dala.views.cityoverview;

import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.company.Company;
import com.dala.data.company.CompanyRepository;
import com.dala.data.person.Person;
import com.dala.data.person.PersonDataProvider;
import com.dala.data.person.PersonRepository;
import com.dala.utils.HouseImageUtils;
import com.dala.views.MainLayout;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.crud.Crud;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@PageTitle("City Overview")
@Route(value = "overview", layout = MainLayout.class)
@PermitAll
public class CityOverviewView extends VerticalLayout {
    private final HouseRepository houseRepository;
    private final CompanyRepository companyRepository;
    HouseImageUtils houseImageUtils = HouseImageUtils.getInstance();
    @Autowired
    public CityOverviewView(HouseRepository houseRepository, CompanyRepository companyRepository) {
        this.houseRepository = houseRepository;
        this.companyRepository = companyRepository;

        setupPage();
    }
    public void setupPage() {
        List<House> houses = houseRepository.findAll();
        List<Company> companies = companyRepository.findAll();

        for (House house: houses) {
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
            image.setWidth(house.getSize().getWidth()/2f, Unit.PIXELS);
            image.setHeight(500, Unit.PIXELS);
            add(image);
        }

        for (Company company: companies) {
            Image companyImage = new Image();
            companyImage.setSrc("images/office.jpg");
            companyImage.setHeight(400, Unit.PIXELS);

            add(companyImage);
            add(company.getCompanyName());
        }

        setSizeFull();
    }
}
