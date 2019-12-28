package org.intranet.graphics.raytrace.ui.swing.traversalType;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Action;

import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;

public final class TraversalTypeSelection
	extends ToggleButtons
{
	private static final long serialVersionUID = 1L;
	private final Map<CanvasTraversalType, Action> traversalToActionMap =
		new HashMap<>();

	public TraversalTypeSelection(CanvasTraversalType defaultTraversalType,
		Consumer<CanvasTraversalType> traversalTypeChanged)
	{
		CanvasTraversalType[] canvasTraversalTypes = CanvasTraversalType.values();
		for (CanvasTraversalType traversalType : canvasTraversalTypes)
		{
			AbstractAction toggleAction = new AbstractAction("", traversalType.getIcon()) {
				private static final long serialVersionUID = 1L;

				@Override
				public void actionPerformed(ActionEvent e)
				{
					traversalTypeChanged.accept(traversalType);
				}
			};
			traversalToActionMap.put(traversalType, toggleAction);
			addAction(toggleAction, traversalType == defaultTraversalType);
		}
	}

	public void setTraversalType(CanvasTraversalType value)
	{
		selectAction(traversalToActionMap.get(value));
	}
}