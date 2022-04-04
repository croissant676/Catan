package dev.kason.catan.backend.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Hex {

    private final int id;
    private final Hex.Type type;
    private final Map<Location, Hex> hexes = new HashMap<>(6);
    private final Map<Location, Vertex> vertices = new HashMap<>(6);
    @Setter(AccessLevel.PACKAGE)
    private int value = 0;

    public Hex(int id, Hex.Type type) {
        this.id = id;
        this.type = type;
    }

    public static String idOnly(Iterable<Hex> hexes) {
        StringBuilder sb = new StringBuilder();
        for (Hex hex : hexes) {
            sb.append(hex.getId());
            sb.append(",");
        }
        return sb.toString();
    }

    public boolean isNextTo(Hex hex) {
        return hexes.containsValue(hex);
    }

    public enum Type {
        FOREST,
        FIELD,
        MOUNTAIN,
        HILL,
        DESERT,
        PASTURE;

        public int getNumber() {
            return switch (this) {
                case HILL, MOUNTAIN -> 3;
                case DESERT -> 1;
                default -> 4;
            };
        }

        public Resource getResource() {
            return switch (this) {
                case FOREST -> Resource.WOOD;
                case FIELD -> Resource.WHEAT;
                case MOUNTAIN -> Resource.ORE;
                case HILL -> Resource.BRICK;
                case PASTURE -> Resource.SHEEP;
                default -> null;
            };
        }
    }

}
