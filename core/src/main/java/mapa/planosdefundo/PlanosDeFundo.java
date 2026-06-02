package mapa.planosdefundo;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class PlanosDeFundo {

    protected final TextureRegion texturaDeFundo;

    public PlanosDeFundo(TextureRegion texturaDeFundo) {
        this.texturaDeFundo = texturaDeFundo;
    }
    
    public void render(SpriteBatch batch, float largura, float altura){
        batch.draw(this.texturaDeFundo, 0, 0, largura, altura);
    }
    
    public abstract int getidBioma();
}
