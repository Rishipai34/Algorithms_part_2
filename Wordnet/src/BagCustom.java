import java.util.Iterator;
import java.util.NoSuchElementException;

public class BagCustom<Item> implements Iterable<Item> {
    private nodes<Item> first ; // Beginning of the bag
    private int anInt; // no of elements in the bag
    private static class nodes<Item> {
        private Item nodeValue;
        private nodes<Item> next;
    }

    public BagCustom(){
        first = null;
        anInt = 0;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public int size() {
        return anInt;
    }

    public void addElement(Item newel ) {
        nodes<Item> oldFirst = first ;
        first = new nodes<>();
        first.nodeValue = newel;
        first.next = oldFirst;
        anInt++;
    }

    public Iterator<Item> iterator(){
        return new BagIterator(first);
    }

    private class BagIterator implements Iterator<Item> {
        private nodes<Item> current;
        public BagIterator(nodes<Item> first ) {
            current = first;
        }
        public boolean hasNext() {
            return first.next != null;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.nodeValue;
            current= current.next;
            return item;
        }
    }
}
