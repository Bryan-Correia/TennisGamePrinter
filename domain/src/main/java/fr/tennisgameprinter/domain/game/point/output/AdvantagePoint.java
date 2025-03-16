package fr.tennisgameprinter.domain.game.point.output;

/**
 * A record which represents a tennis point where the advantage was obtained by a player.
 * @param advantagedPlayerId the identifier of the player who obtained the advantage.
 * @param <P> the type of the player's identifier.
 */
public record AdvantagePoint<P> (P advantagedPlayerId) {
}
