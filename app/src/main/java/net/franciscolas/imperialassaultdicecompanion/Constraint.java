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