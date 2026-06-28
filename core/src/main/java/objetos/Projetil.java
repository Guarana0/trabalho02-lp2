package objetos;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Projetil extends ObjetoDeJogo {
    private final Rectangle colisao;
    private final float velocidade = 600f; 
    private boolean ativo = true;

    public Projetil(TextureRegion textura, float xTela, float yTela) {
        super(textura, xTela, yTela);
        this.colisao = new Rectangle(xTela, yTela, 32f, 16f);
    }

    public void atualizar(float delta, float velocidadeMapa) {
        posicao.x += (velocidade + velocidadeMapa) * delta;
        colisao.setPosition(posicao.x, posicao.y);
    }

    public void renderizar(SpriteBatch batch) {
        batch.draw(textura, posicao.x, posicao.y, colisao.width, colisao.height);
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Rectangle getColisao() {
        return colisao;
    }

    @Override
    public boolean ehAndavel() {
        return false;
    }

    @Override
    public boolean daDano() { //nao da dano no jogador
        return false;
    }
}