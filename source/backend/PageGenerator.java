package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FilePathConstants;
import constants.PageGenerationConstants;
import constants.RegexPatterns;
import objects.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Rename all Content/Template objects to be more clear/distinct -- the current names are a mess
public class PageGenerator {
    private final Pattern elementTypePattern = RegexPatterns.elementTypePattern;
    private final Pattern sectionNamePattern = RegexPatterns.sectionNamePattern;
    private final Pattern variableValuePattern = RegexPatterns.variableValuePattern;

    public PageGenerator() {
    }

    // TODO: Simplify this function call if possible. Need to balance flexibility with simplicity (for users)
    public void generatePage(String projectDirectory, String pageDirectory, String outputDirectory) {
        System.out.println("TODO: Implement this. Generating a single page");
        TemplateNameMap contentElementNameMap;
        TemplateExecutionOrder executionOrder;
        ContentDataMap contentDataMap;
        CssFormattingMap cssFormattingSettings;
        // TODO: Import ContentMD. Type should be multi level map --> [element_type][element_name][section_contents]

        try {
            executionOrder = getExecutionOrder(Path.of(projectDirectory, FilePathConstants.EXECUTION_ORDER_FILE));
            contentElementNameMap = getContentNameMap(Path.of(projectDirectory, FilePathConstants.TEMPLATE_ELEMENT_FILE));
            cssFormattingSettings = getCssFormattingSettings(Path.of(projectDirectory, FilePathConstants.FORMATTING_FILE));
            contentDataMap = getContentDataMap(Path.of(pageDirectory, FilePathConstants.CONTENT_MD_FILE), contentElementNameMap);
        }
        catch (IOException ioException) {
            System.out.println("IOException in generatePage: " + ioException);
            System.out.println("Unable to generate page -- template may need to be recreated");
            return;
        }

        // TODO: Clean up printing when generatePage is completed
        // Note: Printing may be able to stay until unit testing is added
        printProcessingObjects(executionOrder, contentElementNameMap, contentDataMap, cssFormattingSettings);

        // Process the contentDataMap
        processContentDataMap(contentDataMap, cssFormattingSettings);

        // Copy/import the base/template file

        // Update the copied file using templateExecutionOrder

        // Save the output file to outputDirectory


        // TODO Implement these steps:
        // Processing order:
        // executionOrder (import = done)
        // TemplateElementMap (import = done)
        // Settings (Read/import where needed, fall back to default if a section does not have custom settings) (import = done)
        // ContentMD (Prepare for processing/separate each variable/section/etc) (import = done)
        // ContentMD (Process each variable/section/etc using imported settings) (processing = WIP)
        // Copy the Base/Template File (ex: The HTML file the template is based on)
        // executionOrder (replace variables/sections/etc over Base/Template File)
        // Save output file to outputDirectory


        /*
        Process:
        - Import content.md
        - Import executionOrder.json
        - Process content.md to get each section from executionOrder
        - OPTIONAL STEP: Store variables/sections as json objects to precompile for future page generations
            - Might not be needed if execution times aren't slow

        Notes:
            - Should there be a json file that contains a hashmap of all elements needed
                - Ex: File that contains all expected variables, sections, etc
                - TemplateNameMap exists and is already computed and could be stored as json + used for validation
                    - Use this to confirm all expected elements are in content.md
                    - Detection for errors and missing sections caused by new additions or deletions
        */
    }

    public void generateAllPages() {
        System.out.println("TODO: Implement this. Generating all pages");
    }

    public void generateListOfPages() {
        System.out.println("TODO: Implement this. Generating pages from a list");
    }

    private TemplateExecutionOrder getExecutionOrder(Path executionOrderPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(executionOrderPath.toFile(), TemplateExecutionOrder.class);
    }

    // TODO: REMOVE -- > Function not needed, remove after implementing/testing replacement
    private TemplateNameMap extractElementsFromExecutionOrder(ArrayList<TemplateExecutionOrderItem> executionOrder) {
        TemplateNameMap contentNameMap = new TemplateNameMap();

        for (TemplateExecutionOrderItem element : executionOrder) {
            contentNameMap.add(element.elementType, element.elementName);
        }

        return contentNameMap;
    }

    private TemplateNameMap getContentNameMap(Path templateElementMapPath) throws IOException {
        File templateElementFile = templateElementMapPath.toFile();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(templateElementFile, TemplateNameMap.class);
    }

    private CssFormattingMap getCssFormattingSettings(Path cssFormattingPath) throws IOException {
        File cssFormattingFile = cssFormattingPath.toFile();

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(cssFormattingFile, CssFormattingMap.class);
    }

