package org.intranet.app;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;

public abstract class App<DOC extends Document>
{
	public abstract String getTitle();
	public abstract String getDocType();
	public abstract DOC openFile(File file);

	private final File defaultDirectory = new File(getClass().getResource(
		"/org/intranet/graphics/raytrace/yml/reflect-refract.yml").getFile()).getParentFile();
	public File getDefaultDirectory() { return defaultDirectory; }

	private final List<Action> appActions = new ArrayList<>();
	public List<Action> getAppActions() { return appActions; }

	public App()
	{
		AbstractAction exitAction = new AbstractAction("Exit") {
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
