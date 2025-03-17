package fr.tennisgameprinter.domain.validator.listener;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;

/**
 * A stateful {@link GameStateListener} implementation that counts the processed points.
 * @param <P> the type of players' identifier.
 */
public class ProcessedPointsCounter<P> implements GameStateListener<P> {

    /** The points counter that gets incremented everytime this component gets notified of a point. */
    private int pointsCounter = 0;

    @Override
    public void onPoint(final RegularPoint<P> regularPoint) {
        pointsCounter++;
    }

    @Override
    public void onAdvantage(final AdvantagePoint<P> advantagePoint) {
        pointsCounter++;
    }

    @Override
    public void onDeuce() {
        pointsCounter++;
    }

    @Override
    public void onVictory(final VictoryPoint<P> victoryPoint) {
        pointsCounter++;
    }

    /**
     * @return the amount of points that this component was notified of.
     */
    public int getPointsCounter() {
        return pointsCounter;
    }
}
