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

    private static final String REGULAR_POINT_FORMAT = "Player %s : %s / Player %s : %s%n";
    private static final String ADVANTAGE_FORMAT = "Player %s has advantage%n";
    private static final String WIN_FORMAT = "Player %s wins the game%n";

    @Override
    public void onPoint(final RegularPoint<Character> regularPoint) {
        System.out.printf(REGULAR_POINT_FORMAT, regularPoint.playerId(), regularPoint.playerScore(), regularPoint.otherPlayerId(), regularPoint.otherPlayerScore());
    }

    @Override
    public void onAdvantage(final AdvantagePoint<Character> advantagePoint) {
        System.out.printf(ADVANTAGE_FORMAT, advantagePoint.advantagedPlayerId());
    }

    @Override
    public void onDeuce() {
        System.out.println("Deuce");
    }

    @Override
    public void onVictory(final VictoryPoint<Character> victoryPoint) {
        System.out.printf(WIN_FORMAT, victoryPoint.winner());
    }
}
