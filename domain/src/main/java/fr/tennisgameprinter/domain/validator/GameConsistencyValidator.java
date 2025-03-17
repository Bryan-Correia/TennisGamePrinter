package fr.tennisgameprinter.domain.validator;

import fr.tennisgameprinter.domain.TennisGame;
import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import fr.tennisgameprinter.domain.validator.listener.ProcessedPointsCounter;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Handles the validation of a sequence of {@link Point}, making sure that the provided points form a consistent game.
 * To do so, the validation process uses a {@link TennisGameFactory} to simulate a tennis game, while validating
 * that the game is consistent in terms of players, but also in terms of processed points.
 * @param <P> the type of players' identifier.
 */
public class GameConsistencyValidator<P> {

    /** The {@link TennisGameFactory} used to simulate tennis games. */
    private final TennisGameFactory<P> tennisGameFactory;
    /** The {@link Supplier} of {@link ProcessedPointsCounter} used to count the processed points when simulating the tennis game. */
    private final Supplier<ProcessedPointsCounter<P>> processedPointsCounterSupplier;

    /**
     * Creates a new {@link GameConsistencyValidator} with the provided {@link TennisGameFactory} and {@link Supplier} of {@link ProcessedPointsCounter}.
     * @param tennisGameFactory the {@link TennisGameFactory}
     * @param processedPointsCounterSupplier a {@link Supplier} of {@link ProcessedPointsCounter} used to count the processed points.
     */
    public GameConsistencyValidator(final TennisGameFactory<P> tennisGameFactory, final Supplier<ProcessedPointsCounter<P>> processedPointsCounterSupplier) {
        this.tennisGameFactory = Objects.requireNonNull(tennisGameFactory, "the provided tennis game factory must not be null.");
        this.processedPointsCounterSupplier = Objects.requireNonNull(processedPointsCounterSupplier, "the provided processed pointers counter supplier must not be null.");
    }

    /**
     * Validates that the provided player identifiers and {@link Point}s form a consistent game.
     * @param firstPlayer the identifier of the first player.
     * @param secondPlayer the identifier of the second player.
     * @param points a list of {@link Point}.
     * @throws InconsistentGameException when the game is inconsistent.
     */
    public void validate(final P firstPlayer, final P secondPlayer, final List<Point<P>> points) {
        validatePlayers(firstPlayer, secondPlayer);
        validateAllPointsBelongToPlayers(firstPlayer, secondPlayer, points);

        Player<P> playerA = new Player<>(firstPlayer);
        Player<P> playerB = new Player<>(secondPlayer);
        ProcessedPointsCounter<P> processedPointsCounter = processedPointsCounterSupplier.get();
        TennisGame<P> tennisGame = tennisGameFactory.create(playerA, playerB, processedPointsCounter);

        tennisGame.process(points);

        validateGameCompletion(points.size(), processedPointsCounter.getPointsCounter(), tennisGame.isGameOver());
    }

    /**
     * Validates the completion of a {@link TennisGame}.
     * @param totalPoints the total amount of {@link Point}s that required validation.
     * @param pointsProcessed how many {@link Point}s were actually processed when simulating the game.
     * @param isGameOver whether the {@link TennisGame} is ended after processing all {@link Point}s.
     */
    private void validateGameCompletion(final int totalPoints, final int pointsProcessed, final boolean isGameOver) {
        if (pointsProcessed != totalPoints) {
            throw new InconsistentGameException("The provided tennis game is inconsistent as it showcased more points than needed to end the game.");
        } else if (!isGameOver) {
            throw new InconsistentGameException("The provided tennis game is inconsistent as it showcased less points than needed to end the game.");
        }
    }

    /**
     * Validates that the provided {@link List} of {@link Point}s belong to the players whose identifier is provided.
     * @param firstPlayer the identifier of the first player.
     * @param secondPlayer the identifier of the second player.
     * @param points the {@link List} of {@link Point}s.
     */
    private void validateAllPointsBelongToPlayers(final P firstPlayer, final P secondPlayer, final List<Point<P>> points) {
        Set<P> playersIdSet = Set.of(firstPlayer, secondPlayer);
        for (Point<P> point : points) {
            if (!playersIdSet.contains(point.winner())) {
                throw new InconsistentGameException("The provided tennis game points contain a non-declared player.");
            }
        }
    }

    /**
     * Validates that the players are consistent for a {@link TennisGame}.
     * @param firstPlayer the identifier of the first player.
     * @param secondPlayer the identifier of the second player.
     */
    private void validatePlayers(P firstPlayer, P secondPlayer) {
        if (firstPlayer == null || secondPlayer == null || firstPlayer.equals(secondPlayer)) {
            throw new InconsistentGameException("A tennis game must have two different players.");
        }
    }
}
