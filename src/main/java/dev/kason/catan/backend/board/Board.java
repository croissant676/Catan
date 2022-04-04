package dev.kason.catan.backend.board;

import dev.kason.catan.backend.Game;
import dev.kason.catan.backend.player.Player;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Getter
@Setter
public class Board {

    private static final Set<Integer> leftMost = Set.of(0, 3, 7, 12, 16);
    private static final Set<Integer> rightMost = Set.of(2, 6, 11, 15, 18);
    private static final Set<Location> vertexLocations = EnumSet.of(
            Location.TOP,
            Location.TOP_LEFT,
            Location.BOTTOM_LEFT,
            Location.BOTTOM,
            Location.BOTTOM_RIGHT,
            Location.TOP_RIGHT
    );

    private final List<Hex> hexes;
    private final Set<Vertex> vertices;
    private int robber;

    public Board(Random random, ValueGenerationStrategy strategy) {
        if (random == null) {
            random = new Random();
        }
        if (strategy == null) {
            strategy = ValueGenerationStrategy.DEFAULT;
        }
        List<Hex.Type> hexTypes = new ArrayList<>(19);
        for (Hex.Type value : Hex.Type.values()) {
            for (int count = 0; count < value.getNumber(); count++) {
                hexTypes.add(value);
            }
        }
        Collections.shuffle(hexTypes, random);
        hexes = new ArrayList<>(hexTypes.size());
        for (int count = 0; count < hexTypes.size(); count++) {
            Hex.Type type = hexTypes.get(count);
            if (type == Hex.Type.DESERT) {
                robber = count;
            }
            hexes.add(new Hex(count, type));
        }
        for (int index = 0; index < hexes.size(); index++) {
            Hex hex = hexes.get(index);
            Map<Location, Hex> neighbors = hex.getHexes();
            if (!leftMost.contains(index)) {
                neighbors.put(Location.LEFT, hexes.get(index - 1));
            }
            if (!rightMost.contains(index)) {
                neighbors.put(Location.RIGHT, hexes.get(index + 1));
            }
            if (index <= 2) {
                neighbors.put(Location.BOTTOM_LEFT, hexes.get(index + 3));
                neighbors.put(Location.BOTTOM_RIGHT, hexes.get(index + 4));
            } else if (index <= 6) {
                if (index != 3) {
                    neighbors.put(Location.TOP_LEFT, hexes.get(index - 4));
                }
                if (index != 6) {
                    neighbors.put(Location.TOP_RIGHT, hexes.get(index - 3));
                }
                neighbors.put(Location.BOTTOM_LEFT, hexes.get(index + 3));
                neighbors.put(Location.BOTTOM_RIGHT, hexes.get(index + 4));
            } else if (index <= 11) {
                if (index != 7) {
                    neighbors.put(Location.BOTTOM_LEFT, hexes.get(index + 4));
                    neighbors.put(Location.TOP_LEFT, hexes.get(index - 5));
                }
                if (index != 11) {
                    neighbors.put(Location.BOTTOM_RIGHT, hexes.get(index + 5));
                    neighbors.put(Location.TOP_RIGHT, hexes.get(index - 4));
                }
            } else if (index <= 15) {
                if (index != 12) {
                    neighbors.put(Location.BOTTOM_LEFT, hexes.get(index + 3));
                }
                if (index != 15) {
                    neighbors.put(Location.BOTTOM_RIGHT, hexes.get(index + 4));
                }
                neighbors.put(Location.TOP_LEFT, hexes.get(index - 5));
                neighbors.put(Location.TOP_RIGHT, hexes.get(index - 4));
            } else {
                neighbors.put(Location.TOP_LEFT, hexes.get(index - 4));
                neighbors.put(Location.TOP_RIGHT, hexes.get(index - 3));
            }
        }

        this.vertices = new HashSet<>();
        for (Hex hex : hexes) {
            Map<Location, Vertex> vertices = hex.getVertices();
            if (hex.getHexes().containsKey(Location.TOP_LEFT)) {
                Hex topLeft = hex.getHexes().get(Location.TOP_LEFT);
                vertices.put(Location.TOP_LEFT, topLeft.getVertices().get(Location.BOTTOM));
                vertices.put(Location.TOP, topLeft.getVertices().get(Location.BOTTOM_RIGHT));
                if (hex.getHexes().containsKey(Location.TOP_RIGHT)) {
                    Hex topRight = hex.getHexes().get(Location.TOP_RIGHT);
                    vertices.put(Location.TOP_RIGHT, topRight.getVertices().get(Location.BOTTOM));
                }
                if (hex.getHexes().containsKey(Location.LEFT)) {
                    Hex left = hex.getHexes().get(Location.LEFT);
                    vertices.put(Location.BOTTOM_LEFT, left.getVertices().get(Location.BOTTOM_RIGHT));
                }
            } else if (vertices.containsKey(Location.LEFT)) {
                Hex left = hex.getHexes().get(Location.LEFT);
                vertices.put(Location.TOP_LEFT, left.getVertices().get(Location.TOP_RIGHT));
                vertices.put(Location.BOTTOM_LEFT, left.getVertices().get(Location.BOTTOM_RIGHT));
            } else if (vertices.containsKey(Location.TOP_RIGHT)) {
                Hex topRight = hex.getHexes().get(Location.TOP_RIGHT);
                vertices.put(Location.TOP_RIGHT, topRight.getVertices().get(Location.BOTTOM));
                vertices.put(Location.TOP, topRight.getVertices().get(Location.BOTTOM_LEFT));
            }
            for (Location vertexLocation : vertexLocations) {
                if (!vertices.containsKey(vertexLocation)) {
                    vertices.put(vertexLocation, new Vertex());
                }
            }
            for (Map.Entry<Location, Vertex> entry : vertices.entrySet()) {
                Vertex vertex = entry.getValue();
                vertex.getHexes().add(hex);
                this.vertices.add(vertex);
            }
            strategy.generateValues(hexes);
        }

    }

