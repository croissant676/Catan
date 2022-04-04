package dev.kason.catan.backend.board;

import dev.kason.catan.backend.player.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Vertex {

    private final List<Hex> hexes = new ArrayList<>();
    private Port port;
    private boolean isCity;
    private Player player;

    public boolean isNear(Hex hex) {
        return hexes.contains(hex);
    }

    public boolean buildCity() {
        if (isCity) {
            return false;
        }
        isCity = true;
        return true;
    }

    public boolean buildSettlement(Player player) {
        if (this.player != null) {
            return false;
        }
        this.player = player;
        return true;
    }

    public boolean isSettlement() {
        return player != null && !isCity;
    }

    public boolean hasConstruction() {
        return player != null;
    }

    public void setPort(Port port) {
        if (hexes.size() == 3) {
            throw new IllegalStateException("Vertex is an inland intersection, cannot have a port");
        }
        if (this.port != null) {
            throw new IllegalStateException("Vertex already has a port");
        }
        this.port = port;
    }

    public boolean hasPort() {
        return port != null;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "hexes=" + hexes.stream()
                .map(Hex::getId)
                .collect(Collectors.toList()) +
                ", port=" + port +
                ", isCity=" + isCity +
                ", player=" + player +
                '}';
    }
}
