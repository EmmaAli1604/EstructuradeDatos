package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            this.elemento = elemento; 
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            this.start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return this.siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            this.anterior = this.siguiente;
            this.siguiente = this.siguiente.siguiente;
            return this.anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return this.anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.siguiente = this.anterior;
            this.anterior = this.anterior.anterior;
            return this.siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            this.anterior = null;
            this.siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            this.siguiente = null;
            this.anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        return this.longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return this.cabeza == null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        agregaFinal(elemento);
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        longitud++;
        Nodo n = new Nodo(elemento);
        if (this.esVacia() == true) {
            this.cabeza = n;
            this.rabo = n;
        } else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        }
        longitud++;
        Nodo n = new Nodo(elemento);
        if (this.esVacia()) {
            this.cabeza = n;
            this.rabo = n;
        } else {
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if (elemento == null) {
            throw new IllegalArgumentException();
        } else if (i < 1) {
            agregaInicio(elemento);
        } else if (i >= this.longitud) {
            agregaFinal(elemento);
        } else {
            longitud++;
            Nodo n = new Nodo(elemento);
            Nodo s = getNodo(i);
            s.anterior.siguiente = n;
            n.anterior = s.anterior;
            n.siguiente = s;
            s.anterior = n;
        }
    }

    private Nodo getNodo(int i) {
        Nodo r = this.cabeza;
        int index = 0;
        if (i < 1) {
            return cabeza;
        } else if (i >= longitud) {
            return rabo;
        } else {
            while (index != i) {
                r = r.siguiente;
                index++;
            }
        }
        return r;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Nodo eliminado = buscar(elemento);
        if (eliminado == null) {
            return;
        } else if (this.cabeza == this.rabo) {
            this.cabeza = null;
            this.rabo = null;
            longitud--;
        } else if (eliminado.equals(cabeza)) {
            this.eliminaPrimero();
        } else if (eliminado.equals(rabo)) {
            this.eliminaUltimo();
        } else {
            eliminado.anterior.siguiente = eliminado.siguiente;
            eliminado.siguiente.anterior = eliminado.anterior;
            longitud--;
        }
    }

    private Nodo buscar(T elemento) {
        if (elemento == null) {
            return null;
        }
        Nodo n = this.cabeza;
        while (n != null) {
            if (n.elemento.equals(elemento)) {
                break;
            }
            n = n.siguiente;
        }
        return n;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (this.cabeza == null) {
            throw new NoSuchElementException();
        }
        T eliminado = this.cabeza.elemento;
        if (this.longitud == 1) {
            this.cabeza = null;
            this.rabo = null;
            longitud = 0;

        } else {
            this.cabeza = this.cabeza.siguiente;
            this.cabeza.anterior = null;
            longitud--;

        }
        return eliminado;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (this.cabeza == null) {
            throw new NoSuchElementException();
        }
        T eliminado = this.rabo.elemento;
        if (this.longitud == 1) {
            this.rabo = null;
            this.cabeza = null;
            longitud = 0;
        } else {
            this.rabo = this.rabo.anterior;
            this.rabo.siguiente = null;
            longitud--;
        }
        return eliminado;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        Nodo n = buscar(elemento);
        if (n == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> reverse = new Lista<>();
        Nodo r = this.cabeza;
        while (r != null) {
            reverse.agregaInicio(r.elemento);
            r = r.siguiente;
        }
        return reverse;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> copy = new Lista<>();
        if (this.cabeza == null) {
            return copy;
        }
        Nodo n = this.cabeza;
        while (n != null) {
            copy.agregaFinal(n.elemento);
            n = n.siguiente;
        }
        return copy;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        while (this.longitud != 0) {
            eliminaUltimo();
        }
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (cabeza == null) {
            throw new NoSuchElementException();
        } else {
            return cabeza.elemento;
        }
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (rabo == null) {
            throw new NoSuchElementException();
        } else {
            return rabo.elemento;
        }
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= this.longitud) {
            throw new ExcepcionIndiceInvalido();
        }
        if (this.longitud == 1) {
            return this.cabeza.elemento;
        }
        Nodo n = this.cabeza;
        int index = 0;
        while (index != i) {
            n = n.siguiente;
            index++;
        }
        return n.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int index = 0;
        if (contiene(elemento) == false) {
            return -1;
        }
        Nodo n = this.cabeza;
        while (n.elemento != elemento) {
            n = n.siguiente;
            index++;
        }
        return index;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        String c = "[";
        Nodo n = this.cabeza;
        while (n != null) {
            c = c + n.elemento;
            n = n.siguiente;
            if (n != null) {
                c = c + ", ";
            }
        }
        c = c + "]";
        return c;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        boolean data = false;
        if (lista.getElementos() == this.getElementos()) {
            Nodo n1 = this.cabeza;
            Nodo n2 = lista.cabeza;
            while (n1 != null && n2 != null) {
                if (!n1.elemento.equals(n2.elemento)) {
                    return data;
                }
                n1 = n1.siguiente;
                n2 = n2.siguiente;
            }
            return data = true;
        } else {
            return data;
        }
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
