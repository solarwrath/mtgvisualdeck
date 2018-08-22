package com.sunforge.logic;

import com.sunforge.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HeaderCreator implements ClipboardOwner {

    private Logger log = Logger.getLogger(HeaderCreator.class.getName());

    public BufferedImage createHeader(Stats givenStats) {

        //Caching data
        Card givenPreviewCard = givenStats.getPreviewCard();
        String givenTitle = givenStats.getTitle();

        //TODO Adjust the numbers
        BufferedImage headerImage = new BufferedImage(1920, 400, 1);
        Graphics2D headerGraphics = ((Graphics2D) headerImage.getGraphics());

        //Drawing top background(Title, Author etc..

        headerGraphics.setColor(new Color(243, 243, 243));
        headerGraphics.fillRect(357, 0, 1563, 185);
        log.info("Drew chosen card");
        log.info("Drew the top background");

        //Drawing card image

        try {
            BufferedImage previewCardImage = ImageIO.read(new File(App.pathToCurrentDirectory + "cardPreviews/" + givenPreviewCard.getSet() + "/" + givenPreviewCard.getNumber() + ".jpg"));

            //Clipping it in the form

            headerGraphics.clip(new Polygon(new int[]{0, 411, 357, 0}, new int[]{0, 0, 300, 300}, 4));
            headerGraphics.drawImage(previewCardImage, 0, 0, null);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Couldn't access given file: " + App.pathToCurrentDirectory + "cardPreviews/" + givenPreviewCard.getSet() + "/" + givenPreviewCard.getNumber() + ".jpg", e);
        }


        if(givenTitle != null){
            headerGraphics.drawString(givenTitle, 460, 30);
        }

        TransferableImage trans = new TransferableImage(headerImage);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, this);

        return headerImage;
    }

    @Override
    public void lostOwnership(Clipboard clipboard, Transferable contents) {

    }

    private class TransferableImage implements Transferable {

        Image i;

        public TransferableImage(Image i) {
            this.i = i;
        }

        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException, IOException {
            if (flavor.equals(DataFlavor.imageFlavor) && i != null) {
                return i;
            } else {
                throw new UnsupportedFlavorException(flavor);
            }
        }

        public DataFlavor[] getTransferDataFlavors() {
            DataFlavor[] flavors = new DataFlavor[1];
            flavors[0] = DataFlavor.imageFlavor;
            return flavors;
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            DataFlavor[] flavors = getTransferDataFlavors();
            for (int i = 0; i < flavors.length; i++) {
                if (flavor.equals(flavors[i])) {
                    return true;
                }
            }

            return false;
        }
    }
}
