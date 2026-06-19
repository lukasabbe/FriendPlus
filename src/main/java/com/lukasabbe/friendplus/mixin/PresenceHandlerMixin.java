package com.lukasabbe.friendplus.mixin;

import com.lukasabbe.friendplus.config.Config;
import com.lukasabbe.friendplus.config.ConfigManager;
import com.lukasabbe.friendplus.config.PresenceSetting;
import com.mojang.authlib.yggdrasil.response.PresenceStatus;
import net.minecraft.client.gui.screens.social.PresenceHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PresenceHandler.class)
public class PresenceHandlerMixin {
    @Inject(method = "getPresenceStatus", at=@At("HEAD"), cancellable = true)
    public void addFakeStatus(CallbackInfoReturnable<PresenceStatus> cir){
        Config config = ConfigManager.config;
        switch (config.status){
            case PresenceSetting.offline -> cir.setReturnValue(PresenceStatus.OFFLINE);
            case online -> cir.setReturnValue(PresenceStatus.ONLINE);
            case playing_realms -> cir.setReturnValue(PresenceStatus.PLAYING_REALMS);
            case playing_server -> cir.setReturnValue(PresenceStatus.PLAYING_SERVER);
            case playing_offline -> cir.setReturnValue(PresenceStatus.PLAYING_OFFLINE);
            case playing_hosted_server -> cir.setReturnValue(PresenceStatus.PLAYING_HOSTED_SERVER);
            case null, default -> {}
        }

    }
}
