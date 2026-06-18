package com.lukasabbe.friendplus.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.social.PlayerSocialManager;
import net.minecraft.client.renderer.PlayerSkinRenderCache;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.state.gui.pip.GuiEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.component.ResolvableProfile;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PlayerScreen extends Screen
{

    public static Component BACK_TEXT = Component.translatable("friendplus.friendmenu.back.button.title");
    public static WidgetSprites BACK_BUTTON_SPRITE = new WidgetSprites(Identifier.withDefaultNamespace("friends/reject"));

    private final ResolvableProfile profile;
    private final PlayerSocialManager.PlayerData playerData;
    private MultiLineTextWidget usernameText;
    private Button backButton;

    private final Screen prevScreen;

    public PlayerScreen(Component title, PlayerSocialManager.PlayerData playerData, Screen prevScreen) {
        super(title);
        this.profile = ResolvableProfile.createUnresolved(playerData.id());
        this.playerData = playerData;
        this.prevScreen = prevScreen;
    }

    @Override
    protected void init() {
        this.usernameText = new MultiLineTextWidget(Component.literal(playerData.name()), this.font);

        int usernameX = ((this.width-usernameText.getWidth()) / 2);
        int usernameY = 25;
        usernameText.setCentered(true);
        usernameText.setPosition(usernameX, usernameY);

        int buttonWidth = 20;
        int margin = 5;

        int buttonX = this.width - buttonWidth - margin;

        this.backButton = SpriteIconButton.builder(BACK_TEXT, (btn) -> this.minecraft.gui.setScreen(this.prevScreen), true)
                .size(20, 20)
                .sprite(BACK_BUTTON_SPRITE, 18, 18)
                .tooltip(BACK_TEXT)
                .build();
        this.backButton.setPosition(buttonX, margin);

        this.addRenderableWidget(this.backButton);
        this.addRenderableWidget(this.usernameText);
    }


    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);

        int boxWidth = 100;
        int boxHeight = 180;
        int x0 = (this.width - boxWidth) / 2;
        int y0 = (this.height - boxHeight) /2;
        int x1 = x0 + boxWidth;
        int y1 = y0 + boxHeight;

        drawSkin(graphics, x0, y0, x1, y1, 80, mouseX, mouseY, profile);
    }


    public void drawSkin(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int size, int mouseX, int mouseY, ResolvableProfile profile){
        AvatarRenderState avatar = new AvatarRenderState();
        PlayerSkinRenderCache skinCache = Minecraft.getInstance().playerSkinRenderCache();
        PlayerSkinRenderCache.RenderInfo renderInfo = skinCache.getOrDefault(profile);

        avatar.skin = renderInfo.playerSkin();
        avatar.showCape = true;

        float centerX = (x0 + x1) / 2f;
        float centerY = y0 + (y1 -y0)/2f;

        float angleX = (float) Math.atan((centerX - mouseX) / 40f);
        float angleY = (float) Math.atan((centerY - mouseY) / 40f);

        Quaternionf rot = new Quaternionf();
        rot.rotationZ((float) Math.PI);
        rot.rotateY((float) Math.PI);

        rot.rotateX(-angleY * 0.5f);
        rot.rotateY(-angleX * 0.5f);

        Vector3f pos = new Vector3f(0f, 1f, 0f);

        graphics.enableScissor(x0, y0, x1, y1);
        GuiEntityRenderState guiState = new GuiEntityRenderState(avatar, pos, rot, null, x0, y0, x1, y1, (float) size, graphics.scissorStack.peek());

        graphics.guiRenderState.addPicturesInPictureState(guiState);
        graphics.disableScissor();
    }
}
