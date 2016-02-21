package net.franciscolas.imperialassaultdicecompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AttackTabFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

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
        EditText number_range = (EditText) view.findViewById(R.id.number_range);
        number_range.setOnEditorActionListener(this);
        EditText ability_edit1 = (EditText) view.findViewById(R.id.ability_edit1);
        ability_edit1.setOnEditorActionListener(this);
        EditText ability_edit2 = (EditText) view.findViewById(R.id.ability_edit2);
        ability_edit2.setOnEditorActionListener(this);
        EditText ability_edit3 = (EditText) view.findViewById(R.id.ability_edit3);
        ability_edit3.setOnEditorActionListener(this);
        EditText ability_edit4 = (EditText) view.findViewById(R.id.ability_edit4);
        ability_edit4.setOnEditorActionListener(this);
        EditText ability_edit5 = (EditText) view.findViewById(R.id.ability_edit5);
        ability_edit5.setOnEditorActionListener(this);
        EditText ability_edit6 = (EditText) view.findViewById(R.id.ability_edit6);
        ability_edit6.setOnEditorActionListener(this);
        return view;
    }
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        recompute();
        return true;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        recompute();
    }

    private void recompute() {
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
        EditText number_range = (EditText) getView().findViewById(R.id.number_range);
        int n_range = 0;
        try {
            n_range = Integer.parseInt(number_range.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        EditText ability_edit1 = (EditText) getView().findViewById(R.id.ability_edit1);
        String ability_text1 = ability_edit1.getText().toString();
        Conversion ability_conversion1= Conversion.conversionFactory(ability_text1);
        if (ability_conversion1 != null) {
            ability_edit1.setText(ability_conversion1.getDescription());
            //System.out.println(ability_conversion1.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text1);
        }
        EditText ability_edit2 = (EditText) getView().findViewById(R.id.ability_edit2);
        String ability_text2 = ability_edit2.getText().toString();
        Conversion ability_conversion2= Conversion.conversionFactory(ability_text2);
        if (ability_conversion2 != null) {
            ability_edit2.setText(ability_conversion2.getDescription());
            //System.out.println(ability_conversion2.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text2);
        }
        EditText ability_edit3 = (EditText) getView().findViewById(R.id.ability_edit3);
        String ability_text3 = ability_edit3.getText().toString();
        Conversion ability_conversion3= Conversion.conversionFactory(ability_text3);
        if (ability_conversion3 != null) {
            ability_edit3.setText(ability_conversion3.getDescription());
            //System.out.println(ability_conversion3.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text3);
        }
        EditText ability_edit4 = (EditText) getView().findViewById(R.id.ability_edit4);
        String ability_text4 = ability_edit4.getText().toString();
        Conversion ability_conversion4= Conversion.conversionFactory(ability_text4);
        if (ability_conversion4 != null) {
            ability_edit4.setText(ability_conversion4.getDescription());
            //System.out.println(ability_conversion4.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text4);
        }
        EditText ability_edit5 = (EditText) getView().findViewById(R.id.ability_edit5);
        String ability_text5 = ability_edit5.getText().toString();
        Conversion ability_conversion5= Conversion.conversionFactory(ability_text5);
        if (ability_conversion5 != null) {
            ability_edit5.setText(ability_conversion5.getDescription());
            //System.out.println(ability_conversion5.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text5);
        }
        EditText ability_edit6 = (EditText) getView().findViewById(R.id.ability_edit6);
        String ability_text6 = ability_edit6.getText().toString();
        Conversion ability_conversion6= Conversion.conversionFactory(ability_text6);
        if (ability_conversion6 != null) {
            ability_edit6.setText(ability_conversion6.getDescription());
            //System.out.println(ability_conversion6.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text6);
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
        // generate and process outcomes
        Outcomes outcomes = new Outcomes(dice);
        // first dodge and evade
        outcomes.applyConstraint(new NoDodgeConstraint());
        outcomes.applyConversion(new EvadeCancellation());
        // here come abilities
        if (ability_conversion1 != null) {
            outcomes.applyConversion(ability_conversion1);
        }
        if (ability_conversion2 != null) {
            outcomes.applyConversion(ability_conversion2);
        }
        if (ability_conversion3 != null) {
            outcomes.applyConversion(ability_conversion3);
        }
        if (ability_conversion4 != null) {
            outcomes.applyConversion(ability_conversion4);
        }
        if (ability_conversion5 != null) {
            outcomes.applyConversion(ability_conversion5);
        }
        if (ability_conversion6 != null) {
            outcomes.applyConversion(ability_conversion6);
        }
        // finally range and block
        outcomes.applyConstraint(new DistanceConstraint(n_range));
        outcomes.applyConversion(new BlockCancellation());

        // compute stuff to display
        double probaHit = 100 * outcomes.computeProbability();
        double probaDamage = 100 * outcomes.computeProbability(new DamageConstraint());
        Histogram2D histogramDamageSurge = outcomes.project2D("damage", "surge");
        float[] means = histogramDamageSurge.getAverages();

        // display
//        Histogram1D histogramDamage = outcomes.project1D("damage");
//        Histogram1D histogramSurge = outcomes.project1D("surge");
        TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.result_table);
        tableLayout.removeAllViews();
//        histogramDamage.populateTable(tableLayout, "damage");
        histogramDamageSurge.populateTable(tableLayout, "dmg", "surge");
//        histogramSurge.populateTable(tableLayout, "surge");

        TextView result_text = (TextView) getView().findViewById(R.id.result_text);
        result_text.setText(String.format(
                "Proba to hit: %.1f %%\n" +
                        "Proba of damage: %.1f %%\n" +
                        "Mean damage: %.2f\n" +
                        "Mean surge: %.2f\n",
                probaHit, probaDamage, means[0], means[1]));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        recompute();
    }
}
