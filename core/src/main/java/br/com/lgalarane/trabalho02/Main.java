package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.Game;

public class Main extends Game {
    public GameAssets assets;

    @Override
    public void create() {
        assets = new GameAssets();
        assets.carregaTodosAssets();

        setScreen(new TelaMenu(this, assets));
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        if (assets != null) {
            assets.limparAssets(); 
        }
        super.dispose();
    }
}