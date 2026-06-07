package personagem;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.lang.ref.Cleaner;

public abstract class Personagem {
    protected int vida;
    protected boolean estaVivo;
    protected int dano;

    // tudo relacionado ao clean foi implementado com ia 
    protected static final Cleaner cleaner = Cleaner.create();
    private Cleaner.Cleanable cleanable;

    protected Vector2 dimensoes;
    protected Vector2 velocidade;
    protected Vector2 posicao;
    protected Rectangle areaColisao;

    protected Sound somDano;
    protected Texture textura;

    // USO DE IA PARA O CLEAN
    private static class EstadoLimpeza implements Runnable {
        private final Texture tex;
        private final Sound som;

        public EstadoLimpeza(Texture tex, Sound som) {
            this.tex = tex;
            this.som = som;
        }

        @Override
        public void run() {
            if (tex != null) {
                tex.dispose();
            }
            if (som != null) {
                som.dispose();
            }
        }
    }

    public Personagem(float x, float y, float largura, float altura, Sound somDano) {
        this.dimensoes = new Vector2(largura, altura);
        this.posicao = new Vector2(x, y);
        this.velocidade = new Vector2(0, 0);
        this.areaColisao = new Rectangle(x, y, largura, altura);
        this.estaVivo = true;
        this.somDano = somDano;
    }

    // USO DE IA 
    protected void inicializarLimpeza() {
        this.cleanable = cleaner.register(this, new EstadoLimpeza(this.textura, this.somDano));
    }

    // USO DE IA 
    public void destruir() {
        if (cleanable != null) {
            cleanable.clean();
        }
    }

    public Texture gettextura() {
        return this.textura;
    }

    public Vector2 getPosicao() {
        return posicao;
    }

    public Vector2 getDimensoes() {
        return dimensoes;
    }

    public Rectangle getColisao() {
        return areaColisao;
    }

    public int getDano() {
        return dano;
    }

    public boolean getAtivo() {
        return estaVivo;
    }

    public int darDano(int dano) {
        this.vida -= dano;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return this.vida;
    }

    public int getVida() {
        return vida;
    }

    public int tomarDano(int danoRecebido) {
        if (danoRecebido <= 0) {
            return this.vida;
        }

        this.vida -= danoRecebido;
        if (this.vida < 0) {
            this.vida = 0;
        }
        return this.vida;
    }
}
