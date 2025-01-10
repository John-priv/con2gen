package objects;

public class TemplateExecutionOrderItem {
    public String elementType;
    public String elementName;
    public Integer linesToSkip;

    public TemplateExecutionOrderItem() {
        // Empty Constructor used for reading/importing JSON via Jackson
        super();
    }

    public TemplateExecutionOrderItem(String inputExecutionType, String inputVariableName, Integer inputLineNumber) {
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
