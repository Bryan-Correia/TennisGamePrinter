package fr.tennisgameprinter.domain.validator.exception;

/**
 * An exception which is thrown upon encountering an inconsistent tennis game state.
 */
public class InconsistentGameException extends RuntimeException {

    public InconsistentGameException(final String message) {
        super(message);
    }
}
