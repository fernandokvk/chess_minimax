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
        int blackDepth = 1;

        if (args.length > 0) {
            whiteDepth = Integer.parseInt(args[0]) > 0 ? Integer.parseInt(args[0]) : 2;
            blackDepth = Integer.parseInt(args[1]) > 0 ? Integer.parseInt(args[1]) : 2;
        }

//        Board mainBoard = new Board("1kr5/8/8/8/8/8/8/1KQRRB2 w - - 0 1");
        Board mainBoard = new Board(true);
        MinimaxGame.newMinimaxGame(mainBoard, whiteDepth, blackDepth);
    }
}