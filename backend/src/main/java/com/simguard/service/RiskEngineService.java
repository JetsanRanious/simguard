package com.simguard.service;

import com.simguard.model.Decision;
import com.simguard.model.RiskDecisionResult;
import com.simguard.model.RiskDecisionResult.RiskFactor;
import com.simguard.model.RiskLevel;
import com.simguard.model.SignalContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Transparent, deterministic MVP risk scoring — mirrors risk-engine/risk_rules.py.
 *
 * Design intent (Phase 1 submission): every point on the score is traceable to a
 * named factor so the Security Dashboard can explain a decision, not just display it.
 * An anomaly-scoring layer can be added in Phase 2 without replacing this core.
 */
@Service
public class RiskEngineService {

    private static final int SIM_CONTEXT_CHANGED = 30;
    private static final int UNRECOGNIZED_DEVICE = 20;
    private static final int UNFAMILIAR_NETWORK_OR_LOCATION = 10;
    private static final int UNUSUAL_BEHAVIOUR_SEQUENCE = 15;
    private static final int NEW_BENEFICIARY = 20;
    private static final int UNUSUAL_TRANSACTION_AMOUNT = 15;

    private static final int LOW_MAX = 39;
    private static final int MEDIUM_MAX = 74;

    public RiskDecisionResult evaluate(SignalContext ctx) {
        List<RiskFactor> factors = new ArrayList<>();
        int score = 0;

        if (ctx.isSimContextChangedRecently()) {
            score += SIM_CONTEXT_CHANGED;
            factors.add(new RiskFactor("SIM context changed in the last 24 hours", SIM_CONTEXT_CHANGED));
        }
        if (ctx.isUnrecognizedDevice()) {
            score += UNRECOGNIZED_DEVICE;
            factors.add(new RiskFactor("Login from an unrecognized device fingerprint", UNRECOGNIZED_DEVICE));
        }
        if (ctx.isUnfamiliarNetworkOrLocation()) {
            score += UNFAMILIAR_NETWORK_OR_LOCATION;
            factors.add(new RiskFactor("Access from an unfamiliar network/location", UNFAMILIAR_NETWORK_OR_LOCATION));
        }
        if (ctx.isUnusualBehaviourSequence()) {
            score += UNUSUAL_BEHAVIOUR_SEQUENCE;
            factors.add(new RiskFactor("Unusual login/action sequence or velocity", UNUSUAL_BEHAVIOUR_SEQUENCE));
        }
        if (ctx.isNewBeneficiary()) {
            score += NEW_BENEFICIARY;
            factors.add(new RiskFactor("New beneficiary added just before transfer", NEW_BENEFICIARY));
        }
        if (ctx.isUnusualTransactionAmount()) {
            score += UNUSUAL_TRANSACTION_AMOUNT;
            factors.add(new RiskFactor("Transaction amount unusual for this account", UNUSUAL_TRANSACTION_AMOUNT));
        }

        score = Math.min(score, 100);
        RiskLevel level = levelForScore(score);
        Decision decision = decisionForLevel(level);

        return new RiskDecisionResult(score, level, decision, factors);
    }

    private RiskLevel levelForScore(int score) {
        if (score <= LOW_MAX) return RiskLevel.LOW;
        if (score <= MEDIUM_MAX) return RiskLevel.MEDIUM;
        return RiskLevel.HIGH;
    }

    private Decision decisionForLevel(RiskLevel level) {
        return switch (level) {
            case LOW -> Decision.ALLOW;
            case MEDIUM -> Decision.STEP_UP_VERIFY;
            case HIGH -> Decision.RESTRICT;
        };
    }
}
