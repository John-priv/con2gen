package objects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TemplateNameMap {
    public HashMap<String, Set<String>> templateMap = new HashMap<>();

    // TODO: See if these can/should be converted to sets
    // Index is no longer needed as TemplateExecutionOrder covers it
    public Set<String> sectionNames = new HashSet<>();
    public Set<String> variableNames = new HashSet<>();
    public Set<String> imageNames = new HashSet<>();
    public Set<String> scriptNames = new HashSet<>();

    private final String SECTION = "SECTION";
    private final String VARIABLE = "VARIABLE";
    private final String IMAGE = "IMAGE";
    private final String SCRIPT = "SCRIPT";

    public TemplateNameMap() {
        templateMap.put(SECTION, sectionNames);
        templateMap.put(VARIABLE, variableNames);
        templateMap.put(IMAGE, imageNames);
        templateMap.put(SCRIPT, scriptNames);
    }

    // TODO: Add an ordered list of tuples to store execution order
        // This should allow processing the template file in one pass-through
        // This should probably be done in a separate file (TemplateGenerationProcessor probably)

    public void add(String keywordType, String keywordName) {
        switch (keywordType) {
            case SECTION:
                indexMapAdd(keywordName, sectionNames);
                break;
            case VARIABLE:
                indexMapAdd(keywordName, variableNames);
                break;
            case IMAGE:
                indexMapAdd(keywordName, imageNames);
                break;
            case SCRIPT:
                indexMapAdd(keywordName, scriptNames);
                break;
            default:
                System.out.println(keywordType + " is not a valid TemplateNameMap keywordType");
                break;
        }
    }

    private void indexMapAdd(String key, Set<String> indexMap) {
        indexMap.add(key);
    }

    public void print() {
        System.out.println("Template Map: " + templateMap);
        System.out.println("SECTION: " + sectionNames);
        System.out.println("IMAGE: " + imageNames);
        System.out.println("VARIABLE: " + variableNames);
        System.out.println("SCRIPT: " + scriptNames);
    }
}
