package net.franciscolas.imperialassaultdicecompanion;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        ArrayAdapter<CharSequence> graph_spinner_adapter = ArrayAdapter.createFromResource(
                getActivity(),
                R.array.graph_type,
                android.R.layout.simple_spinner_dropdown_item);
        spinner = (Spinner) view.findViewById(R.id.graph_spinner);
        spinner.setAdapter(graph_spinner_adapter);
        spinner.setOnItemSelectedListener(this);
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
        Histogram1D histogramDamage_first = outcomes_first.project1D("damage");
        float mean_damage_first = histogramDamage_first.getAverage();
        Histogram1D histogramSurge_first = outcomes_first.project1D("surge");
        float mean_surge_first = histogramSurge_first.getAverage();
        Histogram1D histogramRange_first = outcomes_first.project1D("range");
        float mean_range_first = histogramRange_first.getAverage();

        TextView result_text_first = (TextView) getView().findViewById(R.id.result_text_first);
        result_text_first.setText(String.format(
                "Proba to hit: %.1f %%\n" +
                        "Proba of damage: %.1f %%\n" +
                        "Mean damage: %.2f\n" +
                        "Mean surge: %.2f\n" +
                        "Mean range: %.2f\n",
                probaHit_first, probaDamage_first, mean_damage_first, mean_surge_first, mean_range_first));
        
        // general stats second
        double probaHit_second = 100 * outcomes_second.computeProbability();
        double probaDamage_second = 100 * outcomes_second.computeProbability(new DamageConstraint());
        Histogram1D histogramDamage_second = outcomes_second.project1D("damage");
        float mean_damage_second = histogramDamage_second.getAverage();
        Histogram1D histogramSurge_second = outcomes_second.project1D("surge");
        float mean_surge_second = histogramSurge_second.getAverage();
        Histogram1D histogramRange_second = outcomes_second.project1D("range");
        float mean_range_second = histogramRange_second.getAverage();

        TextView result_text_second = (TextView) getView().findViewById(R.id.result_text_second);
        result_text_second.setText(String.format(
                "Proba to hit: %.1f %%\n" +
                        "Proba of damage: %.1f %%\n" +
                        "Mean damage: %.2f\n" +
                        "Mean surge: %.2f\n" +
                        "Mean range: %.2f\n",
                probaHit_second, probaDamage_second, mean_damage_second, mean_surge_second, mean_range_second));

        Spinner graph_spinner = (Spinner) getView().findViewById(R.id.graph_spinner);
        String choice = graph_spinner.getSelectedItem().toString();
        Pattern graph_pattern = Pattern.compile("(\\w*) vs (\\w*)");
        Matcher graph_matches = graph_pattern.matcher(choice.toLowerCase());
        if (!graph_matches.find()) {
            return;
        }
        String x_dim = graph_matches.group(2);
        String y_dim = graph_matches.group(1);
        Histogram2D histogram_first = outcomes_first.project2D(x_dim, y_dim);
        Histogram2D histogram_second = outcomes_second.project2D(x_dim, y_dim);

        TableLayout tableLayout = (TableLayout) getView().findViewById(R.id.result_table);
        tableLayout.removeAllViews();
        populateTable(tableLayout, histogram_first, histogram_second, x_dim, y_dim);
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

    private void populateTable(TableLayout tableLayout, Histogram2D h1, Histogram2D h2,
                               String name1, String name2) {
        Context context = tableLayout.getContext();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams trparams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams trparamsVert = new TableRow.LayoutParams(
                1,
                TableRow.LayoutParams.MATCH_PARENT);
        TableRow.LayoutParams trparamsHoriz = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                1);
        TableRow.LayoutParams trparams3 = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        trparams3.span = 3;
        TableRow.LayoutParams trparamsFull = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT);
        trparamsFull.span = 10;
        int bg_color = context.getResources().getColor(R.color.colorPrimary);
        int dark_color = context.getResources().getColor(R.color.black);
        TextView empty;
        View verticalBar;
        View horizontalBar;
        // first line with only header for second dimension
        TableRow header2Row = new TableRow(context);
        header2Row.setLayoutParams(params);
        TextView header2TV = new TextView(context);
        header2TV.setLayoutParams(trparamsFull);
        header2TV.setPadding(5, 5, 5, 5);
        header2TV.setText(name2);
        header2TV.setTextColor(dark_color);
        header2Row.addView(header2TV);
        tableLayout.addView(header2Row);
        int min1 = Math.min(h1.getMin1(), h2.getMin1());
        int max1 = Math.max(h1.getMax1(), h2.getMax1());
        int min2 = Math.min(h1.getMin2(), h2.getMin2());
        int max2 = Math.max(h1.getMax2(), h2.getMax2());
        int v1, v2, vc1, vc2, a, r, g, b;
        int pMax1 = Math.max(100*h1.getvMax1()/h1.getSum(), 100*h2.getvMax1()/h2.getSum());
        int pMax2 = Math.max(100*h1.getvMax2()/h1.getSum(), 100*h2.getvMax2()/h2.getSum());
        int pMax = Math.max(100*h1.getvMax()/h1.getSum(), 100*h2.getvMax()/h2.getSum());
        int ctrst = 100;
        // main content
        for (int j=max2; j >= min2; j--) {
            TableRow jTR = new TableRow(context);
            jTR.setLayoutParams(params);
            // values2[j-min2]
            TextView v2TV = new TextView(context);
            v2TV.setLayoutParams(trparams);
            v2TV.setPadding(5, 5, 5, 5);
            v1 = h1.getPercent2(j);
            v2 = h2.getPercent2(j);
            vc1 = ctrst*v1/pMax2;
            vc2 = ctrst*v2/pMax2;
            a = Math.min(255, Math.max(vc1, vc2));
            r = Math.min(255, 10 * Math.max(vc2 - vc1, 0) + Math.min(vc1, vc2));
            g = Math.min(255, Math.min(vc1, vc2));
            b = Math.min(255, 10*Math.max(vc1 - vc2, 0) + Math.min(vc1, vc2));
//            v2TV.setText(String.format("%02d/%02d", v1, v2));
            v2TV.setText(String.format("%+02d", v2 - v1));
            v2TV.setBackgroundColor(a << 24 | r << 16 | g << 8 | b );
            jTR.addView(v2TV);
            // |
            verticalBar = new View(context);
            verticalBar.setLayoutParams(trparamsVert);
            verticalBar.setBackgroundColor(bg_color);
            jTR.addView(verticalBar);
            // j
            TextView jTV = new TextView(context);
            jTV.setLayoutParams(trparams);
            jTV.setPadding(5, 5, 5, 5);
            jTV.setText(String.format("%2d", j));
            jTV.setTextColor(dark_color);
            jTR.addView(jTV);
            // |
            verticalBar = new View(context);
            verticalBar.setLayoutParams(trparamsVert);
            verticalBar.setBackgroundColor(bg_color);
            jTR.addView(verticalBar);
            for (int i=min1; i <= max1; i++) {
                // values[i-min1][j-min2]
                TextView vTV = new TextView(context);
                vTV.setLayoutParams(trparams);
                vTV.setPadding(5, 5, 5, 5);
                v1 = h1.getPercent(i, j);
                v2 = h2.getPercent(i, j);
                vc1 = ctrst*v1/pMax;
                vc2 = ctrst*v2/pMax;
                a = Math.min(255, Math.max(vc1, vc2));
                r = Math.min(255, 10*Math.max(vc2 - vc1, 0) + Math.min(vc1, vc2));
                g = Math.min(255, Math.min(vc1, vc2));
                b = Math.min(255, 10*Math.max(vc1 - vc2, 0) + Math.min(vc1, vc2));
//                vTV.setText(String.format("%02d/%02d", v1, v2));
                vTV.setText(String.format("%+02d", v2 - v1));
                vTV.setBackgroundColor(a << 24 | r << 16 | g << 8 | b );
                jTR.addView(vTV);
            }
            tableLayout.addView(jTR);
        }
        // --------
        TableRow h1Row = new TableRow(context);
        h1Row.setLayoutParams(params);
        for (int i=min1-4; i <= max1; i++) {
            // -
            horizontalBar = new View(context);
            horizontalBar.setLayoutParams(trparamsHoriz);
            horizontalBar.setBackgroundColor(bg_color);
            h1Row.addView(horizontalBar);
        }
        tableLayout.addView(h1Row);
        // values of i and name
        TableRow header1Row = new TableRow(context);
        header1Row.setLayoutParams(params);
        empty = new TextView(context);
        empty.setLayoutParams(trparams3);
        header1Row.addView(empty);
        // |
        verticalBar = new View(context);
        verticalBar.setLayoutParams(trparamsVert);
        verticalBar.setBackgroundColor(bg_color);
        header1Row.addView(verticalBar);
        for (int i=min1; i <= max1; i++) {
            // i
            TextView iTV = new TextView(context);
            iTV.setLayoutParams(trparams);
            iTV.setPadding(5, 5, 5, 5);
            iTV.setText(String.format("%2d", i));
            iTV.setTextColor(dark_color);
            header1Row.addView(iTV);
        }
        TextView header1TV = new TextView(context);
        header1TV.setLayoutParams(trparams);
        header1TV.setPadding(5, 5, 5, 5);
        header1TV.setText(name1);
        header1TV.setTextColor(dark_color);
        header1Row.addView(header1TV);
        tableLayout.addView(header1Row);
        // --------
        TableRow h2Row = new TableRow(context);
        h2Row.setLayoutParams(params);
        for (int i=0; i < 3; i++) {
            empty = new TextView(context);
            empty.setLayoutParams(trparamsHoriz);
            h2Row.addView(empty);
        }
        // |
