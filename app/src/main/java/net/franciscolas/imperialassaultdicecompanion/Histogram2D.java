package net.franciscolas.imperialassaultdicecompanion;

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

    public void put(int i, int j, int v) {
        if (values == null) {
            // new histogram
            values = new int[][] {{v}};
            min1 = i;
            max1 = i;
            min2 = j;
            max2 = j;
            sum = v;
            weightedSum1 = i*v;
            weightedSum2 = j*v;
        } else if ((i <= max1) && (i >= min1) && (j <= max2) && (j >= min2)) {
            // value in current bounds
            values[i-min1][j-min2] += v;
            sum += v;
            weightedSum1 += i*v;
            weightedSum2 += j*v;
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
                    if ((i <= max1) && (i >= min1) && (j <= max2) && (j >= min2)) {
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
            sum += v;
            weightedSum1 += i*v;
            weightedSum1 += j*v;
        }
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
}
