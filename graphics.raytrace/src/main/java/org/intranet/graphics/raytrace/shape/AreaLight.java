package org.intranet.graphics.raytrace.shape;

import org.intranet.graphics.raytrace.Light;
import org.intranet.graphics.raytrace.Tracer;
import org.intranet.graphics.raytrace.World;
import org.intranet.graphics.raytrace.primitive.Point;
import org.intranet.graphics.raytrace.primitive.Vector;
import org.intranet.graphics.raytrace.surface.Color;

public class AreaLight
	implements Light
{
	private Point corner;
	public Point getCorner() { return corner; }

	private Vector uvec;
	public Vector getUvec() { return uvec; }

	private int usteps;
	public int getUsteps() { return usteps; }

	private Vector vvec;
	public Vector getVvec() { return vvec; }

	private int vsteps;
	public int getVsteps() { return vsteps; }

	private int numSamples;
	public int getNumSamples() { return numSamples; }

	private Point position;
	@Override
	public Point getPosition() { return position; }

	private Color intensity;
	@Override
	public Color getIntensity() { return intensity; }

	public AreaLight(Point cornerPoint, Vector unscaledUvector,
		int unscaledUvectorSteps, Vector unscaledVvector,
		int unscaledVvectorSteps, Color color)
	{
		this.corner = cornerPoint;

		uvec = unscaledUvector.divide(unscaledUvectorSteps);
		this.usteps = unscaledUvectorSteps;

		vvec = unscaledVvector.divide(unscaledVvectorSteps);
		this.vsteps = unscaledVvectorSteps;

		numSamples = usteps * vsteps;

		Vector averageVector = unscaledUvector.add(unscaledVvector).divide(2);
		position = new Point(averageVector.getX(), averageVector.getY(), averageVector.getZ());

		this.intensity = color;
	}

	public Point pointOnLight(int u, int v)
	{
		return corner.add(uvec.multiply(u + 0.5)).add(vvec.multiply(v + 0.5));
	}

	@Override
	public double intensityAt(Point pt, World world)
	{
		double total = 0.0;

		for (int v = 0; v < vsteps; v++)
		{
			for (int u = 0; u < usteps; u++)
			{
				Point light_position = pointOnLight(u, v);
				if (!Tracer.isShadowed(world, light_position, pt))
					total += 1.0;
			}
		}

		double intensityAt = total / numSamples;
System.out.println("Intensity at " + pt + " = " + intensityAt);
		return intensityAt;
	}
}
