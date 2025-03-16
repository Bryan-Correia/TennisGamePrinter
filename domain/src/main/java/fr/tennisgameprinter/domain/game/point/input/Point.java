package fr.tennisgameprinter.domain.game.point.input;

/**
 * Represents a {@link Point} in a tennis game by holding the identifier of the winner.
 * @param winner the identifier of the winner.
 * @param <P> the type of the identifier of the winner.
 */
public record Point<P> (P winner) {

}
