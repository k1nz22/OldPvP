package com.k1nz.oldpvp.Listeners;

import com.k1nz.oldpvp.OldPvP;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OldAttackSpeedListener implements Listener {
    private final OldPvP plugin;
    private boolean pvpEnabled;
    private double attackSpeed;

    public OldAttackSpeedListener(OldPvP plugin) {
        this.plugin = plugin;
        reloadConfig();
    }

    private void reloadConfig() {
        plugin.reloadConfig();
        pvpEnabled = plugin.getConfig().getBoolean("pvp.enabled", true);
        attackSpeed = plugin.getConfig().getDouble("pvp.attack_speed", 16);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (pvpEnabled) {
            setAttackSpeed(player);
        } else {
            resetAttackSpeed(player);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!pvpEnabled) return;
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            if (player.getAttackCooldown() < 1.0) {
                event.setCancelled(true);
                event.setDamage(0);
                player.resetCooldown();
            }
        }
    }

    private void setAttackSpeed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            // Ustawienie wartości atrybutu ataku na wartość attackSpeed
            attribute.setBaseValue(attackSpeed);
        }
    }

    private void resetAttackSpeed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        if (attribute != null) {
            // Ustawienie wartości atrybutu ataku na domyślną wartość
            attribute.setBaseValue(4.0); // Domyślna wartość ataku w Minecraft
        }
    }
}
