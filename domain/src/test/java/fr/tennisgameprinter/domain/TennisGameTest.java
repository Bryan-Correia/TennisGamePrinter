package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class TennisGameTest {

    @Test
    void testProcess_withoutDeuce() {
        // GIVEN a list of Point instances which includes no deuce and results in player A's victory
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointB, pointA, pointA, pointA);
        // AND our two Player instances that are identified by those points
        Player<Character> playerA = new Player<>('A');
        Player<Character> playerB = new Player<>('B');
        // AND a GameStateListener instance
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND our TennisGame instance
        TennisGame<Character> tennisGame = new TennisGame<>(playerA, playerB, gameStateListener);
        // WHEN we attempt to process the points
        tennisGame.process(points);
        // THEN playerA has a score of 40
        assertEquals(40, playerA.getScore());
        // AND playerB has a score of 15
        assertEquals(15, playerB.getScore());
        // AND the GameStateListener was notified exactly four times for a regular point
        verify(gameStateListener, times(4)).onPoint(any());
        // AND the GameStateListener was notified of the expected regular points
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 0));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 40, 15));
        // AND the GameStateListener was notified of a single victory, which is player A's victory
        verify(gameStateListener).onVictory(any());
        verify(gameStateListener).onVictory(new VictoryPoint<>('A'));
        // AND the game is over
        assertTrue(tennisGame.isGameOver());
    }

    @Test
    void testProcess_withSingleDeuce() {
        // GIVEN a list of Point instances which includes a single deuce and results in player A's victory
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointB, pointA, pointB, pointA, pointB, pointA, pointA);
        // AND our two Player instances that are identified by those points
        Player<Character> playerA = new Player<>('A');
        Player<Character> playerB = new Player<>('B');
        // AND a GameStateListener instance
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND our TennisGame instance
        TennisGame<Character> tennisGame = new TennisGame<>(playerA, playerB, gameStateListener);

        // WHEN we attempt to process the points
        tennisGame.process(points);

        // THEN PlayerA and playerB both have a score of 40
        assertEquals(40, playerA.getScore());
        assertEquals(40, playerB.getScore());
        // AND the GameStateListener was notified exactly five times for a regular point
        verify(gameStateListener, times(5)).onPoint(any());
        // AND the GameStateListener was notified of each regular points
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 0));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 30));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 40, 30));
        // AND the GameStateListener was notified of a deuce
        verify(gameStateListener).onDeuce();
        // AND the GameStateListener was notified of playerA's advantage
        verify(gameStateListener).onAdvantageGained(new AdvantagePoint<>('A'));
        // AND the GameStateListener was never notified of any advantage for player B
        verify(gameStateListener, never()).onAdvantageGained(new AdvantagePoint<>('B'));
        // AND the GameStateListener was notified of a single victory, which is player A's victory
        verify(gameStateListener).onVictory(any());
        verify(gameStateListener).onVictory(new VictoryPoint<>('A'));
        // AND the game is over
        assertTrue(tennisGame.isGameOver());
    }

    @Test
    void testProcess_withSeveralDeuces() {
        // GIVEN a list of Point instances which includes several deuces and results in player A's victory
        Point<Character> pointA = new Point<>('A');
        Point<Character> pointB = new Point<>('B');
        List<Point<Character>> points = List.of(pointA, pointB, pointA, pointB, pointA, pointB, pointA, pointB, pointA, pointB, pointA, pointA);
        // AND our two Player instances that are identified by those points
        Player<Character> playerA = new Player<>('A');
        Player<Character> playerB = new Player<>('B');
        // AND a GameStateListener instance
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND our TennisGame instance
        TennisGame<Character> tennisGame = new TennisGame<>(playerA, playerB, gameStateListener);

        // WHEN we attempt to process the points
        tennisGame.process(points);

        // THEN playerA and playerB both have a score of 40
        assertEquals(40, playerA.getScore());
        assertEquals(40, playerB.getScore());
        // AND the GameStateListener was notified exactly five times for a regular point
        verify(gameStateListener, times(5)).onPoint(any());
        // AND the GameStateListener was notified of the expected regular points
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 0));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 15));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 30));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 40, 30));
        // AND the GameStateListener was notified of three deuces
        verify(gameStateListener, times(3)).onDeuce();
        // AND the GameStateListener was notified of player A's advantage three times
        verify(gameStateListener, times(3)).onAdvantageGained(new AdvantagePoint<>('A'));
        // AND the GameStateListener was never notified of any advantage for player B
        verify(gameStateListener, never()).onAdvantageGained(new AdvantagePoint<>('B'));
        // AND the GameStateListener was notified of a single victory, which is player A's victory
        verify(gameStateListener).onVictory(any());
        verify(gameStateListener).onVictory(new VictoryPoint<>('A'));
        // AND the game is over
        assertTrue(tennisGame.isGameOver());
    }

    @Test
    void testProcess_withoutDeuce_withMorePointsThanATrueGame() {
        // GIVEN a list of Point instances which includes no deuce and results in player A's victory, yet the game is inconsistent as it showcases more points than needed
        Point<Character> pointA = new Point<>('A');
        List<Point<Character>> points = List.of(pointA, pointA, pointA, pointA, pointA);
        // AND our two Player instances that are identified by those points
        Player<Character> playerA = new Player<>('A');
        Player<Character> playerB = new Player<>('B');
        // AND a GameStateListener instance
        GameStateListener<Character> gameStateListener = mock(GameStateListener.class);
        // AND our TennisGame instance
        TennisGame<Character> tennisGame = new TennisGame<>(playerA, playerB, gameStateListener);

        // WHEN we attempt to process the points
        tennisGame.process(points);

        // THEN playerA has a score of 40
        assertEquals(40, playerA.getScore());
        // AND the GameStateListener was notified exactly three times for a regular point
        verify(gameStateListener, times(3)).onPoint(any());
        // AND the GameStateListener was notified of the expected regular points
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 15, 0));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 30, 0));
        verify(gameStateListener).onPoint(new RegularPoint<>('A', 'B', 40, 0));
        // AND the GameStateListener was never notified on any deuce
        verify(gameStateListener, never()).onDeuce();
        // AND the GameStateListener was notified of playerA's victory
        verify(gameStateListener).onVictory(any());
        verify(gameStateListener).onVictory(new VictoryPoint<>('A'));
        // AND the game is over
        assertTrue(tennisGame.isGameOver());
    }


}