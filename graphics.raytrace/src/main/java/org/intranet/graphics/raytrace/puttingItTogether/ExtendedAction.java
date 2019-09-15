package org.intranet.graphics.raytrace.puttingItTogether;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

public abstract class ExtendedAction
	extends AbstractAction
{
	private static final long serialVersionUID = 1L;

	public ExtendedAction(String name)
	{
		super(name);
	}

	public ExtendedAction(String name, char accelerator)
	{
		super(name);
		setMnemonicKey(accelerator);
	}

	public final void setName(String name) { putValue(NAME, name); }
	public final String getName() { return (String)getValue(NAME); }

	public final void setAcceleratorKey(char key)
	{ putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(key)); }

	public final void setShortDescription(String shortDescription)
	{ putValue(SHORT_DESCRIPTION, shortDescription); }
	public final String getShortDescription() { return (String)getValue(SHORT_DESCRIPTION); }

	public final void setLongDescription(String shortDescription)
	{ putValue(LONG_DESCRIPTION, shortDescription); }
	public final String getLongDescription() { return (String)getValue(LONG_DESCRIPTION); }

	public final void setMnemonicKey(char c) { putValue(MNEMONIC_KEY, (int)c); }

	public final void setDisplayedMnemonicKey(char c)
	{ putValue(DISPLAYED_MNEMONIC_INDEX_KEY, getName().indexOf(c)); }

	public final void setActionCommandKey(char c) { putValue(ACTION_COMMAND_KEY, c); }
//		public final char getActionCommandKey() { (Character)getValue(ACTION_COMMAND_KEY); }

//		DEFAULT;
//		LARGE_ICON_KEY;
//		SELECTED_KEY;
//		SMALL_ICON;
}