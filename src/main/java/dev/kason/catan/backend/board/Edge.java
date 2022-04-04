package dev.kason.catan.backend.board;

import dev.kason.catan.backend.player.Player;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class Edge {
    private final Hex[] hexes;
    private Player player;

    void check() {
        if (hexes[0] == null || hexes[1] == null) {
            throw new IllegalStateException("edge must have two hexes");
        }
        if (!hexes[0].isNextTo(hexes[1])) {
            throw new IllegalStateException("hexes must be next to each other");
        }
    }

    public boolean isEmpty() {
        return player == null;
    }

    public boolean isOwnedBy(Player player) {
        return this.player == player;
    }

    public void setOwner(Player player) {
        this.player = player;
    }

    public void clear() {
        player = null;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "hexes=" + Hex.idOnly(List.of(hexes)) +
                ", player=" + player +
                '}';
    }
}