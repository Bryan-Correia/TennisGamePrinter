package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;
import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.ports.deserializer.InputDeserializer;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;
import fr.tennisgameprinter.domain.validator.GameConsistencyValidator;

import java.util.List;
import java.util.Objects;

/**
 * A service which processes a {@link TennisGameInput} by simulating the underlying tennis game, with the
 * provided {@link GameStateListener} being notified of each point.
 * @param <I> the type of the input.
 * @param <P> the type of players' identifier.
 */
public class TennisGameProcessor<I, P> {

    private final InputDeserializer<I, P> inputDeserializer;
    private final GameConsistencyValidator<P> gameConsistencyValidator;
    private final GameStateListener<P> gameStateListener;
    private final TennisGameFactory<P> tennisGameFactory;

    /**
     * Creates a new {@link TennisGameProcessor} with the provided {@link InputDeserializer}, {@link GameConsistencyValidator},
     * {@link GameStateListener} and {@link TennisGameFactory}.
     * @param inputDeserializer the {@link InputDeserializer} responsible for deserializing the input.
     * @param gameConsistencyValidator the {@link GameConsistencyValidator} which validates the consistency of the input.
     * @param gameStateListener the {@link GameStateListener} which gets notified of every point outcome.
     * @param tennisGameFactory the {@link TennisGameFactory} which allows creating {@link TennisGame} instances.
     */
    public TennisGameProcessor(final InputDeserializer<I, P> inputDeserializer,
                               final GameConsistencyValidator<P> gameConsistencyValidator,
                               final GameStateListener<P> gameStateListener,
                               final TennisGameFactory<P> tennisGameFactory) {
        this.inputDeserializer = Objects.requireNonNull(inputDeserializer, "the provided input deserializer must not be null.");
        this.gameConsistencyValidator = Objects.requireNonNull(gameConsistencyValidator, "the provided points consistency validator must not be null.");
        this.gameStateListener = Objects.requireNonNull(gameStateListener, "the provided game state listener must not be null.");
        this.tennisGameFactory = Objects.requireNonNull(tennisGameFactory," the provided tennis game factory must not be null.");
    }

    /**
     * Process the provided {@link TennisGameInput} and simulates the underlying tennis game.
     * After each point is processed, the {@link GameStateListener} is notified of the current state of the game.
     * @param tennisGameInput the {@link TennisGameInput}.
     */
    public void process(final TennisGameInput<I, P> tennisGameInput) {
        List<Point<P>> points = inputDeserializer.deserialize(tennisGameInput.input());
        gameConsistencyValidator.validate(tennisGameInput.firstPlayerId(), tennisGameInput.secondPlayerId(), points);
        Player<P> firstPlayer = new Player<>(tennisGameInput.firstPlayerId());
        Player<P> secondPlayer = new Player<>(tennisGameInput.secondPlayerId());
        tennisGameFactory.create(firstPlayer, secondPlayer, gameStateListener).process(points);
    }
}
