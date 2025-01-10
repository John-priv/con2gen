package backend;

import constants.FilePathConstants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProjectCreator {
    private final Path projectPath;

    // TODO: Clean up this class -- it can likely be just static methods
    public ProjectCreator(Path newProjectPath) {
        projectPath = newProjectPath;
    }

    public void createProject() throws IOException {
        createTemplateGeneratorDirectories(projectPath);
        createPageOutputDirectories(projectPath);
    }

    public static void createTemplateGeneratorDirectories(Path templateGeneratorDirectory) throws IOException {
        System.out.println("Creating template generator directories at: " + templateGeneratorDirectory);
        Files.createDirectories(templateGeneratorDirectory);
        Files.createDirectories(Path.of(String.valueOf(templateGeneratorDirectory), FilePathConstants.CONFIG_DIRECTORY));

        // Create template generation files:
        createFileIfDoesNotExist(Path.of(String.valueOf(templateGeneratorDirectory), FilePathConstants.EXECUTION_ORDER_FILE));
        createFileIfDoesNotExist(Path.of(String.valueOf(templateGeneratorDirectory), FilePathConstants.CONTENT_MD_FILE));
        createFileIfDoesNotExist(Path.of(String.valueOf(templateGeneratorDirectory), FilePathConstants.CONFIG_FILE));

        System.out.println("Created directory " + templateGeneratorDirectory);
    }

    public static void createPageOutputDirectories(Path pageOutputDirectory) throws IOException {
        System.out.println("Creating page output directories at: " + pageOutputDirectory);
        Files.createDirectories(Path.of(String.valueOf(pageOutputDirectory), FilePathConstants.GENERATED_PAGE_DIRECTORY));
    }

    private static void createFileIfDoesNotExist(Path fileToAdd) throws IOException {
        if (!Files.isRegularFile(fileToAdd) && !Files.isDirectory(fileToAdd)) {
            Files.createFile(fileToAdd);
        }
    }
}
