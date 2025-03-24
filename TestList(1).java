import ds.ListImpl;
import exceptions.WrongIndexException;

public class TestList {
    public TestList() {
    }

    public static void main(String[] args) {
        try {
            ListImpl<Integer> lista = new ListImpl<>();
            lista.insert(0, 1);
            lista.insert(1, 2);
            lista.insert(2, 3);

            if (lista.size() != 3) {
                System.out.println("Error: El tamaño de la lista debería ser 3.");
            }

            if (lista.get(0) != 1 || lista.get(1) != 2 || lista.get(2) != 3) {
                System.out.println("Error: Los elementos insertados no son correctos.");
            }

            lista.delete(1);

            if (lista.size() != 2) {
                System.out.println("Error: El tamaño de la lista debería ser 2 después de eliminar un elemento.");
            }

            if (lista.get(0) != 1 || lista.get(1) != 3) {
                System.out.println("Error: Los elementos después de eliminar no son correctos.");
            }

            if (lista.search(1) != 0 || lista.search(3) != 1 || lista.search(2) != -1) {
                System.out.println("Error: La búsqueda de elementos no es correcta.");
            }

            System.out.println("Todas las pruebas básicas han pasado correctamente.");
        } catch (WrongIndexException e) {
            System.out.println("Error: Se lanzó una excepción de índice incorrecto.");
        }
    }
}