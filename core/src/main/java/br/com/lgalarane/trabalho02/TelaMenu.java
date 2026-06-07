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

public class TelaMenu extends ScreenAdapter {
    private final Game jogo;
    private SpriteBatch lote;
    private ShapeRenderer forma;
    private BitmapFont fonteTitulo;
    private BitmapFont fonteTexto;
    private GlyphLayout layout;
    private float tempoPulsante;

    public TelaMenu(Game jogo) {
        this.jogo = jogo;
    }

    @Override
    public void show() {
        lote = new SpriteBatch();
        forma = new ShapeRenderer();

        fonteTitulo = new BitmapFont();
        fonteTitulo.getData().setScale(2.4f);
        fonteTitulo.setColor(Color.BLACK);

        fonteTexto = new BitmapFont();
        fonteTexto.getData().setScale(1.05f);
        fonteTexto.setColor(Color.BLACK);

        layout = new GlyphLayout();
    }

    @Override
    public void render(float delta) {
        tempoPulsante += delta;
        ScreenUtils.clear(0.03f, 0.08f, 0.18f, 1f);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.justTouched()) {
            jogo.setScreen(new JogoScreen(jogo));
            return;
        }

        float largura = Gdx.graphics.getWidth();
        float altura = Gdx.graphics.getHeight();
        float painelLargura = largura * 0.74f;
        float painelAltura = 250f;
        float painelX = (largura - painelLargura) / 2f;
        float painelY = (altura - painelAltura) / 2f;

        // Fundo escuro com elementos inspirados em mapa
        forma.begin(ShapeType.Filled);
        forma.setColor(0.04f, 0.10f, 0.22f, 1f);
        forma.rect(0, 0, largura, altura);

        forma.setColor(0.08f, 0.18f, 0.34f, 1f);
        forma.circle(largura * 0.18f, altura * 0.78f, 110f);
        forma.circle(largura * 0.82f, altura * 0.24f, 70f);
        forma.circle(largura * 0.62f, altura * 0.70f, 50f);

        forma.setColor(0.16f, 0.28f, 0.50f, 1f);
        forma.rect(painelX, painelY, painelLargura, painelAltura);
        forma.setColor(0.24f, 0.44f, 0.72f, 1f);
        forma.rect(painelX, painelY + painelAltura - 52f, painelLargura, 52f);

        forma.setColor(1f, 1f, 1f, 0.12f);
        forma.rect(painelX + 18f, painelY + 18f, painelLargura - 36f, painelAltura - 36f);

        forma.setColor(0.14f, 0.30f, 0.56f, 1f);
        forma.rect(painelX, painelY, painelLargura, 4f);
        forma.rect(painelX, painelY + painelAltura - 4f, painelLargura, 4f);
        forma.rect(painelX, painelY, 4f, painelAltura);
        forma.rect(painelX + painelLargura - 4f, painelY, 4f, painelAltura);
        forma.end();

        float brilho = 0.74f + 0.16f * MathUtils.sin(tempoPulsante * 2.7f);
        Color acento = new Color(0.55f, 0.85f, 1f, brilho);

        String titulo = "MENU DO NOSSO JOGO";
        String subtitulo = "AINDA VAMOS DEFINIR UMA LEGENDA PRO SUBTITULO :)";
        String instrucoes = "PRESSIONE ENTER OU TOQUE PARA INICIAR";

        lote.begin();
        layout.setText(fonteTitulo, titulo);
        float tituloX = (largura - layout.width) / 2f;
        float tituloY = painelY + painelAltura - 16f;

        fonteTitulo.setColor(new Color(0.12f, 0.20f, 0.30f, 0.5f));
        fonteTitulo.draw(lote, titulo, tituloX + 4f, tituloY - 4f);
        fonteTitulo.setColor(Color.BLACK);
        fonteTitulo.draw(lote, titulo, tituloX, tituloY);

        fonteTexto.setColor(Color.BLACK);
        layout.setText(fonteTexto, subtitulo);
        float subtituloX = (largura - layout.width) / 2f;
        fonteTexto.draw(lote, subtitulo, subtituloX, painelY + painelAltura - 76f);

        fonteTexto.setColor(new Color(0.10f, 0.28f, 0.46f, 1f));
        layout.setText(fonteTexto, instrucoes);
        float instrucoesX = (largura - layout.width) / 2f;
        fonteTexto.draw(lote, instrucoes, instrucoesX, painelY + 54f);
        lote.end();
    }

    @Override
    public void dispose() {
        if (lote != null) {
            lote.dispose();
        }
        if (forma != null) {
            forma.dispose();
        }
        if (fonteTitulo != null) {
            fonteTitulo.dispose();
        }
        if (fonteTexto != null) {
            fonteTexto.dispose();
        }
    }
}
