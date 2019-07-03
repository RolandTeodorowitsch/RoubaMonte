import java.util.Random;

/**
 * Classe para armazenar as as cartas dos baralhos
 * (montes ou pilhas) de um jogo de cartas.
 *
 * @author Roland Teodorowitsch
 * @version 3 jul. 2019
 */
public class Baralho {
    
    // Este vetor estatico contem a representacao das cartas de um baralho
    private static Carta[] baralhoBase = {
        new Carta(Figura.AS,Naipe.COPAS),
        new Carta(Figura.DOIS,Naipe.COPAS),
        new Carta(Figura.TRES,Naipe.COPAS),
        new Carta(Figura.QUATRO,Naipe.COPAS),
        new Carta(Figura.CINCO,Naipe.COPAS),
        new Carta(Figura.SEIS,Naipe.COPAS),
        new Carta(Figura.SETE,Naipe.COPAS),
        new Carta(Figura.OITO,Naipe.COPAS),
        new Carta(Figura.NOVE,Naipe.COPAS),
        new Carta(Figura.DEZ,Naipe.COPAS),
        new Carta(Figura.VALETE,Naipe.COPAS),
        new Carta(Figura.DAMA,Naipe.COPAS),
        new Carta(Figura.REI,Naipe.COPAS),
        new Carta(Figura.AS,Naipe.OUROS),
        new Carta(Figura.DOIS,Naipe.OUROS),
        new Carta(Figura.TRES,Naipe.OUROS),
        new Carta(Figura.QUATRO,Naipe.OUROS),
        new Carta(Figura.CINCO,Naipe.OUROS),
        new Carta(Figura.SEIS,Naipe.OUROS),
        new Carta(Figura.SETE,Naipe.OUROS),
        new Carta(Figura.OITO,Naipe.OUROS),
        new Carta(Figura.NOVE,Naipe.OUROS),
        new Carta(Figura.DEZ,Naipe.OUROS),
        new Carta(Figura.VALETE,Naipe.OUROS),
        new Carta(Figura.DAMA,Naipe.OUROS),
        new Carta(Figura.REI,Naipe.OUROS),
        new Carta(Figura.AS,Naipe.ESPADAS),
        new Carta(Figura.DOIS,Naipe.ESPADAS),
        new Carta(Figura.TRES,Naipe.ESPADAS),
        new Carta(Figura.QUATRO,Naipe.ESPADAS),
        new Carta(Figura.CINCO,Naipe.ESPADAS),
        new Carta(Figura.SEIS,Naipe.ESPADAS),
        new Carta(Figura.SETE,Naipe.ESPADAS),
        new Carta(Figura.OITO,Naipe.ESPADAS),
        new Carta(Figura.NOVE,Naipe.ESPADAS),
        new Carta(Figura.DEZ,Naipe.ESPADAS),
        new Carta(Figura.VALETE,Naipe.ESPADAS),
        new Carta(Figura.DAMA,Naipe.ESPADAS),
        new Carta(Figura.REI,Naipe.ESPADAS),
        new Carta(Figura.AS,Naipe.PAUS),
        new Carta(Figura.DOIS,Naipe.PAUS),
        new Carta(Figura.TRES,Naipe.PAUS),
        new Carta(Figura.QUATRO,Naipe.PAUS),
        new Carta(Figura.CINCO,Naipe.PAUS),
        new Carta(Figura.SEIS,Naipe.PAUS),
        new Carta(Figura.SETE,Naipe.PAUS),
        new Carta(Figura.OITO,Naipe.PAUS),
        new Carta(Figura.NOVE,Naipe.PAUS),
        new Carta(Figura.DEZ,Naipe.PAUS),
        new Carta(Figura.VALETE,Naipe.PAUS),
        new Carta(Figura.DAMA,Naipe.PAUS),
        new Carta(Figura.REI,Naipe.PAUS),
        new Carta(Figura.CURINGA,Naipe.NENHUM),
        new Carta(Figura.CURINGA,Naipe.NENHUM)
    };
    // A variavel de instancia baralho eh um vetor parcialmente preenchido
    // que armazenara referencias para a estrutura baralhoBase
    private Carta[] baralho;
    // Variavel de instancia para armazenar o numero de cartas do baralho
    private int numCartas;

    /**
     * Construtor para um baralho de cartas vazio.
     */
    public Baralho() {
        baralho = new Carta[baralhoBase.length];
        numCartas = 0;
    }
    
