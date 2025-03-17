package fr.tennisgameprinter.domain.game.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    void testConstructor() {
        // WHEN we create a new player identified as 'A'
        Player<Character> player = new Player<>('A');
        // THEN it is identified as 'A'
        assertEquals('A', player.getId());
        // AND it has a score of zero
        assertEquals(0, player.getScore());
    }

    @Test
    void testWinsPointOver() {
        // GIVEN our first player identified by character 'A'
        Player<Character> firstPlayer = new Player<>('A');
        // AND our second player identified by character 'B'
        Player<Character> secondPlayer = new Player<>('B');

        // WHEN firstPlayer wins a point over secondPlayer
        firstPlayer.winsPointOver(secondPlayer);
        // THEN the score matches our expectations
        assertEquals(15, firstPlayer.getScore());

        // WHEN firstPlayer wins a second point over secondPlayer
        firstPlayer.winsPointOver(secondPlayer);
        // THEN the score matches our expectations
        assertEquals(30, firstPlayer.getScore());

        // WHEN firstPlayer wins a third point over secondPlayer
        firstPlayer.winsPointOver(secondPlayer);
        // THEN the score matches our expectations
        assertEquals(40, firstPlayer.getScore());

        // WHEN secondPlayer catches up
        secondPlayer.winsPointOver(firstPlayer);
        secondPlayer.winsPointOver(firstPlayer);
        secondPlayer.winsPointOver(firstPlayer);
        // THEN the scores match our expectations
        assertEquals(40, firstPlayer.getScore());
        assertEquals(40, secondPlayer.getScore());

        // WHEN firstPlayer gets advantage
        firstPlayer.winsPointOver(secondPlayer);
        // THEN both players' state match our expectation
        assertEquals(40, firstPlayer.getScore());
        assertEquals(40, secondPlayer.getScore());
        assertTrue(firstPlayer.hasAdvantage());
        assertFalse(secondPlayer.hasAdvantage());

        // WHEN firstPlayer loses advantage
        secondPlayer.winsPointOver(firstPlayer);
        // THEN both players' state match our expectation
        assertEquals(40, firstPlayer.getScore());
        assertEquals(40, secondPlayer.getScore());
        assertFalse(firstPlayer.hasAdvantage());
        assertFalse(secondPlayer.hasAdvantage());
    }

}