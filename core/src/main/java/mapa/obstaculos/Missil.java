package mapa.obstaculos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class Missil extends Obstaculo implements Explodivel{
    private final float alturaBase;
    private float tempo = 0f;

    private final float AMPLITUDE = 10f;  
    private final float VELOCIDADE_OSCILACAO = 5f; 

    public Missil(TextureRegion textura, float x, float y) {
        super(textura, x, y, 45f);
        this.alturaBase = y; 
    }

    public void atualizarSeno(float delta) {
        tempo += delta * VELOCIDADE_OSCILACAO;
        float novoY = alturaBase + (MathUtils.sin(tempo) * AMPLITUDE); 
        getPosicao().y = novoY;
    }

    @Override
    public boolean deveExplodir(float xJogador, float yJogador) {
        Rectangle areaMissil = new Rectangle(getPosicao().x, getPosicao().y, 32f, 32f);
        return areaMissil.overlaps(new Rectangle(xJogador, yJogador, 32f, 32f));
    }
}