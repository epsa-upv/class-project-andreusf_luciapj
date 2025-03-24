import ds.HashTable;

public class TestTabla {
    public static void main(String[] args) {
        // Crear instancia del diccionario
        HashTable<String, Integer> diccionario = new HashTable<>();

        System.out.println("----- Test de inserción -----");
        // Inserción de elementos
        diccionario.put("uno", 1);
        diccionario.put("dos", 2);
        diccionario.put("tres", 3);
        diccionario.put("cuatro", 4);
        diccionario.put("cinco", 5);
        // Mostrar contenido de la tabla usando toString
        System.out.println(diccionario);

        System.out.println("----- Test de get -----");
        System.out.println("tres: " + diccionario.get("tres"));
        System.out.println("cuatro: " + diccionario.get("cuatro"));
        System.out.println("diez (no existe): " + diccionario.get("diez"));

        System.out.println("----- Test de contains -----");
        System.out.println("dos? " + diccionario.contains("dos"));
        System.out.println("Contiene?" + diccionario.contains("diez"));

        System.out.println("----- Test de actualización -----");
        // Insertar con clave ya existente actualiza el valor
        System.out.println("Actualizando dos a 22...");
        diccionario.put("dos", 22);
        System.out.println("Nuevo valor de dos: " + diccionario.get("dos"));

        System.out.println("----- Test de size -----");
        System.out.println("Tamaño del diccionario: " + diccionario.size());

        System.out.println("----- Test de remove -----");
        System.out.println("Eliminando 'tres'...");
        Integer valorEliminado = diccionario.remove("tres");
        System.out.println("Valor eliminado: " + valorEliminado);
        System.out.println("¿Contiene 'tres'? " + diccionario.contains("tres"));
        System.out.println("Tamaño después de la eliminación: " + diccionario.size());

        System.out.println("----- Test de iterador -----");
        System.out.println("Iterando sobre las claves del diccionario:");
        for (String clave : diccionario) {
            System.out.println(clave + " -> " + diccionario.get(clave));
        }

        System.out.println("----- Test de clear -----");
        diccionario.clear();
        System.out.println("Diccionario tras clear:");
        System.out.println(diccionario);
        System.out.println("¿Está vacío? " + diccionario.isEmpty());
    }
}
