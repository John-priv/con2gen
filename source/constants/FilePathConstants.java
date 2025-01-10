package constants;

import java.nio.file.Path;

public final class FilePathConstants {
    // Template Files and Directories
    public static final String CONFIG_DIRECTORY = "config";
    public static final String CONFIG_FILE = String.valueOf(Path.of(CONFIG_DIRECTORY, "configuration.json"));
    public static final String CONTENT_MD_FILE = "content.md";
    public static final String EXECUTION_ORDER_FILE = "executionOrder.json";
    public static final String TEMPLATE_ELEMENT_FILE = "templateElementMap.json"; // TODO: Rename if it makes sense to

    // Generated Page Files and Directories
    public static final String GENERATED_PAGE_DIRECTORY = "generated_pages";
}
