package net.franciscolas.imperialassaultdicecompanion;

/**
 * Constraint on the outcome
 */
public abstract class Constraint {
    public abstract boolean eval(Outcome outcome);
}

class DistanceConstraint extends Constraint {
    final int dist;
    public DistanceConstraint(int distance) {
        dist = distance;
    }
    public boolean eval(Outcome outcome) {
        return outcome.range >= dist;
    }
}

class AttributeTestConstraint extends Constraint {
    final int surge_level;
    public AttributeTestConstraint() {
        surge_level = 1;
    }
    public AttributeTestConstraint(int surge_level) {
        this.surge_level = surge_level;
    }
    public boolean eval(Outcome outcome) {
        return outcome.surge >= surge_level;
    }
}

class DamageConstraint extends Constraint {
    final int damage;
    public DamageConstraint() {
        damage = 1;
    }
    public DamageConstraint(int damage) {
        this.damage = damage;
    }
    public boolean eval(Outcome outcome) {
        return outcome.damage >= damage;
    }
}

class NoDodgeConstraint extends Constraint {
    public boolean eval(Outcome outcome) {
        return outcome.dodge == 0;
    }
}