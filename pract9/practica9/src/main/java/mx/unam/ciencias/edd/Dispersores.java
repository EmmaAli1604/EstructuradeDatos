package mx.unam.ciencias.edd;

/**
 * Clase para métodos estáticos con dispersores de bytes.
 */
public class Dispersores {

    /* Constructor privado para evitar instanciación. */
    private Dispersores() {}

    private static int combina(byte a, byte b, byte c, byte d){
        return ((a & 0xFF) << 24) | ((b & 0xFF) << 16) | ((c & 0xFF) << 8) | ((d & 0xFF)) ;
    }

    private static int desplazar(byte[] llave, int i){
        if (i < llave.length)
            return (llave[i] & 0xFF);
        return 0;
    }

    /**
     * Función de dispersión XOR.
     * @param llave la llave a dispersar.
     * @return la dispersión de XOR de la llave.
     */
    public static int dispersaXOR(byte[] llave) {
        int r = 0,i=0,l=llave.length;
        while(l>=4){
            r^=combina(llave[i], llave[i+1], llave[i+2], llave[i+3]);
            i+=4;
            l-=4;
        }

        int n=0;

        switch(l){
            case 3: n |= desplazar(llave, i+2) << 8;
            case 2: n |= desplazar(llave, i+1) << 16;
            case 1: n |= desplazar(llave, i) << 24;
        }

        r = r^n;

        return r;
    }

    /**
     * Función de dispersión de Bob Jenkins.
     * @param llave la llave a dispersar.
     * @return la dispersión de Bob Jenkins de la llave.
     */
    public static int dispersaBJ(byte[] llave) {
       int a = 0x9E3779B9;
       int b = 0x9E3779B9;
       int c = 0xFFFFFFFF;

        int i=0;
        int length = llave.length;

        while(length >= 12){
            a+= (llave[i] & 0xFF) + ((llave[i+1] & 0xFF) << 8) + ((llave[i+2] & 0xFF) << 16) + ((llave[i+3] & 0xFF) << 24);
            b += (llave[i+4] & 0xFF) + ((llave[i+5] & 0xFF) << 8) + ((llave[i+6] & 0xFF) << 16) + ((llave[i+7] & 0xFF) << 24);
            c += (llave[i+8] & 0xFF) + ((llave[i+9] & 0xFF) << 8) + ((llave[i+10] & 0xFF) << 16) + ((llave[i+11] & 0xFF) << 24);
            
            i+=12;
            length-=12;
            
            a -= b; a -= c; a ^= (c >>> 13);
            b -= c; b -= a; b ^= (a << 8);
            c -= a; c -= b; c ^= (b >>> 13);
            
            a -= b; a -= c; a ^= (c >>> 12);
            b -= c; b -= a; b ^= (a << 16);
            c -= a; c -= b; c ^= (b >>> 5);
            
            a -= b; a -= c; a ^= (c >>> 3);
            b -= c; b -= a; b ^= (a << 10);
            c -= a; c -= b; c ^= (b >>> 15);
            
        }

        switch(length){
            case 11:
                c += desplazar(llave, i+10) << 24;
            case 10:
                c += desplazar(llave, i+9) << 16;
            case 9:
                c+= desplazar(llave, i+8) << 8;
            case 8:
                b += desplazar(llave, i+7) << 24;
            case 7:
                b += desplazar(llave, i+6) << 16;
            case 6:
                b += desplazar(llave, i+5) << 8;
            case 5:
                b += desplazar(llave, i+4);
            case 4:
                a += desplazar(llave, i+3) << 24;
            case 3:
                a += desplazar(llave, i+2) << 16;
            case 2:
                a += desplazar(llave, i+1) << 8;
            case 1:
                a += desplazar(llave, i);
        }

        c+=llave.length;

        a -= b; a -= c; a ^= (c >>> 13);
        b -= c; b -= a; b ^= (a << 8);
        c -= a; c -= b; c ^= (b >>> 13);
        
        a -= b; a -= c; a ^= (c >>> 12);
        b -= c; b -= a; b ^= (a << 16);
        c -= a; c -= b; c ^= (b >>> 5);
        
        a -= b; a -= c; a ^= (c >>> 3);
        b -= c; b -= a; b ^= (a << 10);
        c -= a; c -= b; c ^= (b >>> 15);

        return c;
    }

    /**
     * Función de dispersión Daniel J. Bernstein.
     * @param llave la llave a dispersar.
     * @return la dispersión de Daniel Bernstein de la llave.
     */
    public static int dispersaDJB(byte[] llave) {
        int h = 5381;
        for(int i = 0 ; i<llave.length; i++){
            h += (h<<5) + desplazar(llave, i); 
        }
        return h ;
    }
}
