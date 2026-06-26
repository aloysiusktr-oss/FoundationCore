package service;

import profile.PlayerProfile;
import org.bukkit.entity.Player;

public class EconomyService implements FoundationService {

    private final PlayerProfileService profileService;

    public EconomyService(PlayerProfileService profileService) {
        this.profileService = profileService;
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    public int getCoins(Player player) {
        PlayerProfile profile = profileService.get(player);
        return profile == null ? 0 : profile.getCoins();
    }

    public void addCoins(Player player, int amount) {
        PlayerProfile profile = profileService.get(player);

        if (profile != null) {
            profile.addCoins(amount);
        }
    }

    public boolean removeCoins(Player player, int amount) {
        PlayerProfile profile = profileService.get(player);

        if (profile == null || profile.getCoins() < amount) {
            return false;
        }

        profile.removeCoins(amount);
        return true;
    }
}