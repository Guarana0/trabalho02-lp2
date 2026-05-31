package mapa;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class GramaTile implements Tile{
    private final TextureRegion textura;
    private Vector2 posicao;

    public GramaTile(TextureRegion textura, float x, float y){
        this.textura = textura;
        this.posicao = new Vector2(x, y);
    }

    public void setPosicao(float x, float y) {
        this.posicao = new Vector2(x, y);
    }

    //padrao prototype
    @Override
    public Tile clone() { 
        try {
            return new GramaTile(this.textura, this.posicao.x, this.posicao.y);
        } catch (Exception e) {
            throw new AssertionError(); 
        }
    }


    @Override
    public TextureRegion getTextura() {
        return textura;
    }

    @Override
    public boolean ehAndavel() {
        return true;
    }
    
    @Override
    public boolean daDano() {
        return false;
    }

    @Override
    public Vector2 getPosicao() {
        return this.posicao;
    }
}
