package net.franciscolas.imperialassaultdicecompanion;

/**
 * Conversions and cancellations.
 */
public abstract class Conversion {
    public abstract Outcome convert(Outcome outcome);
}

class BlockCancellation extends Conversion {
    public Outcome convert(Outcome outcome) {
        int cancel = Math.min(outcome.damage, outcome.block);
        return new Outcome(
                outcome.damage - cancel,
                outcome.surge,
                outcome.range,
                outcome.block - cancel,
                outcome.evade,
                outcome.dodge);
    }
}

class EvadeCancellation extends Conversion {
    public Outcome convert(Outcome outcome) {
        int cancel = Math.min(outcome.surge, outcome.evade);
        return new Outcome(
                outcome.damage,
                outcome.surge - cancel,
                outcome.range,
                outcome.block,
                outcome.evade - cancel,
                outcome.dodge);
    }
}

class DamageBonus extends Conversion {
    final private int damage;
    public DamageBonus(int damage) {
        this.damage = damage;
    }
    public Outcome convert(Outcome outcome) {
        return new Outcome(
                outcome.damage + damage,
                outcome.surge,
                outcome.range,
                outcome.block,
                outcome.evade,
                outcome.dodge);
    }
}

class PrecisionBonus extends Conversion {
    final private int precision;
    public PrecisionBonus(int precision) {
        this.precision = precision;
    }
    public Outcome convert(Outcome outcome) {
        return new Outcome(
                outcome.damage,
                outcome.surge,
                outcome.range + precision,
                outcome.block,
                outcome.evade,
                outcome.dodge);
    }
}

class SurgeBonus extends Conversion {
    final private int surge;
    public SurgeBonus(int surge) {
        this.surge = surge;
    }
    public Outcome convert(Outcome outcome) {
        return new Outcome(
                outcome.damage,
                outcome.surge + surge,
                outcome.range,
                outcome.block,
                outcome.evade,
                outcome.dodge);
    }
}

class DamageSurgeEffect extends Conversion {
    final private int damage;
    public DamageSurgeEffect(int damage) {
        this.damage = damage;
    }
    public Outcome convert(Outcome outcome) {
        if (outcome.surge >= 1) {
            return new Outcome(
                    outcome.damage + damage,
                    outcome.surge - 1,
                    outcome.range,
                    outcome.block,
                    outcome.evade,
                    outcome.dodge);
        } else {
            return outcome;
        }
    }
}

class PrecisionSurgeEffect extends Conversion {
    final private int precision;
    public PrecisionSurgeEffect(int precision) {
        this.precision = precision;
    }
    public Outcome convert(Outcome outcome) {
        if (outcome.surge >= 1) {
            return new Outcome(
                    outcome.damage,
                    outcome.surge - 1,
                    outcome.range + precision,
                    outcome.block,
                    outcome.evade,
                    outcome.dodge);
        } else {
            return outcome;
        }
    }
}