package org.example;

import io.github.asdfjkl.jchesslib.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;

public class Main {
    // turn = 0 -> white
    // turn = 1 -> black
    private static final int WHITE_DEPTH = 3;
    private static final int BLACK_DEPTH = 1;

    public static void main(String[] args) {
        String testFen = "6kr/8/8/8/8/8/8/RK6 w - - 0 1";
        Board board = new Board(true);

        while (!gameEndingConditions(board)) {
            int depth = board.turn == 0 ? WHITE_DEPTH : BLACK_DEPTH;
            Node rootNode = new Node(board, null, null, 0);
            initializeTree(rootNode, depth, 0);
            Move move = getMinimaxMove(rootNode, depth);
//            System.out.println("\t\tboard turn " +  board.turn + "\t\tdepth:" + depth);
            if (move != null) {
                board.apply(move);
                System.out.println(board);
                System.out.println(board.fen());
            }
        }
        System.out.println("-------------------");
        System.out.println("ending fen:" + board.fen());
    }



    public static Move getMinimaxMove(Node rootNode, int depth) {
        ArrayList<Node> moveCandidates = new ArrayList<>();
        Instant start = Instant.now();
        int minimax = minimax(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, rootNode.getBoard().turn);
        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).toMillis() + "ms");
        for (Node child : rootNode.getChildren()) {
            if (child.getValue() == minimax) {
                moveCandidates.add(child);
            }
        }
        if (moveCandidates.size() == 1) {
            return moveCandidates.get(0).getMove();
        } else if (moveCandidates.size() == 0) {
            return null;
        } else {
            int randomIndex = (int) Math.floor(Math.random() * moveCandidates.size());
            return moveCandidates.get(randomIndex).getMove();
        }
    }

    public static void initializeTree(Node parentNode, int maxDepth, int nodeDepth) {
        if (maxDepth > 0) {
            Board parentBoard = parentNode.getBoard();
            ArrayList<Move> legalMoves = parentBoard.legalMoves();
            for (Move legalMove : legalMoves) {
                Board clonedBoard = parentBoard.makeCopy();
                clonedBoard.apply(legalMove);
                Node node = new Node(clonedBoard, parentNode, legalMove, nodeDepth + 1);
                parentNode.addNodeAsChild(node);
                initializeTree(node, maxDepth - 1, nodeDepth + 1);
            }
        }
    }


    //maximizar -> white
    //minimizar -> black
    public static int minimax(Node node, int depth, int alpha, int beta, int playerTurn) {
        if (node.getChildren().size() == 0 || depth == 0) {
            return node.getValue();
        }

        if (playerTurn == 0) {
            int maxValue = Integer.MIN_VALUE;
            for (Node child : node.getChildren()) {
                int value = minimax(child, depth - 1, alpha, beta, 1);
                maxValue = Math.max(maxValue, value);
                alpha = Math.max(alpha, maxValue);
                if (alpha >= beta) {
                    break; // beta
                }
            }
            node.setValue(maxValue); // Set the value for the current node
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            for (Node child : node.getChildren()) {
                int value = minimax(child, depth - 1, alpha, beta, 0);
                minValue = Math.min(minValue, value);
                beta = Math.min(beta, minValue);
                if (alpha >= beta) {
                    break; // alpha
                }
            }
            node.setValue(minValue); // Set the value for the current node
            return minValue;
        }

    }



    public static String intToColor(int x) {
        return x == 0 ? "WHITE" : "BLACK";
    }


    public static boolean gameEndingConditions(Board board) {
        if (board.isCheckmate()) {
            System.out.println("Checkmate");
            return true;
        } else if (board.isStalemate()) {
            System.out.println("Stalemate");
            return true;
        } else if (board.isInsufficientMaterial()) {
            System.out.println("Insufficient Material");
            return true;
        }
        return false;
    }
}