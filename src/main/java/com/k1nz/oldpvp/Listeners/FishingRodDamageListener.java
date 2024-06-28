package com.k1nz.oldpvp.Listeners;

import com.k1nz.oldpvp.OldPvP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FishingRodDamageListener implements Listener {
    private final OldPvP plugin;
    private final Map<UUID, Long> lastClickTime;

    public FishingRodDamageListener(OldPvP plugin) {
        this.plugin = plugin;
        this.lastClickTime = new HashMap<>();
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        if (event.getState() == State.CAUGHT_ENTITY && event.getCaught() instanceof Player) {
            Player caughtPlayer = (Player) event.getCaught();
            Player fishingPlayer = event.getPlayer();
            caughtPlayer.damage(plugin.getFishingRodDamage(), fishingPlayer);

            ItemStack fishingRod = fishingPlayer.getInventory().getItemInMainHand();
            ItemStack originalFishingRod = fishingRod.clone();

            plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                fishingPlayer.getInventory().setItemInMainHand(null);

                plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
                    fishingPlayer.getInventory().setItemInMainHand(originalFishingRod);
                }, 1);
            }, 1);

            event.setCancelled(true); // Anulowanie przyciągania
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().toString().contains("RIGHT")) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && isFishingRod(item)) {
                UUID playerId = player.getUniqueId();
                long currentTime = System.currentTimeMillis();

                if (lastClickTime.containsKey(playerId) && currentTime - lastClickTime.get(playerId) <= 500) {
                    player.getInventory().setItemInMainHand(null);

                    lastClickTime.remove(playerId);
                } else {
                    lastClickTime.put(playerId, currentTime);
                }
            }
        }
    }

    private boolean isFishingRod(ItemStack item) {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName() && item.getItemMeta().getDisplayName().equals("Fishing Rod");
    }
}
