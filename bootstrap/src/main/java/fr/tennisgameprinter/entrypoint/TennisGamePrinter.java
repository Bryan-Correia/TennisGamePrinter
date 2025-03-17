package fr.tennisgameprinter.entrypoint;

import fr.tennisgameprinter.adapters.input.TennisGameInputReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * The application's entrypoint.
 */
@SpringBootApplication
@ComponentScan(basePackages = "fr.tennisgameprinter")
public class TennisGamePrinter {
    public static void main(String[] args) {
        SpringApplication.run(TennisGamePrinter.class, args);
    }

    /**
     * Starts our {@link TennisGameInputReader} when the application starts.
     * @param tennisGameInputReader the {@link TennisGameInputReader}.
     * @return a {@link CommandLineRunner} that is run by Spring.
     */
    @Bean
    CommandLineRunner run(final TennisGameInputReader tennisGameInputReader) {
        return args -> tennisGameInputReader.start();
    }
}
