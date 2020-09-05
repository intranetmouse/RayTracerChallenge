package org.intranet.graphics.raytrace.surface;

public final class MathFuncs
	{
		static final double mod1(double val)
		{
			if (val < 0)
				return Math.abs(Math.floor(val)) + val;
			return val - Math.floor(val);
//			val = val % 1.0;
//			if (val < -0.5)
//				val = -val + 0.5 - 1;
//			else if (val < 0.0)
//				val = -val + 0.5;
//			return val;
		}

		static double mod2(double value)
		{
			return value % 2.0;
		}
	}