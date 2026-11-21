package engine.model.emitter.impl;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import engine.events.domain.ports.BodyToEmitDTO;
import engine.model.emitter.ports.EmitterConfigDto;
import engine.model.emitter.ports.EmitterState;

public class BasicEmitter {

    // region Fields
    private final String id;
    private final AtomicInteger bodiesRemaining = new AtomicInteger(0);
    private AtomicInteger bodiesRemainingInBursts = new AtomicInteger(0);
    private final EmitterConfigDto config;
    private volatile double cooldown = 0.0; // seconds
    private final AtomicLong lastRequest = new AtomicLong(0L);
    private final AtomicLong lastHandledRequest = new AtomicLong(0L);
    private volatile EmitterState state;
    // endregion

    // region Constructors
    public BasicEmitter(EmitterConfigDto config) {
        if (config == null) {
            throw new IllegalArgumentException(
                    "config cannot be null. Emitter not created");
        }

        if (config.emisionRate <= 0) {
            throw new IllegalArgumentException(
                    "emisionRate must be > 0. Emitter not created");
        }

        this.id = UUID.randomUUID().toString();
        this.state = EmitterState.READY;
        this.config = config;
        this.bodiesRemaining.set(config.maxBodiesEmitted);
        this.bodiesRemainingInBursts.set(0);
    }
    // endregion

    // *** PUBLICS ***

    // region Decrementers (dec***)
    public void decCooldown(double dtSeconds) {
        this.cooldown -= dtSeconds;
    }

    public void decBodiesRemaining() {
        this.bodiesRemaining.decrementAndGet();
    }

    public void decBodiesRemainingInBursts() {
        this.bodiesRemainingInBursts.decrementAndGet();
    }
    // endregion

    // region Getters (get***)
    public BodyToEmitDTO getBodyToEmitConfig() {
        return this.config.bodyEmitted;
    }

    public double getCooldown() {
        return this.cooldown;
    }

    public int getBodiesRemaining() {
        return this.bodiesRemaining.get();
    }

    public int getBodiesRemainingInBursts() {
        return this.bodiesRemainingInBursts.get();
    }

    public String getId() {
        return this.id;
    }

    public EmitterConfigDto getConfig() { //
        return this.config;
    }
    // endregion

    public boolean mustEmitNow(double dtSeconds) {
        EmitterConfigDto emitterConfig = this.getConfig();

        // IF COOL DOWN -> NO EMISSION
        if (this.getCooldown() > 0) {
            // Cool down time. Any pending requests are discarded.
            // Cool down time can be due to:
            // - time between emissions
            // - time to reload when ammo is exhausted
            // - timen between emission when burst mode is active
            this.decCooldown(dtSeconds);
            this.markAllRequestsHandled();
            return false; // ======== Trail Emiter is overheated =========>
        }

        // IF NO AMMUNITION -> NO EMISSION AND RELOAD TIME SET
        if (!emitterConfig.unlimitedBodies && this.getBodiesRemaining() <= 0) {
            // No bodies remaining (ammo is exhausted): RELOAD is mandatory:
            // - set cool down time to reload time
            // - and discard new requests
            this.setState(EmitterState.RELOADING);
            this.markAllRequestsHandled();
            this.setCooldown(this.getConfig().reloadTime);
            this.setBodiesRemaining(this.getConfig().maxBodiesEmitted);

            return false;
        }

        // IF BURST MODE ONGOING -> HANDLE BURST EMISSION
        if (this.getBodiesRemainingInBursts() > 0) {
            // Discard any pending requests while in burst mode
            this.markAllRequestsHandled();

            this.decBodiesRemainingInBursts();
            this.decBodiesRemaining();

            if (this.getBodiesRemainingInBursts() == 0) {
                // Burst finished. Cooldown between bursts
                this.setCooldown(1.0 / this.getConfig().emisionRate);
            } else {
                // More shots to fire in this burst. Cooldown between shots
                this.setCooldown(1.0 / this.getConfig().burstEmissionRate);
            }

            return true; // ======== Must emit now! ======>
        }

        this.setState(EmitterState.READY);

        // IF NO REQUESTS -> NO EMISSION
        if (!this.hasRequest()) {
            // Nothing to do
            this.setCooldown(0);
            return false; // ======== No requests =========>
        }

        // IF HAS REQUEST -> EMIT ...
        if (emitterConfig.burstSize > 1 && emitterConfig.burstEmissionRate > 0) {
            // Burst is configured -> Start new burst
            this.setBodiesRemainingInBursts(emitterConfig.burstSize);
            this.decBodiesRemainingInBursts();
        }

        if (!emitterConfig.unlimitedBodies)
            this.decBodiesRemaining();

        this.markAllRequestsHandled();
        this.setCooldown(1.0 / emitterConfig.emisionRate);
        return true;
    }

    public void registerRequest() {
        this.lastRequest.set(System.nanoTime());
    }

    // region Setters (set***)
    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }

    public void setBodiesRemaining(int numBodies) {
        this.bodiesRemaining.set(numBodies);
    }

    public void setBodiesRemainingInBursts(int numBodiesInBursts) {
        this.bodiesRemainingInBursts.set(numBodiesInBursts);
    }

    public void setState(EmitterState state) {
        this.state = state;
    }
    // endregion

    // *** PRIVATES ***

    private boolean hasRequest() {
        return this.lastRequest.get() > this.lastHandledRequest.get();
    }

    private void markAllRequestsHandled() {
        this.lastHandledRequest.set(this.lastRequest.get());
    }

}