    /**
     * Construtor para um baralho completo, com todas as cartas
     * (em duas versoes: com curingas e sem curingas).
     * @param comCuringas <code>boolean</code>: <code>true</code> indica que
     * o baralho cont&eacute;m dois curingas; <code>false</code> indica que
     * o baralho n&atilde;o tem curingas.
     */
    public Baralho(boolean comCuringas) {
        int numMaxCartas = baralhoBase.length;
        baralho = new Carta[numMaxCartas];
        if (!comCuringas)
           numMaxCartas = numMaxCartas - 2;
        for (int i=0;i<numMaxCartas;++i)
            baralho[i] = baralhoBase[i];
        numCartas = numMaxCartas;
    }

    /**
     * M&eacute;todo para obter o n&uacute;mero de cartas do baralho.
     * @return N&uacute;mero de cartas do baralho.
     */
    public int obtemNumCartas() {
        return numCartas;
    }
    
    /**
     * Gera uma representa&ccedil;&atilde;o de todas as cartas do baralho
     * no formato texto, usando o m&eacute;todo <code>toString()</code>
     * de cada carta.
     * @return Cadeira de caracteres com uma representa&ccedil;&atilde;o
     * das cartas do baralho no formato texto.
     */
    public String toString() {
        String res = "";
        for (int i=0;i<numCartas;++i)
            res += baralho[i].toString();
        return res;
    }
    
    /**
     * Embaralha as cartas.
     */
    public void embaralha() {
        Random r = new Random();
        for (int i=0;i<numCartas;++i) {
            int j = r.nextInt(numCartas);
            Carta aux = baralho[i];
            baralho[i] = baralho[j];
            baralho[j] = aux;
        }
        for (int i=0;i<1000;++i) {
            int j = r.nextInt(numCartas);
            int k = r.nextInt(numCartas);
            Carta aux = baralho[j];
            baralho[j] = baralho[k];
            baralho[k] = aux;
        }
    }
    
    /**
     * Insere uma carta no topo (ou seja, no final) do baralho.
     * @param c Carta a ser inserida no baralho.
     */
    public void insere(Carta c) {
        if (numCartas < baralho.length) {
            baralho[numCartas] = c;
            ++numCartas;
        }
    }
    
    /**
     * Retorna a carta do topo (ou seja, do final) do baralho, sem remov&ecirc;-la do baralho.
     * @return Carta do topo (ou seja, do final) do baralho, ou <code>null</code> em caso de erro.
     */
    public Carta topo() {
        if (numCartas <= 0)
            return null;
        return baralho[numCartas-1];
    }
    
    /**
     * Retorna a carta da posi&ccedil;&atilde;o especificada do baralho, sem remov&ecirc;-la do baralho.
     * @param indCarta &Iacute;ndice da carta do baralho que deve ser retornada.
     * @return Carta da posi&ccedil;&atilde;o especificada do baralho, ou <code>null</code> em caso de erro.
     */
    public Carta posicao(int indCarta) {
        if (indCarta < 0 || indCarta >= numCartas)
            return null;
        return baralho[indCarta];
    }
    
    /**
     * Remove a carta do topo (ou seja, do final) do baralho.
     * @return Carta removida do topo (ou seja, do final) do baralho, ou <code>null</code> em caso de erro.
     */
    public Carta remove() {
        if (numCartas <= 0)
            return null;
        numCartas--;
        return baralho[numCartas];
    }
    
    /**
     * Remove uma carta de uma posi&ccedil;&atilde;o espec&iacute;fica do baralho.
     * @param indCarta &Iacute;ndice da carta que deve ser removida do baralho e que ser&aacute; retornada.
     * @return Carta removida de uma posi&ccedil;&atilde;o espec&iacute;fica do baralho, ou <code>null</code> em caso de erro.
     */
    public Carta remove(int indCarta) {
        if (indCarta < 0 || indCarta >= numCartas)
            return null;
        Carta res = baralho[indCarta];
        for (int i=indCarta;i < numCartas-1; ++i)
            baralho[i] = baralho[i+1];
        numCartas--;
        return res;
    }
    
    /**
     * Transfere determinado n&uacute;mero de cartas de um baralho para o baralho corrente.
     * @param b Baralho do qual as cartas ser&atilde;o transferidas (compradas).
     * @param nC N&uacute;mero de cartas que ser&atilde;o transferidas (compradas).
     */
    public void compra(Baralho b, int nC) {
        for (int i=0;i<nC;++i) {
            Carta carta = b.remove();
            if (carta != null && numCartas < baralho.length) {
               baralho[numCartas] = carta;
               ++numCartas;
            }
        }                    
    }
    
}
