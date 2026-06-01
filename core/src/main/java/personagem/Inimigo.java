package personagem;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public abstract class Inimigo extends Personagem {
    protected int vida = 1;
    // para gerar posição aleatoria do inimigo no mapa conforme tasmanho da tela e do mundo atual
    float randomX = MathUtils.random();
    float randomY = MathUtils.random();

    public Inimigo(float x, float y, float altura, float largura) {
        super(x, y, altura, largura);
    }
}

