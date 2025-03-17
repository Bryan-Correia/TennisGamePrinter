package fr.tennisgameprinter.domain.validator.listener;

import fr.tennisgameprinter.domain.game.point.output.AdvantagePoint;
import fr.tennisgameprinter.domain.game.point.output.RegularPoint;
import fr.tennisgameprinter.domain.game.point.output.VictoryPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessedPointsCounterTest {

    private ProcessedPointsCounter<Character> processedPointsCounter;

    @BeforeEach
    void setUp () {
        processedPointsCounter = new ProcessedPointsCounter<>();
    }

    @Test
    void testConstructor() {
        // WHEN we retrieve the points counter with no points processed
        int pointsCounter = processedPointsCounter.getPointsCounter();
        // THEN it is equal to zero
        assertEquals(0, pointsCounter);
    }

    @Test
    void testOnPoint() {
        // GIVEN a regular point
        RegularPoint<Character> regularPoint = new RegularPoint<>('A', 'B', 15, 0);
        // WHEN we call the onPoint() method and retrieve the points counter
        processedPointsCounter.onPoint(regularPoint);
        int pointsCounter = processedPointsCounter.getPointsCounter();
        // THEN it is equal to one
        assertEquals(1, pointsCounter);
    }

    @Test
    void testOnAdvantage() {
        // GIVEN an advantage point
        AdvantagePoint<Character> advantagePoint = new AdvantagePoint<>('A');
        // WHEN we call the onAdvantage() method and retrieve the points counter
        processedPointsCounter.onAdvantage(advantagePoint);
        int pointsCounter = processedPointsCounter.getPointsCounter();
        // THEN it is equal to one
        assertEquals(1, pointsCounter);
    }

    @Test
    void testOnDeuce() {
        // WHEN we call the onDeuce() method and retrieve the points counter
        processedPointsCounter.onDeuce();
        int pointsCounter = processedPointsCounter.getPointsCounter();
        // THEN it is equal to one
        assertEquals(1, pointsCounter);
    }

    @Test
    void testOnVictory() {
        // GIVEN a point that determined victory
        VictoryPoint<Character> victoryPoint = new VictoryPoint<>('A');
        // WHEN we call the onVictory() method and retrieve the points counter
        processedPointsCounter.onVictory(victoryPoint);
        int pointsCounter = processedPointsCounter.getPointsCounter();
        // THEN it is equal to one
        assertEquals(1, pointsCounter);
    }

    @Test
    void testWithSeveralPoints() {
        // GIVEN some regular points
        List<RegularPoint<Character>> regularPoints = List.of(new RegularPoint<>('A', 'B', 15, 0),
                new RegularPoint<>('A', 'B', 30, 0),
                new RegularPoint<>('A', 'B', 40, 0),
                new RegularPoint<>('A', 'B', 40, 15),
                new RegularPoint<>('A', 'B', 40, 30),
                new RegularPoint<>('A', 'B', 40, 40)
        );
        // AND an advantage point
        AdvantagePoint<Character> advantagePointA = new AdvantagePoint<>('A');
        // AND assuming a deuce occurred
        processedPointsCounter.onDeuce();
        // AND an advantage point followed by a victory point
        AdvantagePoint<Character> advantagePointB = new AdvantagePoint<>('A');
        VictoryPoint<Character> victoryPoint = new VictoryPoint<>('A');
        // AND we call the onPoint() method for all regular points
        regularPoints.forEach(processedPointsCounter::onPoint);
        // AND we call the onAdvantage() method for all advantage points
        processedPointsCounter.onAdvantage(advantagePointA);
        processedPointsCounter.onAdvantage(advantagePointB);
        // AND we call the onVictory() method for the victory point
        processedPointsCounter.onVictory(victoryPoint);

        // WHEN we retrieve the points counter
        int pointsCounter = processedPointsCounter.getPointsCounter();

        // THEN it is equal to ten
        assertEquals(10, pointsCounter);
    }
}