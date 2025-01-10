package objects;

import java.util.Arrays;
import java.util.HashSet;

public class TemplateElementTypes {
    public static final String SECTION = "SECTION";
    public static final String VARIABLE = "VARIABLE";
    public static final String IMAGE = "IMAGE";
    public static final String SCRIPT = "SCRIPT";

    public static final HashSet<String> ELEMENT_TYPES = new HashSet<>(Arrays.asList(SECTION, VARIABLE, IMAGE, SCRIPT));
}
