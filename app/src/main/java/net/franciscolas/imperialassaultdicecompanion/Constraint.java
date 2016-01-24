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
    public boolean eval(Outcome outcome) {
        return outcome.surge >= 1;
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