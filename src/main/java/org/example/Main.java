package org.example;

import io.github.asdfjkl.jchesslib.Board;
import io.github.asdfjkl.jchesslib.Game;
import io.github.asdfjkl.jchesslib.Move;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(true);
        Game game = new Game();
        // turn = 0 -> white
        // turn = 1 -> black
        ArrayList<Move> legalMoves = board.legalMoves();
        Node rootNode = new Node(board, null, null);
        for (Move legalMove : legalMoves) {
            Board clonedBoard = board.makeCopy();
            clonedBoard.apply(legalMove);
            Node node = new Node(clonedBoard, rootNode, legalMove);
            rootNode.addNodeAsChild(node);
        }


        Node maxUCBChild = rootNode.getMaxUCBChild();
        Node expansion = expansion(maxUCBChild);
        System.out.println("Main.main");
    }

    public static Node expansion(Node node) {
        if (node.getChildren().size() == 0) {
            //nó folha
            return node;
        }
        double maxUCB = Double.MIN_VALUE;
        Node selectedChild = null;
        for (Node child : node.getChildren()) {
            double currentUCB = child.getUcb().getValue();
            if (currentUCB > maxUCB) {
                maxUCB = currentUCB;
                selectedChild = child;
            }
        }
        return expansion(selectedChild);

    }


    public static void randomDebug(Board board) {
        ArrayList<Move> legalMoves = board.legalMoves();
        board.apply(legalMoves.get(0));
        System.out.println("turn: " + board.turn);
        System.out.println(board);

        legalMoves = board.legalMoves();
        board.apply(legalMoves.get(0));
        System.out.println("turn: " + board.turn);
        System.out.println(board);

        legalMoves = board.legalMoves();
        board.apply(legalMoves.get(0));
        System.out.println("turn: " + board.turn);
        System.out.println(board);
    }

    public static String intToColor(int x) {
        return x == 0 ? "WHITE" : "BLACK";
    }

    public static void checkmate(Board board){
        if (!board.isCheckmate()) {
            //isCheckmate -> returns true if you checkmate the opponent's king (win)
            int kingPos = board.getKingPos(0);
            System.out.println("--------------" + intToColor(board.getPieceColorAt(kingPos)));

        }
    }

}