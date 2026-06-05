package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

public class JogoScreen implements Screen {
    private final Game game;
    private final Jogo jogo;

    public JogoScreen(Game game) {
        // isso aq mantem a classe Jogo original e delega chamada às suas
        // funções assim agente nao mexe na lógica do jogo em si
        this.game = game;
        this.jogo = new Jogo();
    }

    @Override
    public void show() {
        // Quando a tela é mostrada inicializa o jogo
        jogo.create();
    }

    @Override
    public void render(float delta) {
        // Só repassa para a classe Jogo fazer o render real
        jogo.render();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        jogo.pause();
    }

    @Override
    public void resume() {
        jogo.resume();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        // Limpa recursos do jogo quando a tela for descartada
        jogo.dispose();
    }
}
