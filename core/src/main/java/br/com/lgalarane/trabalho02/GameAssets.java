package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    private final AssetManager managerAsset = new AssetManager();

    public Sound somPulo;
    public Sound somDano;

    // Guardaremos as TextureRegions prontas para o jogo usar
    public TextureRegion texRegConcreto;
    public TextureRegion texRegFogo;
    public TextureRegion texRegNeve;
    public TextureRegion texRegGrama;
    public TextureRegion texRegMoeda;

    public void carregaTodosAssets() {
        // Carregando os Sons
        managerAsset.load("fontes/dano.wav", Sound.class);
        managerAsset.load("fontes/pulo.wav", Sound.class);

        // Carregando as Texturas (AssetManager carrega o arquivo como Texture)
        managerAsset.load("textures/concreto.png", Texture.class);
        managerAsset.load("textures/fogo.png", Texture.class);
        managerAsset.load("textures/neve.png", Texture.class);
        managerAsset.load("textures/grama.png", Texture.class);
        managerAsset.load("textures/moeda.png", Texture.class);

        // Bloqueia a execução até que tudo seja carregado
        managerAsset.finishLoading();

        // Atribuindo os sons
        somPulo = managerAsset.get("fontes/pulo.wav", Sound.class);
        somDano = managerAsset.get("fontes/dano.wav", Sound.class);

        // Criando as TextureRegions a partir das Textures carregadas
        texRegConcreto = new TextureRegion(managerAsset.get("textures/concreto.png", Texture.class));
        texRegFogo = new TextureRegion(managerAsset.get("textures/fogo.png", Texture.class));
        texRegNeve = new TextureRegion(managerAsset.get("textures/neve.png", Texture.class));
        texRegGrama = new TextureRegion(managerAsset.get("textures/grama.png", Texture.class));
        texRegMoeda = new TextureRegion(managerAsset.get("textures/moeda.png", Texture.class));
    }

    public void limparAssets() {
        managerAsset.dispose();
    }
}