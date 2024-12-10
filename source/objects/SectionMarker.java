package objects;

// NOTE: This entire class might end up getting scrapped
// Section Markers are likely going to have the format "#SECTION.section_name" or "#VARIABLE.variable_name"
// TODO: Add Google Guice
public class SectionMarker {
    // TODO: Figure out if these should be private or public
    private int sectionStartLine;
    private int sectionEndLine;
    private String sectionName;

    // TODO: Evaluate if scetionEndLine is needed
    // It seems like a single line breakpoint can be used to mark where content should be inserted
    // All actual other text can either be pruned or set aside

    // TODO: Add map for markdown to HTML/CSS, based on custom CSS in a section/parent section
    // The two formatting Strings are placeholders, they'll likely NOT be strings
    // These should probably be made into maps
    // Note: The main concern with "parent formatting" is CSS that opens in a prior section that closes in the next one
        // This is an edge case that can be dealt with later on. For now, we can assume
    private String parentFormatting;
    private String sectionFormatting;

    public SectionMarker(){
        System.out.println("Created new SectionMarker");
    }

    public void setStartLine(int startLine) {sectionStartLine = startLine;}
    public void setEndLine(int endLine) {sectionEndLine = endLine;}
    public void setSectionName(String name) {sectionName = name;}

    public int getStartLine() {return sectionStartLine;}
    public int getEndLine() {return sectionEndLine;}
    public String getSectionName() {return sectionName;}
}


// This file is basically just a dump for unneeded code at this point:
// TODO: Remove this after protoype is working
/*
    String sectionRegex = "#SECTION.(.[a-zA-Z0-9_]*)(\\(\\))";
    String variableRegex = "#VARIABLE.(.[a-zA-Z0-9_]*)(\\(\\))";
    String scriptRegex = "#SCRIPT.(.[a-zA-Z0-9_]*)(\\(\\))";
    String imageRegex = "#IMAGE.(.[a-zA-Z0-9_]*)(\\(\\))";
    String genericTemplateRegex = "#([A-Z0-9]*)\\.(.[a-zA-Z0-9_]*)(\\(\\))";
*/

// TODO: Clean up this comment block after implementation of it
// Look for instances of #SECTION.section_name()
// Add instances to a Map of Lists of line numbers
// Ex: variables['recipe_name'] = [46, 93, 102] // the lines #VARIABLE.recipe_name() appears on
//                    matcher.