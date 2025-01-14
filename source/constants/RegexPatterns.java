package constants;

import java.util.regex.Pattern;

public class RegexPatterns {

    // Markdown Processing Regex: Importing
    private static final String elementTypeRegex = "\\{\\{([a-zA-Z0-9_-]*)S}}:";
    private static final String sectionNameRegex = "\\{([a-zA-Z0-9_-]*)}:";
    private static final String variableValueRegex = "([a-zA-Z0-9_-]*): *\"([a-zA-Z0-9_ -]*)\"";

    // Markdown Processing Regex: Formatting
    private static final String italicOrBoldRegex = "(\\*+)([a-zA-Z0-9_ -]*)(\\*+)";
    private static final String headerRegex = "([ \\t]*)#+([a-zA-Z0-9_ -]*)";


    public static final Pattern elementTypePattern = Pattern.compile(elementTypeRegex);
    public static final Pattern sectionNamePattern = Pattern.compile(sectionNameRegex);
    public static final Pattern variableValuePattern = Pattern.compile(variableValueRegex);
    public static final Pattern italicOrBoldPattern = Pattern.compile(italicOrBoldRegex);
    public static final Pattern headerPattern = Pattern.compile(headerRegex);

}
