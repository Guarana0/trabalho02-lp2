package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public abstract class Inimigo extends Personagem {
    protected int vida = 1;

    public Inimigo(float larguraMapa, float alturaMapa, float altura, float largura, Sound somDano) {
        super(
            // geração aleatoria da psoição do inimigo consulta no StackOverflow: Enemy spawning problem [closed]
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getWidth(), larguraMapa) - largura)),
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getHeight(), alturaMapa) - altura)),
            altura,
            largura,
            somDano
        );
    }
}

