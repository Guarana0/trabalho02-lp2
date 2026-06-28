package br.com.lgalarane.trabalho02;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import mapa.GeradorCenario;
import mapa.TipoBioma;
import mapa.obstaculos.Explodivel;
import mapa.obstaculos.Missil;
import mapa.obstaculos.Obstaculo;
import mapa.obstaculos.Zapper;
import mapa.planosdefundo.GeradorFundo;
import mapa.tiles.MoedaTile;
import objetos.ObjetoDeJogo;
import objetos.Projetil;
import personagem.Inimigo;
import personagem.Inimigos.Corvo;
import personagem.Inimigos.Esqueleto;
import personagem.Inimigos.Goblin;
import personagem.PersonagemPrincipal;
import poderes.Escudo;
import poderes.Ima;
import poderes.Poder;

public class Jogo extends ApplicationAdapter {
    private final Game game;

    private SpriteBatch batch;
    private BitmapFont font;
    private GeradorCenario geradorCenario;
    private GeradorFundo geradorFundo;

    private GameAssets assets;
    private PersonagemPrincipal personagem;

    private ArrayList<Inimigo> listaInimigos;
    private ArrayList<Poder> listaPoderes;
    private ArrayList<Projetil> listaProjeteis;
    private Rectangle areaMoeda;

    private float posicaoMapaX = 0f;

    // Velocidade base do mapa
    private static final float VELOCIDADE_MAPA_BASE = 150f;

    // Teto para evitar valores absurdos.
    private static final float VELOCIDADE_MAPA_MAX = 900f;

    // Tempo base entre spawns de inimigos
    private static final float TEMPO_SPAWN_INIMIGO_BASE = 3f;
    private static final float TEMPO_SPAWN_INIMIGO_MIN = 0.8f;

    // Velocidade do jogo
    private static final float TEMPO_VELOCIDADE_MAX = 70f;

    private float tempoTotal = 0f;
    private float velocidadeMapaAtual = VELOCIDADE_MAPA_BASE;

    float larguraMundo;
    float alturaMundo;

    private float tempoDesdeUltimoInimigo = 0f;
    private float tempoDesdeUltimoPoder = 0f;
    private final float TEMPO_SPAWN_PODER = 8f;


    private Animation<TextureRegion> animacaoExplosao;
    private final ArrayList<EfeitoExplosao> explosoesAtivas = new ArrayList<>();
    private boolean iniciouAnimacaoMorte = false;

    private Missil ultimoMissilAvistado = null;

    private int abatesTotais = 0;

    private Music musicaPrincipalAtual = null;
    private boolean musicaPoderTocando = false;
    private boolean musicaInicialSorteada = false;

    private static class EfeitoExplosao {
        float x, y;
        float tempoDeVida = 0f;

        public EfeitoExplosao(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    public Jogo(Game game) {
        this.game = game;
    }

    @Override
    public void create() {
        larguraMundo = Gdx.graphics.getWidth();
        alturaMundo = Gdx.graphics.getHeight();

        listaInimigos = new ArrayList<>();
        listaPoderes = new ArrayList<>();
        listaProjeteis = new ArrayList<>();

        assets = new GameAssets();
        assets.carregaTodosAssets();

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        animacaoExplosao = new Animation<>(0.04f, assets.framesExplosao, Animation.PlayMode.NORMAL);

        geradorCenario = new GeradorCenario(
                assets.texRegConcreto,
                assets.texRegFogo,
                assets.texRegNeve,
                assets.texRegGrama,
                assets.texRegMoeda,
                assets.texRegMissil,
                assets.texRegZapper);

        geradorFundo = new GeradorFundo(
                assets.texRegFundoGrama,
                assets.texRegFundoFogo,
                assets.texRegFundoNeve,
                assets.texRegFundoConcreto);

        personagem = new PersonagemPrincipal(0f, 100f, 100f, 130f, assets.somDano, assets);
        areaMoeda = new Rectangle();

        assets.musica1.setVolume(1.2f);
        assets.musica2.setVolume(1.2f);

        assets.musica1.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                musicaPrincipalAtual = assets.musica2;
                assets.musica2.play();
            }
        });

