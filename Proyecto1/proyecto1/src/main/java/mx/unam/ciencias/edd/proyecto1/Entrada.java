package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;

public class Entrada {

    private Lista<Linea> lista;
    private String buscador;
    private String archivo;
    private String[] argumentos;
    private int posicion;

    public Entrada(String[] args) {
        this.lista = new Lista<Linea>();
        this.buscador = Banderas.buscarbandera(args);
        this.archivo = Banderas.buscarArchivo(args);
        this.argumentos = Banderas.eliminarbandera(args);
        this.posicion = Banderas.posicionbandera(buscador);
    }

    public String entrance(String[] args) {
        for (int i = 0; i < argumentos.length - posicion; i++) {
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(argumentos[i])));
                String s = "";
                while ((s = bf.readLine()) != null) {
                    lista.agregaFinal(new Linea(s));
                }
                bf.close();
            } catch (IOException e) {
                System.out.println(
                        "Error al leer el archivo: El archivo " + argumentos[i].toString()
                                + " no es un archivo ó el archivo no existe.");
                System.exit(1);
            }
        }
        Lista<Linea> ord = Lista.mergeSort(lista);
        String text = "";
        if (buscador.equals("-r")) {
            System.out.println("Imprimir texto ordenado en reversa: ");
            Lista<Linea> reversa = Banderas.reverseFlag(ord);
            text = Banderas.imprimir(reversa);
        } else if (buscador.equals("-o")) {
            while (!ord.esVacia()) {
                text += ord.eliminaPrimero().get() + "\n";
            }
            Banderas.saveFlag(text, archivo);
        } else {
            System.out.println("Imprimir texto ordenado: ");
            text = Banderas.imprimir(ord);
        }
        System.out.println(text);
        return text;
    }
}
