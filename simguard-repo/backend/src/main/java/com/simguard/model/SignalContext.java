package com.simguard.model;

/**
 * Normalized signals for a single sensitive event (e.g. a transaction attempt).
 * Populated by the Signal Service from raw SIM/device/network/behaviour/transaction events.
 */
public class SignalContext {

    private boolean simContextChangedRecently;
    private boolean unrecognizedDevice;
    private boolean unfamiliarNetworkOrLocation;
    private boolean unusualBehaviourSequence;
    private boolean newBeneficiary;
    private boolean unusualTransactionAmount;

    public boolean isSimContextChangedRecently() { return simContextChangedRecently; }
    public void setSimContextChangedRecently(boolean v) { this.simContextChangedRecently = v; }

    public boolean isUnrecognizedDevice() { return unrecognizedDevice; }
    public void setUnrecognizedDevice(boolean v) { this.unrecognizedDevice = v; }

    public boolean isUnfamiliarNetworkOrLocation() { return unfamiliarNetworkOrLocation; }
    public void setUnfamiliarNetworkOrLocation(boolean v) { this.unfamiliarNetworkOrLocation = v; }

    public boolean isUnusualBehaviourSequence() { return unusualBehaviourSequence; }
    public void setUnusualBehaviourSequence(boolean v) { this.unusualBehaviourSequence = v; }

    public boolean isNewBeneficiary() { return newBeneficiary; }
    public void setNewBeneficiary(boolean v) { this.newBeneficiary = v; }

    public boolean isUnusualTransactionAmount() { return unusualTransactionAmount; }
    public void setUnusualTransactionAmount(boolean v) { this.unusualTransactionAmount = v; }
}
