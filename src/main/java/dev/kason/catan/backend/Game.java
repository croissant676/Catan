package dev.kason.catan.backend;

import dev.kason.catan.backend.board.Board;
import dev.kason.catan.backend.board.Hex;
import dev.kason.catan.backend.board.Resource;
import dev.kason.catan.backend.board.Vertex;
import dev.kason.catan.backend.player.Player;
import javafx.util.Pair;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unused")
@Slf4j
@Getter
public class Game {


    public static final Map<Resource, Integer> COST_OF_ROAD = Map.of(
            Resource.BRICK, 1,
            Resource.WOOD, 1
    );
    public static final Map<Resource, Integer> COST_OF_SETTLEMENT = Map.of(
            Resource.BRICK, 1,
            Resource.WOOD, 1,
            Resource.WHEAT, 1,
            Resource.SHEEP, 1
    );
    public static final Map<Resource, Integer> COST_OF_CITY = Map.of(
            Resource.WHEAT, 2,
            Resource.ORE, 3
    );
    public static final Map<Resource, Integer> COST_OF_DEV_CARD = Map.of(
            Resource.WHEAT, 1,
            Resource.SHEEP, 1,
            Resource.ORE, 1
    );

    private static Game instance;
    private final Random random;
    private Board board;
    private final List<Player> players;
    private int diceRoll;
    private int turn;
    private final int longestRoad = 0;
    private final int largestArmy = 0;

    public Game(List<Player.Color> playerOrder, long seed) {
        if (instance != null) {
            throw new IllegalStateException("Game already exists");
        }
        instance = this;
        log.debug("Game(playerOrder={}, seed={})", playerOrder, seed);
        if (seed == 0) {
            random = new Random();
        } else {
            random = new Random(seed);
        }
        players = new ArrayList<>(playerOrder.size());
        for (int index = 0; index < playerOrder.size(); index++) {
            Player player = new Player(playerOrder.get(index), index);
            players.add(player);
        }
        try {
            this.board = new Board(random, Board.ValueGenerationStrategy.random());
        } catch (Exception e) {
            log.error("Failed to create board", e);
        }
        this.turn = 0;
        this.diceRoll = -1;
    }

    public static Game getInstance() {
        if (instance == null) {
            log.info("Game is yet to be created.");
        }
        return instance;
    }

    public static boolean isGameInitialized() {
        return instance != null;
    }

    public Player getCurrentPlayer() {
        return players.get(turn);
    }

    public Pair<Integer, Integer> rollDice() {
        int num1 = random.nextInt(6) + 1;
        int num2 = random.nextInt(6) + 1;
        diceRoll = num1 + num2;
        Pair<Integer, Integer> pair = new Pair<>(num1, num2);
        log.debug("Rolled a {}, sums to {}", pair, diceRoll);
        for (Hex hex : board.getHexes()) {
            if (hex.getValue() != diceRoll) continue;
            for (Vertex vertex : hex.getVertices().values()) {
                if (!vertex.hasConstruction()) continue;
                Player player = vertex.getPlayer();
                player.addResource(hex.getType().getResource(), vertex.isCity() ? 2 : 1);
            }
        }
        return pair;
    }

    public List<Vertex> getSettlementsFor(int value) {
        List<Vertex> vertices = new ArrayList<>();
        for (Hex hex : board.getHexes()) {
            if (hex.getValue() != value) continue;
            for (Vertex vertex : hex.getVertices().values()) {
                if (vertex.hasConstruction()) {
                    vertices.add(vertex);
                }
            }
        }
        return vertices;
    }

    public boolean trade(Player p1, Player p2, Map<Resource, Integer> offer, Map<Resource, Integer> request) {
        if (p1 == null) {
            log.error("Player 1 is null");
            return false;
        } else if (p2 == null) {
            log.error("Player 2 is null");
            return false;
        } else if (offer == null) {
            log.error("Offer is null");
            return false;
        } else if (request == null) {
            log.error("Request is null");
            return false;
        }
        log.debug("Trade(p1={}, p2={}, offer={}, request={})", p1, p2, offer, request);
        if (!p1.hasResources(offer)) {
            log.debug("Player {} does not have the resources to offer", p1);
            return false;
        } else if (!p2.hasResources(request)) {
            log.debug("Player {} does not have the resources to request", p2);
            return false;
        }
        p2.addResources(offer);
        p1.removeResources(offer);
        p1.addResources(request);
        p2.removeResources(request);
        return true;
    }

    public boolean buildSettlement(Vertex vertex, Player player) {
        if (!player.hasResources(COST_OF_SETTLEMENT)) {
            log.debug("Player {} does not have the resources to build a settlement", player);
            return false;
        }
        if (vertex.hasConstruction()) {
            log.debug("Vertex {} already has a settlement", vertex);
            return false;
        }
        if (!board.getValidBuildingPoints().contains(vertex)) {
            log.debug("Vertex {} is not a valid building point", vertex);
            return false;
        }
        player.removeResources(COST_OF_SETTLEMENT);
        vertex.buildSettlement(player);
        return true;
    }

    public boolean upgradeCity(Vertex vertex, Player player) {
        if (vertex == null) {
            log.debug("Vertex is null");
            return false;
        }
        if (player == null) {
            log.debug("Player is null for upgradeCity");
            player = getCurrentPlayer();
        }
        log.debug("CreateSettlement(vertex={}, player={})", vertex, player);
        if (!vertex.hasConstruction()) {
            log.debug("Vertex {} is not available for construction", vertex);
            return false;
        } else if (vertex.getPlayer() != player) {
            log.debug("Vertex {} is not owned by player {}", vertex, player);
            return false;
        } else if (!player.hasResources(COST_OF_CITY)) {
            log.debug("Player {} does not have the resources to upgrade city", player);
            return false;
        }
        vertex.buildCity();
        player.removeResources(COST_OF_CITY);
        return true;
    }

    public Player nextTurn() {
        turn++;
        return getCurrentPlayer();
    }

}
