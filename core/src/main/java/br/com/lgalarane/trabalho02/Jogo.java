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
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;

import mapa.GeradorCenario;
import mapa.TipoBioma;
import mapa.obstaculos.Explodivel;
import mapa.obstaculos.Missil;
import mapa.planosdefundo.GeradorFundo;
import mapa.tiles.MoedaTile;
import objetos.ObjetoDeJogo;
import personagem.Inimigo;
import personagem.Inimigos.Esqueleto;
import personagem.Inimigos.Goblin;
import poderes.*;
import personagem.Inimigos.Corvo;

import personagem.PersonagemPrincipal;

public class Jogo extends ApplicationAdapter {
    private final Game game;

    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private GeradorCenario geradorCenario;
    private GeradorFundo geradorFundo;

    private GameAssets assets;
    private PersonagemPrincipal personagem;

    private ArrayList<Inimigo> listaInimigos;
    private ArrayList<Poder> listaPoderes;
    private Rectangle areaMoeda;

    private float posicaoMapaX = 0f;
    private final float VELOCIDADE_MAPA = 150f;

    float larguraMundo;
    float alturaMundo;

    private float tempoDesdeUltimoInimigo = 0f;
    private final float TEMPO_SPAWN = 3f;
    private float tempoDesdeUltimoPoder = 0f;
    private final float TEMPO_SPAWN_PODER = 6f; 

    private Animation<TextureRegion> animacaoExplosao;
    private final ArrayList<EfeitoExplosao> explosoesAtivas = new ArrayList<>();
    private boolean iniciouAnimacaoMorte = false;

    private Missil ultimoMissilAvistado = null;

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

        assets = new GameAssets();
        assets.carregaTodosAssets();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        animacaoExplosao = new Animation<>(0.04f, assets.framesExplosao, Animation.PlayMode.NORMAL);

        geradorCenario = new GeradorCenario(
                assets.texRegConcreto,
                assets.texRegFogo,
                assets.texRegNeve,
                assets.texRegGrama,
                assets.texRegMoeda,
                assets.texRegMissil);

        geradorFundo = new GeradorFundo(
                assets.texRegFundoGrama,
                assets.texRegFundoFogo,
                assets.texRegFundoNeve,
                assets.texRegFundoConcreto);

        personagem = new PersonagemPrincipal(0f, 100f, 100f, 130f, assets.somDano, assets);
        areaMoeda = new Rectangle();

        assets.musica1.setVolume(1.2f);
        assets.musica2.setVolume(1.2f);

