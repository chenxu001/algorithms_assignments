/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node first, last;
    private int length;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        length = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the randomized queue
    public int size() {
        return length;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            first = last;
        }
        else oldlast.next = last;
        length++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int s = size();
        if (s == 1) {
            Node m = first;
            first = null;
            last = null;
            length--;
            return m.item;
        }
        Node m, nextnode;
        Node previous = first;
        int j = StdRandom.uniform(s) + 1;
        if (j == 1) {
            m = first;
            first = first.next;
            m.next = null;
            length--;
            return m.item;
        }
        else {
            for (int i = 1; i < j - 1; i++) {
                previous = previous.next;
            }
            m = previous.next;
            nextnode = m.next;
            previous.next = nextnode;
            m.next = null;
            if (j == s) {
                last = previous;
            }
            length--;
            return m.item;
        }
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node m = first;
        int s = size();
        int j = StdRandom.uniform(s) + 1;
        for (int i = 1; i < j; i++) {
            m = m.next;
        }
        return m.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        public boolean hasNext() {
            return size() != 0;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (size() == 0) {
                throw new java.util.NoSuchElementException();
            }
            return dequeue();
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

        RandomizedQueue<Integer> test = new RandomizedQueue<Integer>();

        for (int i = 1; i < 20; i++) {
            if (i % 2 == 0) {
                test.enqueue(i);
            }
        }

        while (!test.isEmpty()) {
            System.out.println(test.sample());
            System.out.println(test.size());
            System.out.println(test.dequeue());
        }
    }

}
