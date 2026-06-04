package poderes;

import com.badlogic.gdx.math.Rectangle;

public abstract class Poder {
    protected boolean estaAtivo;
    protected final Rectangle areaItem = new Rectangle();
    protected float tempoPoder = 10f;

    public boolean verificarColisao(personagem.Personagem personagem) {
        if (personagem == null || personagem.getColisao() == null || areaItem == null) {
            return false;
        }

        if (personagem.getColisao().overlaps(areaItem)) {
            this.estaAtivo = true;
            return true;
        } else {
            return false;
        }
    }
}
