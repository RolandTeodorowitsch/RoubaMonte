import java.util.Scanner;

/**
 * Classe para criar e executar jogo de cartas Rouba Monte.
 * 
 * As regras usadas na implementa&ccedil;&atilde;o s&atilde;o basicamente 
 * 
 * @author Roland Teodorowitsch
 * @version 12 jun. 2019
 */
public class RoubaMonte {
    
    private Baralho compras;
    private Baralho mesa;
    private Baralho[] maoJogador;
    private Baralho[] monteJogador;
    private Jogador[] jogadores;
    private int numJogadores;
    
    public RoubaMonte(Jogador[] jog) throws IllegalArgumentException {
        int nJ = jog.length;
        if (nJ < 2 || nJ > 4)
            throw new IllegalArgumentException("Número inválido de jogadores.");
        jogadores = jog;
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

    private void repoemCartas(Baralho baralho, int numCartas) {
        for (int i=0;i<numCartas;++i) {
            Carta carta = compras.remove();
            if (carta != null) {
                carta.vira();
                baralho.insere(carta);
            }
        }                    
    }
    
    private void mostraMesa(int jogadorDaVez) {
        System.out.println("========================================================================");
        int numCartasCompras = compras.obtemNumCartas();
        if (numCartasCompras == 0)
            System.out.printf("Compras (0): -\n");
        else
            System.out.printf("Compras (%d): %s\n",compras.obtemNumCartas(),compras.topo().toString());
        System.out.printf("Mesa (%d): %s\n",
                           mesa.obtemNumCartas(),
                           mesa.toString() );
        for (int i=0;i<numJogadores;++i) {
            System.out.println("------------------------------------------------------------------------");
            System.out.printf("Jogador %s (%d):\n",jogadores[i].obtemNome(),i);
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
        System.out.println("========================================================================");
    }

    private int qualMontePodeSerRoubado(int jogador, int carta) {
        Carta cartaMao = maoJogador[jogador].posicao(carta);
        for (int j=0; j<numJogadores; ++j) {
            if (j != jogador) {
                Carta cartaMonte = monteJogador[j].topo();
                if (cartaMao!=null && cartaMonte!=null &&
                    cartaMao.obtemFigura()==cartaMonte.obtemFigura())
                    return j;
            }
        }
        return -1;
    }
    
    private boolean roubaMonte(int jogador, int selecao1, int selecao2) {
        if (selecao1 < 0 || selecao1 >= maoJogador[jogador].obtemNumCartas())
            return false;
        if (selecao2 < 0 || selecao2 >= numJogadores || jogador == selecao2)
            return false;
        Carta cartaMao = maoJogador[jogador].posicao(selecao1);
        Carta cartaMonte = monteJogador[selecao2].topo();
        if (cartaMao==null || cartaMonte==null || cartaMao.obtemFigura()!=cartaMonte.obtemFigura())
            return false;
        Carta c;
        while (monteJogador[selecao2].obtemNumCartas()>0) {
            c = monteJogador[selecao2].remove(0);
            monteJogador[jogador].insere(c);
        }
        c = maoJogador[jogador].remove(selecao1);
        monteJogador[jogador].insere(c);
        return true;
    }
    
    private int qualCartaMesaPodeSerRoubada(int jogador, int carta) {
        Carta cartaMao = maoJogador[jogador].posicao(carta);
        for (int i=0; i<mesa.obtemNumCartas(); ++i) {
            Carta cartaMesa = mesa.posicao(i);
            if (cartaMao!=null && cartaMesa!=null &&
                cartaMao.obtemFigura()==cartaMesa.obtemFigura())
                return i;
        }
        return -1;
    }
    
    private boolean roubaMesa(int jogador, int selecao1, int selecao2) {
        if (selecao1 < 0 || selecao1 >= maoJogador[jogador].obtemNumCartas())
            return false;
        if (selecao2 < 0 || selecao2 >= mesa.obtemNumCartas())
            return false;
        Carta cartaMao = maoJogador[jogador].posicao(selecao1);
        Carta cartaMesa = mesa.posicao(selecao2);
        if (cartaMao==null || cartaMesa==null || cartaMao.obtemFigura()!=cartaMesa.obtemFigura())
            return false;
        Carta carta = maoJogador[jogador].remove(selecao1);
        monteJogador[jogador].insere(carta);
        carta = mesa.remove(selecao2);
        monteJogador[jogador].insere(carta);
        repoemCartas(maoJogador[jogador],1);
        int numCartasMesa = mesa.obtemNumCartas();
        if (numCartasMesa < 8)
            repoemCartas(mesa,8-numCartasMesa);
        return true;
    }
    
    private boolean descarta(int jogador, int selecao) {
        if (selecao < 0 || selecao >= maoJogador[jogador].obtemNumCartas())
            return false;
        Carta carta = maoJogador[jogador].remove(selecao);
        mesa.insere(carta);
        repoemCartas(maoJogador[jogador],1);
        return true;
    }

    private boolean jogadaAutomatica(int jogadorDaVez) {
        // Avalia cartas do Jogador:
        // (1) para roubar monte...
        int cartaParaRoubarMonte = -1;
        int monteParaRoubar = -1;
        for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
            int monte = qualMontePodeSerRoubado(jogadorDaVez,cm);
            if (monte != -1) {
                cartaParaRoubarMonte = cm;
                monteParaRoubar = monte;
                break;
            }
        }
        if (cartaParaRoubarMonte != -1)
            return roubaMonte(jogadorDaVez,cartaParaRoubarMonte,monteParaRoubar);
        // (2) para roubar carta da mesa...
        int cartaParaRoubarMesa = -1;
        int cartaMesaParaRoubar = -1;
        for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
            int cartaMesa = qualCartaMesaPodeSerRoubada(jogadorDaVez,cm);
            if (cartaMesa != -1) {
                cartaParaRoubarMesa = cm;
                cartaMesaParaRoubar = cartaMesa;
                break;
            }
        }
        if (cartaParaRoubarMesa != -1)
            return roubaMesa(jogadorDaVez,cartaParaRoubarMesa,cartaMesaParaRoubar);
        // ... ou eh obrigado a descartar!
        return descarta(jogadorDaVez,0);
    }
    
