package personagem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import br.com.lgalarane.trabalho02.GameAssets;
import mapa.obstaculos.Explodivel;

public class PersonagemPrincipal extends Personagem implements Explodivel{
    private boolean apertouEsp;
    private boolean protegidoPorEscudo;
    private boolean imaAtivo;

    private int moeda = 0;
    private int qtdTiros = 20;

    private final float GRAVIDADE = -2200f;
    private final float FORCA_JETPACK = 1800f;
    private final float VELOCIDADE_MAXIMA_SUBIDA = 320f;
    private final float VELOCIDADE_MAXIMA_DESCIDA = -700f;
    private float velocidadeY = 0f;

    private final float ALTURA_CHAO = 8f;
    private final float POSICAO_FIXA_TELA_X = 100f;
    private float distanciaPercorrida = 0f;

    private Animation<TextureRegion> animCorrendo;
    private Animation<TextureRegion> animVoando;

    private enum Estado { PARADO, CORRENDO, VOANDO }
    private Estado estado = Estado.CORRENDO;

    private float stateTime = 0f; 

    public PersonagemPrincipal(float x, float y, float largura, float altura, Sound somDano, GameAssets assets) {
        super(x, y, largura, altura, somDano);
        this.protegidoPorEscudo = false;
        this.vida = 3;

        // Redimensiona a caixa de colisão para ser menor que o sprite
        float larguraColisao = largura * 0.5f;
        float alturaColisao = altura * 0.7f;
        this.areaColisao.setSize(larguraColisao, alturaColisao);

        animCorrendo = new Animation<>(0.1f, assets.framesCorrendo, Animation.PlayMode.LOOP);
        animVoando   = new Animation<>(0.1f, assets.framesVoando,   Animation.PlayMode.LOOP);
    }

    public boolean getProtegidoPorEscudo() {
        return protegidoPorEscudo;
    }

    public void setProtegidoPorEscudo(boolean protegidoPorEscudo) {
        this.protegidoPorEscudo = protegidoPorEscudo;
    }

    public boolean isImaAtivo() {
        return imaAtivo;
    }

    public void setImaAtivo(boolean imaAtivo) {
        this.imaAtivo = imaAtivo;
    }

    public float getDistanciaPercorrida() {
        return distanciaPercorrida;
    }

    public void atualizar(float deltaTempo, float velocidadeMapa) {
    stateTime += deltaTempo;

    apertouEsp = Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched();

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

        float xColisao = POSICAO_FIXA_TELA_X + (dimensoes.x - areaColisao.width) / 2f;
        posicao.set(POSICAO_FIXA_TELA_X, novoY);
        areaColisao.setPosition(xColisao, novoY);
    }

    public void renderizar(SpriteBatch batch) {
        TextureRegion frame;

        switch (estado) {
            case VOANDO:
                frame = animVoando.getKeyFrame(stateTime);
                break;
            default:
                frame = animCorrendo.getKeyFrame(stateTime);
        }

        batch.draw(frame, posicao.x, posicao.y, dimensoes.x, dimensoes.y);
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

    // tiros
    public int getQtdTiros() {
        return qtdTiros;
    }

    public void setQtdTiros(int qtdTiros) {
        this.qtdTiros = qtdTiros;
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