package fr.tennisgameprinter.domain.validator;

import fr.tennisgameprinter.domain.game.point.Point;

import java.util.List;

/**
 * Handles the validation of a sequence of {@link Point}, making sure that the provided points form a consistent game.
 * @param <P> the type of players' identifier.
 */
public class GameConsistencyValidator<P> {

    public void validate(final P firstPlayer, final P secondPlayer, final List<Point<P>> points) {
    }
}
