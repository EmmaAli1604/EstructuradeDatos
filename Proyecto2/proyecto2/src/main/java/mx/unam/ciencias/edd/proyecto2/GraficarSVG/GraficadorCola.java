package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

import mx.unam.ciencias.edd.Lista;

public class GraficadorCola {
    private String textosvg = "";

    public GraficadorCola(){}

    public void graficar(Lista<Integer> elementos){
        int xr=20;
        int yr=7;
        int widthr=50;
        int heightr=25;
        int xt= xr+20;
        int yt = yr+20;
        int xf;
        int yf= yt;

        textosvg += GraficarSVG.iniciaSVG();
        textosvg += GraficarSVG.lineaColorSVG(1, 1, elementos.getElementos()*82, yr-5,7);
        textosvg += GraficarSVG.lineaColorSVG(1, yr+30, elementos.getElementos()*82, yr+30,7);
        for(int i=0; i<elementos.getElementos(); i++){
            textosvg += GraficarSVG.rectanguloTextoSVG(xr, yr, widthr, heightr,xt, yt, elementos.get(i));
            xr=xr+80;
            xt=xr+20;
            xf=xr-20;
            textosvg += GraficarSVG.flechaIzqSVG(xf, yf);
        }
        
        textosvg += GraficarSVG.terminarSVG();
        System.out.println(textosvg);
    }
}
