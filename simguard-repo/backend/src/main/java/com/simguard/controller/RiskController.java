package com.simguard.controller;

import com.simguard.model.RiskDecisionResult;
import com.simguard.model.SignalContext;
import com.simguard.service.RiskEngineService;
import org.springframework.web.bind.annotation.*;

/**
 * MVP endpoint: submit a normalized SignalContext for an event, get back an
 * explainable risk decision. In the full pipeline this is called by the
 * Action Service after the Signal Service normalizes raw events.
 */
@RestController
@RequestMapping("/api/risk")
public class RiskController {

    private final RiskEngineService riskEngineService;

    public RiskController(RiskEngineService riskEngineService) {
        this.riskEngineService = riskEngineService;
    }

    @PostMapping("/evaluate")
    public RiskDecisionResult evaluate(@RequestBody SignalContext signalContext) {
        return riskEngineService.evaluate(signalContext);
    }
}
