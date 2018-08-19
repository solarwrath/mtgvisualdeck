package com.sunforge.logic;

import java.util.Set;

public class CardBuilder {
    private String name;
    private SetType set;
    private int number;
    private int cmc;
    private String manaCost;
    private Set<CardType> types;

    public CardBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CardBuilder setSet(SetType set) {
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

    public CardBuilder setTypes(Set<CardType> types) {
        this.types = types;
        return this;
    }

    public Card createCard() {
        return new Card(name, set, number, cmc, manaCost, types);
    }
}