    private ContentDataMap getContentDataMap(Path contentMDPath, TemplateNameMap contentElementNameMap) throws IOException {
        // TODO: Refactor this into separate functions where possible --> Do this during unit testing stage
        //       It's not worth refactoring this without unit tests, as it currently works
        ContentDataMap contentDataMap = new ContentDataMap();

        File contentMDFile = contentMDPath.toFile();
        Scanner contentMDFileReader = new Scanner(contentMDFile);
        String currentLine;
        String currentElementType = "";
        String currentElementName = "";

        // TODO: Extract this into a separate function/functions
        // This will be a pretty messy section
        while (contentMDFileReader.hasNextLine()) {
            currentLine = contentMDFileReader.nextLine();

            // TODO: Clean up this line (debugging print statement)
//            System.out.println("currentType: " + currentElementType + ", currentName: " + currentElementName);

            // TODO: Extract this into a separate function if possible
            if (currentLine.startsWith("{{") && currentLine.endsWith("}}:")) {
                Matcher elementTypeMatcher = elementTypePattern.matcher(currentLine);
                if (elementTypeMatcher.find()) {
                    if (TemplateElementTypes.ELEMENT_TYPES.contains(elementTypeMatcher.group(1))) {
                        currentElementType = elementTypeMatcher.group(1);
                        currentElementName = "";
                        continue;
                    } else {
                        System.out.println(elementTypeMatcher.group(1) + " is not in element types: "
                                + TemplateElementTypes.ELEMENT_TYPES);
                    }
                }
            }

            if (currentElementType.equals(TemplateElementTypes.VARIABLE)) {
                // TODO: Remove debugging print line
//                System.out.println("Testing for variable: " + currentLine);
                Matcher variableMatcher = variableValuePattern.matcher(currentLine);
                if (variableMatcher.find()) {
                    String variableName = variableMatcher.group(1);
                    String variableValue = variableMatcher.group(2);
                    contentDataMap.setValue(TemplateElementTypes.VARIABLE, variableName, variableValue);
                }
            }

            // TODO: Refactor this into a separate function
            if (currentElementType.equals(TemplateElementTypes.SECTION)) {
                // Check for new sections first
                if (currentLine.startsWith("{") && currentLine.endsWith("}:")) {
                    Matcher sectionMatcher = sectionNamePattern.matcher(currentLine);
                    if (sectionMatcher.find()) {
                        String sectionName = sectionMatcher.group(1);
                        if (contentElementNameMap.sectionNames.contains(sectionName)) {
                            // Start a new section
                            currentElementName = sectionName;
                            // Check for duplicate section entries (section added 2+ times)
                            if (contentDataMap.containsKey(currentElementType, currentElementName)) {
                                System.out.println("WARNING: Duplicate section \"" + sectionName + "\" in file: "
                                        + contentMDPath);
                            } else {
                                continue;
                            }
                        }
                    }
                }
                // Case where no other types are met/not a new section
                // The logic here is messy and needs to be cleaned up
                // TODO: Clean this up and ADD TESTS
                // TODO: Deal with LONG_LINE being added --> clean up last line of last section
                //          This can be done in post-processing (look for last section, remove long line)
                //          Need to set this up in a way where it wouldn't accidentally impact users
                //          Might have to change LONG_LINE to start/end with different characters?
                //          Personal note: I don't want this to look like a C line
                if (!currentElementName.isEmpty()) {
                    contentDataMap.add(currentElementType, currentElementName, currentLine);
                }
                continue;
            }

            // TODO: Check for and remove any useless code + comments below
            System.out.println(currentLine);
            // TODO: Need to process lines:
            // How to process:
            //      Content.md is in order, so look for the first time
            //          For example if "VARIABLES:" is encountered first:
            //              Process every line as a variable until either a blank line or the next elementType is found
            //      Once "SECTIONS:" is found, start processing after the "-----" line (need to refine this)
            //      SECTIONS notes:
            //          "-----" (or replacement) is used to mark the end of sections
            //          Once it's encountered, check to see if next line is either in the content name map or
        }

        contentMDFileReader.close();

        return contentDataMap;
    }

    private void processContentDataMap(ContentDataMap contentDataMap, CssFormattingMap cssFormattingMap) {
        processSections(contentDataMap, cssFormattingMap);
    }

