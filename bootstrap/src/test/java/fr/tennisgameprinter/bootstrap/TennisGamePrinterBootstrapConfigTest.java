package fr.tennisgameprinter.bootstrap;

import fr.tennisgameprinter.adapters.input.TennisGameInputReader;
import fr.tennisgameprinter.domain.TennisGameProcessor;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TennisGamePrinterBootstrapConfig.class)
class TennisGamePrinterBootstrapConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private TennisGameProcessor<String, Character> tennisGameProcessor;

    @Autowired
    private TennisGameInputReader tennisGameInputReader;

    @Test
    void testApplicationContext() {
        assertNotNull(applicationContext, "Spring application context should be created.");
    }

    @Test
    void testTennisGameProcessorBeanExists() {
        assertNotNull(tennisGameProcessor, "TennisGameProcessor bean should be created.");
    }

    @Test
    void testTennisGameInputReaderBeanExists() {
        assertNotNull(tennisGameInputReader, "TennisGameInputReader bean should be created.");
    }

    @Test
    void testTennisGameProcessor_consistentGame() {
        assertDoesNotThrow(() -> tennisGameProcessor.process(new TennisGameInput<>("AAABA", 'A', 'B')),
                "process method should have executed without throwing exceptions.");
    }

    @Test
    void testTennisGameProcessor_inconsistentGame() {
        assertThrows(InconsistentGameException.class, () -> tennisGameProcessor.process(new TennisGameInput<>("AAAB", 'A', 'B')),
                "process method should have thrown an InconsistentGameException.");
    }
}
