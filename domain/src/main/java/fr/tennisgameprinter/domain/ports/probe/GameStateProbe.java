package fr.tennisgameprinter.domain.ports.probe;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;

/**
 * An interface which represents a probe that gets notified of all tennis game states.
 * That is, everytime a point is processed by the game logic, this probe gets notified and any implementation can act
 * accordingly.
 * @param <P> the type of the players' identifier.
 */
public interface GameStateProbe<P> {
    void onPoint(RegularPoint<P> regularPoint);
    void onAdvantageGained(AdvantagePoint<P> advantagePoint);
    void onDeuce();
    void onVictory(VictoryPoint<P> victoryPoint);
}
