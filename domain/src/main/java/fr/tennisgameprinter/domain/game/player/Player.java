package fr.tennisgameprinter.domain.game.player;

import java.util.Objects;

/**
 * A stateful representation of a tennis player. It keeps track of a player's score, and allows updating its state
 * everytime a point is won against the other player.
 * @param <P> the type of players' identifier.
 */
public class Player<P> {

    /** the identifier of the player. */
    private final P id;

    /**
     * Creates a new {@link Player} with the provided identifier.
     * @param id the identifier of the player.
     */
    public Player(final P id) {
        this.id = Objects.requireNonNull(id, "the provided id must not be null.");
    }

    /**
     * @return the identifier of this player.
     */
    public P getId() {
        return id;
    }

    /**
     * @return the score for this player.
     */
    public int getScore() {
        return 0;
    }

    /**
     * Updates the state of this player by making it win the point over the provided player.
     * @param otherPlayer the other player against which the point was won.
     */
    public void winsPointOver(final Player<P> otherPlayer) {
    }

    /**
     * @return whether this player has the advantage.
     */
    public boolean hasAdvantage() {
        return false;
    }

}
