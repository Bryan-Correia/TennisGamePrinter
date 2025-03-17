package fr.tennisgameprinter.adapters.listener;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;

/**
 * An adapter implementation of the {@link GameStateListener} port that prints each tennis game point into the standard
 * output with a specific format.
 */
public class StdoutPointPrintingListener implements GameStateListener<Character> {

    @Override
    public void onPoint(final RegularPoint<Character> regularPoint) {

    }

    @Override
    public void onAdvantage(final AdvantagePoint<Character> advantagePoint) {

    }

    @Override
    public void onDeuce() {

    }

    @Override
    public void onVictory(final VictoryPoint<Character> victoryPoint) {

    }
}
