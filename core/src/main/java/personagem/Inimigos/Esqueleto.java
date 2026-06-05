package personagem.Inimigos;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import personagem.Inimigo;

public class Esqueleto extends Inimigo {

    public Esqueleto(float larguraMapa, float alturaMapa, float altura, float largura, Sound somDano) {
        super(larguraMapa, alturaMapa, altura, largura, somDano);
    }

    public void renderizar(ShapeRenderer shape) {
        shape.setColor(Color.RED);

        shape.rect(posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }
}

