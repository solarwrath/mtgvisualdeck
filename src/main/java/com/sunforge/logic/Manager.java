package com.sunforge.logic;

import java.util.List;
import java.util.SortedMap;
import java.util.logging.Logger;

public class Manager {

    private static Logger log = Logger.getLogger(Manager.class.getName());
    public static void processData(String givenDeck){

        //Splitting the deck into main and side boards.
        List<String> mainBoard = DeckSplitter.splitDeck(givenDeck).get(0);
        List<String> sideBoard = DeckSplitter.splitDeck(givenDeck).get(1);

        //TODO Need to predict the size of document
        SortedMap<Card, Integer> parsedMainBoard = (SortedMap<Card, Integer>) DecklistParser.parse(mainBoard);

        /*String pathToJARDir = null;
        try {
            log.info("Getting the path to current dir");
            pathToJARDir = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            pathToJARDir = pathToJARDir.substring(1).substring(0, pathToJARDir.lastIndexOf("/"));
            ImageCreator.createOutput(pathToJARDir+"output.jpg", parsedCards);
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, "Problem with path to current dir. Probably wrong formatting", e);
            e.printStackTrace();
        }*/


    }
}
