package dev.kason.catan.backend.player;

public class DevCard {

    private String displayText;
    private Type type;

    public enum Type {
        KNIGHT,
        YEAR_OF_PLENTY,
        ROAD_BUILDING,
        VICTORY_POINT,
        MONOPOLY;

        public static Type fromString(String str) {
            return switch (str) {
                case "knight" -> KNIGHT;
                case "year_of_plenty" -> YEAR_OF_PLENTY;
                case "road_building" -> ROAD_BUILDING;
                case "victory_point" -> VICTORY_POINT;
                case "monopoly" -> MONOPOLY;
                default -> throw new IllegalArgumentException("unknown dev card type: " + str);
            };
        }

        public String toString() {
            return switch (this) {
                case KNIGHT -> "knight";
                case YEAR_OF_PLENTY -> "year_of_plenty";
                case ROAD_BUILDING -> "road_building";
                case VICTORY_POINT -> "victory_point";
                case MONOPOLY -> "monopoly";
            };
        }
    }


}
