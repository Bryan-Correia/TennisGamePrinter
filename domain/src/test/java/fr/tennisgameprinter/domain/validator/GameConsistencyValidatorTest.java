package fr.tennisgameprinter.domain.validator;

import fr.tennisgameprinter.domain.TennisGame;
import fr.tennisgameprinter.domain.game.factory.TennisGameFactory;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import fr.tennisgameprinter.domain.validator.listener.ProcessedPointsCounter;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GameConsistencyValidatorTest {

    @Test
    public void testValidate_withNullFirstPlayer() {
        // GIVEN a ProcessedPointsCounter instance
        ProcessedPointsCounter<Character> processedPointsCounter = mock(ProcessedPointsCounter.class);
        // AND a TennisGameFactory
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate try to validate a list of points with a null first player
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate(null, 'B', List.of()),
                "An exception should have been caught as the first player provided is null.");
        // AND its message matches our expectation
        assertEquals("A tennis game must have two players.", inconsistentGameException.getMessage());
    }

    @Test
    public void testValidate_withNullSecondPlayer() {
        // GIVEN a ProcessedPointsCounter instance
        ProcessedPointsCounter<Character> processedPointsCounter = (ProcessedPointsCounter<Character>) mock(ProcessedPointsCounter.class);
        // AND a TennisGameFactory
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate try to validate a list of points with a null second player
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate('A', null, List.of()),
                "An exception should have been caught as the first player provided is null.");
        // AND its message matches our expectation
        assertEquals("A tennis game must have two players.", inconsistentGameException.getMessage());
    }

    @Test
    public void testValidate_withSameFirstAndSecondPlayer() {
        // GIVEN a ProcessedPointsCounter instance
        ProcessedPointsCounter<Character> processedPointsCounter = mock(ProcessedPointsCounter.class);
        // AND a TennisGameFactory
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate try to validate a list of points with a first and second players that are the same
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate('A', 'A', List.of()),
                "An exception should have been caught as the first player provided is null.");
        // AND its message matches our expectation
        assertEquals("A tennis game must have two players.", inconsistentGameException.getMessage());
    }

    @Test
    public void testValidate_withPlayerAndPointMismatch() {
        // GIVEN a list of Point where each winner is identified by a Character
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointA);
        // AND a ProcessedPointsCounter instance that is expected to return the same amount of points
        ProcessedPointsCounter<Character> processedPointsCounter = (ProcessedPointsCounter<Character>) mock(ProcessedPointsCounter.class);
        when(processedPointsCounter.getPointsCounter()).thenReturn(points.size());
        // AND a TennisGameFactory that returns a TennisGame
        TennisGame<Character> tennisGame = mock(TennisGame.class);
        when(tennisGame.isGameOver()).thenReturn(true);
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        when(tennisGameFactory.create(
                argThat(player -> player.getId().equals('A')),
                argThat(player -> player.getId().equals('B')),
                eq(processedPointsCounter))
        ).thenReturn(tennisGame);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate the input for players identified by 'C' and 'D', which does not match any winner of those points
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate('C', 'D', points),
                "An exception should have been caught as there is a mismatch between the points' winner and the provided players.");
        // AND its message matches our expectation
        assertEquals("The provided tennis game points contain a non-declared player.", inconsistentGameException.getMessage());
    }

    @Test
    public void testValidate() {
        // GIVEN a list of Point where each winner is identified by a Character
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointA);
        // AND a ProcessedPointsCounter instance that is expected to return the same amount of points
        ProcessedPointsCounter<Character> processedPointsCounter = mock(ProcessedPointsCounter.class);
        when(processedPointsCounter.getPointsCounter()).thenReturn(points.size());
        // AND a TennisGameFactory that returns a TennisGame
        TennisGame<Character> tennisGame = (TennisGame<Character>) mock(TennisGame.class);
        when(tennisGame.isGameOver()).thenReturn(true);
        TennisGameFactory<Character> tennisGameFactory = (TennisGameFactory<Character>) mock(TennisGameFactory.class);
        when(tennisGameFactory.create(
                argThat(player -> player.getId().equals('A')),
                argThat(player -> player.getId().equals('B')),
                eq(processedPointsCounter))
        ).thenReturn(tennisGame);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate the input for players identified by 'A' and 'B'
        assertDoesNotThrow(() -> gameConsistencyValidator.validate('A', 'B', points));

        // THEN no exception was thrown.
        // AND the points were processed by the TennisGame instance before we checked whether the game is over
        InOrder inOrder = inOrder(tennisGame);
        inOrder.verify(tennisGame).process(points);
        inOrder.verify(tennisGame).isGameOver();
        // AND the processed points counter was consulted to know the actual number of points processed
        verify(processedPointsCounter).getPointsCounter();
    }

    @Test
    public void testValidate_withMorePointsThanNeededToCloseTheGame() {
        // GIVEN a list of Point where each winner is identified by a Character
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointA);
        // AND a ProcessedPointsCounter instance that is expected to not return the same amount of points
        ProcessedPointsCounter<Character> processedPointsCounter = mock(ProcessedPointsCounter.class);
        when(processedPointsCounter.getPointsCounter()).thenReturn(points.size() - 1);
        // AND a TennisGameFactory that returns a TennisGame
        TennisGame<Character> tennisGame = mock(TennisGame.class);
        when(tennisGame.isGameOver()).thenReturn(true);
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        when(tennisGameFactory.create(
                argThat(player -> player.getId().equals('A')),
                argThat(player -> player.getId().equals('B')),
                eq(processedPointsCounter))
        ).thenReturn(tennisGame);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate the input for players identified by 'A' and 'B'
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate('A', 'B', points),
                "An InconsistentGameException should have been thrown as there were more points than needed to actually end the game.");
        // AND its message matches our expectation
        assertEquals("The provided tennis game is inconsistent has it showcased more points than needed to end the game.", inconsistentGameException.getMessage());
    }

    @Test
    public void testValidate_withLessPointsThanNeededToCloseTheGame() {
        // GIVEN a list of Point where each winner is identified by a Character
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointB, pointA);
        // AND a ProcessedPointsCounter instance that is expected to not return the same amount of points
        ProcessedPointsCounter<Character> processedPointsCounter = mock(ProcessedPointsCounter.class);
        when(processedPointsCounter.getPointsCounter()).thenReturn(points.size());
        // AND a TennisGameFactory that returns a TennisGame that we expect will not be finished as there were not enough points to actually end the game
        TennisGame<Character> tennisGame = mock(TennisGame.class);
        when(tennisGame.isGameOver()).thenReturn(false);
        TennisGameFactory<Character> tennisGameFactory = mock(TennisGameFactory.class);
        when(tennisGameFactory.create(
                argThat(player -> player.getId().equals('A')),
                argThat(player -> player.getId().equals('B')),
                eq(processedPointsCounter))
        ).thenReturn(tennisGame);
        // AND our validator
        GameConsistencyValidator<Character> gameConsistencyValidator = new GameConsistencyValidator<>(tennisGameFactory, () -> processedPointsCounter);

        // WHEN we validate the input for players identified by 'A' and 'B'
        // THEN an InconsistentGameException is thrown
        InconsistentGameException inconsistentGameException = assertThrows(InconsistentGameException.class,
                () -> gameConsistencyValidator.validate('A', 'B', points),
                "An InconsistentGameException should have been thrown as there were not enough points to end the game.");
        // AND its message matches our expectation
        assertEquals("The provided tennis game is inconsistent has it showcased less points than needed to end the game.", inconsistentGameException.getMessage());
        // AND the points were processed by the TennisGame instance before we checked whether the game is over
        InOrder inOrder = inOrder(tennisGame);
        inOrder.verify(tennisGame).process(points);
        inOrder.verify(tennisGame).isGameOver();
    }

}