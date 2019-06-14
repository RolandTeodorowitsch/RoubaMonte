/**
 * Classe principal para iniciar um jogo de cartas Rouba Monte.
 * 
 * @author Roland Teodorowitsch
 * @version 12 jun. 2019
 */
public class Main {
    
    public static void main(String[] args) {
        
        Jogador[] jogadores = {
            new Jogador("PROFESSOR",TipoJogador.USUARIO),
            new Jogador("COMPUTADOR",TipoJogador.COMPUTADOR),
            new Jogador("ALUNO",TipoJogador.USUARIO)
        };
        
        RoubaMonte roubamonte = new RoubaMonte(jogadores);
        roubamonte.jogar();
    }
    
}