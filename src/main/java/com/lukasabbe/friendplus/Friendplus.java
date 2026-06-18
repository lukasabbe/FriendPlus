package com.lukasabbe.friendplus;

import com.lukasabbe.friendplus.config.ConfigManager;
import com.lukasabbe.friendplus.gui.PlayerScreen;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.friends.FriendsOverlayScreen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.network.chat.Component;

public class Friendplus implements ModInitializer {

    private static PlayerScreen playerScreen;

    @Override
    public void onInitialize() {
        ConfigManager.loadConfig();
    }

    public static void openMenu(FriendsOverlayScreen screen, PlayerSocialManager.PlayerData playerdata){
        System.out.println("Running");
        playerScreen = new PlayerScreen(Component.literal("test"), playerdata, screen);
        Minecraft.getInstance().gui.setScreen(playerScreen);
    }
}
