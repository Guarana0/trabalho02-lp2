package personagem.Inimigos;

import com.badlogic.gdx.audio.Sound;

import personagem.Inimigo;
public class Esqueleto extends Inimigo {

    public Esqueleto(float larguraMapa, float alturaMapa, float altura, float largura, Sound somDano) {
        super(larguraMapa, alturaMapa, largura, altura, somDano);
    }
}

