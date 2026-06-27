package item;

import attribute.Attribute;

import java.util.EnumMap;
import java.util.Map;

import registry.Identifiable;

public class FoundationItem implements Identifiable {

    private final String id;

    private final String displayName;

    private final ItemType type;

    private final ItemRarity rarity;

    private final Map<Attribute, Double> attributes =
            new EnumMap<>(Attribute.class);

    public FoundationItem(
            String id,
            String displayName,
            ItemType type,
            ItemRarity rarity
    ) {
        this.id = id;
        this.displayName = displayName;
        this.type = type;
        this.rarity = rarity;
    }

    public FoundationItem addAttribute(Attribute attribute, double amount) {
        attributes.put(attribute, amount);
        return this;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public ItemType getType() {
        return type;
    }

    public ItemRarity getRarity() {
        return rarity;
    }

    public Map<Attribute, Double> getAttributes() {
        return attributes;
    }
}