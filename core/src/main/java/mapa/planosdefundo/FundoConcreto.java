package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mapa.TipoBioma;

public class FundoConcreto extends PlanosDeFundo{

    public FundoConcreto(TextureRegion textura) {
        super(textura);
    }

    @Override
    public int getidBioma() {
        return TipoBioma.CONCRETO.ordinal();
    }
}
