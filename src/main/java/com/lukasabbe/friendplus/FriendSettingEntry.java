package com.lukasabbe.friendplus;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractContainerWidget;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.StringWidget;
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

    public FriendSettingEntry(int x, int y, int width, int height, Component settingTitle, Component description) {
        super(x, y, width, height, Component.empty());
        this.children = new ArrayList<>();
        this.settingTitleWidget = new StringWidget(settingTitle, Minecraft.getInstance().font);
        this.settingDescriptionWidget = new StringWidget(description.copy().withStyle(ChatFormatting.GRAY), Minecraft.getInstance().font);
        this.children.add(this.settingTitleWidget);
        this.children.add(this.settingDescriptionWidget);
    }

    @Override
    protected int contentHeight() {
        return this.height;
    }

    @Override
    protected void extractWidgetRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        this.settingTitleWidget.setPosition(this.getX(), this.getY());
        this.settingTitleWidget.extractWidgetRenderState(graphics, mouseX, mouseY, a);

        this.settingDescriptionWidget.setPosition(this.getX(), this.settingTitleWidget.getBottom() + 3);
        this.settingDescriptionWidget.extractWidgetRenderState(graphics, mouseX, mouseY, a);

    }

    @Override
    protected void updateWidgetNarration(@NonNull NarrationElementOutput output) {}

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return this.children;
    }

}
