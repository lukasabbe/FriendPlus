package com.lukasabbe.friendplus.config;

import net.minecraft.network.chat.Component;

public enum PresenceSetting {
    none,
    offline,
    online,
    playing_offline,
    playing_realms,
    playing_server,
    playing_hosted_server;

    public static Component getTranslation(PresenceSetting status){
        return Component.translatable("friendplus.friendmenu.settings.status.enum."+status.name());
    }
}
