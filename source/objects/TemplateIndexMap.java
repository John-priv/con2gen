package objects;

import java.util.ArrayList;
import java.util.HashMap;

public class TemplateIndexMap {
    public HashMap<String, HashMap<String, ArrayList<Integer>>> templateMap = new HashMap<>();

    // TODO: Figure out if these should be declared automatically, or only if they are used
    public HashMap<String, ArrayList<Integer>> sectionIndexes = new HashMap<>();
    public HashMap<String, ArrayList<Integer>> variableIndexes = new HashMap<>();
    public HashMap<String, ArrayList<Integer>> imageIndexes = new HashMap<>();
    public HashMap<String, ArrayList<Integer>> scriptIndexes = new HashMap<>();

    private final String SECTION = "SECTION";
    private final String VARIABLE = "VARIABLE";
    private final String IMAGE = "IMAGE";
    private final String SCRIPT = "SCRIPT";

    public TemplateIndexMap() {
        templateMap.put(SECTION, sectionIndexes);
        templateMap.put(VARIABLE, variableIndexes);
        templateMap.put(IMAGE, imageIndexes);
        templateMap.put(SCRIPT, scriptIndexes);
    }

    public void add(String keywordType, String variableName, int lineNumber) {
        switch (keywordType) {
            case SECTION:
                indexMapAdd(variableName, lineNumber, sectionIndexes);
                break;
            case VARIABLE:
                indexMapAdd(variableName, lineNumber, variableIndexes);
                break;
            case IMAGE:
                indexMapAdd(variableName, lineNumber, imageIndexes);
                break;
            case SCRIPT:
                indexMapAdd(variableName, lineNumber, scriptIndexes);
                break;
            default:
                System.out.println(keywordType + " is not a valid TemplateIndexMap keywordType");
                break;
        }
    }

    private void indexMapAdd(String key, int lineNumber, HashMap<String, ArrayList<Integer>> indexMap) {
        if (!indexMap.containsKey(key)) {
            indexMap.put(key, new ArrayList<>());
        }
        indexMap.get(key).add(lineNumber);
    }

    public void print() {
        System.out.println("SECTION: " + sectionIndexes);
        System.out.println("IMAGE: " + imageIndexes);
        System.out.println("VARIABLE: " + variableIndexes);
        System.out.println("SCRIPT: " + scriptIndexes);
        System.out.println("Template Map: " + templateMap);
    }
}
