package mx.unam.ciencias.edd.proyecto2.GraficarSVG;

public class GraficarSVG {

    private GraficarSVG(){

    }

    public static String iniciaSVG(double width, double height){
        return "<?xml version='1.0' encoding='UTF-8' ?>\n"+String.format("<svg width='%f' height='%f'>\n\t<g>\n",width,height );
    }

    public static String iniciaSVG(){
        return "<?xml version='1.0' encoding='UTF-8' ?>\n"+"<svg width='' height=''>\n\t<g>\n";
    }

    public static String rectanguloTextoSVG(int xr, int yr, int widthr, int heightr, int xt, int yt, Integer elementos){
        return String.format("\t<rect x='%d' y='%d' width='%d' height='%d' fill='white' stroke='black' stroke-width='2' />\n"
        +"\t<text x='%d' y='%d'>%s</text>\n", xr, yr, widthr, heightr, xt, yt, elementos);
    }

    public static String circuloTextoSVG(double cx, double cy,double radio, String fill){
        return String.format("\t<circle cx='%f' cy='%f' r='%f' stroke='black' stroke-width='1' fill='%s'/>\n", cx,cy,radio,fill);
    }

    public static String lineaSVG(int x1, int x2, int y1, int y2){
        return String.format("\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='black' stroke-width='5' />\n",x1, x2, y1, y2);
    }

    public static String texto(int x, int y,String texto){
        return String.format("\t<text x='%d' y='%d' font-size='25' >%s</text>\n", x,y,texto);
    }

    public static String texto2(double x, double y,String texto){
        return String.format("\t<text x='%f' y='%f' font-size='12' >%s</text>\n", x,y,texto);
    }

    public static String textoColorSVG(String filltx, double xt, double yt, Integer text){
        return String.format("\t<text fill='%s' font-family='sans-serif' text-anchor='high' font-size='12' x='%f' y='%f'>%s</text>\n",filltx, xt,yt,text);
    }

    public static String lineaColorSVG(double x1, double y1, double x2 ,double y2, int stroke_width){
        return String.format("\t<line x1='%f' y1='%f' x2='%f' y2='%f' stroke='fuchsia' stroke-width='%d' />\n", x1, y1, x2, y2,stroke_width);
    }

    public static String flechadobleSVG(int x, int y){
        return String.format("\t<text x='%d' y='%d' font-size='25' >⮂</text>\n", x,y);
    }

    public static String flechaUpDownSVG(int x, int y){
        return String.format("\t<text x='%d' y='%d' font-size='33' > ⮁ </text>\n", x,y);
    }

    public static String flechaIzqSVG(int x, int y){
        return String.format("\t<text x='%d' y='%d' font-size='20' > 🡨 </text>\n", x,y);
    }

    public static String terminarSVG(){
        return "\n\t</g>\n</svg>";
    }
}
