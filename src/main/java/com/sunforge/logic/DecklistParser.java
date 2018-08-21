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
        SortedMap<Card, Integer> parsedCards = new TreeMap<>(Comparator.reverseOrder());

        int landNumber = 0;
        int artifactNumber = 0;
        int creatureNumber = 0;
        int sorceryNumber = 0;
        int instantNumber = 0;
        int planeswalkerNumber = 0;

        log.info("Started consuming the data from given decklist");

        for (String currentEntry : listOfCards) {

            currentEntry = currentEntry.trim();
            if (currentEntry.length() > 0) {

                int firstIndexSpace = currentEntry.indexOf(" ");
                int indexOfBracket = currentEntry.indexOf("(");

                int cardQuantity = Integer.parseInt(currentEntry.substring(0, firstIndexSpace));

                String cardName = currentEntry.substring(firstIndexSpace, indexOfBracket - 1);
                SetType cardSet = SetType.valueOf(currentEntry.substring(indexOfBracket + 1, currentEntry.indexOf(")")));
                int cardID = Integer.parseInt(currentEntry.substring(currentEntry.lastIndexOf(" ") + 1));
                Set<CardType> cardTypes = returnCardTypes(cardSet, cardID);

                //Lands do not have this
                List<ColorType> cardColorIdentity = null;
                int cardCMC = -1;
                String cardManaCost = null;

                if(!cardTypes.contains(CardType.LAND)){
                    cardColorIdentity = returnCardColorIdentity(cardSet, cardID);
                    cardCMC = returnCardCMC(cardSet, cardID);
                    cardManaCost = returnCardManaCost(cardSet, cardID);
                }

                log.info("Parsed the data from given card");

                //Put a card object with quantity
                parsedCards.put(new CardBuilder().setName(cardName).setSet(cardSet).setNumber(cardID).setCMC(cardCMC).setManaCost(cardManaCost).setColorIdentity(cardColorIdentity).setTypes(cardTypes).createCard(), cardQuantity);

                log.info("Created a pair in map");

                //Increase counters of card type
            }
        }

        //TODO AKH 210-224 card images are missing
        //TODO Overall, aftermath is broken because its number is like XXXa and XXXb. FIX IT
        log.info("Finished consuming the data from the given decklist");

        return parsedCards;
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

    private static List<ColorType> returnCardColorIdentity(SetType givenSet, int givenCode) {
        List<ColorType> cardColorIdentity = null;
        switch (givenSet) {
            case M19: {
                cardColorIdentity = readColorIdentity(jsonM19, givenCode);
                break;
            }
            case DAR: {
                cardColorIdentity = readColorIdentity(jsonDAR, givenCode);
                break;
            }
            case RIX: {
                cardColorIdentity = readColorIdentity(jsonRIX, givenCode);
                break;
            }
            case XLN: {
                cardColorIdentity = readColorIdentity(jsonXLN, givenCode - 1);
                break;
            }
            case HOU: {
                cardColorIdentity = readColorIdentity(jsonHOU, givenCode);
                break;
            }
            case AKH: {
                cardColorIdentity = readColorIdentity(jsonAKH, givenCode);
                break;
            }
            case AER: {
                cardColorIdentity = readColorIdentity(jsonAER, givenCode);
                break;
            }
            case KLD: {
                cardColorIdentity = readColorIdentity(jsonKLD, givenCode);
                break;
            }
            case W17: {
                cardColorIdentity = readColorIdentity(jsonW17, givenCode);
                break;
            }
        }

        return cardColorIdentity;
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

    private static List<ColorType> readColorIdentity(String givenPathToJSON, int givenCode) {
        List<ColorType> resultColorIdentity = new ArrayList<>();
        String[] colorIdentityFromJSON = readFromJSONArray(givenPathToJSON, "colorIdentity", givenCode);

        for (String currentColor : colorIdentityFromJSON) {
            //I don't wanna store colors as Strings and for them to be of nice name, I need the cases instead of
            //ColorTYpe.valueOf()
            switch (currentColor) {
                case "W":
                    resultColorIdentity.add(ColorType.WHITE);
                    break;
                case "U":
                    resultColorIdentity.add(ColorType.BLUE);
                    break;
                case "R":
                    resultColorIdentity.add(ColorType.RED);
                    break;
                case "B":
                    resultColorIdentity.add(ColorType.BLACK);
                    break;
                case "G":
                    resultColorIdentity.add(ColorType.GREEN);
                    break;
                default:
                    log.severe("There is no such color identity! json:" + givenPathToJSON + ". Code: " + givenCode);
                    break;
            }
        }

        return resultColorIdentity;
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
        } catch (NullPointerException e){
            //Lands do not have colorIdentity
            if(!fieldName.equals("colorIdentity")){
                log.log(Level.SEVERE, "Color identity is missing.", e);
            }
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

            //Checking if trying to get manaCost of land, because that is not an appropriate error to log
            //Only for manaCost because now using this method only to get it. Later expand if needed, which is unlikely
            if (!(readTypes(givenPathToJSON, givenCode).contains(CardType.LAND) && fieldName.equals("manaCost"))) {
                log.log(Level.WARNING, "There is no such field.", e);
            }
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
