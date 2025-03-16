package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;
import fr.tennisgameprinter.domain.ports.deserializer.InputDeserializer;
import fr.tennisgameprinter.domain.ports.probe.GameStateProbe;
import fr.tennisgameprinter.domain.validator.GameConsistencyValidator;

/**
 * A service which processes a {@link TennisGameInput} by simulating the underlying tennis game, with the
 * provided {@link GameStateProbe} being notified of each point.
 * @param <I> the type of the input.
 * @param <P> the type of players' identifier.
 */
public class TennisGameProcessor<I, P> {

    /**
     * Creates a new {@link TennisGameProcessor} with the provided {@link InputDeserializer}, {@link GameConsistencyValidator},
     * {@link GameStateProbe} and {@link TennisGameFactory}.
     * @param inputDeserializer the {@link InputDeserializer} responsible for deserializing the input.
     * @param gameConsistencyValidator the {@link GameConsistencyValidator} which validates the consistency of the input.
     * @param gameStateProbe the {@link GameStateProbe} which gets notified of every point outcome.
     * @param tennisGameFactory the {@link TennisGameFactory} which allows creating {@link TennisGame} instances.
     */
    public TennisGameProcessor(final InputDeserializer<I, P> inputDeserializer,
                               final GameConsistencyValidator<P> gameConsistencyValidator,
                               final GameStateProbe<P> gameStateProbe,
                               final TennisGameFactory<P> tennisGameFactory) {
    }

    /**
     * Process the provided {@link TennisGameInput} and simulates the underlying tennis game.
     * After each point is processed, the {@link GameStateProbe} is notified of the current state of the game.
     * @param tennisGameInput the {@link TennisGameInput}.
     */
    public void process(final TennisGameInput<I, P> tennisGameInput) {
    }
}
