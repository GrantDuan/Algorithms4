import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first, last;
    private int size;

    public Deque() // construct an empty deque
    {

    }

    public boolean isEmpty() // is the deque empty?
    {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    public void addFirst(Item item) // insert the item at the front
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node newNode = new Node(item);
        if (first != null)
            first.pre = newNode;
        newNode.next = first;
        first = newNode;
        if (size == 0)
            last = first;

        size++;
    }

    public void addLast(Item item) // insert the item at the end
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        Node newNode = new Node(item);
        if (size == 0) {
            last = newNode;
            first = last;
        } else {
            last.next = newNode;
            newNode.pre = last;
            last = newNode;
        }

        size++;
    }

    public Item removeFirst() // delete and return the item at the front
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();

        Node temp = first;
        if (size == 1) {
            first = null;
            last = null;
        }

        first = temp.next;
        first.pre = null;
        size--;
        return temp.value;
    }

    public Item removeLast() // delete and return the item at the end
    {
        if (size == 0)
            throw new java.util.NoSuchElementException();

        Node temp = last;
        if (size == 1) {
            first = null;
            last = null;
        }
        last = temp.pre;
        last.next = null;
        size--;
        return temp.value;
    }

    public Iterator<Item> iterator() // return an iterator over items in order
                                     // from front to end
    {
        return new DeueIterator();
    }

    private class DeueIterator implements Iterator<Item> {

        private Node current;

        private DeueIterator() {
            current = first;
        }

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();

            Node temp = current;
            current = current.next;
            return temp.value;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();

        }

    }

    private class Node {
        private Node pre;
        private Node next;
        private Item value;

        private Node(Item value) {
            this.value = value;
        }
    }
}
