package backend;

import objects.SectionMarker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Need to focus on just passing along mock data
public class Con2Gen {
    private final Scanner userTextInput = new Scanner(System.in);

    protected void runCon2Gen(){
        boolean runProgram = true;
        String userSelection = "";

        System.out.println("TODO: Upgrade this to a proper CLI, optionally add a GUI version");

        // TODO: Make this into a GUI or into command line options
        while (runProgram) {
            // prompt user for input
            System.out.println("/----------------------------------------------------------------/\n" +
                    "Select an option:\n" +
                    "template: Create a new template/update existing template\n" + // TODO: Should these options be separated?
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
            case "template":
                System.out.println("Generating template (TODO: implement this)");
                generateTemplates();
                break;
            case "page":
                System.out.println("Generating single page (TODO: implement this");
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

     /*
     TODO: verify that the formats of "#SECTION.", "#VARIABLE.", "#SCRIPT.", and "#IMAGE." aren't used by major tools
     TODO: Add ability to have optional WRAPPED sections
        These "optional wrapped sections" should only generate if there is content for them
         Ex: If no nutrition info is provided, no nutrition info section should be added
         Ex2: A section for "More Images" should only generate if there are more images
             Any HTML/CSS related to it should not appear if there's no content
             Title "More Images", the image files, and any border/grid should only be created if there's content
             If the "More Images" content file is blank, don't generate ANY adjacent web code
         This can probably be done via a new flag + brackets
         Sections that aren't optional don't need this extra overhead
     Question: Should ALL sections be bracketed?
         That would make things consistent, but it feels unneeded in a lot of cases
             It seems better if EVERY section behaves like an optional section
         Adding ANY bracketed sections would make nesting a potential problem
             If I have to deal with nesting at all, it might be worth just actually dealing with it
             Option 1: Make ANY nesting fail
                 I think this is a good option at first
             Option 2: Allow ALL nesting
                 Generate any portions that are nested even if the things above them are not
             Option 3: Allow nesting ONLY when all layers are complete
                 If every section in a nest has content, then generate the full nest
     Solution:
         All three options can eventually be supported through configurations
         For the first approach, no nested brackets will be supported
             Other cases can be added later on if the need arises

    Section formatting should be done outside of the template page
        In the template page, sections/variables/etc should be simple
            Any additional formatting should be done elsewhere
            If a section requires <ol> and <li>, they should be set in separate file

    */
    private void generateTemplates() {
        System.out.println("Generating Templates from marked up page");

        System.out.println("Absolute path to a file to extract/make into a template:");

        String sectionRegex = "#SECTION.(.[a-zA-Z0-9_]*)(\\(\\))";
        String variableRegex = "#VARIABLE.(.[a-zA-Z0-9_]*)(\\(\\))";
        String scriptRegex = "#SCRIPT.(.[a-zA-Z0-9_]*)(\\(\\))";
        String imageRegex = "#IMAGE.(.[a-zA-Z0-9_]*)(\\(\\))";

        // TODO: Replace this with CLI input
        String filePath = userTextInput.nextLine();
        Pattern sectionPattern = Pattern.compile(sectionRegex);
        HashMap<String, Integer> templateSectionLines = new HashMap<>();

        try {
            File fileToTemplate = new File(filePath);
            Scanner fileToTemplateReader = new Scanner(fileToTemplate);
            String currentLine = "";

            // Currently this just reads the file. Add logic for creating template
            // This logic should make a list of all marked sections and variables
            //      #SECTION.section_name()
            //      #VARIABLE.variable_name()
            //      #SCRIPT.script_name()
            while (fileToTemplateReader.hasNextLine()) {
                currentLine = fileToTemplateReader.nextLine();

                // TODO: Extract this logic into a separate function
                // Temporary regex for #SECTION.sectionName(): "(#SECTION.(.[a-zA-Z0-9_]*)(\(\)))"
                // Use contains() first to check for any matches, as it's significantly faster
                // Further processing can be done using regex to extract section names
                // TODO: See if this is even worth it. I think doing this should save processing power
                // There's a bit more duplication of searching for #SECTION., but it's still likely faster
                if (currentLine.contains("#SECTION.")) {
                    Matcher matcher = sectionPattern.matcher(currentLine);
                    // Look for instances of #SECTION.section_name()
                    // Add instances to a Map of Lists of line numbers
                        // Ex: variables['recipe_name'] = [46, 93, 102] // the lines #VARIABLE.recipe_name() appears on
//                    matcher.
                    // TODO: Add code to get all valid sections and extract section names + line numbers
                    System.out.println("(light) Match found: " + currentLine);
                }
//                System.out.println(currentLine);
            }
            fileToTemplateReader.close();
        }
        catch (FileNotFoundException exception) {
            System.out.println("Invalid file path: " + filePath);
            return;
        }



        // TODO: should I mock out this whole thing, or just hard code print a template?
        // For V1: User inputs a path to the template file
        // This should open the template file and extract each line
        // I think processing should be added here during the initial approach?
        // I'd rather not mock this --> can do a really bad implementation instead

//        HashMap<String, SectionMarker> sectionMap = new HashMap<String, SectionMarker>();

        // TODO: Implement actual process
        // Open marked up page

        // Extract the formatting from the section

        // Get all section names, start lines, and end lines (div that start with "generator_template_SECTION_NAME")

        // Create a mock
    }

    private void mockGenerateTemplate() {
        System.out.println("Generating Mock Templates");
    }

    // Add functions for each of the above steps of the code. These can be refactored to separate files later on

}

//// General states: TODO: move these to relevant spots

//        // Select file to make into a template
//        importFile();
//
//        // Generate templates
//        generateTemplates();
//
//        // Content.md creation logic
//        System.out.println("Creating/updating 'Content.md'");
//
//        // Variables.json creation logic
//        // TODO: Figure out format for Variables file, as it may not be json/markdown
//        System.out.println("Creating/updating 'Variables.json'");
//
//        // Generate section files from Content.md
//        System.out.println("Generating section files from `Content.md`");
//
//        // Clone marked up page for processing
//        System.out.println("Cloning marked up page");
//
//        // Process section files into the marked up page format
//        System.out.println("Processing and adding section content data to output file");
//
//        // Save/rename/move/finish processing output file
//        System.out.println("Completing processing for content file");