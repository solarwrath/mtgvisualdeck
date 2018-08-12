package com.sunforge.logic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DecklistParser {
    public static Map<Card, Integer> parse(String givenArea) {
        List<String> listOfCards = Arrays.asList(givenArea.split("\n"));
        for (String currentEntry : listOfCards) {
            currentEntry = currentEntry.trim();
            //TODO Proper validation
            Gson gson = new Gson();
            try {
                JsonReader jsonReader = gson.newJsonReader(new FileReader("D:\\JAVA-PROJECTS\\mtgvisualdeck\\target\\classes\\jsons\\m19.json"));
                jsonReader.setLenient(true);
                JsonArray jsonArray = gson.fromJson(jsonReader, JsonArray.class);
                System.out.println(jsonArray.get(0).getAsJsonObject().getAsJsonArray("cards").get(1).getAsJsonObject().get("types").getAsJsonArray().get(0).getAsString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (currentEntry.length() > 0) {
                int firstIndexSpace = currentEntry.indexOf(" ");
                int indexOfBracket = currentEntry.indexOf("(");
                int cardQuantity = Integer.parseInt(currentEntry.substring(0, firstIndexSpace));
                String cardName = currentEntry.substring(firstIndexSpace, indexOfBracket - 1);
                String cardSet = currentEntry.substring(indexOfBracket + 1, currentEntry.indexOf(")"));

                System.out.println(cardQuantity + ", " + cardName + ", " + cardSet);
            }
        }
        return null;
    }
}
