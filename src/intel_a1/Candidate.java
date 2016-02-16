package intel_a1;

public class Candidate {

    private final Genotype genotype;

    public Candidate(Genotype genotype) {
        super();
        this.genotype = genotype;
    }

    public Genotype getGenotype() {
        return this.genotype;
    }

    public void mutate() {
        this.genotype.mutate();
    }

    /**
     * Produce two offspring with a given "spouse"
     * 
     * @param spouse
     *            another {@link Candidate} to breed with
     * @return two child {@link Candidate}
     */
    public Candidate[] breed(Candidate spouse) {
        if (this == spouse) {
            throw new IllegalArgumentException("Cannot breed with self.");
        }

        /* Produce a new genome based on both parents */
        Genotype[] newGenomes = this.genotype
                .getCrossovers(spouse.getGenotype());

        /* Create children based on new genome */
        return new Candidate[] { new Candidate(newGenomes[0]),
                new Candidate(newGenomes[1]) };
    }

    public double f(double x) {
        double ans = 0;
        double[] params = this.genotype.getParameters();
        for (int i = 0; i < params.length; i++) {
            ans += params[i] * Math.pow(x, i);
        }
        return ans;
    }

}
