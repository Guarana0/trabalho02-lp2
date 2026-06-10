package mapa;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import mapa.obstaculos.Missil;
import mapa.tiles.ConcretoTile;
import mapa.tiles.FogoTile;
import mapa.tiles.GramaTile;
import mapa.tiles.MoedaTile;
import mapa.tiles.NeveTile; // Import do seu míssil ajustado
import objetos.ObjetoDeJogo;

public class GeradorCenario {
    private final Array<ObjetoDeJogo> objetosAtivos; 
    private float proximoX = 0;
    private float proximoXMoeda = 200f; 
    private final float TAMANHO_TILE = 64f;

    private TipoBioma biomaAtual;
    private int blocosGeradosNoBiomaAtual = 0;
    private final int DURACAO_DO_BIOMA = 50; 

    private final TextureRegion texConcreto;
    private final TextureRegion texFogo;
    private final TextureRegion texNeve;
    private final TextureRegion texGrama;
    private final TextureRegion texMoeda;
    private final TextureRegion texMissil; 


    public GeradorCenario(TextureRegion concreto, TextureRegion fogo, TextureRegion neve, TextureRegion grama, TextureRegion moeda, TextureRegion missil) {
        this.objetosAtivos = new Array<>();
        this.biomaAtual = TipoBioma.CONCRETO;
        
        this.texConcreto = concreto;
        this.texFogo = fogo;
        this.texNeve = neve;
        this.texGrama = grama;
        this.texMoeda = moeda;
        this.texMissil = missil;
        
        for (int i = 0; i < 30; i++) {
            gerarProximoBloco();
        }
    }

    private void gerarProximoBloco() {
        if (blocosGeradosNoBiomaAtual >= DURACAO_DO_BIOMA) {
            mudarDeBiomaAleatoriamente();
        }

        ObjetoDeJogo novoChao;
        
        String nomeBioma = biomaAtual.name();
        if (nomeBioma.equals("FLORESTA") || nomeBioma.equals("GRAMA")) {
            novoChao = new GramaTile(texGrama, proximoX, 0);
        } else if (nomeBioma.equals("FOGO")) {
            novoChao = new FogoTile(texFogo, proximoX, 0);
        } else if (nomeBioma.equals("NEVE")) {
            novoChao = new NeveTile(texNeve, proximoX, 0);
        } else {
            novoChao = new ConcretoTile(texConcreto, proximoX, 0);
        }

        objetosAtivos.add(novoChao);
        blocosGeradosNoBiomaAtual++; 

        boolean gerouMoeda = false;

        if (proximoX >= proximoXMoeda) {
            if (MathUtils.randomBoolean(0.12f)) {
                gerouMoeda = true;
                float alturaBaseMoeda = 300f; 
                if (nomeBioma.equals("FOGO")) alturaBaseMoeda = 320f;
                if (nomeBioma.equals("FLORESTA") || nomeBioma.equals("GRAMA")) alturaBaseMoeda = 300f;
                if (nomeBioma.equals("NEVE")) alturaBaseMoeda = 220f;

                int colunas = MathUtils.random(2, 10); 
                int linhas = MathUtils.random(1, 4);   

                float espacamentoX = 45f; 
                float espacamentoY = 45f;

                for (int l = 0; l < linhas; l++) {
                    for (int c = 0; c < colunas; c++) {
                        float posXMoeda = proximoX + (c * espacamentoX);
                        float posYMoeda = alturaBaseMoeda + (l * espacamentoY);

                        ObjetoDeJogo novaMoeda = new MoedaTile(texMoeda, posXMoeda, posYMoeda);
                        objetosAtivos.add(novaMoeda);
                    }
                }

                float fimDaFileiraX = proximoX + (colunas * espacamentoX);
                proximoXMoeda = fimDaFileiraX + 300f;

            } else {
                proximoXMoeda = proximoX + TAMANHO_TILE;
            }
        }

        if (!gerouMoeda && MathUtils.randomBoolean(0.13f) && proximoX > (20 * TAMANHO_TILE)) {
            float alturaMissil = MathUtils.random(100f, 250f);
            
            Missil novoMissil = new Missil(texMissil, proximoX, alturaMissil);
            objetosAtivos.add(novoMissil);
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

    public void atualizar(float jogadorX, float delta) {
        while (jogadorX > proximoX - 1500) { 
            gerarProximoBloco();
        }

        for (int i = objetosAtivos.size - 1; i >= 0; i--) {
            ObjetoDeJogo obj = objetosAtivos.get(i);
            
            if (obj instanceof Missil) {
                Missil m = (Missil) obj;
                m.getPosicao().x -= m.getVelocidade() * delta; 
                
                m.atualizarSeno(delta); 
            }

            if (obj.getPosicao().x < jogadorX - 300) {
                objetosAtivos.removeIndex(i);
            }
        }
    }

    public void renderizar(SpriteBatch batch, float jogadorX) {
        for (ObjetoDeJogo obj : objetosAtivos) {
            float largura = TAMANHO_TILE;
            float altura = TAMANHO_TILE;

            if (obj instanceof MoedaTile) {
                largura = 32f;
                altura = 32f;
            }

            float posXNaTela = obj.getPosicao().x - jogadorX + 100f;
            batch.draw(obj.getTextura(), posXNaTela, obj.getPosicao().y, largura, altura);
        }
    }

    public Array<ObjetoDeJogo> getObjetosAtivos() {
        return objetosAtivos;
    }

    public TipoBioma getBiomaSobOJogador(float jogadorX) {
        for (ObjetoDeJogo obj : objetosAtivos) {
            if (!(obj instanceof MoedaTile) && !(obj instanceof Missil)) {
                float limiteEsquerdo = obj.getPosicao().x;
                float limiteDireito = limiteEsquerdo + TAMANHO_TILE;

                if (jogadorX >= limiteEsquerdo && jogadorX <= limiteDireito) {
                    String nomeClasse = obj.getClass().getSimpleName();
                    
                    if (nomeClasse.equals("GramaTile")) {
                        return TipoBioma.FLORESTA;
                    } else if (nomeClasse.equals("FogoTile")) {
                        return TipoBioma.FOGO;
                    } else if (nomeClasse.equals("NeveTile")) {
                        return TipoBioma.NEVE;
                    } else if (nomeClasse.equals("ConcretoTile")) {
                        return TipoBioma.CONCRETO;
                    }
                }
            }
        }
        return biomaAtual;
    }
}