package mx.unam.ciencias.edd.proyecto2;

public class EstructuraDatos {
    private String estructura;

    public EstructuraDatos(String estructura){
        if(estructura == null){
            this.estructura="No se encontró la estructura";
            return;
        }
        switch(estructura){
            case "ArbolAVL":
                this.estructura = "ArbolAvl";
                break;
            case "ArbolRojinegro":
                this.estructura="ArbolRojinegro";
                break;
            case "ArbolBinarioOrdenado":
                this.estructura="ArbolBinarioOrdenado";
                break;
            case "ArbolBinarioCompleto":
                this.estructura="ArbolBinarioCompleto";
                break;
            case "Pila":
                this.estructura="Pila";
                break;
            case "Cola":
                this.estructura="Cola";
                break;
            case "Lista":
                this.estructura="Lista";
                break;
            case "Arreglo":
                this.estructura="Arreglo";
                break;
            case "Grafica":
                this.estructura="Grafica";
                break;
        }
    }

    public String getEstructura(){
        return this.estructura;
    }

    public void setEstructura(String dato){
        this.estructura = dato;
    }
    
}
