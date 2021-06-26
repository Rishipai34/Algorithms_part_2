import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private final WordNet wordnet;
    public Outcast(WordNet given) {
        this.wordnet = given;
    }

    public String outcast(String[] nouns) {
        int[] OutcastDistance = new int[nouns.length];
        int longestLength = Integer.MIN_VALUE;
        int maxIndex = -1;
        int distance;
        for (int i = 0; i < nouns.length; i++) {
            for (String noun : nouns) {
                distance = wordnet.distance(nouns[i], noun);
                OutcastDistance[i] += distance;
            }
        }
        for (int i = 0; i < nouns.length; i++) {
            if (OutcastDistance[i] > longestLength) {
                maxIndex = i;
                longestLength = OutcastDistance[i];
            }
        }
        return nouns[maxIndex];
    }
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
