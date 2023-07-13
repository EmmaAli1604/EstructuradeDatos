package mx.unam.ciencias.edd.proyecto3;

public class Proyecto3 {
    public static void main(String[] args) {
        if(args.length==0){
           LecturaArchivoBytes.lecturaArchivos(args); 
        }else{
            Entrada.lecturaDatos(args);
        }
    }
}
