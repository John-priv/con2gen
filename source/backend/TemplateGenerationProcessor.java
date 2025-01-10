package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import constants.FilePathConstants;
import constants.PageGenerationConstants;
import objects.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Data Structure Notes:
        TemplateNameMap:
            - Used for get/store a list of types and names for the output template file
        TemplateExecutionOrder:
            - Ordered tuple for processing templates; contains type, name, and how many lines to jump

    TODO/General Notes:
        - Can only operate on one template at a time while executionOrder is global
            - To fix this, create one TemplateGenerationProcessor per template
        - Reduce/remove the number of global variables/objects after the prototype is working
        - Store validatedDirectory in a config file, then make it a private final String
            - This should be accessible to everything instead of being passed through multiple functions
            - One TemplateGenerationProcessor per template being generated
*/
public class TemplateGenerationProcessor {
    public TemplateNameMap templateNameMap = new TemplateNameMap();
    private TemplateExecutionOrder executionOrder = new TemplateExecutionOrder();
    Pattern templatePattern = getTemplateRegex();

    private static final String LONG_LINE = "--------------------------------------------------";

    public TemplateGenerationProcessor() {
    }

    public Pattern getTemplateRegex() {
        String templateRegex = "#([A-Z0-9]*)\\.(.[a-zA-Z0-9_-]*)(\\(\\))";
        return Pattern.compile(templateRegex);
    }

    private void processMatch(String keywordType, String variableName, int linesToSkip) {
        executionOrder.add(new TemplateExecutionOrderItem(keywordType, variableName, linesToSkip));
    }

    public int processLine(String lineText, int linesToSkip) {
        Matcher matcher = templatePattern.matcher(lineText);
        while (matcher.find()) {
            String keywordType = matcher.group(1);  // TODO: Change this name
            String variableName = matcher.group(2);
            processMatch(keywordType, variableName, linesToSkip);
            linesToSkip = 0;
        }
        return linesToSkip + 1;
    }

    public void generateExecutionOrderFromFile(String filePath) throws FileNotFoundException {
        executionOrder.clear();
        File fileToTemplate = new File(filePath);
        Scanner fileToTemplateReader = new Scanner(fileToTemplate);
        int linesToSkip = 0;
        String currentLine;

        while (fileToTemplateReader.hasNextLine()) {
            currentLine = fileToTemplateReader.nextLine();
            linesToSkip = processLine(currentLine, linesToSkip);
        }

        fileToTemplateReader.close();
    }

    private ArrayList<String> generateContentMarkdownLines(String validatedDirectory) {
        ArrayList<String> contentMarkdownLines = new ArrayList<>();
        TemplateElementList templateElementList = generateElementOrderList(validatedDirectory);
        contentMarkdownLines.addAll(generateVariableLines(templateElementList.variableNames));
        contentMarkdownLines.addAll(generateSectionLines(templateElementList.sectionNames));
        generateContentMarkdownLinesImages();   // TODO: Implement this
        generateContentMarkdownLinesScripts();  // TODO: Implement this
        return contentMarkdownLines;
    }

    private ArrayList<String> generateVariableLines(ArrayList<String> variableNames) {
        ArrayList<String> variableLines = new ArrayList<>(Arrays.asList(LONG_LINE, "#VARIABLES():"));
        for (String variableName : variableNames) {
            variableLines.add(variableName + ": \"\"");
        }
        variableLines.addAll(Arrays.asList("", "")); // Add two blank lines to the end of the "variables" entries
        return variableLines;
    }

    private ArrayList<String> generateSectionLines(ArrayList<String> sectionNames) {
        ArrayList<String> sectionLines = new ArrayList<>(Arrays.asList(LONG_LINE, "#SECTIONS():", ""));
        for (String sectionName : sectionNames) {
            sectionLines.add("{" + sectionName + "}:");
            sectionLines.addAll(Arrays.asList("", "")); // Add two blank lines between sections
        }
        return sectionLines;
    }

    private void generateContentMarkdownLinesImages() {
        System.out.println("TODO: Implement generateContentMarkdownLinesImages");
    }

    private void generateContentMarkdownLinesScripts() {
        System.out.println("TODO: Implement generateContentMarkdownLinesScripts");
    }

    private TemplateElementList generateElementOrderList(String validatedDirectory) {
        templateNameMap.clear();
        TemplateElementList templateElementList = new TemplateElementList();
        for (TemplateExecutionOrderItem element : executionOrder.items) {
            if (!templateNameMap.contains(element.elementType, element.elementName)) {
                templateNameMap.add(element.elementType, element.elementName);
                templateElementList.add(element.elementType, element.elementName);
            }
        }
        try {
            jsonWriteToFile(Path.of(validatedDirectory, FilePathConstants.TEMPLATE_ELEMENT_FILE), templateNameMap);
        } catch (IOException e) {
            System.out.println("Unable to store templateNameMap: " + e + ", TODO: Add proper error handling");
        }
        return templateElementList;
    }

