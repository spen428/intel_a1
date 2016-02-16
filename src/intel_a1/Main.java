package intel_a1;

import java.util.Random;

public class Main {

    public static final Random RNG = new Random();

    /** Chance of mutation */
    public static final float MUTATE_RATE = 0.01f;

    public static final int POPULATION_SIZE = 10;
    public static final int NUM_GENERATIONS = 1;

    /** Minimum coeff values */
    private static final int[] MIN = new int[] { 0, 0, 0, 0, 0 };
    /** Maximum coeff values */
    private static final int[] MAX = new int[] { 100, 100, 100, 100, 100 };

    public static void main(String[] args) {
        Candidate[] candidates = new Candidate[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            candidates[i] = new Candidate(new Genotype(randomGenomeSequence()));
        }

        Population pop = new Population(candidates);
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            pop.step();
        }
    }

    private static String randomGenomeSequence() {
        /* Randomly generate integers between given min and max values */
        int[] val = new int[MIN.length];
        for (int i = 0; i < val.length; i++) {
            val[i] = RNG.nextInt((MAX[i] + 1) - MIN[i]) + MIN[i];
        }

        /* Convert integers into a long binary string */
        String sequence = "";
        for (int i = 0; i < val.length; i++) {
            sequence += String.format("%32s", Integer.toBinaryString(val[i]))
                    .replace(' ', '0');
        }
        return sequence;
    }

}
