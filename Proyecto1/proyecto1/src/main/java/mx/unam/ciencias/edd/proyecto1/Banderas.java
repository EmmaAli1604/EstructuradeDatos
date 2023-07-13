package mx.unam.ciencias.edd.proyecto1;

import mx.unam.ciencias.edd.Lista;

public class Banderas {

    public static String buscarbandera(String[] args) {
        String buscar = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-r"))
                buscar = "-r";
            else if (args[i].equals("-o"))
                buscar = "-o";
        }
        return buscar;
    }

    public static String buscarArchivo(String[] args) {
        String archivo = "";
        int posicion = args.length - 1;
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals("-o")) {
                archivo = args[i + 1];
                System.out.println("El texto se va a guardar en el archivo: " + archivo);
                if ((i + 1) == posicion) {
                    break;
                } else {
                    args[i + 1] = null;
                    args[i + 1] = args[i + 2];
                }
            }
        }
        return archivo;
    }

    public static int posicionbandera(String bandera) {
        if (bandera.equals("-r")) {
            return 1;
        } else if (bandera.equals("-o")) {
            return 2;
        } else {
            return 0;
        }
    }

    public static String[] eliminarbandera(String[] args) {
        String[] argumentos = args;
        for (int i = 0; i < argumentos.length - 1; i++) {
            if (argumentos[i].equals("-r")) {
                System.out.println("Se encontro la bandera -r");
                argumentos[i] = null;
                argumentos[i] = argumentos[i + 1];
            } else if (argumentos[i].equals("-o")) {
                System.out.println("Se encontro la bandera -o");
                argumentos[i] = null;
                argumentos[i] = argumentos[i + 1];
                break;
            }
        }
        return argumentos;
    }

    public static void saveFlag(String text, String archivo) {
        Guardar guardar = new Guardar(text, archivo);
        guardar.guardar();
    }

    public static Lista<Linea> reverseFlag(Lista<Linea> lista) {
        Lista<Linea> reversa = lista.reversa();
        return reversa;
    }

    public static String imprimir(Lista<Linea> ord) {
        String text = "";
        if (ord.getLongitud() == 0)
            return "No hay un texto que ordenar";
        Linea p = ord.eliminaPrimero();
        while (!ord.esVacia()) {
            text += p;
            p = ord.eliminaPrimero();
        }
        return text;
    }
}
