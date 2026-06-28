package br.com.lgalarane.trabalho02;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class GameAssets {
    private AssetManager managerAsset;

    public Sound somDano;
    public Sound somMissilVoando;
    public Sound somMissilExplosao;
    public TextureRegion texRegConcreto;
    public TextureRegion texRegFogo;
    public TextureRegion texRegNeve;
    public TextureRegion texRegGrama;
    public TextureRegion texRegMoeda;
    public TextureRegion texRegVida;
    public TextureRegion texRegGranada;
    public TextureRegion texRegMissil;
    public TextureRegion texRegZapper;
    public TextureRegion texRegEscudo;
    public TextureRegion texRegIma;

    public TextureRegion texRegFundoGrama;
    public TextureRegion texRegFundoFogo;
    public TextureRegion texRegFundoNeve;
    public TextureRegion texRegFundoConcreto;

    public Array<TextureRegion> framesExplosao;

    public Texture texPersonagem;

    public Array<TextureRegion> framesCorrendo;
    public Array<TextureRegion> framesVoando;
    public Array<TextureRegion> framePulando;

    public Texture texEsqueleto;
    public Texture texGoblin;
    public Texture texCorvo;

    // Frames/Animações separadas por inimigo
    public Array<TextureRegion> framesCorrendoEsqueleto;
    public Array<TextureRegion> framesCorrendoGoblin;
    public Array<TextureRegion> framesPulandoGoblin;

    public Array<TextureRegion> framesVoandoCorvo;

    public Sound somPulo;
    public Sound somTiro;
    public Sound somPowerUp1;
    public Sound somPowerUp2;
    public Sound somPowerUp3;
    public Sound somMoeda;

    public Music musicaMenu;
    public Music musica1;
    public Music musica2;
    public Music musicaMorte;

    public GameAssets() {
        this.managerAsset = new AssetManager();
    }

    public void carregaEsqueleto() {
        int frameW = 64;
        int frameH = 64;

        framesCorrendoEsqueleto = new Array<>();
        framesCorrendoEsqueleto.add(new TextureRegion(texEsqueleto, 0 * frameW, 0, frameW, frameH));
        framesCorrendoEsqueleto.add(new TextureRegion(texEsqueleto, 1 * frameW, 0, frameW, frameH));
        framesCorrendoEsqueleto.add(new TextureRegion(texEsqueleto, 2 * frameW, 0, frameW, frameH));
        framesCorrendoEsqueleto.add(new TextureRegion(texEsqueleto, 3 * frameW, 0, frameW, frameH));
    }

    public void carregaGoblin() {
        int frameW = 64;
        int frameH = 64;

        framesCorrendoGoblin = new Array<>();
        framesCorrendoGoblin.add(new TextureRegion(texGoblin, 0 * frameW, 0, frameW, frameH));
        framesCorrendoGoblin.add(new TextureRegion(texGoblin, 1 * frameW, 0, frameW, frameH));
        framesCorrendoGoblin.add(new TextureRegion(texGoblin, 2 * frameW, 0, frameW, frameH));
        framesCorrendoGoblin.add(new TextureRegion(texGoblin, 3 * frameW, 0, frameW, frameH));

        framesPulandoGoblin = new Array<>();
        framesPulandoGoblin.add(new TextureRegion(texGoblin, 0 * frameW, 0, frameW, frameH)); // Frame de fallback
    }

    public void carregaCorvo() {
        int frameW = 64;
        int frameH = 64;

        framesVoandoCorvo = new Array<>();
        framesVoandoCorvo.add(new TextureRegion(texCorvo, 0 * frameW, 0, frameW, frameH));
        framesVoandoCorvo.add(new TextureRegion(texCorvo, 1 * frameW, 0, frameW, frameH));
        framesVoandoCorvo.add(new TextureRegion(texCorvo, 2 * frameW, 0, frameW, frameH));
        framesVoandoCorvo.add(new TextureRegion(texCorvo, 3 * frameW, 0, frameW, frameH));
    }

    public void carregaTodosAssets() {
        managerAsset.load("fontes/dano.wav", Sound.class);
        managerAsset.load("fontes/missilvoando.mp3", Sound.class);
        managerAsset.load("fontes/explosao.mp3", Sound.class);
        managerAsset.load("fontes/pulo.wav", Sound.class);
        managerAsset.load("fontes/tiro.mp3", Sound.class);
        managerAsset.load("fontes/powerup1.mp3", Sound.class);
        managerAsset.load("fontes/powerup2.mp3", Sound.class);
        managerAsset.load("fontes/powerup3.mp3", Sound.class);

        managerAsset.load("fontes/sommoeda.mp3", Sound.class);

        managerAsset.load("fontes/musicamenu.mp3", Music.class);
        managerAsset.load("fontes/musica1.mp3", Music.class);
        managerAsset.load("fontes/musica2.mp3", Music.class);
        managerAsset.load("fontes/menuMorte.mp3", Music.class);

        managerAsset.load("textures/concreto.png", Texture.class);
        managerAsset.load("textures/fogo.png", Texture.class);
        managerAsset.load("textures/neve.png", Texture.class);
        managerAsset.load("textures/grama.png", Texture.class);
        managerAsset.load("textures/moeda.png", Texture.class);
        managerAsset.load("textures/vida.png", Texture.class);
        managerAsset.load("textures/granada.png", Texture.class);
        managerAsset.load("textures/missil.png", Texture.class);
        managerAsset.load("textures/escudo.png", Texture.class);
        managerAsset.load("textures/ima.png", Texture.class);

        managerAsset.load("textures/granada.png", Texture.class); 
        managerAsset.load("textures/missil.png", Texture.class); 
        managerAsset.load("textures/escudo.png", Texture.class); 
        managerAsset.load("textures/ima.png", Texture.class); 

        managerAsset.load("textures/gramafundo.png", Texture.class);
        managerAsset.load("textures/fogofundo.png", Texture.class);
        managerAsset.load("textures/nevefundo.png", Texture.class);
        managerAsset.load("textures/concretofundo.png", Texture.class);

        // texturas do personagem e dos inimigos
        managerAsset.load("textures/personagem.png", Texture.class);
        managerAsset.load("textures/skeletonSprite.png", Texture.class);
        managerAsset.load("textures/goblinSprite.png", Texture.class);
        managerAsset.load("textures/corvoSprite.png", Texture.class);

        for (int i = 0; i < 24; i++) {
            // String.format("%02d", i) transforma o número 0 em "00", 1 em "01", etc.
            String caminhoFrame = String.format("textures/explosao_frames/explosao_%02d.png", i);
            managerAsset.load(caminhoFrame, Texture.class);
        }

        managerAsset.finishLoading();

        somDano = managerAsset.get("fontes/dano.wav", Sound.class);
        somMissilVoando = managerAsset.get("fontes/missilvoando.mp3", Sound.class);
        somMissilExplosao = managerAsset.get("fontes/explosao.mp3", Sound.class);
        somPulo = managerAsset.get("fontes/pulo.wav", Sound.class);
        somTiro = managerAsset.get("fontes/tiro.mp3", Sound.class);
        somPowerUp1 = managerAsset.get("fontes/powerup1.mp3", Sound.class);
        somPowerUp2 = managerAsset.get("fontes/powerup2.mp3", Sound.class);
        somPowerUp3 = managerAsset.get("fontes/powerup3.mp3", Sound.class);
        somMoeda = managerAsset.get("fontes/sommoeda.mp3", Sound.class);

        musicaMenu = managerAsset.get("fontes/musicamenu.mp3", Music.class);
        musica1 = managerAsset.get("fontes/musica1.mp3", Music.class);
        musica2 = managerAsset.get("fontes/musica2.mp3", Music.class);
        musicaMorte = managerAsset.get("fontes/menuMorte.mp3", Music.class);

        texRegConcreto = new TextureRegion(managerAsset.get("textures/concreto.png", Texture.class));
        texRegFogo = new TextureRegion(managerAsset.get("textures/fogo.png", Texture.class));
        texRegNeve = new TextureRegion(managerAsset.get("textures/neve.png", Texture.class));
        texRegGrama = new TextureRegion(managerAsset.get("textures/grama.png", Texture.class));

        texRegMoeda = new TextureRegion(managerAsset.get("textures/moeda.png", Texture.class));
        texRegVida = new TextureRegion(managerAsset.get("textures/vida.png", Texture.class));
        texRegGranada = new TextureRegion(managerAsset.get("textures/granada.png", Texture.class));

        texRegMissil = new TextureRegion(managerAsset.get("textures/missil.png", Texture.class));
        texRegZapper = new TextureRegion(managerAsset.get("textures/zapper.png", Texture.class));
        texRegEscudo = new TextureRegion(managerAsset.get("textures/escudo.png", Texture.class));
        texRegIma = new TextureRegion(managerAsset.get("textures/ima.png", Texture.class));

        texRegFundoGrama = new TextureRegion(managerAsset.get("textures/gramafundo.png", Texture.class));
        texRegFundoFogo = new TextureRegion(managerAsset.get("textures/fogofundo.png", Texture.class));
        texRegFundoNeve = new TextureRegion(managerAsset.get("textures/nevefundo.png", Texture.class));
        texRegFundoConcreto = new TextureRegion(managerAsset.get("textures/concretofundo.png", Texture.class));

        // IA para a criação de design do sprite
        texPersonagem = managerAsset.get("textures/personagem.png", Texture.class);
        texEsqueleto = managerAsset.get("textures/skeletonSprite.png", Texture.class);
        texGoblin = managerAsset.get("textures/goblinSprite.png", Texture.class);
        texCorvo = managerAsset.get("textures/corvoSprite.png", Texture.class);

        // tudo abaixo até a linha 100 foi utilizado IA

        int frameW = 426;
        int frameH = 1440;

        framesCorrendo = new Array<>();
        framesCorrendo.add(new TextureRegion(texPersonagem, 0 * frameW, 0, frameW, frameH));
        framesCorrendo.add(new TextureRegion(texPersonagem, 1 * frameW, 0, frameW, frameH));
        framesCorrendo.add(new TextureRegion(texPersonagem, 2 * frameW, 0, frameW, frameH));
        framesCorrendo.add(new TextureRegion(texPersonagem, 3 * frameW, 0, frameW, frameH));

        framesVoando = new Array<>();
        framesVoando.add(new TextureRegion(texPersonagem, 4 * frameW, 0, frameW, frameH));
        framesVoando.add(new TextureRegion(texPersonagem, 5 * frameW, 0, frameW, frameH));

        carregaEsqueleto();
        carregaGoblin();
        carregaCorvo();

        if (texEsqueleto != null)
            texEsqueleto.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        if (texGoblin != null)
            texGoblin.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        if (texCorvo != null)
            texCorvo.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        framesExplosao = new Array<>();
        for (int i = 0; i < 24; i++) {
            String caminhoFrame = String.format("textures/explosao_frames/explosao_%02d.png", i);
            Texture tex = managerAsset.get(caminhoFrame, Texture.class);

            tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

            framesExplosao.add(new TextureRegion(tex));
        }
    }

    public void limparAssets() {
        if (managerAsset != null) {
            managerAsset.dispose();
        }
    }
}