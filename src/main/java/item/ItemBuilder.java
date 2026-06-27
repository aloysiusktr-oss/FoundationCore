package item;

import attribute.Attribute;

public class ItemBuilder {

    private final String id;
    private String displayName;
    private ItemType type = ItemType.MISC;
    private ItemRarity rarity = ItemRarity.COMMON;
    private final FoundationItem item;

    private ItemBuilder(String id) {
        this.id = id;
        this.displayName = id;
        this.item = new FoundationItem(id, displayName, type, rarity);
    }

    public static ItemBuilder create(String id) {
        return new ItemBuilder(id);
    }

    public ItemBuilder name(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public ItemBuilder type(ItemType type) {
        this.type = type;
        return this;
    }

    public ItemBuilder rarity(ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    public ItemBuilder attribute(Attribute attribute, double amount) {
        item.addAttribute(attribute, amount);
        return this;
    }

    public FoundationItem build() {
        FoundationItem builtItem = new FoundationItem(id, displayName, type, rarity);

        for (var entry : item.getAttributes().entrySet()) {
            builtItem.addAttribute(entry.getKey(), entry.getValue());
        }

        return builtItem;
    }
}