        assets.musica2.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                musicaPrincipalAtual = assets.musica1;
                assets.musica1.play();
            }
        });

        if (MathUtils.randomBoolean(0.5f)) {
            musicaPrincipalAtual = assets.musica1;
        } else {
            musicaPrincipalAtual = assets.musica2;
        }

        musicaPrincipalAtual.play();
    }

    @Override
    public void render() { 
        float delta = Gdx.graphics.getDeltaTime(); //recebe o delta
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        if (personagem.getVida() > 0) {
            tempoTotal += delta;
            sortearMusicaInicial();
            velocidadeMapaAtual = getVelocidadeAtual();
            personagem.atualizar(delta, velocidadeMapaAtual);
            posicaoMapaX += velocidadeMapaAtual * delta;

            geradorCenario.atualizar(posicaoMapaX, delta);
            verificarColetaMoedas();
            verificarColisaoObstaculos();
            verificarColisaoInimigos();
            verificarInputTiro();
            verificarColisaoTiros();
            iniciaListaDeProjeteis(delta);
            tempoDesdeUltimoInimigo += delta;
            if (tempoDesdeUltimoInimigo >= getTempoSpawnInimigo()) {
                spawnInimigo();
                tempoDesdeUltimoInimigo = 0f;
            }
            iniciaListaDeInimigos(delta);
            ajustaPoderes(delta);
            verificaMusicas(delta); 
        }
        for (int i = explosoesAtivas.size() - 1; i >= 0; i--) {
            EfeitoExplosao exp = explosoesAtivas.get(i);
            exp.tempoDeVida += delta;
            if (animacaoExplosao.isAnimationFinished(exp.tempoDeVida)) {
                explosoesAtivas.remove(i);
            }
        }
        batch.begin();

        TipoBioma biomaParaDesenho = geradorCenario.getBiomaSobOJogador(posicaoMapaX);
        geradorFundo.renderizar(batch, biomaParaDesenho);
        geradorCenario.renderizar(batch, posicaoMapaX);

        for (EfeitoExplosao exp : explosoesAtivas) {
            TextureRegion frameAtual = animacaoExplosao.getKeyFrame(exp.tempoDeVida);
            batch.draw(frameAtual, exp.x, exp.y, 32f, 32f);
        }

        for (Projetil proj : listaProjeteis) {
            proj.renderizar(batch);
        }

        renderizaObjetos();
        renderizaPoderes(delta);
        renderizaHUD();

        batch.end();

        fimJogo(); 
    }

    private void sortearMusicaInicial(){
        if (!musicaInicialSorteada) {
            if (MathUtils.randomBoolean()) {
                tocarMusicaPrincipal(assets.musica1);
            } else {
                tocarMusicaPrincipal(assets.musica2);
            }
            musicaInicialSorteada = true;
        }
    }

    public void tocarMusicaPrincipal(Music novaMusica) {
        if (musicaPrincipalAtual == novaMusica && novaMusica.isPlaying()) {
            return; 
        }

        if (musicaPrincipalAtual != null) {
            musicaPrincipalAtual.stop();
        }

        musicaPrincipalAtual = novaMusica;
        musicaPrincipalAtual.setLooping(true);
        musicaPrincipalAtual.setVolume(0.4f);
        musicaPrincipalAtual.play();
    }

    private void ajustaPoderes(float delta){
        tempoDesdeUltimoPoder += delta;
        if (tempoDesdeUltimoPoder >= TEMPO_SPAWN_PODER) {
            spawnPoder();
            tempoDesdeUltimoPoder = 0f;
        }

        for (int i = listaPoderes.size() - 1; i >= 0; i--) {
            Poder poder = listaPoderes.get(i);
            if (poder instanceof Escudo escudo) {
                // Verifica colisão convertendo coordenadas do mundo para tela
                Rectangle areaItemTela = new Rectangle();
                areaItemTela.set(escudo.getAreaItem());
                areaItemTela.setPosition(
                        escudo.getAreaItem().x - posicaoMapaX + 100f,
                        escudo.getAreaItem().y);

                if (!escudo.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    escudo.setEstaAtivo(true);
                    escudo.getAreaItem().set(0, 0, 0, 0);
                    //da mais tiros
                    personagem.setQtdTiros(personagem.getQtdTiros() + 5);
                }
                escudo.atualizar(delta, personagem);
            } else if (poder instanceof Ima ima) {
                // Verifica colisão convertendo coordenadas do mundo para tela
                Rectangle areaItemTela = new Rectangle();
                areaItemTela.set(ima.getAreaItem());
                areaItemTela.setPosition(
                        ima.getAreaItem().x - posicaoMapaX + 100f,
                        ima.getAreaItem().y);

                if (!ima.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    ima.setEstaAtivo(true);
                    ima.getAreaItem().set(0, 0, 0, 0);
                }
                ima.atualizar(delta, personagem, geradorCenario.getObjetosAtivos(), posicaoMapaX);
            }

            if (!poder.estaAtivo() && poder.getTempoPoder() <= 0) {
                listaPoderes.remove(i);
            }
        }
    }

    private void renderizaObjetos(){
        if (personagem.getVida() > 0) {
            personagem.renderizar(batch);
        }

        for (Inimigo inimigo : listaInimigos) {
            float xTela = inimigo.getPosicao().x - posicaoMapaX + 100f;
            if (inimigo instanceof Esqueleto esqueleto) {
                esqueleto.renderizar(batch, xTela);
            } else if (inimigo instanceof Goblin goblin) {
                goblin.renderizar(batch, xTela);
            } else if (inimigo instanceof Corvo corvo) {
                corvo.renderizar(batch, xTela);
            }
        }

        // Renderiza os itens dos poderes no mapa (antes de serem coletados)
        for (Poder poder : listaPoderes) {
            if (poder instanceof Escudo escudo) {
                float xTela = escudo.getAreaItem().x - posicaoMapaX + 100f;
                float yTela = escudo.getAreaItem().y;
                if (!escudo.estaAtivo()) {
                    escudo.renderizarItem(batch, xTela, yTela);
                }
            } else if (poder instanceof Ima ima) {
                float xTela = ima.getAreaItem().x - posicaoMapaX + 100f;
                float yTela = ima.getAreaItem().y;
                if (!ima.estaAtivo()) {
                    ima.renderizarItem(batch, xTela, yTela);
                }
            }
        }

        // Renderiza os ícones dos poderes na HUD (quando ativos)
        for (Poder poder : listaPoderes) {
            if (poder instanceof Escudo escudo && escudo.estaAtivo()) {
                escudo.renderizar(batch, 10f, 20f, 48f, 48f);
            } else if (poder instanceof Ima ima && ima.estaAtivo()) {
                ima.renderizar(batch, 65f, 20f, 48f, 48f);
            }
        }
    }

    private void iniciaListaDeInimigos(float delta){
        for (int i = listaInimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = listaInimigos.get(i);
            inimigo.update(delta);

            if (!inimigo.getAtivo()) {
                listaInimigos.remove(i);
            }
        }
    }

    private void iniciaListaDeProjeteis(float delta){
        for (int i = listaProjeteis.size() - 1; i >= 0; i--) {
            Projetil proj = listaProjeteis.get(i);
                
            proj.atualizar(delta, velocidadeMapaAtual);
            if (proj.getPosicao().x > larguraMundo) {
                proj.setAtivo(false);
            }

            if (!proj.isAtivo()) {
                listaProjeteis.remove(i);
            }
        }
    }

    private void renderizaPoderes(float delta){
        tempoDesdeUltimoPoder += delta;
        if (tempoDesdeUltimoPoder >= TEMPO_SPAWN_PODER) {
            spawnPoder();
            tempoDesdeUltimoPoder = 0f;
        }

        for (int i = listaPoderes.size() - 1; i >= 0; i--) {
            Poder poder = listaPoderes.get(i);
            if (poder instanceof Escudo escudo) {
                // Verifica colisão convertendo coordenadas do mundo para tela
                Rectangle areaItemTela = new Rectangle();
                areaItemTela.set(escudo.getAreaItem());
                areaItemTela.setPosition(
                        escudo.getAreaItem().x - posicaoMapaX + 100f,
                        escudo.getAreaItem().y);

                if (!escudo.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    escudo.setEstaAtivo(true);
                    escudo.getAreaItem().set(0, 0, 0, 0);
                    //da mais tiros
                    personagem.setQtdTiros(personagem.getQtdTiros() + 5);
                }
                escudo.atualizar(delta, personagem);
            } else if (poder instanceof Ima ima) {
                // Verifica colisão convertendo coordenadas do mundo para tela
                Rectangle areaItemTela = new Rectangle();
                areaItemTela.set(ima.getAreaItem());
                areaItemTela.setPosition(
                        ima.getAreaItem().x - posicaoMapaX + 100f,
                        ima.getAreaItem().y);

                if (!ima.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    ima.setEstaAtivo(true);
                    ima.getAreaItem().set(0, 0, 0, 0);
                }
                ima.atualizar(delta, personagem, geradorCenario.getObjetosAtivos(), posicaoMapaX);
            }

             if (!poder.estaAtivo() && poder.getTempoPoder() <= 0) {
                listaPoderes.remove(i);
            }
        }
    }
    

    private void renderizaHUD(){
        int distancia = (int) personagem.getDistanciaPercorrida();
        int vida = personagem.getVida();
        int moedas = personagem.getMoeda();
        int tiros = personagem.getQtdTiros();

        float hudTop = Gdx.graphics.getHeight();

        font.draw(batch, "Distancia: " + distancia + "m", 10, hudTop - 10);

        batch.draw(assets.texRegVida, 10, hudTop - 50, 24, 24);
        font.draw(batch, "x" + vida, 40, hudTop - 33);

        batch.draw(assets.texRegMoeda, 10, hudTop - 85, 24, 24);
        font.draw(batch, "x" + moedas, 40, hudTop - 68);

        batch.draw(assets.texRegTiro, 10, hudTop - 120, 24, 24);
        font.draw(batch, "x" + tiros + " - Aperte G para atirar!", 40, hudTop - 103);

        font.draw(batch, "Abates: " + abatesTotais, 10, hudTop - 145);
    }

    private void verificaMusicas(float delta) {
        boolean temEscudoAtivo = false;
        boolean temImaAtivo = false;

        for (int i = listaPoderes.size() - 1; i >= 0; i--) {
            Poder poder = listaPoderes.get(i);
            
            if (poder instanceof Escudo escudo) {
                Rectangle areaItemTela = new Rectangle(escudo.getAreaItem());
                areaItemTela.setPosition(escudo.getAreaItem().x - posicaoMapaX + 100f, escudo.getAreaItem().y);

                if (!escudo.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    escudo.setEstaAtivo(true);
                    escudo.getAreaItem().set(0, 0, 0, 0);
                }
                
                escudo.atualizar(delta, personagem);
                if (escudo.estaAtivo()) temEscudoAtivo = true;

            } else if (poder instanceof Ima ima) {
                Rectangle areaItemTela = new Rectangle(ima.getAreaItem());
                areaItemTela.setPosition(ima.getAreaItem().x - posicaoMapaX + 100f, ima.getAreaItem().y);

                if (!ima.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                    ima.setEstaAtivo(true);
                    ima.getAreaItem().set(0, 0, 0, 0);
                }
                
                ima.atualizar(delta, personagem, geradorCenario.getObjetosAtivos(), posicaoMapaX);
                if (ima.estaAtivo()) temImaAtivo = true;
            }

            if (!poder.estaAtivo() && poder.getTempoPoder() <= 0) {
                listaPoderes.remove(i);
            }
        }
        gerenciarAudioPoderes(temEscudoAtivo, temImaAtivo);
    }

    private void gerenciarAudioPoderes(boolean temEscudo, boolean temIma) {
        if (temEscudo) {
            if (!assets.musicaEscudo.isPlaying()) {
                musicaPrincipalAtual.pause();
                assets.musicaIma.stop();
                assets.musicaEscudo.setLooping(true);
                assets.musicaEscudo.setVolume(0.5f);
                assets.musicaEscudo.play();
                musicaPoderTocando = true;
            }
        } else if (temIma) {
            if (!assets.musicaIma.isPlaying()) {
                musicaPrincipalAtual.pause();
                assets.musicaEscudo.stop();
                assets.musicaIma.setLooping(true);
                assets.musicaIma.setVolume(0.5f);
                assets.musicaIma.play();
                musicaPoderTocando = true;
            }
        } else {
            if (musicaPoderTocando) {
                assets.musicaEscudo.stop();
                assets.musicaIma.stop();
                musicaPrincipalAtual.play();
                musicaPoderTocando = false;
            }
        }
    }
    

    private void verificarColisaoInimigos() {
        Rectangle colisaoJogadorTela = personagem.getColisao();
        Rectangle colisaoJogadorMundo = new Rectangle(
                colisaoJogadorTela.x - 100f + posicaoMapaX,
                colisaoJogadorTela.y,
                colisaoJogadorTela.width,
                colisaoJogadorTela.height);

        for (int i = listaInimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = listaInimigos.get(i);
            Rectangle areaInimigo = inimigo.getColisao();

            if (colisaoJogadorMundo.overlaps(areaInimigo)) {
                if (personagem.temEscudo()) {
                    personagem.desativarEscudo();
                    desativarEscudoAtivo();
                } else {
                    personagem.tomarDano(inimigo.getDano());
                }

                listaInimigos.remove(i);
            }
        }
    }

    private void verificarColisaoObstaculos() {
        Rectangle colisaoJogador = personagem.getColisao();
        Missil missilAtualNaTela = null;

        for (int i = geradorCenario.getObjetosAtivos().size - 1; i >= 0; i--) {
            ObjetoDeJogo obj = geradorCenario.getObjetosAtivos().get(i);

            if (obj instanceof Obstaculo obstaculo) {
                if (obstaculo instanceof Missil missil) {
                    missilAtualNaTela = missil;
                }

                float xObstaculoTela = obstaculo.getPosicao().x - posicaoMapaX + 100f;
                float yObstaculoTela = obstaculo.getPosicao().y;

                float larguraObs = 32f;
                float alturaObs = 32f;

                Rectangle areaObstaculoTela = new Rectangle(xObstaculoTela, yObstaculoTela, larguraObs, alturaObs);

                if (colisaoJogador.overlaps(areaObstaculoTela)) {

                    if (obstaculo instanceof Missil) {
                        assets.somMissilExplosao.play(0.33f);
                    } else {
                        assets.somDano.play(0.33f);
                    }

                    if (personagem.temEscudo()) {
                        personagem.desativarEscudo();
                        desativarEscudoAtivo();
                    } else {
                        personagem.tomarDano(personagem.getVida());
                    }

                    if (obstaculo instanceof Explodivel) {
                        explosoesAtivas.add(new EfeitoExplosao(xObstaculoTela, yObstaculoTela));
                    }

                    geradorCenario.getObjetosAtivos().removeIndex(i);

                    if (ultimoMissilAvistado == obstaculo) {
                        ultimoMissilAvistado = null;
                    }
                    break;
                }
            }
        }

        if (missilAtualNaTela != null) {
            if (missilAtualNaTela != ultimoMissilAvistado) {
                assets.somMissilVoando.play(0.15f);
                ultimoMissilAvistado = missilAtualNaTela;
            }
        } else {
            ultimoMissilAvistado = null;
        }
    }

    private void desativarEscudoAtivo() {
        for (int i = listaPoderes.size() - 1; i >= 0; i--) {
            Poder poder = listaPoderes.get(i);
            if (poder instanceof Escudo escudo && escudo.estaAtivo()) {
                escudo.desativar();
            }
        }
    }

    private void verificarInputTiro() {
    if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
        int tirosAtuais = personagem.getQtdTiros();
        if (tirosAtuais > 0) {
            personagem.setQtdTiros(tirosAtuais - 1);

            assets.somTiro.play(0.3f);

            float spawnXTela = 100f + personagem.getColisao().width;
            float spawnYTela = personagem.getColisao().y + (personagem.getColisao().height / 2f);

            listaProjeteis.add(new Projetil(assets.texRegTiro, spawnXTela, spawnYTela));
        }
    }
}

    private void verificarColetaMoedas() {
        Rectangle colisaoJogador = personagem.getColisao();
        for (int i = geradorCenario.getObjetosAtivos().size - 1; i >= 0; i--) {
            ObjetoDeJogo obj = geradorCenario.getObjetosAtivos().get(i);
            if (!(obj instanceof MoedaTile)) {
                continue;
            }

            float xTela = obj.getPosicao().x - posicaoMapaX + 100f;
            float yTela = obj.getPosicao().y;
            areaMoeda.set(xTela, yTela, 32f, 32f);

            if (colisaoJogador.overlaps(areaMoeda)) {
                assets.somMoeda.play(0.15f);
                personagem.adicionarMoeda();
                geradorCenario.getObjetosAtivos().removeIndex(i);
            }
        }
    }

    private void verificarColisaoTiros() {
        for (int i = listaProjeteis.size() - 1; i >= 0; i--) {
            Projetil proj = listaProjeteis.get(i);
            Rectangle areaTiro = proj.getColisao();

            for (int j = geradorCenario.getObjetosAtivos().size - 1; j >= 0; j--) {
                ObjetoDeJogo obj = geradorCenario.getObjetosAtivos().get(j);

                if (obj instanceof Missil || obj instanceof Zapper) {
                    float xObjTela = obj.getPosicao().x - posicaoMapaX + 100f;
                    float yObjTela = obj.getPosicao().y;
                    
                    float larguraObs = (obj instanceof Zapper) ? 32f : 32f;
                    float alturaObs = (obj instanceof Zapper) ? 48f : 32f;

                    Rectangle areaObstaculoTela = new Rectangle(xObjTela, yObjTela, larguraObs, alturaObs);

                    if (areaTiro.overlaps(areaObstaculoTela)) {
                        explosoesAtivas.add(new EfeitoExplosao(xObjTela, yObjTela));
                        assets.somMissilExplosao.play(0.33f);
                        
                        geradorCenario.getObjetosAtivos().removeIndex(j);
                        proj.setAtivo(false);
                        abatesTotais++;
                        break;
                    }
                }
            }

            if (!proj.isAtivo()) continue;

            for (int k = listaInimigos.size() - 1; k >= 0; k--) {
                Inimigo inimigo = listaInimigos.get(k);
                Rectangle colInimigo = inimigo.getColisao();                
                float xInimigoTela = colInimigo.x - posicaoMapaX + 100f;               
                Rectangle areaInimigoTela = new Rectangle(xInimigoTela, colInimigo.y, colInimigo.width, colInimigo.height);
                
                if (areaTiro.overlaps(areaInimigoTela)) {
                    explosoesAtivas.add(new EfeitoExplosao(xInimigoTela, colInimigo.y));
                    assets.somMissilExplosao.play(0.33f);
                    
                    listaInimigos.remove(k);
                    
                    proj.setAtivo(false);
                    abatesTotais++;
                    break;
                }
            }
        }
    }

    /**
     * Calcula o tempo de spawn de inimigos baseado no tempo de jogo.
     * A cada 60 segundos o spawn fica mais rápido até o mínimo de 0.8s.
     */
    private float getTempoSpawnInimigo() {
        float dificuldade = Math.min(tempoTotal / 60f, 1f);
        return TEMPO_SPAWN_INIMIGO_BASE - (TEMPO_SPAWN_INIMIGO_BASE - TEMPO_SPAWN_INIMIGO_MIN) * dificuldade;
    }

    /**
     * Calcula a velocidade atual do mapa baseada no tempo de jogo.
     * A cada 70 segundos atinge a velocidade máxima.
     */
    private float getVelocidadeAtual() {
        float dificuldade = Math.min(tempoTotal / TEMPO_VELOCIDADE_MAX, 1f);
        return VELOCIDADE_MAPA_BASE + (VELOCIDADE_MAPA_MAX - VELOCIDADE_MAPA_BASE) * dificuldade;
    }

    public void spawnInimigo() {
        float xInicial = posicaoMapaX - 100f + larguraMundo + 50f;
        int tipoInimigo = MathUtils.random(0, 2);
        float inimigoLargura = 80f;
        float inimigoAltura = 80f;

        if (tipoInimigo == 0) { // Esqueleto (chão)
            listaInimigos.add(new Esqueleto(xInicial, 32f, inimigoLargura, inimigoAltura, assets.somDano, assets));
        } else if (tipoInimigo == 1) { // Goblin (chão)
            listaInimigos.add(new Goblin(xInicial, 32f, inimigoLargura, inimigoAltura, assets.somDano, assets));
        } else { // Corvo (voador)
            float yAleatorio = MathUtils.random(alturaMundo / 3f, alturaMundo - 120f);
            listaInimigos.add(new Corvo(xInicial, yAleatorio, inimigoLargura, inimigoAltura, assets.somDano, assets));
        }
    }

    public void spawnPoder() {
        float xItem = posicaoMapaX - 100f + larguraMundo + 50f;
        float yItem = MathUtils.random(100f, alturaMundo - 100f);
        if (MathUtils.randomBoolean(0.5f)) {
            Escudo escudo = new Escudo(assets.texRegEscudo);
            escudo.getAreaItem().set(xItem, yItem, 32f, 32f);
            listaPoderes.add(escudo);
        } else {
            Ima ima = new Ima(assets.texRegIma);
            ima.getAreaItem().set(xItem, yItem, 32f, 32f);
            listaPoderes.add(ima);
        }
    }

    public void fimJogo() {
        if (personagem.deveExplodir(personagem.getColisao().x, personagem.getColisao().y)) {
            if (!iniciouAnimacaoMorte) {
                if (assets.musica1.isPlaying()) {
                    assets.musica1.stop();
                }
                if (assets.musica2.isPlaying()) {
                    assets.musica2.stop();
                }
                if(assets.musicaEscudo.isPlaying()){
                    assets.musicaEscudo.stop();
                }
                if(assets.musicaIma.isPlaying()){
                    assets.musicaIma.stop();
                }

                assets.musicaMorte.setLooping(true);
                assets.musicaMorte.setVolume(0.6f);
                assets.musicaMorte.play();

                explosoesAtivas.add(new EfeitoExplosao(personagem.getColisao().x, personagem.getColisao().y));
                iniciouAnimacaoMorte = true;
            }

            if (iniciouAnimacaoMorte && explosoesAtivas.isEmpty()) {
                this.dispose();
                game.setScreen(new MorteScreen(game, assets, abatesTotais, personagem.getDistanciaPercorrida()));
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
