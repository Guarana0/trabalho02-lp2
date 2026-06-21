package personagem;

import br.com.lgalarane.trabalho02.GameAssets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
<<<<<<< HEAD
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
=======
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
>>>>>>> main
import com.badlogic.gdx.math.MathUtils;

import mapa.obstaculos.Explodivel;

public class PersonagemPrincipal extends Personagem implements Explodivel{
    private boolean apertouEsp;
    private boolean protegidoPorEscudo;

    private int moeda = 0;
    private int qtdGranadas = 5;

    private final float GRAVIDADE = -2200f;
    private final float FORCA_JETPACK = 1800f;
    private final float VELOCIDADE_MAXIMA_SUBIDA = 320f;
    private final float VELOCIDADE_MAXIMA_DESCIDA = -700f;
    private float velocidadeY = 0f;

    private final float ALTURA_CHAO = 8f;
    private final float POSICAO_FIXA_TELA_X = 100f;
    private float distanciaPercorrida = 0f;

<<<<<<< HEAD
    private Animation<TextureRegion> animCorrendo;
    private Animation<TextureRegion> animVoando;

    private enum Estado { PARADO, CORRENDO, VOANDO }
    private Estado estado = Estado.CORRENDO;

    private float stateTime = 0f; 

    public PersonagemPrincipal(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);
        this.protegidoPorEscudo = false;
        this.vida = 3;

        animCorrendo = new Animation<>(0.1f, assets.framesCorrendo, Animation.PlayMode.LOOP);
        animVoando   = new Animation<>(0.1f, assets.framesVoando,   Animation.PlayMode.LOOP);
=======
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
>>>>>>> main
    }

    public boolean getProtegidoPorEscudo() {
        return protegidoPorEscudo;
    }

    public void setProtegidoPorEscudo(boolean protegidoPorEscudo) {
        this.protegidoPorEscudo = protegidoPorEscudo;
    }

    public float getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public void atualizar(float deltaTempo, float velocidadeMapa) {
<<<<<<< HEAD
    stateTime += deltaTempo;

    apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();

    Estado estadoAnterior = estado;
=======
        this.stateTime += deltaTempo;

        apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();
>>>>>>> main

        if (apertouEsp) {
            if (velocidadeY < 0) {
                velocidadeY *= 0.5f; // reduz a queda antes de subir
            }
            velocidadeY += FORCA_JETPACK * deltaTempo;
            estado = Estado.VOANDO;
        } else {
            velocidadeY += GRAVIDADE * deltaTempo;
            if (posicao.y <= ALTURA_CHAO) {
                estado = Estado.CORRENDO;
            } else {
                estado = Estado.VOANDO;
            }
        }

        velocidadeY = MathUtils.clamp(velocidadeY, VELOCIDADE_MAXIMA_DESCIDA, VELOCIDADE_MAXIMA_SUBIDA);

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
<<<<<<< HEAD
        TextureRegion frame;

        switch (estado) {
            case VOANDO:
                frame = animVoando.getKeyFrame(stateTime);
                break;
            default:
                frame = animCorrendo.getKeyFrame(stateTime);
        }

        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
=======
        // aqui renderiza as animações e o loop fica em true para a animação sempre progredir
        TextureRegion frameAtual = animacaoAtual.getKeyFrame(stateTime, true);

        batch.draw(frameAtual, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
>>>>>>> main
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

    // granadas
    public int getQtdGranadas() {
        return qtdGranadas;
    }

    public void setQtdGranadas(int qtdGranadas) {
        this.qtdGranadas = qtdGranadas;
    }

    public boolean morte() {
        if (this.getVida() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean temEscudo() {
        return this.protegidoPorEscudo;
    }

    public void desativarEscudo() {
        this.protegidoPorEscudo = false;
    }

    @Override
    public boolean deveExplodir(float x, float y) {
        return this.vida <= 0;
    }
}