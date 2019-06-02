package org.intranet.graphics.raytrace.puttingItTogether;

import java.awt.Component;

public enum RepaintMode
{
	UPDATE_PER_PIXEL {
		@Override
		public void pixelUpdated(Component c, int x, int y)
		{ c.repaint(x, y, 1, 1); }
	},
	UPDATE_PERIODICALLY {
		@Override
		public void pixelUpdated(Component c, int x, int y)
		{ c.repaint(500); }
	}, UPDATE_AT_END{
		@Override
		public void pixelUpdated(Component c, int x, int y)
		{ }
	};
	public abstract void pixelUpdated(Component c, int x, int y);
}