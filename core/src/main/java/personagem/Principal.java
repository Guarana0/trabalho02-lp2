package personagem;

import com.badlogic.gdx.audio.Sound;

public class Principal extends Personagem {
    // variaveis para verificar a mudança de state ao longo do jogo
    private boolean apertouD, apertouS, apertouA, apertouEsp;
    private boolean clickDir, clickEsq;

    private int vida = 3;

    public Principal(float x, float y, float altura, float largura, Sound somDano) {
        super(x, y, altura, largura, somDano);
    }
}