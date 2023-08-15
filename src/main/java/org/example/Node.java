package org.example;

import io.github.asdfjkl.jchesslib.Board;
import io.github.asdfjkl.jchesslib.CONSTANTS;
import io.github.asdfjkl.jchesslib.Move;

import java.util.ArrayList;

public class Node {
    private Board board;
    private Node parent;
    private Move move;
    private ArrayList<Node> children = new ArrayList<>();
    private int nodeDepth;
    private int value;
    private static int[] pieceValues = {1, 3, 3, 5, 9, 0};


    public Node(Board board, Node parentNode, Move legalMove, int nodeDepth) {
        this.board = board;
        this.parent = parentNode;
        this.move = legalMove;
        this.nodeDepth = nodeDepth;
        this.value = evaluateBoard(this.board);
    }

    public int evaluateBoard(Board board) {
        int blackScore = 0;
        int whiteScore = 0;
        //  EMPTY, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING

        for (int i = 21; i < 99; i++) {
            int piece = board.getPieceAt(i);

            if (piece != CONSTANTS.EMPTY) {
                if (piece >= CONSTANTS.BLACK_PAWN && piece <= CONSTANTS.BLACK_KING) {
//                    System.out.println("black piece");
                    blackScore += pieceValues[piece - CONSTANTS.BLACK_PAWN];
                } else if (piece >= CONSTANTS.WHITE_PAWN && piece <= CONSTANTS.WHITE_KING) {
//                    System.out.println("white piece");
                    whiteScore += pieceValues[piece - CONSTANTS.WHITE_PAWN];
                }
            }
        }
//        return playerColor == 0 ? whiteScore : blackScore;
        return whiteScore - blackScore;
    }

    public void addNodeAsChild(Node child) {
        this.children.add(child);
    }


    public Board getBoard() {
        return board;
    }

    public Node getParent() {
        return parent;
    }

    public Move getMove() {
        return move;
    }


    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getNodeDepth() {
        return nodeDepth;
    }

    @Override
    public String toString() {
        return "Node{" +
                "nodeDepth=" + nodeDepth +
                ", value=" + value +
                '}';
    }
}
