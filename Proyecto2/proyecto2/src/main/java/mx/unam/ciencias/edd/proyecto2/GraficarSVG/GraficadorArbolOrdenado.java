package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

import mx.unam.ciencias.edd.ArbolBinarioOrdenado;

public class GraficadorArbolOrdenado extends GraficadoraArbol{

    public GraficadorArbolOrdenado(ArbolBinarioOrdenado<Integer> arbolOrd){
        super(arbolOrd);
    }

    public void graficarAO(ArbolBinarioOrdenado<Integer> arbolOrd){
        super.graficar(arbolOrd);
    }
}
