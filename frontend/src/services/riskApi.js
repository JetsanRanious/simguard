const API_BASE = process.env.REACT_APP_API_BASE || "http://localhost:8080";

/**
 * Calls the backend Risk Engine endpoint with a normalized signal context
 * and returns the explainable RiskDecisionResult.
 */
export async function evaluateSignals(signals) {
  const res = await fetch(`${API_BASE}/api/risk/evaluate`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(signals),
  });

  if (!res.ok) {
    throw new Error(`Risk evaluation failed: ${res.status}`);
  }

  return res.json();
}
