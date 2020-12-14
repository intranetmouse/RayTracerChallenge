package org.intranet.graphics.raytrace.ui.swing.resolution;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Consumer;

import javax.swing.JComboBox;

public final class CanvasResolutionCombo
	extends JComboBox<Resolution>
{
	private static final long serialVersionUID = 1L;

	public CanvasResolutionCombo(Resolution[] resolutions,
		Resolution defaultResolution,
		Consumer<Resolution> updateResolutionAction,
		ItemListener ...itemListeners)
	{
		super(resolutions);
		// Force not null
		if (updateResolutionAction == null)
			throw new IllegalArgumentException("updateResolutionAction");

		setSelectedItem(defaultResolution);

		addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			Resolution res = (Resolution)itemEvent.getItem();
			if (res != null)
				updateResolutionAction.accept(res);
			for (ItemListener itemListener : itemListeners)
				itemListener.itemStateChanged(itemEvent);
		});
	}
}