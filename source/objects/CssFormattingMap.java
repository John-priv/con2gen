package objects;

import constants.PageGenerationConstants;

import java.util.HashMap;

public class CssFormattingMap {
    public HashMap<String, CssFormattingItem> cssFormattingSettings = new HashMap<>();

    public CssFormattingMap() {
        super();
        cssFormattingSettings.put(PageGenerationConstants.DEFAULT_CONFIG_NAME, new CssFormattingItem());
    }

    public void put(String configObjectKey, CssFormattingItem cssFormattingItem) {
        cssFormattingSettings.put(configObjectKey, cssFormattingItem);
    }

    public CssFormattingItem get(String configObjectKey) {
        // TODO: Add proper error handling for key misses
        if (cssFormattingSettings.containsKey(configObjectKey)) {
            return cssFormattingSettings.get(configObjectKey);
        }
        else {
            System.out.println("ConfigObjectMap does not contain key: " + configObjectKey);
        }
        return new CssFormattingItem(); // Return an empty ConfigDataObject if a configuration does not exist
    }

    public void print() {
        for (String formatSettings : cssFormattingSettings.keySet()) {
//            System.out.println("CSS Format Settings for \"" + formatSettings + "\": ");
            System.out.println("    " + formatSettings + ": ");
            cssFormattingSettings.get(formatSettings).print();
        }
    }

//    // TODO: Remove this if it's not needed
//    // Is an "updateValue" function needed? It's probably fine just using "put"
//    public void updateValue(String configObjectKey, String objectToUpdate, String value) {
//        ConfigObjectData configObjectData = cssFormattingSettings.get(configObjectKey);
//        if (configObjectData)
//    }
}
