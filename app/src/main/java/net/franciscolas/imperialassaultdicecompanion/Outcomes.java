package net.franciscolas.imperialassaultdicecompanion;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Distribution of results for a set of dice
 */
public class Outcomes {
    public HashMap<Outcome, Integer> outcomes = null;

    public final long n_tot;

    public Outcomes(List<Die> dice) {
        outcomes = new HashMap<>();
        if (dice.isEmpty()) {
            outcomes.put(new Outcome(), 1);
            n_tot = 1;
        } else {
            Outcomes tmp_outcomes = new Outcomes(dice.subList(1, dice.size()));
            n_tot = tmp_outcomes.n_tot * dice.get(0).getFaces().size();
            HashMap<Outcome, Integer> partial_outcomes = tmp_outcomes.outcomes;
            for (Face face : dice.get(0).getFaces()) {
                for (Map.Entry<Outcome, Integer> outcome_entry : partial_outcomes.entrySet()) {
                    Outcome new_key = new Outcome(outcome_entry.getKey(), face);
                    Integer n = outcomes.get(new_key);
                    if (n == null) {
                        n = 0;
                    }
                    outcomes.put(new_key, n + outcome_entry.getValue());
                }
            }
        }
    }

    public float computeProbability() {
        long n = 0;
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            n += outcome_entry.getValue();
        }
        return (float)n/(float)n_tot;
    }

    public float computeProbability(Constraint constraint) {
        long n_ok = 0;
        long n_weird = 0;
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            if (constraint.eval(outcome_entry.getKey())) {
                n_ok += outcome_entry.getValue();
            }
            n_weird += outcome_entry.getValue();
        }
        //System.out.println("tot = " + n_tot + "; ok = " + n_ok + "; weird = " + n_weird);
        return (float)n_ok/(float)n_tot;
    }

    public void applyConstraint(Constraint constraint) {
        HashMap<Outcome, Integer> new_outcomes = new HashMap<>();
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            if (constraint.eval(outcome_entry.getKey())) {
                new_outcomes.put(outcome_entry.getKey(), outcome_entry.getValue());
            }
        }
        outcomes = new_outcomes;
    }

    public void applyConversion(Conversion conversion) {
        HashMap<Outcome, Integer> new_outcomes = new HashMap<>();
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            Outcome new_key = conversion.convert(outcome_entry.getKey());
            Integer n = new_outcomes.get(new_key);
            if (n == null) {
                n = 0;
            }
            new_outcomes.put(new_key, n + outcome_entry.getValue());
        }
        outcomes = new_outcomes;
    }

    class Selector {
        private String name;
        public Selector(String name) {
            this.name = name;
        }
        public int getValue(Outcome outcome) {
            switch (name) {
                case "damage":
                    return outcome.damage;
                case "surge":
                    return outcome.surge;
                case "range":
                    return outcome.range;
                case "block":
                    return outcome.block;
                case "evade":
                    return outcome.evade;
                case "dodge":
                    return outcome.dodge;
                default:
                    // that should be an exception
                    System.out.println("Asking for the projection on an unknown name: "+name);
                    return 0;
            }
        }
    }

    public Histogram1D project1D(String name) {
        Selector selector = new Selector(name);
        Histogram1D histogram = new Histogram1D();
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            histogram.put(selector.getValue(outcome_entry.getKey()), outcome_entry.getValue());
        }
        return histogram;
    }

    public Histogram2D project2D(String name1, String name2) {
        Selector selector1 = new Selector(name1);
        Selector selector2 = new Selector(name2);
        Histogram2D histogram = new Histogram2D();
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            histogram.put(
                    selector1.getValue(outcome_entry.getKey()),
                    selector2.getValue(outcome_entry.getKey()),
                    outcome_entry.getValue());
        }
        return histogram;
    }
}
