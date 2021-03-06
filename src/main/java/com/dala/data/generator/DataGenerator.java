package com.dala.data.generator;

import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseBuilder;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.building.size.Size;
import com.dala.data.building.size.SizeRepository;
import com.dala.data.building.wall.Wall;
import com.dala.data.building.wall.WallRepository;
import com.dala.data.company.CompanyRepository;
import com.dala.data.person.PersonRepository;
import com.dala.data.user.User;
import com.dala.data.user.UserRepository;
import com.dala.security.Role;
import com.vaadin.flow.spring.annotation.SpringComponent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.awt.*;
import java.util.Collections;
import java.util.Set;

@SpringComponent
@Log4j2
public class DataGenerator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private WallRepository wallRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Bean
    public CommandLineRunner loadData(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        return args -> {

            log.info("Generating data");

            if (!hasUser("davide")) {
                User user = new User();
                user.setName("Davide Marcoli");
                user.setUsername("davide");
                user.setHashedPassword(passwordEncoder.encode("davide"));
                user.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                user.setRoles(Collections.singleton(Role.USER));
                userRepository.save(user);
            }

            if (!hasUser("lazar")) {
                User admin = new User();
                admin.setName("Lazar Petrovi??");
                admin.setUsername("lazar");
                admin.setHashedPassword(passwordEncoder.encode("lazar"));
                admin.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
                admin.setRoles(Set.of(Role.USER, Role.ADMIN));
                userRepository.save(admin);
            }

            saveSizeIfNotExists("Small", 800);
            saveSizeIfNotExists("Wide", 1000);
            saveSizeIfNotExists("Extra Wide", 1400);

            saveWallIfNotExists("Wood");
            saveWallIfNotExists("Stone");
            saveWallIfNotExists("Brick");

            if (houseRepository.count() < 1) {
                House house = new HouseBuilder()
                        .ceilingColor(Integer.toHexString(Color.blue.getRGB()).substring(2))
                        .size(sizeRepository.getSizeByType("Extra Wide").orElse(null))
                        .wall(wallRepository.getWallByType("Wood").orElse(null))
                        .build();

                houseRepository.save(house);
            }


            if (personRepository.count() < 10) {
                personRepository.saveAllAndFlush(FakeGenerator.getInstance().generateRandomPersons(10));
            }

            if (companyRepository.count() < 5) {
                companyRepository.saveAllAndFlush(FakeGenerator.getInstance().generateCompanies(5));
            }

            log.info("Generated data");

        };
    }

    public boolean hasUser(String username) {
        return userRepository.findByUsername(username) != null;
    }

    /**
     * Save the Size if it not allready exists
     * @param type the Size name
     * @param width the Width
     */
    public void saveSizeIfNotExists(String type, int width) {
        if (sizeRepository.getSizeByType(type).isEmpty()) {
            sizeRepository.save(new Size(0L, type, width));
        }
    }

    /**
     *
     * @param type
     */
    public void saveWallIfNotExists(String type) {
        if (wallRepository.getWallByType(type).isEmpty()) {
            wallRepository.save(new Wall(0L, type));
        }
    }

}