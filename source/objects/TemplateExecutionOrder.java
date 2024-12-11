package objects;

public class TemplateExecutionOrder {
    public final String executionType;
    public final String variableName;
    public final Integer linesToSkip;

    public TemplateExecutionOrder(String inputExecutionType, String inputVariableName, Integer inputLineNumber) {
        executionType = inputExecutionType;
        variableName = inputVariableName;
        linesToSkip = inputLineNumber;   // TODO: Should this be "number of lines to skip"?
    }

    public void print() {
        System.out.println("Type: " + executionType +
                ", Name: " + variableName +
                ", Lines Skipped: " + linesToSkip);
    }
}
