package personagem;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Personagem {
	protected int vida;
	protected boolean estaVivo;

	// tamanho real de todos os personagens ao longo do jogo (sem considerar armas)
	protected Vector2 dimensoes;

	// criação das variaveis de atributos variavies 
	protected Vector2 velocidade;
	protected Vector2 posicao;
	protected Rectangle colisao;

	protected Sound somDano;

	public Personagem(float x, float y, float altura, float largura, Sound somDano) {
		this.dimensoes = new Vector2(largura, altura);
		this.posicao = new Vector2(x, y);
		this.velocidade = new Vector2(0, 0);
		this.colisao = new Rectangle(x, y, largura, altura);
		this.estaVivo = true;
		this.somDano = somDano;
	}

}