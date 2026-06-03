package mapa;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import mapa.tiles.ConcretoTile;
import mapa.tiles.FogoTile;
import mapa.tiles.GramaTile;
import mapa.tiles.MoedaTile;
import mapa.tiles.NeveTile;
import objetos.ObjetoDeJogo;

public class GeradorCenario {
    private final Array<ObjetoDeJogo> objetosAtivos; 
    private float proximoX = 0;
    private final float TAMANHO_TILE = 64f;

    private TipoBioma biomaAtual;
    private int blocosGeradosNoBiomaAtual = 0;
    private final int DURACAO_DO_BIOMA = 50; 

    private final TextureRegion texConcreto;
    private final TextureRegion texFogo;
    private final TextureRegion texNeve;
    private final TextureRegion texGrama;
    private final TextureRegion texMoeda;

    public GeradorCenario(TextureRegion concreto, TextureRegion fogo, TextureRegion neve, TextureRegion grama, TextureRegion moeda) {
        this.objetosAtivos = new Array<>();
        this.biomaAtual = TipoBioma.CONCRETO;
        
        this.texConcreto = concreto;
        this.texFogo = fogo;
        this.texNeve = neve;
        this.texGrama = grama;
        this.texMoeda = moeda;
        
        // gera o inicio
        for (int i = 0; i < 30; i++) {
            gerarProximoBloco();
        }
    }

    private void gerarProximoBloco() {
        if (blocosGeradosNoBiomaAtual >= DURACAO_DO_BIOMA) {
            mudarDeBiomaAleatoriamente();
        }

        ObjetoDeJogo novoChao;
        
        switch (biomaAtual) {
            case FLORESTA: 
                novoChao = new GramaTile(texGrama, proximoX, 0);
                break;
            case FOGO: 
                novoChao = new FogoTile(texFogo, proximoX, 0);
                break;
            case NEVE: 
                novoChao = new NeveTile(texNeve, proximoX, 0);
                break;
            case CONCRETO:
            default: 
                novoChao = new ConcretoTile(texConcreto, proximoX, 0);
                break;
        }

        objetosAtivos.add(novoChao);
        blocosGeradosNoBiomaAtual++; 

        // moedas
        if (MathUtils.randomBoolean(0.12f)) {
            float alturaMoeda = 300f; 
            if (biomaAtual == TipoBioma.FOGO) alturaMoeda = 420f;
            if (biomaAtual == TipoBioma.FLORESTA) alturaMoeda = 350f;
            if (biomaAtual == TipoBioma.NEVE) alturaMoeda = 220f;

            for (int i = 0; i < 3; i++) {
                ObjetoDeJogo novaMoeda = new MoedaTile(texMoeda, proximoX + (i * 35f), alturaMoeda);
                objetosAtivos.add(novaMoeda);
            }
        }

        proximoX += TAMANHO_TILE;
    }

    private void mudarDeBiomaAleatoriamente() {
        blocosGeradosNoBiomaAtual = 0;
        TipoBioma novoBioma;
        do {
            int sorteio = MathUtils.random(0, TipoBioma.values().length - 1);
            novoBioma = TipoBioma.values()[sorteio];
        } while (novoBioma == biomaAtual);
        
        biomaAtual = novoBioma;
    }

    public void atualizar(float jogadorX) {
        if (jogadorX > proximoX - 1500) { 
            for (int i = 0; i < 10; i++) {
                gerarProximoBloco();
            }
        }

        for (int i = objetosAtivos.size - 1; i >= 0; i--) {
            ObjetoDeJogo obj = objetosAtivos.get(i);
            if (obj.getPosicao().x < jogadorX - 300) {
                objetosAtivos.removeIndex(i);
            }
        }
    }

    public void renderizar(SpriteBatch batch) {
        for (ObjetoDeJogo obj : objetosAtivos) {
            float tamanho = (obj instanceof MoedaTile) ? 32f : TAMANHO_TILE;
            batch.draw(obj.getTextura(), obj.getPosicao().x, obj.getPosicao().y, tamanho, tamanho);
        }
    }

    public Array<ObjetoDeJogo> getObjetosAtivos() {
        return objetosAtivos;
    }
}