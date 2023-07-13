package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<Vertice>();
            if (raiz == null)
                return;

            pila.mete(raiz);
            Vertice vertice = raiz;

            while ((vertice = vertice.izquierdo) != null)
                pila.mete(vertice);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            Vertice vertice = pila.saca();

            if (vertice.derecho != null) {
                Vertice verticeAux = vertice.derecho;
                pila.mete(verticeAux);

                while ((verticeAux = verticeAux.izquierdo) != null)
                    pila.mete(verticeAux);
            }

            return vertice.elemento;
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    private void agrega(Vertice verticeActual, Vertice verticeAgregar) {
        if (verticeAgregar.elemento.compareTo(verticeActual.elemento) <= 0) {
            if (verticeActual.izquierdo == null) {
                verticeActual.izquierdo = verticeAgregar;
                verticeAgregar.padre = verticeActual;
                return;
            } else {
                agrega(verticeActual.izquierdo, verticeAgregar);
            }
        } else {
            if (verticeActual.derecho == null) {
                verticeActual.derecho = verticeAgregar;
                verticeAgregar.padre = verticeActual;
                return;
            } else {
                agrega(verticeActual.derecho, verticeAgregar);
            }
        }
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice agregar = nuevoVertice(elemento);
        elementos++;
        if (raiz == null) {
            raiz = agregar;
        } else {
            agrega(raiz, agregar);
        }
        ultimoAgregado = agregar;
    }


    private Vertice maximoEnSubarbol(Vertice v) {
        if (v.derecho == null)
            return v;
        return maximoEnSubarbol(v.derecho);
    }
    
    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice vertice = vertice(busca(elemento));

        if (vertice == null)
            return;

        elementos--;

        if (vertice.izquierdo != null && vertice.derecho != null)
            vertice = intercambiaEliminable(vertice);

        eliminaVertice(vertice);
    }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice maxizq = maximoEnSubarbol(vertice.izquierdo);
        T maxelemento = maxizq.elemento;
        maxizq.elemento = vertice.elemento;
        vertice.elemento = maxelemento;

        return maxizq;
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice hijo;
        if (vertice.izquierdo != null)
            hijo = vertice.izquierdo;
        else
            hijo = vertice.derecho;

        if (!vertice.hayPadre())
            raiz = hijo;
        else if (vertice.padre.izquierdo == vertice)
            vertice.padre.izquierdo = hijo;
        else
            vertice.padre.derecho = hijo;

        if (hijo != null)
            hijo.padre = vertice.padre;
    }

    private VerticeArbolBinario<T> auxbusca(Vertice vertice, T elemento) {
        if (vertice == null) {
            return null;
        }
        if (elemento.equals(vertice.elemento))
            return vertice;
        if (elemento.compareTo(vertice.elemento) <= 0)
            return auxbusca(vertice.izquierdo, elemento);
        return auxbusca(vertice.derecho, elemento);
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        return auxbusca(raiz, elemento);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        Vertice q = vertice(vertice);

        if (q.izquierdo == null || q == null)
            return;

        Vertice p = q.izquierdo;
        p.padre = q.padre;

        if (q.padre == null)
            raiz = p;
        else {
            if (q.padre.izquierdo == q)
                q.padre.izquierdo = p;
            else
                q.padre.derecho = p;
        }

        q.izquierdo = p.derecho;

        if (q.izquierdo != null)
            q.izquierdo.padre = q;

        p.derecho = q;
        q.padre = p;
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        Vertice p = vertice(vertice);

        if (p.derecho == null || p == null)
            return;

        Vertice q = p.derecho;
        q.padre = p.padre;

        if (p.padre == null)
            raiz = q;
        else {
            if (p.padre.derecho == p)
                p.padre.derecho = q;
            else
                p.padre.izquierdo = q;
        }

        p.derecho = q.izquierdo;

        if (p.derecho != null)
            p.derecho.padre = p;
        q.izquierdo = p;
        p.padre = q;
    }

    private void dfsPreOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null)
            return;
        accion.actua(v);
        dfsPreOrder(v.izquierdo, accion);
        dfsPreOrder(v.derecho, accion);
    }

    

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPreOrder(raiz, accion);
    }

    private void dfsInOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null)
            return;
        dfsInOrder(v.izquierdo, accion);
        accion.actua(v);
        dfsInOrder(v.derecho, accion);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        dfsInOrder(raiz, accion);
    }


    private void dfsPostOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        if (v == null)
            return;
        dfsPostOrder(v.izquierdo, accion);
        dfsPostOrder(v.derecho, accion);
        accion.actua(v);
    }


    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        dfsPostOrder(raiz, accion);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
