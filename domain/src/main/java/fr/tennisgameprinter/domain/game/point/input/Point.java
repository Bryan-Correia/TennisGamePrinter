package fr.tennisgameprinter.domain.game.point.input;

import fr.tennisgameprinter.domain.game.player.Player;

/**
 * Represents a {@link Point} in a tennis game by holding the identifier of the winner.
 * @param winner the identifier of the winner.
 * @param <P> the type of the identifier of the winner.
 */
public record Point<P> (P winner) {
    public boolean isWonBy(final Player<P> player) {
        return winner.equals(player.getId());
    }
}