    public List<Port> findPortsFor(Player player) {
        List<Port> ports = new ArrayList<>();
        for (Vertex vertex : vertices) {
            if (vertex.getPlayer().equals(player)
                    && vertex.hasPort()) {
                ports.add(vertex.getPort());
            }
        }
        return ports;
    }

    public Hex getRobberHex() {
        return hexes.get(robber);
    }


    public Hex moveRobber(int newHex) {
        if (robber == newHex) {
            return null;
        }
        Hex hex = hexes.get(newHex);
        robber = newHex;
        return hex;
    }

    public Set<Hex> possibleRobberHexes() {
        Set<Hex> hexes = new HashSet<>();
        for (Hex hex : this.hexes) {
            if (hex.getId() != robber) {
                hexes.add(hex);
            }
        }
        return hexes;
    }

    public Set<Vertex> getValidBuildingPoints() {
        return vertices;
    }

    @Data
    @Builder
    public static class ValueGenerationStrategy {
        public static final ValueGenerationStrategy DEFAULT = new ValueGenerationStrategy(0, false);
        private static final int[] VALUES = {
                5, 2, 6, 3, 8, 10, 9, 12, 11, 4, 8, 10, 9, 4, 5, 6, 3, 11
        };
        private static final List<Integer> values = List.of(
                0, 1, 2, 3, 6, 7, 11, 12, 15, 16, 18
        );
        private static Random random;
        private final int first;
        private final boolean clockwise;
        private final int[] inOrder;

