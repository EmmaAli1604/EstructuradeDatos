package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Guardar {

    private String text;
    private String archivo;

    public Guardar(String text, String archivo) {
        this.text = text;
        this.archivo = archivo;
    }

    public void guardar() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(text);
            System.out.println("Se guardo con exito el texto en el archivo: " + archivo);
            bw.close();
        } catch (IOException e) {
            System.out.println("No se pudo crear el siguiente archivo debido a " + e);
        }
    }

}
