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

    @Override
    public void onPoint(final RegularPoint<P> regularPoint) {

    }

    @Override
    public void onAdvantageGained(final AdvantagePoint<P> advantagePoint) {

    }

    @Override
    public void onDeuce() {

    }

    @Override
    public void onVictory(final VictoryPoint<P> victoryPoint) {

    }

    public int getPointsCounter() {
        return 0;
    }
}
