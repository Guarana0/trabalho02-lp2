package mapa.obstaculos;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Zapper extends Obstaculo implements Explodivel {

    private float angulo = 0f;
    private final float VELOCIDADE_ROTACAO = 40f; 

    public Zapper(TextureRegion textura, float x, float y) {
        super(textura, x, y, 0f); 
    }

    @Override
    public void atualizarObstaculo(float delta) {
        angulo += VELOCIDADE_ROTACAO * delta;
        if (angulo >= 360f) {
            angulo -= 360f;
        }
    }

    @Override
    public boolean deveExplodir(float xJogador, float yJogador) {
        Rectangle areaZapper = new Rectangle(getPosicao().x, getPosicao().y, 32f, 48f);
        Rectangle areaJogador = new Rectangle(xJogador, yJogador, 32f, 32f);
        return areaZapper.overlaps(areaJogador);
    }

    public float getAngulo() {
        return this.angulo;
    }
}