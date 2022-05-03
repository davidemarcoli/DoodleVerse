package com.dala.utils;

import com.dala.data.building.ceiling.CeilingRepository;
import com.dala.data.building.house.House;
import com.dala.data.building.house.HouseRepository;
import com.dala.data.building.size.SizeRepository;
import com.dala.data.building.wall.WallRepository;
import com.dala.data.person.PersonRepository;
import com.dala.data.user.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

@Log4j2
public class HouseImageUtils {

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

    Random random = new Random();

    @SneakyThrows
    public void generateHouseImages() {

        /*
        Color your_color = new Color(128,128,128);
        String hex = "#"+Integer.toHexString(your_color.getRGB()).substring(2);
         */

        for (House house : houseRepository.findAll()) {
            generateImageByHouse(house);
        }

    }

    @SneakyThrows
    public String generateImageByHouse(House house) {

        File directory = new File("/tmp/Doodleverse/images/");
        directory.mkdirs();

        File image;
        BufferedImage bufferedImage;

        image = new File("/tmp/Doodleverse/images/" + getFileName(house));

        if (image.exists()) return image.getAbsolutePath();

        int width = 1000;
        int height = 1000;

        if (house.getSize().getWidth() != 0) {
            width = house.getSize().getWidth();
        }

        bufferedImage = new BufferedImage(width, height, Image.SCALE_SMOOTH);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setPaint(new Color(255, 255, 255));
        graphics2D.fillRect(0, 0, width, height);


        Color ceilingColor = Color.decode("#" + house.getCeilingColor());
        graphics2D.setPaint(ceilingColor);
        Shape ceiling = new Polygon(new int[]{width / 2, width / 100 * 15, width / 100 * 85}, new int[]{height / 10, height / 10 * 3, height / 10 * 3}, 3);
        graphics2D.fill(ceiling);

        InputStream textureIs = this.getClass().getClassLoader().getResourceAsStream("META-INF/resources/images/" + house.getWall().getType().toLowerCase() + ".jpg");
        assert textureIs != null;
        BufferedImage texture = ImageIO.read(textureIs);
        graphics2D.drawImage(texture, width / 5, height / 10 * 3, width / 5 * 4, height / 5 * 4, 0, 0, 1000, 1000, null);
        ImageIO.write(bufferedImage, "jpg", image);


        Color grassColor = Color.GREEN;
        graphics2D.setPaint(grassColor);

        log.debug("Width: " + width);

        for (int i = 0; i < width; i += random.nextInt(10)) {
            float randOffset = random.nextFloat(50);
            boolean isNegativeOffset = random.nextBoolean();
            int grassHeight = MathUtils.getInstance().randomMinMax(100, 200);

            log.debug("Grass Height: " + grassHeight);
            log.debug("Random Offset: " + randOffset);
            log.debug("Is Negative Offset: " + isNegativeOffset);

            graphics2D.fill(new Line2D.Float(i, bufferedImage.getHeight(), i + (isNegativeOffset ? randOffset * -1 : randOffset), bufferedImage.getHeight() - grassHeight));
            log.debug("Drawing grass");
        }

        return image.getAbsolutePath();
    }

    public Color getColor(String color) {

        try {
            String[] colorSplit = color.replaceAll(" ", "").split(",");
            return new Color(Integer.parseInt(colorSplit[0]), Integer.parseInt(colorSplit[1]), Integer.parseInt(colorSplit[2]));
        } catch (Exception ignore) {
        }

        try {
            System.out.println(Integer.toHexString(Integer.parseInt(color)));
        } catch (NumberFormatException ignore) {
        }

        try {
            return (Color) Color.class.getField(color.toUpperCase()).get(null);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ignore) {
        }

        if (color.length() == 3) {
            StringBuilder extendedHex = new StringBuilder();

            for (Character c : color.toCharArray()) {
                extendedHex.append(c).append(c);
            }

            color = extendedHex.toString();
        }
        if (!color.startsWith("#"))
            color = "#" + color;


        try {
            return Color.decode(color);
        } catch (Exception ignore) {
        }


        return null;
    }

    public String getFileName(House house) {
        return "house_" + house.getCeilingColor() + "_" +
                house.getSize().getType().toLowerCase().replaceAll(" ", "") + "_" +
                house.getWall().getType().toLowerCase() + ".jpg";
    }

    private static HouseImageUtils instance;

    private HouseImageUtils() {
    }

    @Bean
    public static HouseImageUtils getInstance() {
        if (instance == null) {
            instance = new HouseImageUtils();
        }

        return instance;
    }
}
