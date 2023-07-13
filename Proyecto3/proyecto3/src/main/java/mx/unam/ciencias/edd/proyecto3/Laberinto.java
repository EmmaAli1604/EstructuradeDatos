package mx.unam.ciencias.edd.proyecto3;

import java.util.Iterator;
import java.util.Random;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.VerticeGrafica;

/*
 * Clase para poder poder construir laberintos, encontrar su solución y generar su imagen SVG.
 */

public class Laberinto implements Iterable<Laberinto.Casillas>{

    /*Clase interna para los iteradores */
    private class Iterador implements Iterator<Casillas>{
        /*Fila de la  casilla*/
        private int fila=0;
        /*Columna de la casilla */
        private int columna=0;
        /*Construye un iterador */
        public Iterador(){}
        /* Nos indica si hay un siguiente elemento*/
        @Override
        public boolean hasNext() {
            return fila != filas && columna != columnas;
        }
        /*Nos da la casillla siguiente */
        @Override
        public Laberinto.Casillas next() {
            Casillas casilla = laberinto[fila][columna];
            if(columna == columnas - 1){
                columna = 0;
                fila = fila + 1;
            }else{
                columna =  columna + 1;
            }
            return casilla;
        }
    }
    /*Clase interna Casillas */
    public class Casillas{
        /*Nos indica si tiene su puerta norte */
        private boolean northd;
        /*Nos indica si tiene su puerta sur */
        private boolean southd;
        /*Nos indica si tiene su puerta este */
        private boolean eastd;
        /*Nos indica si tiene su puerta oeste */
        private boolean westd;
        
        /*Nos indica si es la casilla de entrada del laberinto */
        private boolean start;
        /*Nos indica si es la casilla  de salida del laberinto*/
        private boolean end;

        /*Nos dice si la  casilla ya fue visitada*/
        private boolean pasado;

        /*Valor de la fila en la que se encuentra la casilla */
        private int fila;
        /*Valor de la columna en la que se encuentra la casilla */
        private int columna;
        /*Valor del tipo de la casillla */
        private int type;

        /*Valor del puntaje de la puerte en byte */
        private byte puntaje;
        /*Valor de la puerta de la puerte en byte */
        private byte puerta;
        
        /*Constructor sin parametros */
        public Casillas(){}
        
        /*Constructor de casillas que recibe como parametros la fila y la columna
         * @param fila y columna en la que se encuentra la casilla.
         */
        public Casillas(int fila, int columna){
            this.fila = fila;
            this.columna = columna;
            this.northd = this.southd = this.eastd = this.westd = true;
            this.pasado = false;
            if(start){
                westd = false;
            }else if(end){
                eastd = false;
            }
        }

        /*Constructor que recibe el valor de la casilla en byte, su fila y columna en la que se encuentra
         * @param valor de la casilla en byte, la fila y columna de la casilla en el laberinto.
         */
        public Casillas(int b, int fila, int columna){
            this.puntaje = (byte) ((b & 0xF0) >>> 4);
            this.puerta = (byte) (b & 0x0F);
            this.fila = fila;
            this.columna = columna;
        }

        /*Genera el byte de la casilla, donde los primero 4 bitys más significativos
        * corresponden al puntaje de la casilla y los 4 bits menos significativos corresponde 
        * a byte de la puerta.
         * @return byte de la casilla
         */
        public byte casillasByte(){
            return (byte) ((puntaje << 4 | puerta));
        }

        /*
         * Establece el tipo de casilla de acuerdo a su posición en el laberinto, para poder identificar 
         * los límites del laberinto y los posibles vecinos de una casilla.
         */
        public void setTipoCasilla(int ancho, int alto){
            if(fila==0 && columna == 0){
                type = 1;
            }else if(fila == 0 && columna>0 && columna<ancho-1){
                type=2;
            }else if(fila == 0 && columna == ancho-1){
                type=3;
            }
            else if(fila == alto-1 && columna == 0){
                type = 7;
            }else if(columna==0 && fila>0 && fila<alto-1){
                type = 4;
            }else if(fila>0 && fila<alto-1 && columna>0 && columna<ancho-1){
                type = 5;
            }else if(columna == ancho-1 && fila>0 && fila<alto-1){
                type = 6;
            }else if(fila == alto-1 && columna>0 && columna<ancho-1){
                type = 8;
            }else if(fila == alto - 1 && columna == ancho-1){
                type = 9;
            }
        }

