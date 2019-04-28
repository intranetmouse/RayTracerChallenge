package org.intranet.graphics.raytrace;

public final class PixelCoordinate
{
	private final int x;
	public int getX() { return x; }

	private final int y;
	public int getY() { return y; }

	public PixelCoordinate(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString()
	{
		return String.format("PixelCoord[%d,%d]", x, y);
	}
}