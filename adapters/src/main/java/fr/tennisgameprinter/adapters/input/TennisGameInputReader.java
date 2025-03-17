package fr.tennisgameprinter.adapters.input;

import fr.tennisgameprinter.domain.TennisGameProcessor;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;

import java.util.Objects;
import java.util.Scanner;

/**
 * Starts an infinite loop that reads the standard input for tennis games in the form of {@link String}s that
 * represent two players respectively identified by {@link Character} 'A' and {@link Character} 'B'.
*/
public class TennisGameInputReader {
    /** The {@link TennisGameProcessor} instance which will process the user input. */
    private final TennisGameProcessor<String, Character> tennisGameProcessor;
    /** The {@link Scanner} which will read user input. */
    private final Scanner scanner;

    /**
     * Creates a new {@link TennisGameInputReader} from the provided {@link TennisGameProcessor} and {@link Scanner}.
     * @param tennisGameProcessor the {@link TennisGameProcessor}.
     * @param scanner the {@link Scanner}.
     */
    public TennisGameInputReader(TennisGameProcessor<String, Character> tennisGameProcessor, Scanner scanner) {
        this.tennisGameProcessor = Objects.requireNonNull(tennisGameProcessor, "the provided TennisGameProcessor instance must not be null.");
        this.scanner = Objects.requireNonNull(scanner, "the provided Scanner instance must not be null.");
    }

    /** Starts listening to the standard input for tennis games {@link String}s. */
    public void start() {
        System.out.println("Tennis Game Printer started. Type 'exit' to quit.");

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting Tennis Game Printer...");
                break;
            }

            try {
                tennisGameProcessor.process(new TennisGameInput<>(input, 'A', 'B'));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
