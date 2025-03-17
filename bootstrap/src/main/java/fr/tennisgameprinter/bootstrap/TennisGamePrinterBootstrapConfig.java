package fr.tennisgameprinter.bootstrap;

import fr.tennisgameprinter.adapters.deserializer.StringDeserializer;
import fr.tennisgameprinter.adapters.input.TennisGameInputReader;
import fr.tennisgameprinter.adapters.listener.StdoutPointPrintingListener;
import fr.tennisgameprinter.domain.TennisGame;
import fr.tennisgameprinter.domain.TennisGameProcessor;
import fr.tennisgameprinter.domain.validator.GameConsistencyValidator;
import fr.tennisgameprinter.domain.validator.listener.ProcessedPointsCounter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

/**
 * Configuration class that handles the bootstrap of our application.
 */
@Configuration
public class TennisGamePrinterBootstrapConfig {

    /**
     * @return a {@link StringDeserializer}.
     */
    @Bean
    public StringDeserializer stringDeserializer() {
        return new StringDeserializer();
    }

    /**
     * @return a {@link StdoutPointPrintingListener}.
     */
    @Bean
    public StdoutPointPrintingListener stdoutPointPrintingProbe() {
        return new StdoutPointPrintingListener();
    }

    /**
     * @return a {@link GameConsistencyValidator}.
     */
    @Bean
    public GameConsistencyValidator<Character> gameConsistencyValidator() {
        return new GameConsistencyValidator<>(TennisGame::new, ProcessedPointsCounter::new);
    }

    /**
     * Creates a new {@link TennisGameProcessor} from the two provided adapters {@link StringDeserializer} and
     * {@link StdoutPointPrintingListener}, and from a {@link GameConsistencyValidator}.
     * @param stringDeserializer the {@link StringDeserializer} adapter.
     * @param gameConsistencyValidator the {@link GameConsistencyValidator}.
     * @param stdoutPointPrintingProbe the {@link StdoutPointPrintingListener} adapter.
     * @return a {@link TennisGameProcessor} initialized with our adapters.
     */
    @Bean
    public TennisGameProcessor<String, Character> tennisGameProcessor(
            final StringDeserializer stringDeserializer,
            final GameConsistencyValidator<Character> gameConsistencyValidator,
            final StdoutPointPrintingListener stdoutPointPrintingProbe) {
        return new TennisGameProcessor<>(
                stringDeserializer,
                gameConsistencyValidator,
                stdoutPointPrintingProbe,
                TennisGame::new
        );
    }

    /**
     * Creates a new {@link TennisGameInputReader} to read user input.
     * @param tennisGameProcessor the {@link TennisGameProcessor} that will process the input.
     * @return a {@link TennisGameInputReader} initialized with the provided {@link TennisGameProcessor} and a {@link Scanner}.
     */
    @Bean
    public TennisGameInputReader tennisGamePrinterRunner(final TennisGameProcessor<String, Character> tennisGameProcessor) {
        return new TennisGameInputReader(tennisGameProcessor, new Scanner(System.in));
    }
}
