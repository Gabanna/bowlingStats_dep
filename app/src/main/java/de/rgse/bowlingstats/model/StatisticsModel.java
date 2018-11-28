package de.rgse.bowlingstats.model;

public class StatisticsModel {

    // Berechnet das Maximum der Arrayeintraege
    public double max(double[] a) {
        double max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }

    // Berechnet den Mittelwert der Arrayeintraege
    public double mean(double[] a) {
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + a[i];
        }
        return sum / a.length;
    }

    // Berechnet die korrigierte Stichprobenvarianz
    public double var(double[] a) {
        double m = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; i++) {
            sum = sum + (a[i] - m) * (a[i] - m);
        }
        return sum / (a.length - 1);
    }

    // Berechnet die Standardabweichung
    public double stddv(double[] a) {
        return Math.sqrt(var(a));
    }

    // Ermittelt die Position des kleinsten Wertes 
    // aus dem Indexbereich [low,high]
    public int minPos(double[] a, int low, int high) {
        double min = a[low];
        int minPos = low;
        for (int i = low; i < high; i++) {
            if (min > a[i]) {
                min = a[i];
                minPos = i;
            }
        }
        return minPos;
    }

    // Vertauscht den Wert von Eintrag an Position i 
    // mit dem Eintrag an Position j
    public void swap(double[] a, int i, int j) {
        double t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    // Sortiert die Werte im Array aufsteigend
    public void sort(double[] a) {
        for (int i = 0; i < a.length; i++) {
            int m = minPos(a, i, a.length);
            swap(a, m, i);
        }
    }

    // Berechnet den Median der Arrayeintraege
    public double median(double[] a) {
        sort(a);
        if (a.length % 2 == 0) {
            return (a[a.length / 2 - 1] + a[a.length / 2]) / 2.0;
        } else {
            return (a[a.length / 2]);
        }
    }
}
