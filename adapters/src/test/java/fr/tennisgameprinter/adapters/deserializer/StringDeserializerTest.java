package fr.tennisgameprinter.adapters.deserializer;

import fr.tennisgameprinter.domain.game.point.input.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringDeserializerTest {
    @Test
    void testDeserialize() {
        // GIVEN a String input
        String input = "AAABA";
        // AND our StringDeserializer
        StringDeserializer stringDeserializer = new StringDeserializer();
        // WHEN we deserialize the input into points
        List<Point<Character>> points = stringDeserializer.deserialize(input);
        // THEN the points match our expectations
        for (int i = 0; i < input.length(); ++i) {
            assertEquals(input.charAt(i), points.get(i).winner());
        }
    }
}
