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

    public Goblin(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);

        // animações recortadas com base na spritesheet (256x64 conforme definido no GameAssets)
        animCorrendo = new Animation<>(0.1f, assets.framesCorrendo, Animation.PlayMode.LOOP);
        animPulando  = new Animation<>(0.1f, assets.framePulando,  Animation.PlayMode.LOOP);
    }


    public void renderizar(SpriteBatch batch) {
        TextureRegion frame;

        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

}

