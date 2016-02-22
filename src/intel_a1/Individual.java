package intel_a1;

public class Individual implements Comparable<Individual> {

    private static long counter = 0;
    public static final double UNKNOWN = Double.MIN_VALUE;

    private final long id;
    private final Genome genome;
    private double fitness;

    public Individual(Genome genome) {
        super();
        this.genome = genome;
        this.fitness = UNKNOWN;
        this.id = counter++;
    }

    public Genome getGenome() {
        return this.genome;
    }

    /**
     * Evaluate the fitness of this {@link Individual}, a lower value is better
     * 
     * @return the fitness value as a {@link Double}
     */
    public double evaluateFitness() {
        if (this.fitness == UNKNOWN) {
            // TODO
            this.fitness = Main.RNG.nextDouble();
        }
        return this.fitness;
    }

    public void mutate() {
        this.genome.mutate();
    }

    @Override
    public Individual clone() {
        return new Individual(this.genome.clone());
    }

    @Override
    public int compareTo(Individual o) {
        return Double.compare(this.evaluateFitness(), o.evaluateFitness());
    }

    /**
     * Produce two offspring with a given "spouse"
     * 
     * @param spouse
     *            another {@link Individual} to breed with
     * @return two child {@link Individual}
     */
    public Individual[] breed(Individual spouse) {
        if (this == spouse) {
            return new Individual[] { this.clone(), this.clone() };
        }

        /* Produce a new genome based on both parents */
        Genome[] newGenomes = this.genome.getCrossovers(spouse.getGenome());

        /* Create children based on new genome */
        return new Individual[] { new Individual(newGenomes[0]),
                new Individual(newGenomes[1]) };
    }

    public double f(double x) {
        double ans = 0;
        int[] params = this.genome.getParameters();
        for (int i = 0; i < params.length; i++) {
            ans += params[i] * Math.pow(x, i);
        }
        return ans;
    }

    public long getId() {
        return this.id;
    }

}
