package personagem;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Inimigo extends Personagem {
    protected float velocidadeX = -20f;
    protected boolean indoEsquerda = true;
    protected boolean jaCausouDano = false;

    public Inimigo(float x, float y, float largura, float altura, Sound somDano) {
        super(x, y, largura, altura, somDano);
        this.dano = 1;
        this.vida = 1;
        this.velocidade.x = velocidadeX;
    }

    public void darDano(Personagem alvo) {
        if (alvo != null && this.getColisao() != null && alvo.getColisao() != null) {
            if (this.getColisao().overlaps(alvo.getColisao())) {
                if (!jaCausouDano) {
                    alvo.tomarDano(this.dano);
                    jaCausouDano = true;
                }
            } else {
                jaCausouDano = false;
            }
        }
    }

    public void renderizar(SpriteBatch batch) {
        // Default draw for subclasses that implement SpriteBatch render
    }

    public void renderizar(SpriteBatch batch, float posicaoTelaX) {
        float oldX = posicao.x;
        posicao.x = posicaoTelaX;
        renderizar(batch);
        posicao.x = oldX;
    }

    public void update(float deltaTime) {
        posicao.x += velocidade.x * deltaTime;
    }
}
