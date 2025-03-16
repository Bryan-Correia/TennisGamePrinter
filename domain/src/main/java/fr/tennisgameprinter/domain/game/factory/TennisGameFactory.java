package fr.tennisgameprinter.domain.game.factory;

import fr.tennisgameprinter.domain.TennisGame;
import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.ports.probe.GameStateProbe;

/**
 * Represents a class which handles the creation of a {@link TennisGame}.
 * @param <P> the type of players' identifier.
 */
@FunctionalInterface
public interface TennisGameFactory<P> {
    TennisGame<P> create(Player<P> playerA, Player<P> playerB, GameStateProbe<P> probe);
}
