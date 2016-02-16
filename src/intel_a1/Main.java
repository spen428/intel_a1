package intel_a1;

public class Main {

    public static void main(String[] args) {
        Candidate c1 = new Candidate(new Genome("abcdefg"));
        Candidate c2 = new Candidate(new Genome("1234567"));
        
        c1.breed(c2);
    }

}
