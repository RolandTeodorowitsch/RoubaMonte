public class Jogador {
    private String nome;
    private TipoJogador tipo;
    
    public Jogador(String n, TipoJogador t) {
        nome = n;
        tipo = t;
    }
    
    public String obtemNome() {
        return nome;
    }
    
    public TipoJogador obtemTipo() {
        return tipo;
    }
    
}