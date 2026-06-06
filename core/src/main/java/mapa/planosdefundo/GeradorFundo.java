package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;

import mapa.TipoBioma;

public class GeradorFundo {
    private TextureRegion fundoGrama;
    private TextureRegion fundoFogo;
    private TextureRegion fundoNeve;
    private TextureRegion fundoConcreto;

    public GeradorFundo(TextureRegion fundoGrama, TextureRegion fundoFogo, TextureRegion fundoNeve, TextureRegion fundoConcreto) {
        this.fundoGrama = fundoGrama;
        this.fundoFogo = fundoFogo;
        this.fundoNeve = fundoNeve;
        this.fundoConcreto = fundoConcreto;
    }

    public void renderizar(SpriteBatch batch, TipoBioma bioma) {
        TextureRegion fundoParaDesenhar = fundoConcreto;

        if (bioma != null) {
            String nomeBioma = bioma.name();
            if (nomeBioma.equals("FLORESTA") || nomeBioma.equals("GRAMA")) {
                fundoParaDesenhar = fundoGrama;
            } else if (nomeBioma.equals("FOGO")) {
                fundoParaDesenhar = fundoFogo;
            } else if (nomeBioma.equals("NEVE")) {
                fundoParaDesenhar = fundoNeve;
            } else {
                fundoParaDesenhar = fundoConcreto;
            }
        }

        if (fundoParaDesenhar != null) {
            batch.draw(fundoParaDesenhar, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }
}