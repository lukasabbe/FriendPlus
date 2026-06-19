package com.lukasabbe.friendplus.mixin;

import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.lukasabbe.friendplus.gui.FriendSettingsTab;
import net.minecraft.client.gui.components.LoadingDotsWidget;
import net.minecraft.client.gui.components.tabs.TabManager;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.friends.FriendsOverlayScreen;
import net.minecraft.client.gui.screens.friends.FriendsOverlayTabButton;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FriendsOverlayScreen.class)
public class FriendOverlayScreenMixin extends Screen {
    @Shadow
    @Final
    private TabManager tabManager;

    @Shadow
    @Final
    private static Component LOADING_FRIENDS;
    @Unique
    private FriendSettingsTab friendSettingsTab = null;

    protected FriendOverlayScreenMixin(Component title) {super(title);}

    @Inject(method = "init", at=@At("HEAD"))
    public void addFriendSettingTab(CallbackInfo ci){
        friendSettingsTab = new FriendSettingsTab(this.minecraft, new LoadingDotsWidget(this.font, LOADING_FRIENDS), (FriendsOverlayScreen)((Object)this), 220, this.height - 80);
    }

    @Inject(method = "repositionElements", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/friends/FriendsTab;setHeight(I)V"))
    public void setHeight(CallbackInfo ci){
        friendSettingsTab.setHeight(this.height - 80);
    }

    @ModifyReceiver(method = "init", at= @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/tabs/TabNavigationBar$Builder;build()Lnet/minecraft/client/gui/components/tabs/TabNavigationBar;"))
    public TabNavigationBar.Builder addTab(TabNavigationBar.Builder instance){
        return instance.addTab(new FriendsOverlayTabButton(this.tabManager, this.friendSettingsTab, (110*2)/3, 20), this.friendSettingsTab);
    }

    @ModifyConstant(method = "init", constant = @Constant(intValue = 110))
    public int changeButtonWidth(int constant){
        return (110*2)/3;
    }

    @Inject(method = "populateLists", at=@At("RETURN"))
    public void addEmpty(PlayerSocialManager playerSocialManager, CallbackInfo ci){
        this.friendSettingsTab.showContent();
    }
}
