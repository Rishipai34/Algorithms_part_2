import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;
import java.util.HashMap;


public class WordNet {
    private final HashMap<Integer, String[]> IdToNoun;
    private final HashMap<String, Bag<Integer>> NounToId;
    private final SAP shortest;

    //constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        IdToNoun = new HashMap<>();
        NounToId = new HashMap<>();
        In synset = new In(synsets);
        while (synset.hasNextLine()) {
            String[] read = synset.readLine().split(",");
            int id = Integer.parseInt(read[0]);
            String[] nouns = read[1].split(" ");
            IdToNoun.put(id, nouns);
            for (String noun : nouns) {
                Bag<Integer> bag;
                if (NounToId.containsKey(noun)) {
                    bag = NounToId.get(noun);
                    bag.add(id);
                    NounToId.replace(noun, bag);
                } else {
                    bag = new Bag<Integer>();
                    bag.add(id);
                    NounToId.put(noun,bag);
                }
            }
        }
        In hypernym = new In(hypernyms);
        Digraph wordNet = new Digraph(IdToNoun.size());
        int count = 0;
        while (hypernym.hasNextLine()) {
            String[] readHypernym = hypernym.readLine().split(",");
            int idHypernym = Integer.parseInt(readHypernym[0]);
            for (int i = 1; i < readHypernym.length ; i++) {
                wordNet.addEdge(idHypernym,Integer.parseInt(readHypernym[i]));
            }
        }
        DirectedCycle cycleChecker = new DirectedCycle(wordNet);
        if (cycleChecker.hasCycle()) {
            throw new IllegalArgumentException(" The given digraph contains one or more cycles ");
        }
        else {
            for (int i = 0; i < wordNet.V(); i++) {
                if (wordNet.outdegree(i) == 0) {
                    count++;
                }
            }

            if (count != 1) {
                throw new IllegalArgumentException("The given DAG is not a rooted DAG ");
            }
        }
        shortest = new SAP(wordNet);
        synset.close();
        hypernym.close();
    }

    //returns all the Wordnet nouns
    public Iterable<String> nouns() {
        return NounToId.keySet();
    }

    //is the word a WordNet noun
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("invalid argument");
        }
        return NounToId.containsKey(word);
    }

    //distance between noun A and noun B
    public int distance(String nounA, String nounB) {
        return shortest.length(NounToId.get(nounA), NounToId.get(nounB));
    }

    // a synset(second field of synsets.txt) that is common ancestor of both the given arguments
    // in a shortest ancestoral path
    public String sap(String nounA, String nounB) {
        if (isNoun(nounA) && isNoun(nounB)) {
            int ancestor = this.shortest.ancestor(NounToId.get(nounA), NounToId.get(nounB));
            String s = Arrays.toString(IdToNoun.get(ancestor));
            return s.substring(1, s.length()-1).replaceAll(",", " "); // to remove the [ and ] placed at the start and end of the string by Arrays.tostring
        } else {
            throw new IllegalArgumentException(" The given nouns are not in the wordnet ");
        }
    }

    //for unit testing of this class
    public static void main(String[] args) {
        if (args.length > 1) {
            WordNet wordNet = new WordNet(args[0], args[1]);
            wordNet.isNoun("a");
        }
    }
}
