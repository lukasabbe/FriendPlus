package com.lukasabbe.friendplus.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "friendplus.json");
    public static final Logger LOGGER = LoggerFactory.getLogger("friendplus");

    public static Config config = new Config();

    public static void loadConfig(){
        if(!CONFIG_FILE.exists()) {
            saveConfig();
            return;
        }
        try(FileReader reader = new FileReader(CONFIG_FILE)){
            config = GSON.fromJson(reader, Config.class);
        } catch (IOException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }

    public static void saveConfig(){
        try (FileWriter writer = new FileWriter(CONFIG_FILE)){
            GSON.toJson(config, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
