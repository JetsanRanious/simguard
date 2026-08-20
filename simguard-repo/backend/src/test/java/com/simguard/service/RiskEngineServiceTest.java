package com.simguard.service;

import com.simguard.model.Decision;
import com.simguard.model.RiskDecisionResult;
import com.simguard.model.RiskLevel;
import com.simguard.model.SignalContext;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskEngineServiceTest {

    private final RiskEngineService service = new RiskEngineService();

    @Test
    void normalLoginIsLowRiskAndAllowed() {
        SignalContext ctx = new SignalContext();
        RiskDecisionResult result = service.evaluate(ctx);

        assertEquals(0, result.getScore());
        assertEquals(RiskLevel.LOW, result.getLevel());
        assertEquals(Decision.ALLOW, result.getDecision());
    }

    @Test
    void takeoverScenarioMatchesSubmissionWorkedExample() {
        // Reproduces the worked example from the Phase 1 PDF: score 95 -> HIGH -> RESTRICT
        SignalContext ctx = new SignalContext();
        ctx.setSimContextChangedRecently(true);
        ctx.setUnrecognizedDevice(true);
        ctx.setUnfamiliarNetworkOrLocation(true);
        ctx.setNewBeneficiary(true);
        ctx.setUnusualTransactionAmount(true);

        RiskDecisionResult result = service.evaluate(ctx);

        assertEquals(95, result.getScore());
        assertEquals(RiskLevel.HIGH, result.getLevel());
        assertEquals(Decision.RESTRICT, result.getDecision());
        assertEquals(5, result.getFactors().size());
    }

    @Test
    void singleWeakSignalStaysMediumOrLow() {
        SignalContext ctx = new SignalContext();
        ctx.setUnfamiliarNetworkOrLocation(true); // +10 only

        RiskDecisionResult result = service.evaluate(ctx);

        assertEquals(10, result.getScore());
        assertEquals(RiskLevel.LOW, result.getLevel());
        assertEquals(Decision.ALLOW, result.getDecision());
    }
}
