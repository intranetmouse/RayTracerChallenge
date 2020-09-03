package org.intranet.graphics.raytrace.surface;

public class CheckersUvPattern
	implements UvPattern
{
	private final int uSquares;
	private final int vSquares;
	private final Color colorA;
	private final Color colorB;

	public CheckersUvPattern(int uSquares, int vSquares, Color colorA,
		Color colorB)
	{
		this.uSquares = uSquares;
		this.vSquares = vSquares;
		this.colorA = colorA;
		this.colorB = colorB;
	}

	@Override
	public Color colorAt(double u, double v)
	{
		double u2 = Math.floor(u * uSquares);
		double v2 = Math.floor(v * vSquares);

		if (((u2 + v2) % 2) == 0)
			return colorA;
		return colorB;
	}
}
