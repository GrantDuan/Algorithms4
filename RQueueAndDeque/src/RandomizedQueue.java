import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private ArrayList itemList;

    public RandomizedQueue() // construct an empty randomized queue
    {
        itemList = new ArrayList();
    }

    public boolean isEmpty() // is the queue empty?
    {
        return itemList.size() == 0;
    }

    public int size() // return the number of items on the queue
    {
        return itemList.size();
    }

    public void enqueue(Item item) // add the item
    {
        if (item == null)
            throw new java.lang.NullPointerException();
        itemList.add(item);
    }

    public Item dequeue() // delete and return a random item
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();

        int index = StdRandom.uniform(size());
        Item item = itemList.get(index);
        itemList.remove(index);
        return item;
    }

    public Item sample() // return (but do not delete) a random item
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        int index = StdRandom.uniform(size());
        Item item = itemList.get(index);
        return item;
    }

    public Iterator<Item> iterator() // return an independent iterator over
                                     // items in random order
    {
        return new RQueueIterator();
    }

    private class RQueueIterator implements Iterator<Item> {

        private Item[] items;
        private int current;

        public RQueueIterator() {
            items = (Item[]) itemList.toArray();
            for (int i = 0; i < items.length; i++) {
                int index = StdRandom.uniform(i + 1);
                Item temp = items[index];
                items[index] = items[i];
                items[i] = temp;
            }

        }

        @Override
        public boolean hasNext() {
            return current < items.length;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item next = items[current];
            current++;
            return next;
        }

        @Override
        public void remove() {
            throw new java.lang.UnsupportedOperationException();

        }

    }

    private class ArrayList {
        private Object[] items;
        private int size;
        private int capacity;

        public ArrayList() {
            capacity = 8;
            items = new Object[capacity];
        }

        public void add(Item item) {
            if (item == null)
                throw new java.lang.NullPointerException();

            items[size] = item;
            size++;
            if (size == capacity) {
                capacity *= 2;
                Object[] newArray = new Object[capacity];
                for (int i = 0; i < size; i++) {
                    newArray[i] = items[i];
                }
                items = newArray;
            }

        }

        public void remove(int index) {
            if (index >= size)
                throw new java.util.NoSuchElementException();
            for (int i = index; i < size - 1; i++) {
                items[i] = items[i + 1];
            }
            size--;

            if (size * 4 < capacity) {
                capacity /= 2;
                Object[] newArray = new Object[capacity];
                for (int i = 0; i < size; i++) {
                    newArray[i] = items[i];
                }
                items = newArray;
            }
        }

        public Item get(int index) {
            if (index >= size)
                throw new java.util.NoSuchElementException();

            return (Item) items[index];
        }

        public int size() {
            return size;
        }

        public Object[] toArray() {
            Object[] result = new Object[size];
            for (int i = 0; i < size; i++) {
                result[i] = items[i];
            }

            return result;
        }

    }
}
