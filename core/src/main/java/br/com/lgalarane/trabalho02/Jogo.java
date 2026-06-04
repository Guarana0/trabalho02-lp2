package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

import mapa.GeradorCenario;

public class Jogo extends ApplicationAdapter{
    private SpriteBatch batch;
    private GeradorCenario geradorCenario;
    private float jogadorX = 0f; //temporario

    private Texture texConcreto;
    private Texture texFogo;
    private Texture texNeve;
    private Texture texGrama;
    private Texture texMoeda;

    @Override
    public void create() {
        batch = new SpriteBatch();

        texConcreto = new Texture("textures/concreto.png");
        texFogo = new Texture("textures/fogo.png");
        texNeve = new Texture("textures/neve.png");
        texGrama = new Texture("textures/grama.png");
        texMoeda = new Texture("textures/moeda.png");
     
      

        TextureRegion texRegConcreto = new TextureRegion(texConcreto);
        TextureRegion texRegFogo = new TextureRegion(texFogo);
        TextureRegion texRegNeve = new TextureRegion(texNeve);
        TextureRegion texRegGrama = new TextureRegion(texGrama);
        TextureRegion texRegMoeda = new TextureRegion(texMoeda);
       
    
        geradorCenario = new GeradorCenario(texRegConcreto, texRegFogo, texRegNeve, texRegGrama, texRegMoeda);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        
        // Atualiza a posição do jogador de forma suave
        jogadorX += 200f * com.badlogic.gdx.Gdx.graphics.getDeltaTime(); 
        geradorCenario.atualizar(jogadorX);

        batch.begin();
        // Modifique aqui para passar o jogadorX para o renderizar
        geradorCenario.renderizar(batch, jogadorX);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texConcreto.dispose();
        texFogo.dispose();
        texNeve.dispose();
        texGrama.dispose();
        texMoeda.dispose();
    }

}
