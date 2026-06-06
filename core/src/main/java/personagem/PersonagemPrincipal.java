package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class PersonagemPrincipal extends Personagem {
    private boolean apertouEsp;
    private boolean protegidoPorEscudo;

    private int moeda = 0;

    private final float GRAVIDADE = -500f;
    private final float FORCA_JETPACK = 1000f;
    private final float VELOCIDADE_MAIXIMA = 500f;
    private float velocidadeY = 0f;

    private final float ALTURA_CHAO = 50f;
    private final float VELOCIDADE_CORRIDA = 20f;
    private float distanciaPercorrida = 0f;

    public PersonagemPrincipal(float x, float y, float largura, float altura, Sound somDano) {
        super(x, y, largura, altura, somDano);
        this.protegidoPorEscudo = false;
        this.vida = 3;
    }

    public boolean getProtegidoPorEscudo() { return protegidoPorEscudo; }
    public void setProtegidoPorEscudo(boolean protegidoPorEscudo) { this.protegidoPorEscudo = protegidoPorEscudo; }
    public float getDistanciaPercorrida() { return distanciaPercorrida; }

    public void atualizar(float deltaTempo, float velocidadeMapa) {
        apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();
        
        if (apertouEsp) {
            velocidadeY += (GRAVIDADE + FORCA_JETPACK) * deltaTempo;
        } else {
            velocidadeY += GRAVIDADE * deltaTempo;
        }

        if (velocidadeY > VELOCIDADE_MAIXIMA) { velocidadeY = VELOCIDADE_MAIXIMA; }
        if (velocidadeY < -VELOCIDADE_MAIXIMA) { velocidadeY = -VELOCIDADE_MAIXIMA; }

        float novoY = posicao.y + (velocidadeY * deltaTempo);
        float velocidadeHorizontal = Math.min(VELOCIDADE_CORRIDA, velocidadeMapa);
        float novoX = posicao.x + (velocidadeHorizontal * deltaTempo);
        distanciaPercorrida += velocidadeHorizontal * deltaTempo;

        float alturaTetoTela = Gdx.graphics.getHeight() - dimensoes.y;

        if (novoY < ALTURA_CHAO) {
            novoY = ALTURA_CHAO;
            velocidadeY = 0;
        } else if (novoY > alturaTetoTela) {
            novoY = alturaTetoTela;
            velocidadeY = 0;
        }

        posicao.set(novoX, novoY);
        areaColisao.setPosition(novoX, novoY);
    }

    public void renderizar(ShapeRenderer shape) {
        if (protegidoPorEscudo) {
            shape.setColor(Color.CYAN);
        } else {
            shape.setColor(Color.BLUE);
        }

        shape.rect(posicao.x, posicao.y, dimensoes.x, dimensoes.y);
    }

    @Override
    public int tomarDano(int danoRecebido) {
        if (this.protegidoPorEscudo) {
            return this.vida;
        }
        return super.tomarDano(danoRecebido);
    }

    public int getMoeda() {
        return moeda;
    }

    public void adicionarMoeda() {
        moeda += 1;
    }

    @Override
    public int getVida() {
        return vida;
    }
}