package fr.tennisgameprinter.domain;

import fr.tennisgameprinter.domain.game.player.Player;
import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import fr.tennisgameprinter.domain.ports.listener.GameStateListener;

import java.util.List;
import java.util.Objects;

/**
 * A stateful representation of a tennis game.
 * @param <P> the type of players' identifier.
 */
public class TennisGame<P> {

    /** The first {@link Player}. */
    private final Player<P> firstPlayer;
    /** The second {@link Player}. */
    private final Player<P> secondPlayer;
    /** The {@link GameStateListener} to notify after processing a {@link Point}. */
    private final GameStateListener<P> gameStateListener;

    /** Whether this game is over. */
    private boolean isGameOver;

    /**
     * Creates a new {@link TennisGame} with the provided two provided {@link Player}s and {@link GameStateListener}.
     * @param firstPlayer the first player.
     * @param secondPlayer the second player.
     * @param gameStateListener the {@link GameStateListener} to notify of each point.
     */
    public TennisGame(Player<P> firstPlayer, Player<P> secondPlayer, final GameStateListener<P> gameStateListener) {
        this.firstPlayer = Objects.requireNonNull(firstPlayer, "the provided first player must not be null.");
        this.secondPlayer = Objects.requireNonNull(secondPlayer, "the provided second player must not be null.");
        this.gameStateListener = Objects.requireNonNull(gameStateListener, "the provided game listener must not be null");
    }

    /**
     * Processes the provided list of {@link Point}.
     * @param points the list of {@link Point} representing the tennis game.
     */
    public void process(final List<Point<P>> points) {
        for (Point<P> point : points) {
            if (isGameOver) {
                break;
            } else {
                this.process(point);
            }
        }
    }

    /**
     * Processes a single {@link Point}.
     * @param point the {@link Point} to process.
     */
    private void process(final Point<P> point) {
        if (isVictoryByPoints(point) || isVictoryOnAdvantage(point)) {
            isGameOver = true;
            return;
        }
        give(point);
        if (isDeuce()) {
            gameStateListener.onDeuce();
            return;
        }
        if (advantageToFirstPlayer()) {
            gameStateListener.onAdvantage(new AdvantagePoint<>(firstPlayer.getId()));
            return;
        }
        if (advantageToSecondPlayer()) {
            gameStateListener.onAdvantage(new AdvantagePoint<>(secondPlayer.getId()));
            return;
        }
        gameStateListener.onPoint(new RegularPoint<>(firstPlayer.getId(), secondPlayer.getId(), firstPlayer.getScore(), secondPlayer.getScore()));
    }

    /**
     * @return whether this game is over.
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * @return whether the second player has the advantage.
     */
    private boolean advantageToSecondPlayer() {
        return secondPlayer.hasAdvantage();
    }

    /**
     * @return whether the first player has the advantage.
     */
    private boolean advantageToFirstPlayer() {
        return firstPlayer.hasAdvantage();
    }

    /**
     * Gives the point to the {@link Player} that actually won it.
     * @param point the {@link Point} to give.
     */
    private void give(final Point<P> point) {
        Player<P> winner = point.isWonBy(firstPlayer) ? firstPlayer : secondPlayer;
        Player<P> loser = (winner == firstPlayer) ? secondPlayer : firstPlayer;
        winner.winsPointOver(loser);
    }

    /**
     * @return whether the current game state is a deuce.
     */
    private boolean isDeuce() {
        return firstPlayer.getScore() == 40 && firstPlayer.getScore() == secondPlayer.getScore() && noPlayerHasAdvantage();
    }

    /**
     * Asserts whether there is a victory by points when processing the provided {@link Point}.
     * @param point the {@link Point} which might lead a player to victory.
     * @return true if the provided {@link Point} leads to a victory, false otherwise.
     */
    private boolean isVictoryByPoints(final Point<P> point) {
        if (point.isWonBy(firstPlayer) && firstPlayer.getScore() == 40 && secondPlayer.getScore() < 40) {
            gameStateListener.onVictory(new VictoryPoint<>(firstPlayer.getId()));
            return true;
        } else if (point.isWonBy(secondPlayer) && secondPlayer.getScore() == 40 && firstPlayer.getScore() < 40) {
            gameStateListener.onVictory(new VictoryPoint<>(secondPlayer.getId()));
            return true;
        }
        return false;
    }

    /**
     * Asserts whether there is a victory due to an advantage when processing the provided {@link Point}.
     * @param point the {@link Point} which might lead a player to victory.
     * @return true if the provided {@link Point} leads to a victory, false otherwise.
     */
    private boolean isVictoryOnAdvantage(final Point<P> point) {
        if (noPlayerHasAdvantage()) {
            return false;
        }
        if (point.isWonBy(firstPlayer) && firstPlayer.hasAdvantage()) {
            gameStateListener.onVictory(new VictoryPoint<>(firstPlayer.getId()));
            return true;
        } else if (point.isWonBy(secondPlayer) && secondPlayer.hasAdvantage()) {
            gameStateListener.onVictory(new VictoryPoint<>(secondPlayer.getId()));
            return true;
        }
        return false;
    }

    /**
     * @return whether no player has the advantage.
     */
    private boolean noPlayerHasAdvantage() {
        return !(firstPlayer.hasAdvantage() || secondPlayer.hasAdvantage());
    }
}
