package org.intranet.app;

import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

final class ExitAction
	extends ExtendedAbstractAction
{
	private static final long serialVersionUID = 1L;
	public static final ImageIcon APP_EXIT_ICON = IconUtils.image("application-exit.png");

	ExitAction()
	{
		super("Exit", 'x', APP_EXIT_ICON);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0);
	}
}