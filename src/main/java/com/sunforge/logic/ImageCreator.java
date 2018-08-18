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

        String pathToCurrentDirectory;
        try {
            pathToCurrentDirectory = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            pathToCurrentDirectory = pathToCurrentDirectory.substring(1).substring(0, pathToCurrentDirectory.lastIndexOf("/"));

            BufferedImage currentIteratedCardImage;

            int currentRow = 1, currentCol = 1, cardsInCell = 0, currentX, currentY;

            Graphics2D g = (Graphics2D) myPicture.getGraphics();

            log.info("Staring to iterating through the cards");

            for (Map.Entry<Card, Integer> currentPair : givenMapOfCards.entrySet()) {

                Card currentCard = currentPair.getKey();

                try {

                    //Checkout if can slot ALL these cards in the previous cell

                   if (4 - currentPair.getValue() - cardsInCell < 0) {
                        cardsInCell = 0;
                        if (currentCol == 5) {
                            currentCol = 1;
                            currentRow++;
                        } else {
                            currentCol++;
                        }
                    }

                    //Get image from file

                    currentIteratedCardImage = ImageIO.read(new File(pathToCurrentDirectory + "cardImages/" + currentCard.getSet() + "/" + currentCard.getNumber() + ".jpg"));

                    //Calculate initial position. Because original files are not beautifully sized those parameters are not too.
                    currentX = calculateX(currentCol);
                    currentY = calculateY(currentRow, cardsInCell);

                    for (int idx = 0; idx < currentPair.getValue(); idx++) {

                        //Draw that image and move forward so they stack

                        g.drawImage(currentIteratedCardImage, currentX, currentY, null);

                        //Move next image, so that it stacks

                        currentY += 39;
                        cardsInCell++;

                        if (cardsInCell == 4) {
                            cardsInCell = 0;
                            if (currentCol == 5) {
                                currentCol = 1;
                                currentRow++;
                            } else {
                                currentCol++;
                            }

                            //Recalculate coordinates when changing the cell

                            currentX = calculateX(currentCol);
                            currentY = calculateY(currentRow, cardsInCell);
                        }
                    }
                } catch (IIOException e) {
                    log.log(Level.SEVERE, "There is no such file: " + pathToCurrentDirectory + "cardImages/" + currentCard.getSet() + "/" + currentCard.getNumber() + ".jpg", e);
                }
            }

            log.info("Finished creating the image. Starting to save it...");

            //Saving the image

            try {
                String savingPath = pathToCurrentDirectory + "output.jpg";
                ImageIO.write(myPicture, "jpg", new File(savingPath));

                log.info("Saved the output image!");
            } catch (IOException e) {
                log.log(Level.SEVERE, "There is an error with outputting!", e);
            }
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, "Problem with path to current dir. Probably wrong formatting", e);
        } catch (IOException e) {
            //TODO Delete this and change the current dir savepath to the given by user when everything else is done
            log.log(Level.SEVERE, "There is an error with getting current dir path!", e);
        }
    }

    private static int calculateX(int givenCol) {
        return 78 + (givenCol - 1) * 254;
    }

    private static int calculateY(int givenRow, int givenCardsInCell) {
        return 267 + (givenRow - 1) * 467 + 39 * givenCardsInCell;
    }
}
