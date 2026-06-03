package mapa.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public class MoedaTile extends ObjetoDeJogo {

    public MoedaTile(TextureRegion textura, float x, float y) {
        super(textura, x, y);
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