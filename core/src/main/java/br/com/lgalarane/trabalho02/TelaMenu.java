package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class TelaMenu extends ScreenAdapter {
    private final Game jogo;
    private SpriteBatch lote;
    private BitmapFont fonte;

    public TelaMenu(Game jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        // Criando o básico pra desenhar texto na tela
        lote = new SpriteBatch();
        fonte = new BitmapFont();
        fonte.setColor(Color.WHITE);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Se o jogador apertar enter ou tocar na tela, começa o jogo
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) {
            jogo.setScreen(new JogoScreen(jogo));
            return; // faz a troca de tela
        }

        // isso aq desenha o titulo e a instrucao do menu
        lote.begin();
        int y = Gdx.graphics.getHeight() - 100;
        fonte.draw(lote, "MENU DO NOSSO JOGO", 50, y);
        fonte.draw(lote, "CLIQUE NO ENTER OU TOQUE: Iniciar", 50, y - 40);
        lote.end();
    }

    @Override
    public void dispose() {
        // Descartando recursos de renderização do menu.
        if (lote != null)
            lote.dispose();
        if (fonte != null)
            fonte.dispose();
    }
}