        /*Establece el byte de la puerta, de acuerdo a las puertas que tenga abierta la casilla */
        public void setPuerta(){
            if(!northd && !eastd && !westd && !southd){
                this.puerta = (byte)0x00;
            }
            else if(!northd && !southd && !westd && eastd){
                this.puerta = (byte)0x01;
            }
            else if(northd && !southd && !eastd && !westd){
                this.puerta = (byte)0x02;
            }
            else if(northd && !southd && eastd && !westd){
                this.puerta = (byte)0x03;
            }
            else if(!northd && !southd &&!eastd && westd){
                this.puerta = (byte)0x04;
            }
            else if(!northd && !southd && eastd && westd){
                this.puerta = (byte)0x05;
            }
            else if(northd && !southd && !eastd && westd){
                this.puerta = (byte)0x06;
            }
            else if (!southd && northd && eastd && westd){
                this.puerta = (byte)0x07;
            }
            else if(!northd && southd && !eastd && !westd){
                this.puerta = (byte)0x08;
            }
            else if(!northd && southd && eastd && !westd){
                this.puerta = (byte)0x09;
            }
            else if( southd && northd &&!eastd && !westd){
                this.puerta = (byte)0x0a;
            }
            else if(northd && southd && eastd && !westd){
                this.puerta = (byte)0x0b;
            }
            else if(!northd && southd && !eastd && westd){
                this.puerta = (byte)0x0c;
            }
            else if(!northd && southd && eastd && westd){
                this.puerta = (byte)0x0d;
            }
            else if(!eastd && northd && southd && westd){
                this.puerta = (byte)0x0e;
            }
            else if(northd && eastd && westd && southd){
                this.puerta = (byte)0x0f;  
            }

            if(start){
                this.puerta = (byte) (this.puerta & 0x0b);
            }else if(end){
                this.puerta = (byte) (this.puerta & 0x0e);
            }
        }
        
        /*
         * Establace las puertas que tiene abierta la casilla de acuerdo a su byte recibido.
         */
        public void setPuertaByte(int b){
            switch(b){
                case 0:
                northd = southd = westd = eastd = false;
                break;
                case 1:
                northd = southd = westd = false; 
                eastd = true ;
                break;
                case 2:
                southd = eastd = westd = false;
                northd = true;
                break;
                case 3: 
                southd = westd = false;
                northd = eastd = true;
                break;
                case 4:
                northd = southd = eastd = false;
                westd = true;
                break;
                case 5:
                northd  = southd = false; 
                eastd = westd = true;
                break;
                case 6:
                northd = westd = true;
                southd = eastd = false; 
                break;
                case 7:
                southd = false; 
                northd = eastd = westd = true;
                break;
                case 8:
                northd = eastd = westd = false; 
                southd = true;
                break;
                case 9:
                northd = westd = false;
                southd = eastd = true;
                break;
                case 10:
                eastd = westd = false;
                southd = northd = true;
                break;
                case 11:
                westd = false;
                northd = southd = eastd = true; 
                break;
                case 12:
                northd = eastd = false; 
                westd = southd = true;
                break;
                case 13:
                northd = false;
                southd = eastd = westd = true;
                break;
                case 14:
                eastd = false; 
                northd = southd = westd = true;
                break;
                case 15:
                northd = eastd = westd = southd = true;
                break;
            }
        }

        /*Establace el puntaaje de la puerta de acuerdo a un valor aleatorio del 1 al 15.
         * @param un valor aleatorio de 1 al 15.
         */
        public void setPuntaje(int i){
            this.puntaje = (byte) (i);
        }

