package com.jdvsn.mousedrop;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;

import javax.inject.Inject;
import javax.inject.Singleton;

@Slf4j
@Singleton
public class MouseDropMenuOverride
{
    @Inject
    private Client client;

    private final DropIndexCache dropIndexCache = new DropIndexCache();

    public void invoke(boolean mouseButtonHeld)
    {
        if (!mouseButtonHeld || client.isMenuOpen()) return;

        MenuEntry[] entries = client.getMenu().getMenuEntries();
        if (entries.length < 2) return;

        int topIdx = entries.length - 1;
        int itemId = entries[topIdx].getItemId();
        if (itemId < 1) return;

        if (itemId != dropIndexCache.itemId)
        {
            int dropIdx = findDropIdx(entries);
            if (dropIdx < 0 || dropIdx == topIdx)
            {
                return;
            }
            dropIndexCache.itemId = itemId;
            dropIndexCache.dropIdx = dropIdx;
            log.debug("Will swap dropIdx: {} with topIdx: {}", dropIdx, topIdx);
        }

        setLeftClickable(entries[dropIndexCache.dropIdx]);
        swapMenuEntries(entries, topIdx, dropIndexCache.dropIdx);
        client.getMenu().setMenuEntries(entries);
    }

    private void setLeftClickable(MenuEntry entry)
    {
        entry.setType(MenuAction.CC_OP);
    }

    private int findDropIdx(MenuEntry[] entries)
    {
        int dropIdx = -1;
        for (int i = 0; i < entries.length; i++)
        {
            log.debug("[{}] {}", i, entries[i].getOption());
            MenuEntry en = entries[i];
            if ("Drop".equals(en.getOption()))
            {
                return i;
            }
        }
        log.debug("Drop not found");
        return dropIdx;
    }

    private void swapMenuEntries(MenuEntry[] entries, int topIdx, int dropIdx)
    {
        MenuEntry tmp = entries[topIdx];
        entries[topIdx] = entries[dropIdx];
        entries[dropIdx] = tmp;
    }

    @Data
    private static class DropIndexCache
    {
        private int itemId = -1;
        private int dropIdx = -1;
    }
}
