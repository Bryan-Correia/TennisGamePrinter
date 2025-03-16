package fr.tennisgameprinter.domain.game.player;

import java.util.Objects;

/**
 * A stateful representation of a tennis player. It keeps track of a player's score.
 * @param <P> the type of players' identifier.
 */
public class Player<P> {

    private final P id;

    public Player(final P id) {
        this.id = Objects.requireNonNull(id, "the provided id must not be null.");
    }

    public P getId() {
        return id;
    }

    public int getScore() {
        return 0;
    }

}
