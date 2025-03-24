import ds.BST;

public class TestABB {
    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();
        
        System.out.println("----- Prueba de add -----");
        bst.add(50);
        bst.add(30);
        bst.add(70);
        bst.add(20);
        bst.add(40);
        bst.add(60);
        bst.add(80);
        System.out.println("Árbol (inorden): " + bst);
        
        System.out.println("\n----- Prueba de search -----");
        BST<Integer>.Node found = bst.search(40);
        System.out.println("Buscar 40: " + (found != null ? found.value : "No encontrado"));
        found = bst.search(100);
        System.out.println("Buscar 100: " + (found != null ? found.value : "No encontrado"));
        
        System.out.println("\n----- Prueba de min y max -----");
        BST<Integer>.Node minNode = bst.min();
        BST<Integer>.Node maxNode = bst.max();
        System.out.println("Mínimo: " + (minNode != null ? minNode.value : "Árbol vacío"));
        System.out.println("Máximo: " + (maxNode != null ? maxNode.value : "Árbol vacío"));
        
        System.out.println("\n----- Prueba de delete -----");
        System.out.println("Eliminar 20 (nodo hoja)");
        bst.delete(20);
        System.out.println("Árbol (inorden): " + bst);
        
        System.out.println("Eliminar 30 (nodo con un hijo)");
        bst.delete(30);
        System.out.println("Árbol (inorden): " + bst);
        
        System.out.println("Eliminar 50 (nodo con dos hijos)");
        bst.delete(50);
        System.out.println("Árbol (inorden): " + bst);
        
        System.out.println("\n----- Prueba del iterador -----");
        System.out.print("Recorrido inorden: ");
        for (Integer value : bst) {
            System.out.print(value + " ");
        }
        System.out.println();
    }
}
