package attribute;

import java.util.EnumMap;
import java.util.Map;

public class AttributeMap {

    private final Map<Attribute, Double> values = new EnumMap<>(Attribute.class);

    public AttributeMap() {
        for (Attribute attribute : Attribute.values()) {
            values.put(attribute, attribute.getDefaultValue());
        }
    }

    public double get(Attribute attribute) {
        return values.getOrDefault(attribute, attribute.getDefaultValue());
    }

    public void set(Attribute attribute, double value) {
        values.put(attribute, value);
    }

    public void add(Attribute attribute, double amount) {
        set(attribute, get(attribute) + amount);
    }

    public Map<Attribute, Double> getValues() {
        return values;
    }
}