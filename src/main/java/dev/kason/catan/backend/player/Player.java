package dev.kason.catan.backend.player;

import dev.kason.catan.backend.board.Resource;
import dev.kason.catan.backend.board.Vertex;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class Player {

    private final int id;
    private final Color color;
    private final Map<Resource, Integer> resources;
    private final Set<Vertex> vertices = new HashSet<>();
    private final Set<DevCard> devCards = new HashSet<>();
    private int victoryPoints = 0;
    private int victoryPointsWithoutDev = 0;
    private int armySize = 0;

    public Player(Color color, int id) {
        this.color = color;
        this.id = id;
        this.resources = new HashMap<>(6);
    }

    public void addResource(Resource resource, int amount) {
        resources.put(resource, resources.getOrDefault(resource, 0) + amount);
    }

    public void removeResource(Resource resource, int amount) {
        resources.put(resource, resources.getOrDefault(resource, 0) - amount);
    }

    public void addResources(Map<Resource, Integer> resources) {
        resources.forEach((k, v) -> this.resources.put(k, this.resources.getOrDefault(k, 0) + v));
    }

    public void removeResources(Map<Resource, Integer> resources) {
        resources.forEach((k, v) -> this.resources.put(k, this.resources.getOrDefault(k, 0) - v));
    }

    public boolean hasResources(Map<Resource, Integer> resources) {
        for (Map.Entry<Resource, Integer> entry : resources.entrySet()) {
            if (this.resources.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
                return false;
            }
        }
        return true;
    }

    public enum Color {
        RED, WHITE, ORANGE, BLUE
    }

}
