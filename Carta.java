/**
 * Classe para armazenar as informa&ccedil;&otilde;oes de
 * uma carta de um jogo de cartas.
 *
 * @author Roland Teodorowitsch
 * @version 5 jun. 2019
 */
public class Carta {
    private final Naipe naipe;
    private final Figura figura;
    private boolean visivel;
    private int valor;

    /**
     * Construtor completo para a classe Carta.
     * @param f Figura da carta (<code>Figura.AS</code>, <code>Figura.UM</code>,etc.).
     * @param n Naipe da carta (<code>Naipe.OUROS</code>, <code>Naipe.PAUS</code>, etc.).
     * @param v Valor <code>boolean</code>: <code>true</code> indica que
     * a carta est&aacute; virada para cima; <code>false</code> indica que
     * a carta est&aacute; virada para baixo.
     * @param vl Valor (n&uacute;mero de pontos) da carta, geralmente usada
     * para c&aacute;culo de pontua&ccedil;&atilde;o no jogo de cartas.
     */
    public Carta(Figura f, Naipe n, boolean v, int vl) {
        figura = f;
        naipe = n;
        visivel = v;
        valor = vl;
    }
  
    /**
     * Construtor m&iacute;nimo para a classe Carta.
     * @param f Figura da carta (<code>Figura.AS</code>, <code>Figura.UM</code>,etc.).
     * @param n Naipe da carta (<code>Naipe.OUROS</code>, <code>Naipe.PAUS</code>, etc.).
     */
    public Carta(Figura f, Naipe n) {
        figura = f;
        naipe = n;
        visivel = false;
        valor = figura.obtemValor();
    }

    /**
     * M&eacute;todo para obter o naipe de uma carta.
     * @return Naipe da carta (<code>Naipe.OUROS</code>, <code>Naipe.PAUS</code>, etc.).
     */
    public Naipe obtemNaipe() {
        return naipe;
    }
    
    /**
     * M&eacute;todo para obter a figura de uma carta.
     * @return Figura da carta (<code>Figura.AS</code>, <code>Figura.UM</code>,etc.).
     */
    public Figura obtemFigura() {
        return figura;
    }
    
    /**
     * M&eacute;todo para verificar se uma carta est&aacute; vis&iacute;vel,
     * ou seja, virada para cima ou n&atilde;o.
     * @return <code>true</code> se a carta est&aacute; vis&iacute;vel, ou
     * <code>false</code> em caso contr&aacute;rio.
     */
    public boolean estaVisivel() {
        return visivel;
    }
    
    /**
     * M&eacute;todo para obter o valor de uma carta.
     * @return N&uacute;mero inteiro correspondente ao valor da carta.
     */
    public int obtemValor() {
        return valor;
    }
    
    /**
     * Inverte o estado (vis&iacute;vel ou n&atilde;o) de uma carta.
     */
    public void vira() {
        visivel = !visivel;
    }
    
    /**
     * Redefine o valor de uma carta.
     * @param v N&uacute;mero inteiro correspondente ao valor da carta.
     */
    public void defineValor(int v) {
        valor = v;
    }
    
    /**
     * Gera uma representa&ccedil;&atilde;o da carta no formato texto.
     * @return Cadeira de caracteres com uma representa&ccedil;&atilde;o
     * da carta no formato texto. Apenas o valor da carta n&atilde;o
     * &eacute; mostrado.
     */
    public String toString() {
        if (visivel)
            return "["+figura.obtemRotulo()+naipe.obtemRotulo()+"]";
        return "[X]";
    }
    
}