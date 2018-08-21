package com.sunforge.logic;

import java.util.Map;

import static com.sunforge.logic.BoardUtils.*;

public class StatsCreator {
    public static Stats gatherStats(String givenTitle, String givenAuthor, Map<Card, Integer> givenMainBoard, Map<Card, Integer> givenSideBoard) {
        Card previewCard = findPreviewCard(givenMainBoard);

        Map<CardType, Integer> mainBoardTypes = getTypesFromBoard(givenMainBoard);
        Map<CardType, Integer> sideBoardTypes = getTypesFromBoard(givenSideBoard);
        Map<CardType, Integer> joinedBoardTypes = joinBoardTypes(mainBoardTypes, sideBoardTypes);

        int[] manaCurve = getCurve(givenMainBoard);

        //TODO Mana Curve and Mana Percentage

        return new StatsBuilder().setPreviewCard(previewCard).setTitle(givenTitle).setAuthor(givenAuthor)
                .setTypes(joinedBoardTypes).setManaCurve(manaCurve).createStats();
    }
}
