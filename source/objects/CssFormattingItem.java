package objects;

public class CssFormattingItem {

    // TODO: Add all expected markdown elements as configurable options
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
    public String blockQuote;
    public String italics;          // Denoted by "*TEXT*"
    public String bold;             // Denoted by "**TEXT**"

    public CssFormattingItem() {
        h1 = null;
        h2 = null;
        h3 = null;
        h4 = null;
        h5 = null;
        h6 = null;
        p = null;
        div = null;
        img = null;
        hr = null;
        orderedList = null;
        unorderedList = null;
        code = null;
        blockQuote = null;
        italics = null;
        bold = null;
    }

    public CssFormattingItem(String defaultValue) {
        h1 = defaultValue;
        h2 = defaultValue;
        h3 = defaultValue;
        h4 = defaultValue;
        h5 = defaultValue;
        h6 = defaultValue;
        p = defaultValue;
        div = defaultValue;
        img = defaultValue;
        hr = defaultValue;
        orderedList = defaultValue;
        unorderedList = defaultValue;
        code = defaultValue;
        blockQuote = defaultValue;
        italics = defaultValue;
        bold = defaultValue;
    }

    public void setAllToBlank() {
        String EMPTY_STRING = "";
        h1 = EMPTY_STRING;
        h2 = EMPTY_STRING;
        h3 = EMPTY_STRING;
        h4 = EMPTY_STRING;
        h5 = EMPTY_STRING;
        h6 = EMPTY_STRING;
        p = EMPTY_STRING;
        div = EMPTY_STRING;
        img = EMPTY_STRING;
        hr = EMPTY_STRING;
        orderedList = EMPTY_STRING;
        unorderedList = EMPTY_STRING;
        code = EMPTY_STRING;
        blockQuote = EMPTY_STRING;
        italics = EMPTY_STRING;
        bold = EMPTY_STRING;
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
        printIfNotBlank("block quote: ", blockQuote);
        printIfNotBlank("italics: ", italics);
        printIfNotBlank("bold: ", bold);
    }

    public void printIfNotBlank(String prefix, String value) {
        if (value != null && !value.isBlank()) {
            System.out.println("        " + prefix + value);
        }
    }

    public String getHeadingFormatting(int headingLevel) {
        return switch (headingLevel) {
            case (1) -> h1;
            case (2) -> h2;
            case (3) -> h3;
            case (4) -> h4;
            case (5) -> h5;
            case (6) -> h6;
            default -> {
                System.out.println("Invalid header level, returning h6 value");
                yield ""; // TODO: Figure out if this should return "" or h6
            }
        };
    }
}
