package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunctionalAction
	extends ExtendedAction
{
	private static final long serialVersionUID = 1L;
	private final ActionListener l;

	public FunctionalAction(String name, ActionListener l)
	{
		super(name);
		this.l = l;
	}

	public FunctionalAction(String name, char accelerator, ActionListener l)
	{
		super(name, accelerator);
		this.l = l;
	}

	@Override
	public void actionPerformed(ActionEvent e) { l.actionPerformed(e); }
}