package personagem;

import com.badlogic.gdx.audio.Sound;

public class Principal extends Personagem {
    // variaveis para verificar a mudança de state ao longo do jogo
    private boolean apertouD, apertouS, apertouA, apertouEsp;
    private boolean clickDir, clickEsq;

    private int vida = 3;
    /*
     * coloqeui como final pois como os valores quevou declarar sao constante vai
     * facilitar agente e garantir
     * que nao os alteraremos por wngano ao longo do codigo
     */
    private final float GRAVIDADE = -500f;// o 500 eu botei como ilustrativo no mmomentop depois vou definir o valor
                                          // mais adequado
    private final float FORCA_JATPACK = 1000f;// forca que o jatpack faz pra cima quando e ativado

    private final float VELOCIDADE_MAIXIMA = 500f;// estabeli ese limite pra eviar que o voo ou queda fiquem muito
                                                  // acelerados

    private float velocidadeY = 0f;// issso aq e a velocidade vertical do personagem, como ele comeca parado ela
                                   // comeca e m zero

    // isso aq e pro personagem nao sumir no teto nek afundar no chao
    private final float ALTURA_CHAO = 50f;
    private final float ALTURA_TETO = 500f;

    // velocidade que o personagem corre em X
    private final float VELOCIDADE_CORRIDA = 205f;

    public Principal(float x, float y, float altura, float largura, Sound somDano) {
        super(x, y, altura, largura, somDano);
    }
}