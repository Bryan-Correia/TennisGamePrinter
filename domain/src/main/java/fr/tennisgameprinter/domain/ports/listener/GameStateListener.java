package fr.tennisgameprinter.domain.ports.listener;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;

/**
 * An interface which represents a listener that gets notified of the tennis game states.
 * That is, everytime a point is processed by the game logic, this listener gets notified and any implementation can act
 * accordingly.
 * @param <P> the type of the players' identifier.
 */
public interface GameStateListener<P> {
    void onPoint(RegularPoint<P> regularPoint);
    void onAdvantage(AdvantagePoint<P> advantagePoint);
    void onDeuce();
    void onVictory(VictoryPoint<P> victoryPoint);
}
