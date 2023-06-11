package B_Tree;

import java.util.List;

public class TestArbolB1 {
    public static void main(String[] args) {
        ArbolB arbol = new ArbolB(3);

        System.out.println("=== Prueba de inserción ===");
        arbol.insertar(10);
        arbol.insertar(20);
        arbol.insertar(30);
        arbol.insertar(40);
        arbol.insertar(50);
        arbol.insertar(60);
        arbol.insertar(70);
        arbol.insertar(80);
        arbol.insertar(90);
        arbol.insertar(100);
        arbol.showBTree();
        System.out.println();

        System.out.println("=== Prueba de búsqueda ===");
        int[] nodoEncontrado = arbol.buscarNodoPorClave(30);
        if (nodoEncontrado != null) {
            System.out.print("Nodo encontrado: ");
            for (int key : nodoEncontrado) {
                System.out.print(key + " ");
            }
            System.out.println();
        }
        System.out.println();

        System.out.println("=== Prueba de eliminación ===");
        arbol.eliminar(30);
        arbol.eliminar(50);
        arbol.showBTree();
        System.out.println();

        System.out.println("=== Prueba de rastreo de camino ===");
        List<Integer> camino = arbol.rastrearCamino(20);
        System.out.print("Camino hasta el nodo 20: ");
        for (int key : camino) {
            System.out.print(key + " ");
        }
        System.out.println();
        System.out.println();

        System.out.println("=== Prueba de obtener máximo ===");
        int maximo = arbol.obtenerMaximo();
        System.out.println("Valor máximo en el árbol: " + maximo);
        System.out.println();

        System.out.println("=== Prueba de obtener nodo mínimo ===");
        NodoArbolB nodoMinimo = arbol.obtenerNodoMinimo();
        System.out.print("Claves del nodo mínimo: ");
        for (int i = 0; i < nodoMinimo.n; i++) {
            System.out.print(nodoMinimo.key[i] + " ");
        }
        System.out.println();
        System.out.println();

        System.out.println("=== Árbol B resultante ===");
        arbol.showBTree();
    }
}
