package constants;

import java.util.regex.Pattern;

public class RegexPatterns {
    // TODO: Should this be non-static for optimization?

    // TODO: Replace these to allow more/all non-whitespace characters
    //       Should work for all characters that aren't special/reserved symbols (like "#*[](){};:,.'<>/\~!?$%^|)
    //       Original selection was [a-z, A-Z, 0-9, and _-] --> no support for other non-whitespace non-reserved symbols
    //       These other characters (ex: letters with accents, non-latin letters) should be allowed

    // Markdown Processing Regex: Importing
    private static final String elementTypeRegex = "\\{\\{([a-zA-Z0-9_-]*)S}}:";
    private static final String sectionNameRegex = "\\{([a-zA-Z0-9_-]*)}:";
    private static final String variableValueRegex = "([a-zA-Z0-9_-]*): *\"([a-zA-Z0-9_ -]*)\"";

    // Markdown Processing Regex: Formatting
    // TODO: Remove these comments after they've been committed/merged (remove after con2gen-11 is merged)
//    private static final String italicOrBoldRegex = "(\\*+)([^*\\n\\r]*)(\\*+)"; // Old regex, saving for now
    private static final String italicOrBoldRegex = "(?<!\\\\)(\\*+)([^*\\n\\r]*|\\**[^\\n\\r]*)(?<!\\\\)(\\*+)";
    private static final String headerRegex = "^(?<!\\\\)[ \\t]*(#+)[ \\t]*([\\S \\t]+)";
    private static final String unorderedListRegex = "^([ \\t]*)- [^-]?([\\S \\t]*)";
    private static final String orderedListDashRegex = "^([ \\t]*)-- [^-]?([\\S \\t]*)";
                // old orderedListDashRegex: "^(?<!\\\\)[ \\t]*--([\\S \\t]*)";
    // TODO: Do ordered lists need to support escaping? It doesn't feel like they should
    private static final String orderedListNumberedRegex = "^(?<!\\\\)[ \\t]*[0-9]*\\.([\\S \\t]*)";
    private static final String horizontalLineRegex = "^([-*]{3,})$";
    // TODO: Fix multi-line escaping for code blocks (writing "\`" breaks multi-line code blocks)
    /*
    Test case for the multi-line escape failing:
    ```
    This is a multi-line code block
    Adding an escaped "`" symbol, \` anywhere will break the multi-line portion of the regex
    ```
    */
    private static final String codeBlockRegex = "(?<!\\\\)```([^`]*|\\`*[^\\n\\r]*)(?<!\\\\)```";
//    "(?<!\\)(\*+)([^*\n\r]*|[^*\n\r]*\\\*)(?<!\\)(\*+)" // Current closest match for index/bold regex
//    "(?<!\\)(\*+)([^*\n\r]*|[^*\n\r]*\\\*|\\\*[^*\n\r]*)(?<!\\)(\*+)" // Hits 3/4 cases (misses *\*text text\**
//    "(?<!\\)(\*+)([^*\n\r]*|\**[^\n\r]*)(?<!\\)(\*+)" // Should be the working string


    // Markdown Processing Patterns: Importing
    public static final Pattern elementTypePattern = Pattern.compile(elementTypeRegex);
    public static final Pattern sectionNamePattern = Pattern.compile(sectionNameRegex);
    public static final Pattern variableValuePattern = Pattern.compile(variableValueRegex);

    // Markdown Processing Patterns: Formatting
    public static final Pattern italicOrBoldPattern = Pattern.compile(italicOrBoldRegex);
    public static final Pattern headerPattern = Pattern.compile(headerRegex);
    public static final Pattern unorderedListPattern = Pattern.compile(unorderedListRegex);
    public static final Pattern orderedListNumberedPattern = Pattern.compile(orderedListNumberedRegex);
    public static final Pattern orderedListDashPattern = Pattern.compile(orderedListDashRegex);
    public static final Pattern horizontalLinePattern = Pattern.compile(horizontalLineRegex);
    public static final Pattern codeBlockPattern = Pattern.compile(codeBlockRegex);

}