    private void processSections(ContentDataMap contentDataMap, CssFormattingMap cssFormattingMap) {
        CssFormattingItem defaultSectionFormatting = cssFormattingMap.get(PageGenerationConstants.DEFAULT_CONFIG_NAME);
        // TODO: Refactor this
        // NOTE: Multi-line items need special processing (code blocks, paragraphs)
        if (!contentDataMap.containsElementType(TemplateElementTypes.SECTION)) {
            System.out.println("WARNING: contentDataMap does not contain \"" + TemplateElementTypes.SECTION + "\" element type");
            return;
        }
        for (String sectionName : contentDataMap.getElementType(TemplateElementTypes.SECTION).keySet()) {

            CssFormattingItem sectionFormatting = cssFormattingMap.getAndReplaceEmpty(sectionName, defaultSectionFormatting);
            if (sectionFormatting == null) {
                // TODO: Move this --> this should be set somewhere BEFORE this stage
                sectionFormatting = cssFormattingMap.get(PageGenerationConstants.DEFAULT_CONFIG_NAME);
            }

            // TODO: Remove this printing, importing sections with a default value seems to work
            System.out.println("Section: " + sectionName);
//            sectionFormatting.print();

            updateSectionInLine(contentDataMap, contentDataMap.getData(TemplateElementTypes.SECTION, sectionName), sectionFormatting);
            updateSectionMultiLine(contentDataMap, contentDataMap.getData(TemplateElementTypes.SECTION, sectionName), sectionFormatting);

            System.out.println(contentDataMap.getData(TemplateElementTypes.SECTION, sectionName));

            // TODO: Add separate checks for in-line formatting vs multi-line formatting
            // Ex: Italics and headings are in-line, code blocks and paragraphs are multi-line
            // Multi-line processing is different than in-line
        }
    }

    private void updateSectionInLine(ContentDataMap contentDataMap, ArrayList<String> sectionData, CssFormattingItem sectionFormatting) {
        // TODO: Refactor this
        // Should there be a processing class JUST for this?
        // A regex processing class might be useful

        // NOTE: Bold/Italic/Strong text share a regex
        // TODO: Investigate if the "bold" or "strong" HTML tags should be used
        if (sectionData.isEmpty()) {
            return;
        }

        for (int i = 0; i < sectionData.size(); i++) {
            String line = sectionData.get(i);

            line = checkForHeading(line, sectionFormatting);
            line = checkForItalicsOrBold(line, sectionFormatting);
            line = checkForUnorderedList(line, sectionFormatting);

//             TODO: Investigate if this needs to be multi-line
//            line = checkForOrderedList(line, sectionFormatting); // TODO: Implement this

            sectionData.set(i, line);
        }

        // Process:
        // Go line by line and check for markdown rules
        // Start by checking if a line contains a markdown symbol
        // Run regex checks for any matched markdown symbols (ex: run italic/bold check if "*" is in a line)
        // On regex hits, replace the original markdown text with relevant HTML
        // EX: Replace "#Cool text" with <h1>Cool text</h1>, with whatever css the section specifies

    }

    // TODO con2gen-20: Implement support for multi-line regex
    private void updateSectionMultiLine(ContentDataMap contentDataMap, ArrayList<String> sectionData, CssFormattingItem sectionFormatting) {
        // Look for multi-line formatting, such as code blocks and paragraphs
        // Need to mark start and end of lines that may contain multi-line formatting
        // NOTE: Code blocks can be in-line OR multi-line --> need to check in-line AND multi-line
        //          This means that code blocks may be in-line/in-line multiple times
    }

    private boolean containsPotentialHeader(String line) {
        return line.contains("#");
    }

    private boolean containsPotentialItalicOrBold(String line) {
        return line.contains("*");
    }

    private boolean containsPotentialUnorderedList(String line) {
        return line.contains("- ");
    }

    private boolean containsPotentialOrderedList(String line) {
        return line.contains("- ") || line.contains(". ");
    }

    private String checkForHeading(String line, CssFormattingItem sectionFormatting) {
        // TODO: Need to import settings for header
        if (!containsPotentialHeader(line)) {
            return line;
        }

        System.out.println("Look for header regex in line: \"" + line + "\"");  // TODO: remove print
        Matcher matcher = RegexPatterns.headerPattern.matcher(line);
        if (matcher.find()) {
            int headingLevel = matcher.group(1).length();

            // Add a ceiling of 6 to headingLevel, as <h6> is the highest header value
            if (headingLevel > 6) {
                headingLevel = 6;
            }

            String headingPrefix = getHeadingPrefix(headingLevel, sectionFormatting);
            String headingSuffix = getHeadingSuffix(headingLevel);
            String headerText = headingPrefix + matcher.group(2) + headingSuffix;
            line = line.replaceFirst(matcher.group(0), headerText);
        }
        return line;
    }

    private String getHeadingPrefix(int headingLevel, CssFormattingItem sectionFormatting) {
        if (headingLevel < 1 || headingLevel > 6) {
            headingLevel = 6;
        }
        String headingElementType = "h" + headingLevel;
        String headingFormatting = sectionFormatting.getHeadingFormatting(headingLevel);
        return getHtmlElementPrefix(headingElementType, headingFormatting);
    }

    private String getHeadingSuffix(int headingLevel) {
        if (headingLevel < 1 || headingLevel > 6) {
            headingLevel = 6;
        }
        return getHtmlElementSuffix("h" + headingLevel);
    }

