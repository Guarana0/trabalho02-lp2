package mapa.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public class FogoTile extends ObjetoDeJogo {
    private final int dano = 1;

    public FogoTile(TextureRegion textura, float x, float y) {
        super(textura, x, y);
    }

    @Override
    public boolean ehAndavel() {
        return false;
    }

    @Override
    public boolean daDano() {
        return true;
    }

    public int getDano() {
        return dano;
    }
    
}
