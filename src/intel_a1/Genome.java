package intel_a1;

public class Genome {

    /**
     * Number of bits used to encode coefficient values.
     */
    public static final int BITS = 16; // Must be <= 30
    public static final int MAX_UNSIGNED = (int) Math.pow(2, BITS);
    public static final boolean HIGHER_FITNESS_IS_BETTER = false;

    private final int[] coefficients;
    private String toString;

    /**
     * Instantiate a new {@link Genome} with a random genome sequence
     * 
     * @param numParams
     *            the number of parameters that this {@link Genome} encodes
     */
    public Genome(int numParams) {
        this(randomGenomeSequence(numParams));
    }

    /**
     * Instantate a new {@link Genome} that encodes the given coefficients
     * 
     * @param coefficients
     *            array of coefficient values
     */
    public Genome(int[] coefficients) {
        super();
        this.coefficients = coefficients;
    }

    public Genome(String binaryString) {
        super();
        if (binaryString == null) {
            throw new IllegalArgumentException("Binary string cannot be null");
        } else if (binaryString.length() % BITS != 0) {
            throw new IllegalArgumentException(
                    "Binary string length must be a multiple of " + BITS);
        }
        this.coefficients = new int[binaryString.length() / BITS];
        for (int i = 0; i < this.coefficients.length; i++) {
            String s = binaryString.substring(i * BITS, (i + 1) * BITS);
            this.coefficients[i] = Integer.parseUnsignedInt(s, 2);
        }
    }

    @Override
    public Genome clone() {
        return new Genome(this.toString());
    }

    @Override
    public String toString() {
        /*
         * https://stackoverflow.com/questions/12310017/how-to-convert-a-byte-to
         * -its-binary-string-representation
         */
        if (this.toString == null) {
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < this.coefficients.length; i++) {
                int val = this.coefficients[i];
                String binaryString = Integer
                        .toBinaryString((val + MAX_UNSIGNED) % MAX_UNSIGNED);
                /* Pad with leading zeroes */
                s.append(String.format("%" + BITS + "s", binaryString)
                        .replace(' ', '0'));
            }
            this.toString = s.toString();
        }
        return this.toString;
    }

    /**
     * @return the parameters encoded by this {@link Genome}
     */
    public int[] getParameters() {
        // TODO: Prevent external modification
        return this.coefficients;
    }

    /**
     * Perform a crossover operation between this {@link Genome} and another
     * {@link Genome}
     * 
     * @param genome
     *            the {@link Genome} to mix with
     * @return the two crossover solutions
     */
    public Genome[] getCrossovers(Genome genome) {
        /* Random single-point crossover */

        String s1 = this.toString(); // This genotype
        String s2 = genome.toString(); // Spouse genotype

        if (s1.length() != s2.length()) {
            throw new IllegalStateException("Genomes are of differing lengths");
        }

        int crossPoint = Main.RNG.nextInt(s1.length());

        /* Split genomes at crossover point */
        String a1 = s1.substring(0, crossPoint);
        String a2 = s1.substring(crossPoint, s1.length());

        String b1 = s2.substring(0, crossPoint);
        String b2 = s2.substring(crossPoint, s2.length());

        // System.out.printf("P1 : %s|%s%nP2 : %s|%s%n", a1, a2, b1, b2);
        // System.out.printf("C1 : %s|%s%nC2 : %s|%s%n", a1, b2, b1, a2);

        /* Recombine and return */
        return new Genome[] { new Genome(a1 + b2), new Genome(b1 + a2) };
    }

    /**
     * Flip one random bit of the genome sequence.
     */
    public void mutate() {
        int bit = Main.RNG.nextInt(BITS);
        int coeff = Main.RNG.nextInt(this.coefficients.length);
        this.coefficients[coeff] ^= (1 << bit);
        changed();
    }

    private void changed() {
        this.toString = null; // Unset memoized toString() value
    }

    private static int[] randomGenomeSequence(int numParams) {
        /* Randomly generate integers between given min and max values */
        int[] vals = new int[numParams];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = Main.RNG.nextInt(MAX_UNSIGNED);
        }
        return vals;
    }

}
