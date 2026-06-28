package poderes;

import com.badlogic.gdx.math.Rectangle;

public abstract class Poder {
    protected boolean estaAtivo;
    protected Rectangle areaItem = new Rectangle();
    protected float tempoPoder = 10f;

    public void setEstaAtivo(boolean ativo) {
        this.estaAtivo = ativo;
        if (ativo) {
            this.tempoPoder = 10f; // Reseta o tempo do poder
        }
    }

    public Rectangle getAreaItem() {
        return areaItem;
    }

    public float getTempoPoder() {
        return tempoPoder;
    }

    public boolean estaAtivo() {
        return estaAtivo;
    }

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
