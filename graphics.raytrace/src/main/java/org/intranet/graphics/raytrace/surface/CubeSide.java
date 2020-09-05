package org.intranet.graphics.raytrace.surface;

import org.intranet.graphics.raytrace.primitive.DoublePair;
import org.intranet.graphics.raytrace.primitive.Point;

public enum CubeSide
	implements UvMap
{
	RIGHT {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(1 - point.getZ()) / 2.0;
			double v = mod2(point.getY() + 1) / 2.0;

			return new DoublePair(u, v);
		}
	},
	LEFT {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(point.getZ() + 1) / 2.0;
			double v = mod2(point.getY() + 1) / 2.0;

			return new DoublePair(u, v);
		}
	},
	UP {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(1 + point.getX()) / 2.0;
			double v = mod2(1 - point.getZ()) / 2.0;

			return new DoublePair(u, v);
		}
	},
	DOWN {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(1 + point.getX()) / 2.0;
			double v = mod2(point.getZ() + 1) / 2.0;

			return new DoublePair(u, v);
		}
	},
	FRONT {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(point.getX() + 1) / 2.0;
			double v = mod2(point.getY() + 1) / 2.0;

			return new DoublePair(u, v);
		}
	},
	BACK {
		@Override
		public DoublePair map(Point point)
		{
			double u = mod2(1 - point.getX()) / 2.0;
			double v = mod2(point.getY() + 1) / 2.0;

			return new DoublePair(u, v);
		}
	};

	private static double mod2(double value)
	{
		return value % 2.0;
	}

	public abstract DoublePair map(Point p);

	public static CubeSide faceFromPoint(Point point)
	{
		double abs_x = Math.abs(point.getX());
		double abs_y = Math.abs(point.getY());
		double abs_z = Math.abs(point.getZ());
		double coord = Math.max(Math.max(abs_x, abs_y), abs_z);

		if (coord == point.getX()) return CubeSide.RIGHT;
		if (coord == -point.getX()) return CubeSide.LEFT;
		if (coord == point.getY()) return CubeSide.UP;
		if (coord == -point.getY()) return CubeSide.DOWN;
		if (coord == point.getZ()) return CubeSide.FRONT;

		// the only option remaining!
		return CubeSide.BACK;
	}
}