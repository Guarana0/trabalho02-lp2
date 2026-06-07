package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public abstract class Inimigo extends Personagem {
    protected float velocidadeX = -20f;
    protected boolean indoEsquerda = true;

    public Inimigo(float larguraMapa, float alturaMapa, float largura, float altura, Sound somDano) {
        super(
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getWidth(), larguraMapa) - largura)),
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getHeight(), alturaMapa) - altura)),
            largura,
            altura,
            somDano
        );
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
        posicao.x += velocidade.x * deltaTime;
        areaColisao.setPosition(posicao.x, posicao.y);
        
        // Verifica a cada quadro se o inimigo saiu totalmente da tela
        deletar();
    }

    private void deletar() {
        if (this.posicao.x + this.dimensoes.x < 0) {
            this.estaVivo = false; // Desativa o inimigo
            this.destruir();
        }
    }
}
