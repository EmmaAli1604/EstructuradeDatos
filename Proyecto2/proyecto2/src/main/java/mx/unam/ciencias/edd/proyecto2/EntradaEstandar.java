package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import mx.unam.ciencias.edd.Lista;



public class EntradaEstandar {
    private Lista<Integer> elementos = new Lista<>();
    private EstructuraDatos estructura;

    public EntradaEstandar(){
    }

    public void leer(){
        Lista<Linea> lista=new Lista<>();
        String c;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while ((c = br.readLine()) != null) {
                if(!c.contains("#") && !c.isEmpty()){
                    lista.agregaFinal(new Linea(c));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer el archivo");
            System.exit(1);
        }
        
        this.estructura = DatosValidos.identificarEstructura(lista);
        this.elementos = DatosValidos.identificarNumeros(lista);
        
        Graficador graficadora= new Graficador(estructura, this.elementos);
        graficadora.graficar();

    }

}
