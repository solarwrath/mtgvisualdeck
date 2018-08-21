package com.sunforge.logic;

import java.util.HashMap;
import java.util.Map;

public class BoardUtils {

    public static Map<CardType, Integer> joinBoardTypes(Map<CardType, Integer> firstBoardTypes, Map<CardType, Integer> secondBoardTypes){
        Map<CardType, Integer> joinedMap = createTypesMap();

        for (Map.Entry<CardType, Integer> currentTypePair : firstBoardTypes.entrySet()) {
            joinedMap.replace(currentTypePair.getKey(), joinedMap.get(currentTypePair.getKey()) + currentTypePair.getValue());
        }

        for (Map.Entry<CardType, Integer> currentTypePair : secondBoardTypes.entrySet()) {
            joinedMap.replace(currentTypePair.getKey(), joinedMap.get(currentTypePair.getKey()) + currentTypePair.getValue());
        }

        return joinedMap;
    }

    public static Map<CardType, Integer> getTypesFromBoard(Map<Card, Integer> givenMap) {

        //No inline initializing for Map in Java 8 that is the latest for JavaFX wrapper, unfortunately, so need to
        //add items manually
        Map<CardType, Integer> analyzedTypes = createTypesMap();
        for (Map.Entry<Card, Integer> currentPair : givenMap.entrySet()) {
            int currentCardQuantity = currentPair.getValue();
            for (CardType currentCardType : currentPair.getKey().getTypes()) {
                if (currentCardType.equals(CardType.CREATURE)) {
                    analyzedTypes.replace(CardType.CREATURE, analyzedTypes.get(CardType.CREATURE) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.INSTANT)) {
                    analyzedTypes.replace(CardType.INSTANT, analyzedTypes.get(CardType.INSTANT) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.SORCERY)) {
                    analyzedTypes.replace(CardType.SORCERY, analyzedTypes.get(CardType.SORCERY) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.ARTIFACT)) {
                    analyzedTypes.replace(CardType.ARTIFACT, analyzedTypes.get(CardType.ARTIFACT) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.ENCHANTMENT)) {
                    analyzedTypes.replace(CardType.ENCHANTMENT, analyzedTypes.get(CardType.ENCHANTMENT) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.PLANESWALKER)) {
                    analyzedTypes.replace(CardType.PLANESWALKER, analyzedTypes.get(CardType.PLANESWALKER) + currentCardQuantity);
                    break;
                }
                if (currentCardType.equals(CardType.LAND)) {
                    analyzedTypes.replace(CardType.LAND, analyzedTypes.get(CardType.LAND) + currentCardQuantity);
                    break;
                }
            }
        }
        return analyzedTypes;
    }

    private static Map<CardType, Integer> createTypesMap(){
        Map<CardType, Integer> types = new HashMap<CardType, Integer>();

        types.put(CardType.CREATURE, 0);
        types.put(CardType.INSTANT, 0);
        types.put(CardType.SORCERY, 0);
        types.put(CardType.ARTIFACT, 0);
        types.put(CardType.ENCHANTMENT, 0);
        types.put(CardType.PLANESWALKER, 0);
        types.put(CardType.LAND, 0);

        return types;
    }

    public static Card findPreviewCard(Map<Card, Integer> givenBoard){
        int maxCMC = 0;

        Card currentMaxCard = null;
        for (Map.Entry<Card, Integer> currentPair : givenBoard.entrySet()) {
            if(currentPair.getKey().getCmc() >= maxCMC){
                currentMaxCard = currentPair.getKey();
            }
        }

        return currentMaxCard;
    }

    public static int[] getCurve(Map<Card, Integer> givenBoard){
        int[] manaCurve = new int[8];

        for (Map.Entry<Card, Integer> currentPair : givenBoard.entrySet()) {
            int currentCMC = currentPair.getKey().getCmc();
            int quantityOfCard = currentPair.getValue();
            switch(currentCMC){
                case 0:
                    manaCurve[0] += quantityOfCard;
                    break;
                case 1:
                    manaCurve[1] += quantityOfCard;
                    break;
                case 2:
                    manaCurve[2] += quantityOfCard;
                    break;
                case 3:
                    manaCurve[3] += quantityOfCard;
                    break;
                case 4:
                    manaCurve[4] += quantityOfCard;
                    break;
                case 5:
                    manaCurve[5] += quantityOfCard;
                    break;
                case 6:
                    manaCurve[6] += quantityOfCard;
                    break;
                default:
                    if(currentCMC >= 7)
                        manaCurve[7] += quantityOfCard;
            }
        }

        return manaCurve;
    }
}
