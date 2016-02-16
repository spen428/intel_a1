package intel_a1;

public class Genome {

    private final String sequence;

    public Genome(String sequence) {
        super();
        this.sequence = sequence;
    }

    public String getSequence() {
        return this.sequence;
    }

    /**
     * Perform a crossover operation between this genome and another genome
     * 
     * @param genome
     *            the genome to mix with
     * @return the two crossover solutions
     */
    public Genome[] getCrossovers(Genome genome) {
        /* Non-random single-point crossover */

        // This genome
        int aLen = this.sequence.length();
        String a1 = this.sequence.substring(0, aLen / 2);
        String a2 = this.sequence.substring(aLen / 2, aLen);

        // Spouse genome
        int bLen = genome.getSequence().length();
        String b1 = genome.getSequence().substring(0, bLen / 2);
        String b2 = genome.getSequence().substring(bLen / 2, bLen);

        // Combine
        String s1 = a1 + b2;
        String s2 = b1 + a2;

        System.out.printf("P1 : %s|%s%nP2 : %s|%s%n", a1, a2, b1, b2);
        System.out.printf("C1 : %s|%s%nC2 : %s|%s%n", a1, b2, b1, a2);

        return new Genome[] { new Genome(s1), new Genome(s2) };
    }

    public void mutate() {

    }

}
