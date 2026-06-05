package personagem.Inimigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import personagem.Inimigo;

public class Corvo extends Inimigo {
    private final float GRAVIDADE = -500f;
    private float velocidadeX = 0f;

    private final float ALTURA_CHAO = 50f;
    private final float ALTURA_TETO = 500f;
    private final float VELOCIDADE_VOO = 10f;

    public Corvo(float larguraMapa, float alturaMapa, float altura, float largura, Sound somDano) {
        super(larguraMapa, alturaMapa, altura, largura, somDano);
    }

    public void renderizar(ShapeRenderer shape) {
        shape.setColor(Color.YELLOW);

        shape.rect(posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

    public void atualizar(float deltaTempo) {

    }
}
