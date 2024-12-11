package backend;

import objects.TemplateExecutionOrder;
import objects.TemplateNameMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    Pattern templatePattern = getTemplateRegex();

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

    public void processFile(String filePath) {
        try {
            File fileToTemplate = new File(filePath);
            Scanner fileToTemplateReader = new Scanner(fileToTemplate);
            int linesToSkip = 0;
            String currentLine;

            while (fileToTemplateReader.hasNextLine()) {
                currentLine = fileToTemplateReader.nextLine();
                linesToSkip = processLine(currentLine, linesToSkip);
            }

            templateNameMap.print();
            for (TemplateExecutionOrder templateExecutionOrder : executionOrder) {
                templateExecutionOrder.print();
            }

            fileToTemplateReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Invalid file path: " + filePath);
        }
    }

    // Should this be here or somewhere else?
    // TODO: Refactor this when working on "update-templates", require templateNameMap and executionOrder as inputs
    // NOTE: This function is the "hard-add" version: it WILL overwrite existing files BY DESIGN
    public void generateTemplateOutputs(String outputDirectory) {
        try {
            Files.createDirectories(Paths.get(outputDirectory));
            System.out.println("Created directory " + outputDirectory);
        } catch (IOException e) {
            System.out.println("FilePath issue with " + outputDirectory + ": " + e);
//            throw new RuntimeException(e); // TODO: Figure out if this should throw an exception
        }

        // create execution.json

        // create template.md

        // create template hash
        // TODO: Implement template hash creation + validation

        // generate the output template.md, execution.json, and template hash files
        // template.md comes from templateNameMap --> Variables, Sections, Images, Scripts
    }

    // TODO: Remove this if it's not needed (after "update-template" workflow)
    public void generateTemplate(String templateFilePath, String outputDirectory) {
        processFile(templateFilePath);
        generateTemplateOutputs(outputDirectory);
    }
}
