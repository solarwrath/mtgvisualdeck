package com.sunforge.logic;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class Card implements Comparable<Card> {
    private String name;
    private SetType set;
    private int number;
    private int cmc;
    private String manaCost;
    private List<ColorType> colorIdentity;
    private Set<CardType> types;

    private String artist;
    private String[] colors;
    private String id;
    private String imageName;
    private String layout;
    private int multiverseid;
    private String power;
    private String rarity;
    private String[] subtypes;
    private String text;
    private String toughness;
    private String type;

    private static Logger log = Logger.getLogger(Card.class.getName());

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SetType getSet() {
        return set;
    }

    public void setSet(SetType set) {
        this.set = set;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getCmc() {
        return cmc;
    }

    public void setCmc(int cmc) {
        this.cmc = cmc;
    }

    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public List<ColorType> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<ColorType> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public Set<CardType> getTypes() {
        return types;
    }

    public void setTypes(Set<CardType> types) {
        this.types = types;
    }

    public Card(String name, SetType set, int number, int cmc, String manaCost, List<ColorType> colorIdentity, Set<CardType> types) {
        this.name = name;
        this.set = set;
        this.number = number;
        this.cmc = cmc;
        this.manaCost = manaCost;
        this.colorIdentity = colorIdentity;
        this.types = types;
    }

    @Override
    public String toString() {

        //Parse color identity

        StringBuilder colorIdentitySB = new StringBuilder();
        for (ColorType currentColor : colorIdentity) {
            colorIdentitySB.append(currentColor.toString()).append(",");
        }
        colorIdentitySB.deleteCharAt(colorIdentity.size()-1);

        return new StringBuilder("Card object{\nName: ").append(name).append("\nSet; ").append(set.toString())
                .append("\nNumber: ").append(number).append("\nCMC: ").append(cmc).append("\nManaCost: ").append(manaCost)
                .append("\nColor identity:").append(colorIdentitySB.toString()).append("\nCard types: ")
                .append(Arrays.toString(types.toArray())).toString();
    }

    @Override
    public int compareTo(Card card2) {

        //Sorting by weight (Creature - Instant - Sorcery - Artifact - Planeswalker - Land)
        int weightCurrent = getTheBiggestType(this);
        int weightGiven = getTheBiggestType(card2);

        if (weightCurrent > weightGiven) {
            return 1;
        }
        if (weightCurrent < weightGiven) {
            return -1;
        }

        //Sorting by CMC

        if (this.cmc > card2.cmc) {
            return -1;
        }

        if (this.cmc < card2.cmc) {
            return 1;
        }

        //Sort by alphabetical order (weird) case insesnsetive

        return -this.name.compareToIgnoreCase(card2.name);
    }

    private static int getTheBiggestType(Card givenCard) {

        //To each type is given weight: cards with higher weight will be listed map earlier

        int max = 0;
       /* Set<CardType> cardTypes = givenCard.types;
        for (CardType currentType : cardTypes) {
            currentType.toString()
        }
        for (String currentType : cardTypes) {
            int currentWeight = 0;
            switch (currentType.) {
                case "Creature":
                    currentWeight = 6;
                    break;
                case "Instant":
                    currentWeight = 5;
                    break;
                case "Sorcery":
                    currentWeight = 4;
                    break;
                case "Artifact":
                    currentWeight = 3;
                    break;
                case "Planeswalker":
                    currentWeight = 2;
                    break;
                case "Land":
                    currentWeight = 1;
                    break;
                default:
                    Exception illegalArgument = new IllegalArgumentException("There is no such type: " + currentType);
                    log.log(Level.SEVERE, "Error in getting the weight of type: " + currentType, illegalArgument);
                    break;
            }

            max = Math.max(max, currentWeight);
        }*/

        return max;
    }

}
