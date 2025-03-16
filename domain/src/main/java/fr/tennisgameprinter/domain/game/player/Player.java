package fr.tennisgameprinter.domain.game.player;

import java.util.Objects;

/**
 * A stateful representation of a tennis player. It keeps track of a player's score, and allows updating its state
 * everytime a point is won against the other player.
 * @param <P> the type of players' identifier.
 */
public class Player<P> {

    /** A indexed array which allows to map a score index to the actual score. */
    private static final int[] SCORE = {0, 15, 30, 40};

    /** The identifier of the player. */
    private final P id;
    /** Whether this player has the advantage. */
    private boolean hasAdvantage;
    /** An index indicating the score of the player. */
    private int scoreIndex;

    /**
     * Creates a new {@link Player} with the provided identifier.
     * @param id the identifier of the player.
     */
    public Player(final P id) {
        this.id = Objects.requireNonNull(id, "the provided id must not be null.");
    }

    /**
     * @return The identifier of this player.
     */
    public P getId() {
        return id;
    }

    /**
     * @return The score of this player.
     */
    public int getScore() {
        return SCORE[scoreIndex];
    }

    /**
     * Updates the state of this player by making him win the point over the provided player.
     * @param otherPlayer the other player against which the point was won.
     */
    public void winsPointOver(final Player<P> otherPlayer) {
        if (otherPlayer.hasAdvantage()) {
            otherPlayer.losesAdvantage();
        } else if (getScore() == 40 && otherPlayer.getScore() == 40) {
            this.acquiresAdvantage();
        } else {
            scoreIndex++;
        }
    }

    /**
     * @return Whether this player has the advantage.
     */
    public boolean hasAdvantage() {
        return hasAdvantage;
    }

    /**
     * Makes this player lose the advantage.
     */
    public void losesAdvantage() {
        hasAdvantage = false;
    }

    /**
     * Makes this player acquire the advantage.
     */
    public void acquiresAdvantage() {
        hasAdvantage = true;
    }

}