        /*
         * Genera un StringBuilder apartir del String que tenemos en la clase SVG.
         */
        public StringBuilder casillasSVG(){
            StringBuilder s = new StringBuilder();
            if(start){
                if(northd)
                s.append(SVG.lineaSVG(columna*20, fila*20, 20+columna*20, fila * 20));
                if(eastd)
                s.append(SVG.lineaSVG(20+columna*20, fila * 20, 20+columna*20, 20 + fila*20));
                if(southd)
                s.append(SVG.lineaSVG(columna*20, 20 + fila*20, 20+columna*20, 20 + fila*20));
            }else if(end){
                if(northd)
                s.append(SVG.lineaSVG(columna*20, fila*20, 20+columna*20, fila * 20));
                if(westd)
                s.append(SVG.lineaSVG(columna*20, fila*20, columna*20, 20 + fila*20));
                if(southd)
                s.append(SVG.lineaSVG(columna*20, 20 + fila*20, 20+columna*20, 20 + fila*20));    
            }else{
                if(northd)
                s.append(SVG.lineaSVG(columna*20, fila*20, 20+columna*20, fila * 20));
                if(eastd)
                s.append(SVG.lineaSVG(20+columna*20, fila * 20, 20+columna*20, 20 + fila*20));
                if(westd)
                s.append(SVG.lineaSVG(columna*20, fila*20, columna*20, 20 + fila*20));
                if(southd)
                s.append(SVG.lineaSVG(columna*20, 20 + fila*20, 20+columna*20, 20 + fila*20));
            }
 
            return s;
        }

    }
    /*Valor de las filas del laberinto */
    private int filas;
    /*Valor de las columnas del laberinto */
    private int columnas; 
    /*Valor aleatorio */
    private Random r;
    /*Matriz de casillas para el laberinto */
    private Casillas[][] laberinto;
    /*Casilla de entrada */
    private Casillas start;
    /*Casilla de salida */
    private Casillas end;
    /*Grafica del laberinto */
    private Grafica<Casillas> grafica;
    /*Representación de cadena para el SVG. 
     * Utilizamos un StringBuilder ya que como es más óptimo utilizarlo, 
     * debido a que si utilizamos el String cada vez que modifiquemos se 
     * crea un objeto miesytas como el StringBuilder es mutable, podemos 
     * modificar el String sin necesidad de que se creé otro objecto.
    */
    private StringBuilder s;

    /*Constructor de laberinto, el cuál recibe la semilla, las filas y la columna para generar el laberinto
     * @param el valor de la semilla, las filas y las columnas del laberinto.
     */
    public Laberinto(long seed ,int filas, int columnas){
        this.filas = filas;
        this.columnas = columnas;
        r = new Random(seed);
        
        this.laberinto = new Casillas[filas][columnas];
        
        if(filas >= columnas){
            for(int i = 0; i < filas; i++){
                for(int j = 0; j < columnas; j++){
                    laberinto[i][j] = new Casillas(i,j);
                    if(filas == columnas){
                        laberinto[i][j].setTipoCasilla(filas,columnas);
                    }else{
                        laberinto[i][j].setTipoCasilla(columnas,filas);
                    }
                }
            }
            
        }else{
            for(int i = 0; i < columnas; i++){
                for(int j = 0; j < filas; j++){
                    laberinto[j][i] = new Casillas(j,i);
                    laberinto[j][i].setTipoCasilla(columnas,filas);
                }
            }
        }
        
        this.start = buildStart();
        start.start=true;
        this.end = buildEnd();
        end.end=true;
        buildMaze();
    }
    
    /*Constructor del laberinto, el cuál recibe el valor de las filas, columnas y un lista de enteros
     que representa los bytes del archivo maze.
     * @param el valor de las filas y columnas del laberinto, y uns lista de enteros que representa 
     * los bytes del archivo maze
     */
    public Laberinto(int filas, int columnas, Lista<Integer> maze){
        this.filas = filas;
        this.columnas = columnas;
        this.laberinto = new Casillas[filas][columnas];
        s = new StringBuilder();
        int index = 0;
        for(int i = 0; i < filas ; i ++){
            for(int j  = 0; j < columnas ; j++){
                laberinto[i][j] = new Casillas(maze.get(index) & 0xFF,i, j);
                laberinto[i][j].setPuertaByte(laberinto[i][j].puerta);
                index++;
            }
        }
        searchStart();
        this.start.start = true;
        searechEnd();
        this.end.end= true;
        graficaEnMaze();
    }

    /*
     * Construye el laberinto en bytes para poder guardar cada byte de las casillas en un arreglo.
     * @return un arreglo de enteros.
     */
    public int[] buildMazeBytes(){
        int[] maze = new int[filas * columnas + 6];
        maze[0] = 0x4d;
        maze[1] = 0x41;
        maze[2] = 0x5a;
        maze[3] = 0x45;
        maze[4] = (byte) filas;
        maze[5] = (byte) columnas;
        int index = 6;
        for(Casillas c : this){
            c.setPuerta();
            c.setPuntaje(r.nextInt(16));
            maze[index] = c.casillasByte() & 0xFF;
            index++;
        }
        return maze;
    }

