/**
 * Classe principal para iniciar um jogo de cartas Rouba Monte.
 * 
 * @author Roland Teodorowitsch
 * @version 12 jun. 2019
 */
public class Main {
    
    public static void main(String[] args) {
        Jogador[] jogadoresPartida1 = {
            new Jogador("COMPUTADOR1",TipoJogador.COMPUTADOR),
            new Jogador("COMPUTADOR2",TipoJogador.COMPUTADOR),
            new Jogador("COMPUTADOR3",TipoJogador.COMPUTADOR),
            new Jogador("COMPUTADOR4",TipoJogador.COMPUTADOR)
        };
        RoubaMonte partida1 = new RoubaMonte(jogadoresPartida1);
        partida1.jogar();

        Jogador[] jogadoresPartida2 = {
            new Jogador("VOCE",TipoJogador.USUARIO),
            new Jogador("COMPUTADOR",TipoJogador.COMPUTADOR),
        };
        RoubaMonte partida2 = new RoubaMonte(jogadoresPartida2);
        partida2.jogar();
    }
    
}