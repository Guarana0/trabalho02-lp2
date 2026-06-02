package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private final float FORCA_JETPACK = 1000f;// forca que o jatpack faz pra cima quando e ativado

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

    /*
     * esse metodo que eu criei vai calcular a fisica baseado no tempo, para nao
     * epender do
     * fps da maquina do usuarioe rodar de maneira fluida em qualquer aparelhpo
     */
    public void atualizar(float deltaTempo) {
        // Verifica se a barra de ESPACO tá apertada ou se o usuario clicou na tela
        apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();
        if (apertouEsp) {
            // Se o Jetpack está ligado eu somo a força de subida e a gravidade
            velocidadeY += (GRAVIDADE + FORCA_JETPACK) * deltaTempo;
        } else {
            // Se o cara soltou o botão só a gravidade puxa o boneco para baixo
            velocidadeY += GRAVIDADE * deltaTempo;
        }

        // isso aq garante q a elociodade de subida na vai passar da velocidade maxima
        if (velocidadeY > VELOCIDADE_MAIXIMA) {
            velocidadeY = VELOCIDADE_MAIXIMA;
        }
        if (velocidadeY < -VELOCIDADE_MAIXIMA) {
            // isso aq garante q o personagem nao caia em supervelocidade
            // se a velociodade da queda passa do limite negativo ela trava na vel de queda
            // maxima
            velocidadeY = -VELOCIDADE_MAIXIMA;
        }
        // aqui usei a formula da posicao de fisica para fazer os calculos de posicao em
        // cada eixo
        float novoY = posicao.y + (velocidadeY * deltaTempo);

        float novoX = posicao.x + (VELOCIDADE_CORRIDA * deltaTempo);

    }

}