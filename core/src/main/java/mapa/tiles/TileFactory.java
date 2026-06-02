package mapa.tiles;

import java.util.HashMap;
import java.util.Map;

// Gerencia a criação de objetos, útil para a otimização de memória
public class TileFactory {
    private static final Map<Integer, Tile> registro = new HashMap<>();

    public static void register(int id, Tile tile) {
        registro.put(id, tile);
    }

    public static Tile createTile(int id) {
        Tile prototipo = registro.get(id);
        
        if (prototipo == null) {
            throw new IllegalArgumentException("Tile com o ID " + id + " não foi registrado na Factory.");
        }
        
        return prototipo.clone();
    }
}