package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    private AssetManager managerAsset;

    public Sound somDano;

    public TextureRegion texRegConcreto;
    public TextureRegion texRegFogo;
    public TextureRegion texRegNeve;
    public TextureRegion texRegGrama;
    public TextureRegion taxRegMoeda; 
    public TextureRegion texRegMoeda;  
    public TextureRegion texRegGranada;

    public TextureRegion texRegFundoGrama;
    public TextureRegion texRegFundoFogo;
    public TextureRegion texRegFundoNeve;
    public TextureRegion texRegFundoConcreto;

    public TextureRegion personagem;
    public Animation<TextureRegion> animacaoPersonagem;

    public GameAssets() {
        this.managerAsset = new AssetManager();
    }

    public void carregaTodosAssets() {
        managerAsset.load("fontes/dano.wav", Sound.class);

        managerAsset.load("textures/concreto.png", Texture.class);
        managerAsset.load("textures/fogo.png", Texture.class);
        managerAsset.load("textures/neve.png", Texture.class);
        managerAsset.load("textures/grama.png", Texture.class);
        managerAsset.load("textures/moeda.png", Texture.class);
        managerAsset.load("textures/granada.png", Texture.class); 

        managerAsset.load("textures/gramafundo.png", Texture.class);
        managerAsset.load("textures/fogofundo.png", Texture.class);
        managerAsset.load("textures/nevefundo.png", Texture.class);
        managerAsset.load("textures/concretofundo.png", Texture.class);
        managerAsset.load("textures/personagem.png", Texture.class);

        managerAsset.finishLoading();

        somDano = managerAsset.get("fontes/dano.wav", Sound.class);

        texRegConcreto = new TextureRegion(managerAsset.get("textures/concreto.png", Texture.class));
        texRegFogo = new TextureRegion(managerAsset.get("textures/fogo.png", Texture.class));
        texRegNeve = new TextureRegion(managerAsset.get("textures/neve.png", Texture.class));
        texRegGrama = new TextureRegion(managerAsset.get("textures/grama.png", Texture.class));
        
        Texture moedaTex = managerAsset.get("textures/moeda.png", Texture.class);
        texRegMoeda = new TextureRegion(moedaTex);
        taxRegMoeda = new TextureRegion(moedaTex);
        
        texRegGranada = new TextureRegion(managerAsset.get("textures/granada.png", Texture.class));

        texRegFundoGrama = new TextureRegion(managerAsset.get("textures/gramafundo.png", Texture.class));
        texRegFundoFogo = new TextureRegion(managerAsset.get("textures/fogofundo.png", Texture.class));
        texRegFundoNeve = new TextureRegion(managerAsset.get("textures/nevefundo.png", Texture.class));
        texRegFundoConcreto = new TextureRegion(managerAsset.get("textures/concretofundo.png", Texture.class));
        
        Texture texturaPersonagem = managerAsset.get("textures/personagem.png", Texture.class);
        personagem = new TextureRegion(texturaPersonagem);

        // Inicializa a animação com 0.1f segundos de intervalo por frame
        animacaoPersonagem = new Animation<>(0.1f, framesAnimacao);
    }

    public void limparAssets() {
        if (managerAsset != null) {
            managerAsset.dispose();
        }
    }
}