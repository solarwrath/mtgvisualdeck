package com.sunforge.logic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.sunforge.App;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    private static Logger log = Logger.getLogger(DecklistParser.class.getName());

    public static Map<Card, Integer> parse(String givenArea) {
        initJSONS();
        log.info("Initted json");

        List<String> listOfCards = Arrays.asList(givenArea.split("\n"));


        //TODO Create a special class to implement this. Also additional check for artifact/sorcery/instant/pw/lands
        SortedMap<Card, Integer> parsedCards = new TreeMap<>(Comparator.reverseOrder());

        //TODO AKH IS BROKEN SOMEHOW

        int landNumber = 0;
        int artifactNumber = 0;
        int creatureNumber = 0;
        int sorceryNumber = 0;
        int instantNumber = 0;
        int planeswalkerNumber = 0;

        log.info("Started consuming the data from given decklist");

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
                String cardManaCost = returnCardManaCost(SetsEnum.Sets.valueOf(cardSet), cardID);
                String[] cardTypes = returnCardTypes(SetsEnum.Sets.valueOf(cardSet), cardID);

                log.info("Parsed the data from given card");

                //Put a card object with quantity
                parsedCards.put(new CardBuilder().setName(cardName).setSet(cardSet).setNumber(cardID).setManaCost(cardManaCost).setTypes(cardTypes).createCard(), cardQuantity);

                log.info("Created a pair in map");

                //Increase counters of card type
                for (String currentCardType : cardTypes) {
                    switch (currentCardType) {
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

        log.info("Finished consuming the data from the given decklist");

        for (Map.Entry<Card, Integer> currentPair : parsedCards.entrySet()) {
            System.out.println("Quantity: " + currentPair.getValue());
            System.out.println(currentPair.getKey().toString());
            System.out.println("---------------------------------");
        }

        /*BufferedImage myPicture = new BufferedImage(1205, 1450, 1);

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
        }*/

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
            log.log(Level.SEVERE, "Can't find such path. Probably, URI is wrong.", e);
            e.printStackTrace();
        }
    }

    private static String[] returnCardTypes(SetsEnum.Sets givenSet, int givenCode) {
        String[] cardTypes = null;
        switch (givenSet) {
            case M19: {
                cardTypes = readTypes(jsonM19, givenCode);
                break;
            }
            case DAR: {
                cardTypes = readTypes(jsonDAR, givenCode);
                break;
            }
            case RIX: {
                cardTypes = readTypes(jsonRIX, givenCode);
                break;
            }
            case XLN: {
                cardTypes = readTypes(jsonXLN, givenCode - 1);
                break;
            }
            case HOU: {
                cardTypes = readTypes(jsonHOU, givenCode);
                break;
            }
            case AKH: {
                cardTypes = readTypes(jsonAKH, givenCode);
                break;
            }
            case AER: {
                cardTypes = readTypes(jsonAER, givenCode);
                break;
            }
            case KLD: {
                cardTypes = readTypes(jsonKLD, givenCode);
                break;
            }
            case W17: {
                cardTypes = readTypes(jsonW17, givenCode);
                break;
            }
        }

        return cardTypes;
    }

    private static String returnCardManaCost(SetsEnum.Sets givenSet, int givenCode) {
        String cardManaCost = null;
        switch (givenSet) {
            case M19: {
                cardManaCost = readManaCost(jsonM19, givenCode);
                break;
            }
            case DAR: {
                cardManaCost = readManaCost(jsonDAR, givenCode);
                break;
            }
            case RIX: {
                cardManaCost = readManaCost(jsonRIX, givenCode);
                break;
            }
            case XLN: {
                cardManaCost = readManaCost(jsonXLN, givenCode - 1);
                break;
            }
            case HOU: {
                cardManaCost = readManaCost(jsonHOU, givenCode);
                break;
            }
            case AKH: {
                cardManaCost = readManaCost(jsonAKH, givenCode);
                break;
            }
            case AER: {
                cardManaCost = readManaCost(jsonAER, givenCode);
                break;
            }
            case KLD: {
                cardManaCost = readManaCost(jsonKLD, givenCode);
                break;
            }
            case W17: {
                cardManaCost = readManaCost(jsonW17, givenCode);
                break;
            }
        }

        return cardManaCost;
    }

    private static String[] readTypes(String givenPathToJSON, int givenCode) {
        return readFromJSONArray(givenPathToJSON, "types", givenCode);
    }

    private static String readManaCost(String givenPathToJson, int givenCode) {
        return readFromJSONPrimitive(givenPathToJson, "manaCost", givenCode);
    }

    private static String[] readFromJSONArray(String givenPathToJSON, String fieldName, int givenCode) {
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = gson.newJsonReader(new FileReader(givenPathToJSON));
            jsonReader.setLenient(true);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            JsonArray resultArray = jsonObject.getAsJsonArray("cards").get(givenCode - 1).getAsJsonObject().get(fieldName).getAsJsonArray();
            String[] cardTypes = new String[resultArray.size()];
            for (int currentIndex = 0; currentIndex < resultArray.size(); currentIndex++) {
                cardTypes[currentIndex] = resultArray.get(currentIndex).getAsString();
            }
            return cardTypes;

        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "There is no such file.", e);
            e.printStackTrace();
        }
        return new String[]{};
    }

    private static String readFromJSONPrimitive(String givenPathToJSON, String fieldName, int givenCode) {
        System.out.println(givenCode + ", " + givenPathToJSON);
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = gson.newJsonReader(new FileReader(givenPathToJSON));
            jsonReader.setLenient(true);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            return jsonObject.getAsJsonArray("cards").get(givenCode - 1).getAsJsonObject().get(fieldName).getAsString();
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "There is no such file.", e);
        } catch (NullPointerException e) {
            log.log(Level.WARNING, "There is no such field.", e);
        }
        return "";
    }
}
