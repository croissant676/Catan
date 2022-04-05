package dev.kason.catan.ui;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum GameState {
    INIT,
    MENU,
    VIEW_RULES,
    DOWNLOAD_PDF,
    VIEW_SPECIAL_RULES,
    SELECT_PLAYERS,
    SELECT_MAP,
    CUSTOM_MAP_CONF,
    GAME_SETTINGS,
    GAME_INIT,
    GAME_PLAY,
    GAME_FINISH,
    CONTINUE_GAME,
    NAV_TO_GAME_CONF;

    private static GameState current = null;

    public static GameState getCurrent() {
        return current;
    }

    public static void setCurrent(GameState state) {
        current = state;
        log.info("Game state was changed to `{}`", state.name().toLowerCase());
    }
}
