package com.lukasabbe.friendplus;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.LoadingDotsWidget;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.Layout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.friends.AbstractFriendsTab;
import net.minecraft.client.gui.screens.friends.FriendsOverlayScreen;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class FriendSettingsTab extends AbstractFriendsTab {
    private final FriendsOverlayScreen screen;
    private final LinearLayout layout;
    private final LinearLayout pendingScrollableContent;
    private final ScrollableLayout scrollableLayout;

    public FriendSettingsTab(final Minecraft minecraft, final LoadingDotsWidget loadingDotsWidget, final FriendsOverlayScreen screen, final int width, final int height) {
        super(width, height);
        this.screen = screen;
        this.layout = LinearLayout.vertical();
        this.layout.defaultCellSetting().alignHorizontallyCenter();
        this.pendingScrollableContent = LinearLayout.vertical();
        this.scrollableLayout = new ScrollableLayout(minecraft, this.pendingScrollableContent, height, ScrollableLayout.ReserveStrategy.BOTH);
        this.scrollableLayout.setScrollbarSpacing(2);
        this.scrollableLayout.setMaxHeight(height);
        this.layout.addChild(this.scrollableLayout);
        this.rearrangeElements();
    }

    public void rearrangeElements() {
        this.scrollableLayout.setMinHeight(this.height);
        this.scrollableLayout.setMaxHeight(this.height);
    }

    public @NonNull Component getTabTitle() {
        return Component.translatable("friendplus.friendmenu.settings_tab.title");
    }

    public @NonNull Component getTabExtraNarration() {
        return Component.empty();
    }

    public void visitChildren(final @NonNull Consumer<AbstractWidget> childrenConsumer) {
        this.layout.visitWidgets(childrenConsumer);
    }

    public void doLayout(final @NonNull ScreenRectangle screenRectangle) {
        this.layout.arrangeElements();
        FrameLayout.alignInRectangle(this.layout, screenRectangle, 0.5F, 0.16666667F);
    }

    public @NonNull Layout getLayout() {
        return this.layout;
    }


    protected @NonNull Layout entriesContainer() {
        return this.pendingScrollableContent;
    }


    public void showContent() {
        LinearLayout content = (new LinearLayout(0, 0, LinearLayout.Orientation.VERTICAL)).spacing(8);
        content.defaultCellSetting().alignHorizontallyCenter().alignVerticallyMiddle();

        int maxWidth = this.getListContentWidth();
        content.addChild(this.createCenteredText(Component.translatable("friendplus.friendmenu.settings_tab.title"), this.screen.getFont(), maxWidth));

        content.addChild(new FriendSettingEntry(0, 0, screen.getOverlayWidth() - 16, 28, Component.literal("Title"), Component.literal("Desc")));

        this.pendingScrollableContent.addChild(content);
    }
}
