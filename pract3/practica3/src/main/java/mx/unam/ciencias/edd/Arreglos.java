package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    private static <T> void intercambia(T[] arreglo, int first, int second) {
        T temp = arreglo[first];
        arreglo[first] = arreglo[second];
        arreglo[second] = temp;
    }

    // Metódo auxilia para implementar el QuickSort
    private static <T> void quickSort(T[] arreglo, Comparator<T> comparador, int inicio, int fin) {
        if (fin <= inicio)
            return;
        int i = inicio + 1;
        int j = fin;
        while (i < j) {
            if (comparador.compare(arreglo[i], arreglo[inicio]) <= 0) {
                i++;
            } else if (comparador.compare(arreglo[j], arreglo[inicio]) > 0) {
                j--;
            } else if (comparador.compare(arreglo[i], arreglo[inicio]) > 0
                    && comparador.compare(arreglo[j], arreglo[inicio]) <= 0) {
                intercambia(arreglo, i++, j--);
            }
        }
        if (comparador.compare(arreglo[i], arreglo[inicio]) > 0) {
            i--;
        }
        intercambia(arreglo, inicio, i);
        quickSort(arreglo, comparador, inicio, i - 1);
        quickSort(arreglo, comparador, i + 1, fin);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        if (arreglo.length > 0) {
            quickSort(arreglo, comparador, 0, arreglo.length - 1);
        }
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for (int i = 0; i < arreglo.length; i++) {
            int m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[m]) <= 0) {
                    intercambia(arreglo, m, j);
                }
            }
        }
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int IndexOfElement = -1;
        for (int i = 0; i < arreglo.length; i++) {
            if (comparador.compare(arreglo[i], elemento) == 0) {
                IndexOfElement = i;
                break;
            }
        }
        return IndexOfElement;
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
