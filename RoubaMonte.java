import java.util.Scanner;

/**
 * Classe para criar e executar jogos de cartas Rouba Monte.
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

    /**
     * Construtor para a classe <code>RoubaMonte</code>.
     * 
     * Quando se cria um objeto desta classe, este m&eacute;todo inicializa todas as vari&aacute;veis de
     * inst&acirc;ncia necess&aacute;rias.
     * @param jog Vetor de objetos da classe <code>Jogador</code> que jogaram a partida.
     * @throws IllegalArgumentException &Eacute; lan&ccedil;ada quando o n&uacute;mero de jogadores &eacute; inv&aacute;lido.
     */
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
            mesa.insere(c);
        }
        // CARTAS DOS JOGADORES
        for (int i=0;i<4;++i) {
            for (int j=0;j<numJogadores;++j) {
                c = compras.remove();
                maoJogador[j].insere(c);
            }
        }
    }

    /**
     * Mostra a mesa (no terminal) do ponto de vista de determinado jogador.
     * @param jogadorDaVez Corresponde ao &iacute;ndice do jogador que deve jogar na rodada atual.
     */
    private void mostraMesa(int jogadorDaVez) {
        System.out.println("========================================================================");
        int numCartasCompras = compras.obtemNumCartas();
        if (numCartasCompras == 0)
            System.out.printf("Compras (0): -\n");
        else
            System.out.printf("Compras (%d): [X]\n",compras.obtemNumCartas());
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

    /**
     * Identifica qual monte de outro jogador pode ser roubado por determinado jogador com determinada carta de sua m&atilde;o.
     * @param jogador Corresponde ao &iacute;ndice do jogador em quest&atilde;o.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o.
     * @return &Iacute;ndice do primeiro jogador cujo monte pode ser roubado pelo jogador em quest&atilde;o com a carta especificada
     * ou -1, se n&atilde;o for poss&iacute;vel roubar nenhum monte.
     */
    private int qualMontePodeSerRoubado(int jogador, int c) {
        Carta cartaMao = maoJogador[jogador].posicao(c);
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
    
    /**
     * Realiza o roubo do monte de um jogador, usando determinada carta de um jogador.
     * @param jogador Corresponde ao &iacute;ndice do jogador que est&aatuce; tentando roubar um monte.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o.
     * @param m &Iacute;ndice do jogador ou monte que se quer roubar com a carta do jogador em quest&atilde;o.
     * @return <code>true</code> se estiver tudo certo e o roubo for conclu&iaculte;do com sucesso ou <code>false</code>
     * se houver algum erro.
     */
    private boolean roubaMonte(int jogador, int c, int m) {
        if (c < 0 || c >= maoJogador[jogador].obtemNumCartas())
            return false;
        if (m < 0 || m >= numJogadores || jogador == m)
            return false;
        Carta cartaMao = maoJogador[jogador].posicao(c);
        Carta cartaMonte = monteJogador[m].topo();
        if (cartaMao==null || cartaMonte==null || cartaMao.obtemFigura()!=cartaMonte.obtemFigura())
            return false;
        Carta carta;
        while (monteJogador[m].obtemNumCartas()>0) {
            carta = monteJogador[m].remove(0);
            monteJogador[jogador].insere(carta);
        }
        carta = maoJogador[jogador].remove(c);
        monteJogador[jogador].insere(carta);
        maoJogador[jogador].compra(compras,1);
        return true;
    }
    
    /**
     * Identifica qual carta da mesa pode ser roubada por determinado jogador com determinada carta de sua m&atilde;o.
     * @param jogador Corresponde ao &iacute;ndice do jogador em quest&atilde;o.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o.
     * @return &Iacute;ndice da carta da mesa que pode ser roubada pelo jogador em quest&atilde;o com a carta especificada
     * ou -1, se n&atilde;o for poss&iacute;vel roubar nenhuma carta.
     */
    private int qualCartaMesaPodeSerRoubada(int jogador, int c) {
        Carta cartaMao = maoJogador[jogador].posicao(c);
        for (int i=0; i<mesa.obtemNumCartas(); ++i) {
            Carta cartaMesa = mesa.posicao(i);
            if (cartaMao!=null && cartaMesa!=null &&
                cartaMao.obtemFigura()==cartaMesa.obtemFigura())
                return i;
        }
        return -1;
    }
    
    /**
     * Realiza o roubo de uma carta da mesa, usando determinada carta de um jogador.
     * @param jogador Corresponde ao &iacute;ndice do jogador que est&aatuce; tentando roubar uma carta da mesa.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o.
     * @param cMesa &Iacute;ndice da carta da mesa que se quer roubar com a carta do jogador em quest&atilde;o.
     * @return <code>true</code> se estiver tudo certo e o roubo for conclu&iaculte;do com sucesso ou <code>false</code>
     * se houver algum erro.
     */
    private boolean roubaMesa(int jogador, int c, int cMesa) {
        if (c < 0 || c >= maoJogador[jogador].obtemNumCartas())
            return false;
        if (cMesa < 0 || cMesa >= mesa.obtemNumCartas())
            return false;
        Carta cartaMao = maoJogador[jogador].posicao(c);
        Carta cartaMesa = mesa.posicao(cMesa);
        if (cartaMao==null || cartaMesa==null || cartaMao.obtemFigura()!=cartaMesa.obtemFigura())
            return false;
        Carta carta = maoJogador[jogador].remove(c);
        monteJogador[jogador].insere(carta);
        carta = mesa.remove(cMesa);
        monteJogador[jogador].insere(carta);
        maoJogador[jogador].compra(compras,1);
        int numCartasMesa = mesa.obtemNumCartas();
        if (numCartasMesa < 8)
            mesa.compra(compras,8-numCartasMesa);
        return true;
    }
    
    /**
     * Realiza o descarte (na mesa) da carta da m&atilde;o de um jogador.
     * @param jogador Corresponde ao &iacute;ndice do jogador que est&aatuce; realizando o descarte.
     * @param c &Iacute;ndice da carta da m&atilde;o do jogador em quest&atilde;o que se quer descartar.
     * @return <code>true</code> se estiver tudo certo e o roubo for conclu&iaculte;do com sucesso ou <code>false</code>
     * se houver algum erro.
     */
    private boolean descarta(int jogador, int c) {
        if (c < 0 || c >= maoJogador[jogador].obtemNumCartas())
            return false;
        Carta carta = maoJogador[jogador].remove(c);
        mesa.insere(carta);
        maoJogador[jogador].compra(compras,1);
        return true;
    }

    /**
     * Realiza a jogada automatizada para determinado jogador, que sempre ser&aacute; do tipo <code>TipoJogador.COMPUTADOR</code>.
     * Aqui usa-se uma l&oacute;gica relativamente simples: tentar roubar um monte; n&atilde;o sendo poss&iacutel;vel, tenta-se
     * roubar uma carta da mesa; n&atilde;o sendo poss&iacute;vel, descarta-se uma carta da m&atilde;o.
     * @param jogador Corresponde ao &iacute;ndice do jogador que deve jogar.
     * @return <code>true</code> se estiver tudo certo e a jogada tiver sido conclu&iaculte;da com sucesso ou <code>false</code>
     * se houver algum erro. A princ&iacute;pio este m&eacute;todo sempre retornar&aacute; <code>true</code>, pois o "computador"
     * n&atilde;o tentar&aacute; executar jogadas inv&aacute;lidas.
     */
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
    
    /**
     * Realiza uma jogada para determinado jogador, que sempre ser&aacute; do tipo <code>TipoJogador.USUARIO</code>, usando determinado
     * dispositivo de entrada.
     * Neste m&eacute;todo apresenta-se um menu que mostra apenas op&ccedil;&otilde;es que realmente podem ser selecionadas pelo
     * usu&aacute;rio, considerando-se as regras do jogo Rouba Monte.
     * @param jogador Corresponde ao &iacute;ndice do jogador que deve jogar.
     * @param in Dispositivo a partir do qual a entrada deve ser lida (tipo <code>Scanner</code>).
     * @return <code>true</code> se tiver conseguido realizar uma jogada v&aacute;lida ou <code>false</code> caso o jogador
     * tenha selecionado a op&ccedil;&atilde;o para encerrar o jogo.
     */
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
    
    /**
     * Desenvolve o jogo entre os jogadores especificados no construtor.
     * Este m&eacute;todo ser&aacute; encerrado quando a partida tiver terminado ou quando um jogador tiver selecionado
     * a op&ccedil;&atilde;o para encerrar o jogo.
     */
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