package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class MorteScreen extends ScreenAdapter {
    private final Game jogo;
    private SpriteBatch lote;
    private ShapeRenderer forma;
    private BitmapFont fonteTitulo, fonteTexto;
    private GlyphLayout layout;
    private float tempo;

    private GameAssets assets;

    public MorteScreen(Game jogo, GameAssets assets) {
        this.jogo = jogo;
        this.assets = assets;
    }

    @Override
    public void show() {
        lote = new SpriteBatch();
        forma = new ShapeRenderer();
        layout = new GlyphLayout();

        fonteTitulo = new BitmapFont();
        fonteTitulo.getData().setScale(3.0f);
        fonteTexto = new BitmapFont();
        fonteTexto.getData().setScale(1.2f);
    }

    @Override
    public void render(float delta) {
        tempo += delta;

        ScreenUtils.clear(0.15f, 0.02f, 0.02f, 1f);

        if (Gdx.input.isTouched()) {
            assets.musicaMorte.stop();        
            jogo.setScreen(new JogoScreen(jogo));
            return;        
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            assets.musicaMorte.stop();
            jogo.setScreen(new TelaMenu(jogo, assets));
            return;
        }

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        forma.begin(ShapeType.Filled);
        forma.rect(0, 0, w, h, 
                new Color(0.2f, 0.02f, 0.02f, 1), new Color(0.2f, 0.02f, 0.02f, 1),
                new Color(0.4f, 0.05f, 0.05f, 1), new Color(0.4f, 0.05f, 0.05f, 1));

        float pW = w * 0.6f, pH = 200f;
        float pX = (w - pW) / 2f, pY = (h - pH) / 2f;

        forma.setColor(0.3f, 0.05f, 0.05f, 0.8f);
        forma.rect(pX, pY, pW, pH); 
        forma.setColor(0.6f, 0.2f, 0.2f, 0.4f);
        forma.rect(pX, pY + pH - 5, pW, 5);
        forma.end();

        lote.begin();
        String titulo = "VOCÊ MORREU!";
        String instrucao = "CLIQUE PARA JOGAR DE NOVO OU ENTER PARA O MENU";

        float alpha = 0.5f + 0.5f * MathUtils.sin(tempo * 3f);

        desenharTextoSombreado(titulo, fonteTitulo, h * 0.6f, Color.WHITE, Color.BLACK);
        desenharTextoSombreado(instrucao, fonteTexto, h * 0.4f, new Color(1, 1, 1, alpha), Color.BLACK);

        lote.end();
    }

    private void desenharTextoSombreado(String texto, BitmapFont fonte, float y, Color cor, Color sombra) {
        layout.setText(fonte, texto);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2f;
        fonte.setColor(sombra);
        fonte.draw(lote, texto, x + 2, y - 2); 
        fonte.setColor(cor);
        fonte.draw(lote, texto, x, y); 
    }

    @Override
    public void dispose() {
        lote.dispose();
        forma.dispose();
        fonteTitulo.dispose();
        fonteTexto.dispose();
    }
}