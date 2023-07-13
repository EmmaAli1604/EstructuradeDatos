package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador=vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            return iterador.next().elemento;
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La distancia del vértice. */
        private double distancia;
        /* El índice del vértice. */
        private int indice;
        /* La lista de vecinos del vértice. */
        private Lista<Vecino> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            this.elemento=elemento;
            this.color = Color.NINGUNO;
            this.vecinos = new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            return this.elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            return vecinos.getLongitud();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecinos;
        }

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            this.indice=indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            if(this.distancia<vertice.distancia)
                return -1;
            else if(this.distancia>vertice.distancia)
                return 1;
            else 
                return 0;
        }
    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            this.vecino=vecino;
            this.peso=peso;
        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            return vecino.getGrado();
        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino<T> {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica<T>.Vertice v, Grafica<T>.Vecino a);
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        vertices = new Lista<>();
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        return this.aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        if(elemento== null)
            throw new IllegalArgumentException("El elemento es nulo");
        
        if(contiene(elemento))
            throw new IllegalArgumentException("El elemento ya ha sido agregado");
        
        Vertice point = new Vertice(elemento);
        vertices.agrega(point);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        conecta(a, b , 1);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        if (a.equals(b))
            throw new IllegalArgumentException("Los elementos son iguales.");
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El elemento no se encuentra en la gráfica");
        if(sonVecinos(a, b) || peso<=0)
            throw new IllegalArgumentException("Los elementos ya estan conectados o el peso no es positivo"); 

        
        Vertice pointA = (Vertice) vertice(a);
        Vertice pointB = (Vertice) vertice(b);

        pointB.vecinos.agrega(new Vecino(pointA, peso));
        pointA.vecinos.agrega(new Vecino(pointB, peso));

        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        
        if(!sonVecinos(a, b))
        throw new IllegalArgumentException("No estan conectados los vértices");
        
        Vertice pointA = (Grafica<T>.Vertice) vertice(a);
        Vertice pointB = (Grafica<T>.Vertice) vertice(b);

        for (Vecino n : pointA.vecinos) {
            if(n.vecino.elemento.equals(pointB.elemento)){
                pointA.vecinos.elimina(n);
            }
        }

        for (Vecino n : pointB.vecinos) {
            if(n.vecino.elemento.equals(pointA.elemento)){
                pointB.vecinos.elimina(n);
            }
        }
        

        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        for(Vertice p: vertices){
            if(p.get().equals(elemento)){
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        Vertice point = (Vertice) vertice(elemento);

        for(Vecino p: point.vecinos){
            desconecta(point.elemento, p.vecino.elemento);
        }
        
        vertices.elimina(point);
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        Vertice pointA = (Vertice) vertice(a);
        Vertice pointB = (Vertice) vertice(b);

        for(Vecino n : pointA.vecinos){
            if(n.vecino.elemento.equals(pointB.get())){
                return true;
            }
        }
        return false;

    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El vértice no es elemento de la gráfica.");

        Vertice v1 = (Vertice) vertice(a);
        
        for (Vecino vecinos: v1.vecinos) {
            if(vecinos.vecino.elemento.equals(b))
                return vecinos.peso;
        }

        throw new IllegalArgumentException("No estan conectados los elementos");
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {

        if (!contiene(a) || !contiene(b))
            throw new NoSuchElementException("El vértice no es elemento de la gráfica.");
        if(!sonVecinos(a, b) || peso<=0)
        throw new IllegalArgumentException("Los elementos ya estan conectados o el peso no es positivo"); 
        Vertice pointA = (Vertice) vertice(a);
        Vertice pointB = (Vertice) vertice(b);
        for(Vecino n : pointA.vecinos){
            if(n.vecino.elemento.equals(pointB.elemento))
                n.peso=peso;
        }
        
        for(Vecino n : pointB.vecinos){
            if(n.vecino.elemento.equals(pointA.elemento))
                n.peso=peso;
        }

    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        for(Vertice p : vertices){
            if(p.elemento.equals(elemento)){
                return p;
            }
        }
        throw new NoSuchElementException("El elemento no se encontró");
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        if(vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)
            throw new IllegalArgumentException("El véritce no es válido");
        
        if(vertice.getClass() == Vertice.class){
            Vertice vertice2 = (Vertice) vertice;
            vertice2.color = color;
        }
        if(vertice.getClass() == Vecino.class){
            Vecino neighbour = (Vecino) vertice;
            neighbour.vecino.color = color;
        }
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        aplicaAccion(vertices.getPrimero().elemento, e -> {}, new Cola<Vertice>());

        for(Vertice p: vertices){
            if(p.getColor() == Color.ROJO){
                return false;
            }
        }

        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        for(Vertice p : vertices){
            accion.actua(p);
        }
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        aplicaAccion(elemento, accion, new Cola<Vertice>());
        paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        aplicaAccion(elemento, accion, new Pila<Vertice>());
        paraCadaVertice((v) -> setColor(v, Color.NINGUNO));
    }

    private void aplicaAccion(T elemento, AccionVerticeGrafica<T> accion,MeteSaca<Vertice> estructura){
        Vertice point = (Vertice) vertice(elemento);
        paraCadaVertice((v) -> setColor(v, Color.ROJO));
        point.color=Color.NEGRO;

        estructura.mete(point);

        while(!estructura.esVacia()){
            point=estructura.saca();
            accion.actua(point);
            for(Vecino v: point.vecinos){
                if(v.getColor() == Color.ROJO){
                    v.vecino.color = Color.NEGRO;
                    estructura.mete(v.vecino);
                }
            }
        }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        aristas=0;
        vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        String c = "{";
        for(Vertice p : vertices){
            c += String.format("%s, ", p.get().toString());
        } 
        Lista<T> pasados = new Lista<>();
        
        c+="}, {";
        
        for (Vertice vertice : vertices) {
            for (Vecino vecino : vertice.vecinos){
                if (!pasados.contiene(vecino.vecino.elemento))
                    c += String.format("(%s, %s), ", vertice.elemento.toString(), vecino.vecino.elemento.toString());
            }

            pasados.agrega(vertice.elemento);
            
        }
        
        c += "}";
        return c;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;

        if(this.vertices.getLongitud() == grafica.vertices.getLongitud() || this.aristas == grafica.getAristas()){
            boolean data=false;
            
            for(Vertice p : vertices){
                if(!grafica.contiene(p.elemento)){
                    return false;
                }
                
                Vertice pointG = (Vertice) grafica.vertice(p.elemento);
                
                if(p.vecinos.getLongitud() != pointG.vecinos.getLongitud()){
                    return false;
                }
                
                for(Vecino n : p.vecinos){
                    for(Vecino b : pointG.vecinos){
                        if(n.vecino.elemento.equals(b.vecino.elemento)){
                            data = true;
                            break;
                        }
                    }
                }

            }
            
            return data;
        }
        return false;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        Vertice vertice = (Vertice) vertice(origen);
        Lista<VerticeGrafica<T>> lista = new Lista<>();

        if(!origen.equals(destino)){
            for (Vertice s : vertices) {
                s.distancia = Double.MAX_VALUE;
            }

            vertice.distancia=0;

            Cola<Vertice> cola = new Cola<>();
            cola.mete(vertice);

            while(!cola.esVacia()){
                vertice = cola.saca();
                for (Vecino v : vertice.vecinos) {
                    if(v.vecino.distancia == Double.MAX_VALUE){
                        v.vecino.distancia=vertice.distancia+1;
                        cola.mete(v.vecino);
                    }
                }
            }
            return reconstruirTray((start, v) -> v.vecino.distancia == start.distancia - 1, (Vertice) vertice(destino));
        }
        
        lista.agrega(vertice);
        return lista;

    }

    private Lista<VerticeGrafica<T>> reconstruirTray(BuscadorCamino<T> buscador, Vertice destino) {
        Vertice destiny = destino;
        Lista<VerticeGrafica<T>> lista = new Lista<>();

        if (destiny.distancia == Double.MAX_VALUE)
            return lista;

        lista.agrega(destiny);

        while (destiny.distancia != 0) {
            for (Vecino v : destiny.vecinos) {
                if (buscador.seSiguen(destiny, v)) {
                    lista.agrega(v.vecino);
                    destiny = v.vecino;
                    break;
                }

            }
        }

        return lista.reversa();
    }

    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        Vertice verticeOrig = (Vertice) vertice(origen);
        for (Vertice v : vertices)
            v.distancia = Double.MAX_VALUE;

        verticeOrig.distancia = 0;
        int elementos = vertices.getElementos();

        MonticuloDijkstra<Vertice> monticuloDijkstra;

        if (aristas > ((elementos*(elementos - 1))/2)-elementos)
            monticuloDijkstra = new MonticuloArreglo<>(vertices);
        else
            monticuloDijkstra = new MonticuloMinimo<>(vertices);

        while(!monticuloDijkstra.esVacia()){
            Vertice raiz = monticuloDijkstra.elimina();
            for (Vecino v : raiz.vecinos) {
                if(v.vecino.distancia>raiz.distancia+v.peso){
                    v.vecino.distancia=raiz.distancia+v.peso;
                    monticuloDijkstra.reordena(v.vecino);
                }
            }
        }

        return reconstruirTray(
                (vertex, v) -> v.vecino.distancia + v.peso == vertex.distancia,
                (Vertice) vertice(destino));
    }
}
