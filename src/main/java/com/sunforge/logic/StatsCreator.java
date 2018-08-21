package com.sunforge.logic;

import java.util.Map;

import static com.sunforge.logic.BoardUtils.getTypesFromBoard;
import static com.sunforge.logic.BoardUtils.joinBoardTypes;

public class StatsCreator {
    public static Stats gatherStats(Map<Card, Integer> givenMainBoard, Map<Card, Integer> givenSideBoard) {
        Map<CardType, Integer> mainBoardTypes = getTypesFromBoard(givenMainBoard);
        Map<CardType, Integer> sideBoardTypes = getTypesFromBoard(givenSideBoard);
        Map<CardType, Integer> joinedBoardTypes = joinBoardTypes(mainBoardTypes, sideBoardTypes);

        return new StatsBuilder().setTypes(joinedBoardTypes).createStats();
    }
}
