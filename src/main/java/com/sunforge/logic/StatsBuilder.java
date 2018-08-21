package com.sunforge.logic;

import java.util.Map;

public class StatsBuilder {
    private Card previewCard;
    private String title;
    private String author;
    private Map<ColorType, Float> manaPercentage;
    private Map<CardType, Integer> types;
    private int[] manaCurve;

    public StatsBuilder setPreviewCard(Card previewCard) {
        this.previewCard = previewCard;
        return this;
    }

    public StatsBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public StatsBuilder setAuthor(String author) {
        this.author = author;
        return this;
    }

    public StatsBuilder setManaPercentage(Map<ColorType, Float> manaPercentage) {
        this.manaPercentage = manaPercentage;
        return this;
    }

    public StatsBuilder setTypes(Map<CardType, Integer> types) {
        this.types = types;
        return this;
    }

    public StatsBuilder setManaCurve(int[] manaCurve) {
        this.manaCurve = manaCurve;
        return this;
    }

    public Stats createStats() {
        return new Stats(previewCard, title, author, manaPercentage, types, manaCurve);
    }
}