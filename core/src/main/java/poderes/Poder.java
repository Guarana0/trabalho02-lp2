package poderes;

import com.badlogic.gdx.math.Rectangle;

public abstract class Poder {
    protected boolean estaAtivo;
    protected final Rectangle areaItem = new Rectangle();
    protected float tempoPoder = 10f;
}
