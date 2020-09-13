package org.intranet.app;

import javax.swing.ImageIcon;

public final class IconUtils
{
	public static final ImageIcon image(String name)
	{
		return new ImageIcon(IconUtils.class.getResource(name));
	}
}