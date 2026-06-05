package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.Game;

public class Main extends Game {
    @Override
    public void create() {
        // Aqui eu inicio o jogo mostrando primeiro o menu
        // O main agora vai passar a gerenciar as telas e trocar entre elas conforme o
        // neceesario
        setScreen(new TelaMenu(this));
    }

    @Override
    public void dispose() {
        // Limpa a tela atual antes de fechar o jogo
        if (getScreen() != null)
            getScreen().dispose();
        super.dispose();
    }
}
