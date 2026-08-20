# Threat Model — SIM-Swap & Account-Takeover

## Attacker goal
Take control of a victim's digital banking account and move funds out, using a SIM-swap or
similar identity-porting attack as the entry point.

## Attack story (used in the demo simulation)
1. Attacker social-engineers or bribes a telecom channel to port the victim's number to a new SIM.
2. Attacker receives OTPs/SMS intended for the victim.
3. Attacker logs into the banking app from an unrecognized device and unfamiliar network.
4. Attacker adds a new beneficiary and attempts a transfer for an unusual amount.

## Trusted state (baseline)
- Known device fingerprint(s) for the account.
- Stable SIM/carrier context over time.
- Typical login times, locations, and networks.
- Historical transaction patterns (typical beneficiaries, amounts, frequency).

## Signals SIMGuard evaluates
| Signal | Source | Why it matters |
|---|---|---|
| SIM context | Carrier/telecom signal or self-reported device state | SIM swap is the common entry point for OTP interception |
| Device fingerprint | Client-side fingerprinting | New/unrecognized device is a strong indicator of compromise |
| Network/location | IP geolocation, ASN | Attackers often operate from different networks than the victim |
| Behaviour | Action sequence, velocity, timing | Attackers move faster and differently than a normal user |
| Transaction | Beneficiary, amount, frequency | The actual harmful action — where mitigation matters most |

## Mitigations mapped to risk level
- **LOW** — Allow. No visible friction to the legitimate user.
- **MEDIUM** — Step-up verification (e.g. additional authentication factor).
- **HIGH** — Restrict the sensitive action, alert the user/operator, offer a recovery flow.

## Out of scope for Phase 1
- Live telecom/carrier API integration (simulated in the MVP).
- Production-grade anomaly/ML scoring (Phase 2).
- Multi-tenant institution isolation (Phase 3 scalability target).
