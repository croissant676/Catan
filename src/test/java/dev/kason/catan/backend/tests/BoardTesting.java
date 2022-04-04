package dev.kason.catan.backend.tests;

import dev.kason.catan.backend.board.Board;
import dev.kason.catan.backend.board.Hex;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.stream.Collectors;

public class BoardTesting {

    @Test
    public void testBoard() {
        Board b = new Board(new Random(), null);
        b.getHexes().forEach(hex -> System.out.println(hex.getId() + " " + hex.getType() + " " +
                hex.getHexes().values().stream().map(Hex::getId).collect(Collectors.toList())));
    }
}
