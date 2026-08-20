# SIMGuard

**Continuous Defense Against SIM-Swap & Account-Takeover Fraud**

OmniKon 2026 · Phase 1 Idea Submission · Team Omni_CyberTech_8

---

## The idea in one sentence

Detect early account-takeover signals around SIM changes, new devices, unusual behaviour and risky
transactions — then apply proportionate controls before a high-risk action succeeds.

## Why

A legitimate account can become risky without its password changing. An attacker may introduce a new
SIM context, unfamiliar device, unusual access pattern, or suspicious transaction sequence. SIMGuard
treats identity as a **continuously evaluated trust context** instead of a one-time login check.

> **Design principle:** security friction should increase only when risk increases.

## How it works

```
USER / ACCOUNT → SIGNAL COLLECTION → RISK ENGINE → ADAPTIVE RESPONSE
```

1. **Detect** — normalize SIM, device, network/location, behaviour, and transaction events into a
   single Account Trust Profile.
2. **Score** — correlate signals into an explainable LOW / MEDIUM / HIGH risk score using transparent,
   weighted rules (no black box).
3. **Respond** — allow, step-up verify, or restrict the sensitive action, and show the jury/operator
   exactly which factors drove the decision.

### Worked example

| Signal | Contribution |
|---|---|
| SIM context changed in the last 24 hours | +30 |
| Login from an unrecognized device fingerprint | +20 |
| Access from an unfamiliar network/location | +10 |
| New beneficiary added just before transfer | +20 |
| Transaction amount unusual for this account | +15 |
| **Total** | **95 / 100 → HIGH → restrict + alert** |

Thresholds (illustrative, tuned in Phase 2): `0–39 LOW (allow)`, `40–74 MEDIUM (step-up verification)`,
`75–100 HIGH (restrict + alert)`.

## System architecture

```
 MOBILE / WEB CLIENT → API GATEWAY + AUTH → EVENT / SIGNAL SERVICE → RISK ENGINE
                              │                     │                    │
                              ▼                     ▼                    ▼
                     ACCOUNT & DEVICE PROFILE   EVENT STORE      ALERT / ACTION SERVICE → SECURITY DASHBOARD
```

| Component | Responsibility |
|---|---|
| Client | Login, transaction initiation, security prompts, account-security status |
| API Gateway + Auth | Authentication, authorization, protected APIs, rate limiting |
| Signal Service | Normalizes SIM/device/access/behaviour/transaction events |
| Account & Device Profile | Maintains trusted-device and account-context information |
| Risk Engine | Applies transparent scoring rules and optional anomaly scoring |
| Action Service | Executes allow / step-up verification / restriction / notification |
| Dashboard | Shows alerts, risk score, contributing factors, and event timeline |

Security controls: least privilege, encrypted communication, secure identifier handling, audit
logging, role-based access, protected administrative actions.

## Tech stack

| Layer | Choice |
|---|---|
| Frontend | React + responsive dashboard |
| Backend | Java Spring Boot / REST APIs |
| Database | PostgreSQL |
| Security | JWT / RBAC / HTTPS / secure password hashing |
| Risk Engine | Java rules engine (+ optional Python anomaly service, Phase 2) |
| Notifications | Email / in-app alerts |
| Deployment | Docker + cloud-ready services |

**MVP-first strategy:** Phase 1 ships deterministic, explainable scoring rules. Phase 2 adds anomaly
scoring on top. Phase 3 containerizes for scale. AI is an enhancement, never a black box the demo
depends on.

## Repository layout

```
simguard/
├── backend/          Spring Boot service — auth, profiles, event ingestion, risk decisions
├── frontend/          React dashboard — login, transaction simulation, risk view, alerts
├── risk-engine/       Standalone scoring rules reference (used by backend/service/RiskEngineService)
├── docs/              Architecture notes, threat model, demo script
├── docker-compose.yml Local dev stack: postgres + backend + frontend
└── .github/workflows/ CI (build + test on push)
```

## Getting started (local dev)

```bash
git clone https://github.com/<your-org>/simguard.git
cd simguard
docker compose up --build
# frontend → http://localhost:3000
# backend  → http://localhost:8080
```

Or run services individually — see `backend/README.md` and `frontend/README.md`.

## Demo story (3-minute pitch)

1. Trusted user → normal transaction (LOW, allowed).
2. SIM/device context changes.
3. New access context appears.
4. New beneficiary + unusual transaction is attempted.
5. Risk score escalates to **HIGH**.
6. Sensitive action is restricted and the dashboard explains why.

## Implementation plan

1. Threat model — attack story, trusted state, signals, mitigations
2. Data model — Account, Device, Event, RiskDecision, Transaction, Alert
3. Authentication — JWT, RBAC, protected APIs
4. Event pipeline — simulated SIM/device/network/behaviour/transaction events
5. Risk engine — weighted rules + LOW/MEDIUM/HIGH thresholds
6. Mitigation — step-up verification, transaction restriction, notifications
7. Dashboard — timeline, score, factor breakdown, alert console
8. Attack simulation — controlled takeover scenario
9. Testing — normal / suspicious / high-risk flows + authorization edge cases
10. Pitch — attack demo in under 3 minutes, then architecture + scalability

## Status

**Phase 1 — Idea Submission.** This repo currently contains the architecture scaffold and a reference
implementation of the risk-scoring rules. Full auth, event pipeline, and dashboard are the Phase 2
build targets — see `docs/implementation-plan.md`.

## License

MIT — see [LICENSE](LICENSE).
=======
# simguard
SIMShield AI is an intelligent cybersecurity platform that detects and mitigates SIM-swap and account-takeover fraud using behavioral analytics, anomaly detection, risk scoring, and adaptive authentication.
