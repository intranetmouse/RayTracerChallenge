package org.intranet.graphics.raytrace.persistence.canvas;

import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.intranet.graphics.raytrace.surface.map.Canvas;

public class CanvasGifWriterTool
	extends JPanel
{
	private static final long serialVersionUID = 1L;

	private final JFileChooser fc = new JFileChooser();

	public CanvasGifWriterTool(Canvas canvas)
	{
		fc.setMultiSelectionEnabled(false);
		JCheckBox ckbx = new JCheckBox("Gif File");
		add(ckbx);
		CanvasToGifWriter canvasGifWriter = new CanvasToGifWriter(canvas);
		ckbx.addItemListener(
			evt -> {
				boolean selected = ckbx.isSelected();
				if (!selected)
				{
					ckbx.setText("Gif File");
					canvasGifWriter.disable();
					return;
				}
				SwingUtilities.invokeLater(() -> {
					int result = fc.showDialog(CanvasGifWriterTool.this, "Set");
					if (result != JFileChooser.APPROVE_OPTION)
					{
						ckbx.setSelected(false);
						return;
					}
					File selectedFile = fc.getSelectedFile();
					if (selectedFile != null)
					{
						canvasGifWriter.setFile(selectedFile);
						ckbx.setText(selectedFile.getName());
					}
				});
			}
		);
		canvas.addCanvasListener(canvasGifWriter);
	}
}