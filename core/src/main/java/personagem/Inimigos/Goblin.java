package personagem.Inimigos;

import br.com.lgalarane.trabalho02.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import personagem.Inimigo;

public class Goblin extends Inimigo {

    private final Animation<TextureRegion> animCorrendo;
    private final Animation<TextureRegion> animPulando;

    private float stateTime = 0f;

    private boolean pulando = false;

    public Goblin(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);

        // animações recortadas com base na spritesheet (256x64 conforme definido no
        // GameAssets)
        animCorrendo = new Animation<>(0.1f, assets.framesCorrendoGoblin, Animation.PlayMode.LOOP);
        animPulando = new Animation<>(0.1f, assets.framesPulandoGoblin, Animation.PlayMode.LOOP);
    }

    public void renderizar(SpriteBatch batch) {
        stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        TextureRegion frame = pulando
                ? animPulando.getKeyFrame(stateTime, true)
                : animCorrendo.getKeyFrame(stateTime, true);

        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

    public void renderizar(SpriteBatch batch, float xTela) {
        stateTime += com.badlogic.gdx.Gdx.graphics.getDeltaTime();

        TextureRegion frame = pulando
                ? animPulando.getKeyFrame(stateTime, true)
                : animCorrendo.getKeyFrame(stateTime, true);

        batch.draw(frame, xTela, posicao.y, dimensoes.x, dimensoes.y);
    }
}
