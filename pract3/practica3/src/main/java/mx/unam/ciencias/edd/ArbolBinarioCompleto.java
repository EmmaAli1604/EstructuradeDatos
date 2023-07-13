package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<Vertice>();

            if (raiz != null)
                cola.mete(raiz);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            Vertice vertice = cola.saca();
            if (vertice.hayIzquierdo())
                cola.mete(vertice.izquierdo);
            if (vertice.hayDerecho())
                cola.mete(vertice.derecho);
            return vertice.elemento;
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice vertice = new Vertice(elemento);
        elementos++;
        if (raiz == null) {
            raiz = vertice;
            return;
        }
        Cola<Vertice> cola = new Cola<>();
        Vertice aux = this.raiz;
        cola.mete(raiz);
        while (!cola.esVacia()) {
            aux = cola.saca();
            if (!aux.hayIzquierdo() || !aux.hayDerecho()) {
                if (!aux.hayIzquierdo()) {
                    vertice.padre = aux;
                    aux.izquierdo = vertice;
                } else {
                    vertice.padre = aux;
                    aux.derecho = vertice;
                }
                break;
            } else {
                cola.mete(aux.izquierdo);
                cola.mete(aux.derecho);
            }
        }
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Cola<Vertice> cola = new Cola<>();
        Vertice eliminar = vertice(busca(elemento));
        if (eliminar == null) {
            return;
        }

        elementos--;

        if (elementos == 0) {
            raiz = null;
            return;
        }

        Vertice v = raiz;
        cola.mete(raiz);
        while (!cola.esVacia()) {
            v = cola.saca();
            if (v.hayIzquierdo()) {
                cola.mete(v.izquierdo);
            }
            if (v.hayDerecho()) {
                cola.mete(v.derecho);
            }
        }

        eliminar.elemento = v.elemento;
        if (v.padre.izquierdo == v) {
            v.padre.izquierdo = null;
        } else {
            v.padre.derecho = null;
        }
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (elementos == 0)
            return -1;
        return (int) Math.floor(Math.log(elementos) / Math.log(2));
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (raiz == null)
            return;
        Cola<Vertice> tail = new Cola<>();
        tail.mete(raiz);
        Vertice v;
        while (!tail.esVacia()) {
            v = tail.saca();
            accion.actua(v);
            if (v.izquierdo != null) {
                tail.mete(v.izquierdo);
            }
            if (v.derecho != null) {
                tail.mete(v.derecho);
            }
        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
