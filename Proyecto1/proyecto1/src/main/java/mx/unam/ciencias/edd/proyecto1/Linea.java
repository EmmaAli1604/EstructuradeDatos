package mx.unam.ciencias.edd.proyecto1;

import java.text.Normalizer;

public class Linea implements Comparable<Linea> {

    private String linea;

    public Linea(String linea) {
        this.linea = linea;
    }

    @Override
    public int compareTo(Linea l) {
        String aux = eliminaChar(linea.trim());
        String aux2 = eliminaChar(l.get().trim());
        return aux.compareTo(aux2);
    }

    public String get() {
        return linea;
    }

    public String eliminaChar(String linea) {
        String s = linea.toLowerCase();
        String g = Normalizer.normalize(s, Normalizer.Form.NFD);
        g = g.replaceAll("[^a-zA-Z]", "");
        return g;
    }

    @Override
    public String toString() {
        String s = linea + "\n";
        return s;
    }
}
