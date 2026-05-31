package mapa;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public class Moeda extends ObjetoDeJogo {

    public Moeda(TextureRegion textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public Tile clone() {
        return new Moeda(this.textura, this.posicao.x, this.posicao.y);
    }

    @Override
    public boolean ehAndavel() {
        return false; 
    }

    @Override
    public boolean daDano() {
         return false; 
    }
}