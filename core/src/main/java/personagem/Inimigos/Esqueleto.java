package personagem.Inimigos;

import br.com.lgalarane.trabalho02.GameAssets;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import personagem.Inimigo;

public class Esqueleto extends Inimigo {

    private final com.badlogic.gdx.graphics.g2d.Animation<TextureRegion> animCorrendo;
    private float stateTime = 0f;

    public Esqueleto(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);

        // Usa frames específicos do esqueleto carregados no GameAssets
        animCorrendo = new com.badlogic.gdx.graphics.g2d.Animation<>(
                0.1f,
                assets.framesCorrendoEsqueleto,
                com.badlogic.gdx.graphics.g2d.Animation.PlayMode.LOOP);
    }

    public void renderizar(SpriteBatch batch) {
        stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        TextureRegion frame = animCorrendo.getKeyFrame(stateTime, true);
        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

    public void renderizar(SpriteBatch batch, float xTela) {
        stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        TextureRegion frame = animCorrendo.getKeyFrame(stateTime, true);
        batch.draw(frame, xTela, posicao.y, dimensoes.x, dimensoes.y);
    }
}
