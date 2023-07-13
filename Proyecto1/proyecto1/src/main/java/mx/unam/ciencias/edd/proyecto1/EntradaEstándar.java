package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mx.unam.ciencias.edd.Lista;

public class EntradaEstándar {
    private String buscador;
    private String archivo;

    public EntradaEstándar(String[] args) {
        this.buscador = Banderas.buscarbandera(args);
        this.archivo = Banderas.buscarArchivo(args);
    }

    public String entradaEst(String[] args) {
        Lista<Linea> lista = new Lista<Linea>();
        String c;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while ((c = br.readLine()) != null) {
                lista.agregaFinal(new Linea(c));
            }
        } catch (IOException e) {
            System.out.println("Ocurre un error: " + e);
        }
        Lista<Linea> ord = Lista.mergeSort(lista);
        String text = "";
        if (buscador.equals("-r") && ((args.length == 1))) {
            System.out.println("Imprimir texto ordenado en reversa: ");
            Lista<Linea> reversa = Banderas.reverseFlag(ord);
            text = Banderas.imprimir(reversa);
        } else if (buscador.equals("-o") && ((args.length == 2))) {
            text = Banderas.imprimir(ord);
            Banderas.saveFlag(text, archivo);
        } else {
            System.out.println("Imprimir texto ordenado: ");
            text = Banderas.imprimir(ord);
        }
        System.out.println(text);
        return text;
    }
}
