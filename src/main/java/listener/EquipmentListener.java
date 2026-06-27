package listener;

import attribute.Attribute;
import attribute.AttributeModifier;
import attribute.ModifierOperation;
import item.FoundationItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import profile.PlayerProfile;
import service.ItemService;
import service.PlayerProfileService;

import java.util.Map;

public class EquipmentListener implements Listener {

    private static final String HELD_ITEM_SOURCE = "held_item";

    private final ItemService itemService;
    private final PlayerProfileService profileService;

    public EquipmentListener(ItemService itemService, PlayerProfileService profileService) {
        this.itemService = itemService;
        this.profileService = profileService;
    }

    @EventHandler
    public void onHeldItemChange(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerProfile profile = profileService.get(player);

        if (profile == null) {
            return;
        }

        profile.getAttributes().clearModifiers(HELD_ITEM_SOURCE);

        FoundationItem item = itemService.getFoundationItem(
                player.getInventory().getItem(event.getNewSlot())
        );

        if (item == null) {
            return;
        }

        for (Map.Entry<Attribute, Double> entry : item.getAttributes().entrySet()) {
            profile.getAttributes().addModifier(new AttributeModifier(
                    HELD_ITEM_SOURCE,
                    entry.getKey(),
                    ModifierOperation.ADD_FLAT,
                    entry.getValue()
            ));
        }
    }
}