package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Dictionary<K, V> {

    /**
     * Clase interna que representa cada entrada en la tabla hash.
     */
    private static class TableEntry<K, V> {
        K key;
        V value;
        TableEntry<K, V> next; // para el encadenamiento

        public TableEntry(K key, V value, TableEntry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public String toString() {
            return key + " -> " + value;
        }
    }

    // Arreglo de cubetas. Cada posición del arreglo apunta a la cabeza de una lista enlazada.
    private TableEntry<K, V>[] table;
    private int size;
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Constructor por defecto: crea la tabla con 16 cubetas.
     */
    public HashTable() {
        this(16);
    }

    /**
     * Constructor que permite especificar el número inicial de cubetas.
     *
     * @param initialCapacity número inicial de cubetas
     */
    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity) {
        table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
        size = 0;
    }

    /**
     * Calcula el índice de la cubeta a partir de la clave.
     */
    private int hash(K key) {
        return (key == null ? 0 : Math.abs(key.hashCode())) % table.length;
    }

    /**
     * Inserta un par clave-valor en la tabla. Si la clave ya existe, se sustituye el valor.
     * Se comprueba el factor de carga y se realiza rehashing si es necesario.
     */
    @Override
    public V put(K key, V value) {
        // Verificar si es necesario rehash: factor de carga <= 0.75
        if ((double)(size + 1) / table.length > LOAD_FACTOR) {
            rehash();
        }

        int index = hash(key);
        TableEntry<K, V> current = table[index];
        // Buscar si la clave ya existe
        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                V oldValue = current.value;
                current.value = value;
                return oldValue;
            }
            current = current.next;
        }
        // Si la clave no existe, se inserta al inicio de la lista de la cubeta
        table[index] = new TableEntry<>(key, value, table[index]);
        size++;
        return null;
    }

    /**
     * Recupera el valor asociado a la clave dada.
     */
    @Override
    public V get(K key) {
        int index = hash(key);
        TableEntry<K, V> current = table[index];
        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    /**
     * Elimina la entrada con la clave dada y devuelve su valor.
     */
    @Override
    public V remove(K key) {
        int index = hash(key);
        TableEntry<K, V> current = table[index];
        TableEntry<K, V> prev = null;
        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.value;
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    /**
     * Comprueba si existe una entrada con la clave dada.
     */
    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    /**
     * Devuelve el número de entradas en la tabla.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Comprueba si el diccionario está vacío.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Elimina todas las entradas de la tabla.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        table = (TableEntry<K, V>[]) new TableEntry[table.length];
        size = 0;
    }

    /**
     * Realiza el rehashing de la tabla, duplicando el número de cubetas
     * y reinserta todas las entradas.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        TableEntry<K, V>[] oldTable = table;
        table = (TableEntry<K, V>[]) new TableEntry[oldTable.length * 2];
        size = 0;
        // Reinserta cada entrada en la nueva tabla
        for (int i = 0; i < oldTable.length; i++) {
            TableEntry<K, V> current = oldTable[i];
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    /**
     * Devuelve un iterador sobre las claves almacenadas en la tabla.
     */
    @Override
    public Iterator<K> iterator() {
        return new Citerator();
    }

    /**
     * Clase interna que implementa un iterador sobre las claves del diccionario.
     */
    private class Citerator implements Iterator<K> {
        int bucketIndex = 0;
        TableEntry<K, V> current = null;

        public Citerator() {
            advanceToNext();
        }

        /**
         * Avanza a la siguiente cubeta no vacía.
         */
        private void advanceToNext() {
            while (bucketIndex < table.length && current == null) {
                current = table[bucketIndex++];
            }
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            K key = current.key;
            current = current.next;
            if (current == null) {
                advanceToNext();
            }
            return key;
        }
    }

    /**
     * Representa la tabla y su contenido en formato texto.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            sb.append("Cubeta ").append(i).append(": ");
            TableEntry<K, V> current = table[i];
            while (current != null) {
                sb.append(current.toString());
                if (current.next != null) {
                    sb.append(" -> ");
                }
                current = current.next;
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
