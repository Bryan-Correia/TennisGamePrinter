package fr.tennisgameprinter.domain.validator.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InconsistentGameExceptionTest {

    @Test
    void testConstructor() {
        // GIVEN an expected message
        String expectedMessage = "message";
        // WHEN we create an InconsistentGameException with a specific message
        InconsistentGameException inconsistentGameException = new InconsistentGameException(expectedMessage);
        // THEN its message matches our expectation
        assertEquals(expectedMessage, inconsistentGameException.getMessage());
    }

}