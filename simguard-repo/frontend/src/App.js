import React, { useState } from "react";
import RiskDashboard from "./components/RiskDashboard";
import { evaluateSignals } from "./services/riskApi";

// Demo scenario toggles mirroring the Phase 1 "worked example":
// SIM change + new device + unfamiliar network + new beneficiary + unusual amount.
const SCENARIOS = {
  normal: {
    label: "Normal login + transaction",
    signals: {
      simContextChangedRecently: false,
      unrecognizedDevice: false,
      unfamiliarNetworkOrLocation: false,
      unusualBehaviourSequence: false,
      newBeneficiary: false,
      unusualTransactionAmount: false,
    },
  },
  takeover: {
    label: "Simulated account-takeover attempt",
    signals: {
      simContextChangedRecently: true,
      unrecognizedDevice: true,
      unfamiliarNetworkOrLocation: true,
      unusualBehaviourSequence: false,
      newBeneficiary: true,
      unusualTransactionAmount: true,
    },
  },
};

export default function App() {
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [scenario, setScenario] = useState("normal");

  const runScenario = async (key) => {
    setScenario(key);
    setLoading(true);
    try {
      const data = await evaluateSignals(SCENARIOS[key].signals);
      setResult(data);
    } catch (err) {
      console.error("Risk evaluation failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{ fontFamily: "sans-serif", maxWidth: 720, margin: "40px auto", padding: "0 20px" }}>
      <h1>SIMGuard — Security Dashboard</h1>
      <p style={{ color: "#555" }}>
        Demo: run a normal event vs. a simulated account-takeover event through the backend
        Risk Engine (<code>/api/risk/evaluate</code>) and see the explainable decision.
      </p>

      <div style={{ display: "flex", gap: 12, marginBottom: 24 }}>
        {Object.entries(SCENARIOS).map(([key, s]) => (
          <button
            key={key}
            onClick={() => runScenario(key)}
            style={{
              padding: "10px 16px",
              border: scenario === key ? "2px solid #2f6fed" : "1px solid #ccc",
              borderRadius: 6,
              background: "#fff",
              cursor: "pointer",
            }}
          >
            {s.label}
          </button>
        ))}
      </div>

      {loading && <p>Evaluating…</p>}
      {result && <RiskDashboard result={result} />}
      {!result && !loading && (
        <p style={{ color: "#888" }}>Choose a scenario above to see the risk decision.</p>
      )}
    </div>
  );
}
