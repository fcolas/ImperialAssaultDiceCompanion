package net.franciscolas.imperialassaultdicecompanion;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * One-dimensional histogram
 */
public class Histogram1D {
    private int[] values = null;
    private int min = 0;
    private int max = 0;
    private int sum = 0;
    private int weightedSum = 0;
    private int vMax = 0;

    public void put(int i, int v) {
        if (values == null) {
            // new histogram
            values = new int[] {v};
            min = i;
            max = i;
        } else if ((i <= max) && (i >= min)) {
            // value in current bounds
            values[i-min] += v;
        } else {
            // value outside bounds
            // new bounds
            int new_min = Math.min(min, i);
            int new_max = Math.max(max, i);
            // new table
            int[] new_values = new int[1+new_max-new_min];
            for (int ii = new_min; ii <= new_max; ii++) {
                if ((ii <= max) && (ii >= min)) {
                    new_values[ii-new_min] = values[ii-min];
                } else {
                    new_values[ii-new_min] = 0;
                }
            }
            values = new_values;
            min = new_min;
            max = new_max;
            // updating with new value
            values[i-min] += v;
        }
        sum += v;
        weightedSum += i*v;
        vMax = Math.max(vMax, values[i-min]);
    }

    public int get(int i) {
        if (values == null) {
            return 0;
        } else if ((i <= max) && (i >= min)) {
            return values[i-min];
        } else {
            return 0;
        }
    }

    public int getPercent(int i) {
        return (100 * get(i)) / sum;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public float getAverage() {
        if (sum == 0) {
            return 0;
        } else {
            return (float)weightedSum/(float)sum;
        }
    }

    public void populateTable(TableLayout tableLayout, String name) {
        Context context = tableLayout.getContext();
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        android.widget.TableRow.LayoutParams trparams = new TableRow.LayoutParams(
                android.widget.TableRow.LayoutParams.WRAP_CONTENT,
                android.widget.TableRow.LayoutParams.WRAP_CONTENT);
        TableRow valuesRow = new TableRow(context);
        TableRow indicesRow = new TableRow(context);
        valuesRow.setLayoutParams(params);
        indicesRow.setLayoutParams(params);
        TextView nTV = new TextView(context);
        TextView nameTV = new TextView(context);
        nTV.setLayoutParams(trparams);
        nameTV.setLayoutParams(trparams);
        nTV.setPadding(5, 5, 5, 5);
        nameTV.setPadding(5, 5, 5, 5);
        nTV.setText(R.string.n_label);
        nameTV.setText(name);
        valuesRow.addView(nTV);
        indicesRow.addView(nameTV);
        int factor = (int)Math.pow(10, Math.max((Math.ceil(Math.log10(vMax))-2), 0));
        System.out.println(vMax + " " + factor);
        for (int i=min; i <= max; i++) {
            TextView vTV = new TextView(context);
            TextView iTV = new TextView(context);
            vTV.setLayoutParams(trparams);
            iTV.setLayoutParams(trparams);
            vTV.setPadding(5, 5, 5, 5);
            iTV.setPadding(5, 5, 5, 5);
            vTV.setText(String.valueOf(values[i - min]/factor));
            iTV.setText(String.valueOf(i));
            vTV.setBackgroundColor((100 * values[i - min] / vMax) << 24);
            valuesRow.addView(vTV);
            indicesRow.addView(iTV);
        }
        tableLayout.addView(valuesRow);
        tableLayout.addView(indicesRow);
    }
}
