package ds;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<T extends Comparable<? super T>> implements Iterable<T> {

    /**
     * Clase interna que representa cada nodo del árbol.
     */
    public class Node {
        public T value;
        public Node left, right;

        public Node(T value) {
            this.value = value;
            this.left = this.right = null;
        }
    }

    private Node root;

    /**
     * Constructor por defecto.
     */
    public BST() {
        root = null;
    }

    /**
     * Busca de forma iterativa el nodo que contiene el valor dado.
     * @param value valor a buscar.
     * @return el nodo con el valor o null si no se encuentra.
     */
    public Node search(T value) {
        Node current = root;
        while (current != null) {
            int cmp = value.compareTo(current.value);
            if (cmp == 0) {
                return current;
            } else if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        return null;
    }

    /**
     * Inserta de forma iterativa un nodo con el valor recibido.
     * Si el valor ya existe, no se realiza la inserción.
     * @param value valor a insertar.
     */
    public void add(T value) {
        Node newNode = new Node(value);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        Node parent = null;
        while (current != null) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp < 0) {
                current = current.left;
            } else if (cmp > 0) {
                current = current.right;
            } else {
                // Valor duplicado: no se inserta.
                return;
            }
        }
        if (value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
    }

    /**
     * Elimina de forma iterativa el nodo que contiene el valor recibido.
     * Si el nodo tiene dos hijos se sustituye por su sucesor (mínimo del subárbol derecho).
     * @param value valor a eliminar.
     */
    public void delete(T value) {
        Node current = root;
        Node parent = null;
        // Buscar el nodo a eliminar
        while (current != null && !current.value.equals(value)) {
            parent = current;
            int cmp = value.compareTo(current.value);
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) return; // Valor no encontrado

        // Caso: nodo con dos hijos
        if (current.left != null && current.right != null) {
            // Buscar el sucesor (mínimo en el subárbol derecho)
            Node successor = current.right;
            Node successorParent = current;
            while (successor.left != null) {
                successorParent = successor;
                successor = successor.left;
            }
            // Copiar el valor del sucesor
            current.value = successor.value;
            // Preparar para eliminar el sucesor
            current = successor;
            parent = successorParent;
        }
        
        // Caso: nodo con 0 o 1 hijo
        Node child = (current.left != null) ? current.left : current.right;
        if (parent == null) {
            // Se elimina la raíz
            root = child;
        } else if (parent.left == current) {
            parent.left = child;
        } else {
            parent.right = child;
        }
    }

    /**
     * Devuelve el nodo que contiene el valor mínimo de forma iterativa.
     * @return nodo con el mínimo valor o null si el árbol está vacío.
     */
    public Node min() {
        if (root == null) return null;
        Node current = root;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Devuelve el nodo que contiene el valor máximo de forma iterativa.
     * @return nodo con el máximo valor o null si el árbol está vacío.
     */
    public Node max() {
        if (root == null) return null;
        Node current = root;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    /**
     * Devuelve un iterador inorden sobre el árbol.
     * Para ello se implementa un iterador que utiliza una pila propia.
     */
    @Override
    public Iterator<T> iterator() {
        return new BSTIterator();
    }

    /**
     * Iterador inorden que utiliza una pila implementada mediante una lista enlazada interna.
     */
    private class BSTIterator implements Iterator<T> {
        private MyStack<Node> stack;

        public BSTIterator() {
            stack = new MyStack<>();
            Node current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node node = stack.pop();
            T result = node.value;
            if (node.right != null) {
                Node current = node.right;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
            return result;
        }
    }

    /**
     * Implementación propia de una pila utilizando una lista enlazada interna.
     */
    private class MyStack<E> {
        /**
         * Nodo interno para la pila.
         */
        private class NodeStack {
            E data;
            NodeStack next;
            NodeStack(E data, NodeStack next) {
                this.data = data;
                this.next = next;
            }
        }

        private NodeStack top;

        public MyStack() {
            top = null;
        }

        /**
         * Inserta un elemento en la pila.
         * @param data elemento a insertar.
         */
        public void push(E data) {
            top = new NodeStack(data, top);
        }

        /**
         * Elimina y devuelve el elemento en la parte superior de la pila.
         * @return el elemento en la parte superior.
         * @throws NoSuchElementException si la pila está vacía.
         */
        public E pop() {
            if (isEmpty()) {
                throw new NoSuchElementException("La pila está vacía");
            }
            E result = top.data;
            top = top.next;
            return result;
        }

        /**
         * Comprueba si la pila está vacía.
         * @return true si está vacía, false en caso contrario.
         */
        public boolean isEmpty() {
            return top == null;
        }
    }

    /**
     * Representa el árbol en forma de recorrido inorden.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T value : this) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }
}
