package personagem.Inimigos;

import br.com.lgalarane.trabalho02.GameAssets;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import personagem.Inimigo;

public class Goblin extends Inimigo {

    private final Animation<TextureRegion> animCorrendo;
    private final Animation<TextureRegion> animPulando;

    private float stateTime = 0f;
    private float tempoPulo = 0f;
    private float tempoProximoPulo = 0f;
    private boolean pulando = false;
    private float vy = 0f;
    private float gravidade = 300f;
    private float forcaPulo = 150f;

    public Goblin(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);

        // animações recortadas com base na spritesheet (256x64 conforme definido no
        // GameAssets)
        animCorrendo = new Animation<>(0.1f, assets.framesCorrendoGoblin, Animation.PlayMode.LOOP);
        animPulando = new Animation<>(0.1f, assets.framesPulandoGoblin, Animation.PlayMode.LOOP);

        tempoProximoPulo = MathUtils.random(1f, 3f);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        tempoPulo += deltaTime;

        if (!pulando && tempoPulo >= tempoProximoPulo) {
            pulando = true;
            vy = forcaPulo;
            tempoProximoPulo = MathUtils.random(1f, 3f);
            tempoPulo = 0f;
        }

        if (pulando) {
            vy -= gravidade * deltaTime;
            posicao.y += vy * deltaTime;

            if (posicao.y <= 32f) {
                posicao.y = 32f;
                pulando = false;
                vy = 0f;
            }
        }
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
