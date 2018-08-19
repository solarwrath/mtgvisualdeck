package com.sunforge.logic;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Logger;

import static com.sunforge.logic.BoardUtils.getTypesFromBoard;
import static com.sunforge.logic.BoardUtils.joinBoardTypes;
import static com.sunforge.logic.DecklistParser.parse;

public class Manager {

    private static Logger log = Logger.getLogger(Manager.class.getName());
    public static void processData(String givenDeck){

        //Splitting the deck into main and side boards. 0 is mainboard. 1 is sideboard.
        List<String> mainBoard = DeckSplitter.splitDeck(givenDeck).get(0);
        List<String> sideBoard = DeckSplitter.splitDeck(givenDeck).get(1);

        //TODO Need to predict the size of document
        //Get the Map of Cards and Quantities from String lines
        SortedMap<Card, Integer> parsedMainBoard = (SortedMap<Card, Integer>) parse(mainBoard);
        SortedMap<Card, Integer> parsedSideBoard = (SortedMap<Card, Integer>) parse(sideBoard);

        //Get board types to display. I separate the deck into two piles so need to call it for both and then join them

        Map<CardType, Integer> mainBoardTypes = getTypesFromBoard(parsedMainBoard);
        Map<CardType, Integer> sideBoardTypes = getTypesFromBoard(parsedSideBoard);

        Map<CardType, Integer> finalTypes = joinBoardTypes(mainBoardTypes, sideBoardTypes);

        //
        Object[] rspMainBoard = ImageCreator.createImageAndGetRows(parsedMainBoard);
        BufferedImage mainBoardImage = (BufferedImage)rspMainBoard[0];


        //TODO Change this to draw sideboard
        Object[] rspSideBoard = ImageCreator.createImageAndGetRows(parsedSideBoard);
        BufferedImage sideBoardImage = (BufferedImage)rspSideBoard[0];

        BufferedImage backgroundImage = BackgroundCreator.createBackground(Math.max((int)rspMainBoard[1], (int)rspSideBoard[1]));


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
