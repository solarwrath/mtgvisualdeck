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

    public static Map<Card, Integer> parse(List<String> listOfCards) {
        initJSONS();
        log.info("Initted json");

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
                SetType cardSet = SetType.valueOf(currentEntry.substring(indexOfBracket + 1, currentEntry.indexOf(")")));
                int cardID = Integer.parseInt(currentEntry.substring(currentEntry.lastIndexOf(" ") + 1));
                int cardCMC = returnCardCMC(cardSet, cardID);
                String cardManaCost = returnCardManaCost(cardSet, cardID);
                Set<CardType> cardTypes = returnCardTypes(cardSet, cardID);

                log.info("Parsed the data from given card");

                //Put a card object with quantity
                parsedCards.put(new CardBuilder().setName(cardName).setSet(cardSet).setNumber(cardID).setCMC(cardCMC).setManaCost(cardManaCost).setTypes(cardTypes).createCard(), cardQuantity);

                log.info("Created a pair in map");

                //Increase counters of card type
            }
        }

        log.info("Finished consuming the data from the given decklist");

        return parsedCards;
    }

    public static Map<CardType, Integer> getTypesFromDeck(SortedMap<Card, Integer> givenMap) {

        //No inline initializing for Map in Java 8 that is the latest for JavaFX wrapper, unfortunately, so need to
        //add items manually
        Map<CardType, Integer> analyzedTypes = new HashMap<CardType, Integer>();

        analyzedTypes.put(CardType.CREATURE, 0);
        analyzedTypes.put(CardType.INSTANT, 0);
        analyzedTypes.put(CardType.SORCERY, 0);
        analyzedTypes.put(CardType.ARTIFACT, 0);
        analyzedTypes.put(CardType.ENCHANTMENT, 0);
        analyzedTypes.put(CardType.PLANESWALKER, 0);
        analyzedTypes.put(CardType.LAND, 0);

        for (Map.Entry<Card, Integer> currentPair : givenMap.entrySet()) {
            for (CardType currentCardType : currentPair.getKey().getTypes()) {
                //S
                if (currentCardType.equals(CardType.CREATURE)) {
                    analyzedTypes.replace(CardType.CREATURE, analyzedTypes.get(CardType.CREATURE) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.INSTANT)) {
                    analyzedTypes.replace(CardType.INSTANT, analyzedTypes.get(CardType.INSTANT) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.SORCERY)) {
                    analyzedTypes.replace(CardType.SORCERY, analyzedTypes.get(CardType.SORCERY) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.ENCHANTMENT)) {
                    analyzedTypes.replace(CardType.ENCHANTMENT, analyzedTypes.get(CardType.ENCHANTMENT) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.ARTIFACT)) {
                    analyzedTypes.replace(CardType.ARTIFACT, analyzedTypes.get(CardType.ARTIFACT) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.PLANESWALKER)) {
                    analyzedTypes.replace(CardType.PLANESWALKER, analyzedTypes.get(CardType.PLANESWALKER) + 1);
                    break;
                }
                if (currentCardType.equals(CardType.LAND)) {
                    analyzedTypes.replace(CardType.LAND, analyzedTypes.get(CardType.LAND) + 1);
                    break;
                }
            }
        }
        return analyzedTypes;
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

    private static Set<CardType> returnCardTypes(SetType givenSet, int givenCode) {
        Set<CardType> cardTypes = null;
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

    private static int returnCardCMC(SetType givenSet, int givenCode) {
        int cardCMC = 0;
        switch (givenSet) {
            case M19: {
                cardCMC = readCMC(jsonM19, givenCode);
                break;
            }
            case DAR: {
                cardCMC = readCMC(jsonDAR, givenCode);
                break;
            }
            case RIX: {
                cardCMC = readCMC(jsonRIX, givenCode);
                break;
            }
            case XLN: {
                cardCMC = readCMC(jsonXLN, givenCode - 1);
                break;
            }
            case HOU: {
                cardCMC = readCMC(jsonHOU, givenCode);
                break;
            }
            case AKH: {
                cardCMC = readCMC(jsonAKH, givenCode);
                break;
            }
            case AER: {
                cardCMC = readCMC(jsonAER, givenCode);
                break;
            }
            case KLD: {
                cardCMC = readCMC(jsonKLD, givenCode);
                break;
            }
            case W17: {
                cardCMC = readCMC(jsonW17, givenCode);
                break;
            }
        }

        return cardCMC;
    }

    private static String returnCardManaCost(SetType givenSet, int givenCode) {
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

    private static Set<CardType> readTypes(String givenPathToJSON, int givenCode) {
        Set<CardType> resultTypes = new HashSet<>();
        String[] typesFromJSON = readFromJSONArray(givenPathToJSON, "types", givenCode);

        for (String currentType : typesFromJSON) {
            //Cards in json are capitalised, while Enums should be uppercased. Gotta call toUpperCase() to avoid
            //savage switch statement
            resultTypes.add(CardType.valueOf(currentType.toUpperCase()));
        }

        return resultTypes;
    }

    private static int readCMC(String givenPathToJson, int givenCode) {
        return readFromJSONPrimitiveInteger(givenPathToJson, "cmc", givenCode);
    }

    private static String readManaCost(String givenPathToJson, int givenCode) {
        return readFromJSONPrimitiveString(givenPathToJson, "manaCost", givenCode);
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

    private static String readFromJSONPrimitiveString(String givenPathToJSON, String fieldName, int givenCode) {
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
            //TODO Check for land
            log.log(Level.WARNING, "There is no such field.", e);
        }
        return "";
    }

    private static int readFromJSONPrimitiveInteger(String givenPathToJSON, String fieldName, int givenCode) {
        Gson gson = new Gson();
        JsonReader jsonReader;
        try {
            jsonReader = gson.newJsonReader(new FileReader(givenPathToJSON));
            jsonReader.setLenient(true);
            JsonObject jsonObject = gson.fromJson(jsonReader, JsonObject.class);
            return jsonObject.getAsJsonArray("cards").get(givenCode - 1).getAsJsonObject().get(fieldName).getAsInt();
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, "There is no such file.", e);
        } catch (NullPointerException e) {
            log.log(Level.WARNING, "There is no such field.", e);
        }
        return 0;
    }
}
