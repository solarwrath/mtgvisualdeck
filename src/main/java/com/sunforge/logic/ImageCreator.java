package com.sunforge.logic;

import com.sunforge.App;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImageCreator {
    private static Logger log = Logger.getLogger(ImageCreator.class.getName());

    public static void createOutput(String pathToOutput, SortedMap<Card, Integer> givenMapOfCards) {
        BufferedImage myPicture = new BufferedImage(3000, 3000, 1);

        String pathToCurrentDirectory = null;
        try {
            pathToCurrentDirectory = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            pathToCurrentDirectory = pathToCurrentDirectory.substring(1).substring(0, pathToCurrentDirectory.lastIndexOf("/"));

            BufferedImage currentIteratedCardImage = null;

            int currentRow = 1;
            int currentCol = 1;

            int currentX = 0;
            int currentY = 0;


            Graphics2D g = (Graphics2D) myPicture.getGraphics();

            for (Map.Entry<Card, Integer> currentPair : givenMapOfCards.entrySet()) {

                Card currentCard = currentPair.getKey();
                try{
                    //TODO Implement distributing them into cells
                    //Get image from file
                    currentIteratedCardImage = ImageIO.read(new File(pathToCurrentDirectory + "cardImages/" + currentCard.getSet() + "/" + currentCard.getNumber() + ".jpg"));

                    //Calculate initial position. Because original files are not beautifully sized those parameteres are not too.
                    currentX = 78 + (currentCol - 1) * 254;
                    currentY = 267 + (currentRow - 1) * 467;

                    for (int idx = 0; idx < currentPair.getValue(); idx++) {
                        //Draw that image and move forward so they stack
                        g.drawImage(currentIteratedCardImage, currentX, currentY, null);
                        currentY += 39;
                    }
                }catch (IIOException e){
                    log.log(Level.SEVERE, pathToCurrentDirectory + "cardImages/" + currentCard.getSet() + "/" + currentCard.getNumber() + ".jpg");
                    log.log(Level.SEVERE, "There is no such file", e);
                }

                if (currentCol == 5) {
                    currentCol = 1;
                    currentRow++;
                } else {
                    currentCol++;
                }

            }

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
