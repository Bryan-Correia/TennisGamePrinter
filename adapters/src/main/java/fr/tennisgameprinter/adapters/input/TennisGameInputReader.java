package fr.tennisgameprinter.adapters.input;

import fr.tennisgameprinter.domain.TennisGameProcessor;

import java.util.Scanner;

/**
 * Starts an infinite loop that reads the standard input for tennis games in the form of {@link String}s that
 * represent two players respectively identified by {@link Character} 'A' and {@link Character} 'B'.
*/
public class TennisGameInputReader {

    /**
     * Creates a new {@link TennisGameInputReader} from the provided {@link TennisGameProcessor} and {@link Scanner}.
     * @param tennisGameProcessor the {@link TennisGameProcessor}.
     * @param scanner the {@link Scanner}.
     */
    public TennisGameInputReader(TennisGameProcessor<String, Character> tennisGameProcessor, Scanner scanner) {
    }

    /** Starts listening the standard input for tennis games {@link String}s. */
    public void start() {
    }

}
