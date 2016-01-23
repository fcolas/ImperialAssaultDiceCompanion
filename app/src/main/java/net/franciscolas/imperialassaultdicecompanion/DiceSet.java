package net.franciscolas.imperialassaultdicecompanion;

import java.util.HashMap;

/**
 * A set of Dice
 */
public class DiceSet extends HashMap<String, Die> {
    public void put(Die die) {
        this.put(die.getName(), die);
    }
}