    private String validateDirectory(String targetDirectory) {
        if (Files.isDirectory(Path.of(targetDirectory))) {
            return targetDirectory;
        }

        boolean validDirectory = false;

        while (!validDirectory) {
            Path targetDirectoryPath = Path.of(targetDirectory);
            System.out.println(targetDirectory + " is not an existing directory. Enter 'yes' create a directory");
            Scanner userTextInput = new Scanner(System.in);
            try {
                if (userTextInput.nextLine().equalsIgnoreCase("yes")) {
                    ProjectCreator.createTemplateGeneratorDirectories(targetDirectoryPath);
                    validDirectory = true;
                } else {
                    System.out.println("Enter a directory to save the template files: ");
                    targetDirectory = userTextInput.nextLine();
                }
            } catch (Exception ex) {
                System.out.println("validateDirectory exception: " + ex);
            }
        }

        return targetDirectory;
    }

    private void jsonWriteToFile(Path validatedPath, Object objectToJSON) throws IOException {
        ObjectWriter objectWriter = new ObjectMapper().writer();
        String executionString = objectWriter.writeValueAsString(objectToJSON);
        Files.writeString(validatedPath, executionString);
    }

    private void jsonWriteToFilePrettyPrint(Path validatedPath, Object objectToJSON) throws IOException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String executionString = objectWriter.writeValueAsString(objectToJSON);
        Files.writeString(validatedPath, executionString);
    }

    private void writeExecutionOrderToFile(String validatedDirectory) {
        Path executionOrderFile = Paths.get(validatedDirectory, FilePathConstants.EXECUTION_ORDER_FILE);
        try {
            jsonWriteToFile(executionOrderFile, executionOrder);
        } catch (IOException e) {
            System.out.println("Error in writeExecutionOrderToFile: " + e);
        }
    }

    private void writeContentMarkdownToFile(String validatedDirectory) {
        System.out.println("Generating content.md");
        ArrayList<String> contentMarkdownLines = generateContentMarkdownLines(validatedDirectory);

        Path contentMarkdownFilePath = Paths.get(validatedDirectory, FilePathConstants.CONTENT_MD_FILE);

        try {
            writeStringArray(contentMarkdownFilePath.toString(), contentMarkdownLines);
        } catch (IOException e) {
            System.out.println("Error in writeContentMarkdownToFile: " + e);
        }
    }

    private void writeConfigFiles(String validatedDirectory) {
        Path configFile = Paths.get(validatedDirectory, FilePathConstants.CONFIG_FILE);
        HashMap<String, ConfigObjectData> configMap = new HashMap<>();
        configMap.put(PageGenerationConstants.DEFAULT_CONFIG_NAME, new ConfigObjectData());
        try {
            jsonWriteToFilePrettyPrint(configFile, configMap);
        } catch (IOException e) {
            System.out.println("Error in writeConfigFiles: " + e);
            System.out.println("Enter \"yes\" to create \"config\" directory");
            Scanner userTextInput = new Scanner(System.in);
            if (userTextInput.nextLine().equalsIgnoreCase("yes")) {
                Path projectDirectory = Path.of(validatedDirectory);
                try {
                    ProjectCreator.createTemplateGeneratorDirectories(projectDirectory);
                    System.out.println("Created directory " + validatedDirectory);
                    jsonWriteToFilePrettyPrint(configFile, configMap);
                } catch (IOException ex) {
                    System.out.println("Failed to create config directory at: " + projectDirectory);
                }
            }
        }
    }

    private void writeStringArray(String filePath, ArrayList<String> stringArray) throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        for (String line : stringArray) {
            bufferedWriter.write(line);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    // TODO: Refactor this when working on "update-templates", require templateNameMap and executionOrder as inputs
    // NOTE: This function is the "hard-add" version: it WILL overwrite existing files BY DESIGN
    // Should this be here or somewhere else?
    public void generateTemplateOutputs(String targetDirectory) {
        String validatedDirectory = validateDirectory(targetDirectory);

        writeExecutionOrderToFile(validatedDirectory);
        writeContentMarkdownToFile(validatedDirectory);
        writeConfigFiles(validatedDirectory);

        // create template hash
        // TODO: Implement template hash creation + validation
    }

    // TODO: Remove this if it's not needed (after "update-template" workflow)
    // It may make sense to have "generateNewTemplate" and "updateExistingTemplate" functions to make calling simpler
    public void generateNewTemplate(String templateFilePath, String outputDirectory) {
        try {
            generateExecutionOrderFromFile(templateFilePath);
            generateTemplateOutputs(outputDirectory);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file: " + templateFilePath);
        }
    }

    public void updateExistingTemplate(String templateFilePath, String outputDirectory) {
        try {
            generateExecutionOrderFromFile(templateFilePath);
            System.out.println("TODO: Add code to update existing template at " + outputDirectory);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file: " + templateFilePath);
        }
    }
}
