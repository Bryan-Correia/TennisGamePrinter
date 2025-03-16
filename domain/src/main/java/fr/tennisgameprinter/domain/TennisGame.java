package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.ports.probe.GameStateProbe;

import java.util.List;

/**
 * A stateful representation of a tennis game.
 * @param <P> the type of players' identifier.
 */
public class TennisGame<P> {

    public TennisGame(Player<P> playerA, Player<P> playerB, final GameStateProbe<P> gameStateProbe) {
    }

    public void process(final List<Point<P>> points) {
    }

    public boolean isGameOver() {
        return false;
    }
}
