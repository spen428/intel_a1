package test;

import intel_a1.Main;

public class SelectionTest {

    private static final int NUM_ROLLS = 100000;

    public static void main(String[] args) {
        System.out.println("Higher is better:");
        select(true);
        System.out.println("Lower is better:");
        select(false);
    }

    private static void select(boolean higherIsBetter) {
        int[] values = new int[] { 1, 2, 3, 4, 10 };
        int[] selected = new int[values.length];
        double total = 0;

        for (int i = 0; i < values.length; i++) {
            if (higherIsBetter) {
                total += values[i];
            } else {
                total += 1.0d / values[i];
            }
        }

        for (int x = 0; x < NUM_ROLLS; x++) {
            double roll = Main.RNG.nextDouble();
            double cumulative = 0;
            for (int i = 0; i < values.length; i++) {
                if (higherIsBetter) {
                    cumulative += values[i];
                } else {
                    cumulative += 1.0d / values[i];
                }
                double normalised = cumulative / total;
                if (roll <= normalised) {
                    selected[i]++;
                    break;
                }
            }
        }

        for (int i = 0; i < values.length; i++) {
            System.out.printf("%2d %5d (%5.2f%%)%n", values[i], selected[i],
                    selected[i] * 100.0d / NUM_ROLLS);
        }
    }

}
