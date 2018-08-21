package com.sunforge.logic;

import java.util.Map;

import static com.sunforge.logic.BoardUtils.*;

public class StatsCreator {
    public static Stats gatherStats(String givenTitle, String givenAuthor, Map<Card, Integer> givenMainBoard, Map<Card, Integer> givenSideBoard) {
        Card previewCard = findPreviewCard(givenMainBoard);

        Map<CardType, Integer> mainBoardTypes = getTypesFromBoard(givenMainBoard);
        Map<CardType, Integer> sideBoardTypes = getTypesFromBoard(givenSideBoard);
        Map<CardType, Integer> joinedBoardTypes = joinBoardTypes(mainBoardTypes, sideBoardTypes);

        Map<ColorType, Float> colorPercentages = getColorPercentages(givenMainBoard);

        int[] manaCurve = getCurve(givenMainBoard);

        return new StatsBuilder().setPreviewCard(previewCard).setTitle(givenTitle).setAuthor(givenAuthor)
                .setManaPercentage(colorPercentages).setTypes(joinedBoardTypes).setManaCurve(manaCurve)
                .createStats();
    }
}
