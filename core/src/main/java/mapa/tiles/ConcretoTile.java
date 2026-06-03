package mapa.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public class ConcretoTile extends ObjetoDeJogo{

    public ConcretoTile(TextureRegion textura, float x, float y) {
        super(textura, x, y);
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
