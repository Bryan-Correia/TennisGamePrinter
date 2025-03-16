package fr.tennisgameprinter.domain.game.input;

/**
 * Represents a raw tennis game, that is, the identifiers of the two players, and an input of type {@link I} representing
 * the sequence of points, where each point holds a winner identified by a type {@link P}.
 * @param input the input that represents a sequence of points, where each point holds a winner.
 * @param firstPlayerId the identifier of the first player.
 * @param secondPlayerId the identifier of the second player.
 * @param <I> the type of the input.
 * @param <P> the type of players' identifier.
 */
public record TennisGameInput<I, P> (I input, P firstPlayerId, P secondPlayerId) {
}
