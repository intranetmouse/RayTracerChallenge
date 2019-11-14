package org.intranet.graphics.raytrace.ui.swing.traversalType;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;

public final class ToggleButtonGroup
	extends ButtonGroup
{
	private static final long serialVersionUID = 1L;

	private ButtonModel prevModel;
	private boolean isAdjusting;

	@Override
	public void setSelected(ButtonModel m, boolean b)
	{
		if (isAdjusting)
			return;
		if (m.equals(prevModel))
		{
			isAdjusting = true;
			clearSelection();
			isAdjusting = false;
		}
		else
			super.setSelected(m, b);
		prevModel = getSelection();
	}
}