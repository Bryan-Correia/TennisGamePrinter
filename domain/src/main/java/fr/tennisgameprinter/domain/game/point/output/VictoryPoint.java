package fr.tennisgameprinter.domain.game.point.output;

/**
 * A record which represents a victory point.
 * @param winner the identifier of the winner.
 * @param <P> the type of the winner's identifier.
 */
public record VictoryPoint<P> (P winner) {
}
