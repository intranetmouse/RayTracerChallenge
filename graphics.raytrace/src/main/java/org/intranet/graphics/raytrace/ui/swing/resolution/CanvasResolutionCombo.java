package org.intranet.graphics.raytrace.ui.swing.resolution;

import java.awt.event.ItemEvent;
import java.util.function.Consumer;

import javax.swing.JComboBox;

public final class CanvasResolutionCombo
	extends JComboBox<Resolution>
{
	private static final long serialVersionUID = 1L;

	public CanvasResolutionCombo(Resolution defaultResolution,
		Consumer<Resolution> updateResolutionAction)
	{
		super(Resolution.resolutions);
		// Force not null
		updateResolutionAction.hashCode();

		setSelectedItem(defaultResolution);

		addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			Resolution res = (Resolution)itemEvent.getItem();
			if (res != null)
				updateResolutionAction.accept(res);
		});
	}
}