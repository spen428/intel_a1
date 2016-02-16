package intel_a1;

public class Genotype {

    private String sequence;

    public Genotype(String sequence) {
        super();
        this.sequence = sequence;
    }

    /**
     * @return a {@link String} representing the genome sequence
     */
    public String getSequence() {
        // New instance so the reference cannot be reassigned
        return new String(this.sequence);
    }

    /**
     * @return the parameters encoded by this {@link Genotype}
     */
    public double[] getParameters() {
        int len = (this.sequence.length() / 32);
        double[] vals = new double[len];
        for (int i = 0; i < len; i++) {
            /* Split string and convert from binary to int */
            String binary = this.sequence.substring(32 * i, 32 * (i + 1));
            vals[i] = Integer.valueOf(binary, 2);
        }
        return vals;
    }

    /**
     * Perform a crossover operation between this {@link Genotype} and another
     * {@link Genotype}
     * 
     * @param genotype
     *            the genome to mix with
     * @return the two crossover solutions
     */
    public Genotype[] getCrossovers(Genotype genotype) {
        /* Non-random single-point crossover */

        // This genotype
        int aLen = this.sequence.length();
        String a1 = this.sequence.substring(0, aLen / 2);
        String a2 = this.sequence.substring(aLen / 2, aLen);

        // Spouse genotype
        int bLen = genotype.getSequence().length();
        String b1 = genotype.getSequence().substring(0, bLen / 2);
        String b2 = genotype.getSequence().substring(bLen / 2, bLen);

        // Combine
        String s1 = a1 + b2;
        String s2 = b1 + a2;

        // System.out.printf("P1 : %s|%s%nP2 : %s|%s%n", a1, a2, b1, b2);
        // System.out.printf("C1 : %s|%s%nC2 : %s|%s%n", a1, b2, b1, a2);

        return new Genotype[] { new Genotype(s1), new Genotype(s2) };
    }

    /**
     * Flip one random bit of the genome sequence.
     */
    public void mutate() {
        int bit = Main.RNG.nextInt(this.sequence.length() - 1);
        char c = this.sequence.charAt(bit);
        if (c == '0') {
            c = '1';
        } else if (c == '1') {
            c = '0';
        } else {
            throw new IllegalStateException("This should be a binary string!");
        }

        /* Flip bit */
        String newSequence = "";
        newSequence += this.sequence.substring(0, bit);
        newSequence += c;
        newSequence += this.sequence.substring(bit + 1, this.sequence.length());

        // System.out.println(this.sequence);
        // System.out.println(newSequence);

        this.sequence = newSequence;
    }

}
