package org.intranet.graphics.raytrace.puttingItTogether.toolbar;

import java.awt.event.ActionEvent;
import java.util.function.Consumer;

import javax.swing.AbstractAction;

import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;

public final class TraversalTypeSelection
	extends ToggleButtons
{
	private static final long serialVersionUID = 1L;

	TraversalTypeSelection(CanvasTraversalType defaultTraversalType,
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
			addAction(toggleAction, traversalType == defaultTraversalType);
		}
	}
}