package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedOutputStream;
import java.io.IOException;

/*
 * Clase para poder procesar los argumentos de la terminal y crear el laberinto
 */

public class Entrada {
    /*Bandera para poder inicar el laberinto */
    private static Banderas g = new Banderas("-g", false);
    /*Bandera para la semilla (esta es opcional) */
    private static Banderas s = new Banderas("-s", false);
    /*Bandera para establecer las columnas del laberinto*/ 
    private static Banderas w = new Banderas("-w", false);
    /*Bandera para establecer las filas del laberinto */
    private static Banderas h = new Banderas("-h", false);
    /*Valor de las filas */
    private static int filas;
    /*Valor de las columnas */
    private static int columnas;
    /*Valor de la semilla */
    private static long seed=0;

    /*
     * Recorra los argumentos que se pasan como parametro y busca las banderas, sino se encuentran 
     * las banderas -g , -w y -h, no se puede generar el laberinto, y por ende no se puede generar 
     * el archivo mnaze.
     * @param los un arreglo de representacion cadena de la terminal
     */
    public static void lecturaDatos(String[] args){

        for(int i = 0; i < args.length ; i++){
            if(g.getFlag().equals(args[i])){
                g.setBflag(true);
            }

            if(s.getFlag().equals(args[i])){
                try {
                    seed = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    System.err.println("El número introducido para la semilla no es válido.");
                    System.exit(1);
                }catch (IndexOutOfBoundsException e){
                    System.err.println("El número introducido para la semilla no es válido.");
                    System.exit(1);
                }
            }
        }

        if(!g.getBflag()){
            System.err.println("No se encontró la bandera -g para iniciar el laberinto");
            System.exit(1);
        }

        for(int i = 0; i < args.length; i++){
            if(w.getFlag().equals(args[i])){
                w.setBflag(true);
                try {
                    filas = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    System.err.println("El número introducido en la bandera -w  no es válido.");
                    System.exit(1);
                }catch (IndexOutOfBoundsException e){
                    System.err.println("El número introducido para la semilla no es válido.");
                    System.exit(1);
                }
            }
        }

        if(!w.getBflag()){
            System.err.println("No se encontró la bandera -w para iniciar el laberinto");
            System.exit(1);
        }

        for(int i = 0; i < args.length; i++){
            if(h.getFlag().equals(args[i])){
                h.setBflag(true);
                try {
                    columnas = Integer.parseInt(args[i+1]);
                } catch (NumberFormatException e) {
                    System.err.println("El número introducido en la bandera -h no es válido.");
                    System.exit(1);
                } catch(IndexOutOfBoundsException e){
                    System.err.println("El número introducido en la bandera -h no es válido.");
                    System.exit(1);
                }
            }
        }

        if(!h.getBflag()){
            System.err.println("No se encontró la bandera -h para iniciar el laberinto");
            System.exit(1);
        }
        
        if(filas <= 1 || filas >= 256 || columnas <= 1 || columnas >= 256){
            System.err.println("No se puede crear un laberinto de "+ filas + " por " + columnas);
            System.exit(1);
        }

        if(seed == 0){
            seed = System.currentTimeMillis();
        }
        
        Laberinto lab = new Laberinto(seed,filas, columnas);
        int[] maze = lab.buildMazeBytes();
        
        try {
            BufferedOutputStream out = new BufferedOutputStream(System.out);
            for(int i = 0; i < maze.length; i++){
                out.write(maze[i]);
            }
            out.close();
        } catch (IOException e) {
            System.out.println("Ocurrió un problema al crear el archivo .mze");
        }
    }

}
