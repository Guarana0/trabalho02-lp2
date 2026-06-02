import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAssets {
    private final AssetManager managerAsset = new AssetManager();

    // ref para gerar os sprites dos personagens e etc
    public TextureRegion texturaPlayer;

    public Sound somPulo;
    public Sound somDano;

    public void carregaTodosAssets() {
        managerAsset.load("fontes/dano.wav", Sound.class);
        managerAsset.load("fontes/pulo.wav", Sound.class);

        managerAsset.finishLoading();

        somPulo = managerAsset.get("fontes/pulo.wav", Sound.class);
        somDano = managerAsset.get("fontes/dano.wav", Sound.class);
    }

    public void limparAssets() {
        managerAsset.dispose();
    }
}
