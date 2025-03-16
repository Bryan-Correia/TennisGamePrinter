package fr.tennisgameprinter.domain.game.point.output;

/**
 * A record which represents a regular tennis point when no deuce has occurred yet.
 * @param playerId the identifier of the first player.
 * @param otherPlayerId the identifier of the second player.
 * @param playerScore the score of the first player.
 * @param otherPlayerScore the score of the second player.
 * @param <P> the type of players' identifier.
 */
public record RegularPoint<P> (P playerId, P otherPlayerId, int playerScore, int otherPlayerScore) {
}
