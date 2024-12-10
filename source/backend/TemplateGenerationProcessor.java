package backend;

import objects.TemplateIndexMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateGenerationProcessor {
    public TemplateIndexMap templateIndexMap;
    Pattern templatePattern = getTemplateRegex();

    public TemplateGenerationProcessor(TemplateIndexMap indexMap) {
        templateIndexMap = indexMap;
    }

    public Pattern getTemplateRegex() {
        String templateRegex = "#([A-Z0-9]*)\\.(.[a-zA-Z0-9_]*)(\\(\\))";
        return Pattern.compile(templateRegex);
    }

    private void processMatch(String keywordType, String variableName, int lineNumber) {
        templateIndexMap.add(keywordType, variableName, lineNumber);
    }

    public void processLine(String lineText, int lineNumber) {
        Matcher matcher = templatePattern.matcher(lineText);
        while (matcher.find()) {
            String keywordType = matcher.group(1);  // TODO: Change this name
            String variableName = matcher.group(2);
            processMatch(keywordType, variableName, lineNumber);
        }
    }

    public void processFile(String filePath) {
        try {
            File fileToTemplate = new File(filePath);
            Scanner fileToTemplateReader = new Scanner(fileToTemplate);
            int lineNumber = 0;
            String currentLine;

            while (fileToTemplateReader.hasNextLine()) {
                currentLine = fileToTemplateReader.nextLine();
                processLine(currentLine, lineNumber);
                lineNumber++; // TODO: Figure out if this should be at the start or end of the loop --> off by 1 issue
            }

            templateIndexMap.print();

            fileToTemplateReader.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Invalid file path: " + filePath);
        }
    }
}
