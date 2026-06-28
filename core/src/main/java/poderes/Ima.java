package poderes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import mapa.tiles.MoedaTile;
import objetos.ObjetoDeJogo;
import personagem.PersonagemPrincipal;

public class Ima extends Poder {
    private final TextureRegion textura;

    public Ima() {
        this(null);
        this.estaAtivo = false;
    }

    public Ima(TextureRegion textura) {
        this.textura = textura;
        this.estaAtivo = false;
        this.areaItem.set(0, 0, 32f, 32f);
    }

    public void atualizar(float delta, PersonagemPrincipal personagem, Array<ObjetoDeJogo> objetos, float jogadorMundoX) {
        if (!estaAtivo) {
            personagem.setImaAtivo(false);
            return;
        }

        tempoPoder -= delta;
        if (tempoPoder <= 0) {
            tempoPoder = 0;
            estaAtivo = false;
            personagem.setImaAtivo(false);
            return;
        }

        atrairMoedas(personagem, objetos, delta, jogadorMundoX);
        personagem.setImaAtivo(true);
    }

    private void atrairMoedas(PersonagemPrincipal personagem, Array<ObjetoDeJogo> objetos, float delta, float jogadorMundoX) {
        Vector2 posJogadorMundo = new Vector2(jogadorMundoX, personagem.getPosicao().y);
        Vector2 direcao = new Vector2();

        for (ObjetoDeJogo obj : objetos) {
            if (!(obj instanceof MoedaTile))
                continue;

            float distancia = posJogadorMundo.dst(obj.getPosicao());
            if (distancia < 250f) {
                direcao.set(posJogadorMundo).sub(obj.getPosicao()).nor();
                obj.getPosicao().mulAdd(direcao, 600f * delta);
            }
        }
    }

    public void renderizar(SpriteBatch batch, float x, float y, float largura, float altura) {
        if (estaAtivo && textura != null) {
            batch.draw(textura, x, y, largura, altura);
        }
    }

    public void renderizarItem(SpriteBatch batch, float x, float y) {
        if (textura != null) {
            batch.draw(textura, x, y, 32f, 32f);
        }
    }
}
