package intel_a1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static final Random RNG = new Random();

    public static final Point[] DATAPOINTS = loadDataPoints();

    /** Minimum coeff values */
    private static final int[] MIN = new int[] { -100, -100, -100, -100, -100,
            -100 };
    /** Maximum coeff values */
    private static final int[] MAX = new int[] { 100, 100, 100, 100, 100, 100 };

    public static final int POPULATION_SIZE = 24;
    public static final int NUM_GENERATIONS = 100;

    /** Chance of mutation */
    public static final float MUTATE_RATE = 0.01f;

    /** Number of individuals that "survive" and produce offspring */
    public static final int NUM_SURVIVORS = (int) (POPULATION_SIZE * 0.25f);

    /** How many of the survivors are elite individuals (top fitness rank) */
    public static final int NUM_ELITES = (int) (NUM_SURVIVORS * 0.2f);

    public static void main(String[] args) {
        Individual[] individuals = new Individual[POPULATION_SIZE];
        for (int i = 0; i < POPULATION_SIZE; i++) {
            individuals[i] = new Individual(new Genome(randomGenomeSequence()));
        }

        Population pop = new Population(individuals);
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            pop.step();
        }
    }

    private static int[] randomGenomeSequence() {
        /* Randomly generate integers between given min and max values */
        int[] vals = new int[MIN.length];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = RNG.nextInt((MAX[i] + 1) - MIN[i]) + MIN[i];
        }
        return vals;
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