        public ValueGenerationStrategy(int first, boolean clockwise) {
            if (!values.contains(first)) {
                throw new IllegalArgumentException("Starting location must be one of " + values);
            }
            this.first = first;
            this.clockwise = clockwise;
            if (clockwise) {
                inOrder = switch (first) {
                    case 0 -> new int[]{0, 1, 2, 6, 11, 15, 18, 17, 16, 12, 7, 3, 4, 5, 10, 14, 13, 8, 9};
                    case 1 -> new int[]{1, 2, 6, 11, 15, 18, 17, 16, 12, 7, 3, 0, 4, 5, 10, 14, 13, 8, 9};
                    case 2 -> new int[]{2, 6, 11, 15, 18, 17, 16, 12, 7, 3, 0, 1, 5, 10, 14, 13, 8, 4, 9};
                    case 3 -> new int[]{3, 0, 1, 2, 6, 11, 15, 18, 17, 16, 12, 7, 8, 4, 5, 10, 14, 13, 9};
                    case 6 -> new int[]{6, 11, 15, 18, 17, 16, 12, 7, 3, 0, 1, 2, 5, 10, 14, 13, 8, 4, 9};
                    case 7 -> new int[]{7, 3, 0, 1, 2, 6, 11, 15, 18, 17, 16, 12, 8, 4, 5, 10, 14, 13, 9};
                    case 11 -> new int[]{11, 15, 18, 17, 16, 12, 7, 3, 0, 1, 2, 6, 10, 14, 13, 8, 4, 5, 9};
                    case 12 -> new int[]{12, 7, 3, 0, 1, 2, 6, 11, 15, 18, 17, 16, 13, 8, 4, 5, 10, 14, 9};
                    case 15 -> new int[]{15, 18, 17, 16, 12, 7, 3, 0, 1, 2, 6, 11, 10, 14, 13, 8, 4, 5, 9};
                    case 16 -> new int[]{16, 12, 7, 3, 0, 1, 2, 6, 11, 15, 18, 17, 13, 8, 4, 5, 10, 14, 9};
                    case 18 -> new int[]{18, 17, 16, 12, 7, 3, 0, 1, 2, 6, 11, 15, 14, 13, 8, 4, 5, 10, 9};
                    default -> throw new IllegalStateException("Unexpected value: " + first);
                };
            } else {
                inOrder = switch (first) {
                    case 0 -> new int[]{0, 3, 7, 12, 16, 17, 18, 15, 11, 6, 2, 1, 4, 8, 13, 14, 10, 5, 9};
                    case 1 -> new int[]{1, 0, 3, 7, 12, 16, 17, 18, 15, 11, 6, 2, 5, 4, 8, 13, 14, 10, 9};
                    case 2 -> new int[]{2, 1, 0, 3, 7, 12, 16, 17, 18, 15, 11, 6, 5, 4, 8, 13, 14, 10, 9};
                    case 3 -> new int[]{3, 7, 12, 16, 17, 18, 15, 11, 6, 2, 1, 0, 4, 8, 13, 14, 10, 5, 9};
                    case 6 -> new int[]{6, 2, 1, 0, 3, 7, 12, 16, 17, 18, 15, 11, 10, 5, 4, 8, 13, 14, 9};
                    case 7 -> new int[]{7, 12, 16, 17, 18, 15, 11, 6, 2, 1, 0, 3, 8, 13, 14, 10, 5, 4, 9};
                    case 11 -> new int[]{11, 6, 2, 1, 0, 3, 7, 12, 16, 17, 18, 15, 10, 5, 4, 8, 13, 14, 9};
                    case 12 -> new int[]{12, 16, 17, 18, 15, 11, 6, 2, 1, 0, 3, 7, 8, 13, 14, 10, 5, 4, 9};
                    case 15 -> new int[]{15, 11, 6, 2, 1, 0, 3, 7, 12, 16, 17, 18, 14, 10, 5, 4, 8, 13, 9};
                    case 16 -> new int[]{16, 17, 18, 15, 11, 6, 2, 1, 0, 3, 7, 12, 13, 14, 10, 5, 4, 8, 9};
                    case 17 -> new int[]{17, 18, 15, 11, 6, 2, 1, 0, 3, 7, 12, 16, 13, 14, 10, 5, 4, 8, 9};
                    case 18 -> new int[]{18, 15, 11, 6, 2, 1, 0, 3, 7, 12, 16, 17, 14, 10, 5, 4, 8, 13, 9};
                    default -> throw new IllegalStateException("Unexpected value: " + first);
                };
            }
        }

        public static ValueGenerationStrategy random() {
            if (random == null) {
                if (Game.isGameInitialized()) {
                    random = Game.getInstance().getRandom();
                } else {
                    random = new Random();
                }
            }
            return random(random);
        }

        public static ValueGenerationStrategy random(Random random) {
            return new ValueGenerationStrategy(
                    values.get(random.nextInt(values.size())),
                    random.nextBoolean()
            );
        }

        public void generateValues(List<Hex> hexes) {
            int valuesIndex = 0;
            for (int i = 0; i < 19; i++) {
                int location = inOrder[i];
                if (hexes.get(location).getType() == Hex.Type.DESERT) continue;
                hexes.get(location).setValue(VALUES[valuesIndex++]);
            }
        }

    }

}
