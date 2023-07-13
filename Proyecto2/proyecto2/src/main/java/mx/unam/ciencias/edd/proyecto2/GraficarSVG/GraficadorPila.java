package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

import mx.unam.ciencias.edd.Lista;

public class GraficadorPila {

    private String textosvg ="";

    public GraficadorPila(){}

    public void graficar(Lista<Integer> elementos){
        int widthr=50;
        int heightr=25;
        int xr=10;
        int yr=1;
        int xt= xr+20;
        int yt = 20;

        textosvg += GraficarSVG.iniciaSVG();
        textosvg += GraficarSVG.lineaColorSVG(1, 1, 1 , (widthr*elementos.getElementos()/2),10);
        textosvg += GraficarSVG.lineaColorSVG(widthr+20, yr, widthr+20, (widthr*elementos.getElementos()/2),6);
        for(int i=0; i<elementos.getElementos(); i++){
            textosvg += GraficarSVG.rectanguloTextoSVG(xr, yr, widthr, heightr,xt, yt, elementos.get(i));
            yr=yr+24;
            yt=yr+18;
        }
        textosvg += GraficarSVG.flechaUpDownSVG(xr+15, (widthr*elementos.getElementos()/2) +15);
        
        textosvg += GraficarSVG.terminarSVG();
        System.out.println(textosvg);
    }
    
}
