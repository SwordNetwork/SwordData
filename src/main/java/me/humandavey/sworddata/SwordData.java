package me.humandavey.sworddata;

import me.humandavey.sworddata.mysql.MySQLHelper;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;

public final class SwordData extends Plugin {

    private static SwordData instance;
    private Configuration config;
    private MySQLHelper helper;

    @Override
    public void onEnable() {
        instance = this;

        setupConfig();

        try {
            helper = new MySQLHelper(
                    config.getString("database.host"),
                    config.getInt("database.port"),
                    config.getString("database.database"),
                    config.getString("database.user"),
                    config.getString("database.password")
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void setupConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        File file = new File(getDataFolder(), "config.yml");

        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());

                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
            try {
                config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public static SwordData getInstance() {
        return instance;
    }
}
