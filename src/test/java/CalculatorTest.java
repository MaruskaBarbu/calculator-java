import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.example.Calculator;
import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testSimpleAddition() {
        assertEquals("9.0", Calculator.Run("4 + 5"));
    }

    @Test
    public void testSimpleSubtraction() {
        assertEquals("1.0", Calculator.Run("5 - 4"));
    }

    @Test
    public void testSimpleMultiplication() {
        assertEquals("20.0", Calculator.Run("5 * 4"));
    }

    @Test
    public void testSimpleDivision() {
        assertEquals("5.0", Calculator.Run("20 / 4"));
    }

    @Test
    public void testComplexExpression() {

        assertEquals("33.0", Calculator.Run("10 + 5 * 4 + 3"));
    }

    @Test
    public void testDivisionByZero() {
        try {
            Calculator.Run("10 / 0");
            fail("Expected an ArithmeticException to be thrown");
        } catch (ArithmeticException e) {
            assertEquals("Împărțirea la zero nu este permisă", e.getMessage());
        }
    }

    @Test
    public void testInvalidExpression() {
        try {
            Calculator.Run("4 + 5x");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Expresie invalidă", e.getMessage());
        }
    }

    @Test
    public void testEmptyExpression() {
        try {
            Calculator.Run("");
            fail("Expected an IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Expresie invalidă", e.getMessage());
        }
    }

    @Test
    public void testNegativeNumbers() {
        assertEquals("-2.0", Calculator.Run("-5 + 3"));
    }

    @Test
    public void testExpressionWithSpaces() {
        assertEquals("9.0", Calculator.Run("4   +    5"));
    }

    @Test
    public void testMultipleOperationsWithPriority() {
        assertEquals("20.0", Calculator.Run("5 + 3 * 10 / 2"));
    }
}
