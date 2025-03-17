package fr.tennisgameprinter.adapters.listener;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StdoutPointPrintingListenerTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final StdoutPointPrintingListener listener = new StdoutPointPrintingListener();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(System.out);
    }

    @Test
    void testOnPoint() {
        // GIVEN a RegularPoint
        RegularPoint<Character> point = new RegularPoint<>('A', 'B', 30, 15);
        // WHEN we call our listener's onPoint method() on this point
        listener.onPoint(point);
        // THEN the expected output was printed with the expected format
        assertEquals(
                "Player A : 30 / Player B : 15" + System.lineSeparator(),
                outContent.toString()
        );
    }

    @Test
    void testOnAdvantage() {
        // GIVEN a AdvantagePoint
        AdvantagePoint<Character> advantagePoint = new AdvantagePoint<>('B');
        // WHEN we call our listener's onAdvantage method() on this point
        listener.onAdvantage(advantagePoint);
        // THEN the expected output was printed with the expected format
        assertEquals(
                "Player B has advantage" + System.lineSeparator(),
                outContent.toString()
        );
    }

    @Test
    void testOnDeuce() {
        // WHEN we call our listener's onDeuce() method
        listener.onDeuce();
        // THEN the expected output was printed with the expected format
        assertEquals("Deuce" + System.lineSeparator(), outContent.toString());
    }

    @Test
    void testOnVictory() {
        // GIVEN a VictoryPoint
        VictoryPoint<Character> victoryPoint = new VictoryPoint<>('A');
        // WHEN we call our listener's onVictory() method
        listener.onVictory(victoryPoint);
        // THEN the expected output was printed with the expected format
        assertEquals(
                "Player A wins the game" + System.lineSeparator(),
                outContent.toString()
        );
    }

}
