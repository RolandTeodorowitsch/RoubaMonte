import java.util.Scanner;

public class RoubaMonte {
    
    private Baralho compras;
    private Baralho mesa;
    private Baralho[] maoJogador;
    private Baralho[] monteJogador;
    private int numJogadores;
    
    public RoubaMonte(int nJ) {
        Carta c;
        numJogadores = nJ;
        compras = new Baralho(false);
        mesa = new Baralho();
        maoJogador = new Baralho[numJogadores];
        monteJogador = new Baralho[numJogadores];
        compras.embaralha();
        for (int i=0;i<numJogadores;++i) {
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
    }

    private void mostraMesa(int jogadorDaVez) {
        System.out.printf("Compras (%d): %s\n",compras.obtemNumCartas(),compras.topo().toString());
        System.out.printf("Mesa (%d): %s\n",
                           mesa.obtemNumCartas(),
                           mesa.toString() );
        for (int i=0;i<numJogadores;++i) {
            System.out.printf("Jogador %d:\n",i);
            if (i == jogadorDaVez)
                System.out.printf("- Mao (%d): %s\n",maoJogador[i].obtemNumCartas(),maoJogador[i].toString() );
            else
                System.out.printf("- Mao (%d): ...\n",maoJogador[i].obtemNumCartas() );
            int nc = monteJogador[i].obtemNumCartas();
            System.out.printf("- Monte (%d): ",nc);
            if ( nc == 0 )
               System.out.println("-");
            else
               System.out.printf("%s\n",monteJogador[i].topo().toString());
        }
    }
    
    public void jogar() {
        int jogadorDaVez;
        Carta c;
        Scanner in = new Scanner(System.in);


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