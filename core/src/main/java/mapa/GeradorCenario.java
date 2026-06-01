package mapa;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import mapa.tiles.Tile;
import mapa.tiles.TileFactory;

public class GeradorCenario {
    private final Array<Tile> tilesAtivos;
    private float proximoX = 0;
    private final float TAMANHO_TILE = 64f;

    private TipoBioma biomaAtual;
    private int blocosGeradosNoBiomaAtual = 0;
    private final int DURACAO_DO_BIOMA = 50; 

    // IDs da TileFactory
    private final int ID_CONCRETO  = 1;
    private final int ID_MOEDA     = 2;
    private final int ID_FOGO      = 3;
    private final int ID_NEVE      = 4;
    private final int ID_GRAMA     = 5; 

    public GeradorCenario() {
        this.tilesAtivos = new Array<>();
        this.biomaAtual = TipoBioma.CONCRETO; // começa no concreto
        
        for (int i = 0; i < 30; i++) {
            gerarProximoBloco();
        }
    }

    private void gerarProximoBloco() {
        if (blocosGeradosNoBiomaAtual >= DURACAO_DO_BIOMA) {
            mudarDeBiomaAleatoriamente();
        }

        // selecao de tiles por bioma
        int idChaoParaCriar;
        switch (biomaAtual) {
            case FLORESTA:
                idChaoParaCriar = ID_GRAMA; 
                break;
            case FOGO:
                idChaoParaCriar = ID_FOGO;
                break;
            case NEVE:
                idChaoParaCriar = ID_NEVE;
                break;
            case CONCRETO:
            default:
                idChaoParaCriar = ID_CONCRETO;
                break;
        }

        Tile novoChao = TileFactory.createTile(idChaoParaCriar);
        novoChao.setPosicao(proximoX, 0);
        tilesAtivos.add(novoChao);
        
        blocosGeradosNoBiomaAtual++; 

        //lLógica de Moedas
        if (MathUtils.randomBoolean(0.12f)) {
            float alturaMoeda = 300f; 
            if (biomaAtual == TipoBioma.FOGO) alturaMoeda = 420f;
            if (biomaAtual == TipoBioma.FLORESTA) alturaMoeda = 350f; 
            if (biomaAtual == TipoBioma.NEVE) alturaMoeda = 220f;

            for (int i = 0; i < 3; i++) {
                Tile novaMoeda = TileFactory.createTile(ID_MOEDA);
                novaMoeda.setPosicao(proximoX + (i * 35f), alturaMoeda);
                tilesAtivos.add(novaMoeda);
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

        for (int i = tilesAtivos.size - 1; i >= 0; i--) {
            Tile tile = tilesAtivos.get(i);
            if (tile.getPosicao().x < jogadorX - 300) {
                tilesAtivos.removeIndex(i);
            }
        }
    }

    public void renderizar(SpriteBatch batch) {
        for (Tile tile : tilesAtivos) {
            float tamanho = (tile.getPosicao().y > 0 && tile.ehAndavel() == false) ? 32f : TAMANHO_TILE;
            batch.draw(tile.getTextura(), tile.getPosicao().x, tile.getPosicao().y, tamanho, tamanho);
        }
    }

    public Array<Tile> getTilesAtivos() { return tilesAtivos; }
}