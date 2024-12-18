package objects;

import java.util.ArrayList;

public class TemplateElementList {

    public ArrayList<String> sectionNames = new ArrayList<>();
    public ArrayList<String> variableNames = new ArrayList<>();
    public ArrayList<String> imageNames = new ArrayList<>();
    public ArrayList<String> scriptNames = new ArrayList<>();

    // TODO: Refactor if the number of lists grows above ~5-10
    // These can stay separated if the number of lists stays low

    public void add(String elementType, String elementName) {
        switch (elementType) {
            case TemplateElementTypes.SECTION:
                addItem(elementName, sectionNames);
                break;
            case TemplateElementTypes.VARIABLE:
                addItem(elementName, variableNames);
                break;
            case TemplateElementTypes.IMAGE:
                addItem(elementName, imageNames);
                break;
            case TemplateElementTypes.SCRIPT:
                addItem(elementName, scriptNames);
                break;
            default:
                System.out.println(elementType + " is not a valid TemplateNameMap elementType");
                break;
        }
    }

    private void addItem(String elementName, ArrayList<String> listToAppend) {
        listToAppend.add(elementName);
    }

    public void print() {
        System.out.println("Variables: " + variableNames);
        System.out.println("Sections: " + sectionNames);
        System.out.println("Images: " + imageNames);
        System.out.println("Scripts: " + scriptNames);
    }
}
