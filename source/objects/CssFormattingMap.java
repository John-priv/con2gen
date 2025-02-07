package objects;

import constants.PageGenerationConstants;

import java.util.HashMap;

public class CssFormattingMap {
    public HashMap<String, CssFormattingItem> cssFormattingSettings = new HashMap<>();

    public CssFormattingMap() {
        super();
        cssFormattingSettings.put(PageGenerationConstants.DEFAULT_CONFIG_NAME, new CssFormattingItem());
        cssFormattingSettings.get(PageGenerationConstants.DEFAULT_CONFIG_NAME).setAllToBlank();
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
        return null;
    }

    public void print() {
        for (String formatSettings : cssFormattingSettings.keySet()) {
//            System.out.println("CSS Format Settings for \"" + formatSettings + "\": ");
            System.out.println("    " + formatSettings + ": ");
            cssFormattingSettings.get(formatSettings).print();
        }
    }

    // TODO: Rename this function and its variables --> it's messy
    //       There should be a function to replace/fix ALL elements (based on section names)
    //       The function that does this should set any sections that don't have a key to be the default value
    //       It should also set any null values in the "default" config to be blank
    //       Ideally the following function would be a helper function to make/verify/fix the actual CssFormattingMap
    public CssFormattingItem getAndReplaceEmpty(String key, CssFormattingItem defaultFormatting) {
        CssFormattingItem formattingToGet = cssFormattingSettings.get(key);
        if (formattingToGet == null) {
            return cssFormattingSettings.get(PageGenerationConstants.DEFAULT_CONFIG_NAME);
        }
        if (defaultFormatting == null) {
            defaultFormatting = new CssFormattingItem();
            defaultFormatting.setAllToBlank();
        }

        // Note: This currently would break if the defaultFormatting has a null value
        // Solution: Either fix this, or just don't manually set the default values to null
        if (formattingToGet.h1 == null) {formattingToGet.h1 = defaultFormatting.h1;}
        if (formattingToGet.h2 == null) {formattingToGet.h2 = defaultFormatting.h2;}
        if (formattingToGet.h3 == null) {formattingToGet.h3 = defaultFormatting.h3;}
        if (formattingToGet.h4 == null) {formattingToGet.h4 = defaultFormatting.h4;}
        if (formattingToGet.h5 == null) {formattingToGet.h5 = defaultFormatting.h5;}
        if (formattingToGet.h6 == null) {formattingToGet.h6 = defaultFormatting.h6;}
        if (formattingToGet.p == null) {formattingToGet.p = defaultFormatting.p;}
        if (formattingToGet.div == null) {formattingToGet.div = defaultFormatting.div;}
        if (formattingToGet.img == null) {formattingToGet.h1 = defaultFormatting.img;}
        if (formattingToGet.hr == null) {formattingToGet.hr = defaultFormatting.hr;}
        if (formattingToGet.orderedList == null) {formattingToGet.orderedList = defaultFormatting.orderedList;}
        if (formattingToGet.unorderedList == null) {formattingToGet.unorderedList = defaultFormatting.unorderedList;}
        if (formattingToGet.code == null) {formattingToGet.code = defaultFormatting.code;}
        if (formattingToGet.blockQuote == null) {formattingToGet.blockQuote = defaultFormatting.blockQuote;}

        return formattingToGet;
    }

//    // TODO: Remove this if it's not needed
//    // Is an "updateValue" function needed? It's probably fine just using "put"
//    public void updateValue(String configObjectKey, String objectToUpdate, String value) {
//        ConfigObjectData configObjectData = cssFormattingSettings.get(configObjectKey);
//        if (configObjectData)
//    }
}
