package hanabi.message;

public enum MessageType {
	START, QUIT, TURNSTART, TURNACTION, NEWCARD, TURNEND, STATUS_COLOR_HINT, STATUS_NUMBER_HINT, STATUS_PLAYED_CARD, STATUS_GAME_END, STATUS_PLAYER_CARDS, STATUS_HINT_STORM_COUNT, STATUS_DISCARDED_CARD, STATUS_BOARD_INFORMATION, STATUS_CARDS_LEFT_IN_DECK, STATUS_PLAYER_NAMES, TURNACTION_NOT_POSSIBLE;
}