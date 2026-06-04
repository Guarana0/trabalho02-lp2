package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

public abstract class Inimigo extends Personagem {
    
    public Inimigo(float larguraMapa, float alturaMapa, float altura, float largura, Sound somDano) {
        super(
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getWidth(), larguraMapa) - largura)),
            MathUtils.random(0f, Math.max(0f, Math.min(Gdx.graphics.getHeight(), alturaMapa) - altura)),
            altura,
            largura,
            somDano
        );
        this.dano = 1; // Define o dano padrão do inimigo na classe mãe
        this.vida = 1; // Altera a vida herdada da classe mãe
    }

    public void darDano(Personagem alvo) {
        // Garante que o alvo e as caixas de colisão existem
        if (alvo != null && this.getColisao() != null && alvo.getColisao() != null) {
            
            // Verifica se a caixa de colisão do inimigo encostou na do alvo
            if (this.getColisao().overlaps(alvo.getColisao())) {
                
                // Faz o alvo tomar dano
                alvo.tomarDano(this.dano);
            }
        }
    }
}