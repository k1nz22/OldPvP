package com.k1nz.oldpvp.Listeners;

import com.k1nz.oldpvp.OldPvP;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;

public class EggDamageListener implements Listener {
    private final OldPvP plugin;

    public EggDamageListener(OldPvP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile projectile = event.getEntity();
        if (projectile instanceof org.bukkit.entity.Egg) {
            ProjectileSource source = projectile.getShooter();
            Entity hitEntity = event.getHitEntity();

            if (source instanceof Player && hitEntity instanceof Player) {
                Player shooter = (Player) source;
                Player hitPlayer = (Player) hitEntity;
                hitPlayer.damage(plugin.getEggDamage(), shooter);
            }
        }
    }
}
