package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PersonagemPrincipal extends Personagem {
    private boolean apertouEsp;
    private boolean protegidoPorEscudo;

    private int moeda = 0;
    private int qtdGranadas=5;

    private final float GRAVIDADE = -500f;
    private final float FORCA_JETPACK = 1000f;
    private final float VELOCIDADE_MAIXIMA = 500f;
    private float velocidadeY = 0f;

    private final float ALTURA_CHAO = 50f;
    private final float POSICAO_FIXA_TELA_X = 100f;
    private float distanciaPercorrida = 0f;

    private Animation<TextureRegion> animacaoAtual;
    private float stateTime;

    public PersonagemPrincipal(float x, float y, float largura, float altura, Sound somDano, Animation<TextureRegion> animacao) {
        super(x, y, largura, altura, somDano);
        this.protegidoPorEscudo = false;
        this.vida = 3;
        this.animacaoAtual = animacao;
        this.stateTime = 0;

        if (animacao != null) {
            TextureRegion primeiroFrame = animacao.getKeyFrame(0);
            this.dimensoes.set(primeiroFrame.getRegionWidth(), primeiroFrame.getRegionHeight());
            
            // Atualiza também o tamanho da área de colisão para bater com o tamanho do sprite
            this.areaColisao.setSize(this.dimensoes.x, this.dimensoes.y);
        }
    }

    public boolean getProtegidoPorEscudo() { return protegidoPorEscudo; }
    public void setProtegidoPorEscudo(boolean protegidoPorEscudo) { this.protegidoPorEscudo = protegidoPorEscudo; }
    public float getDistanciaPercorrida() { return distanciaPercorrida; }

    public void atualizar(float deltaTempo, float velocidadeMapa) {
        this.stateTime += deltaTempo;

        apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();
        
        if (apertouEsp) {
            velocidadeY += (GRAVIDADE + FORCA_JETPACK) * deltaTempo;
        } else {
            velocidadeY += GRAVIDADE * deltaTempo;
        }

        if (velocidadeY > VELOCIDADE_MAIXIMA) { velocidadeY = VELOCIDADE_MAIXIMA; }
        if (velocidadeY < -VELOCIDADE_MAIXIMA) { velocidadeY = -VELOCIDADE_MAIXIMA; }

        float novoY = posicao.y + (velocidadeY * deltaTempo);
        distanciaPercorrida += velocidadeMapa * deltaTempo;

        float alturaTetoTela = Gdx.graphics.getHeight() - dimensoes.y;

        if (novoY < ALTURA_CHAO) {
            novoY = ALTURA_CHAO;
            velocidadeY = 0;
        } else if (novoY > alturaTetoTela) {
            novoY = alturaTetoTela;
            velocidadeY = 0;
        }

        posicao.set(POSICAO_FIXA_TELA_X, novoY);
        areaColisao.setPosition(POSICAO_FIXA_TELA_X, novoY);
    }

    public void renderizar(SpriteBatch batch) {
        // aqui renderiza as animações e o loop fica em true para a animação sempre progredir
        TextureRegion frameAtual = animacaoAtual.getKeyFrame(stateTime, true);

        batch.draw(frameAtual, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
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

    //granadas
    public int getQtdGranadas() {
        return qtdGranadas;
    }

    public void setQtdGranadas(int qtdGranadas) {
        this.qtdGranadas = qtdGranadas;
    }

    public boolean morte() {
        if(this.getVida() == 0) {
            return true;
        } else {
            return false;
        }
    }
}