package com.lukasabbe.friendplus.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;

public class FriendSettingEntry extends AbstractContainerWidget {

    private final List<AbstractWidget> children;
    private final StringWidget settingTitleWidget;
    private final StringWidget settingDescriptionWidget;
    private final Button settingButton;

    public FriendSettingEntry(int x, int y, int width, int height, Component settingTitle, Component description, Component defaultButtonText, Component tooltip, Button.OnPress onAction) {
        super(x, y, width, height, Component.empty());
        this.children = new ArrayList<>();
        this.settingTitleWidget = new StringWidget(settingTitle, Minecraft.getInstance().font);
        this.settingDescriptionWidget = new StringWidget(description.copy().withStyle(ChatFormatting.GRAY), Minecraft.getInstance().font);
        this.settingButton = PlainTextButton.builder(defaultButtonText, onAction).size(80,20).build();
        this.settingTitleWidget.setTooltip(Tooltip.create(tooltip));
        this.settingDescriptionWidget.setTooltip(Tooltip.create(tooltip));
        this.children.add(this.settingTitleWidget);
        this.children.add(this.settingDescriptionWidget);
        this.children.add(this.settingButton);

    }

    @Override
    protected int contentHeight() {
        return this.height;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        this.settingTitleWidget.setPosition(this.getX(), this.getY());
        this.settingDescriptionWidget.setPosition(this.getX(), this.settingTitleWidget.getBottom() + 3);
        this.settingButton.setPosition(this.getRight() - this.settingButton.getWidth(), this.getY());
        this.settingDescriptionWidget.extractWidgetRenderState(graphics, mouseX, mouseY, a);
        this.settingButton.extractRenderState(graphics, mouseX, mouseY, a);
        this.settingTitleWidget.extractWidgetRenderState(graphics, mouseX, mouseY, a);

        this.settingTitleWidget.extractRenderState(graphics, mouseX, mouseY, a);
        this.settingDescriptionWidget.extractRenderState(graphics, mouseX, mouseY, a);

    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput output) {}

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return this.children;
    }

}
