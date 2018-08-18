package com.sunforge.logic;

import java.util.List;
import java.util.SortedMap;

public class Manager {
    public static void processData(String givenDeck){
        List<String> mainBoard = DeckSplitter.splitDeck(givenDeck).get(0);
        List<String> sideBoard = DeckSplitter.splitDeck(givenDeck).get(1);
        System.out.println(mainBoard.size());
        System.out.println(sideBoard.size());
        SortedMap<Card, Integer> parsedCards = (SortedMap<Card, Integer>) DecklistParser.parse(givenDeck);

    }
}
