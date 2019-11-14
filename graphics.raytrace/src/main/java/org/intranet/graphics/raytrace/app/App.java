package org.intranet.graphics.raytrace.app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import org.intranet.graphics.raytrace.Camera;
import org.intranet.graphics.raytrace.Canvas;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.persistence.YamlWorldParser;
import org.intranet.graphics.raytrace.traversal.CanvasTraversalType;
import org.intranet.graphics.raytrace.ui.swing.canvas.CanvasComponent;
import org.intranet.graphics.raytrace.ui.swing.resolution.CanvasResolutionCombo;
import org.intranet.graphics.raytrace.ui.swing.resolution.Resolution;
import org.intranet.graphics.raytrace.ui.swing.traversalType.TraversalTypeSelection;

public class App
	extends JFrame
{
	private static final long serialVersionUID = 1L;

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> new App().setVisible(true));
	}

	private final YamlWorldParser parser = new YamlWorldParser();
	private final JFileChooser fc = new JFileChooser();
	private final Canvas canvas = new Canvas(640, 480);
	private final JButton renderButton;
	private final CanvasResolutionCombo canvasResolutionCombo;

	private CanvasTraversalType traversalType = CanvasTraversalType.AcrossDown;
	public CanvasTraversalType getTraversalType() { return traversalType; }
	public void setTraversalType(CanvasTraversalType value) { traversalType = value; }

	private boolean parallel = true;

	private World world;

	public App()
	{
		super("Tracing Rays");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		File defaultDirectory = new File(getClass().getResource(
			"/org/intranet/graphics/raytrace/yml/reflect-refract.yml").getFile()).getParentFile();
		fc.setCurrentDirectory(defaultDirectory);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu appMenu = new JMenu("App");
		Action exitAction = new AbstractAction("Exit") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		};
		appMenu.add(exitAction);
		menuBar.add(appMenu);

		JMenu sceneMenu = new JMenu("Scene");
		Action openSceneAction = new AbstractAction("Open ...") {
			private static final long serialVersionUID = 1L;
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (fc.showOpenDialog(sceneMenu) == JFileChooser.APPROVE_OPTION)
				{
					File file = fc.getSelectedFile();

					try
					{
						world = parser.parse(new FileInputStream(file));
						renderButton.setEnabled(true);
					}
					catch (FileNotFoundException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		sceneMenu.add(openSceneAction);
		menuBar.add(sceneMenu);

		CanvasComponent canvasComp = new CanvasComponent(canvas);
		JScrollPane canvasScroll = new JScrollPane(canvasComp);
		add(canvasScroll);

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
		add(toolBar, BorderLayout.NORTH);

		Resolution defaultResolution = Resolution.HDTV_720p;
		updateResolution(defaultResolution);
		canvasResolutionCombo = new CanvasResolutionCombo(
			defaultResolution, this::updateResolution);
		toolBar.add(canvasResolutionCombo);

		renderButton = new JButton("Render");
		renderButton.addActionListener(e -> render());
		renderButton.setEnabled(false);
		toolBar.add(renderButton);

		TraversalTypeSelection tts = new TraversalTypeSelection(traversalType,
			tt -> { traversalType = tt; });
		toolBar.add(tts);

		pack();
	}

	private void render()
	{
		canvas.clear();
		if (world == null)
			return;

		Camera camera = world.getCamera();
		camera.setHsize(canvas.getWidth());
		camera.setVsize(canvas.getHeight());
		camera.updatePixelSize();

		canvasResolutionCombo.setEnabled(false);
		renderButton.setEnabled(false);

		Runnable renderer = () -> {
			camera.render(world, canvas, parallel,
				traversalType.getTraversal(canvas));
			canvasResolutionCombo.setEnabled(true);
			renderButton.setEnabled(true);
		};
		new Thread(renderer).start();
	}

	private void updateResolution(Resolution e)
	{
		canvas.resize(e.getWidth(), e.getHeight());
	}
}
