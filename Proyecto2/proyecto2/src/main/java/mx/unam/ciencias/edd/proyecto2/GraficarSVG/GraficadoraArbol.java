package mx.unam.ciencias.edd.proyecto2.GraficarSVG;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;

public class GraficadoraArbol {

    protected String textoSVG ="";
    protected double height;
    protected double width;
    protected ArbolBinario<Integer> arbol;

    public GraficadoraArbol(ArbolBinario<Integer> arbol){
        this.arbol=arbol;
    }

    
    protected void graficar(ArbolBinario<Integer> arbol){
        VerticeArbolBinario<Integer> vertice = arbol.raiz();
        
        this.width = width(arbol.raiz());
        this.height = arbol.altura()*110+100;

        textoSVG += GraficarSVG.iniciaSVG(width, height);
        DFSPreOrder(vertice, width/2, 50);
        textoSVG += GraficarSVG.terminarSVG();

        System.out.println(textoSVG);
    }

    private void DFSPreOrder(VerticeArbolBinario<Integer> vertice, double x, double y){
        if(vertice==null){
            return;
        }
    
        VerticeArbolBinario<Integer> izq = vertice.hayIzquierdo()? vertice.izquierdo() : null;
        VerticeArbolBinario<Integer> der = vertice.hayDerecho()? vertice.derecho() : null;
        double y1 = y+110;
    
        if(izq != null){
            textoSVG += GraficarSVG.lineaColorSVG(x, y, x-separacionVertices(vertice) , y1, 3);
        }
        
        if(der != null){
            textoSVG += GraficarSVG.lineaColorSVG(x, y, x+separacionVertices(vertice), y1, 3);
        }

        if(arbol instanceof ArbolRojinegro){
            ArbolRojinegro<Integer> arbolRN = new ArbolRojinegro<>(arbol);
            if(arbolRN.getColor(vertice).equals(Color.ROJO)){
                textoSVG += GraficarSVG.circuloTextoSVG(x, y,20, "red");
                textoSVG += GraficarSVG.textoColorSVG("white", x-2, y+4, vertice.get());
            }else{
                textoSVG += GraficarSVG.circuloTextoSVG(x, y,20, "black");
                textoSVG += GraficarSVG.textoColorSVG("white", x-2, y+4, vertice.get());
            }
        }
        else if(arbol instanceof ArbolAVL){
            textoSVG += GraficarSVG.circuloTextoSVG(x, y, 20,"white");
            textoSVG += GraficarSVG.textoColorSVG("black", x-2, y+4, vertice.get());
            textoSVG += GraficarSVG.texto2(x-14, y-28, alturaybalance(vertice));
        }else{
            textoSVG += GraficarSVG.circuloTextoSVG(x, y, 20,"white");
            textoSVG += GraficarSVG.textoColorSVG("black", x-2, y+4, vertice.get());
        }
        
        DFSPreOrder(izq, x-separacionVertices(vertice), y1);
        DFSPreOrder(der, x+separacionVertices(vertice), y1);
    }

    private double separacionVertices(VerticeArbolBinario<Integer> vertice){
        double separacion=width/2;
        for(int i=0; i<=vertice.profundidad();i++){
           separacion = separacion/2;
        } 
        return separacion;
    }

    private int width(VerticeArbolBinario<Integer> vertice){
        int izq = 0;
        int der = 0;
        while(vertice.hayIzquierdo()){
            izq++;
            vertice = vertice.izquierdo();
        }
        vertice = arbol.raiz();
        while(vertice.hayDerecho()){
            der++;
            vertice=vertice.derecho();
        }
        int max=Math.max(izq, der);
        int space = 40;
        for(int i=0; i<= max;i++){
            space=space*2;
        }
        return space * 2;
    }

    private String alturaybalance(VerticeArbolBinario<Integer> vertice){
        return String.format("%d / %d", vertice.altura(),balance(vertice));
    }

    private int balance(VerticeArbolBinario<Integer> vertice){
        VerticeArbolBinario<Integer> izq = vertice.hayIzquierdo()? vertice.izquierdo(): null;
        VerticeArbolBinario<Integer> der = vertice.hayDerecho()? vertice.derecho(): null;

        return getAltura(izq)-getAltura(der);
    }

    private int getAltura(VerticeArbolBinario<Integer> vertice){
        return (vertice == null)? -1 : vertice.altura();
    }
}