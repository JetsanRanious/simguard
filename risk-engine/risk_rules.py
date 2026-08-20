"""
SIMGuard Risk Engine — reference scoring rules.

This is the transparent, explainable MVP scoring logic described in the
Phase 1 submission. It is intentionally simple: every point on the score
maps to a named factor, so a decision can always be explained to a user
or an operator. This module is the reference used to port the same
logic into the Spring Boot RiskEngineService (see backend/.../service).

Phase 2 note: an anomaly-scoring layer (e.g. a Python microservice) can
add an additional signal into RiskEvent.anomaly_score without replacing
this deterministic core.
"""

from dataclasses import dataclass, field
from enum import Enum
from typing import Optional


class RiskLevel(str, Enum):
    LOW = "LOW"
    MEDIUM = "MEDIUM"
    HIGH = "HIGH"


class Decision(str, Enum):
    ALLOW = "ALLOW"
    STEP_UP_VERIFY = "STEP_UP_VERIFY"
    RESTRICT = "RESTRICT"


# Illustrative MVP weights — tune with real event data in Phase 2.
WEIGHTS = {
    "sim_context_changed_recently": 30,   # SIM context changed in the last 24h
    "unrecognized_device": 20,             # Unknown device fingerprint
    "unfamiliar_network_or_location": 10,  # Unexpected IP/network/location
    "unusual_behaviour_sequence": 15,      # Unusual login/action sequence or velocity
    "new_beneficiary": 20,                 # New beneficiary added just before a transfer
    "unusual_transaction_amount": 15,      # Amount unusual for this account
}

# Score → decision thresholds (illustrative; documented in the submission PDF)
THRESHOLDS = {
    RiskLevel.LOW: (0, 39),
    RiskLevel.MEDIUM: (40, 74),
    RiskLevel.HIGH: (75, 100),
}

DECISION_FOR_LEVEL = {
    RiskLevel.LOW: Decision.ALLOW,
    RiskLevel.MEDIUM: Decision.STEP_UP_VERIFY,
    RiskLevel.HIGH: Decision.RESTRICT,
}


@dataclass
class SignalContext:
    """Normalized signals for a single sensitive event (e.g. a transaction attempt)."""
    sim_context_changed_recently: bool = False
    unrecognized_device: bool = False
    unfamiliar_network_or_location: bool = False
    unusual_behaviour_sequence: bool = False
    new_beneficiary: bool = False
    unusual_transaction_amount: bool = False


@dataclass
class RiskFactor:
    name: str
    triggered: bool
    points: int


@dataclass
class RiskDecisionResult:
    score: int
    level: RiskLevel
    decision: Decision
    factors: list = field(default_factory=list)  # list[RiskFactor], only triggered ones

    def explain(self) -> str:
        if not self.factors:
            return f"Score {self.score}/100 → {self.level.value} → {self.decision.value}. No risk factors triggered."
        lines = [f"{f.name}: +{f.points}" for f in self.factors]
        return (
            f"Score {self.score}/100 → {self.level.value} → {self.decision.value}\n"
            + "\n".join(lines)
        )


def score_signals(ctx: SignalContext) -> RiskDecisionResult:
    """Deterministically score a SignalContext and return an explainable decision."""
    total = 0
    triggered_factors = []

    for field_name, weight in WEIGHTS.items():
        if getattr(ctx, field_name):
            total += weight
            triggered_factors.append(
                RiskFactor(name=field_name.replace("_", " "), triggered=True, points=weight)
            )

    total = min(total, 100)
    level = _level_for_score(total)
    decision = DECISION_FOR_LEVEL[level]

    return RiskDecisionResult(score=total, level=level, decision=decision, factors=triggered_factors)


def _level_for_score(score: int) -> RiskLevel:
    for level, (low, high) in THRESHOLDS.items():
        if low <= score <= high:
            return level
    return RiskLevel.HIGH  # score > 100 safety fallback


if __name__ == "__main__":
    # Reproduces the worked example from the Phase 1 submission PDF.
    example = SignalContext(
        sim_context_changed_recently=True,
        unrecognized_device=True,
        unfamiliar_network_or_location=True,
        new_beneficiary=True,
        unusual_transaction_amount=True,
    )
    result = score_signals(example)
    print(result.explain())
