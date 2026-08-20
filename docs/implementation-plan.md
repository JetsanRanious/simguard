# Implementation Plan

| # | Step | Status |
|---|---|---|
| 1 | Threat model — attack story, trusted state, signals, mitigations | ✅ `docs/threat-model.md` |
| 2 | Data model — Account, Device, Event, RiskDecision, Transaction, Alert | 🔲 Phase 2 |
| 3 | Authentication — JWT, RBAC, protected APIs | 🔲 Phase 2 (scaffold in `backend/security`) |
| 4 | Event pipeline — simulated SIM/device/network/behaviour/transaction events | 🔲 Phase 2 |
| 5 | Risk engine — weighted rules + LOW/MEDIUM/HIGH thresholds | ✅ `risk-engine/risk_rules.py`, `backend/.../RiskEngineService.java` |
| 6 | Mitigation — step-up verification, transaction restriction, notifications | 🔲 Phase 2 |
| 7 | Dashboard — timeline, score, factor breakdown, alert console | ✅ MVP demo view in `frontend/src/components/RiskDashboard.js` |
| 8 | Attack simulation — controlled takeover scenario | ✅ `frontend/src/App.js` scenario toggles |
| 9 | Testing — normal / suspicious / high-risk flows + authorization edge cases | ✅ `backend/src/test/.../RiskEngineServiceTest.java` |
| 10 | Pitch — attack demo in under 3 minutes, then architecture + scalability | 🔲 See `docs/demo-script.md` |

## Prototype success criteria
- [x] Risk engine reproduces the worked example from the Phase 1 submission (95/100 → HIGH).
- [ ] Normal login and transaction complete without unnecessary friction (needs full event pipeline).
- [ ] A controlled account-takeover scenario visibly escalates risk end-to-end (frontend scaffold only so far).
- [ ] The dashboard explains the decision instead of only showing an alert (MVP dashboard done).
- [ ] A mitigation action is executed and recorded in an audit timeline (Phase 2).
- [ ] Clear path from one demo account to many accounts (see scalability notes in the submission PDF).
