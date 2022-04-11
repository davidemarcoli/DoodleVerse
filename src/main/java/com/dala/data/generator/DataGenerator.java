package com.dala.data.generator;

import com.dala.data.building.ceiling.Ceiling;
import com.dala.data.building.ceiling.CeilingRepository;
import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.building.size.Size;
import com.dala.data.building.size.SizeRepository;
import com.dala.data.building.wall.Wall;
import com.dala.data.building.wall.WallRepository;
import com.dala.data.person.PersonRepository;
import com.dala.security.Role;
import com.dala.data.user.User;
import com.dala.data.user.UserRepository;
import com.vaadin.flow.spring.annotation.SpringComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.Objects;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.imageio.ImageIO;

@SpringComponent
@Log4j2
public class DataGenerator {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CeilingRepository ceilingRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private WallRepository wallRepository;

    @Autowired
    private HouseRepository houseRepository;

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
                admin.setName("Lazar Petrović");
                admin.setUsername("lazar");
                admin.setHashedPassword(passwordEncoder.encode("lazar"));
                admin.setProfilePictureUrl(
                        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=128&h=128&q=80");
//            admin.setRoles(Set.of(Role.USER, Role.ADMIN));
                admin.setRoles(Collections.singleton(Role.ADMIN));
                userRepository.save(admin);
            }

            saveCeilingIfNotExists("Red");
            saveCeilingIfNotExists("Yellow");
            saveCeilingIfNotExists("Blue");

            saveSizeIfNotExists("Small");
            saveSizeIfNotExists("Wide");
            saveSizeIfNotExists("Extra Wide");

            saveWallIfNotExists("Wood");
            saveWallIfNotExists("Stone");
            saveWallIfNotExists("Brick");

            if (houseRepository.count() < 1) {
                House house = new House();
                house.setCeiling(ceilingRepository.getCeilingByType("blue").orElse(null));
//                house.setCeiling_color(Integer.toHexString(Objects.requireNonNull(getColorByName("blue")).getRGB()).substring(2));
                house.setSize(sizeRepository.getSizeByType("Extra Wide").orElse(null));
                house.setWall(wallRepository.getWallByType("Wood").orElse(null));
                houseRepository.save(house);
            }


            if (personRepository.count() < 10) {
                personRepository.saveAll(FakeGenerator.getInstance().generateRandomPersons(10));
            }

            generateHouseImages();

            log.info("Generated data");

        };
    }

    public boolean hasUser(String username) {
        return userRepository.findByUsername(username) != null;
    }

    public void saveCeilingIfNotExists(String type) {
        if (ceilingRepository.getCeilingByType(type).isEmpty()) {
            ceilingRepository.save(new Ceiling(0L, type));
        }
    }

    public void saveSizeIfNotExists(String type) {
        if (sizeRepository.getSizeByType(type).isEmpty()) {
            sizeRepository.save(new Size(0L, type));
        }
    }

    public void saveWallIfNotExists(String type) {
        if (wallRepository.getWallByType(type).isEmpty()) {
            wallRepository.save(new Wall(0L, type));
        }
    }

    @SneakyThrows
    public void generateHouseImages() {
        File directory = new File("/tmp/Doodleverse/images/");
        directory.mkdirs();
        File image;
        BufferedImage bufferedImage;

        /*
        Color your_color = new Color(128,128,128);
        String hex = "#"+Integer.toHexString(your_color.getRGB()).substring(2);
         */

        for (House house : houseRepository.findAll()) {

            image = new File("/tmp/Doodleverse/images/house_" + house.getCeiling().getType().toLowerCase() + "_" +
                    house.getSize().getType().toLowerCase().replaceAll(" ", "") + "_" + house.getWall().getType().toLowerCase() + "jpg");
            bufferedImage = new BufferedImage(1000, 1000, Image.SCALE_SMOOTH);

            if (image.exists()) continue;

            Graphics2D graphics2D = bufferedImage.createGraphics();

            graphics2D.setPaint(new Color(255, 255, 255));
            graphics2D.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());


            Color ceilingColor = getColorByName(house.getCeiling().getType());
            graphics2D.setPaint(ceilingColor);
            Shape ceiling = new Polygon(new int[]{500, 200, 800}, new int[]{100, 200, 200}, 3);
            graphics2D.fill(ceiling);

            ImageIO.write(bufferedImage, "jpg", image);

        }

    }

    private Color getColorByName(String name) {
        try {
            return (Color)Color.class.getField(name.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

}