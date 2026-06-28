package poderes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import personagem.PersonagemPrincipal;

public class Escudo extends Poder {
    private Rectangle tamanhoEscudo;
    private final TextureRegion textura;

    public Escudo() {
        this(null);
        this.estaAtivo = false;

        // criar funcao que define onde o item do escudo vai nascer no mapa e seu
        // tamanho
    }

    public Escudo(TextureRegion textura) {
        this.textura = textura;
        this.estaAtivo = false;
        this.areaItem.set(0, 0, 32f, 32f);
    }

    public void atualizar(float deltaTempo, PersonagemPrincipal personagem) {
        // Se o escudo nao está ativo, checamos se o personagem colidiu com o item
        if (!this.estaAtivo) {
            if (verificarColisao(personagem)) {
                this.estaAtivo = true;
                this.tempoPoder = 10f;

                // Apaga o item para o jogador nao pegar mesmo item duas vezes
                this.areaItem.set(0, 0, 0, 0);
            }
        }

        // Se o escudo esta ativo
        if (this.estaAtivo) {
            tempoPoder -= deltaTempo;

            // Faz o retângulo do escudo seguir exatamente a posição e tamanho do personagem
            this.tamanhoEscudo = personagem.getColisao();

            // Se o tempo acabar
            if (tempoPoder <= 0f) {
                tempoPoder = 0f;
                this.estaAtivo = false;
            }
        }

        // Aplica o estado diretamente no personagem (principal)
        personagem.setProtegidoPorEscudo(this.estaAtivo);
    }

    public Rectangle getTamanhoEscudo() {
        return tamanhoEscudo;
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