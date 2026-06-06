package br.com.lgalarane.trabalho02;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import mapa.GeradorCenario;
import personagem.Inimigo;
import personagem.PersonagemPrincipal;
import personagem.Inimigos.Corvo;
import personagem.Inimigos.Esqueleto;

public class Jogo extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer; 
    private BitmapFont font;
    private GeradorCenario geradorCenario;
    
    private GameAssets assets;
    private PersonagemPrincipal personagem; 

    private ArrayList<Inimigo> listaInimigos;

    // controla a velocidade independente do personagem
    private float posicaoMapaX = 0f;
    private final float VELOCIDADE_MAPA = 70f; 

    @Override
    public void create() {
        // usa do polimorfismo para criar cada inimigo - NAO FOI TERMINADO DE IMPLEMENTAR AINDA
        listaInimigos = new ArrayList<Inimigo>();

        assets = new GameAssets();
        assets.carregaTodosAssets();

        listaInimigos.add(new Esqueleto(VELOCIDADE_MAPA, VELOCIDADE_MAPA, posicaoMapaX, VELOCIDADE_MAPA, assets.somDano));
        listaInimigos.add(new Corvo(VELOCIDADE_MAPA, VELOCIDADE_MAPA, posicaoMapaX, VELOCIDADE_MAPA, assets.somDano));

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

        personagem = new PersonagemPrincipal(0f, 100f, 40f, 40f, assets.somDano);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        float delta = Gdx.graphics.getDeltaTime();

        personagem.atualizar(delta);

        posicaoMapaX += VELOCIDADE_MAPA * delta;

        geradorCenario.atualizar(posicaoMapaX);

        batch.begin();

        // O cenário renderiza baseado na posição independente do mapa
        geradorCenario.renderizar(batch, posicaoMapaX);

        int distancia = (int) personagem.getDistanciaPercorrida();
        int vida = personagem.getVida();

        font.draw(batch, "Distância: " + distancia + "m", 10, Gdx.graphics.getHeight() - 10);
        font.draw(batch, "Vida: " + vida, 10, Gdx.graphics.getHeight() - 30);

        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        personagem.renderizar(shapeRenderer);
        
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose(); 
        font.dispose();
        assets.limparAssets();
    }
}