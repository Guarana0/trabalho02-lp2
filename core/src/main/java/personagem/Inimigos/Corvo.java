package personagem.Inimigos;

import br.com.lgalarane.trabalho02.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import personagem.Inimigo;

public class Corvo extends Inimigo {

    private final Animation<TextureRegion> animVoando;
    private float stateTime = 0f;

    public Corvo(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);
        animVoando = new Animation<>(0.1f, assets.framesVoandoCorvo, Animation.PlayMode.LOOP);
    }

    public void renderizar(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = animVoando.getKeyFrame(stateTime, true);
        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }
}

