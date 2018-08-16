package com.sunforge.logic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.sunforge.App;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DecklistParser {

    private static String jsonM19 = null;
    private static String jsonDAR = null;
    private static String jsonRIX = null;
    private static String jsonXLN = null;
    private static String jsonHOU = null;
    private static String jsonAKH = null;
    private static String jsonAER = null;
    private static String jsonKLD = null;
    private static String jsonW17 = null;

    public static Map<Card, Integer> parse(String givenArea) {
        initJSONS();
        List<String> listOfCards = Arrays.asList(givenArea.split("\n"));

        //TODO AKH IS BROKEN SOMEHOW
        System.out.println(Arrays.toString(returnCardTypes(SetsEnum.Sets.AKH, 1)));

        int landNumber = 0;
        int artifactNumber = 0;
        int creatureNumber = 0;
        int sorceryNumber = 0;
        int instantNumber = 0;
        int planeswalkerNumber = 0;

        for (String currentEntry : listOfCards) {
            currentEntry = currentEntry.trim();
            //TODO Proper validation
            if (currentEntry.length() > 0) {


                int firstIndexSpace = currentEntry.indexOf(" ");
                int indexOfBracket = currentEntry.indexOf("(");
                int cardQuantity = Integer.parseInt(currentEntry.substring(0, firstIndexSpace));
                String cardName = currentEntry.substring(firstIndexSpace, indexOfBracket - 1);
                String cardSet = currentEntry.substring(indexOfBracket + 1, currentEntry.indexOf(")"));
                int cardID = Integer.parseInt(currentEntry.substring(currentEntry.lastIndexOf(" ") + 1));
                String[] currentCardTypes = returnCardTypes(SetsEnum.Sets.valueOf(cardSet), cardID);


                System.out.println(cardQuantity + "," + cardName + "," + cardSet + "," + cardID);
                System.out.println(Arrays.toString(currentCardTypes));

                for(String currentCardType: currentCardTypes){
                    switch (currentCardType){
                        case "Land":
                            landNumber += cardQuantity;
                            break;
                        case "Artifact":
                            artifactNumber += cardQuantity;
                            break;
                        case "Creature":
                            creatureNumber += cardQuantity;
                            break;
                        case "Sorcery":
                            sorceryNumber += cardQuantity;
                            break;
                        case "Instant":
                            instantNumber += cardQuantity;
                            break;
                        case "Planeswalker":
                            planeswalkerNumber += cardQuantity;
                            break;
                    }
                }
            }
        }
        System.out.println("Land: " + landNumber);
        System.out.println("Artifact: " + artifactNumber);
        System.out.println("Creature: " + creatureNumber);
        System.out.println("Sorcery: " + sorceryNumber);
        System.out.println("Instant: " + instantNumber);
        System.out.println("Planeswalker: " + planeswalkerNumber);

        BufferedImage myPicture = new BufferedImage(1205, 1450, 1);
        Graphics2D g = (Graphics2D) myPicture.getGraphics();
        g.setStroke(new BasicStroke(3));
        g.setColor(Color.BLUE);
        g.drawRect(10, 10, myPicture.getWidth() - 20, myPicture.getHeight() - 20);

        String savingPath = null;
        try {
            savingPath = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            savingPath = savingPath.substring(1).substring(0, savingPath.lastIndexOf("/"));
            savingPath += "output.jpg";
            ImageIO.write(myPicture, "jpg", new File(savingPath));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void initJSONS() {
        String pathToJARDir;
        try {
            pathToJARDir = App.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            pathToJARDir = pathToJARDir.substring(1).substring(0, pathToJARDir.lastIndexOf("/"));
            jsonM19 = pathToJARDir + "jsons/m19.json";
            jsonDAR = pathToJARDir + "jsons/dar.json";
            jsonRIX = pathToJARDir + "jsons/rix.json";
            jsonXLN = pathToJARDir + "jsons/xln.json";
            jsonHOU = pathToJARDir + "jsons/hou.json";
            jsonAKH = pathToJARDir + "jsons/akh.json";
            jsonAER = pathToJARDir + "jsons/aer.json";
            jsonKLD = pathToJARDir + "jsons/kld.json";
            jsonW17 = pathToJARDir + "jsons/w17.json";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static String[] returnCardTypes(SetsEnum.Sets givenSet, int givenCode) {
        String[] cardTypes = null;
        switch (givenSet) {
            case M19: {
                cardTypes = readFromJSON(jsonM19, givenCode);
                break;
            }
            case DAR: {
                cardTypes = readFromJSON(jsonDAR, givenCode);
                break;
            }
            case RIX: {
                cardTypes = readFromJSON(jsonRIX, givenCode);
                break;
            }
            case XLN: {
                cardTypes = readFromJSON(jsonXLN, givenCode-1);
                break;
            }
            case HOU: {
                cardTypes = readFromJSON(jsonHOU, givenCode);
                break;
            }
            case AKH: {
                cardTypes = readFromJSON(jsonAKH, givenCode);
                break;
            }
            case AER: {
                cardTypes = readFromJSON(jsonAER, givenCode);
                break;
            }
            case KLD: {
                cardTypes = readFromJSON(jsonKLD, givenCode);
                break;
            }
            case W17: {
                cardTypes = readFromJSON(jsonW17, givenCode);
                break;
            }
        }

        return cardTypes;
    }

    private static String[] readFromJSON(String givenPathToJSON, int givenCode) {
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = gson.newJsonReader(new FileReader(givenPathToJSON));
            jsonReader.setLenient(true);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            JsonArray typesJson = jsonObject.getAsJsonArray("cards").get(givenCode - 1).getAsJsonObject().get("types").getAsJsonArray();
            String[] cardTypes = new String[typesJson.size()];
            for (int currentIndex = 0; currentIndex < typesJson.size(); currentIndex++) {
                cardTypes[currentIndex] = typesJson.get(currentIndex).getAsString();
            }
            return cardTypes;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new String[]{};
    }
}
