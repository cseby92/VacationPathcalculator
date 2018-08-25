public class Application {
    public static void main(String[] args) {
        VacationPathCalculator calculator = new VacationPathCalculator();
        String path = "a => b\nc => b";
        try {
            System.out.println(calculator.calculateOptimalPath(path));
        } catch (VacationPathCalculatorException e) {
            System.out.println(String.format("An exception has occured: %s", e));
        }
    }
}
