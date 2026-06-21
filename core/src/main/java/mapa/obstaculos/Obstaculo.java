package mapa.obstaculos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import objetos.ObjetoDeJogo;

public abstract class Obstaculo extends ObjetoDeJogo {
    protected final int dano = 3;
    protected float velocidade;

    public Obstaculo(TextureRegion textura, float x, float y, float velocidade) {
        super(textura, x, y);
        this.velocidade = velocidade;
    }

    @Override
    public boolean ehAndavel() {
        return false;
    }

    @Override
    public boolean daDano() {
        return true;
    }

    public float getVelocidade() {
        return this.velocidade;
    }
    
}
