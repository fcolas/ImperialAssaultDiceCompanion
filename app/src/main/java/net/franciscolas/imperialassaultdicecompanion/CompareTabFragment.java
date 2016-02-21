package net.franciscolas.imperialassaultdicecompanion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CompareTabFragment extends Fragment implements AdapterView.OnItemSelectedListener, TextView.OnEditorActionListener {

    public CompareTabFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.compare_tab, container, false);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.die_number,
                android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> white_adapter = ArrayAdapter.createFromResource(
                getActivity().getApplicationContext(),
                R.array.die_number,
                android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) view.findViewById(R.id.number_blue_first);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner = (Spinner) view.findViewById(R.id.number_green_first);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner = (Spinner) view.findViewById(R.id.number_yellow_first);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
        spinner = (Spinner) view.findViewById(R.id.number_red_first);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner = (Spinner) view.findViewById(R.id.number_black_first);
        //spinner.setAdapter(white_adapter);
        //spinner.setOnItemSelectedListener(this);
        //spinner = (Spinner) view.findViewById(R.id.number_white_first);
        //spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);
        //EditText number_range = (EditText) view.findViewById(R.id.number_range);
        //number_range.setOnEditorActionListener(this);
        EditText ability_edit = (EditText) view.findViewById(R.id.ability_edit1_first);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit2_first);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit3_first);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit4_first);
        ability_edit.setOnEditorActionListener(this);
        //ability_edit = (EditText) view.findViewById(R.id.ability_edit5_first);
        //ability_edit.setOnEditorActionListener(this);
        //ability_edit = (EditText) view.findViewById(R.id.ability_edit6_first);
        //ability_edit.setOnEditorActionListener(this);
        spinner = (Spinner) view.findViewById(R.id.number_blue_second);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner = (Spinner) view.findViewById(R.id.number_green_second);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner = (Spinner) view.findViewById(R.id.number_yellow_second);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(adapter);
        spinner = (Spinner) view.findViewById(R.id.number_red_second);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        //spinner = (Spinner) view.findViewById(R.id.number_black_second);
        //spinner.setAdapter(white_adapter);
        //spinner.setOnItemSelectedListener(this);
        //spinner = (Spinner) view.findViewById(R.id.number_white_second);
        //spinner.setAdapter(adapter);
        //spinner.setOnItemSelectedListener(this);
        //EditText number_range = (EditText) view.findViewById(R.id.number_range);
        //number_range.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit1_second);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit2_second);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit3_second);
        ability_edit.setOnEditorActionListener(this);
        ability_edit = (EditText) view.findViewById(R.id.ability_edit4_second);
        ability_edit.setOnEditorActionListener(this);
        //ability_edit = (EditText) view.findViewById(R.id.ability_edit5_second);
        //ability_edit.setOnEditorActionListener(this);
        //ability_edit = (EditText) view.findViewById(R.id.ability_edit6_second);
        //ability_edit.setOnEditorActionListener(this);
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
        Outcomes outcomes_first = computeOutcomes(R.id.number_blue_first, R.id.number_green_first,
                R.id.number_yellow_first, R.id.number_red_first, R.id.ability_edit1_first,
                R.id.ability_edit2_first, R.id.ability_edit3_first, R.id.ability_edit4_first);

        Outcomes outcomes_second = computeOutcomes(R.id.number_blue_second, R.id.number_green_second,
                R.id.number_yellow_second, R.id.number_red_second, R.id.ability_edit1_second,
                R.id.ability_edit2_second, R.id.ability_edit3_second, R.id.ability_edit4_second);

        // general stats first
        double probaHit_first = 100 * outcomes_first.computeProbability();
        double probaDamage_first = 100 * outcomes_first.computeProbability(new DamageConstraint());
        Histogram1D histogramRange_first = outcomes_first.project1D("range");
        float mean_range_first = histogramRange_first.getAverage();
        Histogram2D histogramDamageSurge_first = outcomes_first.project2D("damage", "surge");
        float[] means_first = histogramDamageSurge_first.getAverages();

        TextView result_text_first = (TextView) getView().findViewById(R.id.result_text_first);
        result_text_first.setText(String.format(
                "Proba to hit: %.1f %%\n" +
                        "Proba of damage: %.1f %%\n" +
                        "Mean damage: %.2f\n" +
                        "Mean surge: %.2f\n" +
                        "Mean range: %.2f\n",
                probaHit_first, probaDamage_first, means_first[0], means_first[1], mean_range_first));
        
        // general stats second
        double probaHit_second = 100 * outcomes_second.computeProbability();
        double probaDamage_second = 100 * outcomes_second.computeProbability(new DamageConstraint());
        Histogram1D histogramRange_second = outcomes_second.project1D("range");
        float mean_range_second = histogramRange_second.getAverage();
        Histogram2D histogramDamageSurge_second = outcomes_second.project2D("damage", "surge");
        float[] means_second = histogramDamageSurge_second.getAverages();

        TextView result_text_second = (TextView) getView().findViewById(R.id.result_text_second);
        result_text_second.setText(String.format(
                "Proba to hit: %.1f %%\n" +
                        "Proba of damage: %.1f %%\n" +
                        "Mean damage: %.2f\n" +
                        "Mean surge: %.2f\n" +
                        "Mean range: %.2f\n",
                probaHit_second, probaDamage_second, means_second[0], means_second[1], mean_range_second));
    }

    private Outcomes computeOutcomes(int id_blue, int id_green, int id_yellow, int id_red,
                                     int id_ab1, int id_ab2, int id_ab3, int id_ab4) {
        // Get values
        Spinner spinner = (Spinner) getView().findViewById(id_blue);
        int n_blue = 0;
        try {
            n_blue = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        spinner = (Spinner) getView().findViewById(id_green);
        int n_green = 0;
        try {
            n_green = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        spinner = (Spinner) getView().findViewById(id_yellow);
        int n_yellow = 0;
        try {
            n_yellow = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        spinner = (Spinner) getView().findViewById(id_red);
        int n_red = 0;
        try {
            n_red = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        /*spinner = (Spinner) getView().findViewById(id_black);
        int n_black = 0;
        try {
            n_black = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }
        spinner = (Spinner) getView().findViewById(id_white);
        int n_white = 0;
        try {
            n_white = Integer.parseInt(spinner.getSelectedItem().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }*/
 /*       EditText number_range = (EditText) getView().findViewById(R.id.number_range);
        int n_range = 0;
        try {
            n_range = Integer.parseInt(number_range.getText().toString());
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }*/
        EditText ability_edit = (EditText) getView().findViewById(id_ab1);
        String ability_text1 = ability_edit.getText().toString();
        Conversion ability_conversion1= Conversion.conversionFactory(ability_text1);
        if (ability_conversion1 != null) {
            ability_edit.setText(ability_conversion1.getDescription());
            //System.out.println(ability_conversion1.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text1);
        }
        EditText ability_edit2 = (EditText) getView().findViewById(id_ab2);
        String ability_text2 = ability_edit2.getText().toString();
        Conversion ability_conversion2= Conversion.conversionFactory(ability_text2);
        if (ability_conversion2 != null) {
            ability_edit2.setText(ability_conversion2.getDescription());
            //System.out.println(ability_conversion2.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text2);
        }
        EditText ability_edit3 = (EditText) getView().findViewById(id_ab3);
        String ability_text3 = ability_edit3.getText().toString();
        Conversion ability_conversion3= Conversion.conversionFactory(ability_text3);
        if (ability_conversion3 != null) {
            ability_edit3.setText(ability_conversion3.getDescription());
            //System.out.println(ability_conversion3.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text3);
        }
        EditText ability_edit4 = (EditText) getView().findViewById(id_ab4);
        String ability_text4 = ability_edit4.getText().toString();
        Conversion ability_conversion4= Conversion.conversionFactory(ability_text4);
        if (ability_conversion4 != null) {
            ability_edit4.setText(ability_conversion4.getDescription());
            //System.out.println(ability_conversion4.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text4);
        }
        /*EditText ability_edit5 = (EditText) getView().findViewById(id_ab5);
        String ability_text5 = ability_edit5.getText().toString();
        Conversion ability_conversion5= Conversion.conversionFactory(ability_text5);
        if (ability_conversion5 != null) {
            ability_edit5.setText(ability_conversion5.getDescription());
            //System.out.println(ability_conversion5.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text5);
        }
        EditText ability_edit6 = (EditText) getView().findViewById(id_ab6);
        String ability_text6 = ability_edit6.getText().toString();
        Conversion ability_conversion6= Conversion.conversionFactory(ability_text6);
        if (ability_conversion6 != null) {
            ability_edit6.setText(ability_conversion6.getDescription());
            //System.out.println(ability_conversion6.getDescription());
        } else {
            //System.out.println("Could not parse ability: " + ability_text6);
        }*/
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
        /*for (int i=0; i<n_black; i++) {
            dice.add(diceSet.get("black"));
        }
        for (int i=0; i<n_white; i++) {
            dice.add(diceSet.get("white"));
        }*/
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
        /*if (ability_conversion5 != null) {
            outcomes.applyConversion(ability_conversion5);
        }
        if (ability_conversion6 != null) {
            outcomes.applyConversion(ability_conversion6);
        }*/
        // finally range and block
//        outcomes.applyConstraint(new DistanceConstraint(n_range));
        outcomes.applyConversion(new BlockCancellation());
        return outcomes;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        recompute();
    }
}
