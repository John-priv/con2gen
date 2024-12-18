package objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import objects.TemplateElementTypes;

public class TemplateNameMap {
    public HashMap<String, Set<String>> templateMap = new HashMap<>();

    // TODO: See if these can/should be converted to sets
    // Index is no longer needed as TemplateExecutionOrder covers it
    public Set<String> sectionNames = new HashSet<>();
    public Set<String> variableNames = new HashSet<>();
    public Set<String> imageNames = new HashSet<>();
    public Set<String> scriptNames = new HashSet<>();

    public TemplateNameMap() {
        templateMap.put(TemplateElementTypes.SECTION, sectionNames);
        templateMap.put(TemplateElementTypes.VARIABLE, variableNames);
        templateMap.put(TemplateElementTypes.IMAGE, imageNames);
        templateMap.put(TemplateElementTypes.SCRIPT, scriptNames);
    }

    public void add(String elementType, String elementName) {
        // Note: This should be reformatted if it expands beyond ~5-10 elements
        // TODO: Investigate if this can/should be simplified to a one-line function
        switch (elementType) {
            case TemplateElementTypes.SECTION:
                indexMapAdd(elementName, sectionNames);
                break;
            case TemplateElementTypes.VARIABLE:
                indexMapAdd(elementName, variableNames);
                break;
            case TemplateElementTypes.IMAGE:
                indexMapAdd(elementName, imageNames);
                break;
            case TemplateElementTypes.SCRIPT:
                indexMapAdd(elementName, scriptNames);
                break;
            default:
                System.out.println(elementType + " is not a valid TemplateNameMap elementType");
                break;
        }
    }

    public boolean contains(String elementType, String elementName) {
        switch (elementType) {
            case TemplateElementTypes.SECTION:
                return indexMapContains(elementName, sectionNames);
            case TemplateElementTypes.VARIABLE:
                return indexMapContains(elementName, variableNames);
            case TemplateElementTypes.IMAGE:
                return indexMapContains(elementName, imageNames);
            case TemplateElementTypes.SCRIPT:
                return indexMapContains(elementName, scriptNames);
            default:
                System.out.println(elementType + " is not a valid TemplateNameMap elementType");
        }
        return false;
    }

    private void indexMapAdd(String key, Set<String> indexMap) { indexMap.add(key); }
    private boolean indexMapContains(String key, Set<String> indexMap) { return indexMap.contains(key); }

    public void print() {
        System.out.println("Template Map: " + templateMap);
        System.out.println("SECTION: " + sectionNames);
        System.out.println("IMAGE: " + imageNames);
        System.out.println("VARIABLE: " + variableNames);
        System.out.println("SCRIPT: " + scriptNames);
    }

    public void clear() {
        for (Map.Entry<String, Set<String>> entry: templateMap.entrySet()) {
            entry.setValue(new HashSet<>());
        }
    }
}
