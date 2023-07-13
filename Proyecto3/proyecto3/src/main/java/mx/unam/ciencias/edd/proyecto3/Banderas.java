package mx.unam.ciencias.edd.proyecto3;
/*
 * Clase para banderas
 */
public class Banderas {
    /*Representación en cadena de la bandera */
    private String flag;
    /*Indica si la bandera se encuentra o no */
    private boolean Bflag = false;

    /*Constructor que recibe la cadena de la bandera y si se encuentra
     * @param la cadena de la bandera
     * @param el booleano de la bandera 
     */
    public Banderas(String flag, boolean Bflag){
        this.flag=flag;
        this.Bflag=Bflag;
    }

    /*Regresa la cadena de la bandera
     * @return la representacion en cadena de una bandera
     */
    public String getFlag() {
        return this.flag;
    }

    /*Regresa la cadena de la bandera
     * @return el valor booleano de la bandera
     */
    public boolean getBflag(){
        return this.Bflag;
    }

    /*
     * Establece un valor booleano para Bflag
     */
    public void setBflag(boolean bflag) {
        Bflag = bflag;
    }

}
