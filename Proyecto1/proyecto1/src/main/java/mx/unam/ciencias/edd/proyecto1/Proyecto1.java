package mx.unam.ciencias.edd.proyecto1;

public class Proyecto1 {
    public static void main(String[] args) {
        String bandera = Banderas.buscarbandera(args);

        if (args.length == 0 || ((args.length == 1) && bandera.equals("-r"))
                || ((args.length == 2) && bandera.equals("-o"))) {
            EntradaEstándar entrada_estandar = new EntradaEstándar(args);
            entrada_estandar.entradaEst(args);
        } else {
            Entrada entrada = new Entrada(args);
            entrada.entrance(args);
        }
    }
}
