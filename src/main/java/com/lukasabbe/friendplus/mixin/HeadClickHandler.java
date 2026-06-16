package com.lukasabbe.friendplus.mixin;

import com.lukasabbe.friendplus.Friendplus;
import com.lukasabbe.friendplus.PlayerScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.screens.friends.FriendsOverlayScreen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.ResolvableProfile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/client/gui/screens/friends/AbstractFriendsEntryContainerWidget")
public abstract class HeadClickHandler extends AbstractContainerWidget {

    public HeadClickHandler(int x, int y, int width, int height, Component message) {super(x, y, width, height, message);}

    @Shadow
    protected abstract void addChild(AbstractWidget child);

    @Unique
    private SpriteIconButton showSkinButton = null;

    @Unique
    private static final Component SHOW_SKIN = Component.translatable("friendplus.friendmenu.show_skin.button.title");

    @Unique
    private static final WidgetSprites SHOW_SKIN_SPRITE = new WidgetSprites(Identifier.fromNamespaceAndPath("friendplus", "show_skin_icon"));

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/client/gui/screens/friends/FriendsOverlayScreen;IIIILnet/minecraft/client/gui/screens/social/PlayerSocialManager$PlayerData;Z)V", at=@At("RETURN"))
    public void addButton(Minecraft minecraft, FriendsOverlayScreen screen, int x, int y, int width, int height, PlayerSocialManager.PlayerData playerData, boolean showingStatus, CallbackInfo ci){
        this.showSkinButton = SpriteIconButton.builder(SHOW_SKIN, (btn) -> Friendplus.openMenu(screen, playerData), true)
                .size(20, 20)
                .sprite(SHOW_SKIN_SPRITE, 13, 13)
                .tooltip(SHOW_SKIN)
                .build();

        this.addChild(showSkinButton);
    }

    @Inject(method = "extractWidgetRenderState", at=@At("RETURN"))
    public void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, CallbackInfo ci){
        int center = this.getY() + (this.getHeight() - 20) / 2;
        int showSkinX = this.getX() + this.getWidth() - 42;
        this.showSkinButton.setPosition(showSkinX, center);
        this.showSkinButton.extractRenderState(graphics, mouseX, mouseY, a);
    }
}
