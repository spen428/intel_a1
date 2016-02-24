package intel_a1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main implements Runnable {

    /**
     * Seed for the random number generator, stored so that simulations can be
     * repeated
     */
    public static final long RANDOM_SEED = System.nanoTime();

    /**
     * Single instance of random number generator
     */
    public static final Random RNG = new Random(RANDOM_SEED);

    /**
     * Data points from the curve we are trying to fit
     */
    public static final Point[] DATAPOINTS = loadDataPoints();

    /**
     * Number of {@link Individual} to keep alive in each iteration
     */
    public static final int POPULATION_SIZE = 50;

    /**
     * Number of iterations of the algorithm to perform
     */
    public static final int NUM_GENERATIONS = 10000;

    /**
     * Chance of mutation for each {@link Individual} per iteration
     */
    public static final float MUTATE_RATE = 0.01f;

    /**
     * Number of individuals that "survive" and produce offspring
     */
    public static final int NUM_SURVIVORS = (int) (POPULATION_SIZE * 0.25f);

    /**
     * How many of the survivors are elite individuals (top fitness rank)
     */
    public static final int NUM_ELITES = (int) (NUM_SURVIVORS * 0.2f);

    /**
     * Number of coefficients (the degree of the polynomial + 1)
     */
    public static final int NUM_PARAMS = 6;

    @Override
    public void run() {
        Individual[] individuals = new Individual[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            individuals[i] = new Individual(new Genome(NUM_PARAMS));
        }

        Population pop = new Population(individuals);
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            pop.step();
        }

        Individual best = pop.getBestSolution();
        int[] p = best.getGenome().getParameters();
        System.out.printf("Best solution after %d generations"
                + " with a population size of %d:%n"
                + "%.1f = %8d %8d %8d %8d %8d %8d%n" + "Random seed was: %d%n",
                NUM_GENERATIONS, POPULATION_SIZE, best.evaluateFitness(), p[0],
                p[1], p[2], p[3], p[4], p[5], RANDOM_SEED);
    }

    public static void main(String[] args) {
        new Main().run();
    }

    private static Point[] loadDataPoints() {
        ArrayList<Point> points = new ArrayList<>();
        File f = new File("datfile.dat");
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\\s+");
                if (tokens.length != 2) {
                    continue;
                } else {
                    double x = Double.valueOf(tokens[0]);
                    double y = Double.valueOf(tokens[1]);
                    points.add(new Point(x, y));
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return points.toArray(new Point[0]);
    }

}
