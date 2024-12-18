package objects;

public class ConfigObjectData {

    public String h1;               // Denoted by "#"
    public String h2;               // Denoted by "##"
    public String h3;               // Denoted by "###"
    public String h4;               // Denoted by "####"
    public String h5;               // Denoted by "#####"
    public String h6;               // Denoted by "######"
    public String p;                // Not implemented
    public String div;              // Not implemented
    public String img;              // Not implemented
    public String hr;               // Denoted by "---"
    public String unorderedList;    // Denoted by "-"
    public String orderedList;      // Denoted by "--", should be "1." "2.", "3.", etc

    public ConfigObjectData() {
        String DEFAULT = "";
        h1 = DEFAULT;
        h2 = DEFAULT;
        h3 = DEFAULT;
        h4 = DEFAULT;
        h5 = DEFAULT;
        h6 = DEFAULT;
        p = DEFAULT;
        div = DEFAULT;
        img = DEFAULT;
        hr = DEFAULT;
        orderedList = DEFAULT;
        unorderedList = DEFAULT;
    }
}