    /*
     * Construye el laberinto.
     */
    private void buildMaze() {
        Lista<Casillas> vecinos;
        Pila<Casillas> pila = new Pila<>();
        pila.mete(start);
        while(!pila.esVacia()){
            Casillas c = pila.mira();
            c.pasado = true;
            vecinos = posibilidades(c);
            if(!vecinos.esVacia()){
                if(vecinos.getLongitud()==1){
                    eraseDoor(c, vecinos.getPrimero());
                    pila.mete(vecinos.getPrimero());
                    continue;
                }else{
                    int aleatorio = r.nextInt(vecinos.getLongitud());
                    Casillas next = vecinos.get(aleatorio);
                    eraseDoor(c,next);
                    pila.mete(next);
                }
            }else{
                pila.saca();
            }
        }
    }

    /*
     * Borra una puerta entre dos casillas recibidas.
     * @param casilla actual y la siguiente casilla a la actual.
     */
    private void eraseDoor(Casillas actual, Casillas next){
        if(actual.fila == next.fila){
            if(actual.columna < next.columna){
                actual.eastd = false;
                next.westd = false;
            }else{
                next.eastd = false;
                actual.westd = false;
            }
            
        }else{
            if(actual.fila < next.fila){
                actual.southd = false;
                next.northd = false;
            }else{
                next.southd = false;
                actual.northd = false;
            }
        }
    }

    /*
     * De acuerdo al tipo de casilla que recibe el método, regresamos una lista con sus posibles vecinos.
     * @param casilla 
     * @return una lista con sus poisbles vecinos de acuerdo al tipo de la casilla como parametro.
     */
    private Lista<Casillas> posibilidades(Casillas casillas){
        Casillas c1, c2, c3, c4;
        Lista<Casillas> posibilidades = new Lista<>();
        switch(casillas.type){
            case 1:
                c1 = laberinto[casillas.fila][casillas.columna+1];
                c2 = laberinto[casillas.fila+1][casillas.columna];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
            break;
            case 2:
                c1 = laberinto[casillas.fila][casillas.columna-1];
                c2 = laberinto[casillas.fila][casillas.columna+1];
                c3 = laberinto[casillas.fila+1][casillas.columna];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
            break;
            case 3:
                c1 = laberinto[casillas.fila][casillas.columna-1];
                c2 = laberinto[casillas.fila+1][casillas.columna];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
            break;
            case 4:
                c1 = laberinto[casillas.fila+1][casillas.columna];
                c2 = laberinto[casillas.fila-1][casillas.columna];
                c3 = laberinto[casillas.fila][casillas.columna+1];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
                if(!c3.pasado)
                    posibilidades.agrega(c3);
            break;
            case 5:
                c1 = laberinto[casillas.fila+1][casillas.columna];
                c2 = laberinto[casillas.fila-1][casillas.columna];
                c3 = laberinto[casillas.fila][casillas.columna+1];
                c4 = laberinto[casillas.fila][casillas.columna-1];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
                if(!c3.pasado)
                    posibilidades.agrega(c3);
                if(!c4.pasado)
                    posibilidades.agrega(c4);
            break;
            case 6:
                c1 = laberinto[casillas.fila+1][casillas.columna];
                c2 = laberinto[casillas.fila-1][casillas.columna];
                c3 = laberinto[casillas.fila][casillas.columna-1];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
                if(!c3.pasado)
                    posibilidades.agrega(c3);
            break;
            case 7:
                c1 = laberinto[casillas.fila-1][casillas.columna];
                c2 = laberinto[casillas.fila][casillas.columna+1];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
            break;
            case 8:
                c1 = laberinto[casillas.fila][casillas.columna+1];
                c2 = laberinto[casillas.fila][casillas.columna-1];
                c3 = laberinto[casillas.fila-1][casillas.columna];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
                if(!c3.pasado)
                    posibilidades.agrega(c3);
            break;
            case 9:
                c1 = laberinto[casillas.fila-1][casillas.columna];
                c2 = laberinto[casillas.fila][casillas.columna-1];
                if(!c1.pasado)
                    posibilidades.agrega(c1);
                if(!c2.pasado)
                    posibilidades.agrega(c2);
            break;
            default:
            break;
        }
        return posibilidades;
    }

    /*
     * Asignamos la casilla de entrada del laberinto.
     * @return casilla de entrada en el laberinto
     */
    private Casillas buildStart(){
        return laberinto[r.nextInt(laberinto.length)][0];
    }

