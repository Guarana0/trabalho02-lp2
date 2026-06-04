package personagem;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public abstract class Personagem {
	protected int vida;
	protected boolean estaVivo;
	protected int dano;

	// tamanho real de todos os personagens ao longo do jogo (sem considerar armas)
	protected Vector2 dimensoes;

	// criação das variaveis de atributos variavies
	protected Vector2 velocidade;
	protected Vector2 posicao;
	protected Rectangle areaColisao;

	protected Sound somDano;
	protected Texture textura;

	public Personagem(float x, float y, float altura, float largura, Sound somDano) {
		this.dimensoes = new Vector2(largura, altura);
		this.posicao = new Vector2(x, y);
		this.velocidade = new Vector2(0, 0);
		this.areaColisao = new Rectangle(x, y, largura, altura);
		this.estaVivo = true;
		this.somDano = somDano;
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

	public int getVida() {
		return vida;
	}

	// diminui a vida do personagem
	public int tomarDano(int danoRecebido) {
		this.vida -= danoRecebido;

		if (this.vida < 0) {
			this.vida = 0;
		}

		return this.vida;
	}
}