package fr.tennisgameprinter.domain.validator;

import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import fr.tennisgameprinter.domain.validator.listener.ProcessedPointsCounter;

import java.util.List;
import java.util.function.Supplier;

/**
 * Handles the validation of a sequence of {@link Point}, making sure that the provided points form a consistent game.
 * To do so, the validation process uses a {@link TennisGameFactory} to simulate a tennis game, while validating
 * that the game is consistent in terms of players, but also in terms of processed points.
 * @param <P> the type of players' identifier.
 */
public class GameConsistencyValidator<P> {

    /**
     * Creates a new {@link GameConsistencyValidator} with the provided {@link TennisGameFactory} and {@link Supplier} of {@link ProcessedPointsCounter}.
     * @param tennisGameFactory the {@link TennisGameFactory}
     * @param processedPointsCounterSupplier a {@link Supplier} of {@link ProcessedPointsCounter} used to count the processed points.
     */
    public GameConsistencyValidator(final TennisGameFactory<P> tennisGameFactory, final Supplier<ProcessedPointsCounter<P>> processedPointsCounterSupplier) {
    }

    /**
     * Validates that the provided player identifiers and {@link Point}s form a consistent game.
     * @param firstPlayer the identifier of the first player.
     * @param secondPlayer the identifier of the second player.
     * @param points a list of {@link Point}.
     * @throws InconsistentGameException when the game is inconsistent.
     */
    public void validate(final P firstPlayer, final P secondPlayer, final List<Point<P>> points) {
    }
}
