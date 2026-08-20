import React from "react";

const LEVEL_COLORS = {
  LOW: "#2f9e44",
  MEDIUM: "#e8a63a",
  HIGH: "#d64545",
};

/**
 * Renders the explainable risk decision: score, level, decision, and the
 * factor breakdown — matching the "Explainable Risk Score" + "Security
 * Timeline" features from the Phase 1 submission.
 */
export default function RiskDashboard({ result }) {
  const color = LEVEL_COLORS[result.level] || "#888";

  return (
    <div style={{ border: "1px solid #e2e7f0", borderRadius: 8, padding: 20 }}>
      <div style={{ display: "flex", alignItems: "center", gap: 12, marginBottom: 16 }}>
        <span
          style={{
            background: color,
            color: "#fff",
            padding: "4px 12px",
            borderRadius: 12,
            fontWeight: 700,
            fontSize: 13,
          }}
        >
          {result.level}
        </span>
        <span style={{ fontWeight: 700, fontSize: 18 }}>{result.score} / 100</span>
        <span style={{ color: "#555" }}>→ {result.decision}</span>
      </div>

      <h3 style={{ fontSize: 14, marginBottom: 8 }}>Contributing factors</h3>
      {result.factors && result.factors.length > 0 ? (
        <ul>
          {result.factors.map((f, i) => (
            <li key={i}>
              {f.name}: <b>+{f.points}</b>
            </li>
          ))}
        </ul>
      ) : (
        <p style={{ color: "#888" }}>No risk factors triggered.</p>
      )}
    </div>
  );
}
