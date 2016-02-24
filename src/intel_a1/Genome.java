package intel_a1;

public class Genome {

    private static final int SIZE = Byte.SIZE;
    private final byte[] coefficients;
    private String toString;

    public Genome(byte... coefficients) {
        super();
        this.coefficients = coefficients;
    }

    public Genome(String binaryString) {
        super();
        if (binaryString == null) {
            throw new IllegalArgumentException("Binary string cannot be null");
        } else if (binaryString.length() % SIZE != 0) {
            throw new IllegalArgumentException(
                    "Binary string length must " + "be a multiple of " + SIZE);
        }
        this.coefficients = new byte[binaryString.length() / SIZE];
        for (int i = 0; i < this.coefficients.length; i++) {
            String s = binaryString.substring(i * SIZE, (i + 1) * SIZE);
            this.coefficients[i] = (byte) (int) Integer.valueOf(s, 2);
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
                byte b = this.coefficients[i];
                String binaryString = Integer.toBinaryString((b + 256) % 256);
                /* Pad with leading zeroes */
                s.append(String.format("%8s", binaryString).replace(' ', '0'));
            }
            this.toString = s.toString();
        }
        return this.toString;
    }

    /**
     * @return the parameters encoded by this {@link Genome}
     */
    public byte[] getParameters() {
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
        int bit = Main.RNG.nextInt(SIZE);
        int coeff = Main.RNG.nextInt(this.coefficients.length);
        this.coefficients[coeff] ^= (1 << bit);
        changed();
    }

    private void changed() {
        this.toString = null; // Unset memoized toString() value
    }

}
