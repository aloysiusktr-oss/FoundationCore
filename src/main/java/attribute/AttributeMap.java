package attribute;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AttributeMap {

    private final Map<Attribute, Double> baseValues = new EnumMap<>(Attribute.class);
    private final Map<Attribute, List<AttributeModifier>> modifiers = new EnumMap<>(Attribute.class);
    private final AttributeCalculator calculator = new AttributeCalculator();

    public AttributeMap() {
        for (Attribute attribute : Attribute.values()) {
            baseValues.put(attribute, attribute.getDefaultValue());
            modifiers.put(attribute, new ArrayList<>());
        }
    }

    public double getBase(Attribute attribute) {
        return baseValues.getOrDefault(attribute, attribute.getDefaultValue());
    }

    public double get(Attribute attribute) {
        return calculator.calculate(
                getBase(attribute),
                modifiers.getOrDefault(attribute, List.of())
        );
    }

    public void set(Attribute attribute, double value) {
        baseValues.put(attribute, value);
    }

    public void add(Attribute attribute, double amount) {
        set(attribute, getBase(attribute) + amount);
    }

    public void addModifier(AttributeModifier modifier) {
        modifiers
                .computeIfAbsent(modifier.getAttribute(), key -> new ArrayList<>())
                .add(modifier);
    }

    public void clearModifiers(String source) {
        for (List<AttributeModifier> list : modifiers.values()) {
            list.removeIf(modifier -> modifier.getSource().equalsIgnoreCase(source));
        }
    }

    public Map<Attribute, Double> getValues() {
        return baseValues;
    }
}