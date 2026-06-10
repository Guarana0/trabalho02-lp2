package personagem;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Inimigo extends Personagem {
    protected float velocidadeX = -20f;
    protected boolean indoEsquerda = true;

    public Inimigo(float x, float y, float largura, float altura, Sound somDano) {
        super(x, y, largura, altura, somDano);
        this.dano = 1;
        this.vida = 1;
        this.velocidade.x = velocidadeX;
    }

    public void darDano(Personagem alvo) {
        if (alvo != null && this.getColisao() != null && alvo.getColisao() != null) {
            if (this.getColisao().overlaps(alvo.getColisao())) {
                alvo.tomarDano(this.dano);
            }
        }
    }

    public void renderizar(ShapeRenderer shape) {
        shape.setColor(Color.RED);
        shape.rect(posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

    public void update(float deltaTime) {
        posicao.x += (velocidade.x - 150f) * deltaTime; 
        
        areaColisao.setPosition(posicao.x, posicao.y);
    }
}
