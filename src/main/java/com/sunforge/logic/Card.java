package com.sunforge.logic;

public class Card {
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

    public Card(String name, String set, int number, String manaCost, String[] types) {
        this.name = name;
        this.set = set;
        this.number = number;
        this.manaCost = manaCost;
        this.types = types;
    }

    @Override
    public String toString() {
        return "Card object{\nName: " + name + "\nSet; " + set + "\nNumber: " + number + "\nManaCost: " + manaCost + "\nCard types: " + types;
    }
}