//        empty = new TextView(context);
//        empty.setLayoutParams(trparams);
//        h2Row.addView(empty);
        horizontalBar = new View(context);
        horizontalBar.setLayoutParams(trparamsHoriz);
        horizontalBar.setBackgroundColor(bg_color);
        h2Row.addView(horizontalBar);
        for (int i=min1; i <= max1; i++) {
            // -
            horizontalBar = new View(context);
            horizontalBar.setLayoutParams(trparamsHoriz);
            horizontalBar.setBackgroundColor(bg_color);
            h2Row.addView(horizontalBar);
        }
        tableLayout.addView(h2Row);
        // values1
        TableRow values1Row = new TableRow(context);
        values1Row.setLayoutParams(params);
        empty = new TextView(context);
        empty.setLayoutParams(trparams3);
        values1Row.addView(empty);
        // |
        verticalBar = new View(context);
        verticalBar.setLayoutParams(trparamsVert);
        verticalBar.setBackgroundColor(bg_color);
        values1Row.addView(verticalBar);
        for (int i=min1; i <= max1; i++) {
            // values1[i-min1]
            TextView v1TV = new TextView(context);
            v1TV.setLayoutParams(trparams);
            v1TV.setPadding(5, 5, 5, 5);
            v1 = h1.getPercent1(i);
            v2 = h2.getPercent1(i);
            vc1 = ctrst*v1/pMax1;
            vc2 = ctrst*v2/pMax1;
            a = Math.min(255, Math.max(vc1, vc2));
            r = Math.min(255, 10 * Math.max(vc2 - vc1, 0) + Math.min(vc1, vc2));
            g = Math.min(255, Math.min(vc1, vc2));
            b = Math.min(255, 10*Math.max(vc1 - vc2, 0) + Math.min(vc1, vc2));
//            v1TV.setText(String.format("%02d/%02d", v1, v2));
            v1TV.setText(String.format("%+02d", v2 - v1));
            v1TV.setBackgroundColor(a << 24 | r << 16 | g << 8 | b );
            values1Row.addView(v1TV);
        }
        tableLayout.addView(values1Row);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        recompute();
    }
}
