package com.k1nz.oldpvp;

import com.k1nz.oldpvp.Listeners.EggDamageListener;
import com.k1nz.oldpvp.Listeners.FishingRodDamageListener;
import com.k1nz.oldpvp.Listeners.SnowballDamageListener;
import com.k1nz.oldpvp.Listeners.OldAttackSpeedListener;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class OldPvP extends JavaPlugin {

    private double snowballDamage;
    private double eggDamage;
    private double fishingRodDamage;
    private boolean snowballEnabled;
    private boolean eggEnabled;
    private boolean fishingRodEnabled;
    private boolean pvpEnabled;
    private double attackSpeed;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfiguration();

        if (snowballEnabled) {
            getServer().getPluginManager().registerEvents(new SnowballDamageListener(this), this);
        }
        if (eggEnabled) {
            getServer().getPluginManager().registerEvents(new EggDamageListener(this), this);
        }
        if (fishingRodEnabled) {
            getServer().getPluginManager().registerEvents(new FishingRodDamageListener(this), this);
        }

        if (pvpEnabled) {
            getServer().getPluginManager().registerEvents(new OldAttackSpeedListener(this), this);
        }

        getLogger().info("Plugin OldPvP włączony!");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin OldPvP wyłączony!");
    }

    private void loadConfiguration() {
        FileConfiguration config = getConfig();
        snowballDamage = config.getDouble("damage.snowball");
        eggDamage = config.getDouble("damage.egg");
        fishingRodDamage = config.getDouble("damage.fishing_rod");
        snowballEnabled = config.getBoolean("enabled.snowball");
        eggEnabled = config.getBoolean("enabled.egg");
        fishingRodEnabled = config.getBoolean("enabled.fishing_rod");

        pvpEnabled = config.getBoolean("pvp.enabled", true);
        attackSpeed = config.getDouble("pvp.attack_speed", 16);
    }

    public double getSnowballDamage() {
        return snowballDamage;
    }

    public double getEggDamage() {
        return eggDamage;
    }

    public double getFishingRodDamage() {
        return fishingRodDamage;
    }

    public boolean isPvpEnabled() {
        return pvpEnabled;
    }

    public double getAttackSpeed() {
        return attackSpeed;
    }
}
