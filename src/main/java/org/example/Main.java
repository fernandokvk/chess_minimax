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
    private static int WHITE_DEPTH = 2;
    private static int BLACK_DEPTH = 2;
    public static int[] pieceValues = {1, 3, 3, 5, 9, 0};
    //  PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING


    public static void main(String[] args) {
        Board mainBoard = new Board(true);

        if (args.length > 0) {
            WHITE_DEPTH = Integer.parseInt(args[0]) > 0 ? Integer.parseInt(args[0]) : 2;
            BLACK_DEPTH = Integer.parseInt(args[1]) > 0 ? Integer.parseInt(args[1]) : 2;
        }
        System.out.println("WHITE_DEPTH:" + WHITE_DEPTH + " - BLACK_DEPTH:" + BLACK_DEPTH);


        while (!gameEndingConditions(mainBoard)) {
            int depth = mainBoard.turn == 0 ? WHITE_DEPTH : BLACK_DEPTH;
            Node rootNode = new Node(mainBoard, null, null, 0);
            Move move = getMinimaxMove(rootNode, depth);
            if (move != null) {
                mainBoard.apply(move);
                System.out.println(intToColor(mainBoard.negColor(mainBoard.turn)) + " plays:");
                System.out.println(mainBoard);
                System.out.println(mainBoard.fen());
                System.out.println("-----------------------------------------------------------");

            }
        }
        System.out.println("ending fen: " + mainBoard.fen());
        System.out.println("-----------------------------------------------------------");

    }


    public static Move getMinimaxMove(Node rootNode, int depth) {
        ArrayList<Node> moveCandidates = new ArrayList<>();

        Instant start = Instant.now();
        int minimaxValue = minimax(rootNode, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, rootNode.getBoard().turn);
        Instant end = Instant.now();
        System.out.println("minimax cost:" + Duration.between(start, end).toMillis() + "ms");

        for (Node child : rootNode.getChildren()) {
            if (child.getValue() == minimaxValue) {
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

    //maximizar -> white
    //minimizar -> black
    public static int minimax(Node node, int depth, int alpha, int beta, int playerTurn) {
        if (depth == 0) {
            return node.getValue();
        }

        if (playerTurn == 0) {
            int maxValue = Integer.MIN_VALUE;

            ArrayList<Move> possibleChildren = node.getBoard().legalMoves();
            for (int i = 0; i < possibleChildren.size(); i++) {
                Node childNode = makeChildNode(node, possibleChildren, i);
                node.addNodeAsChild(childNode);
                int childValue = minimax(childNode, depth - 1, alpha, beta, 1);
                maxValue = Math.max(childValue, maxValue);
                alpha = Math.max(alpha, maxValue);
                if (alpha >= beta) break;
            }
            node.setValue(maxValue);
            return maxValue;
        } else {
            int minValue = Integer.MAX_VALUE;
            ArrayList<Move> possibleChildren = node.getBoard().legalMoves();

            for (int i = 0; i < possibleChildren.size(); i++) {
                Node childNode = makeChildNode(node, possibleChildren, i);
                node.addNodeAsChild(childNode);
                int childValue = minimax(childNode, depth - 1, alpha, beta, 0);
                minValue = Math.min(childValue, minValue);
                beta = Math.min(beta, minValue);
                if (alpha >= beta) break;
            }
            node.setValue(minValue);
            return minValue;
        }

    }

    private static Node makeChildNode(Node node, ArrayList<Move> possibleChildren, int i) {
        Board childBoard = node.getBoard().makeCopy();
        Move childMove = possibleChildren.get(i);
        childBoard.apply(childMove);
        return new Node(childBoard, node, childMove, node.getNodeDepth() + 1);
    }


    public static String intToColor(int x) {
        return x == 0 ? "WHITE" : "BLACK";
    }


    public static boolean gameEndingConditions(Board board) {
        if (board.isCheckmate()) {
            System.out.println("******\tCheckmate\t******");
            System.out.println(intToColor(board.negColor(board.turn)) + " wins");
            return true;
        } else if (board.isStalemate()) {
            System.out.println("******\tStalemate\t******");
            return true;
        } else if (board.isInsufficientMaterial()) {
            System.out.println("******\tInsufficient Material\t******");
            return true;
        }
        return false;
    }
}