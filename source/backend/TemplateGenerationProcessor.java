package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import objects.TemplateExecutionOrder;
import objects.TemplateNameMap;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
    Data Structure Notes:
        TemplateNameMap:
            - Used for get/store a list of types and names for the output template file
        TemplateExecutionOrder:
            - Ordered tuple for processing templates; contains type, name, and how many lines to jump
*/
public class TemplateGenerationProcessor {
    public TemplateNameMap templateNameMap = new TemplateNameMap();
    public ArrayList<TemplateExecutionOrder> executionOrder = new ArrayList<>();
    public String TemplateMarkdownContent;
    Pattern templatePattern = getTemplateRegex();

    private final String executionOrderFileName = "executionOrder.json";

    public TemplateGenerationProcessor() {
    }

    public Pattern getTemplateRegex() {
        String templateRegex = "#([A-Z0-9]*)\\.(.[a-zA-Z0-9_]*)(\\(\\))";
        return Pattern.compile(templateRegex);
    }

    private void processMatch(String keywordType, String variableName, int linesToSkip) {
        templateNameMap.add(keywordType, variableName);
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

    public void processFile(String filePath) throws FileNotFoundException {
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

        generateContentMarkdownString();
    }

    private void generateContentMarkdownString() {
        // TODO: Implement this function
        /* Take the stored "templateNameMap" OR "executionOrder"
            Generate a multi-line markdown string to become the "content.md" file
            Figure out if the TemplateNameMap needs to be ordered, or if execution order can be used
            Ideally the content.md file will either:
                - Be grouped by type, in order (i.e. all variables first, then all sections)
                OR
                - Be in order, ignoring type (in whatever order they're in execution order, by first instance)
                    - Note: repeating non-variable keywords is likely uncommon, so first instance is likely cleanest
        */
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

    private void writeExecutionOrderToFile(String validatedDirectory) {
        Path executionOrderFile = Paths.get(validatedDirectory, executionOrderFileName);
        try {
            ObjectWriter objectWriter = new ObjectMapper().writer();
            String executionString = objectWriter.writeValueAsString(executionOrder);
            Files.writeString(executionOrderFile, executionString);
        } catch (IOException e) {
            System.out.println("Error in writeExecutionOrderToFile: " + e);
        }
    }

    private void writeContentMarkdownToFile(String validatedDirectory) {
        // TODO: Implement this function
        // Note: This should not be using JSON
        // content.md comes from templateNameMap --> Variables, Sections, Images, Scripts
        // This function should ideally not overwrite existing files automatically -- it should ask or append instead
        System.out.println("Generating content.md");
    }

    // TODO: Refactor this when working on "update-templates", require templateNameMap and executionOrder as inputs
    // NOTE: This function is the "hard-add" version: it WILL overwrite existing files BY DESIGN
    // Should this be here or somewhere else?
    public void generateTemplateOutputs(String targetDirectory) {
        String validatedDirectory = validateDirectory(targetDirectory);

        writeExecutionOrderToFile(validatedDirectory);
        writeContentMarkdownToFile(validatedDirectory);

        // create template hash
        // TODO: Implement template hash creation + validation
    }

    // TODO: Remove this if it's not needed (after "update-template" workflow)
    // It may make sense to have "generateNewTemplate" and "updateExistingTemplate" functions to make calling simpler
    public void generateNewTemplate(String templateFilePath, String outputDirectory) {
        try {
            processFile(templateFilePath);
            generateTemplateOutputs(outputDirectory);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file: " + templateFilePath);
        }
    }

    public void updateExistingTemplate(String templateFilePath, String outputDirectory) {
        try {
            processFile(templateFilePath);
            System.out.println("TODO: Add code to update existing template at " + outputDirectory);
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file: " + templateFilePath);
        }
    }
}
