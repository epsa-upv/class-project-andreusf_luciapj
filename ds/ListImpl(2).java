package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;
import exceptions.*;

public class ListImpl<E> implements List<E> {
    private Node<E> head;
    private int size;

    public ListImpl() {
        head = null;
        size = 0;
    }

    public void insert(int pos, E data) throws WrongIndexException {
        if (pos < 0 || pos > size) {
            throw new WrongIndexException();
        }

        if (pos == 0) {
            head = new Node<>(data, head);
        } else {
            Node<E> current = head;
            for (int i = 0; i < pos - 1; i++) {
                current = current.next;
            }
            current.next = new Node<>(data, current.next);
        }
        size++;
    }

    public void delete(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= size) {
            throw new WrongIndexException();
        }

        if (pos == 0) {
            head = head.next;
        } else {
            Node<E> current = head;
            for (int i = 0; i < pos - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    public E get(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= size) {
            throw new WrongIndexException();
        }

        Iterator<E> current = iterator();
        for (int i = 0; i < pos; i++) {
            current.next();
        }
        return current.next();
    }

    public int search(E data) {
        Iterator<E> it = iterator();
        int pos = 0;
        while (it.hasNext()) {
            if (it.next().equals(data)) {
                return pos;
            }
            pos++;
        }
        return -1;
    }

    public Iterator<E> iterator() {
        return new CIterator(head);
    }

    public int size() {
        return size;
    }

    private static class Node<E> {
        E data;
        Node<E> next;

        Node(E data) {
            this.data = data;
            this.next = null;
        }

        Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }

    private class CIterator implements Iterator<E> {
        private Node<E> current;

        public CIterator(Node<E> head) {
            this.current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            E data = current.data;
            current = current.next;
            return data;
        }
    }
}
