package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

public abstract class AppWindow<DOC extends Document>
	extends JFrame
{
	private static final long serialVersionUID = 1L;
	private final JFileChooser fc = new JFileChooser();

	private DocumentView<DOC> view;

	public AppWindow(App<DOC> app)
	{
		super(app.getTitle());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu appMenu = new JMenu("App");
		for (Action appAction : app.getAppActions())
			appMenu.add(appAction);
		menuBar.add(appMenu);

		fc.setCurrentDirectory(app.getDefaultDirectory());

		view = createView();
		add(view, BorderLayout.CENTER);

		JMenu sceneMenu = new JMenu(app.getDocType());
		Action openSceneAction = new AbstractAction("Open ...") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (fc.showOpenDialog(sceneMenu) == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();

					DOC doc = app.openFile(file);

					view.setDocument(doc);

					setTitle(app.getTitle() + " - " + file.getName());
				}
			}
		};
		sceneMenu.add(openSceneAction);
		menuBar.add(sceneMenu);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(toolBar, BorderLayout.NORTH);
		for (JComponent comp : view.getToolbarItems())
			toolBar.add(comp);

		pack();
	}

	protected abstract DocumentView<DOC> createView();
}
