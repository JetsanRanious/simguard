# 3-Minute Demo Script

1. **Trusted user, normal transaction** (~30s)
   Show the "Normal login + transaction" scenario in the dashboard. Score is LOW, decision is
   ALLOW, no friction shown to the user.

2. **Context changes** (~30s)
   Narrate: "Now imagine this account's SIM was just swapped, and someone logs in from a new
   device on an unfamiliar network."

3. **Suspicious transaction attempt** (~45s)
   Click "Simulated account-takeover attempt." Point out the beneficiary and amount signals
   stacking on top of the SIM/device/network signals.

4. **Risk escalates to HIGH** (~30s)
   Show the score (95/100), the HIGH badge, and the full factor breakdown — this is the
   "explainable decision" the pitch emphasizes.

5. **Mitigation** (~30s)
   Explain that in the full pipeline this triggers the Action Service: sensitive action
   restricted, alert sent, recovery flow offered — and it's all recorded to the audit timeline.

6. **Close on the architecture** (~15s)
   One slide/diagram: client → gateway → signal service → risk engine → action service →
   dashboard. Emphasize: stateless, horizontally scalable, new signal adapters plug into the
   same normalized event model.
