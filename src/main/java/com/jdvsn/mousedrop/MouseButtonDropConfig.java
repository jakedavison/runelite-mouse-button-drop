package com.jdvsn.mousedrop;

import net.runelite.client.config.*;

@ConfigGroup("mousebuttondrop")
public interface MouseButtonDropConfig extends Config
{

	@ConfigSection(
			name = "Mouse Button Drop",
			description = "Settings for mouse button dropping",
			position = 1
	)
	String mouseButtonSubject = "mousebuttondrop";

	@Range(
			min = 4,
			max = 9
	)
	@ConfigItem(
			position = 1,
			keyName = "mouseButtonNumber",
			name = "Mouse button number",
			description = "The mouse button number to hold for left-click dropping (between 4 and 9)",
			section = mouseButtonSubject
	)
	default int mouseButtonNumber()
	{
		return 4;
	}
}
