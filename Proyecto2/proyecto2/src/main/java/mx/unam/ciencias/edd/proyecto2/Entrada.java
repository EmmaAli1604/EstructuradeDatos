package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import mx.unam.ciencias.edd.Lista;


public class Entrada {
    private EstructuraDatos estructura;
    private Lista<Integer> elementos = new Lista<>();

    public void leer(String[] args){
        Lista<Linea> lista= new Lista<>();
        try {
            BufferedReader bf = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])));
            String c = "";
            while ((c = bf.readLine()) != null) {
                if(!c.contains("#") && !c.isEmpty()){
                    lista.agregaFinal(new Linea(c));
                }
            }
            bf.close();
        } catch (IOException e) {
            System.out.println(
                    "Error al leer el archivo: El archivo " + args[1].toString()
                            + " no es un archivo ó el archivo no existe.");
            System.exit(1);
        }
        
        estructura = DatosValidos.identificarEstructura(lista);
        elementos = DatosValidos.identificarNumeros(lista);

        Graficador graficadora= new Graficador(estructura, this.elementos);
        graficadora.graficar();

    }
}
