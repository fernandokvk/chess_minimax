package org.example;

public class UCB {
    private double exploitTerm;
    private double explorationTerm;
    private double value;
    private static double REWARD_VALUE = 100;
    private static double EXPLORATION_CONSTANT = 2;

    public UCB() {
        this.exploitTerm = REWARD_VALUE;
        this.explorationTerm = EXPLORATION_CONSTANT * 2 * Math.sqrt(Math.log(1 / Double.MAX_VALUE));
        this.value = exploitTerm + explorationTerm;
    }

    public void updateValue(int parentVisitCount, int childVisitCount) {
        double epsilon = 1e-6; // Small constant to prevent division by zero
        this.explorationTerm = EXPLORATION_CONSTANT * 2 * Math.sqrt(Math.log(parentVisitCount) / (childVisitCount + epsilon));
        this.value = exploitTerm + explorationTerm;
    }

    public double getValue() {
        return this.value;
    }
}
