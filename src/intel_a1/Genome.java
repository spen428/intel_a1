package intel_a1;

public class Genome {

    private final int[] coefficients;

    public Genome(int... coefficients) {
        super();
        this.coefficients = coefficients;
    }

    public Genome(String binaryString) {
        super();
        this.coefficients = new int[binaryString.length() / Integer.SIZE];
        for (int i = 0; i < this.coefficients.length; i++) {
            String s = binaryString.substring(i * Integer.SIZE,
                    (i + 1) * Integer.SIZE);
            this.coefficients[i] = Integer.parseUnsignedInt(s, 2);
        }
    }

    @Override
    public Genome clone() {
        return new Genome(this.toString());
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < this.coefficients.length; i++) {
            String binaryString = Integer.toBinaryString(this.coefficients[i]);
            /* Pad with leading zeroes where necessary */
            s.append(String.format("%" + Integer.SIZE + "s", binaryString)
                    .replace(' ', '0'));
        }
        return s.toString();
    }

    /**
     * @return the parameters encoded by this {@link Genome}
     */
    public int[] getParameters() {
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
        /* Non-random single-point crossover */

        // This genotype
        String s1 = this.toString();
        int aLen = s1.length();
        String a1 = s1.substring(0, aLen / 2);
        String a2 = s1.substring(aLen / 2, aLen);

        // Spouse genotype
        String s2 = genome.toString();
        int bLen = s2.length();
        String b1 = s2.substring(0, bLen / 2);
        String b2 = s2.substring(bLen / 2, bLen);

        // Combine
        String newS1 = a1 + b2;
        String newS2 = b1 + a2;

        // System.out.printf("P1 : %s|%s%nP2 : %s|%s%n", a1, a2, b1, b2);
        // System.out.printf("C1 : %s|%s%nC2 : %s|%s%n", a1, b2, b1, a2);

        return new Genome[] { new Genome(newS1), new Genome(newS2) };
    }

    /**
     * Flip one random bit of the genome sequence.
     */
    public void mutate() {
        int bit = Main.RNG.nextInt(Integer.SIZE);
        int coeff = Main.RNG.nextInt(this.coefficients.length);
        this.coefficients[coeff] ^= (1 << bit);
    }

}
