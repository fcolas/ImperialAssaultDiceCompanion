package net.franciscolas.imperialassaultdicecompanion;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

/**
 * One-dimensional histogram
 */
public class Histogram2D {
    private int[][] values = null;
    private int min1 = 0;
    private int max1 = 0;
    private int min2 = 0;
    private int max2 = 0;
    private int sum = 0;
    private int weightedSum1 = 0;
    private int weightedSum2 = 0;
    private int vMax = 0;

    public void put(int i, int j, int v) {
        if (values == null) {
            // new histogram
            values = new int[][] {{v}};
            min1 = i;
            max1 = i;
            min2 = j;
            max2 = j;
        } else if ((i <= max1) && (i >= min1) && (j <= max2) && (j >= min2)) {
            // value in current bounds
            values[i-min1][j-min2] += v;
        } else {
            // value outside bounds
            // new bounds
            int new_min1 = Math.min(min1, i);
            int new_max1 = Math.max(max1, i);
            int new_min2 = Math.min(min2, j);
            int new_max2 = Math.max(max2, j);
            // new table
            int[][] new_values = new int[1+new_max1-new_min1][1+new_max2-new_min2];
            for (int ii=new_min1; ii <= new_max1; ii++) {
                for (int jj=new_min2; jj <= new_max2; jj++) {
                    if ((ii <= max1) && (ii >= min1) && (jj <= max2) && (jj >= min2)) {
                        new_values[ii-new_min1][jj-new_min2] = values[ii-min1][jj-min2];
                    } else {
                        new_values[ii-new_min1][jj-new_min2] = 0;
                    }
                }
            }
            values = new_values;
            min1 = new_min1;
            max1 = new_max1;
            min2 = new_min2;
            max2 = new_max2;
            // updating with new value
            values[i-min1][j-min2] += v;
        }
        sum += v;
        weightedSum1 += i*v;
        weightedSum2 += j*v;
        vMax = Math.max(vMax, values[i-min1][j-min2]);
    }

    public int get(int i, int j) {
        if (values == null) {
            return 0;
        } else if ((i <= max1) && (i >= min1) && (j <= max2) && (j >= min2)) {
            return values[i-min1][j-min2];
        } else {
            return 0;
        }
    }

    public int getMin1() {
        return min1;
    }

    public int getMax1() {
        return max1;
    }

    public int getMin2() {
        return min2;
    }

    public int getMax2() {
        return max2;
    }

    public float[] getAverages() {
        if (sum == 0) {
            return new float[] {0, 0};
        } else {
            return new float[] {(float)weightedSum1/(float)sum, (float)weightedSum2/(float)sum};
        }
    }

    public void populateTable(TableLayout tableLayout, String name1, String name2) {
        Context context = tableLayout.getContext();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        android.widget.TableRow.LayoutParams trparams = new TableRow.LayoutParams(
                android.widget.TableRow.LayoutParams.WRAP_CONTENT,
                android.widget.TableRow.LayoutParams.WRAP_CONTENT);
        TableRow header2Row = new TableRow(context);
        header2Row.setLayoutParams(params);
        TextView header2TV = new TextView(context);
        header2TV.setLayoutParams(trparams);
        header2TV.setPadding(5, 5, 5, 5);
        header2TV.setText(name2);
        header2Row.addView(header2TV);
        tableLayout.addView(header2Row);
        int factor = (int)Math.pow(10, Math.max((Math.ceil(Math.log10(vMax))-2), 0));
        System.out.println(vMax + " " + factor);
        for (int j=max2; j >= min2; j--) {
            TableRow jTR = new TableRow(context);
            jTR.setLayoutParams(params);
            TextView jTV = new TextView(context);
            jTV.setLayoutParams(trparams);
            jTV.setPadding(5, 5, 5, 5);
            jTV.setText(String.valueOf(j));
            jTR.addView(jTV);
            for (int i=min1; i <= max1; i++) {
                TextView vTV = new TextView(context);
                vTV.setLayoutParams(trparams);
                vTV.setPadding(5, 5, 5, 5);
                vTV.setText(String.valueOf(values[i-min1][j-min2]/factor));
                vTV.setBackgroundColor((100 * values[i-min1][j-min2] / vMax) << 24);
                jTR.addView(vTV);
            }
            tableLayout.addView(jTR);
        }
        TableRow header1Row = new TableRow(context);
        header1Row.setLayoutParams(params);
        TextView empty = new TextView(context);
        empty.setLayoutParams(trparams);
        header1Row.addView(empty);
        for (int i=min1; i <= max1; i++) {
            TextView iTV = new TextView(context);
            iTV.setLayoutParams(trparams);
            iTV.setPadding(5, 5, 5, 5);
            iTV.setText(String.valueOf(i));
            header1Row.addView(iTV);
        }
        TextView header1TV = new TextView(context);
        header1TV.setLayoutParams(trparams);
        header1TV.setPadding(5, 5, 5, 5);
        header1TV.setText(name1);
        header1Row.addView(header1TV);
        tableLayout.addView(header1Row);
    }

}
