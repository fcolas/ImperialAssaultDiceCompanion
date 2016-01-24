package net.franciscolas.imperialassaultdicecompanion;

/**
 * One-dimensional histogram
 */
public class Histogram1D {
    private int[] values = null;
    private int min = 0;
    private int max = 0;
    private int sum = 0;
    private int weightedSum = 0;

    public void put(int i, int v) {
        if (values == null) {
            // new histogram
            values = new int[] {v};
            min = i;
            max = i;
            sum = v;
            weightedSum = i*v;
        } else if ((i <= max) && (i >= min)) {
            // value in current bounds
            values[i-min] += v;
            sum += v;
            weightedSum += i*v;
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
            sum += v;
            weightedSum += i*v;
        }
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
}
