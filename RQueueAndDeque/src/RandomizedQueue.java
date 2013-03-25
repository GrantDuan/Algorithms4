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
        return itemList.removeRandomly();
    }

    public Item sample() // return (but do not delete) a random item
    {
        if (isEmpty())
            throw new java.util.NoSuchElementException();
        return itemList.getRandomly();
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
            StdRandom.shuffle(items);

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
        private int currentIndex;

        public ArrayList() {
            items = new Object[8];
        }

        public void add(Item item) {
            if (item == null)
                throw new java.lang.NullPointerException();

            items[currentIndex++] = item;
            size++;
            if (currentIndex == items.length) {
                currentIndex = 0;
                Object[] newArray = new Object[items.length * 2];
                for (int i = 0; i < items.length; i++) {
                    if (items[i] != null)
                        newArray[currentIndex++] = items[i];
                }
                items = newArray;
            }

        }

        public void remove(int index) {
            if (index >= currentIndex)
                throw new java.util.NoSuchElementException();

            items[index] = null;
            size--;

            if (size * 4 < items.length) {

                Object[] newArray = new Object[items.length / 2];
                currentIndex = 0;
                for (int i = 0; i < items.length; i++) {
                    if (items[i] != null)
                        newArray[currentIndex++] = items[i];
                }

                items = newArray;
            }
        }

        public Item removeRandomly() {

            int index = StdRandom.uniform(currentIndex);
            while (items[index] == null) {
                index = StdRandom.uniform(currentIndex);
            }
            Item result = (Item) items[index];
            remove(index);
            return result;
        }

        public Item getRandomly() {
            int index = StdRandom.uniform(currentIndex);
            while (items[index] == null) {
                index = StdRandom.uniform(currentIndex);
            }
            return (Item) items[index];
        }

        public int size() {
            return size;
        }

        public Object[] toArray() {
            Object[] result = new Object[size];
            int id = 0;
            for (int i = 0; i < currentIndex; i++) {
                if (items[i] != null)
                    result[id++] = items[i];
            }            
            return result;
        }

    }
}