        // quando a 1 terminar, começará a 2
        assets.musica1.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                assets.musica2.play();
            }
        });

        // quando a 2 terminar, começará a 1 de novo
        assets.musica2.setOnCompletionListener(new Music.OnCompletionListener() {
            @Override
            public void onCompletion(Music music) {
                assets.musica1.play();
            }
        });

        assets.musica1.play();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        if (personagem.getVida() > 0) {
            personagem.atualizar(delta, VELOCIDADE_MAPA);
            posicaoMapaX += VELOCIDADE_MAPA * delta;

            geradorCenario.atualizar(posicaoMapaX, delta);
            verificarColetaMoedas();
            verificarColisaoObstaculos();
            verificarColisaoInimigos();
            verificarInputGranada();

            tempoDesdeUltimoInimigo += delta;
            if (tempoDesdeUltimoInimigo >= TEMPO_SPAWN) {
                spawnInimigo();
                tempoDesdeUltimoInimigo = 0f;
            }

            for (int i = listaInimigos.size() - 1; i >= 0; i--) {
                Inimigo inimigo = listaInimigos.get(i);
                inimigo.update(delta);

                if (!inimigo.getAtivo()) {
                    listaInimigos.remove(i);
                }
            }

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
                        escudo.getAreaItem().y
                    );

                    if (!escudo.estaAtivo() && personagem.getColisao().overlaps(areaItemTela)) {
                        escudo.setEstaAtivo(true);
                        escudo.getAreaItem().set(0, 0, 0, 0);
                    }
                    escudo.atualizar(delta, personagem);
                } else if (poder instanceof Ima ima) {
                    // Verifica colisão convertendo coordenadas do mundo para tela
                    Rectangle areaItemTela = new Rectangle();
                    areaItemTela.set(ima.getAreaItem());
                    areaItemTela.setPosition(
                        ima.getAreaItem().x - posicaoMapaX + 100f,
                        ima.getAreaItem().y
                    );

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

        for (int i = explosoesAtivas.size() - 1; i >= 0; i--) {
            EfeitoExplosao exp = explosoesAtivas.get(i);
            exp.tempoDeVida += delta;

            if (animacaoExplosao.isAnimationFinished(exp.tempoDeVida)) {
                explosoesAtivas.remove(i);
            }
        }

        batch.begin();

        TipoBioma biomaAtivo = geradorCenario.getBiomaSobOJogador(posicaoMapaX);
        geradorFundo.renderizar(batch, biomaAtivo);
        geradorCenario.renderizar(batch, posicaoMapaX);
        
        for (EfeitoExplosao exp : explosoesAtivas) {
            TextureRegion frameAtual = animacaoExplosao.getKeyFrame(exp.tempoDeVida);
            batch.draw(frameAtual, exp.x, exp.y, 32f, 32f);
        }

        int distancia = (int) personagem.getDistanciaPercorrida();
        int vida = personagem.getVida();
        int moedas = personagem.getMoeda();
        int granadas = personagem.getQtdGranadas();

        float hudTop = Gdx.graphics.getHeight();

        font.draw(batch, "Distancia: " + distancia + "m", 10, hudTop - 10);

        batch.draw(assets.texRegVida, 10, hudTop - 50, 24, 24);
        font.draw(batch, "x" + vida, 38, hudTop - 30);

        batch.draw(assets.texRegMoeda, 90, hudTop - 50, 24, 24);
        font.draw(batch, "x" + moedas, 118, hudTop - 30);

        batch.draw(assets.texRegGranada, 10, hudTop - 95, 24, 24);
        font.draw(batch, "x" + granadas, 40, hudTop - 78);

        // Render principal com SpriteBatch
        if (personagem.getVida() > 0) {
            personagem.renderizar(batch);
        }

            for (Inimigo inimigo : listaInimigos) {
                if (inimigo instanceof Esqueleto esqueleto) {
                    esqueleto.renderizar(batch);
                } else if (inimigo instanceof Goblin goblin) {
                    goblin.renderizar(batch);
                } else if (inimigo instanceof Corvo corvo) {
                    corvo.renderizar(batch);
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
            if (poder instanceof Escudo escudo) {
                escudo.renderizar(batch, 10f, Gdx.graphics.getHeight() - 125f, 48f, 48f);
            } else if (poder instanceof Ima ima) {
                ima.renderizar(batch, 50f, Gdx.graphics.getHeight() - 125f, 48f, 48f);
            }
        }

        batch.end();
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (Inimigo inimigo : listaInimigos) {
            inimigo.renderizar(shapeRenderer);
        }
        shapeRenderer.end();

        fimJogo();
    }

    private void verificarColisaoInimigos() {
        Rectangle colisaoJogador = personagem.getColisao();

        for (int i = listaInimigos.size() - 1; i >= 0; i--) {
            Inimigo inimigo = listaInimigos.get(i);
            Rectangle areaInimigo = inimigo.getColisao();

            if (colisaoJogador.overlaps(areaInimigo)) {
                if (personagem.temEscudo()) {
                    personagem.desativarEscudo();
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

            if (obj instanceof Missil) {
                Missil missil = (Missil) obj;
                missilAtualNaTela = missil; // Registra que existe um míssil ativo

                float xMissilTela = missil.getPosicao().x - posicaoMapaX + 100f;
                float yMissilTela = missil.getPosicao().y;

                Rectangle areaMissilTela = new Rectangle(xMissilTela, yMissilTela, 32f, 32f);

                if (colisaoJogador.overlaps(areaMissilTela)) {
                    // Toca o som de explosão ao colidir com o míssil!
                    assets.somMissilExplosao.play(0.33f);

                    if (personagem.temEscudo()) {
                        personagem.desativarEscudo();
                    } else {
                        personagem.tomarDano(personagem.getVida());
                    }

                    if (missil instanceof Explodivel) {
                        explosoesAtivas.add(new EfeitoExplosao(xMissilTela, yMissilTela));
                    }

                    geradorCenario.getObjetosAtivos().removeIndex(i);
                    if (ultimoMissilAvistado == missil) {
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

    private void verificarInputGranada() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.G)) {
            int granadasAtuais = personagem.getQtdGranadas();
            if (granadasAtuais > 0) {
                personagem.setQtdGranadas(granadasAtuais - 1);
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

    public void spawnPoder() {
        float xInicial = posicaoMapaX + 250f;
        float ySpawn = MathUtils.random(50f, alturaMundo - 150f);

        if (MathUtils.randomBoolean()) {
            Escudo escudo = new Escudo(assets.texEscudo);
            escudo.getAreaItem().set(xInicial, ySpawn, 32f, 32f);
            listaPoderes.add(escudo);
        } else {
            Ima ima = new Ima(assets.texIma);
            ima.getAreaItem().set(xInicial, ySpawn, 32f, 32f);
            listaPoderes.add(ima);
        }
    }

    public void spawnInimigo() {
        float xInicial = larguraMundo + 50f;
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

    public void fimJogo() {
        if (personagem.deveExplodir(personagem.getColisao().x, personagem.getColisao().y)) {
            if (!iniciouAnimacaoMorte) {
                if (assets.musica1.isPlaying()) {
                    assets.musica1.stop();
                }
                if (assets.musica2.isPlaying()) {
                    assets.musica2.stop();
                }

                assets.musicaMorte.setLooping(true);
                assets.musicaMorte.setVolume(0.6f);
                assets.musicaMorte.play();

                explosoesAtivas.add(new EfeitoExplosao(personagem.getColisao().x, personagem.getColisao().y));
                iniciouAnimacaoMorte = true;
            }

            if (iniciouAnimacaoMorte && explosoesAtivas.isEmpty()) {
                this.dispose();
                game.setScreen(new MorteScreen(game, assets));
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}