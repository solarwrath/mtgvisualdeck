package com.sunforge.logic;

import java.util.ArrayList;
import java.util.List;

public class DeckSplitter {

    public static List<List<String>> splitDeck(String givenDeckList) {
        List<String> mainBoard = new ArrayList<>();
        List<String> sideBoard = new ArrayList<>();
        boolean foundBreak = false;

        //Parsing all lines. Decides where to put the entry based on whether an empty string was found or not yet

        for (String currentLine : givenDeckList.split("\n")) {
            if(!foundBreak){
                if(currentLine.equals("")){
                    foundBreak = true;
                }
                else{
                    mainBoard.add(currentLine);
                }
            }
            else{
                sideBoard.add(currentLine);
            }
        }

        //Unftortunately, Java doesn't really offer us appropriate usage of Generics in Array, so need to use collections
        List<List<String>> result = new ArrayList<>();
        result.add(mainBoard);
        result.add(sideBoard);
        return result;
    }
}
