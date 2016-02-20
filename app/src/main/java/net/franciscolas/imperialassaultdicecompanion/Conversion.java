package net.franciscolas.imperialassaultdicecompanion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Conversions and cancellations.
 */
public abstract class Conversion {
    protected String description;

    public abstract Outcome convert(Outcome outcome);

    public String getDescription() {
        return description;
    }
    public static Conversion conversionFactory(String text) {
        Pattern effect_pattern = Pattern.compile("^[sS]");
        Pattern consequence_pattern = Pattern.compile("(\\d+|\\+\\d+)\\s*(\\w*)");
        Matcher effect_matches = effect_pattern.matcher(text);
        Matcher consequence_matches = consequence_pattern.matcher(text.toLowerCase());

        // parse content
        Boolean isEffect = effect_matches.find();
        int plus_dmg = 0;
        int plus_range = 0;
        int pierce = 0;
        int plus_surge = 0;
        while (consequence_matches.find()) {
            String number = consequence_matches.group(1);
            if (number.startsWith("+")) {
                number = number.substring(1);}
            int bonus = Integer.parseInt(number);
            String name = consequence_matches.group(2);
            switch (name) {
                case "":
                case "dmg":
                case "damage":
                    plus_dmg += bonus;
                    break;
                case "rng":
                case "range":
                case "prec":
                case "precision":
                    plus_range += bonus;
                    break;
                case "pierce":
                    pierce += bonus;
                    break;
                case "surge":
                case "srg":
                case "s":
                    plus_surge += bonus;
                default:
                    //System.out.println("Cannot parse: '" + name + "'");
            }
        }
        // checking there is some effect
        if ((plus_dmg + plus_range + pierce + plus_surge) == 0) {
            return null;
        }
        // build description
        String description = "";
        String delimiter = "";
        if (isEffect) {
            description += "S";
            delimiter = ": ";
        }
        if (plus_dmg != 0) {
            description += delimiter + String.format("+%ddmg", plus_dmg);
            delimiter = ", ";
        }
        if (plus_range != 0) {
            description += delimiter + String.format("+%dprec", plus_range);
            delimiter = ", ";
        }
        if (pierce != 0) {
            description += delimiter + String.format("+%dpierce", pierce);
            delimiter = ", ";
        }
        if (plus_surge != 0) {
            description += delimiter + String.format("+%dsurge", plus_surge);
        }
        if (isEffect) {
            return new GenericEffect(plus_dmg, plus_range, pierce, plus_surge, description);
        } else {
            return new GenericConversion(plus_dmg, plus_range, pierce, plus_surge, description);
        }
    }
}

class GenericConversion extends Conversion {
    private int plus_dmg;
    private int plus_range;
    private int pierce;
    private int plus_surge;
    public GenericConversion(int plus_dmg, int plus_range, int pierce, int plus_surge,
                             String description) {
        this.description = description;
        this.plus_dmg = plus_dmg;
        this.plus_range = plus_range;
        this.pierce = pierce;
        this.plus_surge = plus_surge;
    }
    public Outcome convert(Outcome outcome) {
        return new Outcome(outcome.damage + plus_dmg,
                outcome.surge + plus_surge,
                outcome.range + plus_range,
                Math.max(outcome.block - pierce, 0),
                outcome.evade,
                outcome.dodge);
    }
}

class GenericEffect extends Conversion {
    private int plus_dmg;
    private int plus_range;
    private int pierce;
    private int plus_surge;
    public GenericEffect(int plus_dmg, int plus_range, int pierce, int plus_surge,
                             String description) {
        this.description = description;
        this.plus_dmg = plus_dmg;
        this.plus_range = plus_range;
        this.pierce = pierce;
        this.plus_surge = plus_surge;
    }
    public Outcome convert(Outcome outcome) {
        if (outcome.surge >= 1) {
            return new Outcome(outcome.damage + plus_dmg,
                    outcome.surge - 1 + plus_surge,  // the semantic is weird, here
                    outcome.range + plus_range,
                    Math.max(outcome.block - pierce, 0),
                    outcome.evade,
                    outcome.dodge);
        } else {
            return new Outcome(outcome.damage,
                    outcome.surge,
                    outcome.range,
                    outcome.block,
                    outcome.evade,
                    outcome.dodge);
        }
    }
}

class BlockCancellation extends Conversion {
    public BlockCancellation() {
        this.description = "cancel damage and block";
    }
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
    public EvadeCancellation() {
        this.description = "cancel surge and evade";
    }
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