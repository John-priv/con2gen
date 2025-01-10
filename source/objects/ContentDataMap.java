package objects;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentDataMap {
    public HashMap<String, HashMap<String, ContentDataMapItem>> dataItemMap;

    public ContentDataMap() {
        dataItemMap = new HashMap<>();
        for (String element : TemplateElementTypes.ELEMENT_TYPES) {
            dataItemMap.put(element, new HashMap<>());
        }
    }

    // TODO: Do data processing AFTER the initial add (this is more of a note for PageGenerator)
    public void add(String elementType, String elementName, ArrayList<String> elementValue) {
        if (!dataItemMap.containsKey(elementType)) {
            System.out.println("Invalid element type passed into ContentDataMap: " + elementType);
            return;
        }

        if (dataItemMap.get(elementType).containsKey(elementName)) {
            dataItemMap.get(elementType).get(elementName).update(elementValue);
        }
        else {
            dataItemMap.get(elementType).put(elementName, new ContentDataMapItem(elementValue));
        }
    }

    public ArrayList<String> getData(String elementType, String elementName) {
        if (!dataItemMap.containsKey(elementType)) {
            System.out.println("Invalid element type passed into ContentDataMap: " + elementType);
            return new ArrayList<>();
        }

        if (dataItemMap.get(elementType).containsKey(elementName)) {
            return dataItemMap.get(elementType).get(elementName).data;
        }
        else {
            System.out.println(elementName + " is not in the ContentDataMap.");
            return new ArrayList<>();
        }

    }

    // TODO: Implement this class
    // This class should have something like the following format:
    //      contentDataMap[element_type][element_name] = "text data for a section/variable"
    //      or
    //      contentDataMap[element_type][element_name].data = "text data for a section/variable"
}
