package backend;

import objects.TemplateNameMap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

// TODO: Need to focus on just passing along mock data
public class Con2Gen {
    private final Scanner userTextInput = new Scanner(System.in);
    private final String sampleProjectPathString = "../sample-project-con2gen"; // TODO: Remove after prototype

    protected void runCon2Gen(){
        boolean runProgram = true;
        String userSelection = "";

        System.out.println("TODO: Upgrade this to a proper CLI, optionally add a GUI version");

        // TODO: Make this into a GUI or into command line options
        while (runProgram) {
            // prompt user for input
            System.out.println("/----------------------------------------------------------------/\n" +
                    "Select an option:\n" +
                    "create-project: Create a new Con2Gen project\n" +
                    "template: Create a new template/update existing template\n" + // TODO: Should these options be separated?
                    "update-template: Update an existing template\n" +
                    "page: Generate a single content page\n" +
                    "page-dir: Generate content pages for a directory\n" +
                    "exit: Exit\n" +
                    "/----------------------------------------------------------------/");
            userSelection = userTextInput.nextLine().toLowerCase();
            runProgram = getProgramOperation(userSelection);
        }
    }

    private boolean getProgramOperation(String userSelection) {
        boolean runProgram = true;

        switch(userSelection) {
            case "exit":
                runProgram = false;
                System.out.println("Ending execution");
                break;
            case "create-project":
                System.out.println("Creating a new project");
                createProject();
                break;
            case "template":
                System.out.println("Generating template (TODO: implement this)");
                generateTemplates();
                break;
            case "update-template":
                System.out.println("Update template (TODO: implement this AFTER 'template', 'page', and 'page-dir')");
                generateTemplates();
                break;
            case "page":
                System.out.println("Generating single page (TODO: implement this)");
                generatePage();
                break;
            case "page-dir":
                System.out.println("Generating directory of pages (TODO: implement this");
                break;
            default:
                System.out.println("Invalid user input: " + userSelection);
        }
        return runProgram;
    }

    private void importTemplate(String templatePath) {
        System.out.println("Importing Template: " + templatePath);
    }

    private void generateTemplates() {
        System.out.println("Generating Templates from marked up page");

        System.out.println("Absolute path to a file to extract/make into a template:");
        String inputTemplateFile = userTextInput.nextLine();

        // TODO: Make this configurable based on either user input OR a config file/input command
        // Add a CLI command that takes both the template file and output directory as inputs
        // I'd use the "userTextInput" approach but I'm too lazy to copy/paste two things per execution
        String outputDirectory = sampleProjectPathString;

        TemplateGenerationProcessor templateGenerationProcessor = new TemplateGenerationProcessor();
        templateGenerationProcessor.generateNewTemplate(inputTemplateFile, outputDirectory);
    }

    private void generatePage() {
        // TODO: This should be able to be done via a project config file
        System.out.println("Generating page from template");

        System.out.println("Enter path to Con2Gen project"); // TODO: Organize this
        String projectDirectory = sampleProjectPathString;

        PageGenerator pageGenerator = new PageGenerator();

        System.out.println("Enter path for a page to generate: ");
//        String pageDirectory = userTextInput.nextLine(); // Test page: "../sample-project-con2gen/pages/test_page"
        String pageDirectory = "../sample-project-con2gen/pages/test_page"; // TODO: Make this based on user input

        // TODO: Make this based on user input/config file/extracting page name from pageDirectory
        String outputDirectory = Path.of(projectDirectory, "/generated_pages/test_page").toString();

        pageGenerator.generatePage(projectDirectory, pageDirectory, outputDirectory);
    }

    private void createProject() {
        System.out.println("Enter path for new project: ");
        String userSelection = userTextInput.nextLine();
        Path projectPath = Path.of(userSelection);
        System.out.println("Create new project at \"" + projectPath + "\"? Enter \"yes\" to confirm");
            if (userTextInput.nextLine().equalsIgnoreCase("yes")) {
                System.out.println("Creating project");
                ProjectCreator projectCreator = new ProjectCreator(projectPath);
                try {
                    projectCreator.createProject();
                } catch (IOException e) {
                    System.out.println("Failed to create project at " + projectPath + "due to: " + e);
                }
            }
    }

}

/*
    Future Ideas:
        - Hashing the template file
            - Stored hashed template file to check if a template has been edited since the last "regenerate-all"
        - Hashed markdown/content file
            - Used to quickly check if any updates were made to a file to speed up a "generate-all" call
        - "Smart-generate-all"
            - Generates files that have different template or content hashes, if this is faster than regenerating all
        - "Generate-all-force"
            - Ignores hashes and forces all pages to fully regenerate. Only needed if smart generate is added
*/