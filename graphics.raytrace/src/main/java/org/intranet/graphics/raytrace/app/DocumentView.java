package org.intranet.graphics.raytrace.app;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;

public abstract class DocumentView<DOC extends Document>
	extends JPanel
{
	private static final long serialVersionUID = 1L;

	public DocumentView()
	{
	}

	public abstract void setDocument(DOC doc);

	public abstract List<JComponent> getToolbarItems();
}