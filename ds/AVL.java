package ds;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

public class AVL<T extends Comparable<? super T>> implements Iterable<T> {

    /**
     * Clase interna que representa cada nodo del árbol AVL.
     */
    public class Node {
        public T value;
        public Node left, right;
        public int height;

        public Node(T value) {
            this.value = value;
            left = right = null;
            height = 1; // altura inicial
        }
    }

    private Node root;

    public AVL() {
        root = null;
    }

    // Devuelve la altura de un nodo (0 si es nulo)
    private int height(Node n) {
        return n == null ? 0 : n.height;
    }

    // Calcula el factor de equilibrio (balance factor) de un nodo
    private int getBalance(Node n) {
        return n == null ? 0 : height(n.left) - height(n.right);
    }

    // Realiza una rotación a la derecha y actualiza las alturas
    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;

        // Realizar rotación
        x.right = y;
        y.left = T2;

        // Actualizar alturas
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Realiza una rotación a la izquierda y actualiza las alturas
    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;

        // Realizar rotación
        y.left = x;
        x.right = T2;

        // Actualizar alturas
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Busca de forma iterativa el nodo que contiene el valor dado.
     * @param value valor a buscar.
     * @return el nodo que contiene el valor o null si no se encuentra.
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
     * Inserta un nuevo nodo con el valor recibido de forma iterativa.
     * Si el valor ya existe, no se realiza la inserción.
     * Se reequilibra el árbol AVL actualizando alturas y aplicando las rotaciones
     * necesarias, recorriendo el camino de inserción de abajo hacia arriba.
     * @param value valor a insertar.
     */
    public void add(T value) {
        if (root == null) {
            root = new Node(value);
            return;
        }
        // Almacenar el camino desde la raíz hasta el lugar de inserción
        List<Node> path = new ArrayList<>();
        Node current = root;
        Node parent = null;
        while (current != null) {
            path.add(current);
            int cmp = value.compareTo(current.value);
            if (cmp == 0) {
                return; // valor duplicado, no se inserta
            }
            parent = current;
            if (cmp < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        // Insertar el nuevo nodo
        Node newNode = new Node(value);
        int cmp = value.compareTo(parent.value);
        if (cmp < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        path.add(newNode);

        // Reequilibrar el árbol: recorrer el camino de abajo hacia arriba
        for (int i = path.size() - 1; i >= 0; i--) {
            Node n = path.get(i);
            n.height = Math.max(height(n.left), height(n.right)) + 1;
            int balance = getBalance(n);
            if (balance > 1 || balance < -1) {
                // Caso desequilibrado: aplicar la rotación adecuada
                if (balance > 1) { // subárbol izquierdo pesado
                    if (value.compareTo(n.left.value) < 0) {
                        // Caso Left-Left
                        n = rightRotate(n);
                    } else {
                        // Caso Left-Right
                        n.left = leftRotate(n.left);
                        n = rightRotate(n);
                    }
                } else { // balance < -1: subárbol derecho pesado
                    if (value.compareTo(n.right.value) > 0) {
                        // Caso Right-Right
                        n = leftRotate(n);
                    } else {
                        // Caso Right-Left
                        n.right = rightRotate(n.right);
                        n = leftRotate(n);
                    }
                }
                // Actualizar el vínculo del nodo padre (o la raíz)
                if (i == 0) {
                    root = n;
                } else {
                    Node parentNode = path.get(i - 1);
                    if (parentNode.left == path.get(i)) {
                        parentNode.left = n;
                    } else {
                        parentNode.right = n;
                    }
                }
                path.set(i, n);
            }
        }
    }

    /**
     * Elimina iterativamente el nodo que contiene el valor recibido.
     * Se implementa la eliminación similar a la de un BST y luego se reequilibra el árbol,
     * recorriendo el camino de la eliminación de abajo hacia arriba.
     * @param value valor a eliminar.
     */
    public void delete(T value) {
        if (root == null) return;
        // Almacenar el camino desde la raíz hasta el nodo a eliminar
        List<Node> path = new ArrayList<>();
        Node current = root;
        Node parent = null;
        while (current != null && !current.value.equals(value)) {
            path.add(current);
            parent = current;
            if (value.compareTo(current.value) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }
        if (current == null) return; // valor no encontrado
        path.add(current);

        // Eliminación: dos casos principales
        if (current.left != null && current.right != null) {
            // Nodo con dos hijos: buscar el sucesor (mínimo del subárbol derecho)
            List<Node> succPath = new ArrayList<>();
            Node succ = current.right;
            Node succParent = current;
            while (succ.left != null) {
                succPath.add(succ);
                succParent = succ;
                succ = succ.left;
            }
            // Copiar el valor del sucesor en el nodo actual
            current.value = succ.value;
            // Eliminar el sucesor
            if (succParent.left == succ) {
                succParent.left = succ.right;
            } else {
                succParent.right = succ.right;
            }
            // Añadir el camino del sucesor para reequilibrar (si fuera necesario)
            path.addAll(succPath);
        } else {
            // Nodo con 0 o 1 hijo
            Node child = (current.left != null) ? current.left : current.right;
            if (parent == null) {
                // Se elimina la raíz
                root = child;
                return;
            } else {
                if (parent.left == current) {
                    parent.left = child;
                } else {
                    parent.right = child;
                }
            }
        }

        // Reequilibrar el árbol: recorrer el camino de abajo hacia arriba
        for (int i = path.size() - 1; i >= 0; i--) {
            Node n = path.get(i);
            n.height = Math.max(height(n.left), height(n.right)) + 1;
            int balance = getBalance(n);
            if (balance > 1 || balance < -1) {
                if (balance > 1) { // subárbol izquierdo pesado
                    if (height(n.left.left) >= height(n.left.right)) {
                        n = rightRotate(n);
                    } else {
                        n.left = leftRotate(n.left);
                        n = rightRotate(n);
                    }
                } else { // subárbol derecho pesado
                    if (height(n.right.right) >= height(n.right.left)) {
                        n = leftRotate(n);
                    } else {
                        n.right = rightRotate(n.right);
                        n = leftRotate(n);
                    }
                }
                if (i == 0) {
                    root = n;
                } else {
                    Node parentNode = path.get(i - 1);
                    if (parentNode.left == path.get(i)) {
                        parentNode.left = n;
                    } else {
                        parentNode.right = n;
                    }
                }
                path.set(i, n);
            }
        }
    }

    /**
     * Devuelve el nodo con el valor mínimo del árbol de forma iterativa.
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
     * Devuelve el nodo con el valor máximo del árbol de forma iterativa.
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
     * Implementa un iterador que recorre el árbol en postorden.
     * Se utiliza un algoritmo con dos pilas para generar el recorrido.
     */
    @Override
    public Iterator<T> iterator() {
        return new AVLPostorderIterator();
    }

    private class AVLPostorderIterator implements Iterator<T> {
        private Stack<Node> stack;

        public AVLPostorderIterator() {
            stack = new Stack<>();
            if (root != null) {
                Stack<Node> s1 = new Stack<>();
                Stack<Node> s2 = new Stack<>();
                s1.push(root);
                while (!s1.isEmpty()) {
                    Node node = s1.pop();
                    s2.push(node);
                    if (node.left != null) s1.push(node.left);
                    if (node.right != null) s1.push(node.right);
                }
                while (!s2.isEmpty()) {
                    stack.push(s2.pop());
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return stack.pop().value;
        }
    }

    @Override
    public String toString() {
        // Se devuelve una representación en postorden para facilitar la comprobación
        StringBuilder sb = new StringBuilder();
        for (T value : this) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }
}
