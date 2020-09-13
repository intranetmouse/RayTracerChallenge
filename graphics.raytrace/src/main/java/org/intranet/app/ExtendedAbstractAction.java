package org.intranet.app;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.KeyStroke;

public abstract class ExtendedAbstractAction
	extends AbstractAction
{
	private static final long serialVersionUID = 1L;

	public ExtendedAbstractAction(String name, char mnenomic, Icon icon)
	{
		super(name, icon);
		setMnemonicKey(mnenomic);
	}

	public ExtendedAbstractAction(String name, int mnenomicIndex, Icon icon)
	{
		super(name, icon);
		setDisplayedMnemonicIndexKey(mnenomicIndex);
	}

	public ExtendedAbstractAction(String name)
	{
		super(name);
	}

	public ExtendedAbstractAction(String name, char nmemonic)
	{
		super(name);
		setMnemonicKey(nmemonic);
	}

	public ExtendedAbstractAction(String name, int nmenomicIndex)
	{
		super(name);
		setDisplayedMnemonicIndexKey(nmenomicIndex);
	}

	public ExtendedAbstractAction(Icon icon)
	{
		super(null, icon);
	}

	public ExtendedAbstractAction()
	{
		super();
	}

	public String getLongDescription() { return (String)getValue(LONG_DESCRIPTION); }
	public void setLongDescription(String value) { putValue(LONG_DESCRIPTION, value); }

	public String getShortDescription() { return (String)getValue(SHORT_DESCRIPTION); }
	public void setShortDescription(String value) { putValue(SHORT_DESCRIPTION, value); }

	public Icon getSmallIcon() { return (Icon)getValue(SMALL_ICON); }
	public void setSmallIcon(Icon value) { putValue(SMALL_ICON, value); }

	public Icon getLargelIcon() { return (Icon)getValue(LARGE_ICON_KEY); }
	public void setLargeIcon(Icon value) { putValue(LARGE_ICON_KEY, value); }

	public KeyStroke getAcceleratorKey() { return (KeyStroke)getValue(ACCELERATOR_KEY); }
	/**
	* @param value Suggestion: {@code KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)}
	*/
	public void setAcceleratorKey(KeyStroke value) { putValue(ACCELERATOR_KEY, value); }

	public int getDisplayedMnemonicIndexKey() { return (Integer)getValue(DISPLAYED_MNEMONIC_INDEX_KEY); }
	public void setDisplayedMnemonicIndexKey(int value) { putValue(DISPLAYED_MNEMONIC_INDEX_KEY, value); }

	public int getMnemonicKey() { return (Integer)getValue(MNEMONIC_KEY); }
	/**
	* @param value Suggestion: {@code KeyEvent.VK_F}
	*/
	public void setMnemonicKey(int value) { putValue(MNEMONIC_KEY, value); }

	public String getActionCommand() { return (String)getValue(ACTION_COMMAND_KEY); }
	public void setActionCommand(String value) { putValue(ACTION_COMMAND_KEY, value); }

	public String getName() { return (String)getValue(NAME); }
	public void setName(String value) { putValue(NAME, value); }

	public Boolean getSelectedKey() { return (Boolean)getValue(SELECTED_KEY); }
	public void setSelectedKey(Boolean value) { putValue(SELECTED_KEY, value); }
}
