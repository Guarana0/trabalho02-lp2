package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mapa.TipoBioma;

public class FundoFogo extends PlanosDeFundo{

    public FundoFogo(TextureRegion textura) {
        super(textura);
    }

    @Override
    public int getidBioma() {
        return TipoBioma.FOGO.ordinal();
    }  
}
