import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph digraph;
    private static BreadthFirstDirectedPaths BfsSource = null;
    private static BreadthFirstDirectedPaths BfsDestination = null;

    // takes the input digraph and copies it to a private variable
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("The argument to SAP cannot be null");
        } else {
            digraph = new Digraph(G);
        }
    }

    // checks whether the vertex is valid
    private boolean isNotValid(int v) {
        return ((v <= -1) || (v >= this.digraph.V()));
    }

    // checks whether the vertex is valid for each point ine the given subset
    private boolean isNotValid(Iterable<Integer> intV) {
        if ((intV == null)) {
            throw new IllegalArgumentException("The value passed is null");
        }
        int sizeofiterable = 0;
        for (int i : intV) {
            sizeofiterable++;
        }
        if (sizeofiterable == 0) return true;
        for (int i : intV) {
            if (i<0 || i >= this.digraph.V() || (i != (long)i)) return true;
        }
        return false;
    }
    // returns the length of the shortest ancestral path
    public int length(Iterable<Integer> intA, Iterable<Integer> intB) {
        if (isNotValid(intA) || isNotValid(intB)) {
            throw new IllegalArgumentException("The values passed to length should have valid values");
        }
        BfsSource = new BreadthFirstDirectedPaths(this.digraph, intA);
        BfsDestination = new BreadthFirstDirectedPaths(this.digraph, intB);
        int ancestor = ancestor(intA, intB);
        if (ancestor != -1) {
            return BfsDestination.distTo(ancestor) + BfsSource.distTo(ancestor);
        }
        return -1;
    }

    // for cases in which the arguments are individual elements
    public int length(int v, int w) {
        if (isNotValid(v) || isNotValid(w)) {
            throw new IllegalArgumentException("The values passed are not valid ");
        }
        BfsSource = new BreadthFirstDirectedPaths(this.digraph, v);
        BfsDestination = new BreadthFirstDirectedPaths(this.digraph, w);
        int ancestor = ancestor(v, w);
        if (ancestor != -1) {
            return BfsDestination.distTo(ancestor) + BfsSource.distTo(ancestor);
        }
        return -1;
    }

    //for a pair of source and destination calculates the shortest ancestor
    public int ancestor(int v, int w) {
        if (isNotValid(v) || isNotValid(w)) {
            throw new IllegalArgumentException("The values passed are not valid ");
        }
        BfsSource = new BreadthFirstDirectedPaths(this.digraph, v);
        BfsDestination = new BreadthFirstDirectedPaths(this.digraph, w);

        int ancestor = -1;
        int lengthOfPath = Integer.MAX_VALUE;
        int candidate;

        for (int vertex = 0; vertex < this.digraph.V(); vertex++) {
            if (BfsSource.hasPathTo(vertex) && BfsDestination.hasPathTo(vertex)) {
                candidate = BfsDestination.distTo(vertex) + BfsSource.distTo(vertex);
                // note here that candidate is the node in consideration to be the shortest common ancestor
                if (candidate < lengthOfPath) {
                    lengthOfPath = candidate;
                    ancestor = vertex;
                }
            }
        }
        return ancestor;
    }

    // returns the sequence of integers(Path) which represents the shortest ancestral path between any two numbers of the given subset
    public int ancestor(Iterable<Integer> intA, Iterable<Integer> intB) {
        if (isNotValid(intA) || isNotValid(intB)) {
            throw new IllegalArgumentException("The values passed are not valid ");
        }
        BfsSource = new BreadthFirstDirectedPaths(this.digraph, intA);
        BfsDestination = new BreadthFirstDirectedPaths(this.digraph, intB);
        int ancestor = -1;
        int lengthOfPath = Integer.MAX_VALUE;
        int candidate;

        for (int vertex = 0; vertex < this.digraph.V(); vertex++) {
            if (BfsSource.hasPathTo(vertex) && BfsDestination.hasPathTo(vertex)) {
                candidate = BfsDestination.distTo(vertex) + BfsSource.distTo(vertex);
                // note here that candidate is the node in consideration to be the shortest common ancestor
                if (candidate < lengthOfPath) {
                    lengthOfPath = candidate;
                    ancestor = vertex;
                }
            }
        }
        return ancestor;
    }
}
