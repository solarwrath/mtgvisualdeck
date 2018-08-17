package com.sunforge.logic;

import com.sunforge.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.SortedMap;
import java.util.logging.Logger;

public class ImageCreator {
    private static Logger log = Logger.getLogger(ImageCreator.class.getName());

    public static void createOutput(String pathToOutput, SortedMap<Card, Integer> givenMapOfCards){
        BufferedImage myPicture = new BufferedImage(1205, 1450, 1);

        String pathToCurrentDirectory = null;
        try {
            pathToCurrentDirectory = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            pathToCurrentDirectory = pathToCurrentDirectory.substring(1).substring(0, pathToCurrentDirectory.lastIndexOf("/"));

            BufferedImage testCard = ImageIO.read(new File(pathToCurrentDirectory + "cardImages/AER/1.jpg"));
            Graphics2D g = (Graphics2D) myPicture.getGraphics();
            g.drawImage(testCard, 0, 0, null);

            try {
                String savingPath = pathToCurrentDirectory + "output.jpg";
                ImageIO.write(myPicture, "jpg", new File(savingPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
