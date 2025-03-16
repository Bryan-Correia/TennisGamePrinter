package fr.tennisgameprinter.domain.game.point.input;

import fr.tennisgameprinter.domain.game.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    public void testIsWonBy() {
        // GIVEN a Player identified by 'A'
        Player<Character> player = new Player<>('A');
        // AND a Point won by that player
        Point<Character> point = new Point<>(player.getId());
        // WHEN we check through the point's isWonBy() method whether it was won by that player
        boolean isWonByPlayer = point.isWonBy(player);
        // THEN it was won by that player
        assertTrue(isWonByPlayer);
    }
}