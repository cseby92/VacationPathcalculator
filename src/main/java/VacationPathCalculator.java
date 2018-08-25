import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class calculates the optimal path for a given input pof locations,
 * based on their given dependencies
 * <p>
 * @author cseby92@gmail.com
 */

public class VacationPathCalculator {

    private final String LINE_SEPARATOR = "\\r?\\n";
    private final String DEPENDENCY_SEPARATOR = "=>";
    private final String INPUT_VALIDATOR_REGEXP = "(. =>\n|. => .\n)+(. =>|. => .)|(. =>)";
    private final String OUTPUT_DELIMITER = ", ";
    private List<String> resultPath;
    /**
     * Calculates the preferred path.
     * <p>
     * @param inputDirections input directions as a String delimited by end line characters
     * @return the directions in optimal order as a String
     * @throws VacationPathCalculatorException throws a custom exception if the input is wrong
     */
    public String calculateOptimalPath(String inputDirections) throws VacationPathCalculatorException {
        if(hasNoDirections(inputDirections)) {
            return "";
        }
        validateInput(inputDirections);
        List<String> allDirections = Arrays.asList(inputDirections.split(LINE_SEPARATOR));
        if(hasOnlyOneDirection(allDirections)) {
            return getSingleDirection(allDirections.get(0));
        }
        resultPath = new ArrayList<>();
        allDirections.forEach(this::addDirectionsToResultPath);
        return String.join(OUTPUT_DELIMITER, resultPath);
    }

    private boolean hasNoDirections(String inputDirections) {
        return inputDirections.equals("");
    }

    private boolean hasOnlyOneDirection(List<String> directions) {
        return directions.size() == 1;
    }

    private String getSingleDirection(String direction) {
        return direction.split(DEPENDENCY_SEPARATOR)[0].trim();
    }

    private boolean hasNoDependency(String direction) {
        return direction.split(DEPENDENCY_SEPARATOR).length == 1;
    }

    private boolean doesNotContain(List<String> resultPath, String direction) {
        return !resultPath.contains(direction);
    }

    private List<String> getMultipleDirections(String directions) {
        ArrayList<String> list = new ArrayList<>(Arrays.asList(directions.split(DEPENDENCY_SEPARATOR)));
        Collections.reverse(list);
        return list;
    }

    private boolean isBothDirectionsNotPresent(List<String> resultPath, String directionOne, String directionTwo) {
        return doesNotContain(resultPath, directionOne) && doesNotContain(resultPath, directionTwo);
    }

    private void addDirectionsToResultPath(String directions) {
        if (hasNoDependency(directions)) {
            String singleDirection = getSingleDirection(directions);
            if (doesNotContain(resultPath, singleDirection)) {
                resultPath.add(getSingleDirection(directions));
            }
        } else {
            addMultipleDependenciesToResultPath(directions);
        }
    }

    private void addMultipleDependenciesToResultPath(String directions) {
        List<String> directionsInOrder = getMultipleDirections(directions);
        String firstDirection = directionsInOrder.get(0).trim();
        String secondDirection = directionsInOrder.get(1).trim();
        if (isBothDirectionsNotPresent(resultPath, firstDirection, secondDirection)) {
            appendBothDirectionsAtTheEnd(firstDirection, secondDirection);
        } else if (doesNotContain(resultPath, firstDirection)) {
            appendBeforeSecondDirection(firstDirection, secondDirection);
        } else if (doesNotContain(resultPath, secondDirection)) {
            appendAfterFirstDirection(firstDirection, secondDirection);
        }
    }

    private void appendBothDirectionsAtTheEnd(String firstDirection, String secondDirection) {
        resultPath.add(firstDirection);
        resultPath.add(secondDirection);
    }

    private void appendBeforeSecondDirection(String firstDirection, String secondDirection) {
        int indexOfDirection = resultPath.indexOf(secondDirection);
        resultPath.add(indexOfDirection, firstDirection);
    }

    private void appendAfterFirstDirection(String firstDirection, String secondDirection) {
        int indexOfDirection = resultPath.indexOf(firstDirection);
        resultPath.add(indexOfDirection + 1, secondDirection);
    }

    private void validateInput(String inputDirections) throws VacationPathCalculatorException{
        if(!inputDirections.matches(INPUT_VALIDATOR_REGEXP)) {
            throw new VacationPathCalculatorException(String.format("Invalid input format for: \n %s", inputDirections));
        }
    }
}
