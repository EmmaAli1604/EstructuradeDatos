package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

import mx.unam.ciencias.edd.Lista;

public class GraficadorArreglo {
    private String textosvg="";

    public GraficadorArreglo(){}

    public void graficar(Lista<Integer> elementos){
        int xr=1;
        int yr=1;
        int widthr=50;
        int heightr=25;
        int xt= (widthr/2)-5;
        int yt = heightr-5;

        textosvg += GraficarSVG.iniciaSVG();
        for(int i=0; i<elementos.getElementos(); i++){
            textosvg += GraficarSVG.rectanguloTextoSVG(xr, yr, widthr, heightr,xt, yt, elementos.get(i));
            xr=xr+50;
            xt=xr+18;
        }
        
        textosvg += GraficarSVG.terminarSVG();
        System.out.println(textosvg);
    }
}
