package com.sunforge.logic;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Card implements Comparable<Card> {
    private String name;
    private String set;
    private int number;
    private String manaCost;
    private String[] types;

    private String artist;
    private int cmc;
    private String[] colorIdentity;
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

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
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

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public Card(String name, String set, int number, int cmc, String manaCost, String[] types) {
        this.name = name;
        this.set = set;
        this.number = number;
        this.cmc = cmc;
        this.manaCost = manaCost;
        this.types = types;
    }

    @Override
    public String toString() {
        return "Card object{\nName: " + name + "\nSet; " + set + "\nNumber: " + number + "\nCMC: " + cmc + "\nManaCost: " + manaCost + "\nCard types: " + Arrays.toString(types);
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

        if(this.cmc > card2.cmc){
            return -1;
        }

        if(this.cmc < card2.cmc){
            return 1;
        }

        //Sort by alphabetical order (weird) case insesnsetive

        return -this.name.compareToIgnoreCase(card2.name);
    }

    private static int getTheBiggestType(Card givenCard) {

        //To each type is given weight: cards with higher weight will be listed map earlier

        int max = 0;
        String[] cardTypes = givenCard.types;

        for (String currentType : cardTypes) {
            int currentWeight = 0;
            switch (currentType) {
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
        }

        return max;
    }

}
