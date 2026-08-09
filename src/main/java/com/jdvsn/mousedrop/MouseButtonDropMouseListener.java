package com.jdvsn.mousedrop;

import lombok.extern.slf4j.Slf4j;
import net.runelite.client.input.MouseAdapter;

import javax.inject.Inject;
import java.awt.event.MouseEvent;

@Slf4j
public class MouseButtonDropMouseListener extends MouseAdapter
{

	@Inject
	private MouseButtonDropPlugin plugin;

	@Inject
	private MouseButtonDropConfig config;

	@Override
	public MouseEvent mousePressed(MouseEvent e)
	{
		if (e.getButton() == config.mouseButtonNumber())
		{
			plugin.setMouseButtonHeld(true);
			e.consume();
		}
		return e;
	}

	@Override
	public MouseEvent mouseReleased(MouseEvent e)
	{
		if (e.getButton() == config.mouseButtonNumber())
		{
			plugin.setMouseButtonHeld(false);
			e.consume();
		}
		return e;
	}

}
