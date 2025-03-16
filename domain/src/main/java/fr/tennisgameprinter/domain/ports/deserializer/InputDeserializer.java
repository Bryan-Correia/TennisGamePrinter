package fr.tennisgameprinter.domain.ports.deserializer;

import fr.tennisgameprinter.domain.game.point.input.Point;

import java.util.List;

/**
 * Represents a deserializer of an input of type {@link I} which represents a sequence of tennis points
 * identified by their respective winner, and returns a {@link List} of {@link Point} instances where each winner is
 * identified by an identifier of type {@link P}.
 * @param <I> the type of the input.
 * @param <P> the type of the players' identifier.
 */
public interface InputDeserializer<I, P> {
    List<Point<P>> deserialize(final I input);
}
