package com.gmail.smaglenko.talkandtravel.service.impl;

import com.gmail.smaglenko.talkandtravel.service.ImageService;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {
    private static final int INDEX = 0;
    private static final int IMAGE_X = 0;
    private static final int IMAGE_Y = 0;
    private static final int IMAGE_WIDTH = 200;
    private static final int IMAGE_HEIGHT = 200;
    private static final int OVAL_WIDTH = 100;
    private static final int OVAL_HEIGHT = 100;
    private static final int OVAL_X = 50;
    private static final int OVAL_Y = 50;
    private static final String FONT_NAME = "Arial";
    private static final int FONT_SIZE = 50;

    @Override
    public byte[] generateImage(String name) throws IOException {
        char firstLetterOfName = name.charAt(INDEX);
        var image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        var graphics = getGraphics2D(image);
        var xAndYCoordinatesOfFirstLetterOfName
                = calculateXAndYCoordinates(graphics, firstLetterOfName);
        int xCoordinateOfLatter = xAndYCoordinatesOfFirstLetterOfName[0];
        int yCoordinateOfLatter = xAndYCoordinatesOfFirstLetterOfName[1];
        graphics.drawString(
                String.valueOf(firstLetterOfName), xCoordinateOfLatter, yCoordinateOfLatter
        );
        graphics.dispose();
        var byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private Graphics2D getGraphics2D(BufferedImage image) {
        var graphics = image.createGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(IMAGE_X, IMAGE_Y, IMAGE_WIDTH, IMAGE_HEIGHT);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillOval(OVAL_X, OVAL_Y, OVAL_WIDTH, OVAL_HEIGHT);
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
        return graphics;
    }

    private int[] calculateXAndYCoordinates(Graphics2D graphics, char letter) {
        int[] xAndYCoordinates = new int[2];
        FontMetrics fontMetrics = graphics.getFontMetrics();
        xAndYCoordinates[0] = (IMAGE_WIDTH - fontMetrics.charWidth(letter)) / 2;
        xAndYCoordinates[1] = (IMAGE_HEIGHT + fontMetrics.getAscent() - fontMetrics.getDescent()) / 2;
        return xAndYCoordinates;
    }
}
