package mapa;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class GeradorCenario {
    private final Array<Tile> tilesAtivos; 
    private float proximoX = 0;            
    private final float TAMANHO_TILE = 64f; 

    // IDs registrados na TileFactory
    private final int ID_GRAMA = 1;
    private final int ID_MOEDA = 2; 

    public GeradorCenario() {
        this.tilesAtivos = new Array<>();
        
        for (int i = 0; i < 30; i++) {
            gerarProximoBloco();
        }
    }

    private void gerarProximoBloco() {
        //gera a grama no chão
        Tile novoChao = TileFactory.createTile(ID_GRAMA);
        novoChao.setPosicao(proximoX, 0);
        tilesAtivos.add(novoChao);

        // toda vez que avançamos, temos 10% de chance de iniciar uma fileira de moedas no alto
        if (MathUtils.randomBoolean(0.10f)) {
            float alturaDaMoeda = MathUtils.random(150f, 450f); // altura aleatoria para o jogador voar até elas
            
            // cria uma fileira horizontal de moedas de tamanho aleatorio (entre um e sete)
            for (int i = 0; i < MathUtils.random(1, 7); i++) {
                Tile novaMoeda = TileFactory.createTile(ID_MOEDA);
                
                // posiciona cada moeda um pouco pra frente 
                novaMoeda.setPosicao(proximoX + (i * 40f), alturaDaMoeda);
                
                tilesAtivos.add(novaMoeda);
            }
        }

        proximoX += TAMANHO_TILE;
    }

    public void atualizar(float jogadorX) {
        if (jogadorX > proximoX - 1500) { 
            for (int i = 0; i < 10; i++) {
                gerarProximoBloco();
            }
        }

        // limpeza de memória (remove moedas coletadas ou que saíram da tela)
        for (int i = tilesAtivos.size - 1; i >= 0; i--) {
            Tile tile = tilesAtivos.get(i);
            if (tile.getPosicao().x < jogadorX - 300) {
                tilesAtivos.removeIndex(i);
            }
        }
    }

    public void renderizar(SpriteBatch batch) {
        for (Tile tile : tilesAtivos) {
            batch.draw(
                tile.getTextura(), 
                tile.getPosicao().x, 
                tile.getPosicao().y, 
                // Se for moeda, podemos desenhar ela com um tamanho menor
                tile instanceof Moeda ? 32f : TAMANHO_TILE, 
                tile instanceof Moeda ? 32f : TAMANHO_TILE
            );
        }
    }

    public Array<Tile> getTilesAtivos() {
        return tilesAtivos;
    }
}