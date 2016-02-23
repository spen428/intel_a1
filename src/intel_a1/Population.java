package intel_a1;

import java.util.Arrays;

public class Population {

    private final Individual[] individuals;

    public Population(Individual... individuals) {
        super();
        this.individuals = individuals;
    }

    /**
     * Iterate over one generation of this population
     */
    public void step() {
        // System.out.println("\n--- Generation summary ---");

        // Do mutations
        int numMutations = 0;
        for (Individual c : this.individuals) {
            if (Main.RNG.nextFloat() <= Main.MUTATE_RATE) {
                c.mutate();
                numMutations++;
            }
        }

        // System.out.printf("There were a total of %d mutations%n",
        // numMutations);

        // Select fittest individuals
        Individual[] survivors = getSurvivors();

        // Add survivors to population
        for (int i = 0; i < survivors.length; i++) {
            this.individuals[i] = survivors[i];
            // System.out.printf(
            // "Indiv #%02d has a fitness of %.20f (id:%010d)%s%n", i + 1,
            // survivors[i].evaluateFitness(), survivors[i].getId(),
            // (i < Main.NUM_ELITES) ? " (Elite)" : "");
        }

        // Breed and add offspring to population
        for (int i = survivors.length; i < this.individuals.length; i += 2) {
            int idx0 = Main.RNG.nextInt(survivors.length);
            int idx1 = Main.RNG.nextInt(survivors.length);

            Individual[] children = survivors[idx0].breed(survivors[idx1]);

            // Have to check idx because we are going up by +2 every loop
            if (i < this.individuals.length) {
                this.individuals[i] = children[0];
            } else {
                break;
            }

            // System.out.printf("Child #%02d has a fitness of %.20f
            // (id:%010d)%n",
            // i - survivors.length + 1,
            // this.individuals[i].evaluateFitness(),
            // this.individuals[i].getId());

            if (i + 1 < this.individuals.length) {
                this.individuals[i + 1] = children[1];
            } else {
                break;
            }

            // System.out.printf("Child #%02d has a fitness of %.20f
            // (id:%010d)%n",
            // i - survivors.length + 2,
            // this.individuals[i + 1].evaluateFitness(),
            // this.individuals[i + 1].getId());
        }

        double avgFit = 0;
        for (Individual i : this.individuals) {
            avgFit += i.evaluateFitness();
        }
        System.out.println(avgFit / this.individuals.length);
    }

    private Individual[] getSurvivors() {
        Individual[] survivors = new Individual[Main.NUM_SURVIVORS];

        // Order by fitness, this automatically calls Individual.evaluate()
        Arrays.sort(this.individuals);

        // Add elites
        for (int i = 0; i < Main.NUM_ELITES; i++) {
            survivors[i] = this.individuals[i];
        }

        // Rest of population competes for survival
        int idx = Main.NUM_ELITES;
        for (Individual iv : select(
                Arrays.copyOfRange(this.individuals, Main.NUM_ELITES,
                        this.individuals.length),
                Main.NUM_SURVIVORS - Main.NUM_ELITES)) {
            survivors[idx++] = iv;
        }

        return survivors;
    }

    /**
     * Perform selection of next generation based on fitness
     * 
     * @param pop
     *            array of {@link Individual}
     * @param numSurvivors
     *            number of {@link Individual} to keep alive
     * @return array of {@link Individual} that "survivied"
     */
    private static Individual[] select(Individual[] pop, int numSurvivors) {
        Individual[] survivors = new Individual[numSurvivors];
        // Randomly select remaining survivors, can select clones
        double totalFitness = 0;
        for (int i = 0; i < pop.length; i++) {
            totalFitness += pop[i].evaluateFitness();
        }

        for (int i = 0; i < survivors.length; i++) {
            // A binary search would be faster, but oh well
            double roll = Main.RNG.nextDouble() * totalFitness;
            double prevFitness = 0;
            for (int j = 0; j < pop.length; j++) {
                double cumulativeFitness = prevFitness
                        + pop[j].evaluateFitness();
                prevFitness += cumulativeFitness;
                if (cumulativeFitness >= roll) {
                    survivors[i] = pop[j];
                    break;
                }
            }
        }
        return survivors;
    }

}
