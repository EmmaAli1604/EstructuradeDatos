package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

import mx.unam.ciencias.edd.ArbolAVL;


public class GraficadorArbolAVL extends GraficadorArbolOrdenado{

    public GraficadorArbolAVL(ArbolAVL<Integer> arbolAVL) {
        super(arbolAVL);
    }  

    public void graficarAVL(ArbolAVL<Integer> arbolAVL){
        super.graficar(arbolAVL);
    }
}
