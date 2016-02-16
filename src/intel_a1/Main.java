package intel_a1;

import java.util.Random;

public class Main {

    public static final Random rng = new Random();

    /** Minimum coeff values */
    private static final int[] min = new int[] { 0, 0, 0, 0, 0 };
    /** Maximum coeff values */
    private static final int[] max = new int[] { 100, 100, 100, 100, 100 };

    public static void main(String[] args) {
        Candidate c1 = new Candidate(new Genotype(randomGenomeSequence()));
        Candidate c2 = new Candidate(new Genotype(randomGenomeSequence()));
        Candidate[] n = c1.breed(c2);

        System.out.println(c1.f(0));
        System.out.println(c2.f(0));
        System.out.println(n[0].f(0));
        System.out.println(n[1].f(0));

        c1.mutate();
    }

    private static String randomGenomeSequence() {
        /* Randomly generate integers between given min and max values */
        int[] val = new int[min.length];
        for (int i = 0; i < val.length; i++) {
            val[i] = rng.nextInt((max[i] + 1) - min[i]) + min[i];
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
