package org.example;

import io.github.asdfjkl.jchesslib.Board;
import io.github.asdfjkl.jchesslib.Move;

import java.util.ArrayList;

public class Node {

    private Board board;
    private Node parent;
    private Move move;
    private UCB ucb;
    private ArrayList<Node> children = new ArrayList<>();
    private double visitCount;

    public Node(Board board, Node parentNode, Move legalMove){
        this.board = board;
        this.ucb = new UCB();
        this.ucb.updateValue(1, 0);
        this.parent = parentNode;
        this.move = legalMove;

    }

    public void addNodeAsChild(Node child){
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

    public UCB getUcb() {
        return ucb;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public double getVisitCount() {
        return visitCount;
    }

    public Node getMaxUCBChild() {
        double maxUCB = Double.MIN_VALUE;
        Node maxUCBChild = null;
        for (Node child : this.children) {
            if (child.ucb.getValue() > maxUCB){
                maxUCB = child.ucb.getValue();
                maxUCBChild = child;
            }
        }
        return maxUCBChild;
    }
}
