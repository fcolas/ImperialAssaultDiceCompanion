package net.franciscolas.imperialassaultdicecompanion;

/**
 * Face of the dice in Imperial Assault
 */
public class Face {
    private int damage = 0;
    private int surge = 0;
    private int range = 0;
    private int block = 0;
    private int evade = 0;
    private int dodge = 0;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getSurge() {
        return surge;
    }

    public void setSurge(int surge) {
        this.surge = surge;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getEvade() {
        return evade;
    }

    public void setEvade(int evade) {
        this.evade = evade;
    }

    public int getDodge() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge = dodge;
    }
}
