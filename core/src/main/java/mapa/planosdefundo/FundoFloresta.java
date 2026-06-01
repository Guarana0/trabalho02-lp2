package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mapa.TipoBioma;

public class FundoFloresta extends PlanosDeFundo{
    public FundoFloresta(TextureRegion textura) {
        super(textura);
    }

    @Override
    public int getidBioma() {
        return TipoBioma.FLORESTA.ordinal();
    }
}
