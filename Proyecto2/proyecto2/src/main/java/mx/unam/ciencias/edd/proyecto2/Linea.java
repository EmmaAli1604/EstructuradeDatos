package mx.unam.ciencias.edd.proyecto2;

public class Linea {
    
    private String linea;

    public Linea(String linea) {
        this.linea = eliminaChar(linea);
    }

    public String get() {
        return linea;
    }

    public String eliminaChar(String linea) {
        String s = linea.trim();
        s = s.replaceAll(" +", " ");
        s = s.replaceAll("\n", " ");
        return s;
    }

    @Override
    public String toString() {
        String s = linea + "\n";
        return s;
    }

}
