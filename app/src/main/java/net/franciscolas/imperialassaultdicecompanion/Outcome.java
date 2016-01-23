package net.franciscolas.imperialassaultdicecompanion;

/**
 * Result of a throw of a set of dice
 */
public class Outcome {
    public final int damage;
    public final int surge;
    public final int range;
    public final int block;
    public final int evade;
    public final int dodge;

    public Outcome() {
        this.damage = 0;
        this.surge = 0;
        this.range = 0;
        this.block = 0;
        this.evade = 0;
        this.dodge = 0;
    }
    public Outcome(int damage, int surge, int range, int block, int evade, int dodge) {
        this.damage = damage;
        this.surge = surge;
        this.range = range;
        this.block = block;
        this.evade = evade;
        this.dodge = dodge;
    }

    public Outcome(Face face) {
        damage = face.getDamage();
        surge = face.getSurge();
        range = face.getRange();
        block = face.getBlock();
        evade = face.getEvade();
        dodge = face.getDodge();
    }

    public Outcome(Outcome outcome, Face face) {
        damage = face.getDamage() + outcome.damage;
        surge = face.getSurge() + outcome.surge;
        range = face.getRange() + outcome.range;
        block = face.getBlock() + outcome.block;
        evade = face.getEvade() + outcome.evade;
        dodge = face.getDodge() + outcome.dodge;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Outcome)) {
            return false;
        }
        final Outcome other = (Outcome) object;
        return (damage == other.damage) &&
                (surge == other.surge) &&
                (range == other.range) &&
                (block == other.block) &&
                (evade == other.evade) &&
                (dodge == other.dodge);
    }

    @Override
    public int hashCode() {
        return dodge + 2*(evade + 4*(block + 8*(range + 22*(surge + 11*(damage)))));
    }

}
