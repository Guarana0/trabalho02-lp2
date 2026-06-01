package objetos;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import mapa.tiles.Tile;

public abstract class ObjetoDeJogo implements Tile {
    protected final TextureRegion textura;
    protected final Vector2 posicao;

    public ObjetoDeJogo(TextureRegion textura, float x, float y) {
        this.textura = textura;
        this.posicao = new Vector2(x, y);
    }

    @Override
    public TextureRegion getTextura() {
        return this.textura;
    }

    @Override
    public Vector2 getPosicao() {
        return this.posicao;
    }

    @Override
    public void setPosicao(float x, float y) {
        this.posicao.set(x, y);
    }

    //clone() como abstrato pq força cada filho a implementar o seu próprio clone 
    @Override
    public abstract Tile clone();
}
