package org.intranet.app;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.Action;

public abstract class App<DOC extends Document>
{
	public abstract String getTitle();
	public abstract String getDocType();
	public abstract char getDocTypeMnemonic();
	public abstract DOC openFile(File file);

	private final File defaultDirectory = new File(getClass().getResource(
		"/org/intranet/graphics/raytrace/yml/reflect-refract.yml").getFile()).getParentFile();
	public File getDefaultDirectory() { return defaultDirectory; }

	private final List<Action> appActions = Arrays.asList(new ExitAction());
	public List<Action> getAppActions() { return appActions; }

	public App()
	{
	}
}
