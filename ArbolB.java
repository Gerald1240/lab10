package B_Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Gerald Gomez
 */
public class ArbolB {
    NodoArbolB root;
    int t;

    //Constructor
    public ArbolB(int t) {
        this.t = t;
        root = new NodoArbolB(t);
    }

    
    //Busca el valor ingresado y muestra el contenido del nodo que contiene el valor
    public int[] buscarNodoPorClave(int num) {
        NodoArbolB temp = search(root, num);

        if (temp == null) {
            System.out.println("No se ha encontrado un nodo con el valor ingresado");
            return null;
        } else {
            return Arrays.copyOf(temp.key, temp.n);
        }
    }
    //Search
    private NodoArbolB search(NodoArbolB actual, int key) {
        int i = 0;//se empieza a buscar siempre en la primera posicion

        //Incrementa el indice mientras el valor de la clave del nodo sea menor
        while (i < actual.n && key > actual.key[i]) {
            i++;
        }

        //Si la clave es igual, entonces retornamos el nodo
        if (i < actual.n && key == actual.key[i]) {
            return actual;
        }

        //Si llegamos hasta aqui, entonces hay que buscar los hijos
        //Se revisa primero si tiene hijos
        if (actual.leaf) {
            return null;
        } else {
            //Si tiene hijos, hace una llamada recursiva
            return search(actual.child[i], key);
        }
    }

    public void insertar(int key) {
        NodoArbolB r = root;

        //Si el nodo esta lleno lo debe separar antes de insertar
        if (r.n == ((2 * t) - 1)) {
            NodoArbolB s = new NodoArbolB(t);
            root = s;
            s.leaf = false;
            s.n = 0;
            s.child[0] = r;
            split(s, 0, r);
            nonFullInsert(s, key);
        } else {
            nonFullInsert(r, key);
        }
    }
    // Caso cuando la raiz se divide
    // x =          | | | | | |
    //             /
    //      |10|20|30|40|50|
    // i = 0
    // y = |10|20|30|40|50|
    private void split(NodoArbolB x, int i, NodoArbolB y) {
        //Nodo temporal que sera el hijo i + 1 de x
        NodoArbolB z = new NodoArbolB(t);
        z.leaf = y.leaf;
        z.n = (t - 1);

        //Copia las ultimas (t - 1) claves del nodo y al inicio del nodo z      // z = |40|50| | | |
        for (int j = 0; j < (t - 1); j++) {
            z.key[j] = y.key[(j + t)];
        }

        //Si no es hoja hay que reasignar los nodos hijos
        if (!y.leaf) {
            for (int k = 0; k < t; k++) {
                z.child[k] = y.child[(k + t)];
            }
        }

        //nuevo tamanio de y                                                    // x =            | | | | | |
        y.n = (t - 1);                                                          //               /   \
                                                                                //  |10|20| | | |
        //Mueve los hijos de x para darle espacio a z
        for (int j = x.n; j > i; j--) {
            x.child[(j + 1)] = x.child[j];
        }
        //Reasigna el hijo (i+1) de x                                           // x =            | | | | | |
        x.child[(i + 1)] = z;                                                   //               /   \
                                                                                //  |10|20| | | |     |40|50| | | |
        //Mueve las claves de x
        for (int j = x.n; j > i; j--) {
            x.key[(j + 1)] = x.key[j];
        }

        //Agrega la clave situada en la mediana                                 // x =            |30| | | | |
        x.key[i] = y.key[(t - 1)];                                              //               /    \
        x.n++;                                                                  //  |10|20| | | |      |40|50| | | |
    }
    private void nonFullInsert(NodoArbolB x, int key) {
        //Si es una hoja
        if (x.leaf) {
            int i = x.n; //cantidad de valores del nodo
            //busca la posicion i donde asignar el valor
            while (i >= 1 && key < x.key[i - 1]) {
                x.key[i] = x.key[i - 1];//Desplaza los valores mayores a key
                i--;
            }

            x.key[i] = key;//asigna el valor al nodo
            x.n++; //aumenta la cantidad de elementos del nodo
        } else {
            int j = 0;
            //Busca la posicion del hijo
            while (j < x.n && key > x.key[j]) {
                j++;
            }

            //Si el nodo hijo esta lleno lo separa
            if (x.child[j].n == (2 * t - 1)) {
                split(x, j, x.child[j]);

                if (key > x.key[j]) {
                    j++;
                }
            }

            nonFullInsert(x.child[j], key);
        }
    }
    public void eliminar(int key) {
        // Llama al método de eliminación recursiva comenzando desde la raíz
        eliminarRecursivo(root, key);

        // Si la raíz está vacía y tiene hijos, la raíz se convierte en el primer hijo
        if (root.n == 0 && !root.leaf) {
            root = root.child[0];
        }
    }

    private void eliminarRecursivo(NodoArbolB nodo, int key) {
        int i = 0;
        while (i < nodo.n && key > nodo.key[i]) {
            i++;
        }

        if (i < nodo.n && key == nodo.key[i]) {
            // La clave está en este nodo, eliminarla
            if (nodo.leaf) {
                // Desplaza todas las claves y los hijos a la izquierda
                for (int j = i; j < nodo.n - 1; j++) {
                    nodo.key[j] = nodo.key[j + 1];
                }
                nodo.n--;
            } else {
                // Casos complejos de eliminación de nodos internos
                // ... implementación pendiente ...
            }
        } else if (!nodo.leaf) {
            // La clave no está en este nodo, eliminarla de un hijo
            eliminarRecursivo(nodo.child[i], key);
        }
    }
    public List<Integer> rastrearCamino(int key) {
        List<Integer> camino = new ArrayList<>();
        rastrearCaminoRecursivo(root, key, camino);
        return camino;
    }

    private void rastrearCaminoRecursivo(NodoArbolB nodo, int key, List<Integer> camino) {
        int i = 0;
        while (i < nodo.n && key > nodo.key[i]) {
            i++;
        }

        // Agrega la clave del nodo al camino
        if (i < nodo.n) {
            camino.add(nodo.key[i]);
        }

        if (i < nodo.n && key == nodo.key[i]) {
            // La clave está en este nodo
            if (!nodo.leaf) {
                // Si no es una hoja, sigue rastreando el camino en los hijos
                rastrearCaminoRecursivo(nodo.child[i], key, camino);
            }
        } else if (!nodo.leaf) {
            // La clave no está en este nodo, sigue rastreando el camino en los hijos
            rastrearCaminoRecursivo(nodo.child[i], key, camino);
        }
    }
    public int obtenerMaximo() {
        return obtenerMaximoRecursivo(root);
    }

    private int obtenerMaximoRecursivo(NodoArbolB nodo) {
        // Si el nodo es una hoja, el valor máximo es la última clave
        if (nodo.leaf) {
            return nodo.key[nodo.n - 1];
        } else {
            // Si el nodo no es una hoja, el valor máximo está en el subárbol más a la derecha
            return obtenerMaximoRecursivo(nodo.child[nodo.n]);
        }
    }
    
    public NodoArbolB obtenerNodoMinimo() {
        NodoArbolB nodo = root;
        while (!nodo.leaf) {
            nodo = nodo.child[0];
        }
        return nodo;
    }

    public void showBTree() {
        print(root);
    }

    //Print en preorder
    private void print(NodoArbolB n) {
        n.imprimir();

        //Si no es hoja
        if (!n.leaf) {
            //recorre los nodos para saber si tiene hijos
            for (int j = 0; j <= n.n; j++) {
                if (n.child[j] != null) {
                    System.out.println();
                    print(n.child[j]);
                }
            }
        }
    }
}
