package org.intranet.graphics.raytrace.surface;

public class AlignCheckUvPattern
	implements UvPattern
{
	private Color main;
	private Color ul;
	private Color ur;
	private Color bl;
	private Color br;

	public AlignCheckUvPattern(Color main, Color ul, Color ur, Color bl,
		Color br)
	{
		this.main = main;
		this.ul = ul;
		this.ur = ur;
		this.bl = bl;
		this.br = br;
	}

	@Override
	public Color colorAt(double u, double v)
	{
		if (u < 0) throw new IllegalArgumentException();
		if (u > 1) throw new IllegalArgumentException();
		if (v < 0) throw new IllegalArgumentException();
		if (v > 1) throw new IllegalArgumentException();
		// remember: v=0 at the bottom, v=1 at the top
		boolean isUpper = v > 0.8;
		boolean isLeft = u < 0.2;
		if (isUpper)
		{
			if (isLeft) return ul;
			if (u > 0.8) return ur;
		}
		else
		{
			boolean lower = v < 0.2;
			if (lower)
			{
				if (isLeft) return bl;
				if (u > 0.8) return br;
			}
		}

		return main;
//		return calcCornerWeightedColor(u, v);
	}

//	private Color calcCornerWeightedColor(double u, double v)
//	{
//		double top = 1.0;
//		double bottom = 0.0;
//		double left = 0.0;
//		double right = 1.0;
//
//		double ulStrength = strength(u, v, left, top);
//		double urStrength = strength(u, v, right, top);
//		double blStrength = strength(u, v, left, bottom);
//		double brStrength = strength(u, v, right, bottom);
//
//		Color ulPortion = ul.multiply(ulStrength);
//		Color urPortion = ur.multiply(urStrength);
//		Color blPortion = bl.multiply(blStrength);
//		Color brPortion = br.multiply(brStrength);
//		return ulPortion.add(urPortion).add(blPortion).add(brPortion);
//	}
//
//	private static final double SQRT2 = Math.sqrt(2);
//	private static double strength(double u, double v, double x, double y)
//	{
//		double xdist = u - x;
//		double ydist = y - v;
//		double dist = Math.sqrt(xdist * xdist + ydist * ydist);
//		return Math.max(0.0, 1 - SQRT2 * dist);
//	}
}
