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
    private int[] values1 = null;
    private int[] values2 = null;
    private int min1 = 0;
    private int max1 = 0;
    private int min2 = 0;
    private int max2 = 0;
    private int sum = 0;
    private int weightedSum1 = 0;
    private int weightedSum2 = 0;
    private int vMax = 0;

    public int getvMax() {
        return vMax;
    }

    public int getvMax1() {
        return vMax1;
    }

    public int getvMax2() {
        return vMax2;
    }

    private int vMax1 = 0;
    private int vMax2 = 0;

    public void put(int i, int j, int v) {
        if (values == null) {
            // new histogram
            values = new int[][] {{v}};
            values1 = new int[] {v};
            values2 = new int[] {v};
            min1 = i;
            max1 = i;
            min2 = j;
            max2 = j;
        } else if ((i <= max1) && (i >= min1) && (j <= max2) && (j >= min2)) {
            // value in current bounds
            values[i-min1][j-min2] += v;
            values1[i-min1] += v;
            values2[j-min2] += v;
        } else {
            // value outside bounds
            // new bounds
            int new_min1 = Math.min(min1, i);
            int new_max1 = Math.max(max1, i);
            int new_min2 = Math.min(min2, j);
            int new_max2 = Math.max(max2, j);
            // new table
            int[][] new_values = new int[1+new_max1-new_min1][1+new_max2-new_min2];
            int[] new_values1 = new int[1+new_max1-new_min1];
            int[] new_values2 = new int[1+new_max2-new_min2];
            for (int ii=new_min1; ii <= new_max1; ii++) {
                for (int jj=new_min2; jj <= new_max2; jj++) {
                    if ((ii <= max1) && (ii >= min1) && (jj <= max2) && (jj >= min2)) {
                        new_values[ii-new_min1][jj-new_min2] = values[ii-min1][jj-min2];
                    } else {
                        new_values[ii-new_min1][jj-new_min2] = 0;
                    }
                }
                if ((ii <= max1) && (ii >= min1)) {
                    new_values1[ii-new_min1] = values1[ii - min1];
                } else {
                    new_values1[ii-new_min1] = 0;
                }
            }
            for (int jj=new_min2; jj <= new_max2; jj++) {
                if ((jj <= max2) && (jj >= min2)) {
                    new_values2[jj - new_min2] = values2[jj - min2];
                } else {
                    new_values2[jj - new_min2] = 0;
                }
            }
            values = new_values;
            values1 = new_values1;
            values2 = new_values2;
            min1 = new_min1;
            max1 = new_max1;
            min2 = new_min2;
            max2 = new_max2;
            // updating with new value
            values[i-min1][j-min2] += v;
            values1[i-min1] += v;
            values2[j-min2] += v;
        }
        sum += v;
        weightedSum1 += i*v;
        weightedSum2 += j*v;
        vMax = Math.max(vMax, values[i-min1][j-min2]);
        vMax1 = Math.max(vMax1, values1[i-min1]);
        vMax2 = Math.max(vMax2, values2[j-min2]);
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

    public int getPercent(int i, int j) {
        return (100 * get(i, j))/sum;
    }

    public int get1(int i) {
        if (values1 == null) {
            return 0;
        } else if ((i <= max1) && (i >= min1)) {
            return values1[i-min1];
        } else {
            return 0;
        }
    }

    public int getPercent1(int i) {
        return (100 * get1(i))/sum;
    }

    public int get2(int j) {
        if (values2 == null) {
            return 0;
        } else if ((j <= max2) && (j >= min2)) {
            return values2[j-min2];
        } else {
            return 0;
        }
    }

    public int getPercent2(int j) {
        return (100 * get2(j))/sum;
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

    public int getSum() {
        return sum;
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
        // main content
        for (int j=max2; j >= min2; j--) {
            TableRow jTR = new TableRow(context);
            jTR.setLayoutParams(params);
            // values2[j-min2]
            TextView v2TV = new TextView(context);
            v2TV.setLayoutParams(trparams);
            v2TV.setPadding(5, 5, 5, 5);
            v2TV.setText(String.format("%02d", getPercent2(j)));
            v2TV.setBackgroundColor((100*get2(j)/vMax2) << 24);
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
                vTV.setText(String.format("%02d", getPercent(i, j)));
                vTV.setBackgroundColor((100*get(i, j)/vMax) << 24);
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
            v1TV.setText(String.format("%20d", getPercent1(i)));
            v1TV.setBackgroundColor((100*get1(i)/vMax1) << 24);
            values1Row.addView(v1TV);
        }
        tableLayout.addView(values1Row);
    }
}
