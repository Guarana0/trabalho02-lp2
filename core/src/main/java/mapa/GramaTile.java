package mapa;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public class GramaTile extends ObjetoDeJogo {

    public GramaTile(TextureRegion textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public Tile clone() {
        return new GramaTile(this.textura, this.posicao.x, this.posicao.y);
    }

    @Override
    public boolean ehAndavel() {
        return true;
    }

    @Override
    public boolean daDano() {
        return false; 
    }
}