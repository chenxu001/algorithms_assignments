/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int length;

    private class Node {
        Item item;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        length = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return length;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        if (oldfirst == null) {
            last = first;
        }
        length++;
    }

    // add the item to the end
    public void addLast(Item item) {
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

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        length--;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Item item = last.item;
        if (first.equals(last)) {
            first = null;
            last = first;
        }
        else {
            Node m = first;
            while (!m.next.equals(last)) {
                m = m.next;
            }
            last = m;
        }
        length--;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node currunt = first;

        public boolean hasNext() {
            return currunt != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException();
        }

        public Item next() {
            if (currunt == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = currunt.item;
            currunt = currunt.next;
            return item;
        }
    }

    // unit testing (optional)
    public static void main(String[] args) {

        Deque<Integer> test = new Deque<Integer>();

        for (int i = 1; i < 10; i++) {
            if (i % 2 == 0) {
                test.addFirst(i);
            }
        }

        while (!test.isEmpty()) {
            System.out.println(test.removeLast());
            System.out.println(test.size());
        }
    }
}
