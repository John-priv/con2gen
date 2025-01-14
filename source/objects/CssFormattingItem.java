package objects;

public class CssFormattingItem {

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
    public String code;             // Denoted by "```TEXT```", can be multi-line

    public CssFormattingItem() {
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
        code = DEFAULT;
    }

    public void print() {
        printIfNotBlank("h1: ", h1);
        printIfNotBlank("h2: ", h2);
        printIfNotBlank("h3: ", h3);
        printIfNotBlank("h4: ", h4);
        printIfNotBlank("h5: ", h5);
        printIfNotBlank("h6: ", h6);
        printIfNotBlank("p: ", p);
        printIfNotBlank("div: ", div);
        printIfNotBlank("img: ", img);
        printIfNotBlank("hr: ", hr);
        printIfNotBlank("li: ", unorderedList);
        printIfNotBlank("ol: ", orderedList);
        printIfNotBlank("code: ", code);
    }

    public void printIfNotBlank(String prefix, String value) {
        if (!value.isBlank()) {
            System.out.println("        " + prefix + value);
        }
    }
}