    /*
     * Asignamos la casilla de salida del laberinto.
     * @return casilla de salida en el laberinto.
     */
    private Casillas buildEnd(){
        return laberinto[r.nextInt(laberinto.length)][columnas-1];
    }

    /*
     * Busca la entrada del laberinto.
     */
    public void searchStart(){
        int f = 0;
        int c = 0;
        for(int i = 0; i<columnas; i++){
            if(!laberinto[f][i].northd){
                this.start = laberinto[f][i];
                break;
            }
        }
        
        if(this.start == null){
            for(int i = 0; i < filas ; i++){
                if(!laberinto[i][c].westd){
                    this.start = laberinto[i][c];
                    break;
                }
            }
        }
    }

    /*
     * Busca la salida del laberinto.
     */
    private void searechEnd(){
        int f = filas - 1;
        int c = columnas - 1;
        for(int i = 0; i < columnas; i++){
            if(!laberinto[f][i].southd){
                this.end = laberinto[f][i];
                break;
            }
        }
        
        if(this.end == null){
            for(int i = 0; i < filas ; i++){
                if(!laberinto[i][c].eastd){
                    this.end = laberinto[i][c];
                    break;
                }
            }
        }
    }

    /*
     * Construye el laberinto y la solución en un StringBuilder, para así poder generar la imagen SVG.
     * @return un StringBuilder de la imagen SVG del laberinto.
     */
    public StringBuilder mazeSolucionSVG(){
        s = new StringBuilder(SVG.iniciaSVG(20+columnas*20, 20+filas*20));
        for(Casillas c : this){
            s.append(c.casillasSVG());
        }
        solvingMaze();
        s.append(SVG.terminarSVG());
        return s;
    }
    
    /*
     * Construye una gráfica del laberinto, así mismo conecta a las casillas cuyas puertas esten abiertas.
     */
    public void graficaEnMaze(){
        grafica = new Grafica<>();
        for(Casillas c: this){
            grafica.agrega(c);
        }
        
        for(Casillas c : this){
            if(c.fila == filas -1 && c.columna == columnas -1){
                break;
            }

            if(c.fila == filas - 1){
                conectaDerecha(c);
                continue;
            }

            if(c.columna == columnas-1){
                conectaAbajo(c);
                continue;
            }
            conectaAbajo(c);
            conectaDerecha(c);
        }
    }
    
    /*
     * Recibe una casilla y verifica si su puerta del sur esta abierta, si lo esta se conecta con la casilla que tiene al sur.
     * @param casilla a conectar.
     */
    private void conectaAbajo(Casillas c){
        if(!c.southd){
            grafica.conecta(c, laberinto[c.fila+1][c.columna],1 + c.puntaje + laberinto[c.fila+1][c.columna].puntaje);
        }
    }
    
    /*
     * Recibe una casilla y verifica si su puerta del este esta abierta, si lo esta se conecta con la casilla que tiene al este.
     * @param casilla a conectar.
     */
    private void conectaDerecha(Casillas c){
        if(!c.eastd){
            grafica.conecta(c, laberinto[c.fila][c.columna+1],1 + c.puntaje + laberinto[c.fila][c.columna+1].puntaje);
        }
    }
    
    /*
     * Resolvemos el laberinto utilizando dijkstra, de la clase Gráficas.
     */
    public void solvingMaze(){
        Lista<VerticeGrafica<Casillas>> trayectoria = grafica.dijkstra(start, end);

        if(trayectoria.esVacia()){
            System.err.println("Archivo inválido.");
            System.exit(1);;
        }

        for(int i = 0; i < trayectoria.getElementos()-1; i++){
            s.append(SVG.linea2SVG(10+trayectoria.get(i).get().columna*20, 10+trayectoria.get(i).get().fila*20, 10+trayectoria.get(i+1).get().columna*20, 10+trayectoria.get(i+1).get().fila*20));
        }
        
        s.append(SVG.circulo3SVG(10+start.columna*20, 10+start.fila*20, 5));
        s.append(SVG.circulo2SVG(10+end.columna*20, 10+end.fila*20, 5));

    }
    
    /**
     * Regresa un iterador para recorrer el laberinto por filas.
     * @return un iterador para recorrer el laberinto por filas.
     */
    @Override
    public Iterator<Casillas> iterator(){
        return new Iterador();
    }
}
