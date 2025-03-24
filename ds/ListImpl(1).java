package ds;

import exceptions.WrongIndexException;
import java.util.Iterator;

/**
 * Implementación de la interfaz List utilizando una lista enlazada.
 *
 * @param <E> el tipo de elementos en esta lista
 */
public class ListImpl<E> implements List<E> {
    private Nodo<E> cabeza = null;
    private int tamaño = 0;

    /**
     * Constructor por defecto.
     */
    public ListImpl() {
    }

    /**
     * Inserta un elemento en la posición especificada.
     *
     * @param pos la posición en la que se debe insertar el elemento
     * @param data el elemento a insertar
     * @throws WrongIndexException si la posición es inválida
     */
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

    /**
     * Elimina el elemento en la posición especificada.
     *
     * @param pos la posición del elemento a eliminar
     * @throws WrongIndexException si la posición es inválida
     */
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

    /**
     * Obtiene el elemento en la posición especificada.
     *
     * @param pos la posición del elemento a obtener
     * @return el elemento en la posición especificada
     * @throws WrongIndexException si la posición es inválida
     */
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

    /**
     * Busca un elemento en la lista.
     *
     * @param data el elemento a buscar
     * @return la posición del elemento en la lista, o -1 si no se encuentra
     */
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

    /**
     * Devuelve un iterador sobre los elementos en esta lista.
     *
     * @return un iterador sobre los elementos en esta lista
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterador(cabeza);
    }

    /**
     * Devuelve el tamaño de la lista.
     *
     * @return el tamaño de la lista
     */
    @Override
    public int size() {
        return tamaño;
    }

    /**
     * Clase interna que representa un nodo en la lista enlazada.
     *
     * @param <E> el tipo de elementos en este nodo
     */
    private static class Nodo<E> {
        E dato;
        Nodo<E> siguiente;

        /**
         * Constructor para crear un nodo con un dato.
         *
         * @param dato el dato del nodo
         */
        Nodo(E dato) {
            this.dato = dato;
            this.siguiente = null;
        }

        /**
         * Constructor para crear un nodo con un dato y el siguiente nodo.
         *
         * @param dato el dato del nodo
         * @param siguiente el siguiente nodo en la lista
         */
        Nodo(E dato, Nodo<E> siguiente) {
            this.dato = dato;
            this.siguiente = siguiente;
        }
    }

    /**
     * Clase interna que implementa un iterador para la lista enlazada.
     */
    private class Iterador implements Iterator<E> {
        private Nodo<E> actual;

        /**
         * Constructor para crear un iterador.
         *
         * @param cabeza el nodo inicial de la lista
         */
        public Iterador(Nodo<E> cabeza) {
            this.actual = cabeza;
        }

        /**
         * Verifica si hay más elementos en la lista.
         *
         * @return true si hay más elementos, false en caso contrario
         */
        @Override
        public boolean hasNext() {
            return actual != null;
        }

        /**
         * Devuelve el siguiente elemento en la lista.
         *
         * @return el siguiente elemento en la lista
         */
        @Override
        public E next() {
            E dato = actual.dato;
            actual = actual.siguiente;
            return dato;
        }
    }
}
