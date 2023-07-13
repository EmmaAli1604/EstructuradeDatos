package mx.unam.ciencias.edd.proyecto3;

/*
 * Clase para poder acceder a la sintaxis de las imagenes SVG
 */

public class SVG {
    private SVG(){
    }

    public static String iniciaSVG(double width, double height){
        return "<?xml version='1.0' encoding='UTF-8' ?>\n"+String.format("<svg width='%f' height='%f'>\n\t<g>\n",width,height );
    }

    public static String iniciaSVG(){
        return "<?xml version='1.0' encoding='UTF-8' ?>\n"+"<svg width='' height=''>\n\t<g>\n";
    }

    public static String circuloSVG(double cx, double cy,double radio){
        return String.format("\t<circle cx='%f' cy='%f' r='%f' stroke='blue' stroke-width='3' fill='blue'/>\n", cx,cy,radio);
    }

    public static String circulo2SVG(double cx, double cy,double radio){
        return String.format("\t<circle cx='%f' cy='%f' r='%f' stroke='red' stroke-width='3' fill='blue'/>\n", cx,cy,radio);
    }

    public static String circulo3SVG(double cx, double cy,double radio){
        return String.format("\t<circle cx='%f' cy='%f' r='%f' stroke='orange' stroke-width='3' fill='blue'/>\n", cx,cy,radio);
    }

    public static String lineaSVG(int x1, int x2, int y1, int y2){
        return String.format("\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='#3e3ec0' stroke-width='3' />\n",x1, x2, y1, y2);
    }

    public static String linea2SVG(int x1, int x2, int y1, int y2){
        return String.format("\t<line x1='%d' y1='%d' x2='%d' y2='%d' stroke='fuchsia' stroke-width='2' />\n",x1, x2, y1, y2);
    }

    
    public static String terminarSVG(){
        return "\n\t</g>\n</svg>";
    }
}
