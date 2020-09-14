package org.intranet.app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;

public abstract class AppWindow<DOC extends Document>
	extends JFrame
{
	private final class ReloadDocumentAction
		extends ExtendedAbstractAction
	{
		private final App<DOC> app;
		private static final long serialVersionUID = 1L;

		private ReloadDocumentAction(App<DOC> app)
		{
			super("Reload", 'r', IconUtils.image("view-refresh.png"));
			this.app = app;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			File file = fc.getSelectedFile();
			if (file.exists())
			{
				DOC doc = app.openFile(file);

				view.setDocument(doc);

				setTitle(app.getTitle() + " - " + file.getName());
			}
		}
	}

	private final class OpenDocumentAction
		extends ExtendedAbstractAction
	{
		private final App<DOC> app;
		private final Action reloadSceneAction;
		private final JComponent parent;
		private static final long serialVersionUID = 1L;

		private OpenDocumentAction(App<DOC> app, Action reloadSceneAction,
			JComponent parent)
		{
			super("Open ...", 'o', IconUtils.image("document-open.png"));
			this.app = app;
			this.reloadSceneAction = reloadSceneAction;
			this.parent = parent;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (fc.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();

				DOC doc = app.openFile(file);

				view.setDocument(doc);

				setTitle(app.getTitle() + " - " + file.getName());
				reloadSceneAction.setEnabled(true);
			}
		}
	}

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
		appMenu.setMnemonic('A');
		for (Action appAction : app.getAppActions())
			appMenu.add(appAction);
		menuBar.add(appMenu);

		FileFilter ymlFilter = app.getFileFilter();
		fc.addChoosableFileFilter(ymlFilter);
		fc.setFileFilter(ymlFilter);
		fc.setCurrentDirectory(app.getDefaultDirectory());

		view = createView();
		add(view, BorderLayout.CENTER);

		Action reloadSceneAction = new ReloadDocumentAction(app);
		reloadSceneAction.setEnabled(false);

		JMenu sceneMenu = new JMenu(app.getDocType());
		sceneMenu.setMnemonic(app.getDocTypeMnemonic());
		Action openSceneAction = new OpenDocumentAction(app,
			reloadSceneAction, sceneMenu);

		sceneMenu.add(openSceneAction);
		sceneMenu.add(reloadSceneAction);

		menuBar.add(sceneMenu);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(toolBar, BorderLayout.NORTH);
		toolBar.add(openSceneAction);
		toolBar.add(reloadSceneAction);
		for (JComponent comp : view.getToolbarItems())
			toolBar.add(comp);

		pack();
	}

	protected abstract DocumentView<DOC> createView();
}
