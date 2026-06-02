package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mapa.TipoBioma;

public class FundoNeve extends PlanosDeFundo{

    public FundoNeve(TextureRegion textura) {
        super(textura);
    }

    @Override
    public int getidBioma() {
        return TipoBioma.NEVE.ordinal();
    } 
}
