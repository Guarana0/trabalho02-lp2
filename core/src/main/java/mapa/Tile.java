package mapa;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public interface Tile extends Cloneable {
    TextureRegion getTextura();
    boolean ehAndavel();
    boolean daDano();
    Vector2 getPosicao();

    Tile clone(); //isso facilita a criação de tiles.
}