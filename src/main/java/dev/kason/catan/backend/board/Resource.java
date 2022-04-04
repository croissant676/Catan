package dev.kason.catan.backend.board;

public enum Resource {
    WOOD,
    SHEEP,
    WHEAT,
    BRICK,
    ORE;

    public String getFilePath() {
        return this.name().toLowerCase() + ".jpg";
    }

    public Hex.Type getGenerator() {
        return switch (this) {
            case WOOD -> Hex.Type.FOREST;
            case SHEEP -> Hex.Type.PASTURE;
            case WHEAT -> Hex.Type.FIELD;
            case BRICK -> Hex.Type.HILL;
            case ORE -> Hex.Type.MOUNTAIN;
        };
    }


}
