package fr.tennisgameprinter.adapters.deserializer;

import fr.tennisgameprinter.domain.game.point.input.Point;
import fr.tennisgameprinter.domain.ports.deserializer.InputDeserializer;

import java.util.List;

/**
 * An adapter implementation of the {@link InputDeserializer} port which takes a {@link String} and builds a {@link List}
 * of {@link Point}s that identify the winner as a {@link Character}.
 */
public class StringDeserializer implements InputDeserializer<String, Character> {
    @Override
    public List<Point<Character>> deserialize(final String input) {
        return null;
    }
}
