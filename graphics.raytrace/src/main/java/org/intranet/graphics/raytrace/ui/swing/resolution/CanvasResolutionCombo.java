package org.intranet.graphics.raytrace.ui.swing.resolution;

import java.awt.event.ItemEvent;
import java.util.function.Consumer;

import javax.swing.JComboBox;

public final class CanvasResolutionCombo
	extends JComboBox<Resolution>
{
	private static final long serialVersionUID = 1L;

	public CanvasResolutionCombo(Resolution defaultResolution,
		Consumer<Resolution> resConsumer)
	{
		super(Resolution.resolutions);
		addItemListener(itemEvent -> {
			if (itemEvent.getStateChange() != ItemEvent.SELECTED)
				return;
			Resolution res = (Resolution) itemEvent.getItem();
			if (res != null)
				resConsumer.accept(res);
		});
		setSelectedItem(defaultResolution);
	}
}