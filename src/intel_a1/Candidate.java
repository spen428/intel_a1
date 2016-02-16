package intel_a1;

public class Candidate {

    private final Genome genome;

    public Candidate(Genome genome) {
        super();
        this.genome = genome;
    }

    public Genome getGenome() {
        return this.genome;
    }

    public void mutate() {
        this.genome.mutate();
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
        Genome[] newGenomes = this.genome.getCrossovers(spouse.getGenome());

        /* Create children based on new genome */
        return new Candidate[] { new Candidate(newGenomes[0]),
                new Candidate(newGenomes[1]) };
    }

    public void solve() {

    }

}
