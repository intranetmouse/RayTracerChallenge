package org.intranet.app;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

public abstract class App<DOC extends Document>
{
	public abstract String getTitle();
	public abstract String getDocType();
	public abstract char getDocTypeMnemonic();
	public abstract DOC openFile(File file);

	private final File defaultDirectory = new File(getClass().getResource(
		"/org/intranet/graphics/raytrace/yml/reflect-refract.yml").getFile()).getParentFile();
	public File getDefaultDirectory() { return defaultDirectory; }

	private final List<Action> appActions = new ArrayList<>();
	public List<Action> getAppActions() { return appActions; }

	public static final class IconUtils
	{
		public static final ImageIcon image(String name)
		{
			return new ImageIcon(IconUtils.class.getResource(name));
		}
	}

	public static final ImageIcon APP_EXIT_ICON = IconUtils.image("application-exit.png");

	public App()
	{
		AbstractAction exitAction = new ExtendedAbstractAction("Exit", 'x', APP_EXIT_ICON) {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		};
		appActions.add(exitAction);
	}
}
