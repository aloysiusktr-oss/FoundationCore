package bootstrap;

import attribute.Attribute;
import item.FoundationItem;
import item.ItemBuilder;
import item.ItemRarity;
import item.ItemRegistry;
import item.ItemType;

public class ItemBootstrap {

    private final ItemRegistry itemRegistry;

    public ItemBootstrap(ItemRegistry itemRegistry) {
        this.itemRegistry = itemRegistry;
    }

    public void register() {
        registerTrainingSword();
    }

    private void registerTrainingSword() {
        FoundationItem trainingSword = ItemBuilder.create("test_sword")
                .name("Training Sword")
                .type(ItemType.WEAPON)
                .rarity(ItemRarity.COMMON)
                .attribute(Attribute.STRENGTH, 50)
                .attribute(Attribute.CRIT_DAMAGE, 10)
                .build();

        itemRegistry.register(trainingSword);
    }
}