    private String getHtmlElementPrefix(String htmlElementType, String htmlElementFormatting) {
        if (htmlElementFormatting == null || htmlElementFormatting.isBlank()) {
            return "<" + htmlElementType + ">";
        }
        return "<" + htmlElementType + " class=\"" + htmlElementFormatting + "\">";
    }

    private String getHtmlElementSuffix(String htmlElementType) {
        return "<\\" + htmlElementType + ">";
    }

    private String checkForItalicsOrBold(String line, CssFormattingItem sectionFormatting) {
        if (!containsPotentialItalicOrBold(line)) {
            return line;
        }

        System.out.println("Look for italics or regex in line: \"" + line + "\""); // TODO: remove print
        Matcher matcher = RegexPatterns.italicOrBoldPattern.matcher(line);
        while (matcher.find()) {
            Integer emphasisLevel = Math.min(matcher.group(1).length(), matcher.group(3).length());
            String emphasisPrefix = getEmphasisPrefix(emphasisLevel, sectionFormatting);
            String emphasisSuffix = getEmphasisSuffix(emphasisLevel);

            String emphasisText = emphasisPrefix + matcher.group(2) + emphasisSuffix;
            line = line.replace(matcher.group(0), emphasisText);
        }

        return line;
    }

    private String getEmphasisPrefix(Integer emphasisLevel, CssFormattingItem sectionFormatting) {
        if (emphasisLevel == null || emphasisLevel < 1) {
            return "";
        }

        return switch (emphasisLevel) {
            case PageGenerationConstants.ITALICS -> getItalicsPrefix(sectionFormatting);
            case PageGenerationConstants.BOLD -> getBoldPrefix(sectionFormatting);
            default -> getItalicsAndBoldPrefix(sectionFormatting);
        };
    }

    private String getEmphasisSuffix(Integer emphasisLevel) {
        if (emphasisLevel == null || emphasisLevel < 1) {
            return "";
        }

        return switch (emphasisLevel) {
            case PageGenerationConstants.ITALICS -> getItalicsSuffix();
            case PageGenerationConstants.BOLD -> getBoldSuffix();
            default -> getItalicsAndBoldSuffix();
        };
    }

    private String getItalicsPrefix(CssFormattingItem sectionFormatting) {
        if (sectionFormatting.italics == null || sectionFormatting.italics.isBlank()) {
            return "<em>";
        }
        return "<em class=\"" + sectionFormatting.italics + "\">";
    }

    private String getItalicsSuffix() {
        return "</em>";
    }

    private String getBoldPrefix(CssFormattingItem sectionFormatting) {
        if (sectionFormatting.bold == null || sectionFormatting.bold.isBlank()) {
            return "<strong>";
        }
        return "<strong class=\"" + sectionFormatting.bold + "\">";
    }

    private String getBoldSuffix() {
        return "</strong>";
    }

    private String getItalicsAndBoldPrefix(CssFormattingItem sectionFormatting) {
        return getBoldPrefix(sectionFormatting) + getItalicsPrefix(sectionFormatting);
    }

    private String getItalicsAndBoldSuffix() {
        return getItalicsSuffix() + getBoldSuffix();
    }

    private String checkForUnorderedList(String line, CssFormattingItem sectionFormatting) {
        // TODO: Refine "containsPotentialList()" to support any of the approved formats: "- ", "1. " or "-- "
        if (!containsPotentialUnorderedList(line)) {
            return line;
        }

        // TODO: Apply this for unordered lists
        System.out.println("Look for italics or regex in line: \"" + line + "\""); // TODO: remove print
//        Matcher matcher = RegexPatterns.unorderedListPattern.matcher(line);
//        while (matcher.find()) {
//            Integer emphasisLevel = Math.min(matcher.group(1).length(), matcher.group(3).length());
//            String emphasisPrefix = getEmphasisPrefix(emphasisLevel, sectionFormatting);
//
//            String emphasisText = emphasisPrefix + matcher.group(2);
//            line = line.replace(matcher.group(0), emphasisText);
//        }

        return line + "TODO: Add unordered list support";
    }

    // TODO: Figure out if there's a non-ugly way to implement this
    private Object extractObjectFromJSON(File filePath, Class<?> objectClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return(objectMapper.readValue(filePath, Object.class));
    }

    private void printProcessingObjects(TemplateExecutionOrder executionOrder, TemplateNameMap contentElementNameMap,
                       ContentDataMap contentDataMap, CssFormattingMap cssFormattingSettings) {
        System.out.println("\n\nexecutionOrder: ");
        executionOrder.print();
        System.out.println("\n\ncontentElementNameMap: ");
        contentElementNameMap.print();
        System.out.println("\n\ncontentDataMap: ");
        contentDataMap.printPartial(5);
        System.out.println("\n\ncssFormattingSettings: ");
        cssFormattingSettings.print();
    }
}