    private boolean jogadaManual(int jogadorDaVez, Scanner in) {
        // Avalia cartas do Jogador:
        // (1) para roubar monte...
        boolean podeRoubarMonte = false;
        for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
            if (qualMontePodeSerRoubado(jogadorDaVez,cm) != -1) {
                podeRoubarMonte = true;
                break;
            }
        }
        // (2) para roubar carta da mesa...
        boolean podeRoubarCartaMesa = false;
        for (int cm=0; cm<maoJogador[jogadorDaVez].obtemNumCartas(); ++cm) {
            int cartaMesa = qualCartaMesaPodeSerRoubada(jogadorDaVez,cm);
            if (cartaMesa != -1) {
                podeRoubarCartaMesa = true;
                break;
            }
        }
        
        int selecao1, selecao2, limite;
        int jogada = 0;
        
        boolean respostaOk = false;
        while (!respostaOk) {
            if ( podeRoubarMonte )
                System.out.println("\n1 - ROUBA MONTE");
            if ( podeRoubarCartaMesa )
                System.out.println("2 - ROUBA CARTA DA MESA");
            System.out.println("3 - DESCARTA");
            System.out.println("4 - SAIR");
            System.out.print("JOGADA? ");
            jogada = in.nextInt();
            switch (jogada) {
                case 1: // ROUBA MONTE
                    if (maoJogador[jogadorDaVez].obtemNumCartas()==1)
                        selecao1 = 0;
                    else {
                        limite = maoJogador[jogadorDaVez].obtemNumCartas()-1;
                        System.out.printf("Qual carta da sua mao [0;%d]? ", limite);
                        selecao1 = in.nextInt();
                    }
                    if (numJogadores==2)
                        selecao2 = 1 - jogadorDaVez;
                    else {
                        limite = numJogadores-1;
                        System.out.printf("Monde de qual jogador [0;%d]? ", limite);
                        selecao2 = in.nextInt();
                    }
                    respostaOk = roubaMonte(jogadorDaVez,selecao1,selecao2);
                    break;
                case 2: // ROUBA DA MESA
                    if (maoJogador[jogadorDaVez].obtemNumCartas()==1)
                        selecao1 = 0;
                    else {
                        limite = maoJogador[jogadorDaVez].obtemNumCartas()-1;
                        System.out.printf("Qual carta da sua mao [0;%d]? ", limite);
                        selecao1 = in.nextInt();
                    }
                    if (mesa.obtemNumCartas()==1)
                        selecao2 = 0;
                    else {
                        limite = mesa.obtemNumCartas()-1;
                        System.out.printf("Qual carta da mesa [0;%d]? ", limite);
                        selecao2 = in.nextInt();
                    }
                    respostaOk = roubaMesa(jogadorDaVez,selecao1,selecao2);
                    break;
                case 3: // DESCARTA
                    if (maoJogador[jogadorDaVez].obtemNumCartas()==1)
                        selecao1 = 0;
                    else {
                        limite = maoJogador[jogadorDaVez].obtemNumCartas()-1;
                        System.out.printf("Qual carta da sua mao [0;%d]? ", limite);
                        selecao1 = in.nextInt();
                    }
                    respostaOk = descarta(jogadorDaVez,selecao1);
                    break;
                case 4: // SAIR
                    respostaOk = true;
                    break;
                default:
            } // fim switch (jogada)
                
        } // fim while (!respostaOk)
        
        if (jogada == 4)
            return false;
        return true;
    }
    
    public void jogar() {
        Scanner in = new Scanner(System.in);
        int jogadorDaVez = 0;
        
        boolean continuar = true;
        while (continuar) {
            
            mostraMesa(jogadorDaVez);
            
            if (jogadores[jogadorDaVez].obtemTipo() == TipoJogador.COMPUTADOR)
                continuar = jogadaAutomatica(jogadorDaVez);
            else
                continuar = jogadaManual(jogadorDaVez,in);
            
            mostraMesa(jogadorDaVez);
            for (int i = 0; i<10; ++i)
                System.out.println();
                
            ++jogadorDaVez;
            if (jogadorDaVez >= numJogadores)
                jogadorDaVez = 0;
                
            if (maoJogador[jogadorDaVez].obtemNumCartas() == 0) {
                // FIM DO JOGO: determina vencedor
                int maiorMonte = monteJogador[0].obtemNumCartas();
                for (int i=1; i<numJogadores; ++i) {
                    int monte = monteJogador[i].obtemNumCartas();
                    if (monte > maiorMonte)
                       maiorMonte = monte;
                }
                System.out.println("Vencedor(es):");
                for (int i=0; i<numJogadores; ++i) {
                    if (monteJogador[i].obtemNumCartas() == maiorMonte)
                        System.out.printf("%s (%d)\n", jogadores[i].obtemNome(),maiorMonte);
                }
                continuar = false;
            }
        
        } // fim while (!fim)
        
        System.out.println("\n---- PARTIDA ENCERRADA ----");
    }
}