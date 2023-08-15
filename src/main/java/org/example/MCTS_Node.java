package org.example;

import io.github.asdfjkl.jchesslib.Board;
import io.github.asdfjkl.jchesslib.Move;

import java.util.ArrayList;

public class MCTS_Node {

    private Board board;
    private MCTS_Node parent;
    private Move move;
    private UCB ucb;
    private ArrayList<MCTS_Node> children = new ArrayList<>();
    private double visitCount;

    public MCTS_Node(Board board, MCTS_Node parentNode, Move legalMove){
        this.board = board;
        this.ucb = new UCB();
        this.ucb.updateValue(1, 0);
        this.parent = parentNode;
        this.move = legalMove;

    }

    public void addNodeAsChild(MCTS_Node child){
        this.children.add(child);
    }


    public Board getBoard() {
        return board;
    }

    public MCTS_Node getParent() {
        return parent;
    }

    public Move getMove() {
        return move;
    }

    public UCB getUcb() {
        return ucb;
    }

    public ArrayList<MCTS_Node> getChildren() {
        return children;
    }

    public double getVisitCount() {
        return visitCount;
    }

    public MCTS_Node getMaxUCBChild() {
        double maxUCB = Double.MIN_VALUE;
        MCTS_Node maxUCBChild = null;
        for (MCTS_Node child : this.children) {
            if (child.ucb.getValue() > maxUCB){
                maxUCB = child.ucb.getValue();
                maxUCBChild = child;
            }
        }
        return maxUCBChild;
    }
}
