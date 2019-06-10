import java.util.Scanner;

public class RoubaMonte {
    
    static Baralho compras;
    static Baralho mesa;
    static String[] nomeJogador;
    static Baralho[] maoJogador;
    static Baralho[] monteJogador;
    static int numJogadores;

    public static void mostraMesa(int jogadorDaVez) {
        System.out.printf("Compras (%d): ...\n",compras.obtemNumCartas());
        System.out.printf("Mesa (%d): %s\n",
                           mesa.obtemNumCartas(),
                           mesa.toString() );
        for (int i=0;i<numJogadores;++i) {
            System.out.printf("Jogador %d:\n",i);
            if (i == jogadorDaVez)
                System.out.printf("- Mao (%d): %s\n",maoJogador[i].obtemNumCartas(),maoJogador[i].toString() );
            else
                System.out.printf("- Mao (%d): ...\n",maoJogador[i].obtemNumCartas() );
            System.out.printf("- Monte (%d): %s\n",monteJogador[i].obtemNumCartas(),monteJogador[i].toString() );
        }
    }
    
    public static void main(String[] args) {
        int jogadorDaVez;
        Carta c;
        Scanner in = new Scanner(System.in);
        System.out.print("Numero de jogadores: ");
        numJogadores = in.nextInt();
        if (numJogadores<2 || numJogadores>4) {
            System.err.println("Numero invalido de jogadores...");
            System.exit(1);
        }
        nomeJogador = new String[numJogadores];
        maoJogador = new Baralho[numJogadores];
        monteJogador = new Baralho[numJogadores];
        
        compras = new Baralho(false);
        compras.embaralha();
        mesa = new Baralho();
        for (int i=0;i<numJogadores;++i) {
            System.out.printf("Nome do jogador 1: ");
            nomeJogador[i] = in.next();
            maoJogador[i] = new Baralho();
            monteJogador[i] = new Baralho();
        }
        // MESA
        for (int i=0;i<8;++i) {
            c = compras.remove();
            c.vira();
            mesa.insere(c);
        }
        // CARTAS DOS JOGADORES
        for (int i=0;i<4;++i) {
            for (int j=0;j<numJogadores;++j) {
                c = compras.remove();
                c.vira();
                maoJogador[j].insere(c);
            }
        }

        jogadorDaVez = 0;
        mostraMesa(jogadorDaVez);
        
        System.out.println("\n1 - ROUBA MONTE");
        System.out.println("2 - ROUBA CARTA DA MESA");
        System.out.println("3 - DESCARTA");
        System.out.print("JOGADA? ");
        int jogada = in.nextInt();
        switch(jogada) {
            case 1:
            case 2:
            case 3: // DESCARTA
            default:
                 if (maoJogador[jogadorDaVez].obtemNumCartas()==1) {
                    c = maoJogador[jogadorDaVez].remove();
                    mesa.insere(c);
                    for (int i=0;i<4;++i) {
                        c = compras.remove();
                        if (c != null) {
                            c.vira();
                            maoJogador[jogadorDaVez].insere(c);
                        }
                    }                    
                }
                else {
                    System.out.printf("Qual carta (0-%d)? ",maoJogador[jogadorDaVez].obtemNumCartas()-1);
                    int indCarta = in.nextInt();
                    if (indCarta<0 || indCarta>=maoJogador[jogadorDaVez].obtemNumCartas())
                       indCarta = 0;
                    c = maoJogador[jogadorDaVez].remove(indCarta);
                    mesa.insere(c);
                }
        }
        mostraMesa(jogadorDaVez);
    }
}