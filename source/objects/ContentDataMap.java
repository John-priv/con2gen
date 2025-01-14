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
    public void add(String elementType, String elementName, String elementValue) {
        if (!dataItemMap.containsKey(elementType)) {
            System.out.println("Invalid element type passed into ContentDataMap: " + elementType);
            return;
        }

        if (dataItemMap.get(elementType).containsKey(elementName)) {
            dataItemMap.get(elementType).get(elementName).append(elementValue);
        }
        else {
            dataItemMap.get(elementType).put(elementName, new ContentDataMapItem(elementValue));
        }
    }

    public void setValue(String elementType, String elementName, String elementValue) {
        if (!dataItemMap.containsKey(elementType)) {
            System.out.println("Invalid element type passed into ContentDataMap: " + elementType);
            return;
        }

        if (dataItemMap.get(elementType).containsKey(elementName)) {
            dataItemMap.get(elementType).get(elementName).setValue(elementValue);
        }
        else {
            dataItemMap.get(elementType).put(elementName, new ContentDataMapItem(elementValue));
        }
    }

    public void remove(String elementType, String elementName) {
        if (!dataItemMap.containsKey(elementType)) {
            System.out.println("Invalid element type passed into ContentDataMap: " + elementType);
            return;
        }

        if (dataItemMap.get(elementType).containsKey(elementName)) {
            dataItemMap.get(elementType).get(elementName).clear();
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

    public HashMap<String, ContentDataMapItem> getElementType(String elementType) {
        if (!containsElementType(elementType)) {
            System.out.println("Invalid elementType: \"" + elementType + "\" is not in ContentDataMap");
            return null;
        }
        return dataItemMap.get(elementType);
    }

    public boolean containsKey(String elementType, String elementName) {
        if (!dataItemMap.containsKey(elementType)) {
            return false;
        }
        return dataItemMap.get(elementType).containsKey(elementName);
    }

    public boolean containsElementType(String elementType) {
        return dataItemMap.containsKey(elementType);
    }

    public void print() {
        for (String eType : dataItemMap.keySet()) {
            for (String eName : dataItemMap.get(eType).keySet()) {
                System.out.println(eType + "." + eName + ": " + dataItemMap.get(eType).get(eName).data);
            }
        }
    }

    public void printPartial(int maxSize) {
        if (maxSize < 1) {
            maxSize = 1;
        }
        for (String eType : dataItemMap.keySet()) {
            for (String eName : dataItemMap.get(eType).keySet()) {
                if (dataItemMap.get(eType).get(eName).data.size() > maxSize) {
                    System.out.println(eType + "." + eName + ": " + dataItemMap.get(eType).get(eName).data.subList(0, maxSize));
                }
                else {
                    System.out.println(eType + "." + eName + ": " + dataItemMap.get(eType).get(eName).data);
                }
            }
        }
    }
}
