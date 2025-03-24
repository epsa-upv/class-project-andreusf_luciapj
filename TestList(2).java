import ds.ListImpl;
import exceptions.*;

public class TestList {
    public static void main(String[] args) {
        try {
            ListImpl<Integer> list = new ListImpl<>();

            list.insert(0, 1);
            list.insert(1, 2);
            list.insert(2, 3);

            if (list.size() != 3) {
                System.out.println("Error: El tamaño de la lista debería ser 3.");
            }

            if (list.get(0) != 1 || list.get(1) != 2 || list.get(2) != 3) {
                System.out.println("Error: Los elementos insertados no son correctos.");
            }

            list.delete(1);

            if (list.size() != 2) {
                System.out.println("Error: El tamaño de la lista debería ser 2 después de eliminar un elemento.");
            }

            if (list.get(0) != 1 || list.get(1) != 3) {
                System.out.println("Error: Los elementos después de eliminar no son correctos.");
            }

            if (list.search(1) != 0 || list.search(3) != 1 || list.search(2) != -1) {
                System.out.println("Error: La búsqueda de elementos no es correcta.");
            }

            System.out.println("Todas las pruebas básicas han pasado correctamente.");

        } catch (WrongIndexException e) {
            System.out.println("Error: Se lanzó una excepción de índice incorrecto.");
        }
    }
}