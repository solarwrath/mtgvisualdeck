package com.sunforge.logic;

public class CardBuilder {
    private String manaCost;
    private String name;
    private int number;
    private String[] types;
    private String set;

    public CardBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CardBuilder setSet(String set) {
        this.set = set;
        return this;
    }

    public CardBuilder setNumber(int number) {
        this.number = number;
        return this;
    }

    public CardBuilder setManaCost(String manaCost) {
        this.manaCost = manaCost;
        return this;
    }

    public CardBuilder setTypes(String[] types) {
        this.types = types;
        return this;
    }

    public Card createCard() {
        return new Card(name, set, number, manaCost, types);
    }
}