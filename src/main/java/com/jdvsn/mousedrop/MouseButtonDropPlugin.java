package com.jdvsn.mousedrop;

import com.google.inject.Provides;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.events.PostMenuSort;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.MouseManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Mouse Button Drop",
        description = "Hold a mouse button (eg. MB4) to left-click drop items as if you were holding shift",
        tags = {"mouse", "button", "drop"},
        enabledByDefault = false
)
@Slf4j
public class MouseButtonDropPlugin extends Plugin {
    @Inject
    private MouseManager mouseManager;

    @Inject
    private MouseButtonDropMouseListener mouseListener;

    @Inject
    private ConfigManager configManager;

    @Inject
    private MouseDropMenuOverride mouseDropMenuOverride;

    @Getter
    @Setter
    private boolean mouseButtonHeld = false;

    @Override
    protected void startUp() throws Exception {
        mouseManager.registerMouseListener(mouseListener);
        configManager.setConfiguration("runelite", "blockExtraMouseButtons", false);
    }

    @Override
    protected void shutDown() throws Exception {
        mouseManager.unregisterMouseListener(mouseListener);
    }

    @Subscribe
    public void onPostMenuSort(PostMenuSort postMenuSort) {
        mouseDropMenuOverride.invoke(mouseButtonHeld);
    }

    @Provides
    MouseButtonDropConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(MouseButtonDropConfig.class);
    }
}
