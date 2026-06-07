package br.com.lgalarane.trabalho02;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import mapa.GeradorCenario;
import mapa.TipoBioma;
import mapa.planosdefundo.GeradorFundo;
import mapa.tiles.MoedaTile;
import objetos.ObjetoDeJogo;
import personagem.Inimigo;
import personagem.PersonagemPrincipal;
import personagem.Inimigos.Corvo;
import personagem.Inimigos.Esqueleto;

public class Jogo extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; 
    private BitmapFont font;
    private GeradorCenario geradorCenario;
    private GeradorFundo geradorFundo;
    
    private GameAssets assets;
    private PersonagemPrincipal personagem; 

    private ArrayList<Inimigo> listaInimigos;

    private Rectangle areaMoeda;

    private float posicaoMapaX = 0f;
    private final float VELOCIDADE_MAPA = 150f; 

    @Override
    public void create() {
        float larguraMundo = Gdx.graphics.getWidth(); // Ou o tamanho real do seu mapa/tela
        float alturaMundo = Gdx.graphics.getHeight();
        listaInimigos = new ArrayList<Inimigo>();

        assets = new GameAssets();
        assets.carregaTodosAssets();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer(); 
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        geradorCenario = new GeradorCenario(
            assets.texRegConcreto, 
            assets.texRegFogo, 
            assets.texRegNeve, 
            assets.texRegGrama, 
            assets.texRegMoeda
        );

        geradorFundo = new GeradorFundo(
            assets.texRegFundoGrama,
            assets.texRegFundoFogo,
            assets.texRegFundoNeve,
            assets.texRegFundoConcreto
        );

        personagem = new PersonagemPrincipal(0f, 100f, 40f, 40f, assets.somDano);
        areaMoeda = new Rectangle();

        listaInimigos.add(new Esqueleto(larguraMundo, alturaMundo, posicaoMapaX, VELOCIDADE_MAPA, assets.somDano));
        listaInimigos.add(new Corvo(larguraMundo, alturaMundo, posicaoMapaX, VELOCIDADE_MAPA, assets.somDano));
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        personagem.atualizar(delta, VELOCIDADE_MAPA);

        posicaoMapaX += VELOCIDADE_MAPA * delta;

        geradorCenario.atualizar(posicaoMapaX);
        verificarColetaMoedas();
        verificarInputGranada();

        batch.begin();

        TipoBioma biomaAtivo = geradorCenario.getBiomaSobOJogador(posicaoMapaX);
        geradorFundo.renderizar(batch, biomaAtivo);

        geradorCenario.renderizar(batch, posicaoMapaX);

         for (Inimigo inimigo : listaInimigos) {
            inimigo.update(delta);
            inimigo.darDano(personagem);
        }

        int distancia = (int) personagem.getDistanciaPercorrida();
        int vida = personagem.getVida();
        int moedas = personagem.getMoeda();
        int granadas = personagem.getQtdGranadas();

        font.draw(batch, "Distancia: " + distancia + "m", 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Vida: " + vida, 10, Gdx.graphics.getHeight() - 30);
        font.draw(batch, "Moedas: " + moedas, 10, Gdx.graphics.getHeight() - 50);
        
        batch.draw(assets.texRegGranada, 10, Gdx.graphics.getHeight() - 95, 24, 24);
        font.draw(batch, "x" + granadas, 40, Gdx.graphics.getHeight() - 78);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        personagem.renderizar(shapeRenderer);
        for (Inimigo inimigo : listaInimigos) {
            inimigo.renderizar(shapeRenderer); // Chama a função render de cada inimigo
        }
        shapeRenderer.end();
    }

    private void verificarInputGranada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            int granadasAtuais = personagem.getQtdGranadas();
            if (granadasAtuais > 0) {
                personagem.setQtdGranadas(granadasAtuais - 1);
            }
        }
    }

    private void verificarColetaMoedas() {
        Rectangle colisaoJogador = personagem.getColisao();
        for (int i = geradorCenario.getObjetosAtivos().size - 1; i >= 0; i--) {
            ObjetoDeJogo obj = geradorCenario.getObjetosAtivos().get(i);
            if (!(obj instanceof MoedaTile)) {
                continue;
            }

            float xTela = obj.getPosicao().x - posicaoMapaX + 100f;
            float yTela = obj.getPosicao().y;
            areaMoeda.set(xTela, yTela, 32f, 32f);

            if (colisaoJogador.overlaps(areaMoeda)) {
                personagem.adicionarMoeda();
                geradorCenario.getObjetosAtivos().removeIndex(i);
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose(); 
        font.dispose();
        assets.limparAssets();
    }
}