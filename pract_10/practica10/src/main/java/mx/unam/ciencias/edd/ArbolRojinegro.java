package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            super(elemento);
            if (elemento == null)
                color = Color.NEGRO;
            else
                color = Color.ROJO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            return String.format("%s{%s}", color == Color.NEGRO ? "N" : "R", elemento.toString());
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            return (color == vertice.color) && super.equals(objeto);
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        return new VerticeRojinegro(elemento);
    }

    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice){
        VerticeRojinegro v = (VerticeRojinegro) vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        return verticeRojinegro(vertice).color;
    }

    private Color color(VerticeRojinegro vertice){
        return vertice != null ? vertice.color : Color.NEGRO;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
        VerticeRojinegro last = verticeRojinegro(ultimoAgregado);
        last.color = Color.ROJO;
        rebalancearAdd(last);
    }

    private boolean verticesCruzadosIzq(VerticeRojinegro v) {
        return v.padre.izquierdo == v;
    }

    private void rebalancearAdd(VerticeRojinegro vertice) {

        if (!vertice.hayPadre()) {
            vertice.color = Color.NEGRO;
            return;
        }

        VerticeRojinegro p = verticeRojinegro(vertice.padre);

        if (color(p) == Color.NEGRO)
            return;

        VerticeRojinegro a = verticeRojinegro(p.padre);
        VerticeRojinegro t = verticeRojinegro((verticesCruzadosIzq(p) ? p.padre.derecho : p.padre.izquierdo));

        if (color(t) == Color.ROJO) {
            p.color = t.color = Color.NEGRO;
            a.color = Color.ROJO;
            rebalancearAdd(a);
            return;
        }

        if (verticesCruzadosIzq(vertice) && !verticesCruzadosIzq(p)) {
            super.giraDerecha(p);
            VerticeRojinegro aux = vertice;
            vertice = p;
            p = aux;
        } else if (verticesCruzadosIzq(p) && !verticesCruzadosIzq(vertice)) {
            super.giraIzquierda(p);
            VerticeRojinegro aux = vertice;
            vertice = p;
            p = aux;
        }

        p.color = Color.NEGRO;
        a.color = Color.ROJO;

        if (verticesCruzadosIzq(vertice)) {
            super.giraDerecha(a);
        } else {
            super.giraIzquierda(a);
        }
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        VerticeRojinegro vertice = verticeRojinegro(busca(elemento));

        if(vertice == null)
            return;
        
        elementos--;

        if(vertice.hayDerecho() && vertice.hayIzquierdo()){
            vertice = verticeRojinegro(intercambiaEliminable(vertice));
        }

        VerticeRojinegro hijo;
        VerticeRojinegro fantasma=null;

        if(!vertice.hayDerecho() && !vertice.hayIzquierdo()){
            fantasma=verticeRojinegro(nuevoVertice(null));
            fantasma.color=Color.NEGRO;
            fantasma.padre = vertice;
            vertice.izquierdo = fantasma;
            hijo = fantasma;
        }else{
            hijo = verticeRojinegro(vertice.hayIzquierdo()? vertice.izquierdo : vertice.derecho); 
        }

        eliminaVertice(vertice);

        if(color(hijo)==Color.ROJO || color(vertice)==Color.ROJO){
            hijo.color=Color.NEGRO;
        }else{
            rebalancearDelete(hijo);
        }
        if(fantasma != null){
            eliminaVertice(fantasma);
        }
    }

    private void rebalancearDelete(VerticeRojinegro vertice){
        if(!vertice.hayPadre()){
            return;
        }
        VerticeRojinegro p = verticeRojinegro(vertice.padre);
        VerticeRojinegro h = verticeRojinegro(verticesCruzadosIzq(vertice)? vertice.padre.derecho:vertice.padre.izquierdo);
        if(color(h) == Color.ROJO){
            p.color=Color.ROJO;
            h.color= Color.NEGRO;
            if(verticesCruzadosIzq(vertice)){
                super.giraIzquierda(p);
            }
            else {
                super.giraDerecha(p);
            }
            p=verticeRojinegro(vertice.padre);
            h = verticeRojinegro(verticesCruzadosIzq(vertice)? vertice.padre.derecho:vertice.padre.izquierdo);
            
        }
        VerticeRojinegro hder=verticeRojinegro(h.derecho);
        VerticeRojinegro hizq=verticeRojinegro(h.izquierdo);
        if(color(p)==Color.NEGRO && color(h)==Color.NEGRO && color(hder)==Color.NEGRO && color(hizq)==Color.NEGRO){
            h.color=Color.ROJO;
            rebalancearDelete(p);
            return;
        }
        if(color(p)==Color.ROJO && color(h)==Color.NEGRO && color(hder)==Color.NEGRO && color(hizq)==Color.NEGRO){
            h.color=Color.ROJO;
            p.color=Color.NEGRO;
            return;
        } 
        if((verticesCruzadosIzq(vertice) && color(hizq) == Color.ROJO && color(hder)==Color.NEGRO)||(!verticesCruzadosIzq(vertice) && color(hizq) == Color.NEGRO && color(hder)==Color.ROJO)){
            h.color=Color.ROJO;
            if(color(hizq)==Color.ROJO)
                hizq.color=Color.NEGRO;
            if(color(hder)==Color.ROJO)
                hder.color=Color.NEGRO;
            
            if(verticesCruzadosIzq(vertice))
                super.giraDerecha(h);
            else
                super.giraIzquierda(h);
            h = verticeRojinegro(verticesCruzadosIzq(vertice)? vertice.padre.derecho:vertice.padre.izquierdo);
            hder=verticeRojinegro(h.derecho);
            hizq=verticeRojinegro(h.izquierdo);
        }
        
        h.color = p.color;
        p.color=Color.NEGRO;
        if(verticesCruzadosIzq(vertice)){
            hder.color=Color.NEGRO;
            super.giraIzquierda(p);
            return;
        }else{
            hizq.color=Color.NEGRO;
            super.giraDerecha(p);
            return;
        }
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
