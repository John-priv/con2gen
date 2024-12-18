package objects;

public class TemplateExecutionOrder {
    public final String elementType;
    public final String elementName;
    public final Integer linesToSkip;

    public TemplateExecutionOrder(String inputExecutionType, String inputVariableName, Integer inputLineNumber) {
        elementType = inputExecutionType;
        elementName = inputVariableName;
        linesToSkip = inputLineNumber;   // TODO: Should this be "number of lines to skip"?
    }

    public void print() {
        System.out.println("Type: " + elementType +
                ", Name: " + elementName +
                ", Lines Skipped: " + linesToSkip);
    }
}
