package org.intranet.graphics.raytrace.puttingItTogether.toolbar;

import java.awt.event.ItemEvent;
import java.util.function.Consumer;

import javax.swing.JComboBox;

import org.intranet.graphics.raytrace.ui.swing.canvas.RepaintMode;

public final class RepaintModeCombo
	extends JComboBox<RepaintMode>
{
	private static final long serialVersionUID = 1L;

	RepaintModeCombo(RepaintMode defaultMode,
		Consumer<RepaintMode> repaintModeSelected)
	{
		super(RepaintMode.values());
		addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			RepaintMode mode = (RepaintMode)itemEvent.getItem();
			if (mode != null)
				repaintModeSelected.accept(mode);
		});
		setSelectedItem(defaultMode);
	}
}