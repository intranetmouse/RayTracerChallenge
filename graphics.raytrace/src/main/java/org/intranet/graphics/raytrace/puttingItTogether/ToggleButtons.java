package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.FlowLayout;
import java.awt.Insets;

import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ToggleButtons
	extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final ToggleButtonGroup tbg = new ToggleButtonGroup();
	public ToggleButtons()
	{
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
	}

	public void addAction(Action toggleAction, boolean selected)
	{
		JToggleButton toggleButton = new JToggleButton(toggleAction);
		toggleButton.setMargin(new Insets(0, 0, 0, 0));
		tbg.add(toggleButton);
		add(toggleButton);
		tbg.setSelected(toggleButton.getModel(), selected);
	}
}