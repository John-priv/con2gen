package backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import constants.FilePathConstants;
import objects.ContentDataMap;
import objects.TemplateExecutionOrder;
import objects.TemplateExecutionOrderItem;
import objects.TemplateNameMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class PageGenerator {

    public PageGenerator() {
    }

    // TODO: Simplify this function call if possible. Need to balance flexibility with simplicity (for users)
    public void generatePage(String projectDirectory, String pageDirectory, String outputDirectory) {
        System.out.println("TODO: Implement this. Generating a single page");
        TemplateNameMap contentNameMap;
        TemplateExecutionOrder executionOrder;
        ContentDataMap contentDataMap;
        // TODO: Import ContentMD. Type should be multi level map --> [element_type][element_name][section_contents]

        try {
            executionOrder = getExecutionOrder(Path.of(projectDirectory, FilePathConstants.EXECUTION_ORDER_FILE));
            contentNameMap = getContentNameMap(Path.of(projectDirectory, FilePathConstants.TEMPLATE_ELEMENT_FILE));
            contentDataMap = getContentDataMap(Path.of(pageDirectory, FilePathConstants.CONTENT_MD_FILE));
        }
        catch (IOException ioException) {
            System.out.println("IOException in generatePage: " + ioException);
            System.out.println("Unable to generate page -- template may need to be recreated");
            return;
        }

        // TODO: Clean up printing when generatePage is completed
        // Note: Printing may be able to stay until unit testing is added
        System.out.println("executionOrder: ");
        executionOrder.print();
        System.out.println("contentNameMap: ");
        contentNameMap.print();

//        contentElementList = extractContentMD(Path.of(pageDirectory, FilePathConstants.CONTENT_MD_FILE));

//        executionOrder
//        Path projectPath = Path.of(projectDirectory);
//        Path pagePath = Path.of(pageDirectory);

        // Processing order:
        // executionOrder (import = done)
        // TemplateElementMap (import = done)
        // ContentMD (Prepare for processing/separate each variable/section/etc) (import = TODO)
        // Settings (Read/import where needed, fall back to default if a section does not have custom settings)
        // ContentMD (Process each variable/section/etc using imported settings
        // Copy the Base/Template File (ex: The HTML file the template is based on)
        // executionOrder (replace variables/sections/etc over Base/Template File)
        // Save output file to outputDirectory

        // ContentMD
        // How to process:


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

    private ContentDataMap getContentDataMap(Path contentMDPath) throws IOException {
        ContentDataMap contentDataMap = new ContentDataMap();

        File contentMDFile = contentMDPath.toFile();
        Scanner contentMDFileReader = new Scanner(contentMDFile);
        String currentLine;

        while (contentMDFileReader.hasNextLine()) {
            currentLine = contentMDFileReader.nextLine();
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

    // TODO: Change the type of this to fit its usecase
    private void extractContentMD(Path contentMDPath) {
        // Step 1: Import contentMD

        // Step 2: Populate the TemplateElementList
        System.out.println("TODO: Implement this");
    }

    // TODO: Figure out if there's a non-ugly way to implement this
    private Object extractObjectFromJSON(File filePath, Class<?> objectClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return(objectMapper.readValue(filePath, Object.class));
    }
}
