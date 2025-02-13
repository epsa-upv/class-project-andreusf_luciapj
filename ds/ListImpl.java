package ds;

import exceptions.WrongIndexException;
import java.util.Iterator;

public class ListImpl<E> implements List<E> {
    private Nodo<E> cabeza = null;
    private int tamaño = 0;

    public ListImpl() {
    }

    @Override
    public void insert(int pos, E data) throws WrongIndexException {
        if (pos < 0 || pos > tamaño) {
            throw new WrongIndexException();
        }

        if (pos == 0) {
            cabeza = new Nodo<>(data, cabeza);
        } else {
            Nodo<E> actual = cabeza;
            for (int i = 0; i < pos - 1; i++) {
                actual = actual.siguiente;
            }
            actual.siguiente = new Nodo<>(data, actual.siguiente);
        }
        tamaño++;
    }

    @Override
    public void delete(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= tamaño) {
            throw new WrongIndexException();
        }

        if (pos == 0) {
            cabeza = cabeza.siguiente;
        } else {
            Nodo<E> actual = cabeza;
            for (int i = 0; i < pos - 1; i++) {
                actual = actual.siguiente;
            }
            actual.siguiente = actual.siguiente.siguiente;
        }
        tamaño--;
    }

    @Override
    public E get(int pos) throws WrongIndexException {
        if (pos < 0 || pos >= tamaño) {
            throw new WrongIndexException();
        }

        Nodo<E> actual = cabeza;
        for (int i = 0; i < pos; i++) {
            actual = actual.siguiente;
        }
        return actual.dato;
    }

    @Override
    public int search(E data) {
        Nodo<E> actual = cabeza;
        int pos = 0;
        while (actual != null) {
            if (actual.dato.equals(data)) {
                return pos;
            }
            actual = actual.siguiente;
            pos++;
        }
        return -1;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterador(cabeza);
    }

    @Override
    public int size() {
        return tamaño;
    }

    private static class Nodo<E> {
        E dato;
        Nodo<E> siguiente;

        Nodo(E dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        Nodo(E dato, Nodo<E> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }
    }

    private class Iterador implements Iterator<E> {
        private Nodo<E> actual;

        public Iterador(Nodo<E> cabeza) {
            this.actual = cabeza;
        }

        @Override
        public boolean hasNext() {
            return actual != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            E dato = actual.dato;
            actual = actual.siguiente;
            return dato;
        }
    }
}