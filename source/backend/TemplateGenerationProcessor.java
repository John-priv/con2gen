package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
        - Move "default" to a constants file
*/
public class TemplateGenerationProcessor {
    public TemplateNameMap templateNameMap = new TemplateNameMap();
    private ArrayList<TemplateExecutionOrder> executionOrder = new ArrayList<>();
    Pattern templatePattern = getTemplateRegex();

    private final String CONFIG_DIRECTORY = "config";
    private final String CONFIG_FILE = "configuration.json";
    private final String CONTENT_MD_FILE = "content.md";
    private final String EXECUTION_ORDER_MD = "executionOrder.json";

    private static final String LONG_LINE = "--------------------------------------------------";

    public TemplateGenerationProcessor() {
    }

    public Pattern getTemplateRegex() {
        String templateRegex = "#([A-Z0-9]*)\\.(.[a-zA-Z0-9_-]*)(\\(\\))";
        return Pattern.compile(templateRegex);
    }

    private void processMatch(String keywordType, String variableName, int linesToSkip) {
        executionOrder.add(new TemplateExecutionOrder(keywordType, variableName, linesToSkip));
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
        executionOrder = new ArrayList<>();
        File fileToTemplate = new File(filePath);
        Scanner fileToTemplateReader = new Scanner(fileToTemplate);
        int linesToSkip = 0;
        String currentLine;

        while (fileToTemplateReader.hasNextLine()) {
            currentLine = fileToTemplateReader.nextLine();
            linesToSkip = processLine(currentLine, linesToSkip);
        }

        fileToTemplateReader.close();

        // TODO: Clean up printing or move it behind a config
        templateNameMap.print();
        for (TemplateExecutionOrder templateExecutionOrder : executionOrder) {
            templateExecutionOrder.print();
        }
    }

    private ArrayList<String> generateContentMarkdownLines() {
        ArrayList<String> contentMarkdownLines = new ArrayList<>();
        TemplateElementList templateElementList = generateElementOrderList();
        contentMarkdownLines.addAll(generateVariableLines(templateElementList.variableNames));
        contentMarkdownLines.addAll(generateSectionLines(templateElementList.sectionNames));
        generateContentMarkdownLinesImages();   // TODO: Implement this
        generateContentMarkdownLinesScripts();  // TODO: Implement this
        templateElementList.print();
        System.out.println("contentMarkdownLines: " + contentMarkdownLines);
        return contentMarkdownLines;
    }

    private ArrayList<String> generateVariableLines(ArrayList<String> variableNames) {
        ArrayList<String> variableLines = new ArrayList<>(Arrays.asList("VARIABLES:", LONG_LINE));
        for (String variableName : variableNames) {
            variableLines.add(variableName + ": \"\"");
        }
        return variableLines;
    }

    private ArrayList<String> generateSectionLines(ArrayList<String> sectionNames) {
        ArrayList<String> sectionLines = new ArrayList<>(Arrays.asList("", "", "SECTIONS:"));
        for (String sectionName : sectionNames) {
            sectionLines.add(LONG_LINE);
            sectionLines.add(sectionName + ":");
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

    private TemplateElementList generateElementOrderList() {
        templateNameMap.clear();
        TemplateElementList templateElementList = new TemplateElementList();
        for (TemplateExecutionOrder element : executionOrder) {
            if (!templateNameMap.contains(element.elementType, element.elementName)) {
                templateNameMap.add(element.elementType, element.elementName);
                templateElementList.add(element.elementType, element.elementName);
            }
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
                    Files.createDirectories(targetDirectoryPath);
                    Files.createDirectories(Path.of(targetDirectory, CONFIG_DIRECTORY));
                    System.out.println("Created directory " + targetDirectoryPath);
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
        Path executionOrderFile = Paths.get(validatedDirectory, EXECUTION_ORDER_MD);
        try {
            jsonWriteToFile(executionOrderFile, executionOrder);
        } catch (IOException e) {
            System.out.println("Error in writeExecutionOrderToFile: " + e);
        }
    }

    private void writeContentMarkdownToFile(String validatedDirectory) {
        System.out.println("Generating content.md");
        ArrayList<String> contentMarkdownLines = generateContentMarkdownLines();

        Path contentMarkdownFilePath = Paths.get(validatedDirectory, CONTENT_MD_FILE);

        try {
            writeStringArray(contentMarkdownFilePath.toString(), contentMarkdownLines);
        } catch (IOException e) {
            System.out.println("Error in writeContentMarkdownToFile: " + e);
        }
    }

    private void writeConfigFiles(String validatedDirectory) {
        Path configFile = Paths.get(validatedDirectory, CONFIG_DIRECTORY, CONFIG_FILE);
        HashMap<String, ConfigObjectData> configMap = new HashMap<>();
        configMap.put("default", new ConfigObjectData());
        try {
            jsonWriteToFilePrettyPrint(configFile, configMap);
        } catch (IOException e) {
            System.out.println("Error in writeConfigFiles: " + e);
            System.out.println("Enter \"yes\" to create \"config\" directory");
            Scanner userTextInput = new Scanner(System.in);
            if (userTextInput.nextLine().equalsIgnoreCase("yes")) {
                Path configDirectoryPath = Path.of(validatedDirectory, CONFIG_DIRECTORY);
                try {
                    Files.createDirectories(configDirectoryPath);
                    System.out.println("Created directory " + validatedDirectory);
                    jsonWriteToFilePrettyPrint(configFile, configMap);
                } catch (IOException ex) {
                    System.out.println("Failed to create config directory at: " + configDirectoryPath);
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
