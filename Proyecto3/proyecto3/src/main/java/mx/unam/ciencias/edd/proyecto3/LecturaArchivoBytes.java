package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedInputStream;
import java.io.IOException;
import mx.unam.ciencias.edd.Lista;

/*
 * Clase para poder leer el archivo maze 
 */

public class LecturaArchivoBytes {
    public static void lecturaArchivos(String[] args){
        /*Valor de las filas del archivo maze */
        int fila = 0;
        /*Valor de las columnas del arhivo maze */
        int columna = 0;
        /*Valor del byte del indice i*/
        int c = 0;
        /*Contador del índice*/
        long index = 0;
        /*Lista para guardar los bytes del archivo maze */
        Lista<Integer> maze = new Lista<>();
        
        try {
            BufferedInputStream br = new BufferedInputStream(System.in);
            while ((c = br.read()) != -1) {
                if(index <= 4 && (c == (byte) 0x4d || c == (byte) 0x41 || c == (byte) 0x5a || c==(byte) 0x45)){
                    index++;                                            
                    continue;
                }   
                if(fila == 0){ 
                    fila = c;
                    index++;
                    continue;
                }
                if(columna == 0 && index == 5){
                    columna = c;
                    index++;
                    continue;
                }
                maze.agrega(c & 0xFF);
                index++;
            }
            br.close();
        } catch (IOException e) {
            System.err.println("Ocurrio un error al momento de leer el archivo");
        }
        
        Laberinto lab = new Laberinto(fila, columna, maze);
        System.out.println(lab.mazeSolucionSVG());
    } 
}
