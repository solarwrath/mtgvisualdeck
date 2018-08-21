package com.sunforge.logic;

import java.util.Map;

public class Stats {
    private Card previewCard;
    private String title;
    private String author;
    private Map<ColorType, Float> manaPercentage;
    private Map<CardType, Integer> types;
    private int[] manaCurve = new int[8];

    public Stats(Card previewCard, String title, String author, Map<ColorType, Float> manaPercentage, Map<CardType, Integer> types, int[] manaCurve) {
        this.previewCard = previewCard;
        this.title = title;
        this.author = author;
        this.manaPercentage = manaPercentage;
        this.types = types;
        this.manaCurve = manaCurve;
    }

    public Card getPreviewCard() {
        return previewCard;
    }

    public void setPreviewCard(Card previewCard) {
        this.previewCard = previewCard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<ColorType, Float> getManaPercentage() {
        return manaPercentage;
    }

    public void setManaPercentage(Map<ColorType, Float> manaPercentage) {
        this.manaPercentage = manaPercentage;
    }

    public Map<CardType, Integer> getTypes() {
        return types;
    }

    public void setTypes(Map<CardType, Integer> types) {
        this.types = types;
    }

    public int[] getManaCurve() {
        return manaCurve;
    }

    public void setManaCurve(int[] manaCurve) {
        this.manaCurve = manaCurve;
    }
}
