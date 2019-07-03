/**
 * Enumera&ccedil;&atilde;o para os naipes de uma carta de baralho.
 * 
 * @author Roland Teodorowitsch
 * @version 5 jun. 2019
 */
public enum Naipe {
    OUROS("♦"), PAUS("♣"), COPAS("♥"), ESPADAS("♠"), NENHUM("");
    
    private String rotulo;
    
    /**
     * Construtor para a enumera&ccedil;&atilde;o Naipe.
     * @param r Cadeia de caracteres com o r&oacute;tulo para o naipe.
     */
    Naipe(String r) {
        rotulo = r;
    }
    
    /**
     * M&eacute;todo para obter a cadeia de caracteres com uma
     * representa&ccedil;&atilde;o do naipe.
     * @return Cadeia de caracteres com uma representa&ccedil;&atilde;o
     * do naipe.
     */
    public String obtemRotulo() {
        return rotulo;
    }
}
