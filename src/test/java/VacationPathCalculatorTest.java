import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
/**
 * This class is for unit testing the VacationPathCalculator class.
 * <p>
 * @author cseby92@gmail.com
 */
public class VacationPathCalculatorTest {

    private VacationPathCalculator calculator = new VacationPathCalculator();
    @Test
    public void testForEmptyInput() throws VacationPathCalculatorException {
        assertEquals("Should return empty string for empty input","", calculator.calculateOptimalPath(""));
    }

    @Test
    public void testForOneLineSimpleInputContain() throws VacationPathCalculatorException {
        assertEquals("Should return simple direction for one simple input","a", calculator.calculateOptimalPath("a =>"));
    }

    @Test
    public void testForMultiLineSimpleInputContain() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("a =>\nb =>");
        assertTrue("Should contain a", path.contains("a"));
        assertTrue("Should contain b", path.contains("b"));
    }
    @Test
    public void testForComplexInputContain() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("a => b\nb =>");
        assertTrue("Should contain a", path.contains("a"));
        assertTrue("Should contain b", path.contains("b"));
    }

    public void testForComplexInputOrder() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("a =>b\nb =>");
        assertTrue("B should be before a",path.indexOf("b") < path.indexOf("a"));
    }

    @Test
    public void testForMultipleComplexInputContain() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("u =>\nv => w\nw => z\nx => u\ny => v\nz =>");
        assertTrue("Should contain u", path.contains("u"));
        assertTrue("Should contain v", path.contains("v"));
        assertTrue("Should contain w", path.contains("w"));
        assertTrue("Should contain z", path.contains("z"));
        assertTrue("Should contain x", path.contains("x"));
        assertTrue("Should contain y", path.contains("y"));
    }

    @Test
    public void testForMultipleComplexInputOrder() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("u =>\nv => w\nw => z\nx => u\ny => v\nz =>");
        assertTrue("Z should be before w",path.indexOf("z") < path.indexOf("w"));
        assertTrue("W should be before v",path.indexOf("w") < path.indexOf("v"));
        assertTrue("V should be before y", path.indexOf("v") < path.indexOf("y"));
        assertTrue("U should be before x", path.indexOf("u") < path.indexOf("x"));
    }
    @Test(expected = VacationPathCalculatorException.class)
    public void testForBadInputWithoutLineEnding() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("u =>v => w\nw => z\nx => u\ny => v\nz =>");
    }

    @Test(expected = VacationPathCalculatorException.class)
    public void testForBadInputWithMultipleCharactersInOneStatement() throws VacationPathCalculatorException {
        String path = calculator.calculateOptimalPath("u =>\nv => w\nw => z\nx y z => u\ny => v\nz =>");
    }
}