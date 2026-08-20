package com.simguard.model;

import java.util.List;

/** The explainable output of the Risk Engine for a single evaluated event. */
public class RiskDecisionResult {

    /** A single triggered risk factor and its point contribution. */
    public record RiskFactor(String name, int points) {}

    private final int score;
    private final RiskLevel level;
    private final Decision decision;
    private final List<RiskFactor> factors;

    public RiskDecisionResult(int score, RiskLevel level, Decision decision, List<RiskFactor> factors) {
        this.score = score;
        this.level = level;
        this.decision = decision;
        this.factors = factors;
    }

    public int getScore() { return score; }
    public RiskLevel getLevel() { return level; }
    public Decision getDecision() { return decision; }
    public List<RiskFactor> getFactors() { return factors; }

    /** Human-readable explanation for the dashboard / audit timeline. */
    public String explain() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Score %d/100 -> %s -> %s%n", score, level, decision));
        if (factors.isEmpty()) {
            sb.append("No risk factors triggered.");
        } else {
            for (RiskFactor f : factors) {
                sb.append(String.format("%s: +%d%n", f.name(), f.points()));
            }
        }
        return sb.toString();
    }
}
