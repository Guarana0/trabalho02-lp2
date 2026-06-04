package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;

import mapa.tiles.ConcretoTile;
import personagem.PersonagemPrincipal;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapes;
    private BitmapFont font;
    private PersonagemPrincipal personagem;

    private Texture texturaChao;
    private ConcretoTile chao;
    private Rectangle texturaPersonagem;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapes = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        personagem = new PersonagemPrincipal(0, 0, 50, 50, null);
        texturaChao = new Texture("textures/concretoChao.jpg");
        chao = new ConcretoTile(new TextureRegion(texturaChao), 0, 0);
        texturaPersonagem = new Rectangle(10, 10, 50, 50);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        float larguraTela = Gdx.graphics.getWidth();
        float deltaTempo = Gdx.graphics.getDeltaTime();

        personagem.atualizar(deltaTempo);
        texturaPersonagem.setPosition(personagem.getPosicao().x, personagem.getPosicao().y);

        batch.begin();
        batch.draw(chao.getTextura(), chao.getPosicao().x, chao.getPosicao().y, larguraTela, 100f);
        int distancia = (int) personagem.getDistanciaPercorrida();
        // Aquie onde eu de fato exibo a distancia percorrida na tela
        font.draw(batch, "Distância: " + distancia + "M", 10, Gdx.graphics.getHeight() - 10);
        batch.end();

        shapes.begin(ShapeRenderer.ShapeType.Filled);
        shapes.setColor(Color.BLUE);
        shapes.rect(texturaPersonagem.x, texturaPersonagem.y, texturaPersonagem.width, texturaPersonagem.height);
        shapes.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapes.dispose();
        font.dispose();
        texturaChao.dispose();
    }
}
