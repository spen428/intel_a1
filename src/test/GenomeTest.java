package test;

import static org.junit.Assert.*;

import org.junit.Test;

import intel_a1.Genome;
import intel_a1.Main;

@SuppressWarnings("static-method")
public class GenomeTest {

    @Test
    public final void testClone() {
        Genome g1 = new Genome(Main.NUM_PARAMS);
        Genome g2 = g1.clone();
        assertEquals(g1.toString(), g2.toString());

        int[] p1 = g1.getParameters();
        int[] p2 = g2.getParameters();
        assertEquals(p1.length, p2.length);
        for (int i = 0; i < p1.length; i++) {
            assertEquals(p1[i], p2[i]);
        }
    }

    @Test
    public final void testGetParameters() {
        int[] testParams = new int[] { -127, -50, 0, 50, 127 };
        Genome genome = new Genome(testParams);
        int[] geneParams = genome.getParameters();
        for (int i = 0; i < testParams.length; i++) {
            assertEquals(testParams[i], geneParams[i]);
        }
    }

    @Test
    public final void testToString() {
        int[] testParams = new int[] { -1000, -129, -1, 0, 127, 1000 };
        String[] testStrings = new String[] { pad("10000011000", '1'),
                pad("101111111", '1'), pad("1", '1'), pad("0", '0'),
                pad("1111111", '0'), pad("1111101000", '0') };
        int expectedGenomeLength = Genome.BITS * testParams.length;

        Genome genome = new Genome(testParams);
        String toString = genome.toString();
        assertEquals(expectedGenomeLength, toString.length());

        for (int i = 0; i < testParams.length; i++) {
            String substring = toString.substring(i * Genome.BITS,
                    (i + 1) * Genome.BITS);
            assertEquals(testStrings[i], substring);
        }
    }

    private static String pad(String s, char c) {
        return String.format("%" + Genome.BITS + "s", s).replace(' ', c);
    }

    @Test
    public final void testMutate() {
        Genome genome = new Genome(Main.NUM_PARAMS);
        int parityBefore = 0;
        for (char c : genome.toString().toCharArray()) {
            if (c == '1') {
                parityBefore++;
            }
        }

        genome.mutate();
        int parityAfter = 0;
        for (char c : genome.toString().toCharArray()) {
            if (c == '1') {
                parityAfter++;
            }
        }

        /* Should flip exactly one bit */
        assertNotEquals(parityBefore, parityAfter);
    }

    @Test
    public final void testGetCrossovers() {
        // TODO
    }

}
