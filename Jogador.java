/**
 * Classe para armazenar as informa&ccedil;&otilde;oes de
 * um jogador.
 *
 * @author Roland Teodorowitsch
 * @version 29 jun. 2019
 */
public class Jogador {
    private String nome;
    private TipoJogador tipo;
    
    /**
     * Construtor completo para a classe Jogador.
     * @param n Nome do jogador.
     * @param t Tipo do jogador (<code>TipoJogador.USUARIO</code> ou
     * <code>TipoJogador.COMPUTADOR</code>,etc.).
     */
    public Jogador(String n, TipoJogador t) {
        nome = n;
        tipo = t;
    }
    
    /**
     * M&eacute;todo para obter o nome do jogador.
     * @return Cadeia de caracteres com o nome do jogador.
     */
    public String obtemNome() {
        return nome;
    }
    
    /**
     * M&eacute;todo para obter o tipo do jogador.
     * @return Tipo do jogador (<code>TipoJogador.USUARIO</code> ou
     * <code>TipoJogador.COMPUTADOR</code>,etc.).
     */
    public TipoJogador obtemTipo() {
        return tipo;
    }
    
}