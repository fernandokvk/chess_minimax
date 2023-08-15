package org.example;

import io.github.asdfjkl.jchesslib.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Main {

    public static void main(String[] args) {
        int whiteDepth = 3;
        int blackDepth = 3;


        if (args.length > 0) {
            whiteDepth = Integer.parseInt(args[0]) > 0 ? Integer.parseInt(args[0]) : 2;
            blackDepth = Integer.parseInt(args[1]) > 0 ? Integer.parseInt(args[1]) : 2;
        }

//        Board mainBoard = new Board("1kr5/8/8/8/8/8/8/1KQRRB2 w - - 0 1");
        int numberOfGames = 5;
        ArrayList<EndingCondition> endingConditions = new ArrayList<>();
        for (int i = 0; i < numberOfGames; i++) {
            Board mainBoard = new Board(true);
            MinimaxGame.newMinimaxGame(mainBoard, whiteDepth, blackDepth);
            endingConditions.add(MinimaxGame.endingCondition);
        }
        printExperimentResults(numberOfGames, endingConditions);

    }

    private static void printExperimentResults(int numberOfGames, ArrayList<EndingCondition> endingConditions) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("number of games:\t" + numberOfGames);
        int whiteWins = 0, blackWins = 0, insufficientMaterial = 0, stalemate = 0;
        for (int i = 0; i < endingConditions.size(); i++) {
            EndingCondition endingCondition = endingConditions.get(i);
            switch (endingCondition){
                case WHITE_CHECKMATE:
                    whiteWins++;
                    break;
                case BLACK_CHECKMATE:
                    blackWins++;
                    break;
                case INSUFFICIENT_MATERIAL:
                    insufficientMaterial++;
                    break;
                case STALEMATE:
                    stalemate++;
                    break;
            }
        }
        System.out.println("white wins:\t" + whiteWins);
        System.out.println("black wins:\t" + blackWins);
        System.out.println("insufficientMaterial:\t" + insufficientMaterial);
        System.out.println("stalemate:\t" + stalemate);
    }
}