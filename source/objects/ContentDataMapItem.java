package objects;

import java.util.ArrayList;

public class ContentDataMapItem {
    // TODO: Investigate having data be either an ArrayList or a String based on element type
    // Can use a "getData" or "get" type of function to return the respective data type (if this is implemented)
    // The idea behind this is that types like variables are always just one string (array length 1)
    //      Could make post-processing more simple/clear if there's a simple "smart get" function
    //      Any variable processing should only need a single string (instead of an array of strings)
    public ArrayList<String> data = new ArrayList<>();

    public ContentDataMapItem() {
        super();
    }

    public ContentDataMapItem(String value) {
        data.add(value);
    }

    public void update(ArrayList<String> value) {
        data = value;
    }

    public void setValue(String value) {
        clear();
        data.add(value);
    }

    public void append(String value) {
        data.add(value);
    }

    public void clear() {
        data.clear();
    }

}
