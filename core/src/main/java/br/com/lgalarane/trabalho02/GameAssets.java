package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    private final AssetManager managerAsset = new AssetManager();

    // ref para gerar os sprites dos personagens e etc
    public TextureRegion texturaChao;

    public Sound somPulo;
    public Sound somDano;

    public void carregaTodosAssets() {
        managerAsset.load("fontes/dano.wav", Sound.class);
        managerAsset.load("fontes/pulo.wav", Sound.class);

        managerAsset.load("textures/concretoChao.jpg", TextureRegion.class);

        managerAsset.finishLoading();

        somPulo = managerAsset.get("fontes/pulo.wav", Sound.class);
        somDano = managerAsset.get("fontes/dano.wav", Sound.class);
        texturaChao = managerAsset.get("textures/concretoChao.jpg", TextureRegion.class);
    }

    public void limparAssets() {
        managerAsset.dispose();
    }
}
