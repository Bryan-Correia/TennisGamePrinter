package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;
import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.ports.deserializer.InputDeserializer;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;
import fr.tennisgameprinter.domain.validator.GameConsistencyValidator;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TennisGameProcessorTest {

    @Test
    void testProcess() {
        // GIVEN a String input that represents a sequence of points between players identified as 'A' and 'B'
        String input = "AAABBA";
        // AND an InputDeserializer that deserializes a String and returns a list of Points that identifies each winner as a Character
        InputDeserializer<String, Character> inputDeserializer = mock(InputDeserializer.class);
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointB, pointB, pointA);
        when(inputDeserializer.deserialize(input)).thenReturn(points);
        // AND a GameConsistencyValidator
        GameConsistencyValidator<Character> gameConsistencyValidator = mock(GameConsistencyValidator.class);
        // AND a GameStateListener
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND a TennisGameFactory that allows creating a TennisGame
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        TennisGame<Character> tennisGame = mock(TennisGame.class);
        when(tennisGameFactory.create(any(Player.class), any(Player.class), eq(gameStateListener))).thenReturn(tennisGame);
        // AND our TennisGameProcessor built from the previously defined dependencies
        TennisGameProcessor<String, Character> tennisGameProcessor = new TennisGameProcessor<>(inputDeserializer, gameConsistencyValidator, gameStateListener, tennisGameFactory);
        // AND our TennisGameInput which defines that our tennis game is composed of our input and players identified by 'A' and 'B'
        TennisGameInput<String, Character> tennisGameInput = new TennisGameInput<>(input, 'A', 'B');

        // WHEN we process the TennisGameInput
        tennisGameProcessor.process(tennisGameInput);

        // THEN our input deserializer was properly called to deserialize the input
        verify(inputDeserializer).deserialize(input);
        // AND the game consistency validator has validated that the sequence of points was valid
        verify(gameConsistencyValidator).validate('A', 'B', points);
        // AND the tennis game factory was created with the expected players and GameStateListener
        verify(tennisGameFactory).create(
                argThat(player -> player.getId().equals('A')),
                argThat(player -> player.getId().equals('B')),
                eq(gameStateListener)
        );
        // AND the tennis game factory has processed our points
        verify(tennisGame).process(points);
    }

    @Test
    void testProcess_withValidationException() {
        // GIVEN a String input that represents a sequence of points between players identified as 'A' and 'B', yet forms an inconsistent (unfinished) game
        String input = "AAABB";
        // AND a deserializer that deserializes a String and returns a list of Points that identifies each player as a Character
        InputDeserializer<String, Character> inputDeserializer = mock(InputDeserializer.class);
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointB, pointB, pointA);
        when(inputDeserializer.deserialize(input)).thenReturn(points);
        // AND a GameConsistencyValidator
        GameConsistencyValidator<Character> gameConsistencyValidator = mock(GameConsistencyValidator.class);
        doThrow(new InconsistentGameException("the game is inconsistent")).when(gameConsistencyValidator).validate('A', 'B', points);
        // AND a GameStateListener
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND a TennisGameFactory that allows creating a TennisGame
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        TennisGame<Character> tennisGame = mock(TennisGame.class);
        when(tennisGameFactory.create(any(Player.class), any(Player.class), eq(gameStateListener))).thenReturn(tennisGame);
        // AND our TennisGameProcessor built from the previously defined dependencies
        TennisGameProcessor<String, Character> tennisGameProcessor = new TennisGameProcessor<>(inputDeserializer, gameConsistencyValidator, gameStateListener, tennisGameFactory);
        // AND our TennisGameInput which defines that our tennis game is composed of our input and players identified by 'A' and 'B'
        TennisGameInput<String, Character> tennisGameInput = new TennisGameInput<>(input, 'A', 'B');

        // WHEN we try to process the input, THEN an InconsistentGameException is caught
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class, () -> tennisGameProcessor.process(tennisGameInput));
        // AND its message matches our expectations
        assertEquals("the game is inconsistent", inconsistentGameException.getMessage());
    }

}
