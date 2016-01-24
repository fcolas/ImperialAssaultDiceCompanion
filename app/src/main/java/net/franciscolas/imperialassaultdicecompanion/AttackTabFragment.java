package net.franciscolas.imperialassaultdicecompanion;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttackTabFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public AttackTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.attack_tab, container, false);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.die_number,
                android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> white_adapter = ArrayAdapter.createFromResource(
                getActivity().getApplicationContext(),
                R.array.die_number,
                android.R.layout.simple_spinner_dropdown_item);
        Spinner number_blue = (Spinner) view.findViewById(R.id.number_blue);
        number_blue.setAdapter(adapter);
        number_blue.setOnItemSelectedListener(this);
        Spinner number_green = (Spinner) view.findViewById(R.id.number_green);
        number_green.setAdapter(adapter);
        number_green.setOnItemSelectedListener(this);
        Spinner number_yellow = (Spinner) view.findViewById(R.id.number_yellow);
        number_yellow.setOnItemSelectedListener(this);
        number_yellow.setAdapter(adapter);
        Spinner number_red = (Spinner) view.findViewById(R.id.number_red);
        number_red.setAdapter(adapter);
        number_red.setOnItemSelectedListener(this);
        Spinner number_black = (Spinner) view.findViewById(R.id.number_black);
        number_black.setAdapter(white_adapter);
        number_black.setOnItemSelectedListener(this);
        Spinner number_white = (Spinner) view.findViewById(R.id.number_white);
        number_white.setAdapter(adapter);
        number_white.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Get values
        Spinner number_blue = (Spinner) getView().findViewById(R.id.number_blue);
        int n_blue = 0;
        try {
            n_blue = Integer.parseInt(number_blue.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        Spinner number_green = (Spinner) getView().findViewById(R.id.number_green);
        int n_green = 0;
        try {
            n_green = Integer.parseInt(number_green.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        Spinner number_yellow = (Spinner) getView().findViewById(R.id.number_yellow);
        int n_yellow = 0;
        try {
            n_yellow = Integer.parseInt(number_yellow.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        Spinner number_red = (Spinner) getView().findViewById(R.id.number_red);
        int n_red = 0;
        try {
            n_red = Integer.parseInt(number_red.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        Spinner number_black = (Spinner) getView().findViewById(R.id.number_black);
        int n_black = 0;
        try {
            n_black = Integer.parseInt(number_black.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        Spinner number_white = (Spinner) getView().findViewById(R.id.number_white);
        int n_white = 0;
        try {
            n_white = Integer.parseInt(number_white.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        // Computation
        final DiceSet diceSet = ((MainActivity) getActivity()).getDiceSet();
        List<Die> dice = new ArrayList<>();
        for (int i=0; i<n_blue; i++) {
            dice.add(diceSet.get("blue"));
        }
        for (int i=0; i<n_green; i++) {
            dice.add(diceSet.get("green"));
        }
        for (int i=0; i<n_yellow; i++) {
            dice.add(diceSet.get("yellow"));
        }
        for (int i=0; i<n_red; i++) {
            dice.add(diceSet.get("red"));
        }
        for (int i=0; i<n_black; i++) {
            dice.add(diceSet.get("black"));
        }
        for (int i=0; i<n_white; i++) {
            dice.add(diceSet.get("white"));
        }
        Outcomes outcomes = new Outcomes(dice);
        double result = 100 * outcomes.compute_probability(new AttributeTestConstraint());
        //int result = outcomes.outcomes.size();
        // Result
        TextView result_text = (TextView) getView().findViewById(R.id.result_text);
        result_text.setText(String.format("%.1f %%", result));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
