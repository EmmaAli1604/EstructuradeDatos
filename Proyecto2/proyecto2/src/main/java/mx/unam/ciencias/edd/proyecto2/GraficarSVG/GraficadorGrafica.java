package mx.unam.ciencias.edd.proyecto2.GraficarSVG;
import mx.unam.ciencias.edd.Grafica;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeGrafica;

public class GraficadorGrafica {
    private int width;
    private int height;
    private int radio;
    private String textoSVG="";
    private Grafica<Integer> grafica = new Grafica<>();
    private Lista<VerticesCoordenadas> verticesCoord = new Lista<>();
    
    private class VerticesCoordenadas{
        public double x;
        public double y;
        public Integer vertice;

        public VerticesCoordenadas(double x, double y, Integer vertice){
            this.x=x;
            this.y=y;
            this.vertice=vertice;
        }    
        
        public double getX(){
            return x;
        }

        public double getY(){
            return y;
        }

        public Integer getVertice(){
            return vertice;
        }
    }

    public GraficadorGrafica(Lista<Integer> elementos){
        if(elementos.getLongitud()%2 != 0){
            System.out.println("La cantidad de elementos no son pares");
            System.exit(1);
        }
        
        this.grafica=nuevaGrafica(elementos);
        this.width=radio()*2+300;
        this.height=radio()*2+300;
        this.radio=radio();

    }

    public void graficar(){
        textoSVG += GraficarSVG.iniciaSVG(width,height);
        graficaVerticesyAristas();
        textoSVG += GraficarSVG.terminarSVG();
        System.out.println(textoSVG);
    }
    
    
    private Grafica<Integer> nuevaGrafica(Lista<Integer> elementos){
        for(int i =0; i<elementos.getLongitud(); i++){
            if(!grafica.contiene(elementos.get(i)))
            grafica.agrega(elementos.get(i));
        }
        
        for(int i = 0; i<elementos.getLongitud(); i++){
            if(elementos.get(i) == elementos.get(i+1)){
                i=i+1;
            }else{
                grafica.conecta(elementos.get(i), elementos.get(i+1));
                i=i+1;
            }
        }
        return grafica;
    }
    
    public int radio(){
        return grafica.getElementos()*60;
    }
    
    private void graficaVerticesyAristas(){
        double angulo= 360/grafica.getElementos();
        double grado = 0;
        Lista<VerticeGrafica<Integer>> vertices= new Lista<>();

        grafica.paraCadaVertice((vertice) -> vertices.agrega(vertice));

        for (VerticeGrafica<Integer> verticeGrafica : vertices) {
            double x = Math.toRadians(((radio*2)*Math.PI)) + radio* Math.cos(Math.toRadians(grado))+width/2-(7*grafica.getElementos());
            double y = Math.toRadians(((radio*2)*Math.PI)) + radio* Math.sin(Math.toRadians(grado))+height/2-(7*grafica.getElementos());
            grado += angulo;
            VerticesCoordenadas vertexCord = new VerticesCoordenadas(x, y, verticeGrafica.get());
            this.verticesCoord.agrega(vertexCord);
            for(VerticeGrafica<Integer> vecino:verticeGrafica.vecinos()){
                if(getCordX(vecino) != 0 && getCordY(vecino) != 0)
                    textoSVG += GraficarSVG.lineaColorSVG(x, y, getCordX(vecino), getCordY(vecino),3);
                
            }
        }
        
        for(VerticesCoordenadas verticeCoord : verticesCoord){
            textoSVG +=  GraficarSVG.circuloTextoSVG(verticeCoord.getX(), verticeCoord.getY(), 30, "white");
            textoSVG +=  GraficarSVG.textoColorSVG("black", verticeCoord.getX(), verticeCoord.getY(), verticeCoord.getVertice());
        }
    }

    private double getCordX(VerticeGrafica<Integer> vertice){
        double x = 0;
        for(VerticesCoordenadas vertice2 : verticesCoord){
            if(vertice2.getVertice() == vertice.get()){
                x = vertice2.getX();
                break;
            }
        }
        return x;
    }

    private double getCordY(VerticeGrafica<Integer> vertice){
        double y = 0;
        for(VerticesCoordenadas vertice2 : verticesCoord){
            if(vertice2.getVertice() == vertice.get()){
                y = vertice2.getY();
                break;
            }
        }
        return y;
    }
}
