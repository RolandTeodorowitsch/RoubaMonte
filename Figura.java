/**
 * Enumeracao para as figuras de uma carta de baralho.
 *  *
 * @author Roland Teodorowitsch
 * @version 5 jun. 2019
 */
public enum Figura {
    AS("A",1),
    DOIS("2",2),
    TRES("3",3),
    QUATRO("4",4),
    CINCO("5",5),
    SEIS("6",6),
    SETE("7",7),
    OITO("8",8),
    NOVE("9",9),
    DEZ("10",10),
    VALETE("J",11),
    DAMA("Q",12),
    REI("K",13),
    CURINGA("☺",20);
    
    private String rotulo;
    private int valor;
    
    /**
     * Construtor para a enumera&ccedil;&atilde;o Figura.
     * @param r Cadeia de caracteres com o r&oacute;tulo para a figura.
     * @param v Valor (n&uacute;mero de pontos) geralmente atribu&iacute;
     * &agrave; figura.
     */
    Figura(String r,int v) {
        rotulo = r;
        valor = v;
    }
    
    /**
     * M&eacute;todo para obter o valor padr&atilde;o de uma figura.
     * @return N&uacute;mero inteiro correspondente ao valor da carta.
     */
    public int obtemValor() {
        return valor;
    }
    
    /**
     * M&eacute;todo para obter a cadeia de caracteres com uma
     * representa&ccedil;&atilde;o da figura.
     * @return Cadeia de caracteres com uma representa&ccedil;&atilde;o
     * da figura.
     */
    public String obtemRotulo() {
        return rotulo;
    }
}
