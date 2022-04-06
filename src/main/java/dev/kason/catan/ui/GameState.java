package dev.kason.catan.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum GameState {
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
    @Getter
    @Setter
    private static GameState current = MENU;
}
