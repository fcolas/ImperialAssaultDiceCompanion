package net.franciscolas.imperialassaultdicecompanion;

import java.util.ArrayList;
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
                    outcomes.put(
                            new Outcome(outcome_entry.getKey(), face),
                            n + outcome_entry.getValue());
                }
            }
        }
    }

    public float compute_probability(Constraint constraint) {
        long n_ok = 0;
        long n_weird = 0;
        for (Map.Entry<Outcome, Integer> outcome_entry : outcomes.entrySet()) {
            if (constraint.eval(outcome_entry.getKey())) {
                n_ok += outcome_entry.getValue();
            }
            n_weird += outcome_entry.getValue();
        }
        System.out.println("tot = " + n_tot + "; ok = " + n_ok + "; weird = " + n_weird);
        return (float)n_ok/(float)n_tot;
    }
}
