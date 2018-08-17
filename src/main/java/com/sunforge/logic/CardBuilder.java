package com.sunforge.logic;

public class CardBuilder {
    private String name;
    private String set;
    private int number;
    private int cmc;
    private String manaCost;
    private String[] types;

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

    public CardBuilder setCMC(int cmc) {
        this.cmc = cmc;
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
        return new Card(name, set, number, cmc, manaCost, types);
    }
}