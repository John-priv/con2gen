package objects;

import java.util.ArrayList;

public class ContentDataMapItem {
    public ArrayList<String> data = new ArrayList<>();

    public ContentDataMapItem() {
        super();
    }

    public ContentDataMapItem(ArrayList<String> value) {
        data = value;
    }

    public void update(ArrayList<String> value) {
        data = value;
    }
}
