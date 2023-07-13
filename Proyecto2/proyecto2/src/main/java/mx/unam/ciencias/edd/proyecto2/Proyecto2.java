package mx.unam.ciencias.edd.proyecto2;

public class Proyecto2 {
    public static void main(String[] args) {
        EntradaEstandar entradaEst = new EntradaEstandar();
        Entrada entrada = new Entrada();
        
        if(args.length == 0)
            entradaEst.leer();
        else  
            entrada.leer(args);  
    }
}
