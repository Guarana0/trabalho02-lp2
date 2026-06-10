package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    private AssetManager managerAsset;

    public Sound somDano;

    public TextureRegion texRegConcreto;
    public TextureRegion texRegFogo;
    public TextureRegion texRegNeve;
    public TextureRegion texRegGrama;
    public TextureRegion texRegMoeda;  
    public TextureRegion texRegGranada;
    public TextureRegion texRegMissil; 

    public TextureRegion texRegFundoGrama;
    public TextureRegion texRegFundoFogo;
    public TextureRegion texRegFundoNeve;
    public TextureRegion texRegFundoConcreto;

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
        managerAsset.load("textures/missil.png", Texture.class); 

        managerAsset.load("textures/gramafundo.png", Texture.class);
        managerAsset.load("textures/fogofundo.png", Texture.class);
        managerAsset.load("textures/nevefundo.png", Texture.class);
        managerAsset.load("textures/concretofundo.png", Texture.class);

        managerAsset.finishLoading();

        somDano = managerAsset.get("fontes/dano.wav", Sound.class);

        texRegConcreto = new TextureRegion(managerAsset.get("textures/concreto.png", Texture.class));
        texRegFogo = new TextureRegion(managerAsset.get("textures/fogo.png", Texture.class));
        texRegNeve = new TextureRegion(managerAsset.get("textures/neve.png", Texture.class));
        texRegGrama = new TextureRegion(managerAsset.get("textures/grama.png", Texture.class));
        
        texRegMoeda = new TextureRegion(managerAsset.get("textures/moeda.png", Texture.class));
        texRegGranada = new TextureRegion(managerAsset.get("textures/granada.png", Texture.class));
        
        texRegMissil = new TextureRegion(managerAsset.get("textures/missil.png", Texture.class));

        texRegFundoGrama = new TextureRegion(managerAsset.get("textures/gramafundo.png", Texture.class));
        texRegFundoFogo = new TextureRegion(managerAsset.get("textures/fogofundo.png", Texture.class));
        texRegFundoNeve = new TextureRegion(managerAsset.get("textures/nevefundo.png", Texture.class));
        texRegFundoConcreto = new TextureRegion(managerAsset.get("textures/concretofundo.png", Texture.class));
    }

    public void limparAssets() {
        if (managerAsset != null) {
            managerAsset.dispose();
        }
    }
}