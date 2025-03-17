package fr.tennisgameprinter.adapters.input;

import fr.tennisgameprinter.domain.TennisGameProcessor;
import fr.tennisgameprinter.domain.game.input.TennisGameInput;
import fr.tennisgameprinter.domain.validator.exception.InconsistentGameException;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TennisGameInputReaderTest {
    @Test
    void testStart() {
        // GIVEN a TennisGameProcessor
        TennisGameProcessor<String, Character> tennisGameProcessor = mock(TennisGameProcessor.class);
        // AND a Scanner from which we simulate user input
        Scanner scannerMock = mock(Scanner.class);
        when(scannerMock.nextLine()).thenReturn("AAABA", "BBAABB", "exit");
        // AND our TennisGameInputReader built from the previously defined dependencies
        TennisGameInputReader tennisGameInputReader = new TennisGameInputReader(tennisGameProcessor, scannerMock);

        // WHEN we start the TennisGameInputReader
        tennisGameInputReader.start();

        // THEN the TennisGameProcessor instance has processed the user input
        verify(tennisGameProcessor).process(new TennisGameInput<>("AAABA", 'A', 'B'));
        verify(tennisGameProcessor).process(new TennisGameInput<>("BBAABB", 'A', 'B'));
    }

    @Test
    void testStart_withExceptionFromDomainLayer() {
        // GIVEN the expected input that does not form a consistent tennis game
        String inconsistentInput = "AAA";
        // AND a TennisGameProcessor that is expected to throw an exception when processing this input
        TennisGameProcessor<String, Character> tennisGameProcessor = mock(TennisGameProcessor.class);
        TennisGameInput<String, Character> tennisGameInput = new TennisGameInput<>(inconsistentInput, 'A', 'B');
        doThrow(new InconsistentGameException(("the provided tennis game is inconsistent")))
                .when(tennisGameProcessor)
                .process(tennisGameInput);
        // AND a Scanner from which we simulate user input
        Scanner scannerMock = mock(Scanner.class);
        when(scannerMock.nextLine()).thenReturn(inconsistentInput, "exit");
        // AND our TennisGameInputReader built from the previously defined dependencies
        TennisGameInputReader tennisGameInputReader = new TennisGameInputReader(tennisGameProcessor, scannerMock);

        // WHEN we start the TennisGameInputReader, THEN no exception occurs as it was caught by our TennisGameInputReader
        assertDoesNotThrow(tennisGameInputReader::start);
        // AND yet, our TennisGameProcessor was called with the expected input that triggered an InconsistentGameException
        verify(tennisGameProcessor).process(tennisGameInput);
    }
}
