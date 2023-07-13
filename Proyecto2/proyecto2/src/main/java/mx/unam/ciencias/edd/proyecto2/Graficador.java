package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorArbolAVL;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorArbolCompleto;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorArbolOrdenado;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorArbolRojiNegro;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorArreglo;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorCola;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorGrafica;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorLista;
import mx.unam.ciencias.edd.proyecto2.GraficarSVG.GraficadorPila;

public class Graficador {
    private EstructuraDatos estructura;
    private Lista<Integer> elementos;

    public Graficador(EstructuraDatos estructura, Lista<Integer> elementos){
        this.estructura=estructura;
        this.elementos = elementos;
    }
    
    public void graficar(){
        switch(estructura.getEstructura()){
            case "Grafica":
                GraficadorGrafica graficaG = new GraficadorGrafica(elementos);
                graficaG.graficar();
                break;
            case "ArbolAVL":
                ArbolAVL<Integer> arbolAVL = new ArbolAVL<>();
                for(int i =0; i<elementos.getElementos(); i++){
                    arbolAVL.agrega(elementos.get(i));
                }
                GraficadorArbolAVL arbolavl = new GraficadorArbolAVL(arbolAVL);
                arbolavl.graficarAO(arbolAVL);
                break;
            case "ArbolRojinegro":
                ArbolRojinegro<Integer> arbolRojiNegro = new ArbolRojinegro<>();
                for(int i =0; i<elementos.getElementos(); i++){
                    arbolRojiNegro.agrega(elementos.get(i));
                }
                GraficadorArbolRojiNegro arbolRojiN = new GraficadorArbolRojiNegro(arbolRojiNegro);
                arbolRojiN.graficarARN(arbolRojiNegro);
                break;
            case "ArbolBinarioOrdenado":
                ArbolBinarioOrdenado<Integer> arbolOrd = new ArbolBinarioOrdenado<>();
                for(int i=0; i<elementos.getElementos(); i++){
                    arbolOrd.agrega(elementos.get(i));
                }
                GraficadorArbolOrdenado arbolOrdenado = new GraficadorArbolOrdenado(arbolOrd);
                arbolOrdenado.graficarAO(arbolOrd);
                break;
            case "ArbolBinarioCompleto":
                ArbolBinarioCompleto<Integer> arbolCom = new ArbolBinarioCompleto<>();
                for(int i=0; i<elementos.getElementos(); i++){
                    arbolCom.agrega(elementos.get(i));
                }
                GraficadorArbolCompleto arbol =new GraficadorArbolCompleto(arbolCom);
                arbol.graficarAC(arbolCom);
                break;
            case "Pila":
                GraficadorPila pila=new GraficadorPila();
                pila.graficar(elementos);
                break;
            case "Cola":
                GraficadorCola cola=new GraficadorCola();
                cola.graficar(elementos);
                break;
            case "Arreglo":
                GraficadorArreglo arreglo = new GraficadorArreglo();
                arreglo.graficar(elementos);
                break;
            case "Lista":
                GraficadorLista lista=new GraficadorLista();
                lista.graficar(elementos);
                break;
        }
    }
}
