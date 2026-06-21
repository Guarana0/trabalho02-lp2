package personagem.Inimigos;

import br.com.lgalarane.trabalho02.GameAssets;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import personagem.Inimigo;

public class Esqueleto extends Inimigo {

    private final TextureRegion texturaEsqueleto;

    public Esqueleto(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);
        this.texturaEsqueleto = new TextureRegion(assets.texEsqueleto);
    }

    public void renderizar(SpriteBatch batch) {
        TextureRegion frame = texturaEsqueleto;
        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }
}


