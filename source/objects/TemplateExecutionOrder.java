package objects;

import java.util.ArrayList;

public class TemplateExecutionOrder {
    public ArrayList<TemplateExecutionOrderItem> items;

    public TemplateExecutionOrder() {
        items = new ArrayList<>();
    }

    public void add(TemplateExecutionOrderItem templateExecutionOrderItem) {
        items.add(templateExecutionOrderItem);
    }

    public void clear() {
        items.clear();
    }

    public void print() {
        for (TemplateExecutionOrderItem element : items) {
            element.print();
        }
    }
}
