package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;

public class DatosValidos {
    private static Lista <Integer> elementos = new Lista<>(); 
    
    private DatosValidos(){}
    
    public static boolean estructuraValida(String linea){
        if(linea.equals("ArbolAVL") || linea.equals("ArbolRojinegro")|| linea.equals("ArbolBinarioOrdenado") 
        || linea.equals("ArbolBinarioCompleto") || linea.equals("Cola") || linea.equals("Pila")||linea.equals("Arreglo")||linea.equals("Grafica") 
        || linea.equals("Lista"))
            return true;
        return false;
    }

    public static EstructuraDatos identificarEstructura(Lista<Linea> lista){
        EstructuraDatos estructura=new EstructuraDatos(null);
        boolean data=false;
        Linea linea=lista.eliminaPrimero();
        String[] partes = linea.get().split(" ");

        if(linea.get().isEmpty() || partes[0]==null){
            linea=lista.eliminaPrimero();
        }

        if(DatosValidos.estructuraValida(partes[0])){
            estructura.setEstructura(partes[0]);
            data = true;
            for(int i = 1; i<partes.length; i++){
                lista.agrega(new Linea(partes[i]));
            }
        }
        if(DatosValidos.estructuraValida(linea.get())){
            estructura.setEstructura(linea.get());
            data = true;
        }

        if(data == false){
            System.out.println(estructura.getEstructura());
            System.exit(1);
        }

        return estructura;
    }

    public static Lista<Integer> identificarNumeros(Lista<Linea> lista){
        if(lista.esVacia()){
            System.out.println("No se encuentran elementos a graficar");
            System.exit(1);
        }
        for(int i = 0; i < lista.getElementos();i++){
            if(!lista.get(i).get().isEmpty())
                separaElementos(lista.get(i).get());
        }
        return elementos;
    }

    private static Lista<Integer> separaElementos(String linea){
        String[] partes=linea.split(" ");
        for(int i = 0; i < partes.length; i++){
            try {
                elementos.agregaFinal(Integer.parseInt(partes[i]));
            } catch (NumberFormatException e) {
                System.out.println("Error al escribir el número "+ partes[i] +"\nEscribe bien los elementos a graficar");
                System.exit(1);
            }
        }

        return elementos;
    }

